package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;

import service.AppFacadeManager;
import service.BLFacadeInterface;

public class RunQueries extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextArea queryArea;
	private JTextArea resultArea;
	private JButton runButton, backButton;
	private BLFacadeInterface facade;

	public RunQueries() {
		setTitle("Execute SQL Query");
		setSize(600, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);

		facade = AppFacadeManager.getBusinessLogic();

		JLabel lblTitle = new JLabel("Execute SQL Query", SwingConstants.CENTER);
		lblTitle.setFont(new Font("Monospaced", Font.BOLD, 16));
		lblTitle.setBounds(150, 10, 300, 30);
		add(lblTitle);

		JLabel lblQuery = new JLabel("Query:");
		lblQuery.setBounds(20, 50, 100, 20);
		add(lblQuery);

		queryArea = new JTextArea();
		queryArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
		JScrollPane queryScroll = new JScrollPane(queryArea);
		queryScroll.setBounds(20, 70, 540, 120);
		add(queryScroll);

		// Botones
		runButton = new JButton("Run Query");
		runButton.setBounds(150, 200, 120, 30);
		add(runButton);

		backButton = new JButton("Back");
		backButton.setBounds(300, 200, 120, 30);
		add(backButton);

		// Resultados
		JLabel lblResult = new JLabel("Results:");
		lblResult.setBounds(20, 240, 100, 20);
		add(lblResult);

		resultArea = new JTextArea();
		resultArea.setEditable(false);
		resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		JScrollPane resultScroll = new JScrollPane(resultArea);
		resultScroll.setBounds(20, 260, 540, 180);
		add(resultScroll);

		// Eventos
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runSQLQuery();
			}
		});

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DatabaseProjectManager a=new DatabaseProjectManager();
				a.setVisible(true);
				dispose();

			}
		});
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				DatabaseProjectManager a=new DatabaseProjectManager();
				a.setVisible(true);	
				dispose(); 
			} 
		}); 

	}
	
	

	private void runSQLQuery() {
		String query = queryArea.getText().trim();
		if (query.isEmpty()) {
			resultArea.setText("Please enter a SQL query.");
			return;
		}
		
		resultArea.setText(facade.RunQuery(query));
	}		

//		try (Connection con = facade.getDBConnection();
//				Statement stmt = con.createStatement()) {
//
//			if (query.toLowerCase().startsWith("select")) {
//				ResultSet rs = stmt.executeQuery(query);
//				ResultSetMetaData meta = rs.getMetaData();
//				int columns = meta.getColumnCount();
//
//				StringBuilder result = new StringBuilder();
//				while (rs.next()) {
//					for (int i = 1; i <= columns; i++) {
//						result.append(meta.getColumnName(i)).append(": ").append(rs.getString(i)).append("\t");
//					}
//					result.append("\n");
//				}
//
//				resultArea.setText(result.toString().isEmpty() ? "No results." : result.toString());
//
//			} else {
//				int affected = stmt.executeUpdate(query);
//				resultArea.setText("Query executed successfully.\nRows affected: " + affected);
//			}
//
//		} catch (SQLException ex) {
//			resultArea.setText("Error:\n" + ex.getMessage());
//		}
	
}
