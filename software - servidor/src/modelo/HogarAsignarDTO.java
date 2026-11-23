
package modelo;

public class HogarAsignarDTO {

    private long numeroVivienda;
    private String direccion;
    private String responsable;
    private String telefono;

    public HogarAsignarDTO(long numeroVivienda, String direccion,
            String responsable, String telefono) {

        this.numeroVivienda = numeroVivienda;
        this.direccion = direccion;
        this.responsable = responsable;
        this.telefono = telefono;
    }

    public long getNumeroVivienda() {
        return numeroVivienda;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getResponsable() {
        return responsable;
    }

    public String getTelefono() {
        return telefono;
    }
}
