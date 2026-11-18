package controlador;

import vista.P3_MenuPrincipal_Representante;
import vista.P4_GastosDiarios_Representante;
import vista.P5_RevisarEncuesta_Representante;
import modelo.SesionUsuario;
import modelo.GastosDiariosConsultaDAO;
import modelo.GastoDiarioConsulta;
import java.util.List;
import javax.swing.JOptionPane;

public class NavegacionRepresentante {

    private P3_MenuPrincipal_Representante vista3;

    public NavegacionRepresentante(
            P3_MenuPrincipal_Representante vista3) {

        this.vista3 = vista3;
    }

    public void abrirGastosDiarios(int cantidadProductos) {

        P4_GastosDiarios_Representante vista4 =
                new P4_GastosDiarios_Representante(
                        cantidadProductos,
                        this
                );

        GastosDiariosControlador controlador =
                new GastosDiariosControlador(
                        vista4,
                        cantidadProductos,
                        this
                );

        vista4.setControlador(controlador);

        vista4.setVisible(true);

        vista3.dispose();
    }

    public void volverARevisarEncuesta() {

    GastosDiariosConsultaDAO dao =
            new GastosDiariosConsultaDAO();

    List<GastoDiarioConsulta> gastos =
            dao.listarPorEncuesta(SesionUsuario.getCedula());

    if (gastos.isEmpty()) {

        JOptionPane.showMessageDialog(
                vista3,
                "No tiene productos registrados para el día de hoy.",
                "Sin productos registrados",
                JOptionPane.INFORMATION_MESSAGE
        );

        return;
    }

    P5_RevisarEncuesta_Representante pantalla =
            new P5_RevisarEncuesta_Representante(this);

    pantalla.setLocationRelativeTo(null);
    pantalla.setVisible(true);
}
    
    public void cerrarSesion() {

        SesionUsuario.cerrarSesion();

        new principal.Main().mostrarInicioSesion();

        vista3.dispose();
    }

    public void volverMenuPrincipal() {

    // Recupera la instancia del menú principal existente.
    vista3.setLocationRelativeTo(null);
    vista3.setVisible(true);


    }
}