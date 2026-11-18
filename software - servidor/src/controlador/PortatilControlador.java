
package controlador;

import modelo.Portatil;
import modelo.PortatilDAO;
import modelo.SesionUsuario;

import vista.P6_AdministradorPortatiles1;
import vista.P7_AdministradorEditarPortatiles1;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

public class PortatilControlador {

    private final PortatilDAO dao;

    public PortatilControlador() {
        dao = new PortatilDAO();
    }

    // OBTENER LA CONEXIÓN DE LA SESIÓN ACTUAL
    private Connection obtenerConexion() throws SQLException {
        return SesionUsuario.obtenerConexion();
    }

    // VERIFICAR CONEXIÓN
    public boolean estaConectado() {
        return SesionUsuario.estaActiva();
    }

    // LISTAR PORTÁTILES
    public List<Portatil> listarPortatiles() throws SQLException {

        Connection con = obtenerConexion();

        return dao.listarPortatiles(con);
    }

    // VERIFICAR SI ESTÁ ASIGNADO
    public boolean estaAsignado(String numeroSerie)
            throws SQLException {

        Connection con = obtenerConexion();

        return dao.estaAsignado(con, numeroSerie);
    }

    // ACTUALIZAR PORTÁTIL
    public void actualizarPortatil(
            String numeroSerieAnterior,
            String numeroSerieNuevo,
            String marca,
            String modelo) throws SQLException {

        Connection con = obtenerConexion();

        dao.actualizarPortatil(
                con,
                numeroSerieAnterior,
                numeroSerieNuevo,
                marca,
                modelo
        );
    }

    // ELIMINAR PORTÁTIL
    public void eliminarPortatil(String numeroSerie)
            throws SQLException {

        Connection con = obtenerConexion();

        dao.eliminarPortatil(con, numeroSerie);
    }

    // GUARDAR EDICIÓN
    public void guardarEdicion(
            P7_AdministradorEditarPortatiles1 vista,
            String numeroSerieOriginal) {

        if (!estaConectado()) {
            vista.actualizarEstadoBotones();

            JOptionPane.showMessageDialog(
                    vista,
                    "No existe una conexión activa con Oracle.",
                    "Sin conexión",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(
                vista,
                "¿Está seguro de guardar los cambios del portátil?",
                "Confirmar cambios",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        String numeroSerie = vista.obtenerNumeroSerie();
        String marca = vista.obtenerMarca();
        String modelo = vista.obtenerModelo();

        if (numeroSerie.isEmpty()
                || marca.isEmpty()
                || modelo.isEmpty()) {

            JOptionPane.showMessageDialog(
                    vista,
                    "Todos los campos son obligatorios.",
                    "Campos vacíos",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {

            actualizarPortatil(
                    numeroSerieOriginal,
                    numeroSerie,
                    marca,
                    modelo
            );

            JOptionPane.showMessageDialog(
                    vista,
                    "Portátil actualizado correctamente.",
                    "Actualización exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );

            vista.dispose();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "Error al actualizar el portátil:\n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // CANCELAR EDICIÓN
    public void cancelarEdicion(
            P7_AdministradorEditarPortatiles1 vista) {

        int opcion = JOptionPane.showConfirmDialog(
                vista,
                "¿Desea cancelar la edición?",
                "Confirmar cancelación",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            vista.dispose();
        }
    }

    // ABRIR VENTANA DE EDICIÓN
    public void editarPortatil(P6_AdministradorPortatiles1 vista) {

        if (!estaConectado()) {
            vista.actualizarEstadoBotones();

            JOptionPane.showMessageDialog(
                    vista,
                    "No existe una conexión activa con Oracle.",
                    "Sin conexión",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Portatil portatil = vista.obtenerPortatilSeleccionado();

        if (portatil == null) {

            JOptionPane.showMessageDialog(
                    vista,
                    "Seleccione un portátil de la tabla.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {

            if (estaAsignado(portatil.getNumeroSerie())) {

                JOptionPane.showMessageDialog(
                        vista,
                        "No se puede editar este portátil porque "
                        + "se encuentra asignado.",
                        "Portátil asignado",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int opcion = JOptionPane.showConfirmDialog(
                    vista,
                    "¿Desea editar el portátil seleccionado?",
                    "Confirmar edición",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcion != JOptionPane.YES_OPTION) {
                return;
            }

            P7_AdministradorEditarPortatiles1 ventana =
                    new P7_AdministradorEditarPortatiles1(portatil);

            ventana.addWindowListener(
                    new java.awt.event.WindowAdapter() {

                @Override
                public void windowClosed(
                        java.awt.event.WindowEvent e) {

                    if (vista.isDisplayable() && estaConectado()) {
                        vista.cargarPortatiles();
                    }
                }
            });

            ventana.setLocationRelativeTo(vista);
            ventana.setVisible(true);

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "Error al consultar el portátil:\n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ELIMINAR PORTÁTIL
    public void eliminarPortatil(P6_AdministradorPortatiles1 vista) {

        if (!estaConectado()) {
            vista.actualizarEstadoBotones();

            JOptionPane.showMessageDialog(
                    vista,
                    "No existe una conexión activa con Oracle.",
                    "Sin conexión",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Portatil portatil = vista.obtenerPortatilSeleccionado();

        if (portatil == null) {

            JOptionPane.showMessageDialog(
                    vista,
                    "Seleccione un portátil de la tabla.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {

            if (estaAsignado(portatil.getNumeroSerie())) {

                JOptionPane.showMessageDialog(
                        vista,
                        "No se puede eliminar este portátil porque "
                        + "se encuentra asignado.",
                        "Portátil asignado",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int opcion = JOptionPane.showConfirmDialog(
                    vista,
                    "¿Está seguro de eliminar el portátil seleccionado?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcion != JOptionPane.YES_OPTION) {
                return;
            }

            eliminarPortatil(portatil.getNumeroSerie());

            JOptionPane.showMessageDialog(
                    vista,
                    "Portátil eliminado correctamente.",
                    "Eliminación exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );

            vista.cargarPortatiles();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "Error al eliminar el portátil:\n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
