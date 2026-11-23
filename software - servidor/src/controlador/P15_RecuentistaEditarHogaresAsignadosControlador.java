
package controlador;

import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

import modelo.SesionUsuario;
import modelo.ResultadoLogin;
import modelo.P15_RecuentistaEditarHogaresAsignadosDAO;
import vista.P15_RecuentistaEditarHogaresAsignados1;

public class P15_RecuentistaEditarHogaresAsignadosControlador {

    private final P15_RecuentistaEditarHogaresAsignados1 vista;
    private final P15_RecuentistaEditarHogaresAsignadosDAO dao;

    private final String cedulaRecuentista;

    public P15_RecuentistaEditarHogaresAsignadosControlador(
            P15_RecuentistaEditarHogaresAsignados1 vista) {

        this.vista = vista;
        this.dao = new P15_RecuentistaEditarHogaresAsignadosDAO();

        ResultadoLogin usuario = SesionUsuario.obtenerUsuario();

        if (usuario == null) {
            throw new IllegalStateException(
                    "No existe un usuario con sesión iniciada."
            );
        }

        this.cedulaRecuentista = usuario.getCedula();
    }

    public void iniciar() {

        try {

            // Cargar encuestadores en la primera tabla
            List<Object[]> encuestadores =
                    dao.listarEncuestadores();

            vista.mostrarEncuestadores(encuestadores);

            // Cargar hogares disponibles en la segunda tabla
            List<Object[]> hogares =
                    dao.listarHogaresRecuentista(
                            cedulaRecuentista
                    );

            vista.mostrarHogaresDisponibles(hogares);

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "Error al cargar la información:\n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    
public void guardarAsignacion() {

    String cedulaEncuestador =
            vista.obtenerCedulaEncuestadorSeleccionado();

    if (cedulaEncuestador == null) {

        JOptionPane.showMessageDialog(
                vista,
                "Seleccione un encuestador.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
        );

        return;
    }

    List<String> hogares =
            vista.obtenerHogaresSeleccionados();

    if (hogares.isEmpty()) {

        JOptionPane.showMessageDialog(
                vista,
                "Seleccione al menos un hogar.",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
        );

        return;
    }

    int respuesta = JOptionPane.showConfirmDialog(
            vista,
            "¿Desea asignar " + hogares.size()
            + " hogar(es) al encuestador seleccionado?",
            "Confirmar asignación",
            JOptionPane.YES_NO_OPTION
    );

    if (respuesta != JOptionPane.YES_OPTION) {
        return;
    }

    try {

        dao.asignarHogares(
                cedulaRecuentista,
                cedulaEncuestador,
                hogares
        );

        JOptionPane.showMessageDialog(
                vista,
                "Los hogares se asignaron correctamente."
        );

        iniciar();

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
                vista,
                e.getMessage(),
                "Error al asignar hogares",
                JOptionPane.ERROR_MESSAGE
        );
    }
}

}
