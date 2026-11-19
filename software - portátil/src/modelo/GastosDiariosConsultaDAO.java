
package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class GastosDiariosConsultaDAO {

    public List<GastoDiarioConsulta> listarPorEncuesta(
            String cedulaRepresentante) {

        List<GastoDiarioConsulta> gastos = new ArrayList<>();

        // Llamar al procedimiento exclusivo del recuentista.
    String procedimiento =
        "{CALL PORTATIL_ENPH.SP_LISTAR_GASTOS_DIA_REC(?, ?)}";

        try (Connection cn = conexion.conectar()) {

            if (cn == null) {
                return gastos;
            }

            try (CallableStatement cs =
                    cn.prepareCall(procedimiento)) {

                cs.setString(1, cedulaRepresentante);

                cs.registerOutParameter(2, OracleTypes.CURSOR);

                cs.execute();

                try (ResultSet rs =
                        (ResultSet) cs.getObject(2)) {

                    while (rs.next()) {

                        Double cantidadEquivalencia =
                                rs.getObject("CANTIDAD_MEDIDA_EQUI") == null
                                ? null
                                : rs.getDouble("CANTIDAD_MEDIDA_EQUI");

                        GastoDiarioConsulta gasto =
                                new GastoDiarioConsulta(

                                    rs.getInt("ID"),

                                    rs.getInt("NUMERO_ORDEN"),

                                    rs.getString("NOMBRE_PRODUCTO"),

                                    rs.getDouble("CANTIDAD_ADQUIRIDA"),

                                    rs.getString("NOMBRE_UNIDAD_MEDIDA"),

                                    rs.getString("NOMBRE_FORMA_ADQUISICION"),

                                    rs.getString("NOMBRE_TIPO_MEDIDA"),

                                    rs.getString("NOMBRE_UNIDAD_EQUIVALENCIA"),

                                    cantidadEquivalencia,

                                    rs.getString("NOMBRE_LUGAR_COMPRA"),

                                    rs.getDouble("VALOR"),

                                    rs.getString("NOMBRE_FRECUENCIA"),

                                    rs.getString("TIPO_GASTO")
                                );

                        gastos.add(gasto);
                    }
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al consultar los gastos diarios: "
                    + e.getMessage()
            );
        }

        return gastos;
    }
    
    
public boolean eliminarGastoDiario(int idGasto) {

    String sql =
        "{CALL PORTATIL_ENPH.SP_ELIMINAR_GASTO_DIARIO(?)}";

    try (Connection cn = conexion.conectar()) {

        if (cn == null) {
            return false;
        }

        boolean autoCommitOriginal = cn.getAutoCommit();

        try {

            cn.setAutoCommit(false);

            try (CallableStatement cs = cn.prepareCall(sql)) {

                cs.setInt(1, idGasto);
                cs.execute();

            }

            cn.commit();
            return true;

        } catch (SQLException e) {

            cn.rollback();

            System.out.println(
                "Error al eliminar el gasto diario: "
                + e.getMessage()
            );

            return false;

        } finally {

            cn.setAutoCommit(autoCommitOriginal);
        }

    } catch (SQLException e) {

        System.out.println(
            "Error de conexión al eliminar el gasto: "
            + e.getMessage()
        );

        return false;
    }
}

    
}
