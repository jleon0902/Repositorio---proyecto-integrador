
package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class HogarDAO {

    public List<Object[]> listarHogaresAsignados(
            String cedulaRecuentista
    ) throws SQLException {

        List<Object[]> hogares = new ArrayList<>();

        Connection cn = conexion.conectarPorRol("RECUENTISTA");

        try {

            CallableStatement cs = cn.prepareCall(
                "{call SP_P14_LISTAR_HOGARES(?, ?)}"
            );

            cs.setString(1, cedulaRecuentista);
            cs.registerOutParameter(2, Types.REF_CURSOR);

            cs.execute();

            ResultSet rs = (ResultSet) cs.getObject(2);

            while (rs.next()) {

                Object[] hogar = {
                    rs.getInt("NUMERO"),
                    rs.getInt("NUMERO_VIVIENDA"),
                    rs.getString("DIRECCION"),
                    rs.getString("NOMBRE_ENCUESTADOR"),
                    rs.getString("CEDULA")
                };

                hogares.add(hogar);
            }

            rs.close();
            cs.close();

        } finally {
            cn.close();
        }

        return hogares;
    }


    public String eliminarAsignacion(
            int numeroVivienda,
            String cedulaRecuentista
    ) throws SQLException {

        String mensaje;

        Connection cn = conexion.conectarPorRol("RECUENTISTA");

        try {

            CallableStatement cs = cn.prepareCall(
                "{call SP_P14_ELIMINAR_ASIGNACION(?, ?, ?)}"
            );

            cs.setString(1, cedulaRecuentista);
            cs.setInt(2, numeroVivienda);
            cs.registerOutParameter(3, Types.VARCHAR);

            cs.execute();

            mensaje = cs.getString(3);

            cs.close();

        } finally {
            cn.close();
        }

        return mensaje;
    }
}
