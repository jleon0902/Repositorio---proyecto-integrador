
package controlador;

import vista.P7_MenuPrincipal_Encuestador;
import vista.P8_RepresentanteEncargado_Encuestador;
import vista.P9_RevisarEncuesta_Encuestador;
import vista.P11_Sincronizar_Encuestador;
import modelo.SesionUsuario;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;

public class MenuEncuestadorControlador {

    private P7_MenuPrincipal_Encuestador vista;

    public MenuEncuestadorControlador(P7_MenuPrincipal_Encuestador vista) {

        this.vista = vista;

        // Entregar a la vista los datos de la sesión.
        vista.mostrarNombreEncuestador(
            SesionUsuario.getNombre(),
            SesionUsuario.getApellido()
        );

        // Conectar botón Representante encargado.
        vista.getBtnRepresentanteEncargado().addActionListener(e -> {
            mostrarRepresentanteEncargado();
        });

        // Conectar botón Revisar encuesta.
        vista.getBtnRevisarEncuesta().addActionListener(e -> {
            mostrarRevisarEncuesta();
        });
        
        // Conectar botón Sincronizar.
        vista.getBtnSincronizar().addActionListener(e -> {
            mostrarSincronizar();
});
    }

    private void mostrarRepresentanteEncargado() {

        // Crear la pantalla P8.
        P8_RepresentanteEncargado_Encuestador pantalla =
                new P8_RepresentanteEncargado_Encuestador();

        // Crear el controlador de P8 y cargar los datos.
        RevisarEncuestaEncuestadorControlador controladorP8 =
                new RevisarEncuestaEncuestadorControlador(pantalla);

        // Obtener el panel de contenido de P8.
        JPanel panel = pantalla.getPanelRepresentanteEncargado();

        // Quitar el menú propio de P8.
        panel.remove(pantalla.getPanelMenu());

        // Crear el desplazamiento para el contenido.
        JScrollPane scroll = new JScrollPane(panel);

        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        JPanel panelPrincipal = vista.getPanelPrincipal();

        // Limpiar el contenido actual.
        panelPrincipal.removeAll();

        // Distribuir el menú y el contenido.
        panelPrincipal.setLayout(new BorderLayout());

        // Mantener el menú de P7.
        panelPrincipal.add(vista.getPanelMenu(), BorderLayout.WEST);

        panelPrincipal.add(
        scroll,
        BorderLayout.CENTER
);

        panelPrincipal.revalidate();
        panelPrincipal.repaint();

        // P8 no se muestra como ventana independiente.
        pantalla.dispose();
    }

    public void mostrarRevisarEncuesta() {

        // Crear la pantalla P9.
        P9_RevisarEncuesta_Encuestador pantalla =
                new P9_RevisarEncuesta_Encuestador();
        
        RevisarEncuestaEncuestadorControlador controladorP9 =
        new RevisarEncuestaEncuestadorControlador(pantalla);

        // Obtener el panel principal de P9.
        JPanel panel = pantalla.getPanelRevisarEncuesta();

        // Quitar el menú lateral propio de P9.
        panel.remove(pantalla.getPanelMenu());

        // Crear el desplazamiento.
        JScrollPane scroll = new JScrollPane(panel);

        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        // Obtener el panel principal de P7.
        JPanel panelPrincipal = vista.getPanelPrincipal();

        // Limpiar el contenido anterior.
        panelPrincipal.removeAll();

        // Organizar el panel.
        panelPrincipal.setLayout(new BorderLayout());

        // Mantener el menú lateral de P7.
        panelPrincipal.add(
                vista.getPanelMenu(),
                BorderLayout.WEST
        );

        // Mostrar P9.
        panelPrincipal.add(
                scroll,
                BorderLayout.CENTER
        );

        // Actualizar la interfaz.
        panelPrincipal.revalidate();
        panelPrincipal.repaint();

        // No abrir P9 como ventana independiente.
        pantalla.dispose();
    }
    
    public void mostrarSincronizar() {

    // Crear la pantalla P11.
    P11_Sincronizar_Encuestador pantalla =
            new P11_Sincronizar_Encuestador();
    
    new SincronizarEncuestaControlador(pantalla);

    // Obtener el panel principal de P11.
    JPanel panel = pantalla.getPanelSincronizar();

    // Quitar el menú lateral propio de P11.
    panel.remove(pantalla.getPanelMenu());

    // Crear el desplazamiento.
    JScrollPane scroll = new JScrollPane(panel);

    scroll.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
    );

    scroll.setHorizontalScrollBarPolicy(
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
    );

    // Obtener el panel principal de P7.
    JPanel panelPrincipal = vista.getPanelPrincipal();

    // Limpiar el contenido anterior.
    panelPrincipal.removeAll();

    // Organizar el panel.
    panelPrincipal.setLayout(new BorderLayout());

    // Mantener el menú lateral de P7.
    panelPrincipal.add(
            vista.getPanelMenu(),
            BorderLayout.WEST
    );

    // Mostrar P11.
    panelPrincipal.add(
            scroll,
            BorderLayout.CENTER
    );

    // Actualizar la interfaz.
    panelPrincipal.revalidate();
    panelPrincipal.repaint();

    // No abrir P11 como ventana independiente.
    pantalla.dispose();
}

    // Cerrar la sesión del encuestador.
    public void cerrarSesion() {

        SesionUsuario.cerrarSesion();

        new principal.Main().mostrarInicioSesion();

        vista.dispose();
    }
}
