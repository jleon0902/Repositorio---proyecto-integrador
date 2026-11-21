
package modelo;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

public class P29CriticoResumenGastoDAO {

    private final Connection conexion;

    public P29CriticoResumenGastoDAO(Connection conexion) {

        this.conexion = Objects.requireNonNull(
                conexion,
                "La conexión recibida no puede ser nula"
        );
    }

    public Connection obtenerConexion() {
        return conexion;
    }

    // 1. GASTO TOTAL PROMEDIO
    public double obtenerGastoTotal() throws SQLException {

        return ejecutarFuncionNumerica(
                "{ ? = call FN_P29_GASTO_TOTAL() }"
        );
    }

    // 2. GASTO PROMEDIO POR DIA
    public double obtenerGastoPromedioDia() throws SQLException {

        return ejecutarFuncionNumerica(
                "{ ? = call FN_P29_GASTO_DIA() }"
        );
    }

    // 3. PROMEDIO DE PRODUCTOS
    public double obtenerPromedioProductos() throws SQLException {

        return ejecutarFuncionNumerica(
                "{ ? = call FN_P29_PROM_PRODUCTOS() }"
        );
    }

    // 4. DIA DE MAYOR GASTO
    public String obtenerDiaMayor() throws SQLException {

        return ejecutarFuncionTexto(
                "{ ? = call FN_P29_DIA_MAYOR() }"
        );
    }

    // 5. DIA DE MENOR GASTO
    public String obtenerDiaMenor() throws SQLException {

        return ejecutarFuncionTexto(
                "{ ? = call FN_P29_DIA_MENOR() }"
        );
    }

    // 6. PORCENTAJE DE GASTOS DEL HOGAR
    public double obtenerPorcentajeHogar() throws SQLException {

        return ejecutarFuncionNumerica(
                "{ ? = call FN_P29_PCT_HOGAR() }"
        );
    }

    // 7. PORCENTAJE DE GASTOS PERSONALES
    public double obtenerPorcentajePersonal() throws SQLException {

        return ejecutarFuncionNumerica(
                "{ ? = call FN_P29_PCT_PERSONAL() }"
        );
    }

    // 8. PROMEDIO DE GASTOS DEL HOGAR
    public double obtenerPromedioHogar() throws SQLException {

        return ejecutarFuncionNumerica(
                "{ ? = call FN_P29_PROM_HOGAR() }"
        );
    }

    // 9. PROMEDIO DE GASTOS PERSONALES
    public double obtenerPromedioPersonal() throws SQLException {

        return ejecutarFuncionNumerica(
                "{ ? = call FN_P29_PROM_PERSONAL() }"
        );
    }

    // EJECUTAR FUNCIONES NUMERICAS
    private double ejecutarFuncionNumerica(String llamada)
            throws SQLException {

        try (CallableStatement cs =
                conexion.prepareCall(llamada)) {

            cs.registerOutParameter(1, Types.NUMERIC);

            cs.execute();

            return cs.getDouble(1);
        }
    }

    // EJECUTAR FUNCIONES DE TEXTO
    private String ejecutarFuncionTexto(String llamada)
            throws SQLException {

        try (CallableStatement cs =
                conexion.prepareCall(llamada)) {

            cs.registerOutParameter(1, Types.VARCHAR);

            cs.execute();

            return cs.getString(1);
        }
    }
}
