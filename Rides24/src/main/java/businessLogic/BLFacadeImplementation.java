package businessLogic;

import domain.Usuario;
import domain.Encargado;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class BLFacadeImplementation implements BLFacade {

    private EntityManagerFactory emf;
    private EntityManager em;

    public BLFacadeImplementation() {
        // Inicialización de EntityManager para la base de datos
        emf = Persistence.createEntityManagerFactory("my-pu");  // Nombre del Persistence Unit
        em = emf.createEntityManager();
    }

    @Override
    public void registrarUsuarioCompleto(String nombre, String dni, String tarjeta, String cuenta, String correo) throws Exception {
        // Verificamos si el usuario ya está registrado
        Usuario existingUsuario = em.find(Usuario.class, dni);
        if (existingUsuario != null) {
            throw new Exception("El usuario ya está registrado.");
        }

        // Creamos el nuevo usuario
        Usuario usuario = new Usuario(nombre, dni, tarjeta, cuenta, correo);

        // Persistimos el usuario en la base de datos
        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception("Error al registrar el usuario: " + e.getMessage());
        }
    }

    @Override
    public void registrarEncargado(String nombre, String dni) throws Exception {
        // Verificamos si el encargado ya está registrado
        Encargado existingEncargado = em.find(Encargado.class, dni);
        if (existingEncargado != null) {
            throw new Exception("El encargado ya está registrado.");
        }

        // Creamos el nuevo encargado
        Encargado encargado = new Encargado(nombre, dni);

        // Persistimos el encargado en la base de datos
        try {
            em.getTransaction().begin();
            em.persist(encargado);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new Exception("Error al registrar el encargado: " + e.getMessage());
        }
    }

    @Override
    public String login(String dni) throws Exception {
        // Verificamos si el usuario existe
        Usuario usuario = em.find(Usuario.class, dni);
        if (usuario != null) {
            return "Usuario";
        }

        // Verificamos si el encargado existe
        Encargado encargado = em.find(Encargado.class, dni);
        if (encargado != null) {
            return "Encargado";
        }

        // Si no existe el DNI
        throw new Exception("DNI no encontrado.");
    }
    
    // Si deseas cerrar el EntityManager, puedes agregar un método para cerrar los recursos
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
