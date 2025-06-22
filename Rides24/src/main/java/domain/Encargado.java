package domain;

import javax.persistence.*;

import dataAccess.DataAccess;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

@Entity
public class Encargado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String dni;

    @OneToMany(mappedBy = "encargado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Actividad> actividades = new ArrayList<>();

    public Encargado() {}

    public Encargado(String nombre, String dni) {
        this.nombre = nombre;
        this.dni = dni;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public List<Actividad> getActividades() { return actividades; }
    public void setActividades(List<Actividad> actividades) { this.actividades = actividades; }

    
    public void anadirActividad(String nombreActividad, int grado, float precio) {
        Actividad nueva = new Actividad(nombreActividad, grado, precio);
        nueva.setEncargado(this);
        actividades.add(nueva);
    }

    
    public void enviarFacturas() {
        
        int semanaActual = LocalDate.now().get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
        if (semanaActual < 5) {
            System.out.println("Aún no se pueden enviar facturas (antes de la semana 5).");
            return;
        }

        LocalDate hoy = LocalDate.now();
        int semanaPrevia = semanaActual - 1;

        
        Map<Socio, Float> facturasPendientes = new HashMap<>();

        for (Actividad actividad : actividades) {
            for (Reservar reserva : actividad.getReservas()) {
                LocalDate fechaReserva = LocalDate.parse(reserva.getFechaReserva());
                int semanaReserva = fechaReserva.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());

                if (semanaReserva == semanaPrevia) {
                    Socio socio = reserva.getSocio();
                    float precio = actividad.getPrecio();

                    facturasPendientes.put(socio, facturasPendientes.getOrDefault(socio, 0f) + precio);
                }
            }
        }

        
        for (Map.Entry<Socio, Float> entry : facturasPendientes.entrySet()) {
            Socio socio = entry.getKey();
            float total = entry.getValue();

            Factura factura = new Factura(hoy.toString(), total, socio);
            System.out.println("Enviando factura a " + socio.getCorreo());
            System.out.println(factura);
        }

        if (facturasPendientes.isEmpty()) {
            System.out.println("No hay reservas en la semana previa para facturar.");
        }
    }
    public void planificarSesion(String nombreActividad, Sala sala, String fecha, String horaInicio, String horaFin, DataAccess dataAccess) {
        for (Actividad act : actividades) {
            if (act.getNombre().equalsIgnoreCase(nombreActividad)) {
                Sesiones sesion = new Sesiones(fecha, horaInicio, horaFin, sala, act);
                dataAccess.guardarSesion(sesion);
                System.out.println("Sesión planificada: " + sesion);
                return;
            }
        }
        System.out.println("Actividad no encontrada entre las actividades del encargado.");
    }
}