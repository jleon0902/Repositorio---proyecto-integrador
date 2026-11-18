
package principal;

import vista.P2_InicioSesion;
import controlador.LoginControlador;
import modelo.ValidacionDAO;
import javax.swing.UIManager;
import controlador.NavegacionRepresentante;
import controlador.MenuEncuestadorControlador;

public class Main {

    public static void main(String[] args) {

        // Establecer la apariencia Nimbus.
        try {
            for (UIManager.LookAndFeelInfo info
                    : UIManager.getInstalledLookAndFeels()) {

                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

        } catch (Exception e) {
            System.err.println(
                    "No se pudo establecer la apariencia Nimbus: "
                    + e.getMessage()
            );
        }

        java.awt.EventQueue.invokeLater(() -> {
            Main main = new Main();
            main.mostrarInicioSesion();
        });
    }

    // Crear y mostrar la pantalla de inicio de sesión.
    public void mostrarInicioSesion() {

        ValidacionDAO validacionDAO = new ValidacionDAO();

        LoginControlador controlador =
                new LoginControlador(validacionDAO);

        P2_InicioSesion inicioSesion =
                new P2_InicioSesion(controlador, this);

        inicioSesion.setLocationRelativeTo(null);
        inicioSesion.setVisible(true);
    }

    public void mostrarMenuEncuestador() {

    vista.P7_MenuPrincipal_Encuestador menu =
            new vista.P7_MenuPrincipal_Encuestador();

    MenuEncuestadorControlador controlador =
            new MenuEncuestadorControlador(menu);

    menu.setControlador(controlador);

    menu.setLocationRelativeTo(null);
    menu.setVisible(true);
}
    
public void mostrarMenuRepresentante() {

    vista.P3_MenuPrincipal_Representante menu =
            new vista.P3_MenuPrincipal_Representante();

    NavegacionRepresentante controlador =
            new NavegacionRepresentante(menu);

    menu.setControlador(controlador);

    menu.setLocationRelativeTo(null);
    menu.setVisible(true);
}

}
