
package modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class P24CriticoHogaresDAO {

    private final Connection conexion;

    public P24CriticoHogaresDAO(Connection conexion)
            throws SQLException {

        if (conexion == null || conexion.isClosed()) {
            throw new SQLException(
                "No existe una conexión activa con Oracle."
            );
        }

        this.conexion = conexion;
    }

    // CONTAR HOGARES FINALIZADOS
    public int contarHogaresFinalizados() throws SQLException {

        String procedimiento =
            "{ call P24_CONTAR_HOGARES_FINALIZADOS(?) }";

        try (CallableStatement cs =
                conexion.prepareCall(procedimiento)) {

            cs.registerOutParameter(1, Types.NUMERIC);

            cs.execute();

            return cs.getInt(1);
        }
    }

    // LISTAR REGIONES
    public List<P24ItemCombo> listarRegiones()
            throws SQLException {

        List<P24ItemCombo> lista = new ArrayList<>();

        String procedimiento =
            "{ call P24_LISTAR_REGIONES(?) }";

        try (CallableStatement cs =
                conexion.prepareCall(procedimiento)) {

            cs.registerOutParameter(
                1, Types.REF_CURSOR
            );

            cs.execute();

            try (ResultSet rs =
                    (ResultSet) cs.getObject(1)) {

                while (rs.next()) {
                    lista.add(new P24ItemCombo(
                        rs.getInt("ID"),
                        rs.getString("NOMBRE")
                    ));
                }
            }
        }

        return lista;
    }

    // LISTAR DEPARTAMENTOS
    public List<P24ItemCombo> listarDepartamentos(
            int idRegion) throws SQLException {

        List<P24ItemCombo> lista = new ArrayList<>();

        String procedimiento =
            "{ call P24_LISTAR_DEPARTAMENTOS(?, ?) }";

        try (CallableStatement cs =
                conexion.prepareCall(procedimiento)) {

            cs.setInt(1, idRegion);

            cs.registerOutParameter(
                2, Types.REF_CURSOR
            );

            cs.execute();

            try (ResultSet rs =
                    (ResultSet) cs.getObject(2)) {

                while (rs.next()) {
                    lista.add(new P24ItemCombo(
                        rs.getInt("ID"),
                        rs.getString("NOMBRE")
                    ));
                }
            }
        }

        return lista;
    }

    // LISTAR MUNICIPIOS
    public List<P24ItemCombo> listarMunicipios(
            int idDepartamento) throws SQLException {

        List<P24ItemCombo> lista = new ArrayList<>();

        String procedimiento =
            "{ call P24_LISTAR_MUNICIPIOS(?, ?) }";

        try (CallableStatement cs =
                conexion.prepareCall(procedimiento)) {

            cs.setInt(1, idDepartamento);

            cs.registerOutParameter(
                2, Types.REF_CURSOR
            );

            cs.execute();

            try (ResultSet rs =
                    (ResultSet) cs.getObject(2)) {

                while (rs.next()) {
                    lista.add(new P24ItemCombo(
                        rs.getInt("ID"),
                        rs.getString("NOMBRE")
                    ));
                }
            }
        }

        return lista;
    }
    
// CREAR DAO DE P25 CON LA MISMA CONEXIÓN
public P25CriticoHogaresDAO crearDAOHogaresP25()
        throws SQLException {

    return new P25CriticoHogaresDAO(conexion);
}


    
}
