
package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class P12_RecuentistaEditarHogaresDAO {

    // 1. CONSULTAR INFORMACION DEL HOGAR.
    public Object[] consultarInformacion(
            String numeroVivienda,
            String cedulaRecuentista
    ) throws SQLException {

        Connection conn = SesionUsuario.obtenerConexion();

        String sql = "{CALL SP_P12_CONSULTAR_HOGAR(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (CallableStatement cs = conn.prepareCall(sql)) {

            cs.setBigDecimal(1, new BigDecimal(numeroVivienda.trim()));
            cs.setString(2, cedulaRecuentista);

            for (int i = 3; i <= 17; i++) {

                if (i == 5 || i == 16 || i == 17) {
                    cs.registerOutParameter(i, Types.NUMERIC);
                } else {
                    cs.registerOutParameter(i, Types.VARCHAR);
                }
            }

            cs.execute();

            return new Object[]{
                cs.getString(3),   // 0 Dirección
                cs.getString(4),   // 1 Barrio
                cs.getInt(5),      // 2 Estrato
                cs.getString(6),   // 3 Región
                cs.getString(7),   // 4 Departamento
                cs.getString(8),   // 5 Municipio
                cs.getString(9),   // 6 Cédula
                cs.getString(10),  // 7 Nombre
                cs.getString(11),  // 8 Apellido
                cs.getString(12),  // 9 Teléfono
                cs.getString(13),  // 10 Correo
                cs.getString(14),  // 11 Usuario
                cs.getString(15),  // 12 Contraseña
                cs.getInt(16),     // 13 ID barrio
                cs.getInt(17)      // 14 ID municipio
            };
        }
    }

    // 2. CONSULTAR ESTADO DE LA ENCUESTA.
  
public String consultarEstado(
        String numeroVivienda,
        String cedulaRecuentista
) throws SQLException {

    Connection conn = SesionUsuario.obtenerConexion();

    String sql = "{CALL SP_ESTADO_ENCUESTA_HOGAR(?, ?, ?)}";

    try (CallableStatement cs = conn.prepareCall(sql)) {

        cs.setBigDecimal(1, new BigDecimal(numeroVivienda));
        cs.setString(2, cedulaRecuentista);
        cs.registerOutParameter(3, Types.VARCHAR);

        cs.execute();

        return cs.getString(3);
    }
}


    // 3. OBTENER LOS BARRIOS DEL MUNICIPIO.
    public List<Object[]> obtenerBarrios(
            int idMunicipio
    ) throws SQLException {

        List<Object[]> barrios = new ArrayList<>();

        Connection conn = SesionUsuario.obtenerConexion();

        String sql = "{CALL OBTENER_BARRIOS(?, ?)}";

        try (CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idMunicipio);
            cs.registerOutParameter(2, Types.REF_CURSOR);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(2)) {

                while (rs.next()) {

                    barrios.add(new Object[]{
                        rs.getInt("ID"),
                        rs.getString("NOMBRE")
                    });
                }
            }
        }

        return barrios;
    }
    
    
    // 4. VALIDAR SI EL BARRIO YA EXISTE.
    public boolean validarBarrio(
            String nombreBarrio,
            int idMunicipio
    ) throws SQLException {

        Connection conn = SesionUsuario.obtenerConexion();

        String sql = "{CALL SP_P12_VALIDAR_BARRIO(?, ?, ?)}";

        try (CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, nombreBarrio.trim());
            cs.setInt(2, idMunicipio);

            cs.registerOutParameter(3, Types.NUMERIC);

            cs.execute();

            return cs.getInt(3) > 0;
        }
    }


    // 5. INSERTAR UN BARRIO Y RECUPERAR SU ID.
    public int insertarBarrio(
            String nombreBarrio,
            int idMunicipio
    ) throws SQLException {

        Connection conn = SesionUsuario.obtenerConexion();

        String sql = "{CALL SP_P12_INSERTAR_BARRIO(?, ?, ?)}";

        try (CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, nombreBarrio.trim());
            cs.setInt(2, idMunicipio);

            cs.registerOutParameter(3, Types.NUMERIC);

            cs.execute();

            return cs.getInt(3);
        }
    }


    // 4. ACTUALIZAR LOS DATOS DEL HOGAR.
    public void editarHogar(
            String numeroViviendaAnterior,
            String numeroViviendaNuevo,
            String direccion,
            int estrato,
            int idBarrio,
            String cedulaRecuentista,
            String cedulaNueva,
            String nombre,
            String apellido,
            String telefono,
            String correo,
            String usuario,
            String contrasena
    ) throws SQLException {

        Connection conn = SesionUsuario.obtenerConexion();

        // IMPORTANTE: este es el procedimiento de ACTUALIZAR.
        String sql = "{CALL SP_P12_ACTUALIZAR_HOGAR(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (CallableStatement cs = conn.prepareCall(sql)) {

            cs.setBigDecimal(1,
                    new BigDecimal(numeroViviendaAnterior.trim()));

            cs.setBigDecimal(2,
                    new BigDecimal(numeroViviendaNuevo.trim()));

            cs.setString(3, direccion);
            cs.setInt(4, estrato);
            cs.setInt(5, idBarrio);
            cs.setString(6, cedulaRecuentista);
            cs.setString(7, cedulaNueva);
            cs.setString(8, nombre);
            cs.setString(9, apellido);
            cs.setString(10, telefono);

            if (correo == null || correo.trim().isEmpty()) {
                cs.setNull(11, Types.VARCHAR);
            } else {
                cs.setString(11, correo.trim());
            }

            cs.setString(12, usuario);
            cs.setString(13, contrasena);

            cs.execute();
        }
    }
}
