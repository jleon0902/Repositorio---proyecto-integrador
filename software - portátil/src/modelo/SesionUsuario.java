

package modelo;

public class SesionUsuario {

    private static String tipoPersona;
    private static String cedula;
    private static String nombre;
    private static String apellido;

    // Guarda los datos de la sesión
    public static void iniciarSesion(
            String tipo,
            String documento,
            String nombrePersona,
            String apellidoPersona) {

        tipoPersona = tipo;
        cedula = documento;
        nombre = nombrePersona;
        apellido = apellidoPersona;
    }

    public static String getTipoPersona() {
        return tipoPersona;
    }

    public static String getCedula() {
        return cedula;
    }

    public static String getNombre() {
        return nombre;
    }

    public static String getApellido() {
        return apellido;
    }

    // Cierra la sesión
    public static void cerrarSesion() {
        tipoPersona = null;
        cedula = null;
        nombre = null;
        apellido = null;
    }
}