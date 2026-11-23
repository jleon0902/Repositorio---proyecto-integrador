
package controlador;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vista.P18_EncuestadorVerHogar1;
import modelo.SesionUsuario;
import modelo.ResultadoLogin;
import modelo.P17_EncuestadorHogaresAsignadosDAO;
import vista.P17_EncuestadorHogaresAsignados1;

public class P17_EncuestadorHogaresAsignadosControlador {

    private final P17_EncuestadorHogaresAsignados1 vista;
    private final P17_EncuestadorHogaresAsignadosDAO dao;
private final Connection conexionUsuario;
    private final String cedulaEncuestador;

    // CONSTRUCTOR
public P17_EncuestadorHogaresAsignadosControlador(
        P17_EncuestadorHogaresAsignados1 vista,
        Connection conexionUsuario) {

    if (conexionUsuario == null) {
        throw new IllegalStateException(
                "No hay una conexión activa con Oracle."
        );
    }

    this.vista = vista;
    this.conexionUsuario = conexionUsuario;

    this.dao = new P17_EncuestadorHogaresAsignadosDAO(
            conexionUsuario
    );

    ResultadoLogin usuario = SesionUsuario.obtenerUsuario();

    if (usuario == null) {
        throw new IllegalStateException(
                "No existe un usuario con sesión iniciada."
        );
    }

    this.cedulaEncuestador = usuario.getCedula();
}

    // INICIAR CONTROLADOR
    public void iniciar() {

        vista.getBtnVerHogar().addActionListener(
                e -> verHogar()
        );

        cargarHogares();
    }

    // CARGAR HOGARES ASIGNADOS
    private void cargarHogares() {

        try {

            List<Object[]> hogares =
                    dao.obtenerHogaresAsignados(
                            cedulaEncuestador
                    );

            DefaultTableModel modelo =
                    (DefaultTableModel) vista.getTblTabla().getModel();

            modelo.setRowCount(0);

            for (Object[] hogar : hogares) {
                modelo.addRow(hogar);
            }

            vista.getLblHogaresasignadoss().setText(
                    "Cantidad de hogares asignados: "
                    + hogares.size()
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "Error al consultar los hogares asignados: "
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

// VER HOGAR SELECCIONADO
private void verHogar() {

    int fila = vista.getTblTabla().getSelectedRow();

    if (fila == -1) {

        JOptionPane.showMessageDialog(
                vista,
                "Seleccione un hogar de la tabla."
        );

        return;
    }

    P18_EncuestadorVerHogar1 pantalla18 =
            new P18_EncuestadorVerHogar1();

    P18_EncuestadorVerHogarControlador controlador18 =
            new P18_EncuestadorVerHogarControlador(
                    pantalla18,
                    conexionUsuario
            );

    controlador18.iniciar();

    pantalla18.setLocationRelativeTo(vista);
    pantalla18.setVisible(true);
}
}
