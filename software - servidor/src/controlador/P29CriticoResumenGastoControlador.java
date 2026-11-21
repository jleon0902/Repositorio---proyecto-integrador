
package controlador;

import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;

import modelo.P29CriticoResumenGastoDAO;
import vista.P29_CriticoResumenGasto;

public class P29CriticoResumenGastoControlador {

    private final P29_CriticoResumenGasto vista;
    private final P29CriticoResumenGastoDAO dao;

    public P29CriticoResumenGastoControlador(
            P29_CriticoResumenGasto vista,
            P29CriticoResumenGastoDAO dao) {

        this.vista = vista;
        this.dao = dao;

        this.vista.setControlador(this);
    }

    // CARGAR INFORME RESUMEN DEL GASTO
 
public void cargarResumenGasto() {

    try {

        // Consultar los datos mediante el DAO.
        double gastoTotal = dao.obtenerGastoTotal();
        double gastoPromedioDia = dao.obtenerGastoPromedioDia();
        double promedioProductos = dao.obtenerPromedioProductos();

        String diaMayor = dao.obtenerDiaMayor();
        String diaMenor = dao.obtenerDiaMenor();

        double porcentajeHogar = dao.obtenerPorcentajeHogar();
        double porcentajePersonal = dao.obtenerPorcentajePersonal();

        double promedioHogar = dao.obtenerPromedioHogar();
        double promedioPersonal = dao.obtenerPromedioPersonal();

        // Mostrar resultados en los campos de texto.
        vista.getTxtGastoTotal().setText(
                String.format("%.2f", gastoTotal)
        );

        vista.getTxtGastoPromedioDia().setText(
                String.format("%.2f", gastoPromedioDia)
        );

        vista.getTxtCantidadPromedioProductos().setText(
                String.format("%.2f", promedioProductos)
        );

        vista.getTxtDiaMayorGasto().setText(diaMayor);

        vista.getTxtDiaMenorGasto().setText(diaMenor);

        vista.getJtfValorProHogar().setText(
                String.format("%.2f", promedioHogar)
        );

        vista.getJtfValorProPersonal().setText(
                String.format("%.2f", promedioPersonal)
        );

        // Mostrar gráfica de distribución.
        vista.mostrarGrafica(
                porcentajeHogar,
                porcentajePersonal
        );

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
                vista,
                "Error al consultar el resumen del gasto: "
                + e.getMessage(),
                "Error de consulta",
                JOptionPane.ERROR_MESSAGE
        );
    }
}


    // VOLVER
    public void volver() {

        int respuesta = JOptionPane.showConfirmDialog(
                vista,
                "¿Está seguro de que desea volver?",
                "Confirmar regreso",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            vista.cerrarVentana();
        }
    }
    
    
}
