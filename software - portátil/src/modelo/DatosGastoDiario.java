
package modelo;

public class DatosGastoDiario {

    private int id;
    private int numeroOrden;
    private String producto;
    private double cantidadAdquirida;
    private String unidadMedida;
    private double cantidadEquivalencia;
    private String tipoMedida;
    private String unidadEquivalencia;
    private String formaAdquisicion;
    private String lugarCompra;
    private double valor;
    private String frecuenciaCompra;
    private String tipoGasto;

    public DatosGastoDiario(
            int id,
            int numeroOrden,
            String producto,
            double cantidadAdquirida,
            String unidadMedida,
            double cantidadEquivalencia,
            String tipoMedida,
            String unidadEquivalencia,
            String formaAdquisicion,
            String lugarCompra,
            double valor,
            String frecuenciaCompra,
            String tipoGasto) {

        this.id = id;
        this.numeroOrden = numeroOrden;
        this.producto = producto;
        this.cantidadAdquirida = cantidadAdquirida;
        this.unidadMedida = unidadMedida;
        this.cantidadEquivalencia = cantidadEquivalencia;
        this.tipoMedida = tipoMedida;
        this.unidadEquivalencia = unidadEquivalencia;
        this.formaAdquisicion = formaAdquisicion;
        this.lugarCompra = lugarCompra;
        this.valor = valor;
        this.frecuenciaCompra = frecuenciaCompra;
        this.tipoGasto = tipoGasto;
    }

    public int getId() {
        return id;
    }

    public int getNumeroOrden() {
        return numeroOrden;
    }

    public String getProducto() {
        return producto;
    }

    public double getCantidadAdquirida() {
        return cantidadAdquirida;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public double getCantidadEquivalencia() {
        return cantidadEquivalencia;
    }

    public String getTipoMedida() {
        return tipoMedida;
    }

    public String getUnidadEquivalencia() {
        return unidadEquivalencia;
    }

    public String getFormaAdquisicion() {
        return formaAdquisicion;
    }

    public String getLugarCompra() {
        return lugarCompra;
    }

    public double getValor() {
        return valor;
    }

    public String getFrecuenciaCompra() {
        return frecuenciaCompra;
    }

    public String getTipoGasto() {
        return tipoGasto;
    }
}
