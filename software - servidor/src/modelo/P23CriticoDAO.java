package modelo;

import java.sql.Connection;
import java.sql.SQLException;

public class P23CriticoDAO {

    private final Connection conexion;

    public P23CriticoDAO(Connection conexion) throws SQLException {

        if (conexion == null || conexion.isClosed()) {
            throw new SQLException(
                    "No existe una conexión activa con Oracle."
            );
        }

        this.conexion = conexion;
    }
}