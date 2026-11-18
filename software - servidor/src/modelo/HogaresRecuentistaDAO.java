
package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class HogaresRecuentistaDAO {

    public List<Object[]> consultarHogares(String cedulaRecuentista)
            throws SQLException {

        List<Object[]> hogares = new ArrayList<>();

        String llamada = "{CALL SP_VER_HOGARES_REC(?, ?)}";

        Connection conexion = SesionUsuario.obtenerConexion();

        try (CallableStatement cs = conexion.prepareCall(llamada)) {

            cs.setString(1, cedulaRecuentista);
            cs.registerOutParameter(2, Types.REF_CURSOR);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(2)) {

                while (rs.next()) {

                    Object[] hogar = {
                        rs.getObject("NUMERO_VIVIENDA"),
                        rs.getString("DIRECCION"),
                        rs.getString("NOMBRE") + " "
                            + rs.getString("APELLIDO"),
                        rs.getString("TELEFONO")
                    };

                    hogares.add(hogar);
                }
            }
        }

        return hogares;
    }
}
