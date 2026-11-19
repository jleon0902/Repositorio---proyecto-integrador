
package controlador;

import java.util.ArrayList;
import java.util.List;
import modelo.DatosGastoDiario;
import modelo.GastosDiariosDAO;
import modelo.OpcionCombo;
import vista.P9_RevisarEncuesta_Encuestador;
import vista.P10_EditarEncuesta_Encuestador;

public class EditarEncuestaEncuestadorControlador {

    private P10_EditarEncuesta_Encuestador vistaP10;
    private P9_RevisarEncuesta_Encuestador vistaP9;

    private DatosGastoDiario gastoSeleccionado;
    private Long idGasto;

    private GastosDiariosDAO dao;

public EditarEncuestaEncuestadorControlador(
        P10_EditarEncuesta_Encuestador vistaP10,
        P9_RevisarEncuesta_Encuestador vistaP9,
        DatosGastoDiario gastoSeleccionado,
        Long idGasto) {

    // Guardar la referencia de la pantalla de edición.
    this.vistaP10 = vistaP10;

    // Guardar la referencia de la pantalla anterior.
    this.vistaP9 = vistaP9;

    // Guardar los datos del gasto que se va a editar.
    this.gastoSeleccionado = gastoSeleccionado;

    // Guardar el identificador del gasto.
    this.idGasto = idGasto;

    // Crear el DAO encargado de comunicarse con Oracle.
    this.dao = new GastosDiariosDAO();

    // Registrar la acción del botón Guardar.
    vistaP10.agregarAccionGuardar(
            e -> guardar()
    );

    // Registrar la acción del botón Cancelar.
    vistaP10.agregarAccionCancelar(
            e -> cancelar()
    );

    // Cargar las opciones disponibles para todos los combos.
    cargarCombos();

    // Registrar el evento de cambio de la forma de adquisición.
    vistaP10.agregarAccionCambioFormaAdquisicion(
            e -> actualizarLugarCompra()
    );

    // Registrar el evento de cambio de la unidad de medida.
    vistaP10.agregarAccionCambioUnidadMedida(
            e -> actualizarEquivalencia()
    );

    // Cargar los datos originales del gasto.
    // Esto selecciona el producto, unidad, forma de adquisición,
    // equivalencia, lugar, frecuencia y tipo de gasto.
    cargarDatos();
    
    // Después de cargar la unidad de medida original,
// comprobar si esa unidad tiene tipo de medida.
    actualizarEquivalencia();

    // Registrar el evento de cambio del producto.
    // Se registra después de cargar los datos originales para
    // evitar abrir la ventana de "Otro producto" durante la carga.
    vistaP10.agregarAccionCambioProducto(
            e -> vistaP10.establecerDisponibilidadOtroProducto()
    );

    // Determinar el estado inicial del lugar de compra.
    actualizarLugarCompra();

}
    
    // Actualizar la disponibilidad de los campos de equivalencia.
private void actualizarEquivalencia() {

    String nombreUnidad = vistaP10.getUnidadMedida();

    // Buscar la unidad seleccionada en las opciones de Oracle.
    OpcionCombo unidadSeleccionada = buscarOpcion(
            dao.listarUnidadesMedida(),
            nombreUnidad,
            false
    );

    // Comprobar si la unidad tiene un tipo de medida asociado.
    boolean tieneTipoMedida = unidadSeleccionada != null
            && unidadSeleccionada.getIdTipoMedida() != null;

    // Activar o desactivar los campos de equivalencia.
    vistaP10.establecerDisponibilidadEquivalencia(tieneTipoMedida);
}

    // Convertir las opciones del DAO en nombres para los combos.
    private List<String> obtenerNombres(List<OpcionCombo> opciones) {

        List<String> nombres = new ArrayList<>();

        for (OpcionCombo opcion : opciones) {
            nombres.add(opcion.getNombre());
        }

        return nombres;
    }

    // Cargar las opciones disponibles en la ventana.
    private void cargarCombos() {

        
        vistaP10.cargarProductos(
                obtenerNombres(dao.listarProductos())
        );
        
        vistaP10.cargarUnidadesMedida(
                obtenerNombres(dao.listarUnidadesMedida())
        );

        vistaP10.cargarFormasAdquisicion(
                obtenerNombres(dao.listarFormasAdquisicion())
        );

        vistaP10.cargarTiposMedida(
                obtenerNombres(dao.listarTiposMedida())
        );

        vistaP10.cargarUnidadesEquivalencia(
                obtenerNombres(dao.listarUnidadesEquivalencia())
        );

        vistaP10.cargarLugaresCompra(
                obtenerNombres(dao.listarLugaresCompra())
        );

        vistaP10.cargarFrecuenciasCompra(
                obtenerNombres(dao.listarFrecuenciasCompra())
        );
        
    }

    // Cargar los datos originales del gasto.
    private void cargarDatos() {

        if (gastoSeleccionado == null) {
            vistaP10.mostrarError(
                    "No fue posible cargar la información del gasto."
            );
            return;
        }

        vistaP10.cargarDatosGasto(
                gastoSeleccionado.getProducto(),
                gastoSeleccionado.getCantidadAdquirida(),
                gastoSeleccionado.getUnidadMedida(),
                gastoSeleccionado.getFormaAdquisicion(),
                gastoSeleccionado.getTipoMedida(),
                gastoSeleccionado.getUnidadEquivalencia(),
                gastoSeleccionado.getCantidadEquivalencia(),
                gastoSeleccionado.getLugarCompra(),
                gastoSeleccionado.getValor(),
                gastoSeleccionado.getFrecuenciaCompra(),
                gastoSeleccionado.getTipoGasto()
        );
    }

    
// Actualizar el lugar de compra según la forma seleccionada.
private void actualizarLugarCompra() {

    String forma = vistaP10.getFormaAdquisicion();

    boolean esCompra = forma != null
            && forma.trim().equalsIgnoreCase("Compra");

    vistaP10.establecerDisponibilidadLugarCompra(esCompra);
}

    // Buscar una opción por su nombre.
    private OpcionCombo buscarOpcion(
            List<OpcionCombo> opciones,
            String nombre,
            boolean permitirPersonalizada) {

        if (nombre == null || nombre.trim().isEmpty()) {
            return null;
        }

        // Buscar una opción que ya exista.
        for (OpcionCombo opcion : opciones) {

            if (opcion.getNombre().equalsIgnoreCase(nombre.trim())) {
                return opcion;
            }
        }

        // Preparar una opción personalizada.
        if (permitirPersonalizada) {
            return new OpcionCombo(-2, nombre.trim());
        }

        return null;
    }

    // Guardar las modificaciones del gasto.
    private void guardar() {

        try {

            String nombreProducto = vistaP10.getProducto();
            String cantidadTexto = vistaP10.getCantidadAdquirida();
            String nombreUnidad = vistaP10.getUnidadMedida();
            String nombreForma = vistaP10.getFormaAdquisicion();
            String nombreUnidadEqui = vistaP10.getUnidadEquivalencia();
            String cantidadEquiTexto =
                    vistaP10.getCantidadEquivalencia();
            String nombreLugar = vistaP10.getLugarCompra();
            
            // Determinar si la adquisición corresponde a una compra.
boolean esCompra = nombreForma != null
        && nombreForma.trim().equalsIgnoreCase("Compra");

// El lugar solamente es obligatorio cuando existe una compra.
if (esCompra && (nombreLugar == null || nombreLugar.trim().isEmpty())) {

    vistaP10.mostrarError(
            "Seleccione el lugar de compra."
    );
    return;
}

// Si no es una compra, no se debe guardar un lugar.
if (!esCompra) {
    nombreLugar = null;
}

            String valorTexto = vistaP10.getValor();
            String nombreFrecuencia = vistaP10.getFrecuenciaCompra();
            String tipoGasto = vistaP10.getTipoGasto();

            // Validar los campos obligatorios.
            if (nombreProducto == null || nombreProducto.trim().isEmpty()
                    || cantidadTexto.isEmpty()
                    || nombreUnidad == null
                    || nombreForma == null
                    || valorTexto.isEmpty()
                    || tipoGasto == null) {

                vistaP10.mostrarError(
                        "Complete todos los campos obligatorios."
                );
                return;
            }

            // Convertir los valores numéricos.
            double cantidad = Double.parseDouble(cantidadTexto);
            double valor = Double.parseDouble(valorTexto);

            Double cantidadEqui = null;

            if (!cantidadEquiTexto.isEmpty()) {
                cantidadEqui = Double.parseDouble(cantidadEquiTexto);
            }

            // Buscar las opciones seleccionadas.
            OpcionCombo producto = buscarOpcion(
                    dao.listarProductos(),
                    nombreProducto,
                    true
            );

            OpcionCombo unidad = buscarOpcion(
                    dao.listarUnidadesMedida(),
                    nombreUnidad,
                    true
            );

            OpcionCombo forma = buscarOpcion(
                    dao.listarFormasAdquisicion(),
                    nombreForma,
                    true
            );

            // Resolver el lugar únicamente cuando la adquisición sea una compra.
OpcionCombo lugar = esCompra
        ? buscarOpcion(
                dao.listarLugaresCompra(),
                nombreLugar,
                true
        )
        : null;

            OpcionCombo unidadEqui = buscarOpcion(
                    dao.listarUnidadesEquivalencia(),
                    nombreUnidadEqui,
                    false
            );

            OpcionCombo frecuencia = buscarOpcion(
                    dao.listarFrecuenciasCompra(),
                    nombreFrecuencia,
                    false
            );

            // Comprobar que se identificaron las opciones.
            if (producto == null
                    || unidad == null
                    || forma == null
                    || (nombreUnidadEqui != null && unidadEqui == null)
                    || (nombreFrecuencia != null && frecuencia == null)) {

                vistaP10.mostrarError(
                        "No fue posible identificar una de las opciones seleccionadas."
                );
                return;
            }

            // Actualizar el gasto mediante el DAO.
            boolean actualizado = dao.actualizarGastoDiario(
                    idGasto.intValue(),
                    producto,
                    cantidad,
                    unidad,
                    forma,
                    unidadEqui,
                    cantidadEqui,
                    lugar,
                    valor,
                    frecuencia,
                    tipoGasto
            );

            if (actualizado) {

                vistaP10.mostrarMensaje(
                        "El gasto diario se actualizó correctamente.",
                        "Actualización exitosa"
                );

                vistaP10.dispose();

            } else {

                vistaP10.mostrarError(
                        "No fue posible actualizar el gasto diario."
                );
            }

        } catch (NumberFormatException e) {

            vistaP10.mostrarError(
                    "Verifique que las cantidades y el valor sean numéricos."
            );

        } catch (Exception e) {

            vistaP10.mostrarError(
                    "Ocurrió un error al actualizar el gasto: "
                    + e.getMessage()
            );
        }
    }

    // Cerrar la ventana sin guardar.
    private void cancelar() {
        vistaP10.dispose();
    }
}
