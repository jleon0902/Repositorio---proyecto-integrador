
package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

public class RegistrarUsuarioDAO {

    public void registrarUsuario(
            Connection conexion,
            String cedula,
            String nombre,
            String apellido,
            String usuario,
            String contrasena,
            String rol,
            Integer idMunicipio
    ) throws SQLException {

        boolean autoCommitAnterior = conexion.getAutoCommit();

        try {

            conexion.setAutoCommit(false);

            try (CallableStatement cs = conexion.prepareCall(
                    "{CALL PR_REGISTRAR_USUARIO_VALIDADO(?, ?, ?, ?, ?, ?, ?)}"
            )) {

                cs.setString(1, cedula);
                cs.setString(2, nombre);
                cs.setString(3, apellido);
                cs.setString(4, usuario);
                cs.setString(5, contrasena);
                cs.setString(6, rol);

                if (idMunicipio == null) {
                    cs.setNull(7, Types.NUMERIC);
                } else {
                    cs.setInt(7, idMunicipio);
                }

                cs.execute();
            }

            conexion.commit();

        } catch (SQLException e) {

            conexion.rollback();
            throw e;

        } finally {

            conexion.setAutoCommit(autoCommitAnterior);

        }
    }
}
