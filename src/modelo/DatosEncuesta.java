
package modelo;

public class DatosEncuesta {

    private String nombreCompleto;
    private String cedula;
    private String telefono;
    private String correo;
    private String fechaInicio;
    private String fechaFin;
    private String fechaEntregaPortatil;
    private String estado;
    private int dia;

    public DatosEncuesta(
            String nombreCompleto,
            String cedula,
            String telefono,
            String correo,
            String fechaInicio,
            String fechaFin,
            String fechaEntregaPortatil,
            String estado,
            int dia) {

        this.nombreCompleto = nombreCompleto;
        this.cedula = cedula;
        this.telefono = telefono;
        this.correo = correo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaEntregaPortatil = fechaEntregaPortatil;
        this.estado = estado;
        this.dia = dia;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getCedula() {
        return cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public String getFechaEntregaPortatil() {
        return fechaEntregaPortatil;
    }

    public String getEstado() {
        return estado;
    }

    public int getDia() {
        return dia;
    }
}
