
package controlador;

import modelo.P9_RecuentistaRegistrarHogaresDAO;
import vista.P9_RecuentistaRegistrarHogares1;

import java.sql.SQLException;
import java.util.List;

public class P9_RecuentistaRegistrarHogaresControlador {

    private final P9_RecuentistaRegistrarHogares1 vista;
    private final P9_RecuentistaRegistrarHogaresDAO dao;

    public P9_RecuentistaRegistrarHogaresControlador(
            P9_RecuentistaRegistrarHogares1 vista) {

        this.vista = vista;
        this.dao = new P9_RecuentistaRegistrarHogaresDAO();

        cargarBarrios();
        iniciarEventos();
    }

    // CARGAR LOS BARRIOS DEL MUNICIPIO DEL RECUENTISTA.
    private void cargarBarrios() {

        try {

            List<String> barrios =
                    dao.obtenerBarriosRecuentista(
                            vista.getCedulaRecuentista()
                    );

            vista.cargarBarrios(barrios);

        } catch (SQLException e) {

            vista.mostrarMensaje(
                    "Error al consultar los barrios: " + e.getMessage()
            );
        }
    }

    // CONECTAR LOS BOTONES.
    private void iniciarEventos() {

        vista.getBtnGuardar().addActionListener(e -> {
            registrarHogar();
        });

        vista.getBtnCancelar().addActionListener(e -> {
            vista.limpiarCampos();
        });
    }

    // REGISTRAR EL HOGAR Y SU REPRESENTANTE.
    private void registrarHogar() {

        if (!vista.validarDatosAcceso()) {
            return;
        }

        try {

            // VALIDAR LOS CAMPOS OBLIGATORIOS.
            if (vista.getNumeroVivienda().isEmpty()
                    || vista.getDireccion().isEmpty()
                    || vista.getCedula().isEmpty()
                    || vista.getNombres().isEmpty()
                    || vista.getApellidos().isEmpty()
                    || vista.getTelefono().isEmpty()
                    || vista.getUsuario().isEmpty()
                    || vista.getContrasena().isEmpty()) {

                vista.mostrarMensaje(
                        "Debe completar todos los campos obligatorios."
                );

                return;
            }

            // OBTENER EL BARRIO SELECCIONADO.
            Object seleccionado = vista.getBarrioSeleccionado();

            if (seleccionado == null
                    || seleccionado.toString().trim().isEmpty()
                    || seleccionado.toString().equals("Otras...")) {

                vista.mostrarMensaje(
                        "Debe seleccionar o ingresar un barrio."
                );

                return;
            }

            String nombreBarrio =
                    seleccionado.toString().trim();

            // REGISTRAR HOGAR Y REPRESENTANTE.
            // ORACLE REGISTRA EL BARRIO SI TODAVÍA NO EXISTE.
            dao.registrarHogar(
                    Integer.parseInt(vista.getNumeroVivienda()),
                    vista.getDireccion(),
                    Integer.parseInt(vista.getEstrato()),
                    nombreBarrio,
                    vista.getCedulaRecuentista(),
                    vista.getCedula(),
                    vista.getNombres(),
                    vista.getApellidos(),
                    vista.getTelefono(),
                    vista.getCorreo(),
                    vista.getUsuario(),
                    vista.getContrasena()
            );

            vista.mostrarMensaje(
                    "El hogar y su representante fueron registrados correctamente."
            );

            vista.limpiarCampos();

            cargarBarrios();

        } catch (NumberFormatException e) {

            vista.mostrarMensaje(
                    "El número de vivienda y el estrato deben ser numéricos."
            );

} catch (SQLException e) {

    String error = e.getMessage();

    if (error != null && error.contains("ORA-20014")) {

        vista.mostrarMensaje(
                "El número de vivienda ya se encuentra registrado."
        );

    } else if (error != null && error.contains("ORA-20012")) {

        vista.mostrarMensaje(
                "La cédula ingresada ya se encuentra registrada."
        );

    } else if (error != null && error.contains("ORA-20013")) {

        vista.mostrarMensaje(
                "El nombre de usuario ya se encuentra registrado."
        );

    } else if (error != null && error.contains("ORA-20010")) {

        vista.mostrarMensaje(
                "Debe completar todos los campos obligatorios."
        );

    } else if (error != null && error.contains("ORA-20011")) {

        vista.mostrarMensaje(
                "El estrato debe estar entre 1 y 6."
        );

    } else if (error != null && error.contains("ORA-00001")) {

        vista.mostrarMensaje(
                "Uno de los datos ingresados ya se encuentra registrado."
        );

    } else {

        vista.mostrarMensaje(
                "No fue posible registrar la información. Intente nuevamente."
        );
    }
}
    }
}
