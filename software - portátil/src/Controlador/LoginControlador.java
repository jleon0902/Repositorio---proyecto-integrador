
package controlador;

import modelo.ValidacionDAO;
import modelo.SesionUsuario;
import java.sql.SQLException;

public class LoginControlador {

    private final ValidacionDAO validacionDAO;
    private boolean errorConexion;

    public LoginControlador(ValidacionDAO validacionDAO) {
        this.validacionDAO = validacionDAO;
    }

    // Valida las credenciales y guarda los datos de sesión.
    public String[] iniciarSesion(String usuario, String contrasena) {

        SesionUsuario.cerrarSesion();
        errorConexion = false;

        try {

            String[] resultado = validacionDAO.validarInicioSesion(
                    usuario, contrasena
            );

            if (resultado != null) {

                SesionUsuario.iniciarSesion(
                        resultado[0],
                        resultado[1],
                        resultado[2],
                        resultado[3]
                );
            }

            return resultado;

        } catch (SQLException e) {

            errorConexion = true;

            System.err.println("Error de conexión: " + e.getMessage());

            return null;
        }
    }

    public boolean huboErrorConexion() {
        return errorConexion;
    }
}
