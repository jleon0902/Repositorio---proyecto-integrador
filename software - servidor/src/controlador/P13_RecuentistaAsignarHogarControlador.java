
package controlador;

import java.sql.SQLException;
import java.util.List;

import modelo.HogarAsignarDTO;
import modelo.P13_RecuentistaAsignarHogarDAO;
import modelo.P13_EncuestadorDAO;
import modelo.P13_EncuestadorDTO;
import modelo.SesionUsuario;
import modelo.ResultadoLogin;
import vista.P13_RecuentistaAsignarHogar1;

public class P13_RecuentistaAsignarHogarControlador {

    private final P13_RecuentistaAsignarHogar1 vista;
    private final P13_RecuentistaAsignarHogarDAO dao;
    private final P13_EncuestadorDAO encuestadorDAO;

    private P13_EncuestadorDTO encuestadorSeleccionado;

    public P13_RecuentistaAsignarHogarControlador(
            P13_RecuentistaAsignarHogar1 vista) {

        this.vista = vista;
        this.dao = new P13_RecuentistaAsignarHogarDAO();
        this.encuestadorDAO = new P13_EncuestadorDAO();
        vista.setControlador(this);
        vista.habilitarTablaHogares(false);

        vista.configurarSeleccionEncuestador(e -> {

            if (!e.getValueIsAdjusting()) {
                seleccionarEncuestador();
            }
        });
        
        vista.getTablaHogares().getSelectionModel()
        .addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()
                    && encuestadorSeleccionado != null) {

                controlarSeleccionHogares();
            }
        });

        cargarEncuestadores();
    }

    public void cargarEncuestadores() {

        try {

            List<P13_EncuestadorDTO> encuestadores =
                    encuestadorDAO.listarEncuestadores();

            vista.cargarEncuestadores(encuestadores);

        } catch (SQLException e) {

            vista.mostrarError(
                    "Error al consultar los encuestadores: "
                    + e.getMessage()
            );
        }
    }

    private void seleccionarEncuestador() {

        int fila = vista.getTablaEncuestadores().getSelectedRow();

        if (fila == -1) {
            encuestadorSeleccionado = null;
            vista.habilitarTablaHogares(false);
            vista.limpiarSeleccionHogares();
            return;
        }

        int filaModelo =
                vista.getTablaEncuestadores()
                     .convertRowIndexToModel(fila);

        String cedula = vista.getTablaEncuestadores()
                .getModel().getValueAt(filaModelo, 1).toString();

        int asignados = Integer.parseInt(
                vista.getTablaEncuestadores()
                     .getModel().getValueAt(filaModelo, 4).toString()
        );

        if (asignados >= 4) {

            encuestadorSeleccionado = null;

            vista.habilitarTablaHogares(false);
            vista.limpiarSeleccionHogares();

            vista.mostrarMensaje(
                    "Este encuestador ya tiene 4 hogares asignados."
            );

            return;
        }

        vista.limpiarSeleccionHogares();
vista.habilitarTablaHogares(true);

encuestadorSeleccionado = new P13_EncuestadorDTO(
        cedula,
        "",
        "",
        asignados
);

// MOSTRAR CUÁNTOS HOGARES PUEDE RECIBIR.
vista.mostrarCantidadDisponible(4 - asignados);

cargarHogares();
    }

    private void cargarHogares() {

        try {

            ResultadoLogin usuario =
                    SesionUsuario.obtenerUsuario();

            if (usuario == null) {
                vista.mostrarMensaje("No hay un usuario activo.");
                return;
            }

            List<HogarAsignarDTO> hogares =
                    dao.listarHogares(usuario.getCedula());

            vista.cargarHogares(hogares);

        } catch (SQLException e) {

            vista.mostrarMensaje(
                    "Error al consultar los hogares: "
                    + e.getMessage()
            );
        }
    }
    
    
    
    private void controlarSeleccionHogares() {

    int asignados =
            encuestadorSeleccionado.getHogaresAsignados();

    int maximoSeleccionable = 4 - asignados;

    int seleccionados =
            vista.getTablaHogares().getSelectedRowCount();

    if (seleccionados > maximoSeleccionable) {

        javax.swing.JOptionPane.showMessageDialog(
                vista,
                "No puede seleccionar más de "
                + maximoSeleccionable
                + " hogares para este encuestador."
        );

        vista.getTablaHogares().clearSelection();
    }
}
    
    public void asignarHogares() {

    if (encuestadorSeleccionado == null) {
        vista.mostrarMensaje("Primero seleccione un encuestador.");
        return;
    }

    int[] filas = vista.getTablaHogares().getSelectedRows();

    if (filas.length == 0) {
        vista.mostrarMensaje("Seleccione al menos un hogar.");
        return;
    }

    int asignados = encuestadorSeleccionado.getHogaresAsignados();

    if (asignados + filas.length > 4) {
        vista.mostrarMensaje(
                "El encuestador no puede tener más de 4 hogares."
        );
        return;
    }

    try {

        ResultadoLogin usuario = SesionUsuario.obtenerUsuario();

        if (usuario == null) {
            vista.mostrarMensaje("No hay un usuario activo.");
            return;
        }

        java.sql.Connection con = SesionUsuario.obtenerConexion();

        con.setAutoCommit(false);

        try {

            for (int fila : filas) {

                int filaModelo = vista.getTablaHogares()
                        .convertRowIndexToModel(fila);

                long numeroVivienda = Long.parseLong(
                        vista.getTablaHogares()
                                .getModel()
                                .getValueAt(filaModelo, 1)
                                .toString()
                );

                dao.asignarHogar(
                        con,
                        usuario.getCedula(),
                        encuestadorSeleccionado.getCedula(),
                        numeroVivienda
                );
            }

            con.commit();

            con.setAutoCommit(true);

            vista.mostrarMensaje(
                    "Los hogares fueron asignados correctamente."
            );

            cargarEncuestadores();
            cargarHogares();

            encuestadorSeleccionado = null;
            vista.habilitarTablaHogares(false);
            vista.limpiarSeleccionHogares();

        } catch (SQLException e) {

            con.rollback();
            con.setAutoCommit(true);

            throw e;
        }

    } catch (SQLException e) {

        vista.mostrarMensaje(
                "Error al asignar los hogares: " + e.getMessage()
        );
    }
}
}
