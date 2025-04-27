package dataAccess;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import domain.Usuario;
import domain.Encargado;

public class DataAccess {

    private EntityManagerFactory emf;
    private EntityManager em;

    public DataAccess() {
        // Asegúrate que este nombre coincida con persistence.xml
        emf = Persistence.createEntityManagerFactory("default");
        em = emf.createEntityManager();
    }

    public void open() {
        if (!em.isOpen()) {
            em = emf.createEntityManager();
        }
    }

    public void close() {
        if (em.isOpen()) {
            em.close();
        }
    }

    // Método para registrar un usuario completo
    public void registrarUsuario(String nombre, String dni, String tarjeta, String cuenta, String correo) {
        em.getTransaction().begin();
        Usuario usuario = new Usuario(nombre, dni, tarjeta, cuenta, correo);
        em.persist(usuario);
        em.getTransaction().commit();
    }

    // Método para registrar un encargado
    public void registrarEncargado(String nombre, String dni) {
        em.getTransaction().begin();
        Encargado encargado = new Encargado(nombre, dni);
        em.persist(encargado);
        em.getTransaction().commit();
    }

    // Método para encontrar si un DNI existe como Usuario o Encargado
    public boolean existeUsuarioPorDNI(String dni) {
        Usuario usuario = em.find(Usuario.class, dni);
        return usuario != null;
    }

    public boolean existeEncargadoPorDNI(String dni) {
        Encargado encargado = em.find(Encargado.class, dni);
        return encargado != null;
    }

    // Obtener tipo por DNI
    public String obtenerTipoPorDNI(String dni) {
        if (existeUsuarioPorDNI(dni)) return "Usuario";
        if (existeEncargadoPorDNI(dni)) return "Encargado";
        return null;
    }
}

