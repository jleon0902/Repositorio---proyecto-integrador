
package modelo;

public class PorcentajeAdquisicion {

    private int id;
    private String nombre;
    private int cantidad;
    private double porcentaje;

    public PorcentajeAdquisicion(
            int id,
            String nombre,
            int cantidad,
            double porcentaje) {

        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.porcentaje = porcentaje;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPorcentaje() {
        return porcentaje;
    }
}
