
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RepresentanteDAO {

    public String[] validarRepresentante(String usuario, String contrasena)
        throws SQLException {

    String sql = "SELECT CEDULA, NOMBRE FROM REPRESENTANTE "
               + "WHERE USUARIO = ? AND CONTRASENA = ?";

    try (Connection conexionBD = conexion.conectar()) {

        if (conexionBD == null) {
            throw new SQLException("No fue posible conectar con Oracle.");
        }

        try (PreparedStatement sentencia =
                conexionBD.prepareStatement(sql)) {

            sentencia.setString(1, usuario);
            sentencia.setString(2, contrasena);

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return new String[] {
                        resultado.getString("CEDULA"),
                        resultado.getString("NOMBRE")
                    };
                }
                return null;
            }
        }
    }
}
    
    public int obtenerIdEncuestaPorCedula(String cedula)
        throws SQLException {

    String sql =
        "SELECT ID FROM ("
      + " SELECT ID FROM ENCUESTA "
      + " WHERE CEDULA_REPRESENTANTE = ? "
      + " ORDER BY FECHA_INICIO DESC, ID DESC"
      + ") WHERE ROWNUM = 1";

    try (Connection conexionBD = conexion.conectar()) {

        if (conexionBD == null) {
            throw new SQLException("No fue posible conectar con Oracle.");
        }

        try (PreparedStatement sentencia =
                conexionBD.prepareStatement(sql)) {

            sentencia.setString(1, cedula);

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return resultado.getInt("ID");
                }
                return -1;
            }
        }
    }
}

    public String[] validarEncuestador(String usuario, String contrasena)
            throws SQLException {

        String sql = "SELECT CEDULA, NOMBRE, APELLIDO FROM ENCUESTADOR "
                   + "WHERE USUARIO = ? AND CONTRASENA = ?";

        try (Connection conexionBD = conexion.conectar()) {

            if (conexionBD == null) {
                throw new SQLException("No fue posible conectar con Oracle.");
            }

            try (PreparedStatement sentencia =
                    conexionBD.prepareStatement(sql)) {

                sentencia.setString(1, usuario);
                sentencia.setString(2, contrasena);

                try (ResultSet resultado = sentencia.executeQuery()) {

                    if (resultado.next()) {
                        String cedula = resultado.getString("CEDULA");

                        String nombreCompleto =
                                resultado.getString("NOMBRE") + " "
                              + resultado.getString("APELLIDO");

                        return new String[]{cedula, nombreCompleto};
                    }

                    return null;
                }
            }
        }
    }

    public String validarCriticoDigitador(
            String usuario, String contrasena) throws SQLException {

        String sql = "SELECT NOMBRE, APELLIDO FROM CRITICO_DIGITADOR "
                   + "WHERE USUARIO = ? AND CONTRASENA = ?";

        try (Connection conexionBD = conexion.conectar()) {

            if (conexionBD == null) {
                throw new SQLException("No fue posible conectar con Oracle.");
            }

            try (PreparedStatement sentencia =
                    conexionBD.prepareStatement(sql)) {

                sentencia.setString(1, usuario);
                sentencia.setString(2, contrasena);

                try (ResultSet resultado = sentencia.executeQuery()) {

                    if (resultado.next()) {
                        return resultado.getString("NOMBRE") + " "
                             + resultado.getString("APELLIDO");
                    }

                    return null;
                }
            }
        }
    }

    private String validarUsuario(
            String tabla, String usuario, String contrasena)
            throws SQLException {

        String sql = "SELECT NOMBRE FROM " + tabla
                   + " WHERE USUARIO = ? AND CONTRASENA = ?";

        try (Connection conexionBD = conexion.conectar()) {

            if (conexionBD == null) {
                throw new SQLException("No fue posible conectar con Oracle.");
            }

            try (PreparedStatement sentencia =
                    conexionBD.prepareStatement(sql)) {

                sentencia.setString(1, usuario);
                sentencia.setString(2, contrasena);

                try (ResultSet resultado = sentencia.executeQuery()) {

                    if (resultado.next()) {
                        return resultado.getString("NOMBRE");
                    }

                    return null;
                }
            }
        }
    }
}
