/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GastosDiariosDAO {

    // Representa un producto registrado en GASTOS_DIARIOS.
    public static class GastoDiario {

        public int id;
        public int numeroOrden;
        public String nombreProducto;
        public int cantidad;
        public Integer equivalencia;
        public int valor;
        public String tipoGasto;

        public int idProducto;
        public int idUnidadMedida;
        public int idFormaAdquisicion;
        public int idLugarCompra;
        public int idFrecuenciaCompra;
    }

    // Representa las opciones de los combos.
    public static class OpcionCatalogo {

        public int id;
        public String nombre;

        public OpcionCatalogo(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        @Override
        public String toString() {
            return nombre;
        }
    }

    // Consulta únicamente los productos de hoy de esta encuesta.
    public List<GastoDiario> cargarGastosHoy(int idEncuesta)
            throws SQLException {

        List<GastoDiario> lista = new ArrayList<>();

        String sql =
                "SELECT GD.ID, GD.NUMERO_ORDEN, P.NOMBRE AS PRODUCTO, "
                + "GD.CANTIDAD_ADQUIRIDA, GD.EQUIVALENCIA, GD.VALOR, "
                + "GD.TIPO_GASTO, GD.ID_PRODUCTO, GD.ID_UNIDADMEDIDA, "
                + "GD.ID_FORMAADQUISICION, GD.ID_LUGCOMPRA, "
                + "GD.ID_FRECOMPRA "
                + "FROM GASTOS_DIARIOS GD "
                + "INNER JOIN PRODUCTO P ON GD.ID_PRODUCTO = P.ID "
                + "WHERE GD.ID_ENCUESTA = ? "
                + "AND TRUNC(GD.FECHA) = TRUNC(SYSDATE) "
                + "ORDER BY GD.NUMERO_ORDEN, GD.ID";

        try (Connection cn = conexion.conectar()) {

            if (cn == null) {
                throw new SQLException("No fue posible conectar con Oracle.");
            }

            try (PreparedStatement ps = cn.prepareStatement(sql)) {

                ps.setInt(1, idEncuesta);

                try (ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {

                        GastoDiario gasto = new GastoDiario();

                        gasto.id = rs.getInt("ID");
                        gasto.numeroOrden = rs.getInt("NUMERO_ORDEN");
                        gasto.nombreProducto = rs.getString("PRODUCTO");
                        gasto.cantidad =
                                rs.getInt("CANTIDAD_ADQUIRIDA");

                        int equivalencia = rs.getInt("EQUIVALENCIA");
                        gasto.equivalencia =
                                rs.wasNull() ? null : equivalencia;

                        gasto.valor = rs.getInt("VALOR");
                        gasto.tipoGasto = rs.getString("TIPO_GASTO");

                        gasto.idProducto = rs.getInt("ID_PRODUCTO");
                        gasto.idUnidadMedida =
                                rs.getInt("ID_UNIDADMEDIDA");
                        gasto.idFormaAdquisicion =
                                rs.getInt("ID_FORMAADQUISICION");
                        gasto.idLugarCompra =
                                rs.getInt("ID_LUGCOMPRA");
                        gasto.idFrecuenciaCompra =
                                rs.getInt("ID_FRECOMPRA");

                        lista.add(gasto);
                    }
                }
            }
        }

        return lista;
    }

    // Carga las opciones de cualquiera de los catálogos indicados.
    private List<OpcionCatalogo> cargarCatalogo(String tabla)
            throws SQLException {

        List<OpcionCatalogo> lista = new ArrayList<>();

        // Solo se permiten estos nombres de tabla.
        if (!tabla.equals("UNIDAD_MEDIDA")
                && !tabla.equals("FORMA_ADQUISICION")
                && !tabla.equals("LUGAR_COMPRA")
                && !tabla.equals("FRECUENCIA_COMPRA")) {

            throw new SQLException("Catálogo no permitido.");
        }

        String sql = "SELECT ID, NOMBRE FROM " + tabla
                + " ORDER BY NOMBRE";

        try (Connection cn = conexion.conectar()) {

            if (cn == null) {
                throw new SQLException("No fue posible conectar con Oracle.");
            }

            try (PreparedStatement ps = cn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    lista.add(new OpcionCatalogo(
                            rs.getInt("ID"),
                            rs.getString("NOMBRE")
                    ));
                }
            }
        }

        return lista;
    }

    public List<OpcionCatalogo> cargarUnidades() throws SQLException {
        return cargarCatalogo("UNIDAD_MEDIDA");
    }

    public List<OpcionCatalogo> cargarFormasAdquisicion()
            throws SQLException {
        return cargarCatalogo("FORMA_ADQUISICION");
    }

    public List<OpcionCatalogo> cargarLugaresCompra()
            throws SQLException {
        return cargarCatalogo("LUGAR_COMPRA");
    }

    public List<OpcionCatalogo> cargarFrecuenciasCompra()
            throws SQLException {
        return cargarCatalogo("FRECUENCIA_COMPRA");
    }

    // Actualiza un registro solo si pertenece a la encuesta
    // indicada y su fecha corresponde al día actual.
    public boolean actualizarGasto(
            int idGasto,
            int idEncuesta,
            int cantidad,
            Integer equivalencia,
            int valor,
            String tipoGasto,
            int idUnidad,
            int idForma,
            int idLugar,
            int idFrecuencia) throws SQLException {

        String sql =
                "UPDATE GASTOS_DIARIOS SET "
                + "CANTIDAD_ADQUIRIDA = ?, "
                + "EQUIVALENCIA = ?, "
                + "VALOR = ?, "
                + "TIPO_GASTO = ?, "
                + "ID_UNIDADMEDIDA = ?, "
                + "ID_FORMAADQUISICION = ?, "
                + "ID_LUGCOMPRA = ?, "
                + "ID_FRECOMPRA = ? "
                + "WHERE ID = ? "
                + "AND ID_ENCUESTA = ? "
                + "AND TRUNC(FECHA) = TRUNC(SYSDATE)";

        try (Connection cn = conexion.conectar()) {

            if (cn == null) {
                throw new SQLException("No fue posible conectar con Oracle.");
            }

            try (PreparedStatement ps = cn.prepareStatement(sql)) {

                ps.setInt(1, cantidad);

                if (equivalencia == null) {
                    ps.setNull(2, java.sql.Types.NUMERIC);
                } else {
                    ps.setInt(2, equivalencia);
                }

                ps.setInt(3, valor);
                ps.setString(4, tipoGasto);
                ps.setInt(5, idUnidad);
                ps.setInt(6, idForma);

                if (idLugar <= 0) {
                    ps.setNull(7, java.sql.Types.NUMERIC);
                } else {
                    ps.setInt(7, idLugar);
                }

                ps.setInt(8, idFrecuencia);
                ps.setInt(9, idGasto);
                ps.setInt(10, idEncuesta);

                return ps.executeUpdate() == 1;
            }
        }
    }
}

