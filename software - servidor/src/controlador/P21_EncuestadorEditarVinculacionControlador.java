package controlador;

import java.sql.Connection;
import modelo.P21_EncuestadorEditarVinculacionDAO;
import vista.P21_EncuestadorEditarVinculacion1;

public class P21_EncuestadorEditarVinculacionControlador {

    private final P21_EncuestadorEditarVinculacion1 vista;
    private final P21_EncuestadorEditarVinculacionDAO dao;

    public P21_EncuestadorEditarVinculacionControlador(
            P21_EncuestadorEditarVinculacion1 vista,
            Connection conexionUsuario) {

        this.vista = vista;
        this.dao = new P21_EncuestadorEditarVinculacionDAO(
                conexionUsuario
        );
    }

    public void iniciar() {
        // La conexión se recibe y se entrega al DAO.
    }
}