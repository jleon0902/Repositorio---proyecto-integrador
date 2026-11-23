package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;

public class P18_EncuestadorVerHogarDAO {

    private final Connection conexionUsuario;

    public P18_EncuestadorVerHogarDAO(
            Connection conexionUsuario) {

        this.conexionUsuario = conexionUsuario;
    }

    public void verificarConexion() throws SQLException {

        if (conexionUsuario == null
                || conexionUsuario.isClosed()
                || !conexionUsuario.isValid(3)) {

            throw new SQLException(
                    "No existe una conexión activa con Oracle."
            );
        }
    }
}