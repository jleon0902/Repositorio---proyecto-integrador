
package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class P31CriticoProductosConsumidosDAO {

    private final Connection conexion;

    public P31CriticoProductosConsumidosDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public Connection obtenerConexion() throws SQLException {

        if (conexion == null
                || conexion.isClosed()
                || !conexion.isValid(3)) {

            throw new SQLException(
                "La conexión con Oracle no está activa."
            );
        }

        return conexion;
    }

    public List<Object[]> obtenerProductosMasConsumidos()
            throws SQLException {

        return ejecutarFuncion(
            "{ ? = call CRITICO_DIGITADOR.FN_P31_PRODUCTOS_MAS() }"
        );
    }

    public List<Object[]> obtenerProductosMenosConsumidos()
            throws SQLException {

        return ejecutarFuncion(
            "{ ? = call CRITICO_DIGITADOR.FN_P31_PRODUCTOS_MENOS() }"
        );
    }

    private List<Object[]> ejecutarFuncion(String sql)
            throws SQLException {

        List<Object[]> resultados = new ArrayList<>();

        try (CallableStatement cs =
                obtenerConexion().prepareCall(sql)) {

            cs.registerOutParameter(1, Types.REF_CURSOR);

            cs.execute();

            try (ResultSet rs =
                    (ResultSet) cs.getObject(1)) {

                while (rs.next()) {

                    Object[] fila = {
                        rs.getString("NOMBRE"),
                        rs.getDouble("TOTAL_ADQUIRIDO"),
                        rs.getString("FRECUENCIA")
                    };

                    resultados.add(fila);
                }
            }
        }

        return resultados;
    }
}
