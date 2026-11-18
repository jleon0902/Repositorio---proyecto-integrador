
package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

public class HogarDAO {

    public String obtenerEstadoEncuesta(
            int numeroVivienda,
            String cedulaRecuentista
    ) throws SQLException {

        String estado;

        Connection cn = conexion.conectarPorRol("RECUENTISTA");

        try {
            CallableStatement cs = cn.prepareCall(
                "{call ENCUESTA_CUADERNILLO_ENPH.SP_ESTADO_ENCUESTA_HOGAR(?, ?, ?)}"
            );

            cs.setInt(1, numeroVivienda);
            cs.setString(2, cedulaRecuentista);
            cs.registerOutParameter(3, Types.VARCHAR);

            cs.execute();

            estado = cs.getString(3);

            cs.close();

        } finally {
            cn.close();
        }

        return estado;
    }
}
