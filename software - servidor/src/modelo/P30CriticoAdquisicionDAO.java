
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class P30CriticoAdquisicionDAO {

    private final Connection conexion;

    private static final String ESQUEMA =
            "ENCUESTA_CUADERNILLO_ENPH.";

    public P30CriticoAdquisicionDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // VERIFICAR CONEXION
    public boolean verificarConexion() throws SQLException {

        return conexion != null
                && !conexion.isClosed()
                && conexion.isValid(2);
    }

    // PORCENTAJES DE FORMAS DE ADQUISICION
    public List<String[]> obtenerPorcentajesAdquisicion()
            throws SQLException {

        List<String[]> resultados = new ArrayList<>();

        String sql =
                "SELECT FA.ID, FA.NOMBRE, " +
                "COUNT(E.ID) AS CANTIDAD, " +
                "ROUND(COUNT(E.ID) * 100 / " +
                "NULLIF((SELECT COUNT(*) " +
                "FROM " + ESQUEMA + "GASTOS_DIARIOS GD2 " +
                "JOIN " + ESQUEMA + "ENCUESTA E2 " +
                "ON GD2.ID_ENCUESTA = E2.ID " +
                "WHERE UPPER(E2.ESTADO) = 'FINALIZADO'), 0), 2) " +
                "AS PORCENTAJE " +
                "FROM " + ESQUEMA + "FORMA_ADQUISICION FA " +
                "LEFT JOIN " + ESQUEMA + "GASTOS_DIARIOS GD " +
                "ON FA.ID = GD.ID_FORMA_ADQUISICION " +
                "LEFT JOIN " + ESQUEMA + "ENCUESTA E " +
                "ON GD.ID_ENCUESTA = E.ID " +
                "AND UPPER(E.ESTADO) = 'FINALIZADO' " +
                "GROUP BY FA.ID, FA.NOMBRE " +
                "ORDER BY FA.ID";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                String[] fila = new String[4];

                fila[0] = rs.getString("ID");
                fila[1] = rs.getString("NOMBRE");
                fila[2] = rs.getString("CANTIDAD");
                fila[3] = rs.getString("PORCENTAJE");

                resultados.add(fila);
            }
        }

        return resultados;
    }

    // LUGARES MAS CONCURRIDOS
    public List<String[]> obtenerLugaresMas(
            int estratoMin, int estratoMax)
            throws SQLException {

        return consultarLugar(estratoMin, estratoMax, "DESC");
    }

    // LUGARES MENOS CONCURRIDOS
    public List<String[]> obtenerLugarMenos(
            int estratoMin, int estratoMax)
            throws SQLException {

        return consultarLugar(estratoMin, estratoMax, "ASC");
    }

    // CONSULTA GENERAL DE LUGARES
    private List<String[]> consultarLugar(
            int estratoMin,
            int estratoMax,
            String orden) throws SQLException {

        List<String[]> resultados = new ArrayList<>();

        String sql =
                "SELECT ID, NOMBRE, CANTIDAD " +
                "FROM (" +
                "SELECT L.ID, L.NOMBRE, " +
                "COUNT(GD.ID) AS CANTIDAD " +
                "FROM " + ESQUEMA + "GASTOS_DIARIOS GD " +
                "JOIN " + ESQUEMA + "ENCUESTA E " +
                "ON GD.ID_ENCUESTA = E.ID " +
                "JOIN " + ESQUEMA + "HOGAR H " +
                "ON E.CEDULA_REPRESENTANTE = H.CED_REPRESENTANTE " +
                "JOIN " + ESQUEMA + "LUGAR_COMPRA_SERVICIO L " +
                "ON GD.ID_LUGAR_COMPRA = L.ID " +
                "WHERE UPPER(E.ESTADO) = 'FINALIZADO' " +
                "AND H.ESTRATO BETWEEN ? AND ? " +
                "AND GD.ID_FORMA_ADQUISICION = 101 " +
                "GROUP BY L.ID, L.NOMBRE " +
                "ORDER BY COUNT(GD.ID) " + orden +
                ", L.NOMBRE ASC" +
                ") WHERE ROWNUM = 1";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, estratoMin);
            ps.setInt(2, estratoMax);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    String[] fila = new String[3];

                    fila[0] = rs.getString("ID");
                    fila[1] = rs.getString("NOMBRE");
                    fila[2] = rs.getString("CANTIDAD");

                    resultados.add(fila);
                }
            }
        }

        return resultados;
    }
}
