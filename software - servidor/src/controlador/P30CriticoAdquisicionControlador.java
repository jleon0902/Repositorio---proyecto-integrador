
package controlador;

import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

import modelo.P30CriticoAdquisicionDAO;
import vista.P30_CriticoAdquisicion1;

public class P30CriticoAdquisicionControlador {

    private final P30_CriticoAdquisicion1 vista;
    private final P30CriticoAdquisicionDAO dao;

    public P30CriticoAdquisicionControlador(
            P30_CriticoAdquisicion1 vista,
            P30CriticoAdquisicionDAO dao) {

        this.vista = vista;
        this.dao = dao;
    }

    // COMPROBAR CONEXION
    public void comprobarConexion() {

        try {

            if (dao.verificarConexion()) {

                vista.mostrarMensaje(
                        "La conexión con Oracle sigue activa."
                );

            } else {

                vista.mostrarMensaje(
                        "La conexión con Oracle no está activa."
                );
            }

        } catch (SQLException e) {

            vista.mostrarMensaje(
                    "Error al comprobar la conexión: "
                    + e.getMessage()
            );
        }
    }

    // CONSULTAR PORCENTAJES DE ADQUISICION
    public void cargarPorcentajesAdquisicion() {

        try {

            List<String[]> resultados =
                    dao.obtenerPorcentajesAdquisicion();

            vista.mostrarPorcentajesAdquisicion(resultados);

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "Error al consultar los porcentajes: "
                    + e.getMessage()
            );
        }
    }

    // CONSULTAR LUGARES MAS CONCURRIDOS
    public void cargarLugaresMas(
            int estratoMin, int estratoMax) {

        try {

            List<String[]> resultados =
                    dao.obtenerLugaresMas(estratoMin, estratoMax);

            vista.mostrarLugaresMas(
                    resultados, estratoMin, estratoMax
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "Error al consultar los lugares más concurridos: "
                    + e.getMessage()
            );
        }
    }

    // CONSULTAR LUGARES MENOS CONCURRIDOS
    public void cargarLugarMenos(
            int estratoMin, int estratoMax) {

        try {

            List<String[]> resultados =
                    dao.obtenerLugarMenos(estratoMin, estratoMax);

            vista.mostrarLugarMenos(
                    resultados, estratoMin, estratoMax
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "Error al consultar los lugares menos concurridos: "
                    + e.getMessage()
            );
        }
    }

    // CARGAR TODOS LOS DATOS DE P30
    public void cargarInformacion() {

        cargarPorcentajesAdquisicion();

        cargarLugaresMas(1, 2);
        cargarLugarMenos(1, 2);

        cargarLugaresMas(3, 4);
        cargarLugarMenos(3, 4);

        cargarLugaresMas(5, 6);
        cargarLugarMenos(5, 6);
    }
}
