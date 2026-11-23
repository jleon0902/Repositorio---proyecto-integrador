//<editor-fold defaultstate="collapsed" desc="comment">

package controlador;
import controlador.P9_RecuentistaRegistrarHogaresControlador;
import vista.P8_RecuentistaMenu1;
import controlador.P13_RecuentistaAsignarHogarControlador;
import controlador.HogaresAsignadosRecuentistaControlador;
import controlador.P14_RecuentistaHogaresAsignadosControlador;
import vista.P9_RecuentistaRegistrarHogares1;
import vista.P10_RecuentistaVerHogares1;
import vista.P13_RecuentistaAsignarHogar1;
import vista.P14_RecuentistaHogaresAsignados1;
import vista.iniciarSesion;
import modelo.SesionUsuario;
import javax.swing.JOptionPane;

public class P8_RecuentistaMenuControlador {
    
    private final P8_RecuentistaMenu1 vista;
    private final String cedulaRecuentista;
    
    public P8_RecuentistaMenuControlador(
            P8_RecuentistaMenu1 vista,
            String cedulaRecuentista) {
        
        this.vista = vista;
        this.cedulaRecuentista = cedulaRecuentista;
        this.vista.setControlador(this);
    }
    
public void mostrarRegistrarHogares() {

    if (!verificarConexion()) {
        return;
    }

    // CREAR LA PANTALLA 9 Y ENVIAR LA CÉDULA DEL RECUENTISTA.
    P9_RecuentistaRegistrarHogares1 pantalla =
            new P9_RecuentistaRegistrarHogares1(cedulaRecuentista);

    // CREAR EL CONTROLADOR DE LA PANTALLA 9.
    P9_RecuentistaRegistrarHogaresControlador controladorP9 =
            new P9_RecuentistaRegistrarHogaresControlador(pantalla);

    // OBTENER EL FORMULARIO.
    javax.swing.JPanel formulario =
            pantalla.getPanelRegistrarHogares();

    // OBTENER LA BARRA DE DESPLAZAMIENTO.
    javax.swing.JScrollBar barra =
            pantalla.getScrollBarRegistrarHogares();

    // CONFIGURAR EL DESPLAZAMIENTO.
    pantalla.configurarScroll();

    // MOSTRAR EL FORMULARIO DENTRO DE LA PANTALLA 8.
    vista.mostrarFormularioConScroll(
            formulario,
            barra,
            680,
            1250
    );

    // CERRAR EL FRAME ORIGINAL SIN CERRAR EL FORMULARIO.
    pantalla.dispose();
}
    


public void mostrarVerHogares() {

    if (!verificarConexion()) {
        return;
    }

    P10_RecuentistaVerHogares1 pantalla =
            new P10_RecuentistaVerHogares1();

    P10_RecuentistaVerHogaresControlador controladorP10 =
            new P10_RecuentistaVerHogaresControlador(pantalla);

    controladorP10.cargarHogares();

    javax.swing.JPanel formulario =
            pantalla.getPanelVerHogares();

    vista.mostrarFormulario(formulario);

    pantalla.dispose();
}


    

public void mostrarAsignarHogar() {

    if (!verificarConexion()) {
        return;
    }

    // Crear la pantalla 13.
    P13_RecuentistaAsignarHogar1 pantalla =
            new P13_RecuentistaAsignarHogar1();

    // Crear el controlador de la pantalla 13.
    // Aquí se ejecuta cargarHogares().
    P13_RecuentistaAsignarHogarControlador controladorP13 =
            new P13_RecuentistaAsignarHogarControlador(pantalla);

    // Obtener el panel de la pantalla 13.
    javax.swing.JPanel formulario =
            pantalla.getPanelAsignarHogar();

    // Obtener la barra de desplazamiento.
    javax.swing.JScrollBar barra =
            pantalla.getScrollBarAsignarHogar();

    // Mostrar la pantalla 13 dentro de la pantalla 8.
    vista.mostrarFormularioConScroll(
            formulario,
            barra,
            670,
            1000
    );

    // Cerrar el JFrame original.
    pantalla.dispose();
}

    
public void mostrarHogaresAsignados() {

    if (!verificarConexion()) {
        return;
    }

P14_RecuentistaHogaresAsignados1 pantalla =
        new P14_RecuentistaHogaresAsignados1();

P14_RecuentistaHogaresAsignadosControlador controladorP14 =
        new P14_RecuentistaHogaresAsignadosControlador(
                pantalla,
                cedulaRecuentista
        );

javax.swing.JPanel formulario =
        pantalla.getPanelHogaresAsignados();

vista.mostrarFormulario(formulario);

pantalla.dispose();
}

    
    public void cerrarSesion() {
        
        int respuesta = JOptionPane.showConfirmDialog(
                vista,
                "¿Está seguro de que desea cerrar sesión?",
                "Confirmar cierre de sesión",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        
        if (respuesta == JOptionPane.YES_OPTION) {
            
            SesionUsuario.cerrar();
            
            iniciarSesion ventana = new iniciarSesion();
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
            
            vista.dispose();
        }
    }
    
    private boolean verificarConexion() {
        
        if (!SesionUsuario.estaActiva()) {
            
            JOptionPane.showMessageDialog(
                    vista,
                    "No hay una conexión activa con Oracle. Debe iniciar sesión nuevamente.",
                    "Error de conexión",
                    JOptionPane.ERROR_MESSAGE
            );
            
            vista.dispose();
            return false;
        }
        
        return true;
    }
}
//</editor-fold>
