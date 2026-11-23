/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.Types;

public class SincronizarEncuestaDAO {

    public DatosSincronizacion consultarDatos() throws Exception {

        String cedula;
        String nombre;
        String apellido;

        int dia;
        String finalizada;

        try (Connection con = conexion.conectar()) {

            if (con == null) {
                throw new Exception("No fue posible conectar con Oracle.");
            }

            // 1. Consultar los datos del representante.

            String llamadaRepresentante =
                "{call PORTATIL_ENPH.SP_P11_CONSULTAR_REPRESENTANTE(?, ?, ?)}";

            try (CallableStatement cs =
                    con.prepareCall(llamadaRepresentante)) {

                cs.registerOutParameter(1, Types.VARCHAR);
                cs.registerOutParameter(2, Types.VARCHAR);
                cs.registerOutParameter(3, Types.VARCHAR);

                cs.execute();

                cedula = cs.getString(1);
                nombre = cs.getString(2);
                apellido = cs.getString(3);
            }

            if (cedula == null || nombre == null || apellido == null) {
                throw new Exception(
                    "No se encontraron los datos del representante."
                );
            }

            // 2. Consultar el día de la encuesta.

            String llamadaDia =
                "{call PORTATIL_ENPH.SP_P11_CONSULTAR_DIA(?, ?, ?)}";

            try (CallableStatement cs =
                    con.prepareCall(llamadaDia)) {

                cs.setString(1, cedula);

                cs.registerOutParameter(2, Types.NUMERIC);
                cs.registerOutParameter(3, Types.VARCHAR);

                cs.execute();

                dia = cs.getInt(2);
                finalizada = cs.getString(3);
            }

            // 3. Devolver los datos.

            return new DatosSincronizacion(
                cedula,
                nombre + " " + apellido,
                dia,
                "S".equalsIgnoreCase(finalizada)
            );
        }
    }
}
