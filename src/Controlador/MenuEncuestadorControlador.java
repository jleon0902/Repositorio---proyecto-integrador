/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controlador;

import vista.P7_MenuPrincipal_Encuestador;
import vista.P8_RepresentanteEncargado_Encuestador;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import modelo.SesionUsuario;
import vista.P2_InicioSesion;

public class MenuEncuestadorControlador {

    private P7_MenuPrincipal_Encuestador vista;

    public MenuEncuestadorControlador(P7_MenuPrincipal_Encuestador vista) {

        this.vista = vista;

        vista.getBtnRepresentanteEncargado().addActionListener(e -> {
            mostrarRepresentanteEncargado();
        });
    }

    private void mostrarRepresentanteEncargado() {

    P8_RepresentanteEncargado_Encuestador pantalla =
            new P8_RepresentanteEncargado_Encuestador();

    JPanel panel = pantalla.getPanelRepresentanteEncargado();

    // Quitar el menú propio de P8
    panel.remove(pantalla.getPanelMenu());

    JScrollPane scroll = new JScrollPane(panel);

    scroll.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
    );

    scroll.setHorizontalScrollBarPolicy(
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
    );

    JPanel panelPrincipal = vista.getPanelPrincipal();

    // Cambiar la distribución del panel principal
    panelPrincipal.removeAll();
    panelPrincipal.setLayout(new BorderLayout());

    // Mantener el menú de P7
    panelPrincipal.add(vista.getPanelMenu(), BorderLayout.WEST);

    // Mostrar P8 en la parte derecha
    panelPrincipal.add(scroll, BorderLayout.CENTER);

    panelPrincipal.revalidate();
    panelPrincipal.repaint();

    pantalla.dispose();
    }

        // Cierra la sesión del encuestador y regresa al inicio de sesión.
    public void cerrarSesion() {

        // Limpiar los datos de la persona autenticada.
        SesionUsuario.cerrarSesion();

        // Abrir la pantalla de inicio de sesión.
        new principal.Main().mostrarInicioSesion();

        // Cerrar la ventana actual del encuestador.
        vista.dispose();
    }
}
