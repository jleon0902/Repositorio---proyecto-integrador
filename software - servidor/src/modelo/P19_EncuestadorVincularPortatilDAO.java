
package modelo;

import java.sql.Connection;
import java.sql.SQLException;

public class P19_EncuestadorVincularPortatilDAO {

    private final Connection conexionUsuario;

    public P19_EncuestadorVincularPortatilDAO(
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
