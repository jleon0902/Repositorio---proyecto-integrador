
package modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class PortatilDAO {

    // REGISTRAR PORTATIL
    public int registrarPortatil(
            Connection conexion,
            String numeroSerie,
            String marca,
            String modelo) throws SQLException {

        String llamada = "{CALL SP_REGISTRAR_PORTATIL(?, ?, ?, ?)}";

        try (CallableStatement cs = conexion.prepareCall(llamada)) {

            cs.setString(1, numeroSerie);
            cs.setString(2, marca);
            cs.setString(3, modelo);

            cs.registerOutParameter(4, Types.NUMERIC);

            cs.execute();

            return cs.getInt(4);
        }
    }

    // LISTAR PORTATILES
    public List<Portatil> listarPortatiles(Connection conexion)
            throws SQLException {

        List<Portatil> lista = new ArrayList<>();

        String llamada = "{CALL SP_LISTAR_PORTATILES(?)}";

        try (CallableStatement cs = conexion.prepareCall(llamada)) {

            cs.registerOutParameter(1, Types.REF_CURSOR);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                while (rs.next()) {

                    Portatil portatil = new Portatil(
                            rs.getString("NUMERO_SERIE"),
                            rs.getString("MARCA"),
                            rs.getString("MODELO")
                    );

                    lista.add(portatil);
                }
            }
        }

        return lista;
    }

    // VERIFICAR SI ESTA ASIGNADO
    public boolean estaAsignado(
            Connection conexion,
            String numeroSerie) throws SQLException {

        String llamada =
                "{CALL SP_VERIFICAR_PORTATIL_ASIGNADO(?, ?)}";

        try (CallableStatement cs = conexion.prepareCall(llamada)) {

            cs.setString(1, numeroSerie);

            cs.registerOutParameter(2, Types.NUMERIC);

            cs.execute();

            return cs.getInt(2) == 1;
        }
    }

    // ACTUALIZAR PORTATIL
    public void actualizarPortatil(
            Connection conexion,
            String numeroSerieAnterior,
            String numeroSerieNuevo,
            String marca,
            String modelo) throws SQLException {

        String llamada =
                "{CALL SP_ACTUALIZAR_PORTATIL(?, ?, ?, ?)}";

        try (CallableStatement cs = conexion.prepareCall(llamada)) {

            cs.setString(1, numeroSerieAnterior);
            cs.setString(2, numeroSerieNuevo);
            cs.setString(3, marca);
            cs.setString(4, modelo);

            cs.execute();
        }
    }

    // ELIMINAR PORTATIL
    public void eliminarPortatil(
            Connection conexion,
            String numeroSerie) throws SQLException {

        String llamada = "{CALL SP_ELIMINAR_PORTATIL(?)}";

        try (CallableStatement cs = conexion.prepareCall(llamada)) {

            cs.setString(1, numeroSerie);

            cs.execute();
        }
    }
}
