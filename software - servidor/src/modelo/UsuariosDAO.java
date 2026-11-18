
package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class UsuariosDAO {

    public List<Usuario> consultarUsuarios(Connection conexion)
            throws SQLException {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = "{CALL SP_CONSULTAR_USUARIOS(?)}";

        try (CallableStatement cs = conexion.prepareCall(sql)) {

            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                while (rs.next()) {

                    usuarios.add(new Usuario(
                            rs.getString("NUMERO"),
                            rs.getString("CEDULA"),
                            rs.getString("NOMBRE"),
                            rs.getString("APELLIDO"),
                            rs.getString("ROL")
                    ));
                }
            }
        }

        return usuarios;
    }

    // Eliminar usuario mediante el procedimiento almacenado.
    public void eliminarUsuario(
            Connection conexion,
            String cedula) throws SQLException {

        String sql = "{CALL SP_ELIMINAR_USUARIO(?)}";

        try (CallableStatement cs = conexion.prepareCall(sql)) {

            cs.setString(1, cedula);
            cs.execute();
        }
    }
}
