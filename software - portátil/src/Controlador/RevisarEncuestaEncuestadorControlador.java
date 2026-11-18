/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package controlador;

import modelo.RevisarEncuestaDAO;
import modelo.DatosEncuesta;
import vista.P8_RepresentanteEncargado_Encuestador;
import javax.swing.JOptionPane;

public class RevisarEncuestaEncuestadorControlador {

    private P8_RepresentanteEncargado_Encuestador vista;
    private RevisarEncuestaDAO dao;

    public RevisarEncuestaEncuestadorControlador(
            P8_RepresentanteEncargado_Encuestador vista) {

        this.vista = vista;
        this.dao = new RevisarEncuestaDAO();

        cargarDatos();
    }

    public void cargarDatos() {

        try {

            DatosEncuesta datos = dao.consultarDatosEncuesta();

            vista.mostrarDatos(datos);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "Error al consultar la información de la encuesta:\n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
