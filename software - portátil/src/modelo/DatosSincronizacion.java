/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package modelo;

public class DatosSincronizacion {

    private String cedula;
    private String nombreCompleto;
    private int dia;
    private boolean finalizada;

    public DatosSincronizacion(
            String cedula,
            String nombreCompleto,
            int dia,
            boolean finalizada) {

        this.cedula = cedula;
        this.nombreCompleto = nombreCompleto;
        this.dia = dia;
        this.finalizada = finalizada;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public int getDia() {
        return dia;
    }

    public boolean isFinalizada() {
        return finalizada;
    }
}
