
package modelo;

public class P13_EncuestadorDTO {

    private String cedula;
    private String nombre;
    private String apellido;
    private int hogaresAsignados;

    public P13_EncuestadorDTO(
            String cedula,
            String nombre,
            String apellido,
            int hogaresAsignados) {

        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.hogaresAsignados = hogaresAsignados;
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

    public int getHogaresAsignados() {
        return hogaresAsignados;
    }

    public void setHogaresAsignados(int hogaresAsignados) {
        this.hogaresAsignados = hogaresAsignados;
    }
}
