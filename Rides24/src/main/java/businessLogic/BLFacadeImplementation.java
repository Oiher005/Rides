package businessLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.swing.JOptionPane;



import domain.*;
import dataAccess.DataAccess;

public class BLFacadeImplementation implements BLFacade {

	private List<Socio> socios;
    private List<Encargado> encargados;
    private List<Sesiones> sesiones;
    private List<Actividad> actividades;
    private List<Reservar> reservas;
    private List<Sala> salas;
    private DataAccess dataAccess;

    public BLFacadeImplementation() {
        this.socios = new ArrayList<>();
        this.encargados = new ArrayList<>();
        this.sesiones = new ArrayList<>();
        this.actividades = new ArrayList<>();
        this.reservas = new ArrayList<>();
        this.salas = new ArrayList<>();
        this.dataAccess = new DataAccess();
    }

    public String consultarsesiones() {
        if (sesiones.isEmpty()) {
            return "No hay sesiones planificadas.";
        }
        
        StringBuilder sb = new StringBuilder("Sesiones planificadas:\n");
        for (Sesiones sesion : sesiones) {
            sb.append("ID: ").append(sesion.getId())
              .append(", Actividad: ").append(sesion.getActividad() != null ? sesion.getActividad().getNombre() : "N/A")
              .append(", Sala: ").append(sesion.getSala() != null ? sesion.getSala().getNombre() : "N/A")
              .append(", Fecha: ").append(sesion.getFecha())
              .append(", Hora Inicio: ").append(sesion.getHoraini())
              .append(", Hora Fin: ").append(sesion.getHorafin())
              .append(", Plazas Libres: ").append(sesion.getPlazasLibres())
              .append("\n");
        }
        return sb.toString();
    }
    
    @Override
    public void registrarUsuarioCompleto(String nombre, String dni, String tarjeta, String cuenta, String correo) {
        Socio socio = new Socio(nombre,dni,tarjeta,cuenta,correo);
    	socios.add(socio);
    }

    @Override
    public void registrarEncargado(String nombre, String dni) {
    	Encargado encargado= new Encargado(nombre,dni);
        encargados.add(encargado);
    }

   
    @Override
    public String login(String dni) {
        if (socios.stream().anyMatch(s -> s.getDni().equals(dni))) return "Socio";
        if (encargados.stream().anyMatch(e -> e.getDni().equals(dni))) return "Encargado";
        return "Desconocido";
    }

    @Override
    public void realizarReserva(String dni) {
        Socio socio = buscarSocioPorDni(dni);
        if (socio == null) {
            JOptionPane.showMessageDialog(null, "Socio no encontrado.");
            return;
        }

        String actividadNombre = JOptionPane.showInputDialog("Nombre de la actividad que desea reservar:");
        String fecha = JOptionPane.showInputDialog("Fecha de la sesión (YYYY-MM-DD):");

        String resultado = socio.realizarReserva(actividadNombre, fecha, sesiones, actividades);
        Reservar nuevaReserva = socio.getReservas().get(socio.getReservas().size() - 1);
        reservas.add(nuevaReserva);  

        JOptionPane.showMessageDialog(null, resultado);
    }
    @Override
    public void cancelarReserva(String dni) {
        Socio socio = buscarSocioPorDni(dni);
        if (socio == null) {
            JOptionPane.showMessageDialog(null, "Socio no encontrado.");
            return;
        }

        String actividadNombre = JOptionPane.showInputDialog("Nombre de la actividad de la reserva a cancelar:");
        String fecha = JOptionPane.showInputDialog("Fecha de la sesión (YYYY-MM-DD):");

        Reservar reserva = socio.getReservas().stream()
                .filter(r -> r.getActividad().getNombre().equals(actividadNombre) &&
                             r.getFechaReserva().equals(fecha) &&
                             !r.getEstado().equals("cancelada"))
                .findFirst()
                .orElse(null);

        if (reserva == null) {
            JOptionPane.showMessageDialog(null, "Reserva no encontrada.");
            return;
        }

        String resultado = socio.cancelarReserva(reserva, sesiones, reservas);
        JOptionPane.showMessageDialog(null, resultado);
    }

    @Override
    public void consultarFacturas(String dni) {
        Socio socio = buscarSocioPorDni(dni);
        if (socio == null) {
            JOptionPane.showMessageDialog(null, "Socio no encontrado.");
            return;
        }

        String input = JOptionPane.showInputDialog("Número de factura:");
        try {
            int numero = Integer.parseInt(input);
            Factura factura = socio.consultarFactura(numero);
            if (factura != null) {
                JOptionPane.showMessageDialog(null, "Factura encontrada:\n" + factura.toString());
            } else {
                JOptionPane.showMessageDialog(null, "Factura no encontrada.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Número inválido.");
        }
    }

    @Override
    public void realizarPago(String dni) {
        Socio socio = buscarSocioPorDni(dni);
        if (socio == null) {
            JOptionPane.showMessageDialog(null, "Socio no encontrado.");
            return;
        }

        String input = JOptionPane.showInputDialog("Seleccione el número de la factura por favor:");
        try {
            int numFactura = Integer.parseInt(input);
            String resultado = socio.pagarFactura(numFactura);  
            JOptionPane.showMessageDialog(null, resultado);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Número de factura inválido.");
        }
    }

    @Override
    public void anadirActividad(String dni) {
        Encargado encargado = buscarEncargadoPorDni(dni);
        if (encargado == null) {
            JOptionPane.showMessageDialog(null, "Encargado no encontrado.");
            return;
        }

        String nombre = JOptionPane.showInputDialog("Nombre de la actividad:");
        int grado = Integer.parseInt(JOptionPane.showInputDialog("Grado de la actividad:"));
        float precio = Float.parseFloat(JOptionPane.showInputDialog("Precio de la actividad:"));

        Actividad nueva = new Actividad(nombre, grado, precio);
        actividades.add(nueva);
        System.out.println("Actividad añadida: " + nueva);
    }

    @Override
    public void enviarFacturas(String dniEncargado) {
        Encargado encargado = buscarEncargadoPorDni(dniEncargado);
        if (encargado == null) {
            JOptionPane.showMessageDialog(null, "Encargado no encontrado.");
            return;
        }

        encargado.enviarFacturas();
    }

    @Override
    public void planificarSesiones(String dni) {
        Encargado encargado = buscarEncargadoPorDni(dni);
        if (encargado == null) {
            JOptionPane.showMessageDialog(null, "Encargado no encontrado.");
            return;
        }

        String nombreActividad = JOptionPane.showInputDialog("Nombre de la actividad:");
        if (nombreActividad == null || nombreActividad.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nombre de actividad inválido.");
            return;
        }

        String nombreSala = JOptionPane.showInputDialog("Nombre de la sala:");
        if (nombreSala == null || nombreSala.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nombre de sala inválido.");
            return;
        }

        String fecha = JOptionPane.showInputDialog("Fecha (YYYY-MM-DD):");
        if (fecha == null || fecha.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fecha inválida.");
            return;
        }

        String horaInicio = JOptionPane.showInputDialog("Hora Inicio (HH:mm):");
        if (horaInicio == null || horaInicio.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hora inicio inválida.");
            return;
        }

        String horaFin = JOptionPane.showInputDialog("Hora Fin (HH:mm):");
        if (horaFin == null || horaFin.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hora fin inválida.");
            return;
        }

        // Buscar actividad
        Actividad actividad = actividades.stream()
            .filter(a -> a.getNombre().equalsIgnoreCase(nombreActividad))
            .findFirst()
            .orElse(null);

        if (actividad == null) {
            JOptionPane.showMessageDialog(null, "Actividad no encontrada.");
            return;
        }

        // Buscar sala usando el método buscarSala
        Sala sala = buscarSala(nombreSala);
        if (sala == null) {
            JOptionPane.showMessageDialog(null, "Sala no encontrada.");
            return;
        }

        // Crear sesión y añadir a las listas
        Sesiones sesion = new Sesiones(fecha, horaInicio, horaFin, sala, actividad);
        sesiones.add(sesion);

        JOptionPane.showMessageDialog(null, "Sesión planificada correctamente:\n" + sesion);
    }
    
    
    private Socio buscarSocioPorDni(String dni) {
        return socios.stream().filter(s -> s.getDni().equals(dni)).findFirst().orElse(null);
    }

    private Encargado buscarEncargadoPorDni(String dni) {
        return encargados.stream().filter(e -> e.getDni().equals(dni)).findFirst().orElse(null);
    }
    public void crearSala(String dni) {
    	Encargado encargado = buscarEncargadoPorDni(dni);
        if (encargado == null) {
            JOptionPane.showMessageDialog(null, "Encargado no encontrado.");
            return;
        }
        String nombre = JOptionPane.showInputDialog(null, "Nombre de la sala:");
        if (nombre == null || nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nombre inválido.");
            return;
        }

        String aforoStr = JOptionPane.showInputDialog(null, "Aforo de la sala:");
        if (aforoStr == null || aforoStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Aforo inválido.");
            return;
        }

        try {
            int aforo = Integer.parseInt(aforoStr);
            if (aforo <= 0) {
                JOptionPane.showMessageDialog(null, "El aforo debe ser un número positivo.");
                return;
            }

            if (buscarSala(nombre) != null) {
                JOptionPane.showMessageDialog(null, "Ya existe una sala con ese nombre.");
                return;
            }

            Sala nuevaSala = new Sala(nombre, aforo);
            salas.add(nuevaSala);
            JOptionPane.showMessageDialog(null, "Sala creada: " + nuevaSala);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El aforo debe ser un número válido.");
        }
    }
    public Sala buscarSala(String nombre) {
        for (Sala sala : salas) {
            if (sala.getNombre().equalsIgnoreCase(nombre)) {
                return sala;
            }
        }
        return null;  
    }
}
