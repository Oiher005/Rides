package testOperations;
import javax.persistence.EntityManager;
import configuration.JPAUtil;

public class TestApp {
    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            // Tu lógica aquí, por ejemplo guardar un usuario
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            JPAUtil.close(); // cerrar el factory al final de la aplicación
        }
    }
}