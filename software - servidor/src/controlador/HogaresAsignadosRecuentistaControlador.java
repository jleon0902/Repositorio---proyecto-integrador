
package controlador;

import modelo.HogaresAsignadosRecuentistaDAO;
import modelo.SesionUsuario;
import modelo.ResultadoLogin;
import vista.P14_RecuentistaHogaresAsignados1;

import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

public class HogaresAsignadosRecuentistaControlador {

    private P14_RecuentistaHogaresAsignados1 vista;

    private HogaresAsignadosRecuentistaDAO dao;

    private String cedulaRecuentista;

    public HogaresAsignadosRecuentistaControlador(
            P14_RecuentistaHogaresAsignados1 vista
    ) {

        this.vista = vista;

        this.dao = new HogaresAsignadosRecuentistaDAO();

        // Obtener el usuario que inició sesión
        ResultadoLogin usuario = SesionUsuario.obtenerUsuario();

        if (usuario == null) {

            JOptionPane.showMessageDialog(
                    vista,
                    "No se encontró información del usuario.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // Verificar que el usuario sea recuentista
        if (!"RECUENTISTA".equalsIgnoreCase(
                usuario.getNombreRol().trim()
        )) {

            JOptionPane.showMessageDialog(
                    vista,
                    "El usuario actual no es un recuentista.",
                    "Acceso denegado",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // Obtener la cédula del recuentista
        cedulaRecuentista = usuario.getCedula();

        // Cargar los hogares
        cargarHogaresAsignados();
    }

    public void cargarHogaresAsignados() {

        try {

            if (!SesionUsuario.estaActiva()) {

                JOptionPane.showMessageDialog(
                        vista,
                        "No existe una conexión activa con Oracle.",
                        "Error de conexión",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            List<Object[]> hogares =
                    dao.listarHogaresAsignados(cedulaRecuentista);

            vista.mostrarHogaresAsignados(hogares);

            if (hogares.isEmpty()) {

                JOptionPane.showMessageDialog(
                        vista,
                        "Este recuentista todavía no tiene hogares asignados a encuestadores.",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "Error al consultar los hogares: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    
public void eliminarAsignacion(int numeroVivienda) {

    int respuesta = JOptionPane.showConfirmDialog(
            vista,
            "¿Está seguro de que desea eliminar la asignación de este hogar?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
    );

    if (respuesta != JOptionPane.YES_OPTION) {
        return;
    }

    try {

        String mensaje = dao.eliminarAsignacion(
                cedulaRecuentista,
                numeroVivienda
        );

        if ("ASIGNACION_ELIMINADA".equals(mensaje)) {

            JOptionPane.showMessageDialog(
                    vista,
                    "La asignación del hogar se eliminó correctamente.",
                    "Eliminación exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Actualizar la tabla
            cargarHogaresAsignados();

        } else {

            JOptionPane.showMessageDialog(
                    vista,
                    mensaje,
                    "No se pudo eliminar",
                    JOptionPane.WARNING_MESSAGE
            );
        }

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
                vista,
                "Error al eliminar la asignación: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}

    
    
}
