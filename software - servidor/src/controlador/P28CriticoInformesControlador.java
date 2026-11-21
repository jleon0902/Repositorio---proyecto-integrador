
package controlador;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.P30CriticoAdquisicionDAO;
import modelo.P28CriticoInformesDAO;
import modelo.P29CriticoResumenGastoDAO;
import vista.P30_CriticoAdquisicion1;
import vista.P28_CriticoGenerarInformes1;
import vista.P29_CriticoResumenGasto;
import modelo.P31CriticoProductosConsumidosDAO;
import vista.P31_CriticoProductosConsumidos1;
public class P28CriticoInformesControlador {

    private final P28_CriticoGenerarInformes1 vista;
    private final P28CriticoInformesDAO dao;
    private final Connection conexion;

    public P28CriticoInformesControlador(
            P28_CriticoGenerarInformes1 vista,
            Connection conexionUsuario) throws SQLException {

        this.vista = vista;
        this.conexion = conexionUsuario;

        this.dao = new P28CriticoInformesDAO(conexionUsuario);
    }

    public Connection obtenerConexion() {
        return conexion;
    }

public void abrirResumenGasto() {

    try {
        
        if (conexion == null || conexion.isClosed()) {

    JOptionPane.showMessageDialog(
            vista,
            "No se puede acceder a esta pantalla porque "
            + "no existe una conexión activa con la base de datos. "
            + "Por favor, inicie sesión nuevamente.",
            "Conexión no disponible",
            JOptionPane.WARNING_MESSAGE
    );

    return;
}

        P29_CriticoResumenGasto pantalla =
                new P29_CriticoResumenGasto();

        P29CriticoResumenGastoDAO daoResumen =
                new P29CriticoResumenGastoDAO(conexion);

        P29CriticoResumenGastoControlador controladorResumen =
                new P29CriticoResumenGastoControlador(
                        pantalla,
                        daoResumen
                );

pantalla.setControlador(controladorResumen);

pantalla.setLocationRelativeTo(null);
pantalla.setVisible(true);

pantalla.iniciarInforme();

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                vista.getPanelInformes(),
                "Error al abrir el resumen del gasto: "
                + e.getMessage()
        );
    }
}


public void abrirProductosConsumidos() {

    try {

        if (conexion == null || conexion.isClosed()
                || !conexion.isValid(3)) {

            JOptionPane.showMessageDialog(
                    vista,
                    "No existe una conexión activa con Oracle.",
                    "Conexión no disponible",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        P31_CriticoProductosConsumidos1 pantalla =
                new P31_CriticoProductosConsumidos1();

        P31CriticoProductosConsumidosDAO daoP31 =
                new P31CriticoProductosConsumidosDAO(conexion);

        P31CriticoProductosConsumidosControlador controladorP31 =
                new P31CriticoProductosConsumidosControlador(
                        pantalla,
                        daoP31
                );

        pantalla.setControlador(controladorP31);

        controladorP31.cargarProductosConsumidos();

        pantalla.setLocationRelativeTo(vista);
        pantalla.setVisible(true);

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                vista,
                "Error al abrir productos consumidos:\n"
                + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}

public void abrirAdquisicion() {

    try {

        if (conexion == null || conexion.isClosed()
                || !conexion.isValid(3)) {

            JOptionPane.showMessageDialog(
                    vista,
                    "No existe una conexión activa con Oracle.",
                    "Conexión no disponible",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        P30_CriticoAdquisicion1 pantalla =
                new P30_CriticoAdquisicion1();

        P30CriticoAdquisicionDAO daoP30 =
                new P30CriticoAdquisicionDAO(conexion);

        P30CriticoAdquisicionControlador controladorP30 =
                new P30CriticoAdquisicionControlador(
                        pantalla,
                        daoP30
                );

        pantalla.setControlador(controladorP30);

        pantalla.setLocationRelativeTo(vista);
        pantalla.setVisible(true);

        pantalla.cargarDatos();

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                vista,
                "Error al abrir el informe de adquisición: "
                + e.getMessage()
        );
    }
}
    public void cerrarSesion() {

        int respuesta = JOptionPane.showConfirmDialog(
                vista.getPanelInformes(),
                "¿Está seguro de que desea cerrar sesión?",
                "Confirmar cierre de sesión",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            vista.mostrarLogin();
            vista.cerrarVentana();
        }
    }
}
