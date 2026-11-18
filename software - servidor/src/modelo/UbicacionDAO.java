/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class UbicacionDAO {

    public List<Ubicacion> obtenerRegiones(Connection cn) throws SQLException {

        List<Ubicacion> lista = new ArrayList<>();

        try (CallableStatement cs = cn.prepareCall(
                "{call OBTENER_REGIONES(?)}")) {

            cs.registerOutParameter(1, Types.REF_CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                while (rs.next()) {
                    lista.add(new Ubicacion(
                            rs.getInt("ID"),
                            rs.getString("NOMBRE")
                    ));
                }
            }
        }

        return lista;
    }

    public List<Ubicacion> obtenerDepartamentos(
            Connection cn, int idRegion) throws SQLException {

        List<Ubicacion> lista = new ArrayList<>();

        try (CallableStatement cs = cn.prepareCall(
                "{call OBTENER_DEPARTAMENTOS(?, ?)}")) {

            cs.setInt(1, idRegion);
            cs.registerOutParameter(2, Types.REF_CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(2)) {

                while (rs.next()) {
                    lista.add(new Ubicacion(
                            rs.getInt("ID"),
                            rs.getString("NOMBRE")
                    ));
                }
            }
        }

        return lista;
    }

    public List<Ubicacion> obtenerMunicipios(
            Connection cn, int idDepartamento) throws SQLException {

        List<Ubicacion> lista = new ArrayList<>();

        try (CallableStatement cs = cn.prepareCall(
                "{call OBTENER_MUNICIPIOS(?, ?)}")) {

            cs.setInt(1, idDepartamento);
            cs.registerOutParameter(2, Types.REF_CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(2)) {

                while (rs.next()) {
                    lista.add(new Ubicacion(
                            rs.getInt("ID"),
                            rs.getString("NOMBRE")
                    ));
                }
            }
        }

        return lista;
    }
}
