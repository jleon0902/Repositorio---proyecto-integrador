
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

    // CONSULTAR HOGARES ASIGNADOS
    public List<Object[]> obtenerHogaresAsignados(
            String cedulaEncuestador) throws SQLException {

        verificarConexion();

        List<Object[]> hogares = new ArrayList<>();

        String procedimiento =
                "{call NOMBRE_PROCEDIMIENTO(?, ?)}";

        try (CallableStatement cs =
                conexionUsuario.prepareCall(procedimiento)) {

            cs.setString(1, cedulaEncuestador);

            cs.registerOutParameter(2, Types.REF_CURSOR);

            cs.execute();

            try (ResultSet rs =
                    (ResultSet) cs.getObject(2)) {

                int numero = 1;

                while (rs.next()) {

                    Object[] fila = {
                        numero++,
                        rs.getObject(1),
                        rs.getObject(2),
                        rs.getObject(3)
                    };

                    hogares.add(fila);
                }
            }
        }

        return hogares;
    }

    // VERIFICAR CONEXIÓN
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
