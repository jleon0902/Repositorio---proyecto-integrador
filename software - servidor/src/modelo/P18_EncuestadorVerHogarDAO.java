
package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;

public class P18_EncuestadorVerHogarDAO {

    private final Connection conexionUsuario;

    public P18_EncuestadorVerHogarDAO(Connection conexionUsuario) {
        this.conexionUsuario = conexionUsuario;
    }

    public Map<String, Object> consultarHogar(
            int numeroVivienda) throws SQLException {

        verificarConexion();

        Map<String, Object> datos = new LinkedHashMap<>();

        String procedimiento =
                "{call P18_CONSULTAR_HOGAR(?, ?)}";

        try (CallableStatement cs =
                conexionUsuario.prepareCall(procedimiento)) {

            cs.setInt(1, numeroVivienda);

            cs.registerOutParameter(2, Types.REF_CURSOR);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(2)) {

                if (rs.next()) {

                    datos.put("NUMERO_VIVIENDA",
                            rs.getObject("NUMERO_VIVIENDA"));

                    datos.put("UBICACION",
                            rs.getObject("UBICACION"));

                    datos.put("DIRECCION",
                            rs.getObject("DIRECCION"));

                    datos.put("BARRIO",
                            rs.getObject("BARRIO"));

                    datos.put("ESTRATO",
                            rs.getObject("ESTRATO"));

                    datos.put("ESTADO",
                            rs.getObject("ESTADO"));

                    datos.put("DIA_ENCUESTA",
                            rs.getObject("DIA_ENCUESTA"));

                    datos.put("FECHA_INICIO",
                            rs.getObject("FECHA_INICIO"));

                    datos.put("FECHA_FIN",
                            rs.getObject("FECHA_FIN"));

                    datos.put("REPRESENTANTE",
                            rs.getObject("REPRESENTANTE"));

                    datos.put("CEDULA",
                            rs.getObject("CEDULA"));

                    datos.put("TELEFONO",
                            rs.getObject("TELEFONO"));

                    datos.put("CORREO",
                            rs.getObject("CORREO"));

                    datos.put("NUMERO_SERIE_PORTATIL",
                            rs.getObject("NUMERO_SERIE_PORTATIL"));

                    datos.put("MARCA",
                            rs.getObject("MARCA"));

                    datos.put("MODELO",
                            rs.getObject("MODELO"));

                    datos.put("FECHA_RECOGIDA",
                            rs.getObject("FECHA_RECOGIDA"));
                }
            }
        }

        return datos;
    }

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
