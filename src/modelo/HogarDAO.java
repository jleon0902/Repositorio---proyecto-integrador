package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HogarDAO {

    public List<Object[]> obtenerFamiliasEncuestador(String cedulaEncuestador) throws SQLException {
        List<Object[]> familias = new ArrayList<>();

        String sql =
                "SELECT * FROM ( " +
                "    SELECT H.NUMERO_VIVIENDA, " +
                "           R.CEDULA AS CEDULA_REPRESENTANTE, " +
                "           R.NOMBRE AS RESPONSABLE, " +
                "           NVL(E.ESTADO, 'Sin iniciar') AS ESTADO_ENCUESTA " +
                "    FROM HOGAR H " +
                "    JOIN REPRESENTANTE R ON H.CED_REPRESENTANTE = R.CEDULA " +
                "    LEFT JOIN ENCUESTA E ON E.CEDULA_REPRESENTANTE = R.CEDULA " +
                "    WHERE H.CED_ENCUESTADOR = ? " +
                "    ORDER BY H.NUMERO_VIVIENDA " +
                ") WHERE ROWNUM <= 4";

        try (Connection cn = conexion.conectar()) {
            if (cn == null) {
                throw new SQLException("No fue posible conectar con Oracle.");
            }

            try (PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setString(1, cedulaEncuestador);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        familias.add(new Object[]{
                            rs.getInt("NUMERO_VIVIENDA"),          // 0: Número de vivienda
                            rs.getString("CEDULA_REPRESENTANTE"),     // 1: Cédula del representante// Número de vivienda
                            rs.getString("RESPONSABLE"),   // Nombre del representante
                            rs.getString("ESTADO_ENCUESTA")// Estado o "Sin iniciar"
                        });
                    }
                }
            }
        }

        return familias;
    }
    
    

}
