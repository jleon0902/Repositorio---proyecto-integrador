
package principal;

import modelo.LoginDAO;
import controlador.LoginControlador;
import vista.iniciarSesion;

public class Main {

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {

            // 1. CREAR EL MODELO (DAO)
            LoginDAO loginDAO = new LoginDAO();

            // 2. CREAR EL CONTROLADOR Y ENTREGARLE EL DAO
            LoginControlador controlador =
                    new LoginControlador(loginDAO);

            // 3. CREAR LA VISTA Y ENTREGARLE EL CONTROLADOR
            iniciarSesion ventana =
                    new iniciarSesion(controlador);

            // 4. MOSTRAR LA VISTA
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);

        });

    }
}
