
package modelo;

public class GastoDiarioTemporal {

    private OpcionCombo producto;
    private Double cantidadAdquirida;
    private OpcionCombo unidadMedida;
    private OpcionCombo formaAdquisicion;
    private OpcionCombo tipoMedida;
    private OpcionCombo unidadEquivalencia;
    private Double cantidadEquivalencia; 
    private OpcionCombo lugarCompra;
    private Double valor;
    private OpcionCombo frecuenciaCompra;
    private String tipoGasto;

    public GastoDiarioTemporal(
            OpcionCombo producto,
            Double cantidadAdquirida,
            OpcionCombo unidadMedida,
            OpcionCombo formaAdquisicion,
            OpcionCombo tipoMedida,
            OpcionCombo unidadEquivalencia,
            Double cantidadEquivalencia,
            OpcionCombo lugarCompra,
            Double valor,
            OpcionCombo frecuenciaCompra,
            String tipoGasto) {

        this.producto = producto;
        this.cantidadAdquirida = cantidadAdquirida;
        this.unidadMedida = unidadMedida;
        this.formaAdquisicion = formaAdquisicion;
        this.tipoMedida = tipoMedida;
        this.unidadEquivalencia = unidadEquivalencia;
        this.cantidadEquivalencia = cantidadEquivalencia;
        this.lugarCompra = lugarCompra;
        this.valor = valor;
        this.frecuenciaCompra = frecuenciaCompra;
        this.tipoGasto = tipoGasto;
    }

    public OpcionCombo getProducto() {
        return producto;
    }

    public Double getCantidadAdquirida() {
        return cantidadAdquirida;
    }

    public OpcionCombo getUnidadMedida() {
        return unidadMedida;
    }

    public OpcionCombo getFormaAdquisicion() {
        return formaAdquisicion;
    }

    public OpcionCombo getTipoMedida() {
        return tipoMedida;
    }

    public OpcionCombo getUnidadEquivalencia() {
        return unidadEquivalencia;
    }

    public Double getCantidadEquivalencia() {
        return cantidadEquivalencia;
    }

    public OpcionCombo getLugarCompra() {
        return lugarCompra;
    }

    public Double getValor() {
        return valor;
    }

    public OpcionCombo getFrecuenciaCompra() {
        return frecuenciaCompra;
    }

    public String getTipoGasto() {
        return tipoGasto;
    }
}
