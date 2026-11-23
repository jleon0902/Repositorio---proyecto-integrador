
package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class P25CriticoHogaresDAO {

    private final Connection conexion;

    public P25CriticoHogaresDAO(Connection conexion)
            throws SQLException {

        if (conexion == null || conexion.isClosed()) {
            throw new SQLException(
                "No existe una conexión activa con Oracle."
            );
        }

        this.conexion = conexion;
    }

    // LISTAR HOGARES ENCUESTADOS
    public List<P25HogarItem> listarHogaresEncuestados(
            Integer idRegion,
            Integer idDepartamento,
            Integer idMunicipio) throws SQLException {

        List<P25HogarItem> lista = new ArrayList<>();

        String procedimiento =
            "{ call P25_LISTAR_HOGARES_ENCUESTADOS(?, ?, ?, ?) }";

        try (CallableStatement cs =
                conexion.prepareCall(procedimiento)) {

            if (idRegion == null) {
                cs.setNull(1, Types.NUMERIC);
            } else {
                cs.setInt(1, idRegion);
            }

            if (idDepartamento == null) {
                cs.setNull(2, Types.NUMERIC);
            } else {
                cs.setInt(2, idDepartamento);
            }

            if (idMunicipio == null) {
                cs.setNull(3, Types.NUMERIC);
            } else {
                cs.setInt(3, idMunicipio);
            }

            cs.registerOutParameter(4, Types.REF_CURSOR);

            cs.execute();

            try (ResultSet rs =
                    (ResultSet) cs.getObject(4)) {

                while (rs.next()) {

                    lista.add(new P25HogarItem(
                        rs.getInt("NUMERO_VIVIENDA"),
                        rs.getString("RESPONSABLE_HOGAR"),
                        rs.getString("FECHA_FIN")
                    ));
                }
            }
        }

        return lista;
    }
}
