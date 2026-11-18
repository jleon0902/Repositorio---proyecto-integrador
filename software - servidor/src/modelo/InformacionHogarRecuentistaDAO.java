
package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.math.BigDecimal;

public class InformacionHogarRecuentistaDAO {

    public Object[] consultarInformacion(
            String numeroVivienda,
            String cedulaRecuentista) throws SQLException {

        String llamada =
                "{CALL SP_INFO_HOGAR_RECUENTISTA(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        Connection conexion = SesionUsuario.obtenerConexion();

        try (CallableStatement cs = conexion.prepareCall(llamada)) {

            // PARÁMETROS DE ENTRADA
            cs.setBigDecimal(1, new BigDecimal(numeroVivienda.trim()));
            cs.setString(2, cedulaRecuentista);

            // INFORMACIÓN DEL HOGAR
            cs.registerOutParameter(3, Types.VARCHAR);
            cs.registerOutParameter(4, Types.VARCHAR);
            cs.registerOutParameter(5, Types.NUMERIC);
            cs.registerOutParameter(6, Types.VARCHAR);
            cs.registerOutParameter(7, Types.VARCHAR);
            cs.registerOutParameter(8, Types.VARCHAR);

            // INFORMACIÓN DEL REPRESENTANTE
            cs.registerOutParameter(9, Types.VARCHAR);
            cs.registerOutParameter(10, Types.VARCHAR);
            cs.registerOutParameter(11, Types.VARCHAR);
            cs.registerOutParameter(12, Types.VARCHAR);
            cs.registerOutParameter(13, Types.VARCHAR);

            // INFORMACIÓN DEL USUARIO
            cs.registerOutParameter(14, Types.VARCHAR);
            cs.registerOutParameter(15, Types.VARCHAR);

            cs.execute();

            return new Object[]{

                // HOGAR
                cs.getString(3),    // DIRECCION
                cs.getString(4),    // BARRIO
                cs.getObject(5),    // ESTRATO
                cs.getString(6),    // REGION
                cs.getString(7),    // DEPARTAMENTO
                cs.getString(8),    // MUNICIPIO

                // REPRESENTANTE
                cs.getString(9),    // CEDULA
                cs.getString(10),   // NOMBRE
                cs.getString(11),   // APELLIDO
                cs.getString(12),   // TELEFONO
                cs.getString(13),   // CORREO

                // USUARIO
                cs.getString(14),   // USUARIO
                cs.getString(15)    // CONTRASENA
            };
        }
    }
    
    
public boolean hogarTieneEncuestador(
        String numeroVivienda,
        String cedulaRecuentista) throws SQLException {

    String llamada =
            "{CALL SP_VERIFICAR_ENCUESTADOR_HOGAR(?, ?, ?)}";

    Connection conexion = SesionUsuario.obtenerConexion();

    try (CallableStatement cs = conexion.prepareCall(llamada)) {

        cs.setBigDecimal(
                1,
                new BigDecimal(numeroVivienda.trim())
        );

        cs.setString(2, cedulaRecuentista);

        cs.registerOutParameter(3, Types.NUMERIC);

        cs.execute();

        return cs.getInt(3) == 1;
    }
} 
    

public void eliminarHogar(
        String numeroVivienda,
        String cedulaRecuentista
) throws SQLException {

    String llamada =
            "{CALL PR_ELIMINAR_HOGAR_RECUENTISTA(?, ?)}";

    Connection conexion = SesionUsuario.obtenerConexion();

    try (CallableStatement cs = conexion.prepareCall(llamada)) {

        cs.setBigDecimal(
                1,
                new BigDecimal(numeroVivienda.trim())
        );

        cs.setString(2, cedulaRecuentista);

        cs.execute();
    }
}
    
    
  public String consultarEstadoEncuestaHogar(
        String numeroVivienda,
        String cedulaRecuentista
) throws SQLException {

    String llamada =
            "{CALL SP_ESTADO_ENCUESTA_HOGAR(?, ?, ?)}";

    Connection conexion = SesionUsuario.obtenerConexion();

    try (CallableStatement cs = conexion.prepareCall(llamada)) {

        cs.setBigDecimal(
                1,
                new BigDecimal(numeroVivienda.trim())
        );

        cs.setString(2, cedulaRecuentista);

        cs.registerOutParameter(3, Types.VARCHAR);

        cs.execute();

        return cs.getString(3);
    }
}  
    
}
