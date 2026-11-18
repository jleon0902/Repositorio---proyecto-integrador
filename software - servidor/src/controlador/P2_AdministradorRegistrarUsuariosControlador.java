/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import modelo.RolesDAO;
import modelo.Ubicacion;
import modelo.UbicacionDAO;
import modelo.RegistrarUsuarioDAO;
import vista.P2_AdministradorRegistrarUsuarios;

public class P2_AdministradorRegistrarUsuariosControlador {

    private final P2_AdministradorRegistrarUsuarios vista;

    private final RolesDAO rolesDAO;
    private final UbicacionDAO ubicacionDAO;
    private final RegistrarUsuarioDAO registrarUsuarioDAO;

    private Connection conexionActiva;

    private List<Ubicacion> regiones = new ArrayList<>();
    private List<Ubicacion> departamentos = new ArrayList<>();
    private List<Ubicacion> municipios = new ArrayList<>();

    private boolean cargandoCombos = false;

    public P2_AdministradorRegistrarUsuariosControlador(
            P2_AdministradorRegistrarUsuarios vista) {

        this.vista = vista;

        rolesDAO = new RolesDAO();
        ubicacionDAO = new UbicacionDAO();
        registrarUsuarioDAO = new RegistrarUsuarioDAO();
    }

    // Recibir la conexión que entrega el menú del administrador.
    public void establecerConexion(Connection cn) {

        conexionActiva = cn;

        vista.limpiarRoles();
        vista.limpiarCombosUbicacion();
        vista.habilitarRoles(false);
        vista.habilitarGuardar(false);

        if (cn == null) {
            vista.mostrarError("No existe una conexión activa con Oracle.");
            return;
        }

        try {

            if (cn.isClosed()) {
                vista.mostrarError("La conexión con Oracle está cerrada.");
                return;
            }

            String usuarioOracle = cn.getMetaData().getUserName();

            if (usuarioOracle == null
                    || !usuarioOracle.equalsIgnoreCase("ADMINISTRADOR")) {

                vista.mostrarError(
                        "No tienes permisos para registrar usuarios."
                );
                return;
            }

            vista.habilitarRoles(true);
            vista.habilitarGuardar(true);

            cargarRoles();

        } catch (SQLException e) {

            vista.mostrarError(
                    "Error al verificar la conexión: " + e.getMessage()
            );
        }
    }

    // Consultar los roles disponibles.
    private void cargarRoles() {

        try {

            List<String> roles = rolesDAO.obtenerRoles(conexionActiva);

            vista.limpiarRoles();

            for (String rol : roles) {
                vista.agregarRol(rol);
            }

            vista.seleccionarNingunRol();

        } catch (SQLException e) {

            vista.mostrarError(
                    "Error al cargar los roles: " + e.getMessage()
            );
        }
    }

    // Se ejecuta cuando se selecciona un rol.
    public void rolSeleccionado() {

        if (cargandoCombos) {
            return;
        }

        String rol = vista.getRolSeleccionado();

        if (rol == null) {
            limpiarUbicaciones();
            return;
        }

        rol = normalizarRol(rol);

        if (rol.equals("ENCUESTADOR") || rol.equals("RECUENTISTA")) {
            cargarRegiones();
        } else {
            limpiarUbicaciones();
        }
    }

    private String normalizarRol(String rol) {
        return rol.toUpperCase().replace("ROL_", "").trim();
    }

    // Consultar regiones.
    private void cargarRegiones() {

        if (conexionActiva == null) {
            return;
        }

        cargandoCombos = true;

        try {

            regiones = ubicacionDAO.obtenerRegiones(conexionActiva);

            vista.limpiarCombosUbicacion();

            for (Ubicacion region : regiones) {
                vista.agregarRegion(region.getNombre());
            }

            vista.seleccionarNingunaRegion();
            vista.habilitarRegion(true);

        } catch (SQLException e) {

            vista.mostrarError(
                    "Error al cargar las regiones: " + e.getMessage()
            );

        } finally {
            cargandoCombos = false;
        }
    }

    // Se ejecuta cuando se selecciona una región.
    public void regionSeleccionada() {

        if (cargandoCombos) {
            return;
        }

        String nombreRegion = vista.getRegionSeleccionada();

        if (nombreRegion == null) {
            return;
        }

        int idRegion = buscarId(regiones, nombreRegion);

        if (idRegion == -1) {
            return;
        }

        cargandoCombos = true;

        try {

            departamentos = ubicacionDAO.obtenerDepartamentos(
                    conexionActiva, idRegion
            );

            municipios.clear();

            vista.limpiarDepartamentosYMunicipios();

            for (Ubicacion departamento : departamentos) {
                vista.agregarDepartamento(departamento.getNombre());
            }

            vista.seleccionarNingunDepartamento();
            vista.habilitarDepartamento(!departamentos.isEmpty());

        } catch (SQLException e) {

            vista.mostrarError(
                    "Error al cargar los departamentos: " + e.getMessage()
            );

        } finally {
            cargandoCombos = false;
        }
    }

    // Se ejecuta cuando se selecciona un departamento.
    public void departamentoSeleccionado() {

        if (cargandoCombos || conexionActiva == null) {
            return;
        }

        String nombreDepartamento = vista.getDepartamentoSeleccionado();

        if (nombreDepartamento == null) {
            return;
        }

        int idDepartamento = buscarId(
                departamentos, nombreDepartamento
        );

        if (idDepartamento == -1) {
            return;
        }

        cargandoCombos = true;

        try {

            municipios = ubicacionDAO.obtenerMunicipios(
                    conexionActiva, idDepartamento
            );

            vista.limpiarMunicipios();

            for (Ubicacion municipio : municipios) {
                vista.agregarMunicipio(municipio.getNombre());
            }

            vista.seleccionarNingunMunicipio();
            vista.habilitarMunicipio(!municipios.isEmpty());

            if (municipios.isEmpty()) {
                vista.mostrarInformacion(
                        "No se encontraron municipios para el departamento seleccionado."
                );
            }

        } catch (SQLException e) {

            vista.mostrarError(
                    "Error al cargar los municipios: " + e.getMessage()
            );

        } finally {
            cargandoCombos = false;
        }
    }

    // Buscar el ID correspondiente al nombre seleccionado.
    private int buscarId(List<Ubicacion> lista, String nombre) {

        for (Ubicacion ubicacion : lista) {

            if (ubicacion.getNombre().equals(nombre)) {
                return ubicacion.getId();
            }
        }

        return -1;
    }

    private void limpiarUbicaciones() {

        regiones.clear();
        departamentos.clear();
        municipios.clear();

        vista.limpiarCombosUbicacion();
    }

    // Registrar un usuario.
    public void guardar() {
if (!vista.validarUsuarioYContrasena()) {
        return;
    }
        if (conexionActiva == null) {
            vista.mostrarError(
                    "No existe una conexión activa con Oracle."
            );
            return;
        }

        String cedula = vista.getCedula();
        String nombre = vista.getNombre();
        String apellido = vista.getApellido();
        String usuario = vista.getUsuario();
        String contrasena = vista.getContrasena();
        String rol = vista.getRolSeleccionado();

        if (cedula.isEmpty()
                || nombre.isEmpty()
                || apellido.isEmpty()
                || usuario.isEmpty()
                || contrasena.isEmpty()
                || rol == null) {

            vista.mostrarAdvertencia("Debe completar todos los campos.");
            return;
        }

        String rolNormalizado = normalizarRol(rol);

        Integer idMunicipio = null;

        if (rolNormalizado.equals("ENCUESTADOR")
                || rolNormalizado.equals("RECUENTISTA")) {

            String nombreMunicipio = vista.getMunicipioSeleccionado();

            if (nombreMunicipio == null) {
                vista.mostrarAdvertencia(
                        "Debe seleccionar un municipio."
                );
                return;
            }

            int id = buscarId(municipios, nombreMunicipio);

            if (id == -1) {
                vista.mostrarAdvertencia(
                        "El municipio seleccionado no es válido."
                );
                return;
            }

            idMunicipio = id;
        }

        if (!vista.confirmarRegistro()) {
            return;
        }

        try {

            registrarUsuarioDAO.registrarUsuario(
                    conexionActiva,
                    cedula,
                    nombre,
                    apellido,
                    usuario,
                    contrasena,
                    rol,
                    idMunicipio
            );

            vista.mostrarInformacion(
                    "El usuario fue registrado correctamente."
            );

            vista.limpiarFormulario();

            regiones.clear();
            departamentos.clear();
            municipios.clear();

        } catch (SQLException e) {

            vista.mostrarError(obtenerMensajeError(e));
        }
    }

    private String obtenerMensajeError(SQLException e) {

        String error = e.getMessage();

        if (error == null) {
            error = "";
        }

        error = error.toUpperCase();

        if (error.contains("ORA-20008")) {
            return "La cédula, el usuario y la contraseña ya existen.";
        } else if (error.contains("ORA-20005")) {
            return "La cédula y el usuario ya existen.";
        } else if (error.contains("ORA-20006")) {
            return "La cédula y la contraseña ya existen.";
        } else if (error.contains("ORA-20007")) {
            return "El usuario y la contraseña ya existen.";
        } else if (error.contains("ORA-20001")) {
            return "No se pudo guardar la información porque esa persona ya existe.";
        } else if (error.contains("ORA-20003")) {
            return "El nombre de usuario ya se encuentra registrado.";
        } else if (error.contains("ORA-20004")) {
            return "La contraseña ya se encuentra registrada.";
        } else if (error.contains("ORA-20002")) {
            return "Debe seleccionar un municipio.";
        }

        return "Error al registrar el usuario: " + e.getMessage();
    }

    // Acción del botón Cancelar.
// Acción del botón Cancelar.
public void cancelar() {

    if (vista.confirmarCancelacion()) {

        vista.limpiarFormulario();

        regiones.clear();
        departamentos.clear();
        municipios.clear();

    }
}
    
    
}
