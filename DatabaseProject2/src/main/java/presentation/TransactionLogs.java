package presentation;

import javax.swing.*;

import service.AppFacadeManager;
import service.BLFacadeInterface;

import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import java.util.List;

public class TransactionLogs extends JFrame {

    private static final long serialVersionUID = 1L;

    private JButton backButton;
    private DefaultListModel<String> LogsInfo = new DefaultListModel<>();
    private JList<String> LogList = new JList<>(LogsInfo);
    private JLabel jLabelEvents;
    private BLFacadeInterface facade;

    public TransactionLogs() {
        facade = AppFacadeManager.getBusinessLogic();

        setTitle("Transaction Logs");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitle = new JLabel("Transaction Logs", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Monospaced", Font.BOLD, 16));
        lblTitle.setBounds(150, 10, 300, 30);
        add(lblTitle);

        JScrollPane logScroll = new JScrollPane(LogList);
        logScroll.setBounds(20, 90, 540, 270);
        add(logScroll);

        jLabelEvents = new JLabel("");
        jLabelEvents.setBounds(20, 60, 540, 20);
        jLabelEvents.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(jLabelEvents);

        // Crear y centrar el botón Back
        backButton = new JButton("Back");
        backButton.setFont(new Font("Monospaced", Font.PLAIN, 14)); // Fuente más grande
        backButton.setBounds(200, 380, 200, 40); // Más grande y centrado
        add(backButton);

        // Eventos botones
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DatabaseProjectManager a = new DatabaseProjectManager();
                a.setVisible(true);
                dispose();
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                DatabaseProjectManager a = new DatabaseProjectManager();
                a.setVisible(true);
                dispose();
            }
        });

        try {
            LogsInfo.clear();

            List<String> Logs = facade.getLogs();

            if (Logs.isEmpty()) {
                jLabelEvents.setText("There are no Messages.");
            } else {
                jLabelEvents.setText("Showing Messages:");
                Iterator<String> logs = Logs.iterator();
                while (logs.hasNext()) {
                    LogsInfo.addElement(logs.next());
                }
            }
        } catch (Exception e1) {
            jLabelEvents.setText("Error loading logs.");
            e1.printStackTrace();
        }
    }
}
