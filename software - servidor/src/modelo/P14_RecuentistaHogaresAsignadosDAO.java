
package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.Types;
import java.sql.SQLException;

public class P14_RecuentistaHogaresAsignadosDAO {


public String eliminarAsignacion(
        String cedulaRecuentista,
        int numeroVivienda) {

    String mensaje = "";

    try {

        Connection conn = SesionUsuario.obtenerConexion();

        String sql = "{CALL SP_P14_ELIMINAR_ASIGNACION(?, ?, ?)}";

        try (CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, cedulaRecuentista);
            cs.setInt(2, numeroVivienda);

            cs.registerOutParameter(3, Types.VARCHAR);

            cs.execute();

            mensaje = cs.getString(3);

            if ("ASIGNACION_ELIMINADA".equals(mensaje)) {

                conn.commit();

            } else {

                conn.rollback();
            }
        }

    } catch (SQLException e) {

        mensaje = "Error al eliminar la asignación: " + e.getMessage();

        e.printStackTrace();
    }

    return mensaje;
}


}
