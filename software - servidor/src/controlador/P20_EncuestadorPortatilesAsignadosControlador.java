
package controlador;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import vista.P21_EncuestadorEditarVinculacion1;

import modelo.P20_EncuestadorPortatilesAsignadosDAO;
import vista.P20_EncuestadorPortatilesAsignados1;

public class P20_EncuestadorPortatilesAsignadosControlador {

private final P20_EncuestadorPortatilesAsignados1 vista;
private final P20_EncuestadorPortatilesAsignadosDAO dao;
private final Connection conexionUsuario;

public P20_EncuestadorPortatilesAsignadosControlador(
        P20_EncuestadorPortatilesAsignados1 vista,
        Connection conexionUsuario) {

    this.vista = vista;
    this.conexionUsuario = conexionUsuario;

    this.dao = new P20_EncuestadorPortatilesAsignadosDAO(
            conexionUsuario
    );
}

    // INICIAR CONTROLADOR
    public void iniciar() {

        try {

            dao.verificarConexion();

            System.out.println(
                    "Pantalla 20: conexión con Oracle recibida correctamente."
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
    public void abrirPantalla21() {

    P21_EncuestadorEditarVinculacion1 pantalla21 =
            new P21_EncuestadorEditarVinculacion1();

    P21_EncuestadorEditarVinculacionControlador controlador21 =
            new P21_EncuestadorEditarVinculacionControlador(
                    pantalla21,
                    conexionUsuario
            );

    controlador21.iniciar();

    pantalla21.setLocationRelativeTo(vista);
    pantalla21.setVisible(true);
}
    
    public void editarPortatil() {
    abrirPantalla21();
}
    
}
