
package controlador;

import modelo.RepresentanteDAO;
import vista.Iniciar_Sesion;
import vista.P1_pantalla_psi;
import vista.P7_pantalla_Encuestador;
import vista.P18_pantalla_Criticodigi;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class LoginControlador {

    private final Iniciar_Sesion vista;
    private final RepresentanteDAO representanteDAO;

    public LoginControlador(Iniciar_Sesion vista) {
        this.vista = vista;
        this.representanteDAO = new RepresentanteDAO();
    }

    public void iniciarSesion(String usuario, String contrasena) {

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(
                    vista,
                    "Ingrese el usuario y la contraseña."
            );
            return;
        }

        try {
// Validar representante
String[] datosRepresentante =
        representanteDAO.validarRepresentante(usuario, contrasena);

if (datosRepresentante != null) {

    String cedulaRepresentante = datosRepresentante[0];
    String nombreRepresentante = datosRepresentante[1];

    JOptionPane.showMessageDialog(
            vista,
            "Bienvenido, " + nombreRepresentante
    );

    P1_pantalla_psi pantalla =
            new P1_pantalla_psi(
                    nombreRepresentante,
                    cedulaRepresentante
            );

    pantalla.setLocationRelativeTo(null);
    pantalla.setVisible(true);
    vista.dispose();
    return;
}


            // Validar encuestador
            String[] datosEncuestador =
                    representanteDAO.validarEncuestador(
                            usuario, contrasena
                    );

            if (datosEncuestador != null) {

                String cedulaEncuestador = datosEncuestador[0];
                String nombreEncuestador = datosEncuestador[1];

                JOptionPane.showMessageDialog(
                        vista,
                        "Bienvenido, " + nombreEncuestador
                );

                P7_pantalla_Encuestador pantalla =
                        new P7_pantalla_Encuestador();

                pantalla.setCedulaEncuestador(cedulaEncuestador);
                pantalla.setNombreEncuestador(nombreEncuestador);
                pantalla.setLocationRelativeTo(null);
                pantalla.setVisible(true);

                vista.dispose();
                return;
            }

            

            // Validar crítico-digitador
            String nombreCritico =
                    representanteDAO.validarCriticoDigitador(
                            usuario, contrasena
                    );

            if (nombreCritico != null) {
P18_pantalla_Criticodigi pantalla =
            new P18_pantalla_Criticodigi();

    pantalla.setNombreCriticoDigitador(nombreCritico);
    pantalla.setLocationRelativeTo(null);
    pantalla.setVisible(true);

    return;
            }

            // Ningún usuario coincidió
            JOptionPane.showMessageDialog(
                    vista,
                    "El usuario o la contraseña son incorrectos."
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "No fue posible consultar la base de datos."
            );

            e.printStackTrace();
        }
    }
}
