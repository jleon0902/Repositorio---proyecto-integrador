
package controlador;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import modelo.Usuario;
import modelo.UsuariosDAO;
import vista.P3_AdministradorUsuarios;
import vista.P4_AdministradorEditarUsuarios;
import controlador.P4_AdministradorEditarUsuariosControlador;

public class P3_AdministradorUsuariosControlador {

    private final P3_AdministradorUsuarios vista;
    private final Connection conexionUsuario;
    private final UsuariosDAO usuariosDAO;

    public P3_AdministradorUsuariosControlador(
            P3_AdministradorUsuarios vista,
            Connection conexionUsuario) {

        this.vista = vista;
        this.conexionUsuario = conexionUsuario;
        this.usuariosDAO = new UsuariosDAO();
    }

    public void cargarUsuarios() {

        if (conexionUsuario == null) {
            vista.mostrarError("No se recibió la conexión con Oracle.");
            return;
        }

        try {
            if (conexionUsuario.isClosed()) {
                vista.mostrarError("La conexión con Oracle está cerrada.");
                return;
            }

            List<Usuario> usuarios =
                    usuariosDAO.consultarUsuarios(conexionUsuario);

            vista.mostrarUsuarios(usuarios);

        } catch (SQLException e) {
            vista.mostrarError(
                    "Error al consultar los usuarios: " + e.getMessage()
            );
        }
    }

    public void editarUsuario() {

        Usuario usuario = vista.obtenerUsuarioSeleccionado();

        if (usuario == null) {
            vista.mostrarAdvertencia(
                    "Seleccione un usuario para editar."
            );
            return;
        }

        if (!vista.confirmarEdicion()) {
            return;
        }



        P4_AdministradorEditarUsuarios pantalla4 =
        new P4_AdministradorEditarUsuarios();

P4_AdministradorEditarUsuariosControlador controlador4 =
        new P4_AdministradorEditarUsuariosControlador(
                pantalla4,
                conexionUsuario,
                usuario.getCedula(),
                this::cargarUsuarios
        );

pantalla4.configurarControlador(controlador4);

controlador4.iniciar();

pantalla4.setLocationRelativeTo(null);
pantalla4.setVisible(true);
    }

public void eliminarUsuario() {

    Usuario usuario = vista.obtenerUsuarioSeleccionado();

    if (usuario == null) {
        vista.mostrarAdvertencia(
                "Seleccione un usuario para eliminar."
        );
        return;
    }

    int respuesta = javax.swing.JOptionPane.showConfirmDialog(
            vista,
            "¿Está seguro que desea eliminar este usuario?\n\n"
            + "Cédula: " + usuario.getCedula() + "\n"
            + "Nombre: " + usuario.getNombre() + " "
            + usuario.getApellido() + "\n"
            + "Rol: " + usuario.getRol(),
            "Confirmar eliminación",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE
    );

    if (respuesta != javax.swing.JOptionPane.YES_OPTION) {
        return;
    }

    try {

        usuariosDAO.eliminarUsuario(
                conexionUsuario,
                usuario.getCedula()
        );

        vista.mostrarInformacion(
                "El usuario fue eliminado correctamente."
        );

        cargarUsuarios();

    } catch (SQLException e) {

        String mensaje = e.getMessage();

        if (mensaje != null && mensaje.contains("ORA-20001")) {
            vista.mostrarAdvertencia(
                    "No se puede eliminar al Superadministrador."
            );

        } else if (mensaje != null && mensaje.contains("ORA-20002")) {
            vista.mostrarAdvertencia(
                    "No se puede eliminar el encuestador porque tiene familias asignadas."
            );

        } else if (mensaje != null && mensaje.contains("ORA-20003")) {
            vista.mostrarAdvertencia(
                    "No se puede eliminar el recuentista porque tiene hogares pendientes de asignar."
            );

        } else if (mensaje != null && mensaje.contains("ORA-20004")) {
            vista.mostrarAdvertencia(
                    "No se encontró el usuario seleccionado."
            );

        } else {
            vista.mostrarError(
                    "Error al eliminar el usuario: " + mensaje
            );
        }
    }
}

}
