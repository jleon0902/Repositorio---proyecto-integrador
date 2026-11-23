
package controlador;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.SesionUsuario;
import modelo.P22_EncuestadorSincronizarCapitulosDAO;
import vista.P22_EncuestadorSincronizarCapitulos1;

public class P22_EncuestadorSincronizarCapitulosControlador {

    private final P22_EncuestadorSincronizarCapitulos1 vista;
    private final P22_EncuestadorSincronizarCapitulosDAO dao;
    private final Connection conexionUsuario;

    public P22_EncuestadorSincronizarCapitulosControlador(
            P22_EncuestadorSincronizarCapitulos1 vista) {

        this.vista = vista;

        try {
            this.conexionUsuario = SesionUsuario.obtenerConexion();

            this.dao = new P22_EncuestadorSincronizarCapitulosDAO(
                    conexionUsuario
            );

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "No se pudo obtener la conexión del encuestador.",
                    e
            );
        }
    }

    // INICIAR CONTROLADOR
    public void iniciar() {

        // El controlador se encarga del evento del botón.
        for (java.awt.event.ActionListener al :
                vista.getBtnSincronizar().getActionListeners()) {

            vista.getBtnSincronizar().removeActionListener(al);
        }

        vista.getBtnSincronizar().addActionListener(
                e -> confirmarSincronizacion()
        );
    }

    // CONFIRMAR SINCRONIZACIÓN
    public void confirmarSincronizacion() {

        int respuesta = JOptionPane.showOptionDialog(
                vista.obtenerPanelSincronizar(),
                "¿Está seguro?\n\n"
                + "Al hacerlo se transferirá la información "
                + "que realizó la familia en el capítulo D: "
                + "Gastos Diarios.",
                "Confirmar sincronización",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Cancelar", "Sincronizar"},
                "Sincronizar"
        );

        if (respuesta != 1) {
            return;
        }

        try {

            dao.verificarConexion();

            JOptionPane.showMessageDialog(
                    vista.obtenerPanelSincronizar(),
                    "La conexión con Oracle está activa. "
                    + "La operación de transferencia está pendiente "
                    + "de implementar."
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    vista.obtenerPanelSincronizar(),
                    "Error al verificar la conexión: "
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
