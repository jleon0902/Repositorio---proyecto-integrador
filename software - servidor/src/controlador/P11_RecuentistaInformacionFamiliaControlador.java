
package controlador;

import modelo.InformacionHogarRecuentistaDAO;
import modelo.ResultadoLogin;
import modelo.SesionUsuario;
import vista.P10_RecuentistaVerHogares1;
import vista.P11_RecuentistaInformacionFamilia;
import vista.P12_RecuentistaEditarHogares1;

public class P11_RecuentistaInformacionFamiliaControlador {

    private final P11_RecuentistaInformacionFamilia vista;
    private final InformacionHogarRecuentistaDAO dao;
    private final String numeroVivienda;

    public P11_RecuentistaInformacionFamiliaControlador(
            P11_RecuentistaInformacionFamilia vista,
            String numeroVivienda) {

        this.vista = vista;
        this.numeroVivienda = numeroVivienda;
        this.dao = new InformacionHogarRecuentistaDAO();
    }

    // CARGAR INFORMACIÓN DEL HOGAR
    public void cargarInformacion() {

        try {

            ResultadoLogin usuario =
                    SesionUsuario.obtenerUsuario();

            if (usuario == null) {
                throw new Exception("No existe un usuario activo.");
            }

            Object[] informacion = dao.consultarInformacion(
                    numeroVivienda,
                    usuario.getCedula()
            );

            vista.mostrarInformacion(informacion);

        } catch (Exception e) {

            vista.mostrarError(
                    "No fue posible cargar la información del hogar: "
                    + e.getMessage()
            );
        }
    }

    // EDITAR HOGAR

public void editarHogar() {

    try {

        ResultadoLogin usuario = SesionUsuario.obtenerUsuario();

        if (usuario == null) {
            throw new Exception("No existe un usuario activo.");
        }

        boolean asignado = dao.hogarTieneEncuestador(
                numeroVivienda,
                usuario.getCedula()
        );

        if (asignado) {
            vista.mostrarAdvertencia(
                    "Este hogar ya fue asignado a un encuestador. "
                    + "No es posible modificar sus datos."
            );
            return;
        }

        P12_RecuentistaEditarHogares1 pantalla12 =
                new P12_RecuentistaEditarHogares1(
                        numeroVivienda,
                        usuario.getCedula(),
                        vista
                );

        pantalla12.setLocationRelativeTo(vista);
        pantalla12.setVisible(true);

        vista.dispose();

    } catch (Exception e) {

        vista.mostrarError(
                "No fue posible abrir la edición: " + e.getMessage()
        );
    }
}


    // ELIMINAR HOGAR
// ELIMINAR HOGAR
public void eliminarHogar() {

    if (!vista.confirmarEliminacion()) {
        return;
    }

    try {

        ResultadoLogin usuario =
                SesionUsuario.obtenerUsuario();

        if (usuario == null) {
            throw new Exception("No existe un usuario activo.");
        }

        // ELIMINAR DE ORACLE
        dao.eliminarHogar(
                numeroVivienda,
                usuario.getCedula()
        );

        // ACTUALIZAR INMEDIATAMENTE LA TABLA DE P10
        if (vista.getPantallaAnterior() != null) {

            vista.getPantallaAnterior()
                    .actualizarFilaEliminada(numeroVivienda);
        }

        vista.mostrarMensaje(
                "La información del hogar fue eliminada correctamente."
        );

        // CERRAR P11
        vista.dispose();

    } catch (Exception e) {

        vista.mostrarError(
                "No fue posible eliminar el hogar: "
                + e.getMessage()
        );
    }
}

// REGRESAR A LA PANTALLA 10
public void salir() {

    if (vista.getPantallaAnterior() != null) {

        vista.getPantallaAnterior().setVisible(true);
        vista.getPantallaAnterior().toFront();

        vista.dispose();
    }
}
}
