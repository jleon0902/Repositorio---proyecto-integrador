
package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class RolesDAO {

    public List<String> obtenerRoles(Connection cn) throws SQLException {

        List<String> roles = new ArrayList<>();

        String procedimiento = "{call CONSULTAR_ROLES(?)}";

        try (
            CallableStatement llamada = cn.prepareCall(procedimiento)
        ) {

            llamada.registerOutParameter(1, Types.REF_CURSOR);

            llamada.execute();

            try (ResultSet rs = (ResultSet) llamada.getObject(1)) {

                while (rs.next()) {

                    roles.add(rs.getString("NOMBRE"));

                }
            }
        }

        return roles;
    }
}
