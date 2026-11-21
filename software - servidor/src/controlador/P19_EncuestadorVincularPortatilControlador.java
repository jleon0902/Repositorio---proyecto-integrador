
package controlador;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import modelo.P19_EncuestadorVincularPortatilDAO;
import vista.P19_EncuestadorVincularPortatil1;

public class P19_EncuestadorVincularPortatilControlador {

    private final P19_EncuestadorVincularPortatil1 vista;
    private final P19_EncuestadorVincularPortatilDAO dao;

    public P19_EncuestadorVincularPortatilControlador(
            P19_EncuestadorVincularPortatil1 vista,
            Connection conexionUsuario) {

        this.vista = vista;

        this.dao = new P19_EncuestadorVincularPortatilDAO(
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
