
package controlador;

import modelo.HogaresRecuentistaDAO;
import modelo.SesionUsuario;
import modelo.ResultadoLogin;
import vista.P10_RecuentistaVerHogares1;

import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class P10_RecuentistaVerHogaresControlador {

    private final P10_RecuentistaVerHogares1 vista;
    private final HogaresRecuentistaDAO dao;

    public P10_RecuentistaVerHogaresControlador(
            P10_RecuentistaVerHogares1 vista) {

        this.vista = vista;
        this.dao = new HogaresRecuentistaDAO();
    }

    public void cargarHogares() {

        try {

            ResultadoLogin usuario =
                    SesionUsuario.obtenerUsuario();

            if (usuario == null) {
                throw new SQLException(
                    "No se encontró el usuario de la sesión."
                );
            }

            String cedula = usuario.getCedula();

            if (cedula == null || cedula.trim().isEmpty()) {
                throw new SQLException(
                    "No se encontró la cédula del recuentista."
                );
            }

            List<Object[]> hogares =
                    dao.consultarHogares(cedula);

            DefaultTableModel modelo =
                    (DefaultTableModel) vista
                            .getTablaHogares().getModel();

            modelo.setRowCount(0);

            int numero = 1;

            for (Object[] hogar : hogares) {

                modelo.addRow(new Object[]{
                    numero++,
                    hogar[0],
                    hogar[1],
                    hogar[2],
                    hogar[3]
                });
            }

            if (hogares.isEmpty()) {
                JOptionPane.showMessageDialog(
                    vista,
                    "No se encontraron hogares registrados para este recuentista."
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                vista,
                "Error al consultar los hogares: "
                        + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
