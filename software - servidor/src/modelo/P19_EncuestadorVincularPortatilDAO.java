
package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class P19_EncuestadorVincularPortatilDAO {

    private final Connection conexionUsuario;

    public P19_EncuestadorVincularPortatilDAO(
            Connection conexionUsuario) {

        this.conexionUsuario = conexionUsuario;
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


public List<String> obtenerRepresentantesSinPortatil(
        String cedulaEncuestador) throws SQLException {

    List<String> representantes = new ArrayList<>();

    String procedimiento =
            "{CALL SP_REPRESENTANTES_SIN_PORTATIL(?, ?)}";

    try (CallableStatement cs =
            conexionUsuario.prepareCall(procedimiento)) {

        cs.setString(1, cedulaEncuestador);

        cs.registerOutParameter(2, Types.REF_CURSOR);

        cs.execute();

        try (ResultSet rs = (ResultSet) cs.getObject(2)) {

            while (rs.next()) {

                String cedula = rs.getString("CEDULA");
                String nombre = rs.getString("NOMBRE_COMPLETO");

                representantes.add(
                        cedula + " / " + nombre
                );
            }
        }
    }

    return representantes;
}

    
    
public List<String> obtenerPortatilesDisponibles() throws SQLException {

    List<String> portatiles = new ArrayList<>();

    String procedimiento =
            "{CALL SP_PORTATILES_DISPONIBLES(?)}";

    try (CallableStatement cs =
            conexionUsuario.prepareCall(procedimiento)) {

        cs.registerOutParameter(1, Types.REF_CURSOR);

        cs.execute();

        try (ResultSet rs = (ResultSet) cs.getObject(1)) {

            while (rs.next()) {

                portatiles.add(
                        rs.getString("NUMERO_SERIE")
                );
            }
        }
    }

    return portatiles;
}






public long vincularPortatil(
        String cedulaRepresentante,
        String numeroSerie,
        java.sql.Date fechaEntrega) throws SQLException {

    String procedimiento =
            "{CALL SP_VINCULAR_PORTATIL(?, ?, ?, ?)}";

    try (CallableStatement cs =
            conexionUsuario.prepareCall(procedimiento)) {

        cs.setString(1, cedulaRepresentante);
        cs.setString(2, numeroSerie);
        cs.setDate(3, fechaEntrega);

        cs.registerOutParameter(4, Types.NUMERIC);

        cs.execute();

        return cs.getLong(4);
    }
}

public int prepararExportacion(
        String cedulaRepresentante,
        String cedulaEncuestador,
        long idEncuesta) throws SQLException {

    String procedimiento =
        "{CALL ENCUESTA_CUADERNILLO_ENPH.SP_PREPARAR_EXPORTACION(?, ?, ?, ?)}";

    try (CallableStatement cs =
            conexionUsuario.prepareCall(procedimiento)) {

        cs.setString(1, cedulaRepresentante);
        cs.setString(2, cedulaEncuestador);
        cs.setLong(3, idEncuesta);

        cs.registerOutParameter(4, Types.NUMERIC);

        cs.execute();

        return cs.getInt(4);
    }
}





}
