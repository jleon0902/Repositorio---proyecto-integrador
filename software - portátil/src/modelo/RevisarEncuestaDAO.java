/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.sql.Types;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

public class RevisarEncuestaDAO {

    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public DatosEncuesta consultarDatosEncuesta() throws Exception {

        String cedula;
        String nombre;
        String apellido;
        String telefono;
        String correo;

        Date fechaInicio;
        Date fechaFin;
        String estado;

        try (Connection con = conexion.conectar()) {

            if (con == null) {
                throw new Exception("No fue posible conectar con Oracle.");
            }

            // 1. Obtener la cédula del representante desde ENCUESTA.

            String sqlCedula =
                    "SELECT CEDULA_REPRESENTANTE " +
                    "FROM PORTATIL_ENPH.ENCUESTA";

            try (PreparedStatement ps =
                    con.prepareStatement(sqlCedula);
                 ResultSet rs = ps.executeQuery()) {

                if (!rs.next()) {
                    throw new Exception(
                            "No se encontró ninguna encuesta."
                    );
                }

                cedula = rs.getString("CEDULA_REPRESENTANTE");

                if (rs.next()) {
                    throw new Exception(
                            "Se encontraron varias encuestas. " +
                            "No se puede determinar cuál mostrar."
                    );
                }
            }

            // 2. Consultar los datos personales del representante.

            String sqlRepresentante =
                    "SELECT NOMBRE, APELLIDO, TELEFONO, CORREO " +
                    "FROM REPRESENTANTE " +
                    "WHERE CEDULA = ?";

            try (PreparedStatement ps =
                    con.prepareStatement(sqlRepresentante)) {

                ps.setString(1, cedula);

                try (ResultSet rs = ps.executeQuery()) {

                    if (!rs.next()) {
                        throw new Exception(
                                "No se encontró el representante."
                        );
                    }

                    nombre = rs.getString("NOMBRE");
                    apellido = rs.getString("APELLIDO");
                    telefono = rs.getString("TELEFONO");
                    correo = rs.getString("CORREO");
                }
            }

            // 3. Ejecutar el procedimiento almacenado
            // creado en PORTATIL_ENPH.

            String llamada =
                    "{call PORTATIL_ENPH.SP_CONSULTAR_DATOS_ENCUESTA(" +
                    "?, ?, ?, ?)}";

            try (CallableStatement cs =
                    con.prepareCall(llamada)) {

                cs.setString(1, cedula);

                cs.registerOutParameter(2, Types.DATE);
                cs.registerOutParameter(3, Types.DATE);
                cs.registerOutParameter(4, Types.VARCHAR);

                cs.execute();

                fechaInicio = cs.getDate(2);
                fechaFin = cs.getDate(3);
                estado = cs.getString(4);
            }

            if (fechaInicio == null || fechaFin == null) {
                throw new Exception(
                        "La encuesta no tiene fechas registradas."
                );
            }

            // 4. Calcular la fecha de entrega del portátil.
            // Es el día siguiente a FECHA_FIN.

            LocalDate inicio = fechaInicio.toLocalDate();
            LocalDate fin = fechaFin.toLocalDate();

            LocalDate fechaEntrega = fin.plusDays(1);

            // 5. Calcular el día de seguimiento.
            // La fecha de inicio cuenta como día 1.

            LocalDate hoy = LocalDate.now();

            long diasTranscurridos =
                    ChronoUnit.DAYS.between(inicio, hoy) + 1;

            int dia;

            if (diasTranscurridos < 1) {
                dia = 0;
            } else {
                dia = (int) Math.min(diasTranscurridos, 14);
            }

            // 6. Construir el objeto con todos los datos.

            return new DatosEncuesta(
                    nombre + " " + apellido,
                    cedula,
                    telefono,
                    correo,
                    inicio.format(FORMATO),
                    fin.format(FORMATO),
                    fechaEntrega.format(FORMATO),
                    estado,
                    dia
            );
        }
    }
}
