
package modelo;

public class LugarCompraP30 {

    private int idLugar;
    private String lugar;
    private int cantidad;

    public LugarCompraP30(
            int idLugar,
            String lugar,
            int cantidad) {

        this.idLugar = idLugar;
        this.lugar = lugar;
        this.cantidad = cantidad;
    }

    public int getIdLugar() {
        return idLugar;
    }

    public String getLugar() {
        return lugar;
    }

    public int getCantidad() {
        return cantidad;
    }
}
