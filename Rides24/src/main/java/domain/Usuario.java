package domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

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
        // Constructor vacío requerido por JPA
    }

    public Usuario(String nombre, String dni, String tarjeta, String cuenta, String correo) {
        this.nombre = nombre;
        this.dni = dni;
        this.tarjeta = tarjeta;
        this.cuenta = cuenta;
        this.correo = correo;
    }

    // Getters
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

    // Setters
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
}
