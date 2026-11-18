
package controlador;

import modelo.SesionUsuario;

import vista.P1_AdministradorMenu;
import vista.P2_AdministradorRegistrarUsuarios;
import vista.P3_AdministradorUsuarios;
import vista.P5_AdministradorRegistrarPortatiles1;
import vista.P6_AdministradorPortatiles1;
import vista.iniciarSesion;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JScrollBar;

public class P1_AdministradorMenuControlador {

    private final P1_AdministradorMenu vista;
    private final Connection conexionUsuario;

    private P2_AdministradorRegistrarUsuarios pantallaRegistro;
    private P3_AdministradorUsuarios pantallaUsuarios;
    private P5_AdministradorRegistrarPortatiles1 pantallaPortatiles;
    private P6_AdministradorPortatiles1 pantallaListaPortatiles;

    public P1_AdministradorMenuControlador(
            P1_AdministradorMenu vista,
            Connection conexionUsuario) {

        this.vista = vista;
        this.conexionUsuario = conexionUsuario;
    }

    // Registrar usuarios
    public void mostrarRegistrarUsuarios() {

        if (!conexionDisponible()) {
            return;
        }

        pantallaRegistro =
                new P2_AdministradorRegistrarUsuarios();

        pantallaRegistro.obtenerControlador()
                .establecerConexion(conexionUsuario);

        JPanel contenido =
                pantallaRegistro.obtenerPanelRegistro();

        JScrollBar barra =
                pantallaRegistro.obtenerScrollBar();

        vista.mostrarContenido(contenido, barra);

        pantallaRegistro.dispose();
    }

    // Consultar usuarios
    public void mostrarUsuarios() {

        if (!conexionDisponible()) {
            return;
        }

        if (pantallaUsuarios == null) {

            pantallaUsuarios =
                    new P3_AdministradorUsuarios(conexionUsuario);
        }

        pantallaUsuarios.actualizarTablaUsuarios();

        JPanel contenido =
                pantallaUsuarios.obtenerPanelUsuarios();

        vista.mostrarContenido(contenido);
    }

    // Registrar portátiles
    public void mostrarRegistrarPortatiles() {

        if (!conexionDisponible()) {
            return;
        }

        pantallaPortatiles =
                new P5_AdministradorRegistrarPortatiles1();

        pantallaPortatiles.obtenerControlador()
                .establecerConexion(conexionUsuario);

        JPanel contenido =
                pantallaPortatiles.obtenerPanelRegistrarPortatiles();

        vista.mostrarContenido(contenido);

        pantallaPortatiles.dispose();
    }

    // Consultar portátiles
public void mostrarPortatiles() {

    if (!conexionDisponible()) {
        return;
    }

    if (pantallaListaPortatiles == null) {

        pantallaListaPortatiles =
                new P6_AdministradorPortatiles1();
    }

    pantallaListaPortatiles.cargarPortatiles();

    JPanel contenido =
            pantallaListaPortatiles.obtenerPanelPortatiles();

    vista.mostrarContenido(contenido);
}

    // Verificar conexión
    private boolean conexionDisponible() {

        try {

            if (conexionUsuario == null
                    || conexionUsuario.isClosed()) {

                vista.mostrarContenido(new JPanel());

                javax.swing.JOptionPane.showMessageDialog(
                        vista,
                        "La conexión con Oracle no está disponible.",
                        "Error de conexión",
                        javax.swing.JOptionPane.ERROR_MESSAGE
                );

                return false;
            }

            return true;

        } catch (SQLException e) {

            javax.swing.JOptionPane.showMessageDialog(
                    vista,
                    "Error al verificar la conexión: "
                    + e.getMessage(),
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }

    // Cerrar sesión
    public void cerrarSesion() {

        if (!vista.confirmarCierreSesion()) {
            return;
        }

        SesionUsuario.cerrar();

        iniciarSesion pantallaLogin =
                new iniciarSesion();

        pantallaLogin.setLocationRelativeTo(null);
        pantallaLogin.setVisible(true);

        vista.cerrarVentana();
    }
}
