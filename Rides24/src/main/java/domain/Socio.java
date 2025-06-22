package domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Entity
public class Socio {

    @Id
    private String dni;

    private String nombre;
    private String correo;
    private String cuenta;
    private String tarjeta;

    @OneToMany(mappedBy = "socio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservar> reservas = new ArrayList<>();

    @OneToMany(mappedBy = "socio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Factura> facturas = new ArrayList<>();

    public Socio() {}

    public Socio(String nombre, String dni, String correo, String cuenta, String tarjeta) {
        this.nombre = nombre;
        this.dni = dni;
        this.correo = correo;
        this.cuenta = cuenta;
        this.tarjeta = tarjeta;
    }

    // Getters y Setters

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    public List<Reservar> getReservas() {
        return reservas;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    
    
    public String realizarReserva(String actividadNombre, String fecha, List<Sesiones> sesiones, List<Actividad> actividades) {
        try {
            Actividad actividad = actividades.stream()
                .filter(a -> a.getNombre().equalsIgnoreCase(actividadNombre))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Actividad no encontrada"));

            Sesiones sesion = sesiones.stream()
                .filter(s -> s.getActividad().equals(actividad) && s.getFecha().equals(fecha))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Sesión no encontrada"));

            Reservar reserva = new Reservar();
            reserva.setSocio(this);
            reserva.setActividad(actividad);
            reserva.setFechaReserva(fecha);

            if (sesion.getPlazasLibres() > 0) {
                reserva.setEstado("confirmada");
                sesion.setPlazasLibres(sesion.getPlazasLibres() - 1);
            } else {
                reserva.setEstado("espera");
            }

            this.getReservas().add(reserva);  
            return reserva.getEstado().equals("confirmada") ?
                "Reserva confirmada correctamente." :
                "No hay plazas disponibles. Reserva en lista de espera.";

        } catch (NoSuchElementException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "Error al realizar la reserva: " + e.getMessage();
        }
    }
    
    public String cancelarReserva(Reservar reserva, List<Sesiones> sesiones, List<Reservar> todasLasReservas) {
        if (!this.getReservas().contains(reserva)) {
            return "La reserva no pertenece a este socio.";
        }

        reserva.setEstado("cancelada");

        Actividad actividad = reserva.getActividad();
        String fecha = reserva.getFechaReserva();

       
        Sesiones sesion = sesiones.stream()
                .filter(s -> s.getActividad().equals(actividad) && s.getFecha().equals(fecha))
                .findFirst()
                .orElse(null);

        if (sesion != null) {
            sesion.setPlazasLibres(sesion.getPlazasLibres() + 1);

             
            Reservar reservaEspera = todasLasReservas.stream()
                    .filter(r -> r.getActividad().equals(actividad)
                            && r.getFechaReserva().equals(fecha)
                            && r.getEstado().equals("espera"))
                    .sorted(Comparator.comparing(Reservar::getIdentificador)) 
                    .findFirst()
                    .orElse(null);

            if (reservaEspera != null) {
                reservaEspera.setEstado("confirmada");
                sesion.setPlazasLibres(sesion.getPlazasLibres() - 1);
            }
        }

        return "Reserva cancelada correctamente.";
    }
    
    public Factura consultarFactura(int numeroFactura) {
        return facturas.stream()
                .filter(f -> f.getCodigo() == numeroFactura)
                .findFirst()
                .orElse(null);
    }

   
    public String pagarFactura(int numeroFactura) {
        Factura factura = this.consultarFactura(numeroFactura);  

        if (factura == null) {
            return "Factura no encontrada.";
        }

        if (factura.isPagada()) {
            return "La factura ya está pagada.";
        }

        if (this.getCuenta() == null && this.getTarjeta() == null) {
            return "No se puede procesar el pago. Falta información de cuenta o tarjeta.";
        }

        factura.setPagada(true);
        return "Factura pagada correctamente.";
    }

    @Override
    public String toString() {
        return "Socio{" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", cuenta='" + cuenta + '\'' +
                ", tarjeta='" + tarjeta + '\'' +
                '}';
    }
}