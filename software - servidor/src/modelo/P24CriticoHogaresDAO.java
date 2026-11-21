package modelo;

import java.sql.Connection;
import java.sql.SQLException;

public class P24CriticoHogaresDAO {

    private final Connection conexion;

    public P24CriticoHogaresDAO(Connection conexion)
            throws SQLException {

        if (conexion == null || conexion.isClosed()) {
            throw new SQLException(
                    "No existe una conexión activa con Oracle."
            );
        }

        this.conexion = conexion;
    }
}