
package modelo;

public class ResultadoLogin {

    private int idRol;
    private String nombreRol;
    private String cedula;
    private String nombre;
    private String apellido;

    public ResultadoLogin(
            int idRol,
            String nombreRol,
            String cedula,
            String nombre,
            String apellido) {

        this.idRol = idRol;
        this.nombreRol = nombreRol;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public int getIdRol() {
        return idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }
}
