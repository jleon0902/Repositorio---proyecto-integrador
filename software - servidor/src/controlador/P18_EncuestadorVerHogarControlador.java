
package controlador;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import modelo.P18_EncuestadorVerHogarDAO;
import vista.P18_EncuestadorVerHogar1;

public class P18_EncuestadorVerHogarControlador {

    private final P18_EncuestadorVerHogar1 vista;
    private final P18_EncuestadorVerHogarDAO dao;

public P18_EncuestadorVerHogarControlador(
        P18_EncuestadorVerHogar1 vista,
        Connection conexionUsuario) {

    if (conexionUsuario == null) {
        throw new IllegalStateException(
                "No hay una conexión activa con Oracle."
        );
    }

    this.vista = vista;

    this.dao = new P18_EncuestadorVerHogarDAO(
            conexionUsuario
    );
}

    public void iniciar() {

        try {

            dao.verificarConexion();

            JOptionPane.showMessageDialog(
                    vista,
                    "Conexión con Oracle recibida correctamente."
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "Error de conexión: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
