
package modelo;

import java.sql.Connection;
import java.sql.SQLException;

public class P28CriticoInformesDAO {

    private final Connection conexion;

    public P28CriticoInformesDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public boolean verificarConexion() throws SQLException {

        return conexion != null
                && !conexion.isClosed()
                && conexion.isValid(2);
    }

    public Connection obtenerConexion() throws SQLException {

        if (!verificarConexion()) {
            throw new SQLException(
                    "La conexión del crítico digitador no está activa."
            );
        }

        return conexion;
    }
}
