
package controlador;

import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.P31CriticoProductosConsumidosDAO;
import vista.P31_CriticoProductosConsumidos1;

public class P31CriticoProductosConsumidosControlador {

    private final P31_CriticoProductosConsumidos1 vista;
    private final P31CriticoProductosConsumidosDAO dao;

    public P31CriticoProductosConsumidosControlador(
            P31_CriticoProductosConsumidos1 vista,
            P31CriticoProductosConsumidosDAO dao)
            throws SQLException {

        this.vista = vista;
        this.dao = dao;

        this.dao.obtenerConexion();
    }

    public List<Object[]> obtenerProductosMasConsumidos()
            throws SQLException {

        return dao.obtenerProductosMasConsumidos();
    }

    public List<Object[]> obtenerProductosMenosConsumidos()
            throws SQLException {

        return dao.obtenerProductosMenosConsumidos();
    }

    public void cargarProductosConsumidos() {

        try {

            List<Object[]> productosMas =
                    dao.obtenerProductosMasConsumidos();

            List<Object[]> productosMenos =
                    dao.obtenerProductosMenosConsumidos();

            DefaultTableModel modeloMas = new DefaultTableModel(
                new Object[]{
                    "Nombre del producto",
                    "Total adquirido",
                    "Frecuencia de compra"
                }, 0
            ) {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };

            DefaultTableModel modeloMenos = new DefaultTableModel(
                new Object[]{
                    "Nombre del producto",
                    "Total adquirido",
                    "Frecuencia de compra"
                }, 0
            ) {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };

            for (Object[] fila : productosMas) {
                modeloMas.addRow(fila);
            }

            for (Object[] fila : productosMenos) {
                modeloMenos.addRow(fila);
            }

            vista.mostrarProductosMas(modeloMas);
            vista.mostrarProductosMenos(modeloMenos);

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                vista,
                "Error al cargar los productos consumidos:\n"
                + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
