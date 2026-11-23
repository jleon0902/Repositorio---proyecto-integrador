
package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class P20_EncuestadorPortatilesAsignadosDAO {

    private final Connection conexionUsuario;

    public P20_EncuestadorPortatilesAsignadosDAO(
            Connection conexionUsuario) {

        this.conexionUsuario = conexionUsuario;
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

    // OBTENER PORTÁTILES ASIGNADOS
    public List<Object[]> obtenerPortatilesAsignados(
            String cedulaEncuestador) throws SQLException {

        verificarConexion();

        List<Object[]> lista = new ArrayList<>();

        String procedimiento =
                "{call SP20_PORTATILES_ASIGNADOS(?, ?)}";

        try (CallableStatement llamada =
                conexionUsuario.prepareCall(procedimiento)) {

            llamada.setString(1, cedulaEncuestador);

            llamada.registerOutParameter(2, Types.REF_CURSOR);

            llamada.execute();

            try (ResultSet resultado =
                    (ResultSet) llamada.getObject(2)) {

                while (resultado.next()) {

                    Object[] fila = new Object[5];

                    fila[0] = lista.size() + 1;

                    fila[1] = resultado.getString("CEDULA");

                    fila[2] = resultado.getString(
                            "NOMBRE_REPRESENTANTE");

                    fila[3] = resultado.getString(
                            "NUMERO_SERIE_PORTATIL");

                    fila[4] = resultado.getDate("FECHA_INICIO");

                    lista.add(fila);
                }
            }
        }

        return lista;
    }
}
