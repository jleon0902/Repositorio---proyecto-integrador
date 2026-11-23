
package controlador;
import modelo.HogarDAO;
import vista.P14_RecuentistaHogaresAsignados1;
import vista.P15_RecuentistaEditarHogaresAsignados1;
import javax.swing.JOptionPane;
import java.sql.SQLException;
public class P14_RecuentistaHogaresAsignadosControlador {

    private final P14_RecuentistaHogaresAsignados1 vista;
private final HogarDAO dao;

    private final String cedulaRecuentista;

    public P14_RecuentistaHogaresAsignadosControlador(
            P14_RecuentistaHogaresAsignados1 vista,
            String cedulaRecuentista) {

        this.vista = vista;
        this.cedulaRecuentista = cedulaRecuentista;
        this.dao = new HogarDAO();

        this.vista.setControlador(this);
        cargarHogaresAsignados();
    }
public void abrirEdicion(String numeroVivienda) {

    int respuesta = JOptionPane.showConfirmDialog(
            vista,
            "¿Desea editar la asignación de esta vivienda?",
            "Confirmar edición",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
    );

    if (respuesta != JOptionPane.YES_OPTION) {
        return;
    }

    String cedulaEncuestador =
            vista.obtenerCedulaEncuestadorSeleccionado();

    if (cedulaEncuestador == null) {
        JOptionPane.showMessageDialog(
                vista,
                "No se encontró la cédula del encuestador.",
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    P15_RecuentistaEditarHogaresAsignados1 ventana =
            new P15_RecuentistaEditarHogaresAsignados1(
                    cedulaRecuentista,
                    cedulaEncuestador,
                    numeroVivienda
            );

    ventana.setLocationRelativeTo(null);
    ventana.setVisible(true);
}
public void eliminarAsignacion(String numeroVivienda) {

    int respuesta = JOptionPane.showConfirmDialog(
            vista,
            "¿Está seguro de eliminar la asignación de esta vivienda?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
    );

    if (respuesta != JOptionPane.YES_OPTION) {
        return;
    }

    try {

        int numero = Integer.parseInt(numeroVivienda);

String mensaje = dao.eliminarAsignacion(
        numero,
        cedulaRecuentista
);

        if ("ASIGNACION_ELIMINADA".equals(mensaje)) {

            vista.quitarHogarDeTabla(numeroVivienda);

            JOptionPane.showMessageDialog(
                    vista,
                    "La asignación del hogar se eliminó correctamente.",
                    "Eliminación exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {

            JOptionPane.showMessageDialog(
                    vista,
                    mensaje,
                    "No se pudo eliminar la asignación",
                    JOptionPane.WARNING_MESSAGE
            );
        }

    } catch (NumberFormatException e) {

        JOptionPane.showMessageDialog(
                vista,
                "El número de vivienda no es válido.",
                "Error",
                JOptionPane.ERROR_MESSAGE
        );

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
                vista,
                "Error al comunicarse con Oracle: " + e.getMessage(),
                "Error de base de datos",
                JOptionPane.ERROR_MESSAGE
        );
    }
}


public void cargarHogaresAsignados() {

    try {

        java.util.List<Object[]> hogares =
                dao.listarHogaresAsignados(cedulaRecuentista);

        vista.mostrarHogaresAsignados(hogares);

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
                vista,
                "Error al cargar los hogares asignados: "
                + e.getMessage(),
                "Error de base de datos",
                JOptionPane.ERROR_MESSAGE
        );
    }
}


}
