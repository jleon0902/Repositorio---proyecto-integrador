/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

import modelo.Usuario;
import modelo.Ubicacion;
import modelo.UbicacionDAO;
import modelo.RolesDAO;
import modelo.EditarUsuarioDAO;
import vista.P4_AdministradorEditarUsuarios;

public class P4_AdministradorEditarUsuariosControlador {

    private final P4_AdministradorEditarUsuarios vista;
    private final Connection conexion;
    private final String cedulaOriginal;
    private final Runnable accionActualizar;

    private final EditarUsuarioDAO usuarioDAO;
    private final UbicacionDAO ubicacionDAO;
    private final RolesDAO rolesDAO;

    public P4_AdministradorEditarUsuariosControlador(
            P4_AdministradorEditarUsuarios vista,
            Connection conexion,
            String cedulaOriginal,
            Runnable accionActualizar) {

        this.vista = vista;
        this.conexion = conexion;
        this.cedulaOriginal = cedulaOriginal;
        this.accionActualizar = accionActualizar;

        usuarioDAO = new EditarUsuarioDAO();
        ubicacionDAO = new UbicacionDAO();
        rolesDAO = new RolesDAO();
    }

    public void iniciar() {

        try {
            cargarRoles();
            cargarRegiones();
            consultarUsuario();

        } catch (SQLException e) {
            vista.mostrarError(
                    "Error al iniciar la edición:\n" + e.getMessage()
            );
        }
    }

    private void cargarRoles() throws SQLException {

        vista.mostrarRoles(rolesDAO.obtenerRoles(conexion));
    }

    private void cargarRegiones() throws SQLException {

        vista.mostrarRegiones(
                ubicacionDAO.obtenerRegiones(conexion)
        );
    }

    public void cambiarRegion() {

        int indice = vista.obtenerIndiceRegion();

        List<Ubicacion> regiones = vista.obtenerRegiones();

        if (indice < 0 || indice >= regiones.size()) {
            return;
        }

        try {
            int id = regiones.get(indice).getId();

            vista.mostrarDepartamentos(
                    ubicacionDAO.obtenerDepartamentos(conexion, id)
            );

            vista.limpiarMunicipios();

        } catch (SQLException e) {
            vista.mostrarError(
                    "Error al cargar los departamentos:\n" + e.getMessage()
            );
        }
    }

    public void cambiarDepartamento() {

        int indice = vista.obtenerIndiceDepartamento();

        List<Ubicacion> departamentos =
                vista.obtenerDepartamentos();

        if (indice < 0 || indice >= departamentos.size()) {
            return;
        }

        try {
            int id = departamentos.get(indice).getId();

            vista.mostrarMunicipios(
                    ubicacionDAO.obtenerMunicipios(conexion, id)
            );

        } catch (SQLException e) {
            vista.mostrarError(
                    "Error al cargar los municipios:\n" + e.getMessage()
            );
        }
    }

    private void consultarUsuario() throws SQLException {

        Usuario datos = usuarioDAO.consultarUsuario(
                conexion,
                cedulaOriginal
        );

        if (datos == null) {
            vista.mostrarError("No se encontró el usuario seleccionado.");
            vista.dispose();
            return;
        }

        vista.mostrarDatosUsuario(datos);

        String rol = datos.getRol();

        vista.bloquearCamposSegunRol(rol);

        if (datos.getIdRegion() != null) {

            vista.seleccionarRegion(datos.getIdRegion());

            if (datos.getIdDepartamento() != null) {

                vista.cargarDepartamentos(
                        ubicacionDAO.obtenerDepartamentos(
                                conexion,
                                datos.getIdRegion()
                        )
                );

                vista.seleccionarDepartamento(
                        datos.getIdDepartamento()
                );

                if (datos.getIdMunicipio() != null) {

                    vista.cargarMunicipios(
                            ubicacionDAO.obtenerMunicipios(
                                    conexion,
                                    datos.getIdDepartamento()
                            )
                    );

                    vista.seleccionarMunicipio(
                            datos.getIdMunicipio()
                    );
                }
            }
        }
    }

    public void guardarCambios() {
if (!vista.validarUsuarioYContrasena()) {
    return;
}
        String cedula = vista.obtenerCedula();
        String nombre = vista.obtenerNombre();
        String apellido = vista.obtenerApellido();
        String usuario = vista.obtenerUsuario();
        String contrasena = vista.obtenerContrasena();
        String rol = vista.obtenerRol();

        if (cedula.isEmpty()
                || nombre.isEmpty()
                || apellido.isEmpty()
                || usuario.isEmpty()
                || contrasena.isEmpty()
                || rol == null) {

            vista.mostrarAdvertencia(
                    "Debe completar todos los campos."
            );
            return;
        }

Integer idMunicipio = null;

if (rol.equalsIgnoreCase("ENCUESTADOR")
        || rol.equalsIgnoreCase("RECUENTISTA")) {

    if (vista.obtenerIndiceRegion() < 0) {
        vista.mostrarAdvertencia(
                "Debe seleccionar una región."
        );
        return;
    }

    if (vista.obtenerIndiceDepartamento() < 0) {
        vista.mostrarAdvertencia(
                "Debe seleccionar un departamento."
        );
        return;
    }

    idMunicipio = vista.obtenerIdMunicipioSeleccionado();

    if (idMunicipio == null) {
        vista.mostrarAdvertencia(
                "Debe seleccionar un municipio."
        );
        return;
    }
}

        try {

            if (!usuarioDAO.validarUsuarioDisponible(
                    conexion,
                    cedulaOriginal,
                    usuario)) {
                vista.mostrarAdvertencia(
                        "Este nombre de usuario ya está registrado por otra persona."
                );
                return;
            }

            if (!vista.confirmarActualizacion()) {
                return;
            }

            usuarioDAO.actualizarUsuario(
                    conexion,
                    cedulaOriginal,
                    cedula,
                    nombre,
                    apellido,
                    usuario,
                    contrasena,
                    rol,
                    idMunicipio
            );

            vista.mostrarInformacion(
                    "Los datos del usuario se actualizaron correctamente."
            );

            if (accionActualizar != null) {
                accionActualizar.run();
            }

            vista.dispose();

        } catch (SQLException e) {

            try {
                if (!conexion.getAutoCommit()) {
                    conexion.rollback();
                }
            } catch (SQLException ex) {
                vista.mostrarError(
                        "Error al deshacer los cambios: " + ex.getMessage()
                );
            }

            vista.mostrarError(
                    usuarioDAO.obtenerMensajeError(e)
            );
        }
    }

public void cancelar() {

    vista.dispose();

    if (accionActualizar != null) {
        accionActualizar.run();
    }
}
}