
package modelo;

import java.sql.Connection;
import java.sql.SQLException;

public class P22_EncuestadorSincronizarCapitulosDAO {

    private final Connection conexionUsuario;

    public P22_EncuestadorSincronizarCapitulosDAO(
            Connection conexionUsuario) {

        this.conexionUsuario = conexionUsuario;
    }

    // VERIFICAR CONEXIÓN CON ORACLE
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
