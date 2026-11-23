
package modelo;

public class ResumenGasto {

    private double gastoTotal;
    private double gastoPromedioDia;
    private double promedioProductos;
    private String diaMayor;
    private String diaMenor;
    private double porcentajeHogar;
    private double porcentajePersonal;
    private double promedioHogar;
    private double promedioPersonal;

    public ResumenGasto(
            double gastoTotal,
            double gastoPromedioDia,
            double promedioProductos,
            String diaMayor,
            String diaMenor,
            double porcentajeHogar,
            double porcentajePersonal,
            double promedioHogar,
            double promedioPersonal) {

        this.gastoTotal = gastoTotal;
        this.gastoPromedioDia = gastoPromedioDia;
        this.promedioProductos = promedioProductos;
        this.diaMayor = diaMayor;
        this.diaMenor = diaMenor;
        this.porcentajeHogar = porcentajeHogar;
        this.porcentajePersonal = porcentajePersonal;
        this.promedioHogar = promedioHogar;
        this.promedioPersonal = promedioPersonal;
    }

    public double getGastoTotal() {
        return gastoTotal;
    }

    public double getGastoPromedioDia() {
        return gastoPromedioDia;
    }

    public double getPromedioProductos() {
        return promedioProductos;
    }

    public String getDiaMayor() {
        return diaMayor;
    }

    public String getDiaMenor() {
        return diaMenor;
    }

    public double getPorcentajeHogar() {
        return porcentajeHogar;
    }

    public double getPorcentajePersonal() {
        return porcentajePersonal;
    }

    public double getPromedioHogar() {
        return promedioHogar;
    }

    public double getPromedioPersonal() {
        return promedioPersonal;
    }
}
