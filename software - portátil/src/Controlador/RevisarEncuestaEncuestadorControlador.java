package controlador;

import modelo.RevisarEncuestaDAO;
import modelo.DatosEncuesta;
import modelo.DatosGastoDiario;
import vista.P8_RepresentanteEncargado_Encuestador;
import vista.P9_RevisarEncuesta_Encuestador;

import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RevisarEncuestaEncuestadorControlador {

    private P8_RepresentanteEncargado_Encuestador vistaP8;
    private P9_RevisarEncuesta_Encuestador vistaP9;

    private RevisarEncuestaDAO dao;

    private String cedulaRepresentante;
    private LocalDate fechaInicio;

    private int diaActual;
    private int diaSeleccionado;
    // Almacena los gastos correspondientes al día que se está revisando.
    // Se utiliza para recuperar todos los datos del gasto seleccionado.
    private List<DatosGastoDiario> gastosDelDia;

    private final DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Constructor de P8: se conserva su funcionamiento.
    public RevisarEncuestaEncuestadorControlador(
            P8_RepresentanteEncargado_Encuestador vista) {

        this.vistaP8 = vista;
        this.dao = new RevisarEncuestaDAO();

        cargarDatos();
    }

    // Constructor de P9: utiliza el mismo controlador.
    public RevisarEncuestaEncuestadorControlador(
            P9_RevisarEncuesta_Encuestador vista) {

        this.vistaP9 = vista;
        this.dao = new RevisarEncuestaDAO();

        iniciarRevision();
    }

    // Cargar información del representante en P8.
    public void cargarDatos() {

        try {

            DatosEncuesta datos = dao.consultarDatosEncuesta();

            vistaP8.mostrarDatos(datos);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    vistaP8,
                    "Error al consultar la información de la encuesta:\n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Preparar la revisión de los gastos en P9.
    private void iniciarRevision() {

        try {

            DatosEncuesta datos = dao.consultarDatosEncuesta();

            cedulaRepresentante = datos.getCedula();

            fechaInicio = LocalDate.parse(
                    datos.getFechaInicio(),
                    formato
            );

            diaActual = datos.getDia();

            diaSeleccionado = 1;

            if (diaActual == 0) {

                JOptionPane.showMessageDialog(
                        vistaP9,
                        "La encuesta todavía no ha iniciado.",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE
                );

                vistaP9.mostrarDia(0, "Sin iniciar");
                vistaP9.mostrarGastosConIds(
                        java.util.Collections.emptyList()
                );

            } else {

                cargarGastosDelDia();
            }

            configurarBotones();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    vistaP9,
                    "Error al iniciar la revisión de la encuesta:\n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        vistaP9.getBtnAnterior().addActionListener(e -> anterior());
        vistaP9.getBtnContinuar().addActionListener(e -> continuar());
        vistaP9.getBtnEliminar().addActionListener(e -> eliminarGasto());
        // Configurar la acción del botón Editar.
    vistaP9.getBtnEditar().addActionListener(e -> abrirEdicion());
    }

    // Abrir la ventana independiente para editar el gasto seleccionado.
private void abrirEdicion() {

    // Obtener el ID del gasto seleccionado en la tabla.
    Long idGasto = vistaP9.getIdGastoSeleccionado();

    // Comprobar que se haya seleccionado un registro.
    if (idGasto == null) {

        JOptionPane.showMessageDialog(
                vistaP9,
                "Seleccione un producto de la tabla para editarlo.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE
        );

        return;
    }

    // Buscar los datos del gasto seleccionado entre los registros del día.
    DatosGastoDiario gastoSeleccionado = null;

    for (DatosGastoDiario gasto : gastosDelDia) {

        if (gasto.getId() == idGasto.intValue()) {
            gastoSeleccionado = gasto;
            break;
        }
    }

    // Comprobar que se hayan encontrado los datos.
    if (gastoSeleccionado == null) {

        JOptionPane.showMessageDialog(
                vistaP9,
                "No fue posible recuperar la información del gasto.",
                "Error",
                JOptionPane.ERROR_MESSAGE
        );

        return;
    }

    // Crear la ventana de edición.
    vista.P10_EditarEncuesta_Encuestador ventana =
            new vista.P10_EditarEncuesta_Encuestador();

    // Crear el controlador independiente de edición.
    new EditarEncuestaEncuestadorControlador(
            ventana,
            vistaP9,
            gastoSeleccionado,
            idGasto
    );

    // Mostrar la ventana.
    ventana.setVisible(true);
}

// Consultar los productos del día seleccionado.
private void cargarGastosDelDia() {

    try {

        // Calcular la fecha correspondiente al día seleccionado.
        LocalDate fechaSeleccionada =
                fechaInicio.plusDays(diaSeleccionado - 1);

        // Consultar y almacenar los gastos registrados para esa fecha.
        gastosDelDia = dao.consultarGastosDia(
                cedulaRepresentante,
                fechaSeleccionada
        );

        // Actualizar la información del día en la interfaz.
        vistaP9.mostrarDia(
                diaSeleccionado,
                fechaSeleccionada.format(formato)
        );

        // Mostrar los gastos conservando sus identificadores.
        vistaP9.mostrarGastosConIds(gastosDelDia);

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                vistaP9,
                "Error al consultar los gastos diarios:\n"
                + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}

    // Retroceder un día.
    private void anterior() {

        if (diaSeleccionado > 1) {

            diaSeleccionado--;

            cargarGastosDelDia();

            configurarBotones();
        }
    }

    // Avanzar un día sin superar el día actual.
    private void continuar() {

        if (diaSeleccionado < diaActual && diaSeleccionado < 14) {

            diaSeleccionado++;

            cargarGastosDelDia();

            configurarBotones();
        }
    }

    // Activar o desactivar los botones según el día.
    private void configurarBotones() {

        vistaP9.getBtnAnterior().setEnabled(
                diaSeleccionado > 1
        );

        vistaP9.getBtnContinuar().setEnabled(
                diaSeleccionado < diaActual
                && diaSeleccionado < 14
        );
    }

    // Eliminar el gasto seleccionado.
    private void eliminarGasto() {

        Long idGasto = vistaP9.getIdGastoSeleccionado();

        if (idGasto == null) {

            JOptionPane.showMessageDialog(
                    vistaP9,
                    "Seleccione un producto de la tabla.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
                vistaP9,
                "¿Está seguro de que desea eliminar este producto?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta == JOptionPane.YES_OPTION) {

            try {

                dao.eliminarGastoDiario(idGasto);

                cargarGastosDelDia();

                JOptionPane.showMessageDialog(
                        vistaP9,
                        "El producto fue eliminado correctamente.",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (Exception e) {

                JOptionPane.showMessageDialog(
                        vistaP9,
                        "Error al eliminar el producto:\n"
                        + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}