package controlador;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import controlador.P28CriticoInformesControlador;
import modelo.P23CriticoDAO;
import vista.P23_CriticoMenu1;
import vista.P24_CriticoHogaresEncuestados1;
import vista.P28_CriticoGenerarInformes1;

public class P23CriticoMenuControlador {

    private final P23_CriticoMenu1 vista;
    private final P23CriticoDAO dao;
    private final Connection conexion;

    public P23CriticoMenuControlador(
            P23_CriticoMenu1 vista,
            Connection conexionUsuario) throws SQLException {

        this.vista = vista;
        this.conexion = conexionUsuario;

        this.dao = new P23CriticoDAO(conexionUsuario);
    }

    public void mostrarHogaresEncuestados() {

    try {

        P24_CriticoHogaresEncuestados1 pantalla =
                new P24_CriticoHogaresEncuestados1(conexion);

        vista.mostrarPanel(pantalla.getPanelHogares());

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                vista,
                "Error al mostrar hogares: " + e.getMessage()
        );
    }
}

public void mostrarInformes() {

    try {

        P28_CriticoGenerarInformes1 pantalla =
                new P28_CriticoGenerarInformes1();

        P28CriticoInformesControlador controladorInformes =
                new P28CriticoInformesControlador(
                        pantalla,
                        conexion
                );

        pantalla.setControlador(controladorInformes);

        vista.mostrarPanel(pantalla.getPanelInformes());

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                vista,
                "Error al mostrar informes: " + e.getMessage()
        );
    }
}

}