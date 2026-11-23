
package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class P15_RecuentistaEditarHogaresAsignadosDAO {

    // LISTAR ENCUESTADORES PARA LA TABLA 1
    public List<Object[]> listarEncuestadores() 
            throws SQLException {

        List<Object[]> lista = new ArrayList<>();

        Connection conn = SesionUsuario.obtenerConexion();

        String sql = "{CALL P13_LISTAR_ENCUESTADORES(?)}";

        try (CallableStatement cs = conn.prepareCall(sql)) {

            cs.registerOutParameter(1, Types.REF_CURSOR);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                int numero = 1;

                while (rs.next()) {

                    Object[] fila = {
                        numero++,
                        rs.getString("CEDULA"),
                        rs.getString("NOMBRE"),
                        rs.getString("APELLIDO"),
                        rs.getInt("HOGARES_ASIGNADOS")
                    };

                    lista.add(fila);
                }
            }
        }

        return lista;
    }


    // LISTAR HOGARES DISPONIBLES PARA LA TABLA 2
    public List<Object[]> listarHogaresRecuentista(
            String cedulaRecuentista)
            throws SQLException {

        List<Object[]> lista = new ArrayList<>();

        Connection conn = SesionUsuario.obtenerConexion();

        String sql = "{CALL SP_LISTAR_HOGARES_RECUENTISTA(?, ?)}";

        try (CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, cedulaRecuentista);

            cs.registerOutParameter(2, Types.REF_CURSOR);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(2)) {

                int numero = 1;

                while (rs.next()) {

                    Object[] fila = {
                        numero++,
                        rs.getString("NUMERO_VIVIENDA"),
                        rs.getString("DIRECCION"),
                        rs.getString("RESPONSABLE"),
                        rs.getString("TELEFONO")
                    };

                    lista.add(fila);
                }
            }
        }

        return lista;
    }
    
    
    
public void asignarHogares(
        String cedulaRecuentista,
        String cedulaEncuestador,
        java.util.List<String> hogares)
        throws SQLException {

    Connection conn = SesionUsuario.obtenerConexion();

    boolean autoCommitAnterior = conn.getAutoCommit();

    java.sql.Savepoint puntoGuardado = null;

    try {

        if (autoCommitAnterior) {
            conn.setAutoCommit(false);
        } else {
            puntoGuardado = conn.setSavepoint();
        }

        String sql = "{CALL P13_ASIGNAR_HOGAR(?, ?, ?)}";

        try (CallableStatement cs = conn.prepareCall(sql)) {

            for (String numeroVivienda : hogares) {

                cs.setString(1, cedulaRecuentista);
                cs.setString(2, cedulaEncuestador);
                cs.setInt(3, Integer.parseInt(numeroVivienda));

                cs.execute();
            }
        }

        if (autoCommitAnterior) {
            conn.commit();
        } else {
            conn.releaseSavepoint(puntoGuardado);
        }

    } catch (SQLException | NumberFormatException e) {

        if (autoCommitAnterior) {
            conn.rollback();
        } else if (puntoGuardado != null) {
            conn.rollback(puntoGuardado);
        }

        if (e instanceof SQLException) {
            throw (SQLException) e;
        }

        throw new SQLException(
                "El número de vivienda no es válido.", e
        );

    } finally {

        if (autoCommitAnterior) {
            conn.setAutoCommit(true);
        }
    }
}

}
