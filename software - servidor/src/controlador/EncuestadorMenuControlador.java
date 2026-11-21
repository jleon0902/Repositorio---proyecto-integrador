
package controlador;

import vista.P16_EncuestadorMenu1;
import modelo.SesionUsuario;

import javax.swing.JOptionPane;

public class EncuestadorMenuControlador {

    private final P16_EncuestadorMenu1 vista;

    public EncuestadorMenuControlador(P16_EncuestadorMenu1 vista) {
        this.vista = vista;
    }

public void cerrarSesion() {

    int respuesta = JOptionPane.showConfirmDialog(
            vista,
            "¿Está seguro de que desea cerrar sesión?",
            "Cerrar sesión",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
    );

    if (respuesta == JOptionPane.YES_OPTION) {

        SesionUsuario.cerrar();

        vista.dispose();

        vista.abrirInicioSesion();
    }
}
}
