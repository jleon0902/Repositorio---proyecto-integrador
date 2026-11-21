
package controlador;

import modelo.LoginDAO;
import modelo.ResultadoLogin;
import modelo.SesionUsuario;

import vista.iniciarSesion;
import vista.P1_AdministradorMenu;
import vista.P8_RecuentistaMenu1;
import vista.P16_EncuestadorMenu1;
import vista.P23_CriticoMenu1;
import controlador.P8_RecuentistaMenuControlador;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JFrame;

public class LoginControlador {

    private final LoginDAO loginDAO;

    public LoginControlador(LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }

    public void iniciarSesion(iniciarSesion vista) {

        String usuario = vista.getUsuario();
        String contrasena = vista.getContrasena();

        if (usuario == null || usuario.trim().isEmpty()
                || contrasena == null || contrasena.isEmpty()) {

            vista.mostrarMensaje(
                    "Ingrese el usuario y la contraseña."
            );
            return;
        }

        try {

            ResultadoLogin resultado =
                    loginDAO.validarUsuario(
                            usuario.trim(),
                            contrasena
                    );

            if (resultado == null) {
                vista.mostrarMensaje(
                        "Usuario o contraseña incorrectos."
                );
                return;
            }

            String rol = resultado.getNombreRol();

            if (rol == null || rol.trim().isEmpty()) {
                vista.mostrarMensaje(
                        "El usuario no tiene un rol asignado."
                );
                return;
            }

            rol = rol.trim()
                    .toUpperCase()
                    .replace('_', ' ');

            switch (rol) {

                case "SUPERADMINISTRADOR":
                case "ADMINISTRADOR":
                case "RECUENTISTA":
                case "ENCUESTADOR":
                case "CRITICO DIGITADOR":
                    break;

                default:
                    vista.mostrarMensaje(
                            "No tiene un rol autorizado para ingresar."
                    );
                    return;
            }

            // Iniciar sesión
            SesionUsuario.iniciar(resultado);

            if (!SesionUsuario.estaActiva()) {

                SesionUsuario.cerrar();

                vista.mostrarMensaje(
                        "No se pudo establecer una sesión válida con Oracle."
                );
                return;
            }

            Connection conexion =
                    SesionUsuario.obtenerConexion();

            JFrame ventana;

            switch (rol) {

                case "SUPERADMINISTRADOR":
                case "ADMINISTRADOR":

                    String nombreCompleto =
                            (resultado.getNombre() == null ? "" :
                                    resultado.getNombre())
                            + " "
                            + (resultado.getApellido() == null ? "" :
                                    resultado.getApellido());

                    P1_AdministradorMenu menuAdmin =
                            new P1_AdministradorMenu(
                                    null,
                                    nombreCompleto.trim()
                            );

                    P1_AdministradorMenuControlador controladorAdmin =
                            new P1_AdministradorMenuControlador(
                                    menuAdmin,
                                    conexion
                            );

                    menuAdmin.establecerControlador(
                            controladorAdmin
                    );

                    ventana = menuAdmin;
                    break;

case "RECUENTISTA":

    P8_RecuentistaMenu1 menuRecuentista =
            new P8_RecuentistaMenu1();

    P8_RecuentistaMenuControlador controladorRecuentista =
            new P8_RecuentistaMenuControlador(
                    menuRecuentista,
                    resultado.getCedula()
            );

    ventana = menuRecuentista;
    break;

case "ENCUESTADOR":

    ventana = new P16_EncuestadorMenu1();
    break;

                case "CRITICO DIGITADOR":

                    ventana = new P23_CriticoMenu1(
                            conexion
                    );
                    break;

                default:

                    SesionUsuario.cerrar();
                    return;
            }

            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);

            vista.cerrarVentana();

        } catch (SQLException e) {

            SesionUsuario.cerrar();

            vista.mostrarMensaje(
                    "Error al iniciar sesión: " + e.getMessage()
            );

        } catch (RuntimeException e) {

            SesionUsuario.cerrar();

            vista.mostrarMensaje(
                    "Error al abrir el menú: " + e.getMessage()
            );
        }
    }
}
