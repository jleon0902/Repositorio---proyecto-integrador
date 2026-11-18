/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

/**
 *
 * @author CONTABILIDAD
 */
import controlador.NavegacionRepresentante;

import controlador.RevisarEncuestaControlador;

public class P5_RevisarEncuesta_Representante extends javax.swing.JFrame {
    
    
    private RevisarEncuestaControlador controlador;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(P5_RevisarEncuesta_Representante.class.getName());

    /**
     * Creates new form P4_GastosDiarios
     */
    public P5_RevisarEncuesta_Representante(NavegacionRepresentante navegacion) {
        initComponents();
        
        
       javax.swing.JScrollPane scroll = new javax.swing.JScrollPane();

    scroll.setViewportView(pnl_revisarencuesta);

    scroll.setVerticalScrollBarPolicy(
        javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
    );

    scroll.setHorizontalScrollBarPolicy(
        javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
    );

    pnl_revisarencuesta.remove(scb_revisarencuesta);

    getContentPane().removeAll();
    getContentPane().setLayout(new java.awt.BorderLayout());

    getContentPane().add(pnl_logos, java.awt.BorderLayout.NORTH);
    getContentPane().add(scroll, java.awt.BorderLayout.CENTER);
    
    setSize(600, 100);
setMinimumSize(new java.awt.Dimension(900, 700));
setLocationRelativeTo(null);
controlador = new RevisarEncuestaControlador(
        this,
        navegacion
);
btn_anterior.addActionListener(e -> controlador.anterior());

btn_continuar.addActionListener(e -> controlador.continuar());

    }
    
    
public void mostrarNombreRepresentante(String nombre) {

    lbl_nombrerepresentante.setText(nombre);
}

public void mostrarGasto(
        modelo.GastoDiarioConsulta gasto,
        int numeroActual,
        int total) {

    lbl_numero.setText(String.valueOf(numeroActual));
    lbl_cantidad_regitros.setText(String.valueOf(total));

    txt_nombreproducto.setText(gasto.getNombreProducto());

    txt_cantidadadquirida.setText(
            gasto.getCantidadAdquirida() == null
            ? ""
            : gasto.getCantidadAdquirida().toString()
    );

    txt_valor.setText(
            gasto.getValor() == null
            ? ""
            : gasto.getValor().toString()
    );

    txt_cantidadequivalencia.setText(
            gasto.getCantidadEquivalencia() == null
            ? ""
            : gasto.getCantidadEquivalencia().toString()
    );

    mostrarValorCombo(cmbox_unidadmedida, gasto.getUnidadMedida());

    mostrarValorCombo(cmb_formaadquisicion, gasto.getFormaAdquisicion());

    mostrarValorCombo(cmb_tipomedida, gasto.getTipoMedida());

    mostrarValorCombo(cmb_unidadmedida, gasto.getUnidadEquivalencia());

    mostrarValorCombo(cmb_lugarcompraarticulo, gasto.getLugarCompra());

    mostrarValorCombo(cmb_frecuenciacompra, gasto.getFrecuenciaCompra());

    btn_hogar.setText(
            "Hogar" + ("Hogar".equalsIgnoreCase(gasto.getTipoGasto())
            ? " ✓" : "")
    );

    btn_personal.setText(
            "Personal" + ("Personal".equalsIgnoreCase(gasto.getTipoGasto())
            ? " ✓" : "")
    );

    bloquearCampos();


    btn_editar.setEnabled(true);
    btn_eliminar.setEnabled(true);

    // Continuar se habilita si existe otro registro.
    btn_continuar.setEnabled(numeroActual < total);

    // Anterior se habilita desde el segundo registro.
    btn_anterior.setEnabled(numeroActual > 1);
}

public void mostrarSinRegistros() {

    lbl_numero.setText("0");
    lbl_cantidad_regitros.setText("0");

    txt_nombreproducto.setText("");
    txt_cantidadadquirida.setText("");
    txt_valor.setText("");
    txt_cantidadequivalencia.setText("");

    mostrarValorCombo(cmbox_unidadmedida, ""); // Unidad adquirida

    mostrarValorCombo(cmb_formaadquisicion, "");

    mostrarValorCombo(cmb_tipomedida, "");

    mostrarValorCombo(cmb_unidadmedida, ""); // Unidad de equivalencia

    mostrarValorCombo(cmb_lugarcompraarticulo, "");

    mostrarValorCombo(cmb_frecuenciacompra, "");

    btn_hogar.setText("Hogar");
    btn_personal.setText("Personal");

    bloquearCampos();

    btn_editar.setEnabled(false);
    btn_continuar.setEnabled(false);
    btn_eliminar.setEnabled(false);
    btn_anterior.setEnabled(false);
}

private void mostrarValorCombo(
        javax.swing.JComboBox<String> combo,
        String valor) {

    String contenido = valor == null ? "" : valor;

    combo.setModel(
            new javax.swing.DefaultComboBoxModel<>(
                    new String[]{contenido}
            )
    );
}

private void bloquearCampos() {

    txt_nombreproducto.setEditable(false);
    txt_cantidadadquirida.setEditable(false);
    txt_valor.setEditable(false);
    txt_cantidadequivalencia.setEditable(false);

    cmbox_unidadmedida.setEnabled(false);
    cmb_formaadquisicion.setEnabled(false);
    cmb_tipomedida.setEnabled(false);
    cmb_unidadmedida.setEnabled(false);
    cmb_lugarcompraarticulo.setEnabled(false);
    cmb_frecuenciacompra.setEnabled(false);

    btn_hogar.setEnabled(false);
    btn_personal.setEnabled(false);
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_logos = new javax.swing.JPanel();
        lbl_dane = new javax.swing.JLabel();
        lbl_enph = new javax.swing.JLabel();
        lbl_representante = new javax.swing.JLabel();
        lbl_dia = new javax.swing.JLabel();
        lbl_dias = new javax.swing.JLabel();
        lblDia1 = new javax.swing.JLabel();
        lbl_nombrerepresentante = new javax.swing.JLabel();
        pnl_revisarencuesta = new javax.swing.JPanel();
        lbl_revisarencuesta = new javax.swing.JLabel();
        lbl_producto = new javax.swing.JLabel();
        scb_revisarencuesta = new javax.swing.JScrollBar();
        lbl_nombreproducto = new javax.swing.JLabel();
        lbl_cantidadadquirida = new javax.swing.JLabel();
        lbl_unidadmedida = new javax.swing.JLabel();
        txt_nombreproducto = new javax.swing.JTextField();
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
        btn_continuar = new javax.swing.JButton();
        lbl_cantidadequivalencia = new javax.swing.JLabel();
        txt_cantidadequivalencia = new javax.swing.JTextField();
        lbl_unidaddmedida = new javax.swing.JLabel();
        cmb_unidadmedida = new javax.swing.JComboBox<>();
        lbl_equivalenciaounidadmedida = new javax.swing.JLabel();
        lbl_formaadquisicion = new javax.swing.JLabel();
        cmb_formaadquisicion = new javax.swing.JComboBox<>();
        btn_editar = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        lbl_de = new javax.swing.JLabel();
        lbl_tipomedida = new javax.swing.JLabel();
        cmb_tipomedida = new javax.swing.JComboBox<>();
        lbl_numero = new javax.swing.JLabel();
        lbl_cantidad_regitros = new javax.swing.JLabel();
        btn_anterior = new javax.swing.JButton();

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
                .addComponent(lbl_dane, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_enph, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnl_logosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_logosLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(pnl_logosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_nombrerepresentante)
                            .addComponent(lbl_representante))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnl_logosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_dia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDia1)
                        .addGap(4, 4, 4)
                        .addComponent(lbl_dias)
                        .addGap(21, 21, 21))))
        );
        pnl_logosLayout.setVerticalGroup(
            pnl_logosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_logosLayout.createSequentialGroup()
                .addGroup(pnl_logosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_logosLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(lbl_dane, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addGroup(pnl_logosLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lbl_enph)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnl_revisarencuesta.setBackground(new java.awt.Color(255, 255, 255));
        pnl_revisarencuesta.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));

        lbl_revisarencuesta.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        lbl_revisarencuesta.setText("Revisar Encuesta");

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

        txt_nombreproducto.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

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
        cmb_frecuenciacompra.addActionListener(this::cmb_frecuenciacompraActionPerformed);

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

        btn_continuar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_continuar.setText("Continuar");

        lbl_cantidadequivalencia.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_cantidadequivalencia.setText("Cantidad de equivalencia:");

        txt_cantidadequivalencia.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        lbl_unidaddmedida.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_unidaddmedida.setText("Unidad de medida estándar:");

        cmb_unidadmedida.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cmb_unidadmedida.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lbl_equivalenciaounidadmedida.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbl_equivalenciaounidadmedida.setText("Equivalencia a unidad de medida estándar");
        lbl_equivalenciaounidadmedida.setToolTipText("");

        lbl_formaadquisicion.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_formaadquisicion.setText("Forma de adquisición:");

        cmb_formaadquisicion.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cmb_formaadquisicion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btn_editar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_editar.setText("Editar");
        btn_editar.addActionListener(this::btn_editarActionPerformed);

        btn_eliminar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_eliminar.setText("Eliminar");
        btn_eliminar.addActionListener(this::btn_eliminarActionPerformed);

        lbl_de.setText("de");

        lbl_tipomedida.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_tipomedida.setText("Tipo de medida:");

        cmb_tipomedida.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cmb_tipomedida.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lbl_numero.setText("jLabel1");

        lbl_cantidad_regitros.setText("jLabel2");

        btn_anterior.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_anterior.setText("Anterior");

        javax.swing.GroupLayout pnl_revisarencuestaLayout = new javax.swing.GroupLayout(pnl_revisarencuesta);
        pnl_revisarencuesta.setLayout(pnl_revisarencuestaLayout);
        pnl_revisarencuestaLayout.setHorizontalGroup(
            pnl_revisarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_revisarencuestaLayout.createSequentialGroup()
                .addGroup(pnl_revisarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_revisarencuestaLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(pnl_revisarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_revisarencuestaLayout.createSequentialGroup()
                                .addComponent(lbl_producto)
                                .addGap(18, 18, 18)
                                .addComponent(lbl_numero, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_de)
                                .addGap(18, 18, 18)
                                .addComponent(lbl_cantidad_regitros, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbl_cantidadadquirida)
                            .addComponent(lbl_nombreproducto)
                            .addComponent(lbl_unidadmedida)
                            .addComponent(lbl_valor, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_frecuenciadecompra)
                            .addComponent(lbl_tipodegasto)
                            .addGroup(pnl_revisarencuestaLayout.createSequentialGroup()
                                .addComponent(btn_cancelar)
                                .addGap(18, 18, 18)
                                .addComponent(btn_eliminar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_editar))
                            .addComponent(lbl_cantidadequivalencia)
                            .addComponent(lbl_unidaddmedida)
                            .addComponent(lbl_lugardecompraarticulo)
                            .addComponent(lbl_formaadquisicion, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_equivalenciaounidadmedida)
                            .addComponent(lbl_tipomedida))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addGroup(pnl_revisarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_revisarencuestaLayout.createSequentialGroup()
                                .addComponent(btn_anterior)
                                .addGap(18, 18, 18)
                                .addComponent(btn_continuar))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_revisarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txt_cantidadadquirida, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                                .addComponent(txt_nombreproducto, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(cmbox_unidadmedida, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmb_formaadquisicion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_valor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_revisarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cmb_tipomedida, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cmb_unidadmedida, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_cantidadequivalencia, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmb_lugarcompraarticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_revisarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_revisarencuestaLayout.createSequentialGroup()
                                    .addComponent(btn_hogar)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_personal))
                                .addComponent(cmb_frecuenciacompra, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_revisarencuestaLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_revisarencuesta)
                        .addGap(232, 232, 232)))
                .addComponent(scb_revisarencuesta, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );
        pnl_revisarencuestaLayout.setVerticalGroup(
            pnl_revisarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_revisarencuestaLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lbl_revisarencuesta, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(pnl_revisarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_producto)
                    .addComponent(lbl_de)
                    .addComponent(lbl_numero)
                    .addComponent(lbl_cantidad_regitros))
                .addGap(26, 26, 26)
                .addGroup(pnl_revisarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_nombreproducto)
                    .addComponent(txt_nombreproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(pnl_revisarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_revisarencuestaLayout.createSequentialGroup()
                        .addComponent(lbl_cantidadadquirida)
                        .addGap(51, 51, 51)
                        .addGroup(pnl_revisarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_unidadmedida)
                            .addComponent(cmbox_unidadmedida, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txt_cantidadadquirida, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(pnl_revisarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_formaadquisicion, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_formaadquisicion))
                .addGap(41, 41, 41)
                .addComponent(lbl_equivalenciaounidadmedida)
                .addGap(36, 36, 36)
                .addGroup(pnl_revisarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_tipomedida)
                    .addComponent(cmb_tipomedida, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(pnl_revisarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_revisarencuestaLayout.createSequentialGroup()
                        .addComponent(cmb_unidadmedida, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addGroup(pnl_revisarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_cantidadequivalencia, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_cantidadequivalencia))
                        .addGap(42, 42, 42)
                        .addGroup(pnl_revisarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmb_lugarcompraarticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_lugardecompraarticulo)))
                    .addComponent(lbl_unidaddmedida))
                .addGap(41, 41, 41)
                .addGroup(pnl_revisarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_valor, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_valor))
                .addGap(45, 45, 45)
                .addGroup(pnl_revisarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cmb_frecuenciacompra, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_frecuenciadecompra))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(pnl_revisarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_personal)
                    .addComponent(btn_hogar)
                    .addComponent(lbl_tipodegasto))
                .addGap(47, 47, 47)
                .addGroup(pnl_revisarencuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_continuar)
                    .addComponent(btn_cancelar)
                    .addComponent(btn_editar)
                    .addComponent(btn_eliminar)
                    .addComponent(btn_anterior))
                .addGap(35, 35, 35))
            .addComponent(scb_revisarencuesta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pnl_revisarencuesta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl_logos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnl_logos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnl_revisarencuesta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
controlador.eliminar();
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        // TODO add your handling code here:
        controlador.cancelar();
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void btn_editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editarActionPerformed
        // TODO add your handling code here:
        
        controlador.editar();
    }//GEN-LAST:event_btn_editarActionPerformed

    private void cmb_frecuenciacompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_frecuenciacompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_frecuenciacompraActionPerformed

   public boolean confirmarEliminacion() {

    int opcion = javax.swing.JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro que desea eliminar este producto?",
            "Confirmar eliminación",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE
    );

    return opcion == javax.swing.JOptionPane.YES_OPTION;
}

public void mostrarMensaje(String mensaje) {

    javax.swing.JOptionPane.showMessageDialog(
            this,
            mensaje
    );
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_anterior;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_continuar;
    private javax.swing.JButton btn_editar;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_hogar;
    private javax.swing.JButton btn_personal;
    private javax.swing.JComboBox<String> cmb_formaadquisicion;
    private javax.swing.JComboBox<String> cmb_frecuenciacompra;
    private javax.swing.JComboBox<String> cmb_lugarcompraarticulo;
    private javax.swing.JComboBox<String> cmb_tipomedida;
    private javax.swing.JComboBox<String> cmb_unidadmedida;
    private javax.swing.JComboBox<String> cmbox_unidadmedida;
    private javax.swing.JLabel lblDia1;
    private javax.swing.JLabel lbl_cantidad_regitros;
    private javax.swing.JLabel lbl_cantidadadquirida;
    private javax.swing.JLabel lbl_cantidadequivalencia;
    private javax.swing.JLabel lbl_dane;
    private javax.swing.JLabel lbl_de;
    private javax.swing.JLabel lbl_dia;
    private javax.swing.JLabel lbl_dias;
    private javax.swing.JLabel lbl_enph;
    private javax.swing.JLabel lbl_equivalenciaounidadmedida;
    private javax.swing.JLabel lbl_formaadquisicion;
    private javax.swing.JLabel lbl_frecuenciadecompra;
    private javax.swing.JLabel lbl_lugardecompraarticulo;
    private javax.swing.JLabel lbl_nombreproducto;
    private javax.swing.JLabel lbl_nombrerepresentante;
    private javax.swing.JLabel lbl_numero;
    private javax.swing.JLabel lbl_producto;
    private javax.swing.JLabel lbl_representante;
    private javax.swing.JLabel lbl_revisarencuesta;
    private javax.swing.JLabel lbl_tipodegasto;
    private javax.swing.JLabel lbl_tipomedida;
    private javax.swing.JLabel lbl_unidaddmedida;
    private javax.swing.JLabel lbl_unidadmedida;
    private javax.swing.JLabel lbl_valor;
    private javax.swing.JPanel pnl_logos;
    private javax.swing.JPanel pnl_revisarencuesta;
    private javax.swing.JScrollBar scb_revisarencuesta;
    private javax.swing.JTextField txt_cantidadadquirida;
    private javax.swing.JTextField txt_cantidadequivalencia;
    private javax.swing.JTextField txt_nombreproducto;
    private javax.swing.JTextField txt_valor;
    // End of variables declaration//GEN-END:variables
}

