
package modelo;

public class OpcionCombo {

    private int id;
    private String nombre;
    private Integer idTipoMedida;

    public OpcionCombo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.idTipoMedida = null;
    }

    public OpcionCombo(int id, String nombre, Integer idTipoMedida) {
        this.id = id;
        this.nombre = nombre;
        this.idTipoMedida = idTipoMedida;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getIdTipoMedida() {
        return idTipoMedida;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
