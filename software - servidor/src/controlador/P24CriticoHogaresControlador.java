
package controlador;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.P24CriticoHogaresDAO;
import modelo.P24ItemCombo;
import vista.P24_CriticoHogaresEncuestados1;
import modelo.P25CriticoHogaresDAO;
import vista.P25_CriticoHogaresEncuestadosTabla1;

public class P24CriticoHogaresControlador {

    private final P24_CriticoHogaresEncuestados1 vista;
    private final P24CriticoHogaresDAO dao;

    private List<P24ItemCombo> regiones;
    private List<P24ItemCombo> departamentos;
    private List<P24ItemCombo> municipios;

    public P24CriticoHogaresControlador(
            P24_CriticoHogaresEncuestados1 vista,
            Connection conexionUsuario) throws SQLException {

        this.vista = vista;
        this.dao = new P24CriticoHogaresDAO(conexionUsuario);

        configurarEventos();

        cargarTotalHogares();
        cargarRegiones();
    }

    // CONFIGURAR EVENTOS DE LOS COMBOS
    private void configurarEventos() {

        vista.getCmbRegion().addActionListener(e -> {
            cargarDepartamentosSeleccionados();
        });

        vista.getCmbDepartamento().addActionListener(e -> {
            cargarMunicipiosSeleccionados();
        });
    }

    // CARGAR TOTAL DE HOGARES
    private void cargarTotalHogares() {

        try {
            int total = dao.contarHogaresFinalizados();

            vista.mostrarTotalHogares(total);

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "Error al consultar el total de hogares: "
                    + e.getMessage()
            );
        }
    }

    // CARGAR REGIONES
// CARGAR REGIONES
private void cargarRegiones() {

    try {
        regiones = dao.listarRegiones();

        vista.getCmbRegion().removeAllItems();
        vista.getCmbRegion().addItem("Seleccione");

        for (P24ItemCombo region : regiones) {
            vista.getCmbRegion().addItem(
                    region.getNombre()
            );
        }

        // Región habilitada.
        vista.getCmbRegion().setEnabled(true);

        // Los demás combos permanecen bloqueados.
        vista.getCmbDepartamento().removeAllItems();
        vista.getCmbDepartamento().addItem("Seleccione");
        vista.getCmbDepartamento().setEnabled(false);

        vista.getCmbMunicipio().removeAllItems();
        vista.getCmbMunicipio().addItem("Seleccione");
        vista.getCmbMunicipio().setEnabled(false);

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
                vista,
                "Error al cargar las regiones: "
                + e.getMessage()
        );
    }
}

    // CARGAR DEPARTAMENTOS SEGÚN LA REGIÓN
// CARGAR DEPARTAMENTOS SEGÚN LA REGIÓN
private void cargarDepartamentosSeleccionados() {

    int indice = vista.getCmbRegion().getSelectedIndex();

    // Limpiar departamentos y municipios anteriores.
    vista.getCmbDepartamento().removeAllItems();
    vista.getCmbDepartamento().addItem("Seleccione");

    vista.getCmbMunicipio().removeAllItems();
    vista.getCmbMunicipio().addItem("Seleccione");

    // Municipio bloqueado hasta seleccionar departamento.
    vista.getCmbMunicipio().setEnabled(false);

    // Si no hay región seleccionada, bloquear departamento.
    if (indice <= 0 || regiones == null) {

        vista.getCmbDepartamento().setEnabled(false);
        return;
    }

    try {

        int idRegion = regiones.get(indice - 1).getId();

        departamentos = dao.listarDepartamentos(idRegion);

        for (P24ItemCombo departamento : departamentos) {
            vista.getCmbDepartamento().addItem(
                    departamento.getNombre()
            );
        }

        // Ahora se puede seleccionar departamento.
        vista.getCmbDepartamento().setEnabled(true);

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
                vista,
                "Error al cargar los departamentos: "
                + e.getMessage()
        );
    }
}

// CARGAR MUNICIPIOS SEGÚN EL DEPARTAMENTO
private void cargarMunicipiosSeleccionados() {

    int indice = vista.getCmbDepartamento().getSelectedIndex();

    // Limpiar municipios anteriores.
    vista.getCmbMunicipio().removeAllItems();
    vista.getCmbMunicipio().addItem("Seleccione");

    // Si no hay departamento seleccionado, bloquear municipio.
    if (indice <= 0 || departamentos == null) {

        vista.getCmbMunicipio().setEnabled(false);
        return;
    }

    try {

        int idDepartamento =
                departamentos.get(indice - 1).getId();

        municipios = dao.listarMunicipios(idDepartamento);

        for (P24ItemCombo municipio : municipios) {
            vista.getCmbMunicipio().addItem(
                    municipio.getNombre()
            );
        }

        // Ahora se puede seleccionar municipio.
        vista.getCmbMunicipio().setEnabled(true);

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
                vista,
                "Error al cargar los municipios: "
                + e.getMessage()
        );
    }
}

// ABRIR PANTALLA P25
public void abrirPantalla25() {

    int indiceRegion =
            vista.getCmbRegion().getSelectedIndex();

    int indiceDepartamento =
            vista.getCmbDepartamento().getSelectedIndex();

    int indiceMunicipio =
            vista.getCmbMunicipio().getSelectedIndex();

    if (indiceRegion <= 0 || regiones == null) {
        return;
    }

    try {

        // OBTENER ID Y NOMBRE DE LA REGIÓN
        int idRegion = regiones.get(indiceRegion - 1).getId();

        String nombreRegion =
                regiones.get(indiceRegion - 1).getNombre();

        // OBTENER DEPARTAMENTO SI ESTÁ SELECCIONADO
        Integer idDepartamento = null;
        String nombreDepartamento = "Todos";

        if (indiceDepartamento > 0 && departamentos != null) {

            idDepartamento =
                    departamentos.get(indiceDepartamento - 1).getId();

            nombreDepartamento =
                    departamentos.get(indiceDepartamento - 1).getNombre();
        }

        // OBTENER MUNICIPIO SI ESTÁ SELECCIONADO
        Integer idMunicipio = null;
        String nombreMunicipio = "Todos";

        if (indiceMunicipio > 0 && municipios != null) {

            idMunicipio =
                    municipios.get(indiceMunicipio - 1).getId();

            nombreMunicipio =
                    municipios.get(indiceMunicipio - 1).getNombre();
        }

        // CREAR DAO P25 CON LA MISMA CONEXIÓN DE P24
        P25CriticoHogaresDAO daoP25 =
                dao.crearDAOHogaresP25();

        // CREAR PANTALLA P25
        P25_CriticoHogaresEncuestadosTabla1 pantalla =
                new P25_CriticoHogaresEncuestadosTabla1(vista);

        // CREAR CONTROLADOR P25
        new P25CriticoHogaresControlador(
                pantalla,
                daoP25,
                idRegion,
                idDepartamento,
                idMunicipio,
                nombreRegion,
                nombreDepartamento,
                nombreMunicipio
        );

        pantalla.setLocationRelativeTo(null);
        pantalla.setVisible(true);

        vista.setVisible(false);

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                vista,
                "Error al abrir los hogares encuestados: "
                + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
}
