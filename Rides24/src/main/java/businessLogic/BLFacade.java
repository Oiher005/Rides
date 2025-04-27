package businessLogic;

public interface BLFacade {

    void registrarUsuarioCompleto(String nombre, String dni, String tarjeta, String cuenta, String correo) throws Exception;

    void registrarEncargado(String nombre, String dni) throws Exception;

    String login(String dni) throws Exception;

    // Puedes agregar aquí los métodos: realizarReserva, cancelarReserva, etc.
}

