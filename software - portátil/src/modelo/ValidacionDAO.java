/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

public class ValidacionDAO {

    public String[] validarInicioSesion(String usuario, String contrasena)
        throws SQLException {

    String[] resultado = null;

    String procedimiento =
            "{call VALIDAR_INICIO_SESION(?, ?, ?, ?, ?, ?)}";

    try (Connection cn = conexion.conectar()) {

        if (cn == null) {
            throw new SQLException(
                    "No se pudo establecer la conexión con PERSONA_PORTATIL."
            );
        }

        try (CallableStatement cs = cn.prepareCall(procedimiento)) {

            // Parámetros de entrada
            cs.setString(1, usuario);
            cs.setString(2, contrasena);

            // Parámetros de salida
            cs.registerOutParameter(3, Types.VARCHAR);
            cs.registerOutParameter(4, Types.VARCHAR);
            cs.registerOutParameter(5, Types.VARCHAR);
            cs.registerOutParameter(6, Types.VARCHAR);

            // Ejecutar procedimiento
            cs.execute();

            String tipoPersona = cs.getString(3);
            String cedula = cs.getString(4);
            String nombre = cs.getString(5);
            String apellido = cs.getString(6);

            // Comprobar credenciales
            if (!"NO_VALIDO".equals(tipoPersona)) {

                resultado = new String[4];

                resultado[0] = tipoPersona;
                resultado[1] = cedula;
                resultado[2] = nombre;
                resultado[3] = apellido;
            }
        }
    }

    return resultado;
}
}
