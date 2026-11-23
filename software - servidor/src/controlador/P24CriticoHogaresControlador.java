package controlador;

import java.sql.Connection;
import java.sql.SQLException;
import modelo.P24CriticoHogaresDAO;
import vista.P24_CriticoHogaresEncuestados1;

public class P24CriticoHogaresControlador {

    private final P24_CriticoHogaresEncuestados1 vista;
    private final P24CriticoHogaresDAO dao;

    public P24CriticoHogaresControlador(
            P24_CriticoHogaresEncuestados1 vista,
            Connection conexionUsuario) throws SQLException {

        this.vista = vista;

        this.dao = new P24CriticoHogaresDAO(
                conexionUsuario
        );
    }
}