package dataAccess;

import domain.Factura;
import domain.Usuario;
import domain.Encargado;
import domain.Sesiones;
import domain.Reservar;  // Clase Reservar ahora con Socio y Actividad
import domain.Socio;     // Asegúrate de que Socio esté en el paquete domain
import domain.Actividad; // Asegúrate de que Actividad esté en el paquete domain
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DataAccess {

    private EntityManagerFactory emf;
    private EntityManager em;

    public DataAccess() {
        // Ruta relativa o absoluta a tu archivo .odb, puede ser "./database.odb"
        String dbUrl = "objectdb:./mydb.odb";
        emf = Persistence.createEntityManagerFactory(dbUrl);
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
    
    public void registrarUsuario(String nombre, String dni, String tarjeta, String cuenta, String correo) {
        em.getTransaction().begin();
        Usuario usuario = new Usuario(nombre, dni, tarjeta, cuenta, correo);
        em.persist(usuario);
        em.getTransaction().commit();
    }
    
    public void registrarEncargado(String nombre, String dni) {
        em.getTransaction().begin();
        Encargado encargado = new Encargado(nombre, dni);
        em.persist(encargado);
        em.getTransaction().commit();
    }
    
    public void registrarFactura(Factura factura) {
        em.getTransaction().begin();
        em.persist(factura);
        em.getTransaction().commit();
    }
    
    public Factura obtenerFactura(int codigo) {
        return em.find(Factura.class, codigo);
    }

    public void actualizarFactura(Factura factura) {
        em.getTransaction().begin();
        em.merge(factura);
        em.getTransaction().commit();
    }

    public void eliminarFactura(int codigo) {
        em.getTransaction().begin();
        Factura factura = em.find(Factura.class, codigo);
        if (factura != null) {
            em.remove(factura);
        }
        em.getTransaction().commit();
    }

    public boolean existeUsuarioPorDNI(String dni) {
        Usuario usuario = em.find(Usuario.class, dni);
        return usuario != null;
    }

    public boolean existeEncargadoPorDNI(String dni) {
        Encargado encargado = em.find(Encargado.class, dni);
        return encargado != null;
    }

    public String obtenerTipoPorDNI(String dni) {
        if (existeUsuarioPorDNI(dni)) return "Usuario";
        if (existeEncargadoPorDNI(dni)) return "Encargado";
        return null;
    }

    public List<Sesiones> obtenerSesionesEntreFechas(LocalDate desde, LocalDate hasta) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String desdeStr = desde.format(formatter);
        String hastaStr = hasta.format(formatter);

        return em.createQuery("SELECT s FROM Sesiones s WHERE s.fecha >= :desde AND s.fecha <= :hasta", Sesiones.class)
                .setParameter("desde", desdeStr)
                .setParameter("hasta", hastaStr)
                .getResultList();
    }
    
    public List<Usuario> obtenerTodosLosUsuarios() {
        return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    }

    
    public List<Encargado> obtenerTodosLosEncargados() {
        return em.createQuery("SELECT e FROM Encargado e", Encargado.class).getResultList();
    }

    public List<Factura> obtenerTodasLasFacturas() {
        return em.createQuery("SELECT f FROM Factura f", Factura.class).getResultList();
    }

    public void guardarSesion(Sesiones sesion) {
        em.getTransaction().begin();
        em.persist(sesion);
        em.getTransaction().commit();
    }

    public String reservarActividad(Socio socio, Actividad actividad, Sesiones sesion, String estadoReserva) {
        em.getTransaction().begin();

        if (sesion.getPlazasLibres() <= 0) {
            em.getTransaction().rollback();
            return "No hay plazas disponibles para esta sesión.";
        }

        Reservar reserva = new Reservar(LocalDate.now().toString(), socio, actividad, estadoReserva);
        em.persist(reserva);

        sesion.setPlazasLibres(sesion.getPlazasLibres() - 1);
        em.merge(sesion);

        em.getTransaction().commit();

        return "Reserva confirmada. ID: " + reserva.getIdentificador();
    }
}
