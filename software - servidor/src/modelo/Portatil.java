
package modelo;

public class Portatil {

    private String numeroSerie;
    private String marca;
    private String modelo;

    public Portatil(String numeroSerie, String marca, String modelo) {
        this.numeroSerie = numeroSerie;
        this.marca = marca;
        this.modelo = modelo;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
