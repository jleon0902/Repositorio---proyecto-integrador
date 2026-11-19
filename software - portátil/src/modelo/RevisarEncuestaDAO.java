
package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.Types;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

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

            // 1. Consultar la cédula del representante mediante
            // el procedimiento almacenado en PORTATIL_ENPH.

            String llamadaCedula =
                    "{call PORTATIL_ENPH.SP_CONSULTAR_CEDULA_ENCUESTA(?)}";

            try (CallableStatement cs =
                    con.prepareCall(llamadaCedula)) {

                cs.registerOutParameter(1, Types.VARCHAR);

                cs.execute();

                cedula = cs.getString(1);
            }

            // 2. Consultar los datos personales del representante
            // mediante el procedimiento almacenado en PORTATIL_ENPH.

            String llamadaRepresentante =
                    "{call PORTATIL_ENPH.SP_CONSULTAR_REPRESENTANTE(?, ?, ?, ?, ?)}";

            try (CallableStatement cs =
                    con.prepareCall(llamadaRepresentante)) {

                cs.setString(1, cedula);

                cs.registerOutParameter(2, Types.VARCHAR);
                cs.registerOutParameter(3, Types.VARCHAR);
                cs.registerOutParameter(4, Types.VARCHAR);
                cs.registerOutParameter(5, Types.VARCHAR);

                cs.execute();

                nombre = cs.getString(2);
                apellido = cs.getString(3);
                telefono = cs.getString(4);
                correo = cs.getString(5);
            }

            // 3. Consultar las fechas y el estado de la encuesta
            // mediante el procedimiento almacenado existente.

            String llamada =
                     "{call PORTATIL_ENPH.SP_CONSULTAR_DATOS_ENCUESTA(?, ?, ?, ?)}";

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
      
public List<DatosGastoDiario> consultarGastosDia(
        String cedula, java.time.LocalDate fecha) throws Exception {

    List<DatosGastoDiario> gastos = new ArrayList<>();

    String llamada =
            "{call PORTATIL_ENPH.SP_LISTAR_GASTOS_DIA(?, ?, ?)}";

    try (Connection con = conexion.conectar()) {

        if (con == null) {
            throw new Exception("No fue posible conectar con Oracle.");
        }

        try (CallableStatement cs = con.prepareCall(llamada)) {

            cs.setString(1, cedula);

            cs.setDate(2, Date.valueOf(fecha));

            cs.registerOutParameter(3, OracleTypes.CURSOR);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(3)) {

                while (rs.next()) {

                    DatosGastoDiario gasto = new DatosGastoDiario(
                            rs.getInt("ID"),
                            rs.getInt("NUMERO_ORDEN"),
                            rs.getString("NOMBRE_PRODUCTO"),
                            rs.getDouble("CANTIDAD_ADQUIRIDA"),
                            rs.getString("NOMBRE_UNIDAD_MEDIDA"),
                            rs.getDouble("CANTIDAD_MEDIDA_EQUI"),
                            rs.getString("NOMBRE_TIPO_MEDIDA"),
                            rs.getString("NOMBRE_UNIDAD_EQUIVALENCIA"),
                            rs.getString("NOMBRE_FORMA_ADQUISICION"),
                            rs.getString("NOMBRE_LUGAR_COMPRA"),
                            rs.getDouble("VALOR"),
                            rs.getString("NOMBRE_FRECUENCIA"),
                            rs.getString("TIPO_GASTO")
                    );

                    gastos.add(gasto);
                }
            }
        }
    }

    return gastos;
}

public void eliminarGastoDiario(Long idGasto) throws Exception {

    try (Connection con = conexion.conectar()) {

        if (con == null) {
            throw new Exception("No fue posible conectar con Oracle.");
        }

        String llamada =
                "{call PORTATIL_ENPH.SP_ELIMINAR_GASTO_DIARIO(?)}";

        try (CallableStatement cs = con.prepareCall(llamada)) {

            cs.setLong(1, idGasto);
            cs.execute();
        }
    }
}

}
