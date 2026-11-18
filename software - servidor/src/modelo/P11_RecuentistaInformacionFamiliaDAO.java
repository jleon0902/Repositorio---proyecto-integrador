
package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.math.BigDecimal;

public class P11_RecuentistaInformacionFamiliaDAO {

    public Object[] consultarInformacion(
            String numeroVivienda,
            String cedulaRecuentista
    ) throws SQLException {

        Object[] informacion = new Object[6];

        Connection conn = SesionUsuario.obtenerConexion();

        String sql = "{CALL PR_CONSULTAR_INFO_HOGAR(?, ?, ?)}";

        try (CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, numeroVivienda);
            cs.setString(2, cedulaRecuentista);

            cs.registerOutParameter(3, Types.REF_CURSOR);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(3)) {

                if (rs.next()) {

                    informacion[0] = rs.getString("DIRECCION");
                    informacion[1] = rs.getString("BARRIO");
                    informacion[2] = rs.getString("ESTRATO");
                    informacion[3] = rs.getString("REGION");
                    informacion[4] = rs.getString("DEPARTAMENTO");
                    informacion[5] = rs.getString("MUNICIPIO");

                } else {

                    throw new SQLException(
                            "No se encontró información para este hogar."
                    );
                }
            }
        }

        return informacion;
    }

    public boolean hogarTieneEncuestador(
            String numeroVivienda,
            String cedulaRecuentista
    ) throws SQLException {

        Connection conn = SesionUsuario.obtenerConexion();

        String sql =
                "SELECT COUNT(*) " +
                "FROM HOGAR " +
                "WHERE NUMERO_VIVIENDA = ? " +
                "AND CED_RECUENTISTA = ? " +
                "AND CED_ENCUESTADOR IS NOT NULL";

        try (java.sql.PreparedStatement ps =
                conn.prepareStatement(sql)) {

            ps.setString(1, numeroVivienda);
            ps.setString(2, cedulaRecuentista);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }
    
    public void eliminarHogar(
        String numeroVivienda,
        String cedulaRecuentista
) throws SQLException {

    Connection conn = SesionUsuario.obtenerConexion();

    String sql = "{CALL PR_ELIMINAR_HOGAR_RECUENTISTA(?, ?)}";

    try (CallableStatement cs = conn.prepareCall(sql)) {

        cs.setBigDecimal(1, new BigDecimal(numeroVivienda));
        cs.setString(2, cedulaRecuentista);

        cs.execute();
    }
}
}
