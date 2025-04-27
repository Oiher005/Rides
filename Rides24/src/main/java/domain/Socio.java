package domain;

import javax.persistence.Entity;

@Entity
public class Socio extends Usuario {

    public Socio() {
        // Constructor vacío requerido por JPA
    }

    public Socio(String nombre, String dni, String tarjeta, String cuenta, String correo) {
        super(nombre, dni, tarjeta, cuenta, correo);
    }

    @Override
    public String toString() {
        return "Socio{" + super.toString() + "}";
    }
}