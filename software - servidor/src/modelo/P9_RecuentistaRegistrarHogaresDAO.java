
package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class P9_RecuentistaRegistrarHogaresDAO {

    // REGISTRAR HOGAR Y REPRESENTANTE.

// REGISTRAR HOGAR Y REPRESENTANTE.
public void registrarHogar(
        int numeroVivienda,
        String direccion,
        int estrato,
        String nombreBarrio,
        String cedulaRecuentista,
        String cedula,
        String nombre,
        String apellido,
        String telefono,
        String correo,
        String usuario,
        String contrasena
) throws SQLException {

    Connection conn = SesionUsuario.obtenerConexion();

    boolean autoCommitOriginal = conn.getAutoCommit();

    try {

        conn.setAutoCommit(false);

        String sql = "{CALL PR_REG_REP_HOGAR_NOMBRE(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, numeroVivienda);
            cs.setString(2, direccion);
            cs.setInt(3, estrato);
            cs.setString(4, nombreBarrio);
            cs.setString(5, cedulaRecuentista);
            cs.setString(6, cedula);
            cs.setString(7, nombre);
            cs.setString(8, apellido);
            cs.setString(9, telefono);
            if (correo == null || correo.trim().isEmpty()) {
    cs.setNull(10, Types.VARCHAR);
} else {
    cs.setString(10, correo.trim());
}
            cs.setString(11, usuario);
            cs.setString(12, contrasena);

            cs.execute();
        }

        conn.commit();

    } catch (SQLException e) {

        conn.rollback();
        throw e;

    } finally {

        conn.setAutoCommit(autoCommitOriginal);
    }
}


    // OBTENER LOS BARRIOS DEL MUNICIPIO DEL RECUENTISTA.
    public List<String> obtenerBarriosRecuentista(
            String cedulaRecuentista
    ) throws SQLException {

        List<String> barrios = new ArrayList<>();

        Connection conn = SesionUsuario.obtenerConexion();

        String sql = "{CALL OBTENER_BARRIOS_RECUENTISTA(?, ?)}";

        try (CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, cedulaRecuentista);
            cs.registerOutParameter(2, Types.REF_CURSOR);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(2)) {

                while (rs.next()) {
                    barrios.add(rs.getString("NOMBRE"));
                }
            }
        }

        return barrios;
    }

    
// REGISTRAR UN BARRIO NUEVO PARA EL RECUENTISTA.
public void registrarBarrioRecuentista(
        String nombre,
        String cedulaRecuentista
) throws SQLException {

    Connection conn = SesionUsuario.obtenerConexion();

    String sql = "{CALL REGISTRAR_BARRIO_RECUENTISTA(?, ?)}";

    try (CallableStatement cs = conn.prepareCall(sql)) {

        cs.setString(1, nombre.trim());
        cs.setString(2, cedulaRecuentista);

        cs.execute();
    }
}
}
