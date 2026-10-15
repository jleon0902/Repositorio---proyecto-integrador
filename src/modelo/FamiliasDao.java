
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FamiliasDao {

    // Contar las familias que tienen una encuesta finalizada




public int contarFamiliasFinalizadas() throws SQLException {

    String sql = "SELECT ESTADO, COUNT(*) " +
                 "FROM ENCUESTA " +
                 "GROUP BY ESTADO";

    int totalFinalizadas = 0;

    try (Connection cn = conexion.conectar();
         PreparedStatement ps = cn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            String estado = rs.getString("ESTADO");
            int cantidad = rs.getInt(2);

            System.out.println(
                "Estado encontrado: [" + estado +
                "] - Cantidad: " + cantidad
            );

            if (estado != null &&
                estado.trim().equalsIgnoreCase("Finalizado")) {
                totalFinalizadas += cantidad;
            }
        }
    }

    System.out.println("TOTAL FINALIZADAS EN JAVA: " + totalFinalizadas);

    return totalFinalizadas;
}




    // Obtener todas las regiones
    public List<String> obtenerRegiones() throws SQLException {

        List<String> lista = new ArrayList<>();

        String sql =
            "SELECT NOMBRE FROM REGION ORDER BY NOMBRE";

        try (Connection cn = conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(rs.getString("NOMBRE"));
            }
        }

        return lista;
    }

    // Obtener departamentos de una región
    public List<String> obtenerDepartamentos(String region)
            throws SQLException {

        List<String> lista = new ArrayList<>();

        String sql =
            "SELECT d.NOMBRE " +
            "FROM DEPARTAMENTO d " +
            "INNER JOIN REGION r ON d.ID_REGION = r.ID " +
            "WHERE r.NOMBRE = ? " +
            "ORDER BY d.NOMBRE";

        try (Connection cn = conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, region);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(rs.getString("NOMBRE"));
                }
            }
        }

        return lista;
    }

    // Obtener municipios de un departamento y una región
    public List<String> obtenerMunicipios(
            String region, String departamento)
            throws SQLException {

        List<String> lista = new ArrayList<>();

        String sql =
            "SELECT m.NOMBRE " +
            "FROM MUNICIPIOS m " +
            "INNER JOIN DEPARTAMENTO d " +
            "ON m.ID_DEPARTAMENTO = d.ID " +
            "INNER JOIN REGION r ON d.ID_REGION = r.ID " +
            "WHERE r.NOMBRE = ? " +
            "AND d.NOMBRE = ? " +
            "ORDER BY m.NOMBRE";

        try (Connection cn = conexion.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, region);
            ps.setString(2, departamento);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(rs.getString("NOMBRE"));
                }
            }
        }

        return lista;
    }

    // Consultar familias con encuestas finalizadas según los filtros
    public List<Object[]> obtenerFamiliasFinalizadas(
            String region, String departamento, String municipio)
            throws SQLException {

        List<Object[]> lista = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
            "SELECT h.NUMERO_VIVIENDA, " +
            "r.NOMBRE AS REGION, " +
            "d.NOMBRE AS DEPARTAMENTO, " +
            "m.NOMBRE AS MUNICIPIO, " +
            "rep.NOMBRE AS RESPONSABLE, " +
            "e.ESTADO " +
            "FROM HOGAR h " +
            "INNER JOIN ENCUESTA e " +
            "ON h.CED_REPRESENTANTE = e.CEDULA_REPRESENTANTE " +
            "INNER JOIN MUNICIPIOS m " +
            "ON h.ID_MUNICIPIOS = m.ID " +
            "INNER JOIN DEPARTAMENTO d " +
            "ON m.ID_DEPARTAMENTO = d.ID " +
            "INNER JOIN REGION r " +
            "ON d.ID_REGION = r.ID " +
            "INNER JOIN REPRESENTANTE rep " +
            "ON h.CED_REPRESENTANTE = rep.CEDULA " +
            "WHERE UPPER(TRIM(e.ESTADO)) = 'FINALIZADO' "
        );

        if (region != null) {
            sql.append("AND r.NOMBRE = ? ");
        }

        if (departamento != null) {
            sql.append("AND d.NOMBRE = ? ");
        }

        if (municipio != null) {
            sql.append("AND m.NOMBRE = ? ");
        }

        sql.append("ORDER BY h.NUMERO_VIVIENDA");

        try (Connection cn = conexion.conectar();
             PreparedStatement ps =
                     cn.prepareStatement(sql.toString())) {

            int posicion = 1;

            if (region != null) {
                ps.setString(posicion++, region);
            }

            if (departamento != null) {
                ps.setString(posicion++, departamento);
            }

            if (municipio != null) {
                ps.setString(posicion++, municipio);
            }

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    lista.add(new Object[]{
                        rs.getString("NUMERO_VIVIENDA"),
                        rs.getString("RESPONSABLE"),
                        rs.getString("ESTADO"),
                        rs.getString("REGION"),
                        rs.getString("DEPARTAMENTO"),
                        rs.getString("MUNICIPIO")
                    });
                }
            }
        }

        return lista;
    }
}
