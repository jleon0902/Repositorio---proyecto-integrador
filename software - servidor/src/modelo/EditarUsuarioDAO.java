/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import oracle.jdbc.OracleTypes;

public class EditarUsuarioDAO {

    public Usuario consultarUsuario(
            Connection conexion,
            String cedula) throws SQLException {

        String sql = "{CALL SP_CONSULTAR_USUARIO_CEDULA(?, ?)}";

        try (CallableStatement cs = conexion.prepareCall(sql)) {

            cs.setString(1, cedula);

            cs.registerOutParameter(
                    2,
                    OracleTypes.CURSOR
            );

            cs.execute();

            try (ResultSet rs =
                    (ResultSet) cs.getObject(2)) {

                if (rs.next()) {

                    return new Usuario(
                            rs.getString("CEDULA"),
                            rs.getString("NOMBRE"),
                            rs.getString("APELLIDO"),
                            rs.getString("USUARIO"),
                            rs.getString("CONTRASENA"),
                            rs.getString("ROL"),
                            obtenerEntero(rs, "ID_REGION"),
                            obtenerEntero(rs, "ID_DEPARTAMENTO"),
                            obtenerEntero(rs, "ID_MUNICIPIO")
                    );
                }
            }
        }

        return null;
    }

    private Integer obtenerEntero(
            ResultSet rs,
            String columna) throws SQLException {

        int valor = rs.getInt(columna);

        return rs.wasNull() ? null : valor;
    }

    public boolean validarUsuarioDisponible(
            Connection conexion,
            String cedula,
            String usuario) throws SQLException {

        String sql = "{CALL SP_VALIDAR_USUARIO_EDITAR(?, ?, ?)}";

        try (CallableStatement cs = conexion.prepareCall(sql)) {

            cs.setString(1, cedula);
            cs.setString(2, usuario);

            cs.registerOutParameter(3, Types.NUMERIC);

            cs.execute();

            return cs.getInt(3) != 0;
        }
    }

    public void actualizarUsuario(
            Connection conexion,
            String cedulaOriginal,
            String cedula,
            String nombre,
            String apellido,
            String usuario,
            String contrasena,
            String rol,
            Integer idMunicipio) throws SQLException {

        String sql = "{CALL SP_ACTUALIZAR_USUARIO(?, ?, ?, ?, ?, ?, ?, ?)}";

        try (CallableStatement cs = conexion.prepareCall(sql)) {

            cs.setString(1, cedulaOriginal);
            cs.setString(2, cedula);
            cs.setString(3, nombre);
            cs.setString(4, apellido);
            cs.setString(5, usuario);
            cs.setString(6, contrasena);
            cs.setString(7, rol);

            if (idMunicipio == null) {
                cs.setNull(8, Types.NUMERIC);
            } else {
                cs.setInt(8, idMunicipio);
            }

            cs.execute();

            if (!conexion.getAutoCommit()) {
                conexion.commit();
            }
        }
    }

    public String obtenerMensajeError(SQLException e) {

        switch (e.getErrorCode()) {

            case 20008:
                return "La cédula, el usuario y la contraseña ya existen.";

            case 20005:
                return "La cédula y el usuario ya existen.";

            case 20006:
                return "La cédula y la contraseña ya existen.";

            case 20007:
                return "El usuario y la contraseña ya existen.";

            case 20011:
                return "La cédula ya se encuentra registrada.";

            case 20003:
                return "El nombre de usuario ya se encuentra registrado.";

            case 20004:
                return "La contraseña ya se encuentra registrada.";

            case 20012:
                return "No se puede actualizar este encuestador porque tiene viviendas asignadas.";

            case 20010:
                return "Debe seleccionar un municipio.";

            default:
                return "No fue posible actualizar el usuario:\n"
                        + e.getMessage();
        }
    }
}