package domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import dataAccess.DataAccess;
import domain.Sala;
import domain.Actividad;
import domain.Sesiones;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String dni;
    private String tarjeta;
    private String cuenta;
    private String correo;

    public Usuario() {
        
    }

    public Usuario(String nombre, String dni, String tarjeta, String cuenta, String correo) {
        this.nombre = nombre;
        this.dni = dni;
        this.tarjeta = tarjeta;
        this.cuenta = cuenta;
        this.correo = correo;
    }

    
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDni() {
        return dni;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public String getCuenta() {
        return cuenta;
    }

    public String getCorreo() {
        return correo;
    }

    
    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", dni='" + dni + '\'' +
                ", tarjeta='" + tarjeta + '\'' +
                ", cuenta='" + cuenta + '\'' +
                ", correo='" + correo + '\'' +
                '}';
    }
    public String consultarsesiones() {
        DataAccess db = new DataAccess();
        db.open();
        StringBuilder sb = new StringBuilder();

        try {
            LocalDate hoy = LocalDate.now();
            LocalDate dentroDe7Dias = hoy.plusDays(7);

            List<Sesiones> sesiones = db.obtenerSesionesEntreFechas(hoy, dentroDe7Dias);

            sb.append("Sesiones de hoy a los próximos 7 días:\n");

            for (Sesiones sesion : sesiones) {
                sb.append("----------------------------------\n");
                sb.append("Fecha: ").append(sesion.getFecha()).append("\n");
                sb.append("Hora inicio: ").append(sesion.getHoraini()).append("\n");
                sb.append("Hora fin: ").append(sesion.getHorafin()).append("\n");

                Sala sala = sesion.getSala();
                if (sala != null) {
                    sb.append("Sala: ").append(sala.getNombre())
                      .append(" (Aforo: ").append(sala.getAforo()).append(")\n");
                }

                Actividad actividad = sesion.getActividad();
                if (actividad != null) {
                    sb.append("Actividad: ").append(actividad.getNombre())
                      .append(" (Grado: ").append(actividad.getGrado())
                      .append(", Precio: ").append(actividad.getPrecio()).append(")\n");
                }
            }

        } catch (Exception e) {
            return "Error al consultar sesiones: " + e.getMessage();
        } finally {
            db.close();
        }

        return sb.toString();
    }
}
