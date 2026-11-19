
package controlador;

import vista.P4_GastosDiarios_Representante;
import modelo.GastosDiariosDAO;
import modelo.OpcionCombo;
import modelo.SesionUsuario;
import modelo.GastoDiarioTemporal;
import java.util.ArrayList;
import javax.swing.JComboBox;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class GastosDiariosControlador {

    private String tipoGasto = null;
    private P4_GastosDiarios_Representante vista;
    private final GastosDiariosDAO dao;

    private int numeroProducto = 1;
    private int cantidadProductos;
    // Almacena el ID de la encuesta del representante actual.
    private int idEncuesta;
    private List<GastoDiarioTemporal> gastosTemporales =
        new ArrayList<>();
    private NavegacionRepresentante navegacion;
    

public GastosDiariosControlador(
        P4_GastosDiarios_Representante vista,
        int cantidadProductos,
        NavegacionRepresentante navegacion) {

    this.vista = vista;
    this.cantidadProductos = cantidadProductos;
    this.navegacion = navegacion;
    this.dao = new GastosDiariosDAO();

    idEncuesta = dao.obtenerIdEncuesta(
            modelo.SesionUsuario.getCedula()
    );

    if (idEncuesta == -1) {

        javax.swing.JOptionPane.showMessageDialog(
                vista,
                "No se encontró una encuesta asociada a este representante.",
                "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE
        );

        return;
    }

    cargarCombos();
    configurarOpcionesOtro();
    configurarFiltroUnidades();
    configurarRestriccionesFormaAdquisicion();
    configurarRestriccionesEquivalencia();
    configurarRestriccionesNumericas();

    actualizarPantalla();
    
    vista.actualizarNombreRepresentante(
    SesionUsuario.getNombre() + " " + SesionUsuario.getApellido()
);

int dia = dao.obtenerDiaEncuesta(SesionUsuario.getCedula());

vista.actualizarDiaEncuesta(dia);
}
    
private void cargarCombos() {

    // Productos: Seleccione + registros + Otro
    cargarCombo(vista.getCmbNombreProducto(),
            dao.listarProductos(), true);

    // Forma de adquisición: Seleccione + registros + Otro
    cargarCombo(vista.getCmbFormaAdquisicion(),
            dao.listarFormasAdquisicion(), true);

    // Frecuencia: Seleccione + registros
    cargarCombo(vista.getCmbFrecuenciaCompra(),
            dao.listarFrecuenciasCompra(), false);

    // Lugar de compra: Seleccione + registros + Otro
    cargarCombo(vista.getCmbLugarCompra(),
            dao.listarLugaresCompra(), true);

    // Tipo de medida: Seleccione + registros
    cargarCombo(vista.getCmbTipoMedida(),
            dao.listarTiposMedida(), false);

    // Unidad de medida: Seleccione + registros + Otro
    cargarCombo(vista.getCmbUnidadMedida(),
            dao.listarUnidadesMedida(), true);

    vista.getCmbUnidadEquivalencia().removeAllItems();
vista.getCmbUnidadEquivalencia().addItem("Seleccione");
}



private void configurarRestriccionesEquivalencia() {

    vista.getCmbUnidadMedida().addActionListener(e -> {
        actualizarCamposEquivalencia();
    });

    vista.getCmbTipoMedida().addActionListener(e -> {
        actualizarEstadoUnidadEquivalencia();
    });

    vista.getCmbUnidadEquivalencia().addActionListener(e -> {
        actualizarEstadoCantidadEquivalencia();
    });

    actualizarCamposEquivalencia();
}


// Permitir letras, números y espacios.
private void permitirLetrasNumeros(JTextField campo) {

    ((AbstractDocument) campo.getDocument()).setDocumentFilter(
        new DocumentFilter() {

            @Override
            public void replace(
                    FilterBypass fb,
                    int offset,
                    int length,
                    String texto,
                    AttributeSet attrs)
                    throws BadLocationException {

                String actual = fb.getDocument().getText(
                        0, fb.getDocument().getLength());

                String nuevo = actual.substring(0, offset)
                        + texto
                        + actual.substring(offset + length);

                if (nuevo.matches("[\\p{L}\\p{N} ]*")) {
                    super.replace(fb, offset, length, texto, attrs);
                }
            }

            @Override
            public void insertString(
                    FilterBypass fb,
                    int offset,
                    String texto,
                    AttributeSet attrs)
                    throws BadLocationException {

                replace(fb, offset, 0, texto, attrs);
            }
        }
    );
}

// Permitir únicamente letras y espacios.
private void permitirSoloLetras(JTextField campo) {

    ((AbstractDocument) campo.getDocument()).setDocumentFilter(
        new DocumentFilter() {

            @Override
            public void replace(
                    FilterBypass fb,
                    int offset,
                    int length,
                    String texto,
                    AttributeSet attrs)
                    throws BadLocationException {

                String actual = fb.getDocument().getText(
                        0, fb.getDocument().getLength());

                String nuevo = actual.substring(0, offset)
                        + texto
                        + actual.substring(offset + length);

                if (nuevo.matches("[\\p{L} ]*")) {
                    super.replace(fb, offset, length, texto, attrs);
                }
            }

            @Override
            public void insertString(
                    FilterBypass fb,
                    int offset,
                    String texto,
                    AttributeSet attrs)
                    throws BadLocationException {

                replace(fb, offset, 0, texto, attrs);
            }
        }
    );
}

// Permitir números y un único punto decimal.
private void permitirNumerosDecimales(JTextField campo) {

    ((AbstractDocument) campo.getDocument()).setDocumentFilter(
        new DocumentFilter() {

            @Override
            public void replace(
                    FilterBypass fb,
                    int offset,
                    int length,
                    String texto,
                    AttributeSet attrs)
                    throws BadLocationException {

                String actual = fb.getDocument().getText(
                        0, fb.getDocument().getLength());

                String nuevo = actual.substring(0, offset)
                        + texto
                        + actual.substring(offset + length);

                if (nuevo.matches("[0-9]*\\.?[0-9]*")) {
                    super.replace(fb, offset, length, texto, attrs);
                }
            }

            @Override
            public void insertString(
                    FilterBypass fb,
                    int offset,
                    String texto,
                    AttributeSet attrs)
                    throws BadLocationException {

                replace(fb, offset, 0, texto, attrs);
            }
        }
    );
}


private void actualizarCamposEquivalencia() {

    Object seleccionado =
            vista.getCmbUnidadMedida().getSelectedItem();

    boolean requiereEquivalencia = false;

    if (seleccionado instanceof OpcionCombo) {

        OpcionCombo unidad = (OpcionCombo) seleccionado;

        if (unidad.getId() == -2) {

            requiereEquivalencia = true;

        } else if (unidad.getId() > 0
                && unidad.getIdTipoMedida() == null) {

            requiereEquivalencia = true;
        }
    }

    vista.habilitarCamposEquivalencia(requiereEquivalencia);
}


private void actualizarEstadoUnidadEquivalencia() {

    Object seleccionado =
            vista.getCmbTipoMedida().getSelectedItem();

    boolean hayTipoMedida =
            seleccionado instanceof OpcionCombo
            && ((OpcionCombo) seleccionado).getId() > 0;

    vista.getCmbUnidadEquivalencia().setEnabled(hayTipoMedida);

    if (!hayTipoMedida) {

        vista.getCmbUnidadEquivalencia().removeAllItems();

        vista.getCmbUnidadEquivalencia().addItem(
                new OpcionCombo(0, "Seleccione")
        );

        vista.getTxtCantidadEquivalencia().setText("");
        vista.getTxtCantidadEquivalencia().setEnabled(false);

    } else {

        actualizarEstadoCantidadEquivalencia();
    }
}




private void actualizarEstadoCantidadEquivalencia() {

    Object seleccionado =
            vista.getCmbUnidadEquivalencia().getSelectedItem();

    boolean hayUnidad =
            seleccionado instanceof OpcionCombo
            && ((OpcionCombo) seleccionado).getId() > 0;

    vista.getTxtCantidadEquivalencia().setEnabled(hayUnidad);

    if (!hayUnidad) {
        vista.getTxtCantidadEquivalencia().setText("");
    }
}


private void cargarCombo(
        javax.swing.JComboBox combo,
        java.util.List<OpcionCombo> opciones,
        boolean permitirOtro) {

    combo.removeAllItems();

    // Primera opción
    combo.addItem(new OpcionCombo(0, "Seleccione"));

    // Registros de Oracle
    for (OpcionCombo opcion : opciones) {
        combo.addItem(opcion);
    }

    // Última opción, cuando corresponda
    if (permitirOtro) {
        combo.addItem(new OpcionCombo(-1, "Otro, ¿Cuál?"));
    }
}

    
private void configurarOpcionOtro(javax.swing.JComboBox combo) {

    combo.addActionListener(e -> {

        Object seleccionado = combo.getSelectedItem();

        if (seleccionado instanceof OpcionCombo) {

            OpcionCombo opcion = (OpcionCombo) seleccionado;

            if (opcion.getId() == -1) {

                String nombre = null;

                // Identificar el tipo de dato que se está ingresando.
                boolean esProducto = combo == vista.getCmbNombreProducto();

                String mensaje = esProducto
                        ? "Ingrese el nombre del producto:"
                        : "Ingrese el dato que desea registrar:";

                while (true) {

                    String respuesta = javax.swing.JOptionPane.showInputDialog(
                            vista,
                            mensaje,
                            "Otra opción",
                            javax.swing.JOptionPane.QUESTION_MESSAGE
                    );

                    // Si cancela, regresar a la opción inicial.
                    if (respuesta == null) {
                        combo.setSelectedIndex(0);
                        return;
                    }

                    nombre = respuesta.trim();

                    if (nombre.isEmpty()) {
                        javax.swing.JOptionPane.showMessageDialog(
                                vista,
                                "Debe ingresar un nombre.",
                                "Dato obligatorio",
                                javax.swing.JOptionPane.WARNING_MESSAGE
                        );
                        continue;
                    }

                    // El producto permite letras y números,
                    // pero debe contener al menos una letra.
                    if (esProducto) {

                        if (!nombre.matches("[\\p{L}0-9 ]+")
                                || !nombre.matches(".*\\p{L}.*")) {

                            javax.swing.JOptionPane.showMessageDialog(
                                    vista,
                                    "El nombre del producto debe contener al menos una letra. "
                                    + "Puede incluir números y espacios.",
                                    "Dato inválido",
                                    javax.swing.JOptionPane.WARNING_MESSAGE
                            );
                            continue;
                        }

                    } else {

                        // Los demás campos solamente permiten letras y espacios.
                        if (!nombre.matches("[\\p{L} ]+")) {

                            javax.swing.JOptionPane.showMessageDialog(
                                    vista,
                                    "Este campo solamente permite letras y espacios.",
                                    "Dato inválido",
                                    javax.swing.JOptionPane.WARNING_MESSAGE
                            );
                            continue;
                        }
                    }

                    // Si el dato es válido, terminar el ciclo.
                    break;
                }

                OpcionCombo nuevaOpcion = new OpcionCombo(-2, nombre);

                combo.insertItemAt(
                        nuevaOpcion,
                        combo.getItemCount() - 1
                );

                combo.setSelectedItem(nuevaOpcion);

                // Actualizar la equivalencia si se agregó una unidad personalizada.
                if (combo == vista.getCmbUnidadMedida()) {
                    actualizarCamposEquivalencia();
                }
            }
        }
    });
}



private void configurarOpcionesOtro() {

    configurarOpcionOtro(vista.getCmbNombreProducto());

    configurarOpcionOtro(vista.getCmbFormaAdquisicion());

    configurarOpcionOtro(vista.getCmbLugarCompra());

    configurarOpcionOtro(vista.getCmbUnidadMedida());
}


private void configurarFiltroUnidades() {

    vista.getCmbTipoMedida().addActionListener(e -> {

        Object seleccionado =
                vista.getCmbTipoMedida().getSelectedItem();

        vista.getCmbUnidadEquivalencia().removeAllItems();
        vista.getCmbUnidadEquivalencia().addItem(
                new OpcionCombo(0, "Seleccione")
        );

        if (seleccionado instanceof OpcionCombo) {

            OpcionCombo tipo = (OpcionCombo) seleccionado;

            if (tipo.getId() > 0) {

                List<OpcionCombo> unidades =
                        dao.listarUnidadesPorTipo(tipo.getId());

                for (OpcionCombo unidad : unidades) {

                    vista.getCmbUnidadEquivalencia().addItem(unidad);
                }
            }
        }

        actualizarEstadoUnidadEquivalencia();
    });
}


    public void siguienteProducto() {

    if (!guardarProducto()) {
        return;
    }

    if (numeroProducto < cantidadProductos) {

        numeroProducto++;

        actualizarPantalla();

        cargarGastoTemporal(numeroProducto - 1);

    } else {

        finalizarGuardado();
    }
}
    
    
private void guardarEstadoTemporal() {

    OpcionCombo producto = obtenerSeleccionTemporal(
            vista.getCmbNombreProducto());

    OpcionCombo unidad = obtenerSeleccionTemporal(
            vista.getCmbUnidadMedida());

    OpcionCombo forma = obtenerSeleccionTemporal(
            vista.getCmbFormaAdquisicion());

    OpcionCombo tipo = obtenerSeleccionTemporal(
            vista.getCmbTipoMedida());

    OpcionCombo unidadEqui = obtenerSeleccionTemporal(
            vista.getCmbUnidadEquivalencia());

    OpcionCombo lugar = obtenerSeleccionTemporal(
            vista.getCmbLugarCompra());

    OpcionCombo frecuencia = obtenerSeleccionTemporal(
            vista.getCmbFrecuenciaCompra());

    Double cantidad = convertirNumeroTemporal(
            vista.getTxtCantidadAdquirida().getText());

    Double valor = convertirNumeroTemporal(
            vista.getTxtValor().getText());

    Double cantidadEqui = convertirNumeroTemporal(
            vista.getTxtCantidadEquivalencia().getText());

    GastoDiarioTemporal gasto = new GastoDiarioTemporal(
            producto,
            cantidad,
            unidad,
            forma,
            tipo,
            unidadEqui,
            cantidadEqui,
            lugar,
            valor,
            frecuencia,
            tipoGasto
    );

    int indice = numeroProducto - 1;

    if (gastosTemporales.size() > indice) {
        gastosTemporales.set(indice, gasto);
    } else {
        gastosTemporales.add(gasto);
    }
}

private OpcionCombo obtenerSeleccionTemporal(JComboBox combo) {

    Object seleccionado = combo.getSelectedItem();

    if (seleccionado instanceof OpcionCombo) {

        OpcionCombo opcion = (OpcionCombo) seleccionado;

        if (opcion.getId() > 0 || opcion.getId() == -2) {
            return opcion;
        }
    }

    return null;
}

private Double convertirNumeroTemporal(String texto) {

    if (texto == null || texto.trim().isEmpty()) {
        return null;
    }

    try {
        return Double.parseDouble(texto.trim().replace(",", "."));
    } catch (NumberFormatException e) {
        return null;
    }
}


    
public void anteriorProducto() {

    if (numeroProducto > 1) {

        // Conservar lo que se lleva diligenciado en el producto actual.
        guardarEstadoTemporal();

        numeroProducto--;

        actualizarPantalla();

        // Recuperar la información del producto anterior.
        cargarGastoTemporal(numeroProducto - 1);
    }
}


    private void actualizarPantalla() {

        vista.actualizarNumeroProducto(numeroProducto);

        vista.mostrarBotonAnterior(numeroProducto > 1);

        if (numeroProducto < cantidadProductos) {

            vista.cambiarTextoBoton("Siguiente");

        } else {

            vista.cambiarTextoBoton("Guardar");
        }
    }

    


private boolean guardarProducto() {

    try {

        // Recuperar las opciones seleccionadas.
        OpcionCombo producto = obtenerSeleccion(
                vista.getCmbNombreProducto());

        OpcionCombo unidad = obtenerSeleccion(
                vista.getCmbUnidadMedida());

        OpcionCombo forma = obtenerSeleccion(
                vista.getCmbFormaAdquisicion());

        OpcionCombo tipo = obtenerSeleccion(
                vista.getCmbTipoMedida());

        OpcionCombo unidadEqui = obtenerSeleccion(
                vista.getCmbUnidadEquivalencia());

        OpcionCombo lugar = obtenerSeleccion(
                vista.getCmbLugarCompra());

        OpcionCombo frecuencia = obtenerSeleccion(
                vista.getCmbFrecuenciaCompra());

        // Validar los campos principales.
        if (!opcionValida(producto)
                || !opcionValida(unidad)
                || !opcionValida(forma)
                || vista.getTxtCantidadAdquirida().getText().trim().isEmpty()
                || vista.getTxtValor().getText().trim().isEmpty()
                || tipoGasto == null) {

            mostrarError("Complete los campos obligatorios.");
            return false;
        }

        // Convertir los valores numéricos.
        double cantidad = Double.parseDouble(
                vista.getTxtCantidadAdquirida().getText().trim());

        double valor = Double.parseDouble(
                vista.getTxtValor().getText().trim());

        if (cantidad <= 0 || valor < 0) {
            mostrarError(
                    "La cantidad debe ser mayor que cero "
                    + "y el valor no puede ser negativo."
            );
            return false;
        }

        // Determinar si la unidad necesita equivalencia.
        boolean requiereEquivalencia =
                unidad.getId() == -2
                || unidad.getIdTipoMedida() == null;

        Double cantidadEqui = null;

        if (requiereEquivalencia) {

            if (tipo == null || tipo.getId() <= 0
                    || unidadEqui == null || unidadEqui.getId() <= 0
                    || vista.getTxtCantidadEquivalencia()
                            .getText().trim().isEmpty()) {

                mostrarError(
                        "Debe ingresar el tipo de medida, "
                        + "la unidad estándar y su equivalencia."
                );
                return false;
            }

            cantidadEqui = Double.parseDouble(
                    vista.getTxtCantidadEquivalencia()
                            .getText().trim());

            if (cantidadEqui <= 0) {
                mostrarError(
                        "La equivalencia debe ser mayor que cero."
                );
                return false;
            }
        }

        // Comprobar si la adquisición es una compra.
        boolean esCompra = forma.getNombre()
                .trim().equalsIgnoreCase("Compra");

        if (esCompra) {

            if (!opcionValida(lugar)
                    || !opcionValida(frecuencia)) {

                mostrarError(
                        "Seleccione el lugar y la frecuencia de compra."
                );
                return false;
            }

        } else {

            // Estos datos no aplican cuando no es una compra.
            lugar = null;
            frecuencia = null;
        }

        // Crear el registro temporal.
        GastoDiarioTemporal gasto = new GastoDiarioTemporal(
                producto,
                cantidad,
                unidad,
                forma,
                requiereEquivalencia ? tipo : null,
                requiereEquivalencia ? unidadEqui : null,
                cantidadEqui,
                lugar,
                valor,
                frecuencia,
                tipoGasto
        );

        // Guardar o actualizar el gasto correspondiente
        // al número de producto actual.
        int indice = numeroProducto - 1;

        if (gastosTemporales.size() > indice) {

            gastosTemporales.set(indice, gasto);

        } else {

            gastosTemporales.add(gasto);
        }

        System.out.println(
                "Gasto " + numeroProducto
                + " almacenado temporalmente."
        );

        return true;

    } catch (NumberFormatException e) {

        mostrarError(
                "Ingrese cantidades y valores numéricos válidos."
        );

        return false;
    }
}


private void guardarTodosLosGastos() {

    // Comprobar que todos los productos estén diligenciados.
    if (gastosTemporales.size() != cantidadProductos
            || gastosTemporales.contains(null)) {

        mostrarError("Hay productos pendientes por diligenciar.");
        return;
    }

    // Enviar todos los gastos a Oracle.
    boolean guardado = dao.guardarGastosDiarios(
            idEncuesta,
            gastosTemporales
    );

    if (guardado) {

        javax.swing.JOptionPane.showMessageDialog(
                vista,
                "Todos los gastos se guardaron correctamente.",
                "Guardado exitoso",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );

        vista.volverPantalla3();

    } else {

        mostrarError(
                "No fue posible guardar los gastos. "
                + "Verifique la conexión y los datos."
        );
    }
}

private void cargarProductoTemporal() {

    if (numeroProducto > gastosTemporales.size()) {
        return;
    }

    GastoDiarioTemporal gasto =
            gastosTemporales.get(numeroProducto - 1);

    if (gasto == null) {
        return;
    }

    // Recuperar las selecciones.
    vista.getCmbNombreProducto().setSelectedItem(
            gasto.getProducto());

    vista.getCmbUnidadMedida().setSelectedItem(
            gasto.getUnidadMedida());

    vista.getCmbFormaAdquisicion().setSelectedItem(
            gasto.getFormaAdquisicion());

    vista.getTxtCantidadAdquirida().setText(
            String.valueOf(gasto.getCantidadAdquirida()));

    vista.getTxtValor().setText(
            String.valueOf(gasto.getValor()));

    // Recuperar equivalencia.
    if (gasto.getTipoMedida() != null) {

        vista.getCmbTipoMedida().setSelectedItem(
                gasto.getTipoMedida());

        vista.getCmbUnidadEquivalencia().setSelectedItem(
                gasto.getUnidadEquivalencia());

        vista.getTxtCantidadEquivalencia().setText(
                String.valueOf(gasto.getCantidadEquivalencia()));

    }

    // Recuperar lugar y frecuencia.
    if (gasto.getLugarCompra() != null) {

        vista.getCmbLugarCompra().setSelectedItem(
                gasto.getLugarCompra());

        vista.getCmbFrecuenciaCompra().setSelectedItem(
                gasto.getFrecuenciaCompra());
    }

    // Recuperar tipo de gasto.
    tipoGasto = gasto.getTipoGasto();

    vista.actualizarSeleccionTipoGasto(tipoGasto);
}

private OpcionCombo obtenerSeleccion(javax.swing.JComboBox combo) {

    Object seleccionado = combo.getSelectedItem();

    if (seleccionado instanceof OpcionCombo) {
        return (OpcionCombo) seleccionado;
    }

    return null;
}

private boolean opcionValida(OpcionCombo opcion) {

    return opcion != null
            && (opcion.getId() > 0 || opcion.getId() == -2);
}

private int obtenerIdReal(OpcionCombo opcion, String tipo) {

    if (opcion == null) {
        return -1;
    }

    if (opcion.getId() > 0) {
        return opcion.getId();
    }

    if (opcion.getId() != -2) {
        return -1;
    }

    switch (tipo) {

        case "producto":
            return dao.insertarProducto(opcion.getNombre());

        case "unidad":
            return dao.insertarUnidadMedida(opcion.getNombre());

        case "forma":
            return dao.insertarFormaAdquisicion(opcion.getNombre());

        case "lugar":
            return dao.insertarLugarCompra(opcion.getNombre());

        default:
            return -1;
    }
}

private void mostrarError(String mensaje) {

    javax.swing.JOptionPane.showMessageDialog(
            vista,
            mensaje,
            "Validación",
            javax.swing.JOptionPane.WARNING_MESSAGE
    );
}

private void finalizarGuardado() {

    // Comprobar que se hayan diligenciado todos los gastos.
    if (gastosTemporales.size() != cantidadProductos) {

        mostrarError(
                "No se han completado todos los productos."
        );

        return;
    }

    // Enviar todos los gastos a Oracle.
    boolean guardado = dao.guardarGastosDiarios(
            idEncuesta,
            gastosTemporales
    );

    if (guardado) {

        javax.swing.JOptionPane.showMessageDialog(
                vista,
                "Todos los gastos se guardaron correctamente.",
                "Guardado exitoso",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );

        // Limpiar la lista temporal después del guardado.
        gastosTemporales.clear();
        
        // Regresar al menú principal del representante.
        vista.volverPantalla3();

    } else {

        mostrarError(
                "No fue posible guardar los gastos. "
                + "Verifique la conexión y los datos."
        );
    }
}

    public void cancelar() {

    // Confirmar si el representante realmente desea cancelar.
    int respuesta = javax.swing.JOptionPane.showConfirmDialog(
            vista,
            "¿Está seguro de que desea cancelar el registro?\n",
            "Confirmar cancelación",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE
    );

    // Regresar al menú solamente si confirma la cancelación.
    if (respuesta == javax.swing.JOptionPane.YES_OPTION) {
        vista.volverPantalla3();
    }
}
    
    private void configurarRestriccionesFormaAdquisicion() {

    vista.getCmbFormaAdquisicion().addActionListener(e -> {
        actualizarCamposCompra();
    });

    actualizarCamposCompra();
}
    
private void actualizarCamposCompra() {

    Object seleccionado =
            vista.getCmbFormaAdquisicion().getSelectedItem();

    boolean esCompra = seleccionado instanceof OpcionCombo
            && ((OpcionCombo) seleccionado).getNombre()
                    .trim().equalsIgnoreCase("Compra");

    vista.getCmbLugarCompra().setEnabled(esCompra);
    vista.getCmbFrecuenciaCompra().setEnabled(esCompra);

    if (!esCompra) {

        vista.getCmbLugarCompra().setSelectedIndex(0);
        vista.getCmbFrecuenciaCompra().setSelectedIndex(0);
    }
}

    public void seleccionarGastoPersonal() {
    tipoGasto = "Personal";
    vista.actualizarSeleccionTipoGasto(tipoGasto);
}

public void seleccionarGastoHogar() {
    tipoGasto = "Hogar";
    vista.actualizarSeleccionTipoGasto(tipoGasto);
}



private void cargarGastoTemporal(int indice) {

    if (indice < 0 || indice >= gastosTemporales.size()) {
        limpiarCampos();
        return;
    }

    GastoDiarioTemporal gasto = gastosTemporales.get(indice);

    // Recuperar producto.
    seleccionarOpcionPorId(
            vista.getCmbNombreProducto(), gasto.getProducto());

    // Recuperar cantidad adquirida.
    vista.getTxtCantidadAdquirida().setText(
            gasto.getCantidadAdquirida() == null
            ? ""
            : String.valueOf(gasto.getCantidadAdquirida()));

    // Recuperar unidad y forma de adquisición.
    seleccionarOpcionPorId(
            vista.getCmbUnidadMedida(), gasto.getUnidadMedida());

    seleccionarOpcionPorId(
            vista.getCmbFormaAdquisicion(), gasto.getFormaAdquisicion());

    // Recuperar tipo de medida.
    if (gasto.getTipoMedida() != null) {

        seleccionarOpcionPorId(
                vista.getCmbTipoMedida(), gasto.getTipoMedida());

        // El evento del tipo carga las unidades correspondientes.
        if (gasto.getUnidadEquivalencia() != null) {

            seleccionarOpcionPorId(
                    vista.getCmbUnidadEquivalencia(),
                    gasto.getUnidadEquivalencia());
        }

    } else {

        vista.getCmbTipoMedida().setSelectedIndex(0);
    }

    // La cantidad se recupera después de seleccionar la unidad.
    vista.getTxtCantidadEquivalencia().setText(
            gasto.getCantidadEquivalencia() == null
            ? ""
            : String.valueOf(gasto.getCantidadEquivalencia()));

    // Recuperar lugar y frecuencia.
    seleccionarOpcionPorId(
            vista.getCmbLugarCompra(), gasto.getLugarCompra());

    seleccionarOpcionPorId(
            vista.getCmbFrecuenciaCompra(), gasto.getFrecuenciaCompra());

    // Recuperar valor.
    vista.getTxtValor().setText(
            gasto.getValor() == null
            ? ""
            : String.valueOf(gasto.getValor()));

    // Recuperar tipo de gasto.
    tipoGasto = gasto.getTipoGasto();
    vista.actualizarSeleccionTipoGasto(tipoGasto);

    // Actualizar restricciones.
    actualizarCamposCompra();
    actualizarCamposEquivalencia();
    actualizarEstadoUnidadEquivalencia();
    actualizarEstadoCantidadEquivalencia();
}

private void seleccionarOpcionPorId(
        JComboBox combo, OpcionCombo opcionBuscada) {

    if (opcionBuscada == null) {
        combo.setSelectedIndex(0);
        return;
    }

    for (int i = 0; i < combo.getItemCount(); i++) {

        Object elemento = combo.getItemAt(i);

        if (elemento instanceof OpcionCombo) {

            OpcionCombo opcion = (OpcionCombo) elemento;

            if (opcion.getId() == opcionBuscada.getId()) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }

    combo.setSelectedIndex(0);
}



private void seleccionarOpcionPorId(
        JComboBox<OpcionCombo> combo, int id) {

    for (int i = 0; i < combo.getItemCount(); i++) {

        OpcionCombo opcion = combo.getItemAt(i);

        if (opcion != null && opcion.getId() == id) {
            combo.setSelectedIndex(i);
            return;
        }
    }
}


private void limpiarCampos() {

    vista.getCmbNombreProducto().setSelectedIndex(0);

    vista.getTxtCantidadAdquirida().setText("");

    vista.getCmbUnidadMedida().setSelectedIndex(0);

    vista.getCmbFormaAdquisicion().setSelectedIndex(0);

    vista.getCmbTipoMedida().setSelectedIndex(0);

    vista.getCmbUnidadEquivalencia().removeAllItems();

    vista.getCmbUnidadEquivalencia().addItem(
            new OpcionCombo(0, "Seleccione")
    );

    vista.getTxtCantidadEquivalencia().setText("");

    vista.getCmbLugarCompra().setSelectedIndex(0);

    vista.getTxtValor().setText("");

    vista.getCmbFrecuenciaCompra().setSelectedIndex(0);

    tipoGasto = null;

    vista.actualizarSeleccionTipoGasto(null);

    actualizarCamposCompra();
    actualizarCamposEquivalencia();
}

private void configurarRestriccionesNumericas() {

    aplicarFiltroNumerico(vista.getTxtCantidadAdquirida());

    aplicarFiltroNumerico(vista.getTxtCantidadEquivalencia());

    aplicarFiltroNumerico(vista.getTxtValor());
}

private void aplicarFiltroNumerico(javax.swing.JTextField campo) {

    ((AbstractDocument) campo.getDocument()).setDocumentFilter(
        new DocumentFilter() {

            @Override
            public void replace(
                    FilterBypass fb,
                    int offset,
                    int length,
                    String texto,
                    AttributeSet attrs)
                    throws BadLocationException {

                String actual = fb.getDocument().getText(
                        0, fb.getDocument().getLength());

                String nuevo = actual.substring(0, offset)
                        + texto
                        + actual.substring(offset + length);

                if (nuevo.matches("[0-9]*([.,][0-9]*)?")) {
                    super.replace(fb, offset, length, texto, attrs);
                }
            }

            @Override
            public void insertString(
                    FilterBypass fb,
                    int offset,
                    String texto,
                    AttributeSet attrs)
                    throws BadLocationException {

                replace(fb, offset, 0, texto, attrs);
            }
        }
    );
}

// Verifica que el texto contenga únicamente letras y espacios.
private boolean contieneSoloLetras(String texto) {
    return texto != null && texto.matches("[\\p{L} ]+");
}

// Verifica que el producto tenga al menos una letra
// y permita también números y espacios.
private boolean nombreProductoValido(String texto) {
    return texto != null
            && texto.matches("[\\p{L}0-9 ]+")
            && texto.matches(".*\\p{L}.*");
}
}
