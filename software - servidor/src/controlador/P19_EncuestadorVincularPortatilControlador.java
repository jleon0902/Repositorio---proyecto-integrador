
package controlador;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import java.io.File;

import modelo.P19_EncuestadorVincularPortatilDAO;
import vista.P19_EncuestadorVincularPortatil1;
import java.text.SimpleDateFormat;
import java.util.Date;

public class P19_EncuestadorVincularPortatilControlador {

    private final P19_EncuestadorVincularPortatil1 vista;
    private final P19_EncuestadorVincularPortatilDAO dao;
    private final String cedulaEncuestador;
    private long idEncuestaCreada;
    private String cedulaRepresentanteSeleccionada;
    private int idSincronizacionCreada;
private final modelo.ExportacionDAO exportacionDAO =
        new modelo.ExportacionDAO();
    public P19_EncuestadorVincularPortatilControlador(
        P19_EncuestadorVincularPortatil1 vista,
        Connection conexionUsuario,
        String cedulaEncuestador) {

    this.vista = vista;
    this.cedulaEncuestador = cedulaEncuestador;
    

    this.dao = new P19_EncuestadorVincularPortatilDAO(
            conexionUsuario
    );

    configurarEventos();
}

    
public void iniciar() {

    try {

        dao.verificarConexion();

        // Cargar representantes disponibles
        List<String> representantes =
                dao.obtenerRepresentantesSinPortatil(
                        cedulaEncuestador
                );

        vista.cargarRepresentantes(representantes);

        if (representantes.isEmpty()) {

            vista.mostrarMensaje(
                    "No tienes representantes disponibles "
                    + "para vincular a un portátil."
            );
        }

        // Cargar portátiles disponibles
        List<String> portatiles =
                dao.obtenerPortatilesDisponibles();

        vista.cargarPortatilesDisponibles(portatiles);

        if (portatiles.isEmpty()) {

            vista.mostrarMensaje(
                    "No hay portátiles disponibles para asignar."
            );
        }

    } catch (SQLException e) {

        vista.mostrarMensaje(
                "Error al cargar la información: "
                + e.getMessage()
        );
    }
}


public void configurarEventos() {

    vista.agregarAccionGuardar(this::guardarVinculacion);

    vista.agregarAccionCancelar(
        () -> vista.limpiarCampos()
    );

    vista.agregarAccionSincronizar(
        this::sincronizarDatos
    );
}

private void guardarVinculacion() {

    try {

        String representante =
                vista.obtenerRepresentanteSeleccionado();

        String portatil =
                vista.obtenerPortatilSeleccionado();

        String fechaTexto =
                vista.obtenerFechaEntrega();

        if (representante == null || portatil == null
                || fechaTexto.isEmpty()) {

            vista.mostrarMensaje(
                    "Seleccione un representante, un portátil "
                    + "e ingrese la fecha de entrega."
            );
            return;
        }

        String cedula =
                representante.split(" / ")[0].trim();

        Date fechaUtil = null;

        String[] formatos = {"dd/MM/yyyy", "dd-MM-yyyy"};

        for (String formato : formatos) {
            try {
                SimpleDateFormat sdf =
                        new SimpleDateFormat(formato);
                sdf.setLenient(false);
                fechaUtil = sdf.parse(fechaTexto);
                break;
            } catch (java.text.ParseException ex) {
                // Probar siguiente formato.
            }
        }

        if (fechaUtil == null) {
            vista.mostrarMensaje(
                    "Fecha inválida. Utilice DD/MM/AAAA."
            );
            return;
        }

        java.util.Calendar fecha =
                java.util.Calendar.getInstance();

        fecha.setTime(fechaUtil);
        fecha.set(java.util.Calendar.HOUR_OF_DAY, 0);
        fecha.set(java.util.Calendar.MINUTE, 0);
        fecha.set(java.util.Calendar.SECOND, 0);
        fecha.set(java.util.Calendar.MILLISECOND, 0);

        java.util.Calendar hoy =
                java.util.Calendar.getInstance();

        hoy.set(java.util.Calendar.HOUR_OF_DAY, 0);
        hoy.set(java.util.Calendar.MINUTE, 0);
        hoy.set(java.util.Calendar.SECOND, 0);
        hoy.set(java.util.Calendar.MILLISECOND, 0);

        if (fecha.before(hoy)) {
            vista.mostrarMensaje(
                    "La fecha no puede ser anterior al día actual."
            );
            return;
        }

        java.sql.Date fechaSQL =
                new java.sql.Date(fechaUtil.getTime());

long idEncuesta = dao.vincularPortatil(
        cedula,
        portatil,
        fechaSQL
);

        idEncuestaCreada = idEncuesta;
        cedulaRepresentanteSeleccionada = cedula;

        idSincronizacionCreada = dao.prepararExportacion(
                cedula,
                cedulaEncuestador,
                idEncuesta
        );

vista.mostrarMensaje(
        "Vinculación guardada correctamente.\n"
        + "ID de encuesta: " + idEncuesta
        + "\nID de sincronización: "
        + idSincronizacionCreada
);

sincronizarDatos();

    } catch (SQLException e) {

        vista.mostrarMensaje(
                "Error al guardar la vinculación: "
                + e.getMessage()
        );
    }
}



private void sincronizarDatos() {

    if (idSincronizacionCreada <= 0) {

        vista.mostrarMensaje(
            "Primero debes guardar una vinculación."
        );

        return;
    }

    try {

        // Abrir el explorador desde la pantalla 29
        File carpetaDestino = vista.seleccionarCarpetaDestino();

        if (carpetaDestino == null) {
            return;
        }

        // Ejecutar la exportación del archivo DMP
        File archivoGenerado = exportacionDAO.exportar(
            idSincronizacionCreada,
            carpetaDestino
        );

        vista.mostrarMensaje(
            "¡EXPORTACIÓN EXITOSA!\n\n"
            + "ID de sincronización: " + idSincronizacionCreada
            + "\n\nArchivo generado:\n"
            + archivoGenerado.getAbsolutePath()
        );

    } catch (Exception e) {

        vista.mostrarMensaje(
            "No fue posible realizar la exportación:\n"
            + e.getMessage()
        );

        e.printStackTrace();
    }
}

}
