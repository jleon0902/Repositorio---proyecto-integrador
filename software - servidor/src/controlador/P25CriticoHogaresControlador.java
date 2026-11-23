
package controlador;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import modelo.P25CriticoHogaresDAO;
import modelo.P25HogarItem;
import vista.P25_CriticoHogaresEncuestadosTabla1;

public class P25CriticoHogaresControlador {

    private final P25_CriticoHogaresEncuestadosTabla1 vista;
    private final P25CriticoHogaresDAO dao;

    public P25CriticoHogaresControlador(
            P25_CriticoHogaresEncuestadosTabla1 vista,
            P25CriticoHogaresDAO dao,
            Integer idRegion,
            Integer idDepartamento,
            Integer idMunicipio,
            String nombreRegion,
            String nombreDepartamento,
            String nombreMunicipio) {

        this.vista = vista;
        this.dao = dao;

        configurarVista(
                nombreRegion,
                nombreDepartamento,
                nombreMunicipio
        );

        cargarHogares(
                idRegion,
                idDepartamento,
                idMunicipio
        );
    }

    // MOSTRAR NOMBRES DE UBICACIÓN
    private void configurarVista(
            String region,
            String departamento,
            String municipio) {

        vista.mostrarUbicacion(
                region,
                departamento,
                municipio
        );
    }

    // CARGAR HOGARES EN LA TABLA
    private void cargarHogares(
            Integer idRegion,
            Integer idDepartamento,
            Integer idMunicipio) {

        try {

            List<P25HogarItem> hogares =
                    dao.listarHogaresEncuestados(
                            idRegion,
                            idDepartamento,
                            idMunicipio
                    );
            
            System.out.println("HOGARES RECIBIDOS: " + hogares.size());

for (P25HogarItem hogar : hogares) {
    System.out.println(
        hogar.getNumeroVivienda() + " - "
        + hogar.getResponsableHogar() + " - "
        + hogar.getFechaFin()
    );
}

            DefaultTableModel modelo =
                    (DefaultTableModel) vista
                            .getTblTablaFamiliasEncuesta()
                            .getModel();

            modelo.setRowCount(0);

            int numero = 1;

            for (P25HogarItem hogar : hogares) {

                modelo.addRow(new Object[]{
                    numero++,
                    hogar.getNumeroVivienda(),
                    hogar.getResponsableHogar(),
                    hogar.getFechaFin(),
                    false
                });
            }

        } catch (java.sql.SQLException e) {

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
