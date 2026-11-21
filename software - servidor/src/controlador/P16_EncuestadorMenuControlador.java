
package controlador;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.sql.SQLException;
import modelo.SesionUsuario;
import vista.P16_EncuestadorMenu1;
import vista.P17_EncuestadorHogaresAsignados1;
import vista.P19_EncuestadorVincularPortatil1;
import vista.P20_EncuestadorPortatilesAsignados1;
import vista.P22_EncuestadorSincronizarCapitulos1;
import vista.iniciarSesion;
import modelo.P19_EncuestadorVincularPortatilDAO;
import controlador.P22_EncuestadorSincronizarCapitulosControlador;

public class P16_EncuestadorMenuControlador {

    private final P16_EncuestadorMenu1 vista;

    public P16_EncuestadorMenuControlador(
            P16_EncuestadorMenu1 vista) {

        this.vista = vista;
    }

    // VERIFICAR CONEXIÓN ACTIVA
    private boolean verificarConexion() {

        if (!SesionUsuario.estaActiva()) {

            JOptionPane.showMessageDialog(
                    vista,
                    "No existe una conexión activa. "
                    + "Debe iniciar sesión nuevamente."
            );

            return false;
        }

        return true;
    }

    // INICIAR CONTROLADOR Y VALIDAR CONEXIÓN
    public void iniciar() {

        if (!verificarConexion()) {
            vista.dispose();
            return;
        }

        vista.getBtnRegistrarUsuarios().addActionListener(
                e -> mostrarHogaresAsignados());

        vista.getBtnUsuarios().addActionListener(
                e -> mostrarVincularPortatil());

        vista.getBtnRegistrarPortatiles().addActionListener(
                e -> mostrarPortatilesAsignados());

        vista.getBtnPortatiles().addActionListener(
                e -> mostrarSincronizar());

        vista.getBtnCerrarSesion().addActionListener(
                e -> cerrarSesion());
    }

    // MOSTRAR PANEL DENTRO DE LA PANTALLA 16
    private void mostrarPanel(JPanel panel) {

        vista.getPnlMenuInterfaz().removeAll();

        vista.getPnlMenuInterfaz().setLayout(
                new BorderLayout());

        panel.setPreferredSize(null);
        panel.setMinimumSize(null);

        vista.getPnlMenuInterfaz().add(
                panel, BorderLayout.CENTER);

        vista.getPnlMenuInterfaz().revalidate();
        vista.getPnlMenuInterfaz().repaint();
    }

    // HOGARES ASIGNADOS


public void mostrarHogaresAsignados() {

    if (!verificarConexion()) {
        return;
    }

    try {

        P17_EncuestadorHogaresAsignados1 pantalla =
                new P17_EncuestadorHogaresAsignados1();

        P17_EncuestadorHogaresAsignadosControlador controlador =
                new P17_EncuestadorHogaresAsignadosControlador(
                        pantalla,
                        SesionUsuario.obtenerConexion()
                );

        controlador.iniciar();

        mostrarPanel(pantalla.obtenerPanelHogaresAsignados());

        pantalla.dispose();

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
                vista,
                "Error al obtener la conexión con Oracle: "
                + e.getMessage(),
                "Error de conexión",
                JOptionPane.ERROR_MESSAGE
        );
    }
}



    // VINCULAR PORTÁTIL


public void mostrarVincularPortatil() {

    if (!verificarConexion()) {
        return;
    }

    try {

        P19_EncuestadorVincularPortatil1 pantalla =
                new P19_EncuestadorVincularPortatil1();

        P19_EncuestadorVincularPortatilControlador controlador =
                new P19_EncuestadorVincularPortatilControlador(
                        pantalla,
                        SesionUsuario.obtenerConexion()
                );

        controlador.iniciar();

        mostrarPanel(
                pantalla.obtenerPanelVincularPortatil()
        );

        pantalla.dispose();

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
                vista,
                "Error al obtener la conexión con Oracle: "
                + e.getMessage(),
                "Error de conexión",
                JOptionPane.ERROR_MESSAGE
        );
    }
}



    // PORTÁTILES ASIGNADOS

 // PORTÁTILES ASIGNADOS
public void mostrarPortatilesAsignados() {

    if (!verificarConexion()) {
        return;
    }

    try {

        P20_EncuestadorPortatilesAsignados1 pantalla =
                new P20_EncuestadorPortatilesAsignados1();

        P20_EncuestadorPortatilesAsignadosControlador controlador20 =
                new P20_EncuestadorPortatilesAsignadosControlador(
                        pantalla,
                        SesionUsuario.obtenerConexion()
                );

        controlador20.iniciar();

        mostrarPanel(
                pantalla.obtenerPanelPortatilesAsignados()
        );

        pantalla.dispose();

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
                vista,
                "Error al obtener la conexión con Oracle: "
                + e.getMessage(),
                "Error de conexión",
                JOptionPane.ERROR_MESSAGE
        );
    }
}


    // SINCRONIZAR

public void mostrarSincronizar() {

    if (!verificarConexion()) return;

    P22_EncuestadorSincronizarCapitulos1 pantalla =
            new P22_EncuestadorSincronizarCapitulos1();

    P22_EncuestadorSincronizarCapitulosControlador controlador =
            new P22_EncuestadorSincronizarCapitulosControlador(pantalla);

    controlador.iniciar();

    mostrarPanel(pantalla.obtenerPanelSincronizar());

    pantalla.dispose();
}


    // CERRAR SESIÓN
    public void cerrarSesion() {

        if (!verificarConexion()) return;

        int respuesta = JOptionPane.showConfirmDialog(
                vista,
                "¿Está seguro de que desea cerrar sesión?",
                "Cerrar sesión",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (respuesta == JOptionPane.YES_OPTION) {

            SesionUsuario.cerrar();

            iniciarSesion inicio = new iniciarSesion();

            inicio.setLocationRelativeTo(null);
            inicio.setVisible(true);

            vista.dispose();
        }
    }
}
