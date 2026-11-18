
package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

public class LoginDAO {


public ResultadoLogin validarUsuario(
        String usuario,
        String contrasena
) throws SQLException {

    String procedimiento =
            "{call SP_VALIDAR_USUARIO(?,?,?,?,?,?,?,?)}";

    try (
        Connection cn = conexion.conectarAdministrador();

        CallableStatement llamada =
                cn.prepareCall(procedimiento)
    ) {

        // Parámetros de entrada
        llamada.setString(1, usuario);
        llamada.setString(2, contrasena);

        // Parámetros de salida
        llamada.registerOutParameter(3, Types.NUMERIC);
        llamada.registerOutParameter(4, Types.VARCHAR);
        llamada.registerOutParameter(5, Types.VARCHAR);
        llamada.registerOutParameter(6, Types.VARCHAR);
        llamada.registerOutParameter(7, Types.VARCHAR);
        llamada.registerOutParameter(8, Types.NUMERIC);

        llamada.execute();

        int valido = llamada.getInt(8);

        if (valido == 1) {

            int idRol = llamada.getInt(3);
            String nombreRol = llamada.getString(4);
            String cedula = llamada.getString(5);
            String nombre = llamada.getString(6);
            String apellido = llamada.getString(7);

            return new ResultadoLogin(
                    idRol,
                    nombreRol,
                    cedula,
                    nombre,
                    apellido
            );
        }

        return null;
    }
}

}
