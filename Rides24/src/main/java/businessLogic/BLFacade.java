package businessLogic;

public interface BLFacade {
    String consultarsesiones();

    void registrarUsuarioCompleto(String nombre, String dni, String tarjeta, String cuenta, String correo);

    void registrarEncargado(String nombre, String dni);

    String login(String dni);

    void realizarReserva(String dni);

    void cancelarReserva(String dni);

    void consultarFacturas(String dni);

    void realizarPago(String dni);

    void anadirActividad(String dni);

    void enviarFacturas(String dni);

    void planificarSesiones(String dni);
    
    public void crearSala(String dni);
}