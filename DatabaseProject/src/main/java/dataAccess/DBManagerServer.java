package dataAccess;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import configuration.ConfigXML;

import javax.swing.JTextArea;



import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

/**
 * It runs the database server as a separate process.
 */
public class DBManagerServer extends JDialog {


	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	JTextArea textArea;
	ConfigXML c;
	
	public final static String DB_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
	public final static String DB_URL = "jdbc:mysql://dif-mysql.ehu.es:23306/dbi68";

	public final static String DB_USERNAME = "DBI68";
	public final static String DB_PASSWORD = "DBI68";

	
	//For windows
//    private String objectDbpath="src\\main\\resources\\objectdb.jar";
    
    //For mac 
    private String objectDbpath="src//main//resources//objectdb.jar";

 	


	public static void main(String[] args) {
		try {
			
			
			DBManagerServer dialog = new DBManagerServer();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public DBManagerServer() {
	    
		setTitle("objectDBManagerServer: running the database server");
		setBounds(100, 100, 486, 180);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			textArea = new JTextArea();
			contentPanel.add(textArea);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						textArea.append("\n\n\nClosing the database... ");
					    try {
					    	System.out.println("Server close");
					    	 try {
					    		    
					    		    
							    	Runtime.getRuntime().exec("java -cp "+objectDbpath+" com.objectdb.Server -port "+ c.getDatabasePort()+" stop");
							    	
							    } catch (Exception ioe) {
							    	System.out.println (ioe);
							    }

								System.exit(1);
							
						} catch (Exception e1) {
						}
						System.exit(1);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.isDatabaseLocal()) {
			textArea.append("\nERROR, the database is configured as local");
		}
		else {
		try{
			System.out.println("Lauching SQL ");

			
			try {
		    	Runtime.getRuntime().exec("java -cp "+objectDbpath+" com.objectdb.Server -port "+ c.getDatabasePort()+" start");
		    } catch (Exception ioe) {
		    	System.out.println (ioe);
		    }

		    
			textArea.append("\nPress button to exit this database server... ");
			
		} catch (Exception e) {
			textArea.append("Something has happened in ObjectDbManagerServer: "+e.toString());

		}
		
		}
	}

	public static Connection getConnection() throws ClassNotFoundException, SQLException {

		Connection con = null;
		System.out.println("==> Starting the connection <==");
		// load the Driver Class
		Class.forName(DB_DRIVER_CLASS);
		System.out.println("  ==> Driver loaded <==");
		// create the connection now
		con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

		System.out.println("  ==> DB Connection created successfully");
		return con;
	}


}
	
