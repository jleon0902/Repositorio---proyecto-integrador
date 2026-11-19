
package controlador;

import java.util.List;
import javax.swing.JOptionPane;

import modelo.SesionUsuario;
import modelo.GastoDiarioConsulta;
import modelo.GastosDiariosConsultaDAO;
import modelo.GastosDiariosDAO;

import vista.P3_MenuPrincipal_Representante;
import vista.P5_RevisarEncuesta_Representante;
import vista.P6_EditarEncuesta_Representante;

public class RevisarEncuestaControlador {

    private final P5_RevisarEncuesta_Representante vista;
    private final GastosDiariosConsultaDAO consultaDAO;
    private final GastosDiariosDAO gastosDAO;

    private List<GastoDiarioConsulta> gastos;
    private int indiceActual;
    private GastoDiarioConsulta gastoActual;
    private final NavegacionRepresentante navegacion;
    
public RevisarEncuestaControlador(
        P5_RevisarEncuesta_Representante vista,
        NavegacionRepresentante navegacion) {

    this.vista = vista;
    this.navegacion = navegacion;
    this.consultaDAO = new GastosDiariosConsultaDAO();
    this.gastosDAO = new GastosDiariosDAO();
    this.indiceActual = 0;

    if (!validarSesion()) {

        JOptionPane.showMessageDialog(
                vista,
                "No tienes autorización para acceder a esta pantalla.",
                "Acceso denegado",
                JOptionPane.WARNING_MESSAGE
        );

        vista.dispose();
        return;
    }

    vista.mostrarNombreRepresentante(
        SesionUsuario.getNombre() + " "
        + SesionUsuario.getApellido()
);

// Consultar y mostrar el día actual de la encuesta.
int dia = gastosDAO.obtenerDiaEncuesta(
        SesionUsuario.getCedula()
);

vista.mostrarDiaEncuesta(dia);

cargarGastos();
}
    

    private boolean validarSesion() {

        return SesionUsuario.getTipoPersona() != null
                && "REPRESENTANTE".equalsIgnoreCase(
                        SesionUsuario.getTipoPersona()
                )
                && SesionUsuario.getCedula() != null
                && !SesionUsuario.getCedula().trim().isEmpty();
    }

    private void cargarGastos() {

        gastos = consultaDAO.listarPorEncuesta(
                SesionUsuario.getCedula()
        );

        if (gastos == null || gastos.isEmpty()) {

            vista.mostrarSinRegistros();

            return;
        }

        indiceActual = 0;

        mostrarGastoActual();
    }

    private void mostrarGastoActual() {

    gastoActual = gastos.get(indiceActual);

    vista.mostrarGasto(
            gastoActual,
            indiceActual + 1,
            gastos.size()
    );
}
    public void anterior() {

    if (indiceActual > 0) {
        indiceActual--;
        mostrarGastoActual();
    }
}

public void continuar() {

    if (indiceActual < gastos.size() - 1) {
        indiceActual++;
        mostrarGastoActual();
    }
}

    public void cancelar() {
    // Recupera el menú principal que ya conserva la navegación.
    navegacion.volverMenuPrincipal();

    // Cierra la pantalla actual.
    vista.dispose();
    }
    

    public void editar() {

    P6_EditarEncuesta_Representante pantalla =
        new P6_EditarEncuesta_Representante();

EditarEncuestaControlador controlador =
        new EditarEncuestaControlador(
                pantalla,
                gastoActual,
                navegacion
        );

pantalla.setVisible(true);

vista.dispose();
}
    
    public void eliminar() {

    // Comprobar que exista un gasto seleccionado.
    if (gastoActual == null) {
        return;
    }

    // Solicitar confirmación antes de eliminar.
    if (!vista.confirmarEliminacion()) {
        return;
    }

    // Guardar el ID del gasto que se va a eliminar.
    int idGasto = gastoActual.getId();

    // Ejecutar la eliminación mediante el DAO.
    boolean eliminado = consultaDAO.eliminarGastoDiario(idGasto);

    if (eliminado) {

        JOptionPane.showMessageDialog(
                vista,
                "El gasto diario se eliminó correctamente.",
                "Eliminación exitosa",
                JOptionPane.INFORMATION_MESSAGE
        );

        // Volver a consultar los registros.
        gastos = consultaDAO.listarPorEncuesta(
                SesionUsuario.getCedula()
        );

        // Si ya no existen registros, limpiar la pantalla.
        if (gastos == null || gastos.isEmpty()) {

            gastoActual = null;
            indiceActual = 0;

            vista.mostrarSinRegistros();

            return;
        }

        // Si se eliminó el último registro,
        // retroceder al registro anterior.
        if (indiceActual >= gastos.size()) {
            indiceActual = gastos.size() - 1;
        }

        // Mostrar el registro que ahora ocupa esa posición.
        mostrarGastoActual();

    } else {

        JOptionPane.showMessageDialog(
                vista,
                "No fue posible eliminar el gasto diario.",
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
}
