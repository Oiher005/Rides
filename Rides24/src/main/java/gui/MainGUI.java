package gui;

import javax.swing.*;
import java.awt.*;
import businessLogic.BLFacade;

public class MainGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private static BLFacade appFacade;

    public MainGUI() {
        setTitle("Gestión Actividades Deportivas");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        JButton consultarSesionesBtn = new JButton("Consultar Sesiones");
        JButton registrarseBtn = new JButton("Registrarse (Socio/Encargado)");
        JButton loginBtn = new JButton("Login");

        consultarSesionesBtn.addActionListener(e -> consultarSesiones());
        registrarseBtn.addActionListener(e -> registrarUsuario());
        loginBtn.addActionListener(e -> hacerLogin());

        add(consultarSesionesBtn);
        add(registrarseBtn);
        add(loginBtn);
    }

    private void consultarSesiones() {
        try {
             String sesionesInfo = getBusinessLogic().consultarsesiones();
            JOptionPane.showMessageDialog(this, sesionesInfo, "Sesiones próximas", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al consultar sesiones: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarUsuario() {
        String[] tipos = {"Encargado", "Socio"};
        String tipo = (String) JOptionPane.showInputDialog(this, "Seleccione tipo de usuario:", "Tipo de usuario", JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);

        if (tipo == null) return;

        JTextField nombre = new JTextField();
        JTextField dni = new JTextField();
        JPanel panel = new JPanel(new GridLayout(tipo.equals("Socio") ? 5 : 2, 2));
        panel.add(new JLabel("Nombre:"));
        panel.add(nombre);
        panel.add(new JLabel("DNI:"));
        panel.add(dni);

        if (tipo.equals("Socio")) {
            JTextField tarjeta = new JTextField();
            JTextField cuenta = new JTextField();
            JTextField correo = new JTextField();
            panel.add(new JLabel("Tarjeta:"));
            panel.add(tarjeta);
            panel.add(new JLabel("Cuenta:"));
            panel.add(cuenta);
            panel.add(new JLabel("Correo:"));
            panel.add(correo);

            int result = JOptionPane.showConfirmDialog(this, panel, "Registro de Socio", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    getBusinessLogic().registrarUsuarioCompleto(nombre.getText(), dni.getText(), tarjeta.getText(), cuenta.getText(), correo.getText());
                    JOptionPane.showMessageDialog(this, "Socio registrado correctamente.");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error al registrar socio: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            int result = JOptionPane.showConfirmDialog(this, panel, "Registro de Encargado", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    getBusinessLogic().registrarEncargado(nombre.getText(), dni.getText());
                    JOptionPane.showMessageDialog(this, "Encargado registrado correctamente.");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error al registrar encargado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void hacerLogin() {
        String dni = JOptionPane.showInputDialog(this, "Ingrese su DNI:");
        if (dni == null || dni.trim().isEmpty()) return;

        try {
            String tipo = getBusinessLogic().login(dni);
            if ("Socio".equalsIgnoreCase(tipo) || "Usuario".equalsIgnoreCase(tipo)) {
                menuSocio(dni);
            } else if ("Encargado".equalsIgnoreCase(tipo)) {
                menuEncargado(dni);
            } else {
                JOptionPane.showMessageDialog(this, "Tipo de usuario desconocido.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error de login: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void menuSocio(String dni) {
        String[] opciones = {"Realizar reserva", "Cancelar reserva", "Consultar facturas", "Realizar pago"};
        String opcion = (String) JOptionPane.showInputDialog(this, "Seleccione una acción:", "Menú Socio", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (opcion == null) return;

        try {
            switch (opcion) {
                case "Realizar reserva":
                    getBusinessLogic().realizarReserva(dni);
                    JOptionPane.showMessageDialog(this, "Reserva realizada.");
                    break;
                case "Cancelar reserva":
                    getBusinessLogic().cancelarReserva(dni);
                    JOptionPane.showMessageDialog(this, "Reserva cancelada.");
                    break;
                case "Consultar facturas":
                    getBusinessLogic().consultarFacturas(dni);
                    break;
                case "Realizar pago":
                    getBusinessLogic().realizarPago(dni);
                    JOptionPane.showMessageDialog(this, "Pago realizado.");
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Opción no válida.");
                    break;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void menuEncargado(String dni) {
        String[] opciones = {"Añadir actividades", "Enviar facturas", "Planificar sesiones", "Crear sala"};
        String opcion = (String) JOptionPane.showInputDialog(this, "Seleccione una acción:", "Menú Encargado", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (opcion == null) return;

        try {
            switch (opcion) {
                case "Añadir actividades":
                    getBusinessLogic().anadirActividad(dni);
                    JOptionPane.showMessageDialog(this, "Actividad añadida.");
                    break;
                case "Enviar facturas":
                    getBusinessLogic().enviarFacturas(dni);
                    JOptionPane.showMessageDialog(this, "Facturas enviadas.");
                    break;
                case "Planificar sesiones":
                	getBusinessLogic().planificarSesiones(dni);
                	   JOptionPane.showMessageDialog(this, "Sesion planificada.");
                    break;
                case "Crear sala":
                    getBusinessLogic().crearSala(dni);
                    JOptionPane.showMessageDialog(this, "Sala creada correctamente.");
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Opción no válida.");
                    break;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void setBussinessLogic(BLFacade facade) {
        appFacade = facade;
    }

    public static BLFacade getBusinessLogic() {
        return appFacade;
    }
}
