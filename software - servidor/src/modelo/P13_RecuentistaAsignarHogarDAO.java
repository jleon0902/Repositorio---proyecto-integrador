
package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class P13_RecuentistaAsignarHogarDAO {

    public List<HogarAsignarDTO> listarHogares(
            String cedulaRecuentista) throws SQLException {

        List<HogarAsignarDTO> hogares = new ArrayList<>();

        Connection con = SesionUsuario.obtenerConexion();

        String procedimiento =
                "{CALL SP_LISTAR_HOGARES_RECUENTISTA(?, ?)}";

        try (CallableStatement cs =
                con.prepareCall(procedimiento)) {

            cs.setString(1, cedulaRecuentista);
            cs.registerOutParameter(2, OracleTypes.CURSOR);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(2)) {

                while (rs.next()) {

                    HogarAsignarDTO hogar = new HogarAsignarDTO(
                            rs.getLong("NUMERO_VIVIENDA"),
                            rs.getString("DIRECCION"),
                            rs.getString("RESPONSABLE"),
                            rs.getString("TELEFONO")
                    );

                    hogares.add(hogar);
                }
            }
        }

        return hogares;
    }
    // ASIGNAR UN HOGAR A UN ENCUESTADOR.
public void asignarHogar(
        Connection con,
        String cedulaRecuentista,
        String cedulaEncuestador,
        long numeroVivienda
) throws SQLException {

    String procedimiento = "{CALL P13_ASIGNAR_HOGAR(?, ?, ?)}";

    try (CallableStatement cs = con.prepareCall(procedimiento)) {

        cs.setString(1, cedulaRecuentista);
        cs.setString(2, cedulaEncuestador);
        cs.setLong(3, numeroVivienda);

        cs.execute();
    }
}
    
    
public List<P13_EncuestadorDTO> listarEncuestadores()
        throws SQLException {

    List<P13_EncuestadorDTO> encuestadores = new ArrayList<>();

    Connection con = SesionUsuario.obtenerConexion();

    String procedimiento =
            "{CALL P13_LISTAR_ENCUESTADORES(?)}";

    try (CallableStatement cs = con.prepareCall(procedimiento)) {

        cs.registerOutParameter(1, OracleTypes.CURSOR);

        cs.execute();

        try (ResultSet rs = (ResultSet) cs.getObject(1)) {

            while (rs.next()) {

                P13_EncuestadorDTO encuestador =
                        new P13_EncuestadorDTO(
                                rs.getString("CEDULA"),
                                rs.getString("NOMBRE"),
                                rs.getString("APELLIDO"),
                                rs.getInt("HOGARES_ASIGNADOS")
                        );

                encuestadores.add(encuestador);
            }
        }
    }

    return encuestadores;
}

}
