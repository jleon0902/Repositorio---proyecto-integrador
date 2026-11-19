/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;


import controlador.EditarEncuestaControlador;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author CONTABILIDAD
 */

public class P6_EditarEncuesta_Representante extends javax.swing.JFrame {
    
    private EditarEncuestaControlador controlador;
    private String tipoGastoSeleccionado;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(P6_EditarEncuesta_Representante.class.getName());

    public P6_EditarEncuesta_Representante() {

    initComponents();
    // Permitir únicamente números y un separador decimal.
    aplicarFiltroDecimal(txt_cantidadadquirida);
    aplicarFiltroDecimal(txt_cantidadequivalencia);

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

    setSize(1000, 800);
    setMinimumSize(new java.awt.Dimension(900, 700));
    setLocationRelativeTo(null);
}
    
    
public void mostrarGasto(
        int numeroOrden,
        Double cantidadAdquirida,
        Double valor,
        Double cantidadEquivalencia,
        String tipoGasto) {

    lblProducto.setText(String.valueOf(numeroOrden));

    txt_cantidadadquirida.setText(
            cantidadAdquirida == null
            ? "" : cantidadAdquirida.toString()
    );

    txt_valor.setText(
            valor == null
            ? "" : valor.toString()
    );

    txt_cantidadequivalencia.setText(
            cantidadEquivalencia == null
            ? "" : cantidadEquivalencia.toString()
    );

    tipoGastoSeleccionado = tipoGasto;

    if ("Hogar".equalsIgnoreCase(tipoGastoSeleccionado)) {
        btn_hogar.setEnabled(false);
        btn_personal.setEnabled(true);

    } else if ("Personal".equalsIgnoreCase(tipoGastoSeleccionado)) {
        btn_personal.setEnabled(false);
        btn_hogar.setEnabled(true);
    }
}
    
public void mostrarNombreRepresentante(String nombre) {
    lbl_nombrerepresentante.setText(nombre);
}

public void mostrarDiaEncuesta(int dia) {
    lblDia1.setText(String.valueOf(dia));
}

  public void cargarOpciones(
        List<String> productos,
        List<String> unidades,
        List<String> formas,
        List<String> tipos,
        List<String> unidadesEquivalencia,
        List<String> lugares,
        List<String> frecuencias,
        String productoSeleccionado,
        String unidadSeleccionada,
        String formaSeleccionada,
        String tipoSeleccionado,
        String unidadEquivalenciaSeleccionada,
        String lugarSeleccionado,
        String frecuenciaSeleccionada) {

    llenarCombo(cmbox_nombre_producto, productos,
            productoSeleccionado, true);

    llenarCombo(cmbox_unidadmedida, unidades,
            unidadSeleccionada, true);

    llenarCombo(cmb_formaadquisicion, formas,
            formaSeleccionada, true);

    llenarCombo(cmb_tipomedida, tipos,
            tipoSeleccionado, false);

    llenarCombo(cmb_medidaEquivalencia, unidadesEquivalencia,
            unidadEquivalenciaSeleccionada, false);

    llenarCombo(cmb_lugarcompraarticulo, lugares,
            lugarSeleccionado, true);

    llenarCombo(cmb_frecuenciacompra, frecuencias,
            frecuenciaSeleccionada, false);
}
    
    
private void llenarCombo(
        javax.swing.JComboBox<String> combo,
        List<String> opciones,
        String seleccionado,
        boolean permitirOtro) {

    combo.removeAllItems();
    combo.addItem("Seleccione");

    String opcionEncontrada = null;

    if (opciones != null) {
        for (String opcion : opciones) {

            combo.addItem(opcion);

            if (seleccionado != null
                    && opcion != null
                    && opcion.trim().equalsIgnoreCase(
                            seleccionado.trim())) {

                opcionEncontrada = opcion;
            }
        }
    }

    if (permitirOtro) {
        combo.addItem("Otro, ¿Cuál?");
    }

    if (opcionEncontrada != null) {
        combo.setSelectedItem(opcionEncontrada);
    } else {
        combo.setSelectedIndex(0);
    }
}

    
    
public void cargarUnidadesEquivalencia(
        List<String> unidades,
        String unidadSeleccionada) {

    cmb_medidaEquivalencia.removeAllItems();
    cmb_medidaEquivalencia.addItem("Seleccione");

    boolean encontrada = false;

    if (unidades != null) {

        for (String unidad : unidades) {

            cmb_medidaEquivalencia.addItem(unidad);

            if (unidadSeleccionada != null
                    && unidad.trim().equalsIgnoreCase(
                            unidadSeleccionada.trim())) {

                encontrada = true;
            }
        }
    }

    if (encontrada) {
        cmb_medidaEquivalencia.setSelectedItem(unidadSeleccionada);
    } else {
        cmb_medidaEquivalencia.setSelectedIndex(0);
    }
}

    
    
public void setControlador(EditarEncuestaControlador controlador) {

    this.controlador = controlador;

        // Producto y lugar permiten letras y números, pero exigen al menos una letra.
    configurarComboOtro(cmbox_nombre_producto, true);
    configurarComboOtro(cmb_lugarcompraarticulo, true);

    // Unidad y forma de adquisición permiten únicamente letras.
    configurarComboOtro(cmbox_unidadmedida, false);
    configurarComboOtro(cmb_formaadquisicion, false);

    cmbox_unidadmedida.addActionListener(e -> {

        if (this.controlador != null) {

            this.controlador.actualizarEstadoUnidad(
                    (String) cmbox_unidadmedida.getSelectedItem()
            );
        }
    });

    cmb_tipomedida.addActionListener(e -> {

        if (this.controlador != null) {

            this.controlador.actualizarTipoMedida(
                    (String) cmb_tipomedida.getSelectedItem()
            );
        }
    });

    cmb_formaadquisicion.addActionListener(e -> {

        if (this.controlador != null) {

            this.controlador.actualizarEstadoForma(
                    (String) cmb_formaadquisicion.getSelectedItem()
            );
        }
    });

    btn_guardar.addActionListener(e -> {
this.controlador.guardar();
       
    });

    btn_hogar.addActionListener(e -> {

        tipoGastoSeleccionado = "Hogar";
        btn_hogar.setEnabled(false);
        btn_personal.setEnabled(true);
    });

    btn_personal.addActionListener(e -> {

        tipoGastoSeleccionado = "Personal";
        btn_personal.setEnabled(false);
        btn_hogar.setEnabled(true);
    });
}

    
    public void habilitarCamposEquivalencia(boolean habilitar) {

    cmb_tipomedida.setEnabled(habilitar);
    cmb_medidaEquivalencia.setEnabled(habilitar);
    txt_cantidadequivalencia.setEnabled(habilitar);
}

public void habilitarCamposCompra(boolean habilitar) {

    cmb_lugarcompraarticulo.setEnabled(habilitar);
    cmb_frecuenciacompra.setEnabled(habilitar);
}

private void configurarComboOtro(
        javax.swing.JComboBox<String> combo,
        boolean permitirNumeros) {

    combo.addActionListener(e -> {

        if (!"Otro, ¿Cuál?".equals(combo.getSelectedItem())) {
            return;
        }

        while (true) {

            String nombre = javax.swing.JOptionPane.showInputDialog(
                    this,
                    "Escriba el nombre de la nueva opción:",
                    "Otra opción",
                    javax.swing.JOptionPane.QUESTION_MESSAGE
            );

            // Si cancela o deja el campo vacío, vuelve a Seleccione.
            if (nombre == null || nombre.trim().isEmpty()) {
                combo.setSelectedIndex(0);
                return;
            }

            nombre = nombre.trim();

            if (esTextoValido(nombre, permitirNumeros)) {

                combo.addItem(nombre);
                combo.setSelectedItem(nombre);
                return;

            } else {

                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        permitirNumeros
                        ? "Ingrese letras y números, pero el nombre "
                          + "debe contener al menos una letra. "
                          + "No se permiten símbolos."
                        : "Ingrese únicamente letras y espacios. "
                          + "No se permiten números ni símbolos.",
                        "Texto incorrecto",
                        javax.swing.JOptionPane.WARNING_MESSAGE
                );
            }
        }
    });
}

private boolean esTextoValido(
        String texto,
        boolean permitirNumeros) {

    if (texto == null || texto.trim().isEmpty()) {
        return false;
    }

    String patron;

    if (permitirNumeros) {

        // Acepta letras, tildes, espacios y números, pero exige una letra.
        patron = "(?=.*\\p{L})[\\p{L}\\p{M}0-9 ]+";

    } else {

        // Acepta únicamente letras, tildes y espacios.
        patron = "(?=.*\\p{L})[\\p{L}\\p{M} ]+";
    }

    return Pattern.matches(patron, texto.trim());
}

public String getProductoSeleccionado() {
    return (String) cmbox_nombre_producto.getSelectedItem();
}

public String getUnidadAdquiridaSeleccionada() {
    return (String) cmbox_unidadmedida.getSelectedItem();
}

public String getFormaAdquisicionSeleccionada() {
    return (String) cmb_formaadquisicion.getSelectedItem();
}

public String getLugarCompraSeleccionado() {
    return (String) cmb_lugarcompraarticulo.getSelectedItem();
}

public String getFrecuenciaSeleccionada() {
    return (String) cmb_frecuenciacompra.getSelectedItem();
}

public String getUnidadEquivalenciaSeleccionada() {
    return (String) cmb_medidaEquivalencia.getSelectedItem();
}

public String getCantidadAdquiridaTexto() {
    return txt_cantidadadquirida.getText();
}

public String getCantidadEquivalenciaTexto() {
    return txt_cantidadequivalencia.getText();
}

public String getValorTexto() {
    return txt_valor.getText();
}

public String getTipoGastoSeleccionado() {
    return tipoGastoSeleccionado;
}

public boolean isEquivalenciaHabilitada() {
    return cmb_tipomedida.isEnabled();
}

public boolean isCompraSeleccionada() {
    return cmb_lugarcompraarticulo.isEnabled();
}
    
public void actualizarTipoGasto(String tipo) {

    tipoGastoSeleccionado = tipo;

    btn_hogar.setEnabled(!"Hogar".equalsIgnoreCase(tipo));
    btn_personal.setEnabled(!"Personal".equalsIgnoreCase(tipo));
}

private void aplicarFiltroDecimal(javax.swing.JTextField campo) {

    AbstractDocument documento =
            (AbstractDocument) campo.getDocument();

    documento.setDocumentFilter(new DocumentFilter() {

        // Permite escribir progresivamente, incluso antes de completar el decimal.
        private final Pattern patron =
                Pattern.compile("\\d*(?:[.,]\\d*)?");

        @Override
        public void insertString(
                FilterBypass fb,
                int offset,
                String texto,
                AttributeSet attr) throws BadLocationException {

            replace(fb, offset, 0, texto, attr);
        }

        @Override
        public void replace(
                FilterBypass fb,
                int offset,
                int length,
                String texto,
                AttributeSet attrs) throws BadLocationException {

            String actual = fb.getDocument().getText(
                    0,
                    fb.getDocument().getLength()
            );

            String nuevo = actual.substring(0, offset)
                    + (texto == null ? "" : texto)
                    + actual.substring(offset + length);

            // Solo actualiza el campo si el contenido cumple el formato.
            if (patron.matcher(nuevo).matches()) {
                fb.replace(offset, length, texto, attrs);
            }
        }
    });
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
        lbl_representante = new javax.swing.JLabel();
        lbl_dia = new javax.swing.JLabel();
        lbl_dias = new javax.swing.JLabel();
        lblDia1 = new javax.swing.JLabel();
        lbl_nombrerepresentante = new javax.swing.JLabel();
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
        btn_cancelar = new javax.swing.JButton();
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
        cmbox_nombre_producto = new javax.swing.JComboBox<>();

        jPasswordField1.setText("jPasswordField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnl_logos.setBackground(new java.awt.Color(250, 211, 219));
        pnl_logos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lbl_dane.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/Logo1.png"))); // NOI18N

        lbl_enph.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/Logo2.png"))); // NOI18N

        lbl_representante.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_representante.setText("Representante: ");

        lbl_dia.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_dia.setText("Día");

        lbl_dias.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_dias.setText("de 14");

        lblDia1.setText("jLabel1");

        lbl_nombrerepresentante.setText("jLabel1");

        javax.swing.GroupLayout pnl_logosLayout = new javax.swing.GroupLayout(pnl_logos);
        pnl_logos.setLayout(pnl_logosLayout);
        pnl_logosLayout.setHorizontalGroup(
            pnl_logosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_logosLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lbl_dane, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnl_logosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_logosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_enph, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(pnl_logosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_representante, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_nombrerepresentante, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_logosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_dia)
                        .addGap(4, 4, 4)
                        .addComponent(lblDia1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_dias)
                        .addGap(21, 21, 21))))
        );
        pnl_logosLayout.setVerticalGroup(
            pnl_logosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_logosLayout.createSequentialGroup()
                .addGroup(pnl_logosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_logosLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(pnl_logosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_dane, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnl_logosLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(lbl_enph)))
                        .addGap(0, 9, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_logosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lbl_representante)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_nombrerepresentante)
                        .addGap(7, 7, 7)
                        .addGroup(pnl_logosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_dia)
                            .addComponent(lbl_dias)
                            .addComponent(lblDia1))))
                .addContainerGap())
        );

        pnl_editarencuesta.setBackground(new java.awt.Color(255, 255, 255));
        pnl_editarencuesta.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));

        lbl_editarencuesta.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        lbl_editarencuesta.setText("Editar Encuesta");

        lbl_producto.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_producto.setText("Producto 1 de");

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

        btn_cancelar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_cancelar.setText("Cancelar");
        btn_cancelar.addActionListener(this::btn_cancelarActionPerformed);

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

        cmbox_nombre_producto.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cmbox_nombre_producto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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
                            .addComponent(btn_cancelar)
                            .addComponent(lbl_cantidadequivalencia)
                            .addComponent(lbl_formaadquisicion, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_unidaddmedida)
                            .addComponent(lbl_tipomedida)
                            .addComponent(lbl_lugardecompraarticulo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                        .addGroup(pnl_editarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                .addGap(50, 50, 50))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_editarencuestaLayout.createSequentialGroup()
                                .addGroup(pnl_editarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbox_nombre_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnl_editarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txt_cantidadadquirida, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                                        .addComponent(cmbox_unidadmedida, 0, 249, Short.MAX_VALUE)
                                        .addComponent(cmb_formaadquisicion, 0, 249, Short.MAX_VALUE)))
                                .addGap(47, 47, 47))))
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
                                    .addComponent(cmbox_nombre_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(btn_cancelar))
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
        controlador.seleccionarTipoGasto("Personal");
    }//GEN-LAST:event_btn_personalActionPerformed

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        // TODO add your handling code here:
        controlador.cancelar();
    }//GEN-LAST:event_btn_cancelarActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_guardar;
    private javax.swing.JButton btn_hogar;
    private javax.swing.JButton btn_personal;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cmb_formaadquisicion;
    private javax.swing.JComboBox<String> cmb_frecuenciacompra;
    private javax.swing.JComboBox<String> cmb_lugarcompraarticulo;
    private javax.swing.JComboBox<String> cmb_medidaEquivalencia;
    private javax.swing.JComboBox<String> cmb_tipomedida;
    private javax.swing.JComboBox<String> cmbox_nombre_producto;
    private javax.swing.JComboBox<String> cmbox_unidadmedida;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JLabel lblDia1;
    private javax.swing.JLabel lblProducto;
    private javax.swing.JLabel lbl_cantidadadquirida;
    private javax.swing.JLabel lbl_cantidadequivalencia;
    private javax.swing.JLabel lbl_dane;
    private javax.swing.JLabel lbl_dia;
    private javax.swing.JLabel lbl_dias;
    private javax.swing.JLabel lbl_editarencuesta;
    private javax.swing.JLabel lbl_enph;
    private javax.swing.JLabel lbl_equivalenciaounidadmedida;
    private javax.swing.JLabel lbl_formaadquisicion;
    private javax.swing.JLabel lbl_frecuenciadecompra;
    private javax.swing.JLabel lbl_lugardecompraarticulo;
    private javax.swing.JLabel lbl_nombreproducto;
    private javax.swing.JLabel lbl_nombrerepresentante;
    private javax.swing.JLabel lbl_producto;
    private javax.swing.JLabel lbl_representante;
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
