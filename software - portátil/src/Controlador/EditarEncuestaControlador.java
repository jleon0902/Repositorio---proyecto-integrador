
package controlador;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import modelo.SesionUsuario;
import modelo.GastoDiarioConsulta;
import modelo.GastosDiariosDAO;
import modelo.OpcionCombo;

import vista.P6_EditarEncuesta_Representante;

public class EditarEncuestaControlador {

    private final P6_EditarEncuesta_Representante vista;
    private final GastoDiarioConsulta gasto;
    private final GastosDiariosDAO dao;
    private final NavegacionRepresentante navegacion;

    private List<OpcionCombo> productos;
    private List<OpcionCombo> unidadesMedida;
    private List<OpcionCombo> formas;
    private List<OpcionCombo> tipos;
    private List<OpcionCombo> unidadesEquivalencia;
    private List<OpcionCombo> lugares;
    private List<OpcionCombo> frecuencias;

    public EditarEncuestaControlador(
            P6_EditarEncuesta_Representante vista,
            GastoDiarioConsulta gasto,
            NavegacionRepresentante navegacion) {

        this.vista = vista;
        this.gasto = gasto;
        this.dao = new GastosDiariosDAO();
        this.navegacion = navegacion;

        cargarCombos();
        cargarDatos();

        vista.mostrarNombreRepresentante(
                SesionUsuario.getNombre() + " "
                + SesionUsuario.getApellido()
        );

        vista.setControlador(this);

        actualizarEstados();

        // Cargar las unidades correspondientes al tipo
        // que ya tenía seleccionado el gasto.
        actualizarTipoMedida(
                gasto.getTipoMedida(),
                gasto.getUnidadEquivalencia()
        );
    }

    private List<String> obtenerNombres(List<OpcionCombo> opciones) {

        List<String> nombres = new ArrayList<>();

        if (opciones != null) {
            for (OpcionCombo opcion : opciones) {
                nombres.add(opcion.getNombre());
            }
        }

        return nombres;
    }

    private void cargarCombos() {

        productos = dao.listarProductos();
        unidadesMedida = dao.listarUnidadesMedida();
        formas = dao.listarFormasAdquisicion();
        tipos = dao.listarTiposMedida();
        unidadesEquivalencia = dao.listarUnidadesEquivalencia();
        lugares = dao.listarLugaresCompra();
        frecuencias = dao.listarFrecuenciasCompra();

        vista.cargarOpciones(
                obtenerNombres(productos),
                obtenerNombres(unidadesMedida),
                obtenerNombres(formas),
                obtenerNombres(tipos),
                obtenerNombres(unidadesEquivalencia),
                obtenerNombres(lugares),
                obtenerNombres(frecuencias),

                gasto.getNombreProducto(),
                gasto.getUnidadMedida(),
                gasto.getFormaAdquisicion(),
                gasto.getTipoMedida(),
                gasto.getUnidadEquivalencia(),
                gasto.getLugarCompra(),
                gasto.getFrecuenciaCompra()
        );
    }

    private void cargarDatos() {

        vista.mostrarGasto(
                gasto.getNumeroOrden(),
                gasto.getCantidadAdquirida(),
                gasto.getValor(),
                gasto.getCantidadEquivalencia(),
                gasto.getTipoGasto()
        );
    }

    public void actualizarEstadoUnidad(String nombreUnidad) {

        boolean habilitar = false;

        if (nombreUnidad != null
                && !nombreUnidad.trim().isEmpty()
                && !nombreUnidad.equalsIgnoreCase("Seleccione")) {

            if (nombreUnidad.equalsIgnoreCase("Otro, ¿Cuál?")) {

                habilitar = true;

            } else {

                OpcionCombo unidad = buscarPorNombre(
                        unidadesMedida,
                        nombreUnidad
                );

                if (unidad != null) {
                    habilitar = unidad.getIdTipoMedida() == null;
                }
            }
        }

        vista.habilitarCamposEquivalencia(habilitar);
    }

    public void actualizarEstadoForma(String forma) {

        boolean esCompra = forma != null
                && forma.trim().equalsIgnoreCase("Compra");

        vista.habilitarCamposCompra(esCompra);
    }

    private void actualizarEstados() {

        actualizarEstadoUnidad(
                vista.getUnidadAdquiridaSeleccionada()
        );

        actualizarEstadoForma(
                vista.getFormaAdquisicionSeleccionada()
        );
    }

    private OpcionCombo buscarPorNombre(
            List<OpcionCombo> opciones,
            String nombre) {

        if (opciones == null || nombre == null) {
            return null;
        }

        for (OpcionCombo opcion : opciones) {

            if (opcion.getNombre() != null
                    && opcion.getNombre().trim()
                            .equalsIgnoreCase(nombre.trim())) {

                return opcion;
            }
        }

        return null;
    }

    private OpcionCombo obtenerOpcion(
            List<OpcionCombo> opciones,
            String nombre,
            boolean permitirPersonalizada) {

        if (nombre == null
                || nombre.trim().isEmpty()
                || nombre.equalsIgnoreCase("Seleccione")
                || nombre.equalsIgnoreCase("Otro, ¿Cuál?")) {

            return null;
        }

        OpcionCombo existente = buscarPorNombre(opciones, nombre);

        if (existente != null) {
            return existente;
        }

        if (permitirPersonalizada) {
            return new OpcionCombo(-2, nombre.trim());
        }

        return null;
    }

    private Double convertirNumero(String texto, String campo)
            throws NumberFormatException {

        if (texto == null || texto.trim().isEmpty()) {

            throw new NumberFormatException(
                    "Debe ingresar " + campo + "."
            );
        }

        return Double.parseDouble(
                texto.trim().replace(",", ".")
        );
    }

    public void guardar() {

        try {
            
             // Obtener los textos seleccionados en la pantalla.
        String nombreProducto = vista.getProductoSeleccionado();
        String nombreUnidad = vista.getUnidadAdquiridaSeleccionada();
        String nombreForma = vista.getFormaAdquisicionSeleccionada();
        String nombreLugar = vista.getLugarCompraSeleccionado();

        OpcionCombo producto = obtenerOpcion(
                productos,
                nombreProducto,
                true
        );

            OpcionCombo unidad = obtenerOpcion(
                    unidadesMedida,
                    vista.getUnidadAdquiridaSeleccionada(),
                    true
            );

            OpcionCombo forma = obtenerOpcion(
                    formas,
                    vista.getFormaAdquisicionSeleccionada(),
                    true
            );

            OpcionCombo lugar = obtenerOpcion(
                    lugares,
                    vista.getLugarCompraSeleccionado(),
                    true
            );

            OpcionCombo frecuencia = obtenerOpcion(
                    frecuencias,
                    vista.getFrecuenciaSeleccionada(),
                    false
            );

            OpcionCombo unidadEquivalencia = obtenerOpcion(
                    unidadesEquivalencia,
                    vista.getUnidadEquivalenciaSeleccionada(),
                    false
            );

            if (producto == null || unidad == null || forma == null) {

                JOptionPane.showMessageDialog(
                        vista,
                        "Seleccione el producto, la unidad de medida "
                        + "y la forma de adquisición.",
                        "Datos incompletos",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            // Validar que la cantidad adquirida tenga un formato numérico correcto.
            String textoCantidad = vista.getCantidadAdquiridaTexto();

            if (!validarDecimal(textoCantidad)) {
                JOptionPane.showMessageDialog(
                        vista,
                        "La cantidad adquirida solo debe contener números "
                        + "y un separador decimal opcional.",
                        "Dato incorrecto",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            double cantidad = convertirNumero(
                    textoCantidad,
                    "la cantidad adquirida"
            );

            double valor = convertirNumero(
                    vista.getValorTexto(),
                    "el valor"
            );

            Double cantidadEquivalencia = null;

            if (vista.isEquivalenciaHabilitada()) {

                if (unidadEquivalencia == null) {

                    JOptionPane.showMessageDialog(
                            vista,
                            "Seleccione la unidad estándar de equivalencia.",
                            "Dato incompleto",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }

            // Validar la cantidad de equivalencia antes de convertirla.
            String textoEquivalencia = vista.getCantidadEquivalenciaTexto();

            if (!validarDecimal(textoEquivalencia)) {
                JOptionPane.showMessageDialog(
                        vista,
                        "La cantidad de equivalencia solo debe contener números "
                        + "y un separador decimal opcional.",
                        "Dato incorrecto",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            cantidadEquivalencia = convertirNumero(
                    textoEquivalencia,
                    "la cantidad de equivalencia"
            );

            } else {

                unidadEquivalencia = null;
            }

            if (!vista.isCompraSeleccionada()) {

                lugar = null;
                frecuencia = null;

            } else if (lugar == null || frecuencia == null) {

                JOptionPane.showMessageDialog(
                        vista,
                        "Seleccione el lugar y la frecuencia de compra.",
                        "Datos incompletos",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            String tipoGasto = vista.getTipoGastoSeleccionado();

            if (tipoGasto == null || tipoGasto.trim().isEmpty()) {

                JOptionPane.showMessageDialog(
                        vista,
                        "Seleccione si el gasto es del hogar o personal.",
                        "Dato incompleto",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            boolean actualizado = dao.actualizarGastoDiario(
                    gasto.getId(),
                    producto,
                    cantidad,
                    unidad,
                    forma,
                    unidadEquivalencia,
                    cantidadEquivalencia,
                    lugar,
                    valor,
                    frecuencia,
                    tipoGasto
            );

            if (actualizado) {

                JOptionPane.showMessageDialog(
                        vista,
                        "El gasto diario se actualizó correctamente."
                );

                vista.dispose();
                navegacion.volverARevisarEncuesta();

            } else {

                JOptionPane.showMessageDialog(
                        vista,
                        "No fue posible actualizar el gasto diario.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "Verifique las cantidades y el valor. "
                    + "Deben ser números válidos.",
                    "Dato incorrecto",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "Ocurrió un error al actualizar el gasto: "
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void cancelar() {

        vista.dispose();
        navegacion.volverARevisarEncuesta();
    }

    public void actualizarTipoMedida(String nombreTipo) {

        actualizarTipoMedida(nombreTipo, null);
    }

    private void actualizarTipoMedida(
            String nombreTipo,
            String unidadEquivalenciaSeleccionada) {

        if (nombreTipo == null
                || nombreTipo.trim().isEmpty()
                || nombreTipo.equalsIgnoreCase("Seleccione")) {

            vista.cargarUnidadesEquivalencia(
                    new ArrayList<>(),
                    null
            );

            return;
        }

        OpcionCombo tipo = buscarPorNombre(tipos, nombreTipo);

        if (tipo == null) {

            vista.cargarUnidadesEquivalencia(
                    new ArrayList<>(),
                    null
            );

            return;
        }

        // Consultar en Oracle únicamente las unidades
        // cuya FK ID_TIPO_MEDIDA corresponde al tipo seleccionado.
        List<OpcionCombo> unidadesFiltradas =
                dao.listarUnidadesPorTipo(tipo.getId());

        vista.cargarUnidadesEquivalencia(
                obtenerNombres(unidadesFiltradas),
                unidadEquivalenciaSeleccionada
        );
    }
    
    public void seleccionarTipoGasto(String tipo) {
    vista.actualizarTipoGasto(tipo);
}
    
    private boolean validarDecimal(String texto) {

    if (texto == null || texto.trim().isEmpty()) {
        return false;
    }

    // Exige números y permite un único separador decimal.
    return texto.trim().matches("\\d+(?:[.,]\\d+)?");
}

private boolean validarTexto(
        String texto,
        boolean permitirNumeros) {

    if (texto == null || texto.trim().isEmpty()) {
        return false;
    }

    String patron;

    if (permitirNumeros) {
        patron = "(?=.*\\p{L})[\\p{L}\\p{M}0-9 ]+";
    } else {
        patron = "(?=.*\\p{L})[\\p{L}\\p{M} ]+";
    }

    return texto.trim().matches(patron);
}
}
