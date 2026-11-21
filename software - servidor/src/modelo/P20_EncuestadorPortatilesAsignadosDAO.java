
package modelo;

import java.sql.Connection;
import java.sql.SQLException;

public class P20_EncuestadorPortatilesAsignadosDAO {

    private final Connection conexionUsuario;

    public P20_EncuestadorPortatilesAsignadosDAO(
            Connection conexionUsuario) {

        this.conexionUsuario = conexionUsuario;
    }

    // VERIFICAR CONEXIÓN
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
