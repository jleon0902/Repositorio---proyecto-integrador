
package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class P13_EncuestadorDAO {

    public List<P13_EncuestadorDTO> listarEncuestadores()
            throws SQLException {

        List<P13_EncuestadorDTO> encuestadores =
                new ArrayList<>();

        Connection con = SesionUsuario.obtenerConexion();

        String procedimiento =
                "{CALL P13_LISTAR_ENCUESTADORES(?)}";

        try (CallableStatement cs =
                con.prepareCall(procedimiento)) {

            cs.registerOutParameter(1, OracleTypes.CURSOR);

            cs.execute();

            try (ResultSet rs =
                    (ResultSet) cs.getObject(1)) {

                while (rs.next()) {

                    P13_EncuestadorDTO encuestador =
                            new P13_EncuestadorDTO(
                                    rs.getString("CEDULA"),
                                    rs.getString("NOMBRE"),
                                    rs.getString("APELLIDO"),
                                    rs.getInt("HOGARES_ASIGNADOS")
                            );

                    encuestadores.add(encuestador);
                }
            }
        }

        return encuestadores;
    }
}
