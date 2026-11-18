
package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


public class GastosDiariosDAO {

    private List<OpcionCombo> ejecutarListado(String procedimiento) {

        List<OpcionCombo> opciones = new ArrayList<>();

        String sql = "{call PORTATIL_ENPH." + procedimiento + "(?)}";

        try (Connection cn = conexion.conectar()) {

            if (cn == null) {
                return opciones;
            }

            try (CallableStatement cs = cn.prepareCall(sql)) {

                cs.registerOutParameter(1, Types.REF_CURSOR);
                cs.execute();

                try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                    while (rs.next()) {

                        opciones.add(
                            new OpcionCombo(
                                rs.getInt("ID"),
                                rs.getString("NOMBRE")
                            )
                        );
                    }
                }
            }

        } catch (SQLException e) {

            System.out.println(
                "Error al consultar " + procedimiento + ": "
                + e.getMessage()
            );
        }

        return opciones;
    }

    public List<OpcionCombo> listarProductos() {
        return ejecutarListado("SP_LISTAR_PRODUCTOS");
    }

    public List<OpcionCombo> listarFormasAdquisicion() {
        return ejecutarListado("SP_LISTAR_FORMAS_ADQUISICION");
    }

    public List<OpcionCombo> listarFrecuenciasCompra() {
        return ejecutarListado("SP_LISTAR_FRECUENCIAS_COMPRA");
    }

    public List<OpcionCombo> listarLugaresCompra() {
        return ejecutarListado("SP_LISTAR_LUGARES_COMPRA");
    }

    public List<OpcionCombo> listarTiposMedida() {
        return ejecutarListado("SP_LISTAR_TIPOS_MEDIDA");
    }

    
public List<OpcionCombo> listarUnidadesMedida() {

    List<OpcionCombo> opciones = new ArrayList<>();

    String sql = "{call PORTATIL_ENPH.SP_LISTAR_UNIDADES_MEDIDA(?)}";

    try (Connection cn = conexion.conectar()) {

        if (cn == null) {
            return opciones;
        }

        try (CallableStatement cs = cn.prepareCall(sql)) {

            cs.registerOutParameter(1, Types.REF_CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                while (rs.next()) {

                    int idTipo = rs.getInt("ID_TIPO_MEDIDA");

                    Integer idTipoMedida = rs.wasNull()
                            ? null : idTipo;

                    opciones.add(
                        new OpcionCombo(
                            rs.getInt("ID"),
                            rs.getString("NOMBRE"),
                            idTipoMedida
                        )
                    );
                }
            }
        }

    } catch (SQLException e) {

        System.out.println(
            "Error al consultar unidades de medida: "
            + e.getMessage()
        );
    }

    return opciones;
}


    public List<OpcionCombo> listarUnidadesEquivalencia() {
        return ejecutarListado("SP_LISTAR_UNID_EQUI");
    }
    
    // Consulta las unidades de medida según el tipo seleccionado.
public List<OpcionCombo> listarUnidadesPorTipo(int idTipoMedida) {

    // Lista donde se almacenarán las unidades encontradas.
    List<OpcionCombo> opciones = new ArrayList<>();

    // Llamada al procedimiento almacenado de Oracle.
    String sql = "{call PORTATIL_ENPH.SP_LISTAR_UNIDADES_POR_TIPO(?, ?)}";

    try (Connection cn = conexion.conectar()) {

        // Comprobar que exista conexión con Oracle.
        if (cn == null) {
            return opciones;
        }

        try (CallableStatement cs = cn.prepareCall(sql)) {

            // Enviar el ID del tipo de medida seleccionado.
            cs.setInt(1, idTipoMedida);

            // Registrar el cursor que devolverá Oracle.
            cs.registerOutParameter(2, Types.REF_CURSOR);

            // Ejecutar el procedimiento.
            cs.execute();

            // Leer los resultados obtenidos.
            try (ResultSet rs = (ResultSet) cs.getObject(2)) {

                while (rs.next()) {

                    // Agregar cada unidad a la lista.
                    opciones.add(
                        new OpcionCombo(
                            rs.getInt("ID"),
                            rs.getString("NOMBRE")
                        )
                    );
                }
            }
        }

    } catch (SQLException e) {

        // Mostrar cualquier error de consulta.
        System.out.println(
            "Error al consultar unidades por tipo: "
            + e.getMessage()
        );
    }

    return opciones;
}
    
    
// Consultar el ID de la encuesta mediante el procedimiento almacenado.
public int obtenerIdEncuesta(String cedula) {

    // Llamar al procedimiento del esquema PORTATIL_ENPH.
    String sql = "{call PORTATIL_ENPH.SP_OBTENER_ID_ENCUESTA(?, ?)}";

    try (Connection cn = conexion.conectar()) {

        // Comprobar que la conexión esté disponible.
        if (cn == null) {
            return -1;
        }

        try (CallableStatement cs = cn.prepareCall(sql)) {

            // Enviar la cédula del representante.
            cs.setString(1, cedula);

            // Registrar el parámetro de salida que recibirá el ID.
            cs.registerOutParameter(2, Types.NUMERIC);

            // Ejecutar el procedimiento almacenado.
            cs.execute();

            // Recuperar el ID devuelto por Oracle.
            int idEncuesta = cs.getInt(2);

            // Comprobar que Oracle haya devuelto un valor.
            if (cs.wasNull()) {
                return -1;
            }

            return idEncuesta;
        }

    } catch (SQLException e) {

        // Mostrar cualquier error durante la ejecución.
        System.out.println("Error al obtener el ID de la encuesta: "
                + e.getMessage());

        return -1;
    }
}


public boolean guardarGastoDiario(
        int idEncuesta,
        int idProducto,
        double cantidadAdquirida,
        int idUnidadMedida,
        int idFormaAdquisicion,
        Integer idTipoMedida,
        Integer unidadMedidaEqui,
        Double cantidadMedidaEqui,
        Integer idLugarCompra,
        double valor,
        Integer idFrecuenciaCompra,
        String tipoGasto) {

    String sql = "{call PORTATIL_ENPH.SP_GUARDAR_GASTO_DIARIO(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

    try (Connection cn = conexion.conectar()) {

        if (cn == null) {
            return false;
        }

        try (CallableStatement cs = cn.prepareCall(sql)) {

            cs.setInt(1, idEncuesta);
            cs.setInt(2, idProducto);
            cs.setDouble(3, cantidadAdquirida);
            cs.setInt(4, idUnidadMedida);
            cs.setInt(5, idFormaAdquisicion);

            if (idTipoMedida == null) {
                cs.setNull(6, Types.NUMERIC);
            } else {
                cs.setInt(6, idTipoMedida);
            }

            if (unidadMedidaEqui == null) {
                cs.setNull(7, Types.NUMERIC);
            } else {
                cs.setInt(7, unidadMedidaEqui);
            }

            if (cantidadMedidaEqui == null) {
                cs.setNull(8, Types.NUMERIC);
            } else {
                cs.setDouble(8, cantidadMedidaEqui);
            }

            if (idLugarCompra == null) {
                cs.setNull(9, Types.NUMERIC);
            } else {
                cs.setInt(9, idLugarCompra);
            }

            cs.setDouble(10, valor);

            if (idFrecuenciaCompra == null) {
                cs.setNull(11, Types.NUMERIC);
            } else {
                cs.setInt(11, idFrecuenciaCompra);
            }

            cs.setString(12, tipoGasto);

            cs.execute();

            return true;
        }

    } catch (SQLException e) {

        System.out.println(
                "Error al guardar el gasto diario: "
                + e.getMessage()
        );

        return false;
    }
}


    // Ejecuta un procedimiento que inserta una opción personalizada.
    // Devuelve el ID generado por Oracle o -1 si ocurre un error.
    private int insertarOpcionPersonalizada(
            String procedimiento,
            String nombre) {

        // Preparar la llamada al procedimiento almacenado.
        String sql = "{call PORTATIL_ENPH."
                + procedimiento + "(?, ?)}";

        try (Connection cn = conexion.conectar()) {

            // Comprobar que exista conexión con Oracle.
            if (cn == null) {
                return -1;
            }

            try (CallableStatement cs = cn.prepareCall(sql)) {

                // Enviar el nombre de la opción personalizada.
                cs.setString(1, nombre.trim());

                // Registrar el parámetro que recibirá el ID generado.
                cs.registerOutParameter(2, Types.NUMERIC);

                // Ejecutar el procedimiento almacenado.
                cs.execute();

                // Recuperar el ID generado por Oracle.
                int id = cs.getInt(2);

                // Comprobar que se haya recibido un ID válido.
                if (cs.wasNull()) {
                    return -1;
                }

                return id;
            }

        } catch (SQLException e) {

            // Mostrar el error producido durante la inserción.
            System.out.println(
                    "Error al ejecutar " + procedimiento + ": "
                    + e.getMessage()
            );

            return -1;
        }
    }

    // Insertar un producto personalizado.
    public int insertarProducto(String nombre) {
        return insertarOpcionPersonalizada(
                "SP_INSERTAR_PRODUCTO",
                nombre
        );
    }

    // Insertar una unidad de medida personalizada.
    public int insertarUnidadMedida(String nombre) {
        return insertarOpcionPersonalizada(
                "SP_INSERTAR_UNIDAD_MEDIDA",
                nombre
        );
    }

    // Insertar una forma de adquisición personalizada.
    public int insertarFormaAdquisicion(String nombre) {
        return insertarOpcionPersonalizada(
                "SP_INSERTAR_FORMA_ADQUISICION",
                nombre
        );
    }

    // Insertar un lugar de compra personalizado.
    public int insertarLugarCompra(String nombre) {
        return insertarOpcionPersonalizada(
                "SP_INSERTAR_LUGAR_COMPRA",
                nombre
        );
    }

    

    
    // Busca una unidad existente o la inserta mediante Oracle.
private int obtenerOInsertarUnidad(
        Connection cn,
        String nombre) throws SQLException {

    String sql = "{call PORTATIL_ENPH.SP_OBTENER_O_INSERTAR_UNIDAD(?, ?)}";

    try (CallableStatement cs = cn.prepareCall(sql)) {

        cs.setString(1, nombre.trim());
        cs.registerOutParameter(2, Types.NUMERIC);

        cs.execute();

        int id = cs.getInt(2);

        if (cs.wasNull()) {
            throw new SQLException(
                    "No se obtuvo el ID de la unidad de medida."
            );
        }

        return id;
    }
}

// Busca una forma de adquisición existente o la inserta mediante Oracle.
private int obtenerOInsertarForma(
        Connection cn,
        String nombre) throws SQLException {

    String sql = "{call PORTATIL_ENPH.SP_OBTENER_O_INSERTAR_FORMA(?, ?)}";

    try (CallableStatement cs = cn.prepareCall(sql)) {

        cs.setString(1, nombre.trim());
        cs.registerOutParameter(2, Types.NUMERIC);

        cs.execute();

        int id = cs.getInt(2);

        if (cs.wasNull()) {
            throw new SQLException(
                    "No se obtuvo el ID de la forma de adquisición."
            );
        }

        return id;
    }
}

// Busca un lugar existente o lo inserta mediante Oracle.
private int obtenerOInsertarLugar(
        Connection cn,
        String nombre) throws SQLException {

    String sql = "{call PORTATIL_ENPH.SP_OBTENER_O_INSERTAR_LUGAR(?, ?)}";

    try (CallableStatement cs = cn.prepareCall(sql)) {

        cs.setString(1, nombre.trim());
        cs.registerOutParameter(2, Types.NUMERIC);

        cs.execute();

        int id = cs.getInt(2);

        if (cs.wasNull()) {
            throw new SQLException(
                    "No se obtuvo el ID del lugar de compra."
            );
        }

        return id;
    }
}

// Busca un producto existente o lo inserta mediante Oracle.
private int obtenerOInsertarProducto(
        Connection cn,
        String nombre) throws SQLException {

    String sql = "{call PORTATIL_ENPH.SP_OBTENER_O_INSERTAR_PRODUCTO(?, ?)}";

    try (CallableStatement cs = cn.prepareCall(sql)) {

        cs.setString(1, nombre.trim());
        cs.registerOutParameter(2, Types.NUMERIC);

        cs.execute();

        int id = cs.getInt(2);

        if (cs.wasNull()) {
            throw new SQLException(
                    "No se obtuvo el ID del producto."
            );
        }

        return id;
    }
}

    // Inserta una opción personalizada utilizando la misma conexión.
    private int insertarOpcion(
            Connection cn,
            String procedimiento,
            String nombre) throws SQLException {

        String sql = "{call PORTATIL_ENPH."
                + procedimiento + "(?, ?)}";

        try (CallableStatement cs = cn.prepareCall(sql)) {

            cs.setString(1, nombre.trim());
            cs.registerOutParameter(2, Types.NUMERIC);

            cs.execute();

            int id = cs.getInt(2);

            if (cs.wasNull()) {
                throw new SQLException(
                        "No se obtuvo el ID de la opción insertada."
                );
            }

            return id;
        }
    }

    // Obtiene el ID existente o inserta la opción personalizada mediante Oracle.
private Integer resolverId(
        Connection cn,
        OpcionCombo opcion,
        String tabla,
        String procedimiento) throws SQLException {

    if (opcion == null) {
        return null;
    }

    // Si la opción ya existe, utilizar su ID.
    if (opcion.getId() > 0) {
        return opcion.getId();
    }

    // Si es una opción personalizada, resolverla mediante Oracle.
    if (opcion.getId() == -2) {

        String nombre = opcion.getNombre().trim();

        if (nombre.isEmpty()) {
            throw new SQLException(
                    "El nombre de la opción personalizada está vacío."
            );
        }

        // Resolver las unidades mediante Oracle.
        if (tabla.equalsIgnoreCase("UNIDAD_MEDIDA")) {
            return obtenerOInsertarUnidad(cn, nombre);
        }

        // Resolver las formas de adquisición mediante Oracle.
        if (tabla.equalsIgnoreCase("FORMA_ADQUISICION")) {
            return obtenerOInsertarForma(cn, nombre);
        }

        // Resolver los lugares de compra mediante Oracle.
        if (tabla.equalsIgnoreCase("LUGAR_COMPRA_SERVICIO")) {
            return obtenerOInsertarLugar(cn, nombre);
        }

        // Resolver los productos mediante Oracle.
        if (tabla.equalsIgnoreCase("PRODUCTO")) {
            return obtenerOInsertarProducto(cn, nombre);
        }

        // No ejecutar consultas SQL para tablas sin procedimiento definido.
        throw new SQLException(
                "No existe un procedimiento definido para resolver la opción: "
                + tabla
        );
    }

    return null;
}

    // Guarda todos los gastos dentro de una sola transacción.
    public boolean guardarGastosDiarios(
            int idEncuesta,
            List<GastoDiarioTemporal> gastos) {

        String sql = "{call PORTATIL_ENPH.SP_GUARDAR_GASTO_DIARIO("
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection cn = conexion.conectar()) {

            if (cn == null) {
                return false;
            }

            try {

                // Iniciar la transacción.
                cn.setAutoCommit(false);

                for (GastoDiarioTemporal gasto : gastos) {

                    // Resolver producto.
                    Integer idProducto = resolverId(
                            cn,
                            gasto.getProducto(),
                            "PRODUCTO",
                            "SP_INSERTAR_PRODUCTO"
                    );

                    // Resolver unidad de medida.
                    Integer idUnidad = resolverId(
                            cn,
                            gasto.getUnidadMedida(),
                            "UNIDAD_MEDIDA",
                            "SP_INSERTAR_UNIDAD_MEDIDA"
                    );

                    // Resolver forma de adquisición.
                    Integer idForma = resolverId(
                            cn,
                            gasto.getFormaAdquisicion(),
                            "FORMA_ADQUISICION",
                            "SP_INSERTAR_FORMA_ADQUISICION"
                    );

                    // Resolver lugar de compra, cuando corresponda.
                    Integer idLugar = resolverId(
                            cn,
                            gasto.getLugarCompra(),
                            "LUGAR_COMPRA_SERVICIO",
                            "SP_INSERTAR_LUGAR_COMPRA"
                    );

                    // Obtener los datos de equivalencia.
                    OpcionCombo tipo = gasto.getTipoMedida();
                    OpcionCombo unidadEqui =
                            gasto.getUnidadEquivalencia();

                    Integer idTipo = tipo != null
                            && tipo.getId() > 0
                            ? tipo.getId() : null;

                    Integer idUnidadEqui =
                            unidadEqui != null
                            && unidadEqui.getId() > 0
                            ? unidadEqui.getId() : null;

                    Double cantidadEqui =
                            gasto.getCantidadEquivalencia();

                    // Resolver frecuencia de compra.
                    OpcionCombo frecuencia =
                            gasto.getFrecuenciaCompra();

                    Integer idFrecuencia =
                            frecuencia != null
                            && frecuencia.getId() > 0
                            ? frecuencia.getId() : null;

                    // Ejecutar el procedimiento de guardado.
                    try (CallableStatement cs =
                            cn.prepareCall(sql)) {

                        cs.setInt(1, idEncuesta);
                        cs.setInt(2, idProducto);
                        cs.setDouble(
                                3,
                                gasto.getCantidadAdquirida()
                        );
                        cs.setInt(4, idUnidad);
                        cs.setInt(5, idForma);

                        if (idTipo == null) {
                            cs.setNull(6, Types.NUMERIC);
                        } else {
                            cs.setInt(6, idTipo);
                        }

                        if (idUnidadEqui == null) {
                            cs.setNull(7, Types.NUMERIC);
                        } else {
                            cs.setInt(7, idUnidadEqui);
                        }

                        if (cantidadEqui == null) {
                            cs.setNull(8, Types.NUMERIC);
                        } else {
                            cs.setDouble(8, cantidadEqui);
                        }

                        if (idLugar == null) {
                            cs.setNull(9, Types.NUMERIC);
                        } else {
                            cs.setInt(9, idLugar);
                        }

                        cs.setDouble(10, gasto.getValor());

                        if (idFrecuencia == null) {
                            cs.setNull(11, Types.NUMERIC);
                        } else {
                            cs.setInt(11, idFrecuencia);
                        }

                        cs.setString(12, gasto.getTipoGasto());

                        cs.execute();
                    }
                }

                // Confirmar todos los registros.
                cn.commit();

                return true;

            } catch (SQLException e) {

                // Deshacer todos los cambios si algo falla.
                cn.rollback();

                System.out.println(
                        "Error al guardar los gastos: "
                        + e.getMessage()
                );

                return false;

            } finally {

                cn.setAutoCommit(true);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error en la conexión al guardar los gastos: "
                    + e.getMessage()
            );

            return false;
        }
    }
    
    public boolean actualizarGastoDiario(
        int idGasto,
        OpcionCombo producto,
        double cantidadAdquirida,
        OpcionCombo unidadMedida,
        OpcionCombo formaAdquisicion,
        OpcionCombo unidadMedidaEqui,
        Double cantidadMedidaEqui,
        OpcionCombo lugarCompra,
        double valor,
        OpcionCombo frecuenciaCompra,
        String tipoGasto) {

    String sql = "{call PORTATIL_ENPH.SP_ACTUALIZAR_GASTO_DIARIO("
            + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

    try (Connection cn = conexion.conectar()) {

        if (cn == null) {
            return false;
        }

        try {
            cn.setAutoCommit(false);

            // Resolver el producto: buscarlo o insertarlo si es nuevo.
            Integer idProducto = resolverId(
                    cn,
                    producto,
                    "PRODUCTO",
                    "SP_INSERTAR_PRODUCTO"
            );

            // Resolver la unidad adquirida.
            Integer idUnidad = resolverId(
                    cn,
                    unidadMedida,
                    "UNIDAD_MEDIDA",
                    "SP_INSERTAR_UNIDAD_MEDIDA"
            );

            // Resolver la forma de adquisición.
            Integer idForma = resolverId(
                    cn,
                    formaAdquisicion,
                    "FORMA_ADQUISICION",
                    "SP_INSERTAR_FORMA_ADQUISICION"
            );

            // Resolver el lugar, si corresponde.
            Integer idLugar = resolverId(
                    cn,
                    lugarCompra,
                    "LUGAR_COMPRA_SERVICIO",
                    "SP_INSERTAR_LUGAR_COMPRA"
            );

            // La unidad de equivalencia debe ser una opción existente.
            Integer idUnidadEquivalencia = null;

            if (unidadMedidaEqui != null) {
                if (unidadMedidaEqui.getId() <= 0) {
                    throw new SQLException(
                            "La unidad de equivalencia seleccionada "
                            + "no tiene un ID válido."
                    );
                }

                idUnidadEquivalencia = unidadMedidaEqui.getId();
            }

            // Ejecutar el procedimiento de actualización.
            try (CallableStatement cs = cn.prepareCall(sql)) {

                cs.setInt(1, idGasto);

                cs.setInt(2, idProducto);

                cs.setDouble(3, cantidadAdquirida);

                cs.setInt(4, idUnidad);

                cs.setInt(5, idForma);

                if (idUnidadEquivalencia == null) {
                    cs.setNull(6, Types.NUMERIC);
                } else {
                    cs.setInt(6, idUnidadEquivalencia);
                }

                if (cantidadMedidaEqui == null) {
                    cs.setNull(7, Types.NUMERIC);
                } else {
                    cs.setDouble(7, cantidadMedidaEqui);
                }

                if (idLugar == null) {
                    cs.setNull(8, Types.NUMERIC);
                } else {
                    cs.setInt(8, idLugar);
                }

                cs.setDouble(9, valor);

                Integer idFrecuencia = null;

                if (frecuenciaCompra != null) {
                    if (frecuenciaCompra.getId() <= 0) {
                        throw new SQLException(
                                "La frecuencia de compra "
                                + "no tiene un ID válido."
                        );
                    }

                    idFrecuencia = frecuenciaCompra.getId();
                }

                if (idFrecuencia == null) {
                    cs.setNull(10, Types.NUMERIC);
                } else {
                    cs.setInt(10, idFrecuencia);
                }

                cs.setString(11, tipoGasto);

                cs.execute();
            }

            // Confirmar tanto las opciones nuevas como la actualización.
            cn.commit();

            return true;

        } catch (SQLException e) {

            // Si falla cualquier operación, deshacer toda la transacción.
            cn.rollback();

            System.out.println(
                    "Error al actualizar el gasto diario: "
                    + e.getMessage()
            );

            return false;

        } finally {
            cn.setAutoCommit(true);
        }

    } catch (SQLException e) {

        System.out.println(
                "Error en la conexión al actualizar el gasto: "
                + e.getMessage()
        );

        return false;
    }
} 

}
