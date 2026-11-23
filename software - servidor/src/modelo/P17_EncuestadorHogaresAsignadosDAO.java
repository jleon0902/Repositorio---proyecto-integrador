
package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class P17_EncuestadorHogaresAsignadosDAO {

    private final Connection conexionUsuario;

    public P17_EncuestadorHogaresAsignadosDAO(
            Connection conexionUsuario) {

        this.conexionUsuario = conexionUsuario;
    }

    // =========================================
    // CONSULTAR CANTIDAD DE HOGARES ASIGNADOS
    // =========================================
    public int contarHogaresAsignados(
            String cedulaEncuestador) throws SQLException {

        verificarConexion();

        String procedimiento =
                "{call P17_CONTAR_HOGARES(?, ?)}";

        try (CallableStatement cs =
                conexionUsuario.prepareCall(procedimiento)) {

            cs.setString(1, cedulaEncuestador);

            cs.registerOutParameter(2, Types.NUMERIC);

            cs.execute();

            return cs.getInt(2);
        }
    }

    // =========================================
    // CONSULTAR INFORMACIÓN DE LOS HOGARES
    // =========================================
    public List<Object[]> obtenerHogaresAsignados(
            String cedulaEncuestador) throws SQLException {

        verificarConexion();

        List<Object[]> hogares = new ArrayList<>();

        String procedimiento =
                "{call P17_CONSULTAR_HOGARES(?, ?)}";

        try (CallableStatement cs =
                conexionUsuario.prepareCall(procedimiento)) {

            cs.setString(1, cedulaEncuestador);

            cs.registerOutParameter(2, Types.REF_CURSOR);

            cs.execute();

            try (ResultSet rs =
                    (ResultSet) cs.getObject(2)) {

                while (rs.next()) {

                    Object[] fila = {

                        rs.getInt("NUMERO_ORDEN"),

                        rs.getObject("NUMERO_VIVIENDA"),

                        rs.getString("RESPONSABLE_HOGAR"),

                        rs.getString("ESTADO_ENCUESTA")

                    };

                    hogares.add(fila);
                }
            }
        }

        return hogares;
    }

    // =========================================
    // VERIFICAR CONEXIÓN
    // =========================================
    public void verificarConexion() throws SQLException {

        if (conexionUsuario == null
                || conexionUsuario.isClosed()
                || !conexionUsuario.isValid(3)) {

            throw new SQLException(
                    "No existe una conexión activa con Oracle."
            );
        }
    }
}
