
package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

public class EstadoEncuestaHogarDAO {

    public String consultarEstadoEncuesta(
            int numeroVivienda,
            String cedulaRecuentista
    ) throws SQLException {

        String estado = null;

        try (Connection cn = conexion.conectarPorRol("RECUENTISTA");
             CallableStatement cs = cn.prepareCall(
                     "{call SP_ESTADO_ENCUESTA_HOGAR(?, ?, ?)}")) {

            cs.setInt(1, numeroVivienda);
            cs.setString(2, cedulaRecuentista);
            cs.registerOutParameter(3, Types.VARCHAR);

            cs.execute();

            estado = cs.getString(3);
        }

        return estado;
    }
    
    
}
