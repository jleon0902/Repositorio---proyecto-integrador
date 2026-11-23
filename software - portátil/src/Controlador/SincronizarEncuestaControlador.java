/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controlador;

import modelo.SincronizarEncuestaDAO;
import modelo.DatosSincronizacion;
import vista.P11_Sincronizar_Encuestador;

public class SincronizarEncuestaControlador {

    private P11_Sincronizar_Encuestador vista;
    private SincronizarEncuestaDAO dao;

    public SincronizarEncuestaControlador(
            P11_Sincronizar_Encuestador vista) {

        this.vista = vista;
        this.dao = new SincronizarEncuestaDAO();

        cargarDatos();
    }

    private void cargarDatos() {

        try {

            DatosSincronizacion datos = dao.consultarDatos();

            vista.mostrarDatosSincronizacion(
                datos.getNombreCompleto(),
                datos.getCedula(),
                datos.getDia(),
                datos.isFinalizada()
            );

        } catch (Exception e) {

            vista.mostrarError(
                "Error al consultar los datos de sincronización:\n"
                + e.getMessage()
            );
        }
    }
}
