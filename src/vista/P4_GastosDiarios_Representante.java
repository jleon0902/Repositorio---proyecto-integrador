
package vista;

/**
 *
 * @author CONTABILIDAD
 */

import controlador.GastosDiariosControlador;
import modelo.SesionUsuario;
import controlador.NavegacionRepresentante;


public class P4_GastosDiarios_Representante extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(P4_GastosDiarios_Representante.class.getName());

    private int cantidadProductos;
    private GastosDiariosControlador controlador;
    private NavegacionRepresentante navegacion;
    /**
     * Creates new form P4_GastosDiarios
     */
    public P4_GastosDiarios_Representante() {
     this(1, null);
}

public P4_GastosDiarios_Representante(
        int cantidadProductos,
        NavegacionRepresentante navegacion) {

    initComponents();

    lbl_nombrerepresentante.setText(
            SesionUsuario.getNombre() + " " +
            SesionUsuario.getApellido()
    );

    this.cantidadProductos = cantidadProductos;
    this.navegacion = navegacion;

    lbl_numero1.setText("1");
    lblProducto.setText(String.valueOf(cantidadProductos));


    javax.swing.JScrollPane scroll = new javax.swing.JScrollPane();

    scroll.setViewportView(pnl_gastosdiarios);

    scroll.setVerticalScrollBarPolicy(
        javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
    );

    scroll.setHorizontalScrollBarPolicy(
        javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
    );

    pnl_gastosdiarios.remove(scb_gastosdiarios);

    getContentPane().removeAll();
    getContentPane().setLayout(new java.awt.BorderLayout());

    getContentPane().add(pnl_logos, java.awt.BorderLayout.NORTH);
    getContentPane().add(scroll, java.awt.BorderLayout.CENTER);

    setSize(935, 700);
    setLocationRelativeTo(null);
}
    
    public void actualizarNumeroProducto(int numero) {
    lbl_numero1.setText(String.valueOf(numero));
    }

    public void mostrarBotonAnterior(boolean mostrar) {
        btn_anterior.setVisible(mostrar);
    }
    
    public void cambiarTextoBoton(String texto) {
    btn_guardar.setText(texto);
    }
    
    
    
public void actualizarSeleccionTipoGasto(String tipoGasto) {

    if ("Personal".equals(tipoGasto)) {

        btn_personal.setBackground(new java.awt.Color(37, 99, 235));
        btn_personal.setForeground(java.awt.Color.WHITE);

        btn_hogar.setBackground(new java.awt.Color(230, 230, 230));
        btn_hogar.setForeground(java.awt.Color.BLACK);

        btn_personal.setEnabled(false);
        btn_hogar.setEnabled(true);

    } else if ("Hogar".equals(tipoGasto)) {

        btn_hogar.setBackground(new java.awt.Color(37, 99, 235));
        btn_hogar.setForeground(java.awt.Color.WHITE);

        btn_personal.setBackground(new java.awt.Color(230, 230, 230));
        btn_personal.setForeground(java.awt.Color.BLACK);

        btn_hogar.setEnabled(false);
        btn_personal.setEnabled(true);

    } else {

        // Ningún tipo de gasto seleccionado.
        btn_personal.setBackground(new java.awt.Color(230, 230, 230));
        btn_personal.setForeground(java.awt.Color.BLACK);

        btn_hogar.setBackground(new java.awt.Color(230, 230, 230));
        btn_hogar.setForeground(java.awt.Color.BLACK);

        btn_personal.setEnabled(true);
        btn_hogar.setEnabled(true);
    }
}
public void setControlador(GastosDiariosControlador controlador) {
    this.controlador = controlador;
}
    

public void volverPantalla3() {
    navegacion.volverMenuPrincipal();
    this.dispose();
}
    
    
public javax.swing.JComboBox getCmbNombreProducto() {
    return cmb_nombreproducto;
}

public javax.swing.JComboBox getCmbFormaAdquisicion() {
    return cmb_formaadquisicion;
}

public javax.swing.JComboBox getCmbFrecuenciaCompra() {
    return cmb_frecuenciacompra;
}

public javax.swing.JComboBox getCmbLugarCompra() {
    return cmb_lugarcompraarticulo;
}

public javax.swing.JComboBox getCmbTipoMedida() {
    return cmb_tipomedida;
}

public javax.swing.JComboBox getCmbUnidadMedida() {
    return cmbox_unidadmedida;
}

public javax.swing.JComboBox getCmbUnidadEquivalencia() {
    return cmb_unidadmedidaequi;
}

public javax.swing.JTextField getTxtCantidadEquivalencia() {
    return txt_cantidadequivalencia;
}

public javax.swing.JTextField getTxtCantidadAdquirida() {
    return txt_cantidadadquirida;
}

public javax.swing.JTextField getTxtValor() {
    return txt_valor;
}


public void habilitarCamposEquivalencia(boolean habilitar) {

    cmb_tipomedida.setEnabled(habilitar);

    if (!habilitar) {

        cmb_tipomedida.setSelectedIndex(0);

        cmb_unidadmedidaequi.removeAllItems();
        cmb_unidadmedidaequi.addItem("Seleccione");

        cmb_unidadmedidaequi.setEnabled(false);

        txt_cantidadequivalencia.setText("");
        txt_cantidadequivalencia.setEnabled(false);

    } else {

        cmb_unidadmedidaequi.setEnabled(false);
        txt_cantidadequivalencia.setEnabled(false);
    }
}



public void habilitarBotonesTipoGasto() {

    btn_hogar.setEnabled(true);
    btn_personal.setEnabled(true);

}

/**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        pnl_logos = new javax.swing.JPanel();
        lbl_dane = new javax.swing.JLabel();
        lbl_enph = new javax.swing.JLabel();
        lbl_representante = new javax.swing.JLabel();
        lbl_dia = new javax.swing.JLabel();
        lbl_dias = new javax.swing.JLabel();
        lblDia = new javax.swing.JLabel();
        lbl_nombrerepresentante = new javax.swing.JLabel();
        pnl_gastosdiarios = new javax.swing.JPanel();
        lbl_gastosdiarios = new javax.swing.JLabel();
        lbl_producto = new javax.swing.JLabel();
        scb_gastosdiarios = new javax.swing.JScrollBar();
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
        btn_Cancelar = new javax.swing.JButton();
        btn_guardar = new javax.swing.JButton();
        lbl_cantidadequivalencia = new javax.swing.JLabel();
        txt_cantidadequivalencia = new javax.swing.JTextField();
        lbl_unidaddmedida = new javax.swing.JLabel();
        cmb_unidadmedidaequi = new javax.swing.JComboBox<>();
        lbl_equivalenciaounidadmedida = new javax.swing.JLabel();
        lbl_formaadquisicion = new javax.swing.JLabel();
        cmb_formaadquisicion = new javax.swing.JComboBox<>();
        lblProducto = new javax.swing.JLabel();
        lbl_tipomedida = new javax.swing.JLabel();
        cmb_tipomedida = new javax.swing.JComboBox<>();
        cmb_nombreproducto = new javax.swing.JComboBox<>();
        btn_anterior = new javax.swing.JButton();
        lbl_numero1 = new javax.swing.JLabel();
        lbl_de = new javax.swing.JLabel();

        jButton1.setText("jButton1");

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

        lblDia.setText("jLabel1");

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
                            .addComponent(lbl_nombrerepresentante))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_logosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_dia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDia)
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
                            .addComponent(lblDia))))
                .addContainerGap())
        );

        pnl_gastosdiarios.setBackground(new java.awt.Color(255, 255, 255));
        pnl_gastosdiarios.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));

        lbl_gastosdiarios.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        lbl_gastosdiarios.setText("Gastos Diarios");

        lbl_producto.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_producto.setText("Producto");

        lbl_nombreproducto.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_nombreproducto.setText("<html>Nombre del producto o servicio<br>que adquirió:</html>");
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
        btn_hogar.addActionListener(this::btn_hogarActionPerformed);

        btn_personal.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_personal.setText("Personal");
        btn_personal.addActionListener(this::btn_personalActionPerformed);

        btn_Cancelar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_Cancelar.setText("Cancelar");
        btn_Cancelar.addActionListener(this::btn_CancelarActionPerformed);

        btn_guardar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_guardar.setText("Guardar");
        btn_guardar.addActionListener(this::btn_guardarActionPerformed);

        lbl_cantidadequivalencia.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_cantidadequivalencia.setText("Cantidad de equivalencia:");

        txt_cantidadequivalencia.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        lbl_unidaddmedida.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_unidaddmedida.setText("Unidad de medida estándar:");

        cmb_unidadmedidaequi.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cmb_unidadmedidaequi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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

        cmb_nombreproducto.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cmb_nombreproducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btn_anterior.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_anterior.setText("Anterior");
        btn_anterior.addActionListener(this::btn_anteriorActionPerformed);

        lbl_numero1.setText("jLabel1");

        lbl_de.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_de.setText("de");

        javax.swing.GroupLayout pnl_gastosdiariosLayout = new javax.swing.GroupLayout(pnl_gastosdiarios);
        pnl_gastosdiarios.setLayout(pnl_gastosdiariosLayout);
        pnl_gastosdiariosLayout.setHorizontalGroup(
            pnl_gastosdiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_gastosdiariosLayout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addGroup(pnl_gastosdiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_gastosdiariosLayout.createSequentialGroup()
                        .addGroup(pnl_gastosdiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_gastosdiariosLayout.createSequentialGroup()
                                .addGroup(pnl_gastosdiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_tipomedida)
                                    .addComponent(lbl_unidaddmedida)
                                    .addComponent(lbl_cantidadequivalencia)
                                    .addComponent(lbl_lugardecompraarticulo)
                                    .addComponent(lbl_valor, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_frecuenciadecompra)
                                    .addComponent(lbl_tipodegasto)
                                    .addComponent(btn_Cancelar))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnl_gastosdiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmb_lugarcompraarticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmb_unidadmedidaequi, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmb_tipomedida, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_cantidadequivalencia, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_valor, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmb_frecuenciacompra, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnl_gastosdiariosLayout.createSequentialGroup()
                                        .addComponent(btn_hogar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btn_personal))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_gastosdiariosLayout.createSequentialGroup()
                                        .addComponent(btn_anterior)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btn_guardar))))
                            .addGroup(pnl_gastosdiariosLayout.createSequentialGroup()
                                .addGroup(pnl_gastosdiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(pnl_gastosdiariosLayout.createSequentialGroup()
                                        .addComponent(lbl_producto)
                                        .addGap(18, 18, 18)
                                        .addComponent(lbl_numero1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lbl_de, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblProducto)
                                        .addGap(12, 12, 12))
                                    .addGroup(pnl_gastosdiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbl_cantidadadquirida)
                                        .addComponent(lbl_nombreproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lbl_unidadmedida)
                                        .addComponent(lbl_formaadquisicion, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnl_gastosdiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_cantidadadquirida, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                                    .addComponent(cmbox_unidadmedida, 0, 249, Short.MAX_VALUE)
                                    .addComponent(cmb_formaadquisicion, 0, 249, Short.MAX_VALUE)
                                    .addComponent(cmb_nombreproducto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(85, 85, 85))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_gastosdiariosLayout.createSequentialGroup()
                        .addComponent(lbl_equivalenciaounidadmedida)
                        .addGap(48, 48, 48))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_gastosdiariosLayout.createSequentialGroup()
                        .addComponent(lbl_gastosdiarios)
                        .addGap(303, 303, 303)))
                .addComponent(scb_gastosdiarios, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnl_gastosdiariosLayout.setVerticalGroup(
            pnl_gastosdiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_gastosdiariosLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lbl_gastosdiarios, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnl_gastosdiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_producto)
                    .addComponent(lblProducto)
                    .addComponent(lbl_numero1)
                    .addComponent(lbl_de))
                .addGap(26, 26, 26)
                .addGroup(pnl_gastosdiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_nombreproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_nombreproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(pnl_gastosdiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_cantidadadquirida, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_cantidadadquirida))
                .addGap(46, 46, 46)
                .addGroup(pnl_gastosdiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_unidadmedida)
                    .addComponent(cmbox_unidadmedida, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(pnl_gastosdiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_formaadquisicion, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_formaadquisicion))
                .addGap(42, 42, 42)
                .addComponent(lbl_equivalenciaounidadmedida)
                .addGroup(pnl_gastosdiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_gastosdiariosLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(pnl_gastosdiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_tipomedida)
                            .addComponent(cmb_tipomedida, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addGroup(pnl_gastosdiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_gastosdiariosLayout.createSequentialGroup()
                                .addGap(90, 90, 90)
                                .addGroup(pnl_gastosdiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_cantidadequivalencia, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_cantidadequivalencia)))
                            .addGroup(pnl_gastosdiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cmb_unidadmedidaequi, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl_unidaddmedida)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addGroup(pnl_gastosdiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmb_lugarcompraarticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_lugardecompraarticulo))
                        .addGap(49, 49, 49)
                        .addGroup(pnl_gastosdiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_valor, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_valor))
                        .addGap(81, 81, 81))
                    .addGroup(pnl_gastosdiariosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnl_gastosdiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmb_frecuenciacompra, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_frecuenciadecompra))))
                .addGap(34, 34, 34)
                .addGroup(pnl_gastosdiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_personal)
                    .addComponent(btn_hogar)
                    .addComponent(lbl_tipodegasto))
                .addGap(63, 63, 63)
                .addGroup(pnl_gastosdiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_guardar)
                    .addComponent(btn_Cancelar)
                    .addComponent(btn_anterior))
                .addGap(59, 59, 59))
            .addComponent(scb_gastosdiarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_logos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnl_gastosdiarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnl_logos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnl_gastosdiarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        controlador.seleccionarGastoPersonal();
    }//GEN-LAST:event_btn_personalActionPerformed

    private void btn_anteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_anteriorActionPerformed
        // TODO add your handling code here:
        controlador.anteriorProducto();
        
    }//GEN-LAST:event_btn_anteriorActionPerformed

    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
        // TODO add your handling code here:
        controlador.siguienteProducto();
    }//GEN-LAST:event_btn_guardarActionPerformed

    private void btn_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CancelarActionPerformed
        // TODO add your handling code here:
        controlador.cancelar();
    }//GEN-LAST:event_btn_CancelarActionPerformed

    private void btn_hogarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hogarActionPerformed
        // TODO add your handling code here:
        controlador.seleccionarGastoHogar();
    }//GEN-LAST:event_btn_hogarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Cancelar;
    private javax.swing.JButton btn_anterior;
    private javax.swing.JButton btn_guardar;
    private javax.swing.JButton btn_hogar;
    private javax.swing.JButton btn_personal;
    private javax.swing.JComboBox<String> cmb_formaadquisicion;
    private javax.swing.JComboBox<String> cmb_frecuenciacompra;
    private javax.swing.JComboBox<String> cmb_lugarcompraarticulo;
    private javax.swing.JComboBox<String> cmb_nombreproducto;
    private javax.swing.JComboBox<String> cmb_tipomedida;
    private javax.swing.JComboBox<String> cmb_unidadmedidaequi;
    private javax.swing.JComboBox<String> cmbox_unidadmedida;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel lblDia;
    private javax.swing.JLabel lblProducto;
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
    private javax.swing.JLabel lbl_gastosdiarios;
    private javax.swing.JLabel lbl_lugardecompraarticulo;
    private javax.swing.JLabel lbl_nombreproducto;
    private javax.swing.JLabel lbl_nombrerepresentante;
    private javax.swing.JLabel lbl_numero1;
    private javax.swing.JLabel lbl_producto;
    private javax.swing.JLabel lbl_representante;
    private javax.swing.JLabel lbl_tipodegasto;
    private javax.swing.JLabel lbl_tipomedida;
    private javax.swing.JLabel lbl_unidaddmedida;
    private javax.swing.JLabel lbl_unidadmedida;
    private javax.swing.JLabel lbl_valor;
    private javax.swing.JPanel pnl_gastosdiarios;
    private javax.swing.JPanel pnl_logos;
    private javax.swing.JScrollBar scb_gastosdiarios;
    private javax.swing.JTextField txt_cantidadadquirida;
    private javax.swing.JTextField txt_cantidadequivalencia;
    private javax.swing.JTextField txt_valor;
    // End of variables declaration//GEN-END:variables
}
