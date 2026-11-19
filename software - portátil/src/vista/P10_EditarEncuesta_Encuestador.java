/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

public class P10_EditarEncuesta_Encuestador extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(P10_EditarEncuesta_Encuestador.class.getName());

    private String productoOtro = "";
    /**
     * Creates new form P4_GastosDiarios
     */
    
    public P10_EditarEncuesta_Encuestador() {
    initComponents();
    
    // Permitir desplazamiento con el scroll cuando las listas tengan
// muchas opciones.
cmb_nombreProducto.setMaximumRowCount(8);
cmbox_unidadmedida.setMaximumRowCount(8);
cmb_formaadquisicion.setMaximumRowCount(8);
cmb_tipomedida.setMaximumRowCount(8);
cmb_medidaEquivalencia.setMaximumRowCount(8);
cmb_lugarcompraarticulo.setMaximumRowCount(8);
cmb_frecuenciacompra.setMaximumRowCount(8);

    // Permitir cerrar esta ventana sin cerrar toda la aplicación.
    setDefaultCloseOperation(
        javax.swing.WindowConstants.DISPOSE_ON_CLOSE
    );

    javax.swing.JScrollPane scroll = new javax.swing.JScrollPane();

    scroll.setViewportView(pnl_editarencuesta);

    scroll.setVerticalScrollBarPolicy(
        javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
    );

    scroll.setHorizontalScrollBarPolicy(
        javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
    );

    pnl_editarencuesta.remove(scb_editarencuesta);

    getContentPane().removeAll();
    getContentPane().setLayout(new java.awt.BorderLayout());

    getContentPane().add(pnl_logos, java.awt.BorderLayout.NORTH);
    getContentPane().add(scroll, java.awt.BorderLayout.CENTER);

    setSize(600, 100);
    setMinimumSize(new java.awt.Dimension(900, 700));
    setLocationRelativeTo(null);
}
    
    
    // Tipo de gasto seleccionado por el encuestador.
    private String tipoGastoSeleccionado = null;

    // Cargar los datos actuales del gasto en los campos de la pantalla.
    public void cargarDatosGasto(
            String producto,
            double cantidad,
            String unidad,
            String formaAdquisicion,
            String tipoMedida,
            String unidadEquivalencia,
            Double cantidadEquivalencia,
            String lugarCompra,
            double valor,
            String frecuencia,
            String tipoGasto) {

seleccionarProducto(producto);

        txt_cantidadadquirida.setText(
                String.valueOf(cantidad)
        );

        seleccionarOpcion(cmbox_unidadmedida, unidad);

        seleccionarOpcion(
                cmb_formaadquisicion,
                formaAdquisicion
        );
        
        actualizarFormaAdquisicion();

        seleccionarOpcion(cmb_tipomedida, tipoMedida);

        seleccionarOpcion(
                cmb_medidaEquivalencia,
                unidadEquivalencia
        );

        txt_cantidadequivalencia.setText(
                cantidadEquivalencia == null
                ? ""
                : String.valueOf(cantidadEquivalencia)
        );

        seleccionarOpcion(
                cmb_lugarcompraarticulo,
                lugarCompra
        );

        txt_valor.setText(String.valueOf(valor));

        seleccionarOpcion(
                cmb_frecuenciacompra,
                frecuencia
        );

        establecerTipoGasto(tipoGasto);
    }

    // Seleccionar una opción existente en un combo.
    private void seleccionarOpcion(
            javax.swing.JComboBox<String> combo,
            String valor) {

        if (valor == null) {
            combo.setSelectedIndex(-1);
            return;
        }

        combo.setSelectedItem(valor);
    }

    // Cargar las opciones de los combos desde el controlador.
    public void cargarUnidadesMedida(
            java.util.List<String> opciones) {
        cargarCombo(cmbox_unidadmedida, opciones);
    }

    public void cargarFormasAdquisicion(
            java.util.List<String> opciones) {
        cargarCombo(cmb_formaadquisicion, opciones);
    }

    public void cargarTiposMedida(
            java.util.List<String> opciones) {
        cargarCombo(cmb_tipomedida, opciones);
    }

    public void cargarUnidadesEquivalencia(
            java.util.List<String> opciones) {
        cargarCombo(cmb_medidaEquivalencia, opciones);
    }

    public void cargarLugaresCompra(
            java.util.List<String> opciones) {
        cargarCombo(cmb_lugarcompraarticulo, opciones);
    }

    public void cargarFrecuenciasCompra(
            java.util.List<String> opciones) {
        cargarCombo(cmb_frecuenciacompra, opciones);
    }

    // Limpiar y llenar un combo con las opciones recibidas.
    private void cargarCombo(
            javax.swing.JComboBox<String> combo,
            java.util.List<String> opciones) {

        combo.removeAllItems();

        for (String opcion : opciones) {
            combo.addItem(opcion);
        }
    }

    // Establecer el tipo de gasto seleccionado.
    public void establecerTipoGasto(String tipo) {

        tipoGastoSeleccionado = tipo;

        btn_hogar.setEnabled(!"Hogar".equalsIgnoreCase(tipo));

        btn_personal.setEnabled(
                !"Personal".equalsIgnoreCase(tipo)
        );
    }

    // Mostrar el número de producto y el día de seguimiento.
    public void mostrarInformacion(
        int numeroOrden,
        int dia) {

    // Mostrar el número de orden del producto.
    lblProducto.setText(String.valueOf(numeroOrden));
    }  

    public String getCantidadAdquirida() {
        return txt_cantidadadquirida.getText().trim();
    }

    public String getUnidadMedida() {
        return (String) cmbox_unidadmedida.getSelectedItem();
    }

    public String getFormaAdquisicion() {
        return (String) cmb_formaadquisicion.getSelectedItem();
    }

    public String getTipoMedida() {
        return (String) cmb_tipomedida.getSelectedItem();
    }

    public String getUnidadEquivalencia() {
        return (String) cmb_medidaEquivalencia.getSelectedItem();
    }

    public String getCantidadEquivalencia() {
        return txt_cantidadequivalencia.getText().trim();
    }

    public String getLugarCompra() {
        return (String) cmb_lugarcompraarticulo.getSelectedItem();
    }

    public String getValor() {
        return txt_valor.getText().trim();
    }

    public String getFrecuenciaCompra() {
        return (String) cmb_frecuenciacompra.getSelectedItem();
    }

    public String getTipoGasto() {
        return tipoGastoSeleccionado;
    }

    // Registrar las acciones de los botones desde el controlador.
    public void agregarAccionGuardar(
            java.awt.event.ActionListener accion) {
        btn_guardar.addActionListener(accion);
    }

    public void agregarAccionCancelar(
            java.awt.event.ActionListener accion) {
        btn_aceptar.addActionListener(accion);
    }

    public void agregarAccionHogar(
            java.awt.event.ActionListener accion) {
        btn_hogar.addActionListener(accion);
    }

    public void agregarAccionPersonal(
            java.awt.event.ActionListener accion) {
        btn_personal.addActionListener(accion);
    }

    public void agregarAccionCambioTipoMedida(
            java.awt.event.ActionListener accion) {
        cmb_tipomedida.addActionListener(accion);
    }

    public void agregarAccionCambioFormaAdquisicion(
            java.awt.event.ActionListener accion) {
        cmb_formaadquisicion.addActionListener(accion);
    }

    // Mostrar mensajes desde el controlador.
    public void mostrarMensaje(String mensaje, String titulo) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                mensaje,
                titulo,
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void mostrarError(String mensaje) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE
        );
    }

    // Habilitar o deshabilitar el lugar de compra según la forma de adquisición.
public void establecerDisponibilidadLugarCompra(
        boolean disponible) {

    cmb_lugarcompraarticulo.setEnabled(disponible);

    cmb_frecuenciacompra.setEnabled(disponible);

    if (!disponible) {

        cmb_lugarcompraarticulo.setSelectedIndex(-1);

        cmb_frecuenciacompra.setSelectedIndex(-1);
    }
}
    

// Cargar los productos registrados en la base de datos.
public void cargarProductos(java.util.List<String> productos) {

    cmb_nombreProducto.removeAllItems();

    for (String producto : productos) {
        cmb_nombreProducto.addItem(producto);
    }

    // Agregar la opción para escribir un producto diferente.
    cmb_nombreProducto.addItem("Otros, ¿Cuál?");
}

// Seleccionar el producto que ya tenía registrado el gasto.
public void seleccionarProducto(String producto) {

    if (producto == null) {
        cmb_nombreProducto.setSelectedIndex(-1);
        productoOtro = "";
        return;
    }

    boolean encontrado = false;

    for (int i = 0; i < cmb_nombreProducto.getItemCount(); i++) {

        String opcion = cmb_nombreProducto.getItemAt(i);

        // Comprobar si el producto está registrado en el combo.
        if (producto.equalsIgnoreCase(opcion)) {

            cmb_nombreProducto.setSelectedIndex(i);
            encontrado = true;
            productoOtro = "";
            break;
        }
    }

    // Si no está registrado, conservarlo como producto personalizado.
    if (!encontrado) {

        cmb_nombreProducto.setSelectedItem("Otros, ¿Cuál?");
        productoOtro = producto;
    }
}


// Mostrar una ventana emergente para escribir otro producto.
public void establecerDisponibilidadOtroProducto() {

    boolean otro = "Otros, ¿Cuál?".equals(
            cmb_nombreProducto.getSelectedItem()
    );

    if (otro) {

        String producto = javax.swing.JOptionPane.showInputDialog(
                this,
                "Ingrese el nombre del producto:",
                "Otro producto",
                javax.swing.JOptionPane.QUESTION_MESSAGE
        );

        if (producto != null && !producto.trim().isEmpty()) {

            // Conservar el nombre escrito para obtenerlo posteriormente.
            productoOtro = producto.trim();

        } else {

            productoOtro = "";
            cmb_nombreProducto.setSelectedIndex(-1);
        }

    } else {

        productoOtro = "";
    }
}



// Detectar cuando cambia el producto.
public void agregarAccionCambioProducto(
        java.awt.event.ActionListener accion) {

    cmb_nombreProducto.addActionListener(accion);
}

// Obtener el producto seleccionado o escrito.
public String getProducto() {

    if ("Otros, ¿Cuál?".equals(
            cmb_nombreProducto.getSelectedItem())) {

        return productoOtro;
    }

    Object seleccionado = cmb_nombreProducto.getSelectedItem();

    return seleccionado == null ? "" : seleccionado.toString();
}


public void establecerDisponibilidadEquivalencia(
        boolean disponible) {

    cmb_tipomedida.setEnabled(disponible);

    cmb_medidaEquivalencia.setEnabled(disponible);

    txt_cantidadequivalencia.setEnabled(disponible);

    if (!disponible) {

        cmb_tipomedida.setSelectedIndex(-1);

        cmb_medidaEquivalencia.setSelectedIndex(-1);

        txt_cantidadequivalencia.setText("");
    }
}

public void agregarAccionCambioUnidadMedida(
        java.awt.event.ActionListener accion) {

    cmbox_unidadmedida.addActionListener(accion);
}

public void actualizarFormaAdquisicion() {

    Object seleccion = cmb_formaadquisicion.getSelectedItem();

    boolean esCompra = seleccion != null
            && "Compra".equalsIgnoreCase(seleccion.toString());

    establecerDisponibilidadLugarCompra(esCompra);
}



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSlider1 = new javax.swing.JSlider();
        jProgressBar1 = new javax.swing.JProgressBar();
        jSeparator1 = new javax.swing.JSeparator();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPasswordField1 = new javax.swing.JPasswordField();
        pnl_logos = new javax.swing.JPanel();
        lbl_dane = new javax.swing.JLabel();
        lbl_enph = new javax.swing.JLabel();
        lbl_encuestador = new javax.swing.JLabel();
        lbl_nombreencuestador = new javax.swing.JLabel();
        pnl_editarencuesta = new javax.swing.JPanel();
        lbl_editarencuesta = new javax.swing.JLabel();
        lbl_producto = new javax.swing.JLabel();
        scb_editarencuesta = new javax.swing.JScrollBar();
        lbl_nombreproducto = new javax.swing.JLabel();
        lbl_cantidadadquirida = new javax.swing.JLabel();
        lbl_unidadmedida = new javax.swing.JLabel();
        txt_cantidadadquirida = new javax.swing.JTextField();
        cmbox_unidadmedida = new javax.swing.JComboBox<>();
        lbl_lugardecompraarticulo = new javax.swing.JLabel();
        cmb_lugarcompraarticulo = new javax.swing.JComboBox<>();
        lbl_valor = new javax.swing.JLabel();
        lbl_frecuenciadecompra = new javax.swing.JLabel();
        txt_valor = new javax.swing.JTextField();
        cmb_frecuenciacompra = new javax.swing.JComboBox<>();
        lbl_tipodegasto = new javax.swing.JLabel();
        btn_hogar = new javax.swing.JButton();
        btn_personal = new javax.swing.JButton();
        btn_aceptar = new javax.swing.JButton();
        btn_guardar = new javax.swing.JButton();
        lbl_cantidadequivalencia = new javax.swing.JLabel();
        txt_cantidadequivalencia = new javax.swing.JTextField();
        lbl_unidaddmedida = new javax.swing.JLabel();
        cmb_medidaEquivalencia = new javax.swing.JComboBox<>();
        lbl_equivalenciaounidadmedida = new javax.swing.JLabel();
        lbl_formaadquisicion = new javax.swing.JLabel();
        cmb_formaadquisicion = new javax.swing.JComboBox<>();
        lblProducto = new javax.swing.JLabel();
        lbl_tipomedida = new javax.swing.JLabel();
        cmb_tipomedida = new javax.swing.JComboBox<>();
        cmb_nombreProducto = new javax.swing.JComboBox<>();

        jPasswordField1.setText("jPasswordField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnl_logos.setBackground(new java.awt.Color(250, 211, 219));
        pnl_logos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lbl_dane.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/Logo1.png"))); // NOI18N

        lbl_enph.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/Logo2.png"))); // NOI18N

        lbl_encuestador.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_encuestador.setText("Encuestador:");

        lbl_nombreencuestador.setText("jLabel1");

        javax.swing.GroupLayout pnl_logosLayout = new javax.swing.GroupLayout(pnl_logos);
        pnl_logos.setLayout(pnl_logosLayout);
        pnl_logosLayout.setHorizontalGroup(
            pnl_logosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_logosLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lbl_dane, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_enph, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(pnl_logosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_encuestador, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_nombreencuestador, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_logosLayout.setVerticalGroup(
            pnl_logosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_logosLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnl_logosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_dane, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_logosLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(lbl_enph)))
                .addContainerGap(15, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_logosLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lbl_encuestador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_nombreencuestador)
                .addGap(34, 34, 34))
        );

        pnl_editarencuesta.setBackground(new java.awt.Color(255, 255, 255));
        pnl_editarencuesta.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));

        lbl_editarencuesta.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        lbl_editarencuesta.setText("Editar Encuesta");

        lbl_producto.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_producto.setText("Producto");

        lbl_nombreproducto.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_nombreproducto.setText("Nombre del producto o servicio que adquirió:");
        lbl_nombreproducto.setToolTipText("");
        lbl_nombreproducto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lbl_cantidadadquirida.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_cantidadadquirida.setText("Cantidad adquirida:");

        lbl_unidadmedida.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_unidadmedida.setText("Unidad de medida:");

        txt_cantidadadquirida.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        cmbox_unidadmedida.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cmbox_unidadmedida.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lbl_lugardecompraarticulo.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_lugardecompraarticulo.setText("Lugar de compra del artículo o de obtención del servicio:");

        cmb_lugarcompraarticulo.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cmb_lugarcompraarticulo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmb_lugarcompraarticulo.addActionListener(this::cmb_lugarcompraarticuloActionPerformed);

        lbl_valor.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_valor.setText("Valor:");

        lbl_frecuenciadecompra.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_frecuenciadecompra.setText("Describa la frecuencia de compra:");

        txt_valor.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_valor.addActionListener(this::txt_valorActionPerformed);

        cmb_frecuenciacompra.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cmb_frecuenciacompra.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lbl_tipodegasto.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_tipodegasto.setText("Seleccione el tipo de gasto:");

        btn_hogar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_hogar.setText("Hogar");

        btn_personal.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_personal.setText("Personal");
        btn_personal.addActionListener(this::btn_personalActionPerformed);

        btn_aceptar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_aceptar.setText("Cancelar");

        btn_guardar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_guardar.setText("Guardar");

        lbl_cantidadequivalencia.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_cantidadequivalencia.setText("Cantidad en la equivalencia:");

        txt_cantidadequivalencia.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        lbl_unidaddmedida.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_unidaddmedida.setText("Unidad de medida estándar:");

        cmb_medidaEquivalencia.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cmb_medidaEquivalencia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lbl_equivalenciaounidadmedida.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbl_equivalenciaounidadmedida.setText("Equivalencia a unidad de medida estándar (Seleccione la unidad estándar e ingrese la cantidad)");

        lbl_formaadquisicion.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_formaadquisicion.setText("Forma de adquisición:");

        cmb_formaadquisicion.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cmb_formaadquisicion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblProducto.setText("jLabel1");

        lbl_tipomedida.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_tipomedida.setText("Tipo de medida:");

        cmb_tipomedida.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cmb_tipomedida.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmb_nombreProducto.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cmb_nombreProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pnl_editarencuestaLayout = new javax.swing.GroupLayout(pnl_editarencuesta);
        pnl_editarencuesta.setLayout(pnl_editarencuestaLayout);
        pnl_editarencuestaLayout.setHorizontalGroup(
            pnl_editarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_editarencuestaLayout.createSequentialGroup()
                .addGroup(pnl_editarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_editarencuestaLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(pnl_editarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_editarencuestaLayout.createSequentialGroup()
                                .addComponent(lbl_producto)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblProducto))
                            .addComponent(lbl_cantidadadquirida)
                            .addComponent(lbl_nombreproducto)
                            .addComponent(lbl_unidadmedida)
                            .addComponent(lbl_valor, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_frecuenciadecompra)
                            .addComponent(lbl_tipodegasto)
                            .addComponent(btn_aceptar)
                            .addComponent(lbl_cantidadequivalencia)
                            .addComponent(lbl_formaadquisicion, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_unidaddmedida)
                            .addComponent(lbl_tipomedida)
                            .addComponent(lbl_lugardecompraarticulo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnl_editarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_editarencuestaLayout.createSequentialGroup()
                                .addGroup(pnl_editarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_cantidadadquirida, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                                    .addComponent(cmbox_unidadmedida, 0, 249, Short.MAX_VALUE)
                                    .addComponent(cmb_formaadquisicion, 0, 249, Short.MAX_VALUE)
                                    .addComponent(cmb_nombreProducto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(47, 47, 47))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_editarencuestaLayout.createSequentialGroup()
                                .addGroup(pnl_editarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btn_guardar)
                                    .addGroup(pnl_editarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(cmb_medidaEquivalencia, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cmb_tipomedida, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_cantidadequivalencia, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cmb_lugarcompraarticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_valor, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cmb_frecuenciacompra, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(pnl_editarencuestaLayout.createSequentialGroup()
                                            .addComponent(btn_hogar)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btn_personal))))
                                .addGap(50, 50, 50))))
                    .addGroup(pnl_editarencuestaLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(lbl_equivalenciaounidadmedida)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_editarencuestaLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_editarencuesta)
                        .addGap(281, 281, 281)))
                .addComponent(scb_editarencuesta, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnl_editarencuestaLayout.setVerticalGroup(
            pnl_editarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_editarencuestaLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lbl_editarencuesta, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(pnl_editarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnl_editarencuestaLayout.createSequentialGroup()
                        .addGroup(pnl_editarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnl_editarencuestaLayout.createSequentialGroup()
                                .addGroup(pnl_editarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbl_producto)
                                    .addComponent(lblProducto))
                                .addGap(38, 38, 38)
                                .addGroup(pnl_editarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbl_nombreproducto)
                                    .addComponent(cmb_nombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addGroup(pnl_editarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_cantidadadquirida, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_cantidadadquirida))
                                .addGap(46, 46, 46)
                                .addGroup(pnl_editarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbl_unidadmedida)
                                    .addComponent(cmbox_unidadmedida, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(40, 40, 40)
                                .addComponent(cmb_formaadquisicion, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbl_formaadquisicion))
                        .addGap(48, 48, 48)
                        .addComponent(lbl_equivalenciaounidadmedida)
                        .addGap(33, 33, 33)
                        .addGroup(pnl_editarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmb_tipomedida, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_tipomedida))
                        .addGap(37, 37, 37)
                        .addGroup(pnl_editarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_unidaddmedida)
                            .addComponent(cmb_medidaEquivalencia, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addGroup(pnl_editarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_cantidadequivalencia)
                            .addComponent(txt_cantidadequivalencia, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(pnl_editarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmb_lugarcompraarticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_lugardecompraarticulo))
                        .addGap(39, 39, 39)
                        .addComponent(txt_valor, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_valor))
                .addGap(42, 42, 42)
                .addGroup(pnl_editarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_frecuenciacompra, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_frecuenciadecompra))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(pnl_editarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_personal)
                    .addComponent(btn_hogar)
                    .addComponent(lbl_tipodegasto))
                .addGap(37, 37, 37)
                .addGroup(pnl_editarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_guardar)
                    .addComponent(btn_aceptar))
                .addGap(37, 37, 37))
            .addComponent(scb_editarencuesta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_logos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnl_editarencuesta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnl_logos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnl_editarencuesta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmb_lugarcompraarticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_lugarcompraarticuloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_lugarcompraarticuloActionPerformed

    private void txt_valorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_valorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_valorActionPerformed

    private void btn_personalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_personalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_personalActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_aceptar;
    private javax.swing.JButton btn_guardar;
    private javax.swing.JButton btn_hogar;
    private javax.swing.JButton btn_personal;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cmb_formaadquisicion;
    private javax.swing.JComboBox<String> cmb_frecuenciacompra;
    private javax.swing.JComboBox<String> cmb_lugarcompraarticulo;
    private javax.swing.JComboBox<String> cmb_medidaEquivalencia;
    private javax.swing.JComboBox<String> cmb_nombreProducto;
    private javax.swing.JComboBox<String> cmb_tipomedida;
    private javax.swing.JComboBox<String> cmbox_unidadmedida;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JLabel lblProducto;
    private javax.swing.JLabel lbl_cantidadadquirida;
    private javax.swing.JLabel lbl_cantidadequivalencia;
    private javax.swing.JLabel lbl_dane;
    private javax.swing.JLabel lbl_editarencuesta;
    private javax.swing.JLabel lbl_encuestador;
    private javax.swing.JLabel lbl_enph;
    private javax.swing.JLabel lbl_equivalenciaounidadmedida;
    private javax.swing.JLabel lbl_formaadquisicion;
    private javax.swing.JLabel lbl_frecuenciadecompra;
    private javax.swing.JLabel lbl_lugardecompraarticulo;
    private javax.swing.JLabel lbl_nombreencuestador;
    private javax.swing.JLabel lbl_nombreproducto;
    private javax.swing.JLabel lbl_producto;
    private javax.swing.JLabel lbl_tipodegasto;
    private javax.swing.JLabel lbl_tipomedida;
    private javax.swing.JLabel lbl_unidaddmedida;
    private javax.swing.JLabel lbl_unidadmedida;
    private javax.swing.JLabel lbl_valor;
    private javax.swing.JPanel pnl_editarencuesta;
    private javax.swing.JPanel pnl_logos;
    private javax.swing.JScrollBar scb_editarencuesta;
    private javax.swing.JTextField txt_cantidadadquirida;
    private javax.swing.JTextField txt_cantidadequivalencia;
    private javax.swing.JTextField txt_valor;
    // End of variables declaration//GEN-END:variables
}
