
package modelo;

public class P25HogarItem {

    private int numeroVivienda;
    private String responsableHogar;
    private String fechaFin;

    public P25HogarItem(
            int numeroVivienda,
            String responsableHogar,
            String fechaFin) {

        this.numeroVivienda = numeroVivienda;
        this.responsableHogar = responsableHogar;
        this.fechaFin = fechaFin;
    }

    public int getNumeroVivienda() {
        return numeroVivienda;
    }

    public String getResponsableHogar() {
        return responsableHogar;
    }

    public String getFechaFin() {
        return fechaFin;
    }
}
