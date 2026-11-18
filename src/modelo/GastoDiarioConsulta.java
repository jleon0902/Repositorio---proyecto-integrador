/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package modelo;

public class GastoDiarioConsulta {

    private int id;
    private int numeroOrden;

    private String nombreProducto;
    private Double cantidadAdquirida;
    private String unidadMedida;
    private String formaAdquisicion;

    private String tipoMedida;
    private String unidadEquivalencia;
    private Double cantidadEquivalencia;

    private String lugarCompra;
    private Double valor;
    private String frecuenciaCompra;
    private String tipoGasto;

    public GastoDiarioConsulta(
            int id,
            int numeroOrden,
            String nombreProducto,
            Double cantidadAdquirida,
            String unidadMedida,
            String formaAdquisicion,
            String tipoMedida,
            String unidadEquivalencia,
            Double cantidadEquivalencia,
            String lugarCompra,
            Double valor,
            String frecuenciaCompra,
            String tipoGasto) {

        this.id = id;
        this.numeroOrden = numeroOrden;
        this.nombreProducto = nombreProducto;
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

    public int getId() {
        return id;
    }

    public int getNumeroOrden() {
        return numeroOrden;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public Double getCantidadAdquirida() {
        return cantidadAdquirida;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public String getFormaAdquisicion() {
        return formaAdquisicion;
    }

    public String getTipoMedida() {
        return tipoMedida;
    }

    public String getUnidadEquivalencia() {
        return unidadEquivalencia;
    }

    public Double getCantidadEquivalencia() {
        return cantidadEquivalencia;
    }

    public String getLugarCompra() {
        return lugarCompra;
    }

    public Double getValor() {
        return valor;
    }

    public String getFrecuenciaCompra() {
        return frecuenciaCompra;
    }

    public String getTipoGasto() {
        return tipoGasto;
    }
}
