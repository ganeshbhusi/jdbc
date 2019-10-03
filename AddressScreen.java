import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

public class AddressScreen{  
JFrame f;
public static final Color VERY_LIGHT_RED = new Color(87,174,241);
String firstName,lastName,email,phone;
public void AddressScreen(){  
    f=new JFrame();
    f.setSize(450,300);
    //setting the title for our program
    f.setTitle("Address Book");
    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    f.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;


    JLabel label0 = new JLabel("Address Book");
    c.weightx = 0.5;
    c.gridx = 1;
    c.gridy = 0;
    c.ipadx=20;
    f.add(label0,c);

    //creating labels
    JLabel label1 = new JLabel("First Name");
    c.weightx = 0.5;
    c.gridx = 0;
    c.gridy = 1;
    f.add(label1,c);
    JLabel label2 = new JLabel("Last Name");
    c.weightx = 0.5;
    c.gridx = 0;
    c.gridy = 2;
    f.add(label2,c);
    JLabel label3 = new JLabel("E-mail");
    c.weightx = 0.5;
    c.gridx = 0;
    c.gridy = 3;
    f.add(label3,c);
    JLabel label4 = new JLabel("Phone Number");
    c.weightx = 0.5;
    c.gridx = 0;
    c.gridy = 4;
    f.add(label4,c);

    //creating input fields to enter inches and fahrenheit values
    TextField t1=new TextField(5);
    c.weightx = 0.5;
    c.gridx = 1;
    c.gridy = 1;
    f.add(t1,c);
    TextField t2=new TextField(5);
    c.weightx = 0.5;
    c.gridx = 1;
    c.gridy = 2;
    f.add(t2,c);
    TextField t3=new TextField(5);
    c.weightx = 0.5;
    c.gridx = 1;
    c.gridy = 3;
    f.add(t3,c);
    TextField t4=new TextField(5);
    c.weightx = 0.5;
    c.gridx = 1;
    c.gridy = 4;
    f.add(t4,c);

    //creating two buttons as Convert
    JButton b1=new JButton("Add Address"); 
    c.weightx = 0.5;
    c.gridx = 2;
    c.gridy = 1;
    f.add(b1,c);
    JButton b2=new JButton("Update Address");
    c.weightx = 0.5;
    c.gridx = 2;
    c.gridy = 2;
    f.add(b2,c);
    JButton b3=new JButton("Delete Address");
    c.weightx = 0.5;
    c.gridx = 2;
    c.gridy = 3;
    f.add(b3,c);
    JButton b4=new JButton("Delete All");
    c.weightx = 0.5;
    c.gridx = 2;
    c.gridy = 4;
    f.add(b4,c);

    JLabel error1=new JLabel();
    error1.setForeground(Color.RED);
    c.ipady = 0;       //reset to default
    c.anchor = GridBagConstraints.PAGE_END; //bottom of space
    c.insets = new Insets(0,0,0,0);  //top padding
    c.gridx = 0;       //aligned with button 2
    c.gridwidth = 3;   //2 columns wide
    c.gridy = 6;       //5th row
    f.getContentPane().add(error1,c);

    Object[][] data = {};

    String[] columnNames = {"Address ID","First Name","Last Name","E-mail","Phone Number"};

    JPanel panel = new JPanel();

    DefaultTableModel model = new DefaultTableModel(data,columnNames) {
      public boolean isCellEditable(int rowIndex, int mColIndex) {//make non-editable rows/columns
        return false;
      }
    };

    getData(model);
    

    JTable table = new JTable(model);
    table.setPreferredScrollableViewportSize(new Dimension(430,100));
    // table.

    table.setFillsViewportHeight(true);

    JScrollPane tableContainer = new JScrollPane(table);
    tableContainer.setVisible(true);
    panel.add(tableContainer, BorderLayout.CENTER);
    c.ipady = 100;       //reset to default
    c.anchor = GridBagConstraints.PAGE_END; //bottom of space
    c.insets = new Insets(10,0,0,0);  //top padding
    c.gridx = 0;       //aligned with button 2
    c.gridwidth = 3;   //2 columns wide
    c.gridy = 7;       //5th row
    f.getContentPane().add(panel,c);

    f.setVisible(true);//set the visibility of jframe

    //when clicks the button add address
    b1.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent e){
        firstName=t1.getText().toString();
        lastName=t2.getText().toString();
        email=t3.getText().toString();
        phone=t4.getText().toString();
        // System.out.println(t1.getText().toString());
        if(firstName.trim().isEmpty() || lastName.trim().isEmpty() || email.trim().isEmpty()|| phone.trim().isEmpty()){
            System.out.println("All are required");
            error1.setText("You must enter a first name,last name,e-mail and phone number");
        }else{
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/testing","root","wamp");
                // Connection con=DriverManager.getConnection("jdbc:derby://localhost:1527/addresses","paras","paras");
                PreparedStatement stmt=con.prepareStatement("INSERT INTO addresses values(?,?,?,?,?)");
                stmt.setInt(1,0); 
                stmt.setString(2,firstName);
                stmt.setString(3,lastName);
                stmt.setString(4,email);
                stmt.setString(5,phone);

                int i=stmt.executeUpdate();  
                System.out.println(i+" records inserted");
                error1.setText("New address is successfully added");
                // model.addRow(new Object[]{"-", firstName,lastName,email,phone});
                getData(model);
            }catch(Exception e1){ System.out.println(e1);}
        }
    }  
    });

    b2.addActionListener(new ActionListener(){  
    public void actionPerformed(ActionEvent e){

        firstName=t1.getText().toString();
        lastName=t2.getText().toString();
        email=t3.getText().toString();
        phone=t4.getText().toString();
        
        if(table.getSelectionModel().isSelectionEmpty()){
        	error1.setText("Please select a row");
        }else{
        	error1.setText("");
        	String dd=table.getValueAt(table.getSelectedRow(), 0).toString();
        	Integer int1=Integer.parseInt(dd);
            if(firstName.isEmpty()){
                firstName=table.getValueAt(table.getSelectedRow(), 1).toString();
            }else{
                firstName=t1.getText().toString();
            }
            if(lastName.isEmpty()){
                lastName=table.getValueAt(table.getSelectedRow(), 2).toString();
            }else{
                lastName=t2.getText().toString();
            }
            if(email.isEmpty()){
                email=table.getValueAt(table.getSelectedRow(), 3).toString();
            }else{
                email=t3.getText().toString();
            }
            if(phone.isEmpty()){
                phone = table.getValueAt(table.getSelectedRow(), 4).toString();
            }else{
                phone=t4.getText().toString();
            }
	        	try{
					Class.forName("com.mysql.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/testing","root","wamp");
                    // Connection con=DriverManager.getConnection("jdbc:derby://localhost:1527/addresses","paras","paras");
					PreparedStatement stmt=con.prepareStatement("UPDATE addresses SET FirstName=?,LastName=?,Email=?,PhoneNumber=? where AddressID=?");  
					stmt.setString(1,firstName);
					stmt.setString(2,lastName);
					stmt.setString(3,email);
					stmt.setString(4,phone);
					stmt.setInt(5,int1);
					int i=stmt.executeUpdate();  
					System.out.println(i+" record updated");
				}catch(Exception e2){ System.out.println(e2);}
			
			System.out.println(firstName+" , "+lastName+" , "+email+" , "+phone);
        }
        getData(model);
    }  
    });

    b3.addActionListener(new ActionListener(){  
    public void actionPerformed(ActionEvent e){        
        int count= table.getModel().getRowCount();
        
        if(count>0){
            if(table.getSelectionModel().isSelectionEmpty()){
               error1.setText("Please select a row");
            }else{
                String dd=table.getValueAt(table.getSelectedRow(), 0).toString();
                Integer int1=Integer.parseInt(dd);
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/testing","root","wamp");
                    // Connection con=DriverManager.getConnection("jdbc:derby://localhost:1527/addresses","paras","paras");
                    PreparedStatement stmt=con.prepareStatement("DELETE FROM addresses where AddressID=?");  
                    stmt.setInt(1,int1);
                    int i=stmt.executeUpdate();  
                    System.out.println(i+" record deleted");
                    error1.setText("Selected address was successfully deleted");
                }catch(Exception e3){ System.out.println(e3);}
            }
        }
        getData(model);
    }  
    });

    b4.addActionListener(new ActionListener(){  
    public void actionPerformed(ActionEvent e){
        System.out.println("Welcome");
        int count= table.getModel().getRowCount();
        if(count>0){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/testing","root","wamp");
                // Connection con=DriverManager.getConnection("jdbc:derby://localhost:1527/addresses","paras","paras");
                PreparedStatement stmt=con.prepareStatement("DELETE FROM addresses");  
                int i=stmt.executeUpdate();
                System.out.println(i+" record(s) deleted");
                error1.setText("All Addresses successfully deleted");
            }catch(Exception e4){System.out.println(e4);}
        }else{
            error1.setText("Table is already blank");
        }
        getData(model);
    }  
    });
}

public static void getData(DefaultTableModel model){
		model.setRowCount(0);
    	try {
	        Class.forName("com.mysql.jdbc.Driver");
	        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing","root","wamp");
	        PreparedStatement pstm = con.prepareStatement("SELECT * FROM addresses");
	        ResultSet Rs = pstm.executeQuery();
	        while(Rs.next()){
	            model.addRow(new Object[]{Rs.getInt(1), Rs.getString(2),Rs.getString(3),Rs.getString(4),Rs.getString(5)});
	        }
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	    }
    }

public static void main(String[] args){
        AddressScreen obj1=new AddressScreen();
        obj1.AddressScreen();
    }

} 