
package modelo;

import java.sql.Connection;
import java.sql.SQLException;

public class P21_EncuestadorEditarVinculacionDAO {

    private final Connection conexionUsuario;

    public P21_EncuestadorEditarVinculacionDAO(
            Connection conexionUsuario) {

        this.conexionUsuario = conexionUsuario;
    }

    public void verificarConexion() throws SQLException {

        if (conexionUsuario == null || conexionUsuario.isClosed()) {
            throw new SQLException(
                    "No hay una conexión activa con Oracle."
            );
        }
    }
}
