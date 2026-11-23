
package controlador;

import java.util.List;
import javax.swing.JOptionPane;

import modelo.P20_EncuestadorPortatilesAsignadosDAO;
import vista.P20_EncuestadorPortatilesAsignados1;

public class P20_EncuestadorPortatilesAsignadosControlador {

    private final P20_EncuestadorPortatilesAsignados1 vista;
    private final P20_EncuestadorPortatilesAsignadosDAO dao;

    private String cedulaEncuestador;

    public P20_EncuestadorPortatilesAsignadosControlador(
            P20_EncuestadorPortatilesAsignados1 vista,
            P20_EncuestadorPortatilesAsignadosDAO dao) {

        this.vista = vista;
        this.dao = dao;
    }

    // RECIBIR CÉDULA DEL ENCUESTADOR
    public void setCedulaEncuestador(String cedulaEncuestador) {

        this.cedulaEncuestador = cedulaEncuestador;
    }

    // INICIAR CONTROLADOR
    public void iniciar() {

        cargarPortatilesAsignados();
    }

    // CARGAR PORTÁTILES ASIGNADOS
    public void cargarPortatilesAsignados() {

        if (cedulaEncuestador == null
                || cedulaEncuestador.trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    vista,
                    "No se recibió la cédula del encuestador.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            List<Object[]> datos =
                    dao.obtenerPortatilesAsignados(cedulaEncuestador);

            vista.mostrarPortatilesAsignados(datos);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "No fue posible cargar los portátiles asignados: "
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
