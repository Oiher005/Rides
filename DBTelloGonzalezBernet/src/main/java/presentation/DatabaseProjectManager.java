package presentation;
import javax.swing.*;
import service.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class DatabaseProjectManager extends JFrame {

	private static final long serialVersionUID = 1L;

	private JButton btnConnect, btnDisconnect, btnRunQuery, btnLogs;
	private JComboBox<String> schemaSelector;
	private JLabel lblSchemaStatus;
	private JTextArea consoleArea;

	public DatabaseProjectManager() {
		setTitle("DATABASE PROJECT MANAGER");
		setSize(500, 390); // Aumentamos altura para la consola
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // Centra la ventana

		BLFacadeInterface facade = AppFacadeManager.getBusinessLogic();

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(240, 240, 240));
		getContentPane().add(panel);

		JLabel header = new JLabel("DATABASE PROJECT MANAGER", SwingConstants.CENTER);
		header.setFont(new Font("Monospaced", Font.BOLD, 16));
		header.setBounds(50, 10, 400, 30);
		panel.add(header);

		// Botones de conexión
		btnConnect = new JButton("Connect to Database");
		btnConnect.setBounds(50, 60, 180, 30);
		panel.add(btnConnect);

		btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setBounds(250, 60, 180, 30);
		panel.add(btnDisconnect);

		lblSchemaStatus = new JLabel(">> Active Schema:");
		lblSchemaStatus.setBounds(50, 110, 150, 25);
		panel.add(lblSchemaStatus);

		String[] schemas = {"COMPANY", "TRAVEL", "RESTAURANT"};
		schemaSelector = new JComboBox<>(schemas);
		schemaSelector.setBounds(200, 110, 230, 25);
		panel.add(schemaSelector);

		btnRunQuery = new JButton("Run SQL Query");
		btnRunQuery.setBounds(80, 160, 150, 30);
		panel.add(btnRunQuery);

		btnLogs = new JButton("View Transaction Logs");
		btnLogs.setBounds(260, 160, 180, 30);
		panel.add(btnLogs);

		consoleArea = new JTextArea();
		consoleArea.setEditable(false);
		consoleArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		JScrollPane scrollPane = new JScrollPane(consoleArea);
		scrollPane.setBounds(50, 210, 400, 110);
		panel.add(scrollPane);

		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					facade.openDB();
					log(" Connected to the database.");

				}
				catch (SQLException|ClassNotFoundException er) {
					String error= "Error al abrir conexión: " + er.getMessage();
					log (error);
					System.out.println(error);
				}



			}
		});

		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					facade.closeDB();
					log(" Disconnected from the database.");
				}
				catch (SQLException er) {
					String error= "Error al cerrar conexión: " + er.getMessage();
					log (error);
					System.out.println(error);
				}
			}
		});

		schemaSelector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedSchema = (String) schemaSelector.getSelectedItem();
				log("Switched to schema: " + selectedSchema);
			}
		});
		
		btnRunQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RunQueries a=new RunQueries();
				a.setVisible(true);
				dispose();
			}
		});
		
		btnLogs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TransactionLogs a=new TransactionLogs();
				a.setVisible(true);
				dispose();
			}
		});
		
		
		
		
	}

	private void log(String message) {
		consoleArea.append(message + "\n");
		consoleArea.setCaretPosition(consoleArea.getDocument().getLength());
	}


}
