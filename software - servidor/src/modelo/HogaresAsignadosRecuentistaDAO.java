
package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class HogaresAsignadosRecuentistaDAO {

    public List<Object[]> listarHogaresAsignados(
            String cedulaRecuentista
    ) throws SQLException {

        List<Object[]> hogares = new ArrayList<>();

        // Utilizar la misma conexión de la sesión actual
        Connection con = SesionUsuario.obtenerConexion();

        // Invocar el procedimiento almacenado de Oracle
        String procedimiento =
                "{CALL SP_P14_LISTAR_HOGARES(?, ?)}";

        try (CallableStatement cs = con.prepareCall(procedimiento)) {

            // Enviar la cédula del recuentista
            cs.setString(1, cedulaRecuentista);

            // Registrar el cursor de salida
            cs.registerOutParameter(2, OracleTypes.CURSOR);

            // Ejecutar el procedimiento
            cs.execute();

            // Recibir los resultados
            try (ResultSet rs = (ResultSet) cs.getObject(2)) {

                while (rs.next()) {

                    Object[] fila = {

                        rs.getInt("NUMERO"),

                        rs.getInt("NUMERO_VIVIENDA"),

                        rs.getString("DIRECCION"),

                        rs.getString("NOMBRE_ENCUESTADOR"),

                        rs.getString("CEDULA")
                    };

                    hogares.add(fila);
                }
            }
        }

        return hogares;
    }
    
    
    public String eliminarAsignacion(String cedulaRecuentista, int numeroVivienda) throws SQLException {

    Connection con = SesionUsuario.obtenerConexion();

    String procedimiento = "{CALL SP_P14_ELIMINAR_ASIGNACION(?, ?, ?)}";

    try (CallableStatement cs = con.prepareCall(procedimiento)) {

        cs.setString(1, cedulaRecuentista);
        cs.setInt(2, numeroVivienda);
        cs.registerOutParameter(3, java.sql.Types.VARCHAR);

        cs.execute();

        return cs.getString(3);
    }
}
}
