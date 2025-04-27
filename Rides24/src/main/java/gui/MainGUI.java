package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import businessLogic.BLFacade;
import domain.Usuario;

public class MainGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private static BLFacade appFacade;

    public MainGUI() {
        iniciarInterfaz();
    }

    private void iniciarInterfaz() {
        String[] opciones = {"Consultar sesiones", "Registrarse", "Hacer login"};
        int seleccion = JOptionPane.showOptionDialog(
                null,
                "Seleccione una opción",
                "Bienvenido",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        switch (seleccion) {
            case 0:
                consultarSesiones();
                break;
            case 1:
                registrarUsuario();
                break;
            case 2:
                hacerLogin();
                break;
        }
    }

    private void registrarUsuario() {
        String[] tipos = {"Encargado", "Usuario"};
        String tipo = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione tipo de usuario:",
                "Tipo de usuario",
                JOptionPane.QUESTION_MESSAGE,
                null,
                tipos,
                tipos[0]
        );

        if (tipo == null) return;

        JTextField nombre = new JTextField();
        JTextField dni = new JTextField();
        JTextField tarjeta = new JTextField();
        JTextField cuenta = new JTextField();
        JTextField correo = new JTextField();

        JPanel panel = new JPanel(new GridLayout(tipo.equals("Usuario") ? 5 : 2, 2));
        panel.add(new JLabel("Nombre:"));
        panel.add(nombre);
        panel.add(new JLabel("DNI:"));
        panel.add(dni);

        if (tipo.equals("Usuario")) {
            panel.add(new JLabel("Tarjeta:"));
            panel.add(tarjeta);
            panel.add(new JLabel("Cuenta:"));
            panel.add(cuenta);
            panel.add(new JLabel("Correo:"));
            panel.add(correo);
        }

        int result = JOptionPane.showConfirmDialog(null, panel, "Registro de " + tipo,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                if (tipo.equals("Usuario")) {
                    getBusinessLogic().registrarUsuarioCompleto(
                            nombre.getText(), dni.getText(), tarjeta.getText(), cuenta.getText(), correo.getText()
                    );
                } else {
                    getBusinessLogic().registrarEncargado(nombre.getText(), dni.getText());
                }
                JOptionPane.showMessageDialog(null, tipo + " registrado correctamente.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al registrar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void hacerLogin() {
        String dni = JOptionPane.showInputDialog("Ingrese su DNI:");

        if (dni == null || dni.trim().isEmpty()) return;

        try {
            String tipo = getBusinessLogic().login(dni);
            if (tipo.equals("Usuario")) {
                String[] opciones = {"Realizar reserva", "Cancelar reserva", "Consultar facturas", "Realizar pago"};
                String opcion = (String) JOptionPane.showInputDialog(null, "Seleccione una acción:",
                        "Menú Usuario", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
                // Aquí iría la lógica correspondiente a cada acción

            } else if (tipo.equals("Encargado")) {
                String[] opciones = {"Añadir actividades", "Enviar factura", "Planificar sesiones"};
                String opcion = (String) JOptionPane.showInputDialog(null, "Seleccione una acción:",
                        "Menú Encargado", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
                // Aquí iría la lógica correspondiente a cada acción
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de login: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void consultarSesiones() {
        // Aquí podrías mostrar una lista de sesiones o redirigir a otra interfaz
        JOptionPane.showMessageDialog(null, "Función de consulta de sesiones aún no implementada.");
    }

    public static void setBussinessLogic(BLFacade facade) {
        appFacade = facade;
    }

    public static BLFacade getBusinessLogic() {
        return appFacade;
    }
}