/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

/**
 *
 * @author CONTABILIDAD
 */
public class P9_RevisarEncuesta_Encuestador extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(P9_RevisarEncuesta_Encuestador.class.getName());

    /**
     * Creates new form P8_MenuPrincipal_Encuestador
     */
    public P9_RevisarEncuesta_Encuestador() {
        initComponents();
        // BARRAS DE DESPLAZAMIENTO
scrlpn_tabla.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
scrlpn_tabla.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

// EVITAR MODIFICAR LAS COLUMNAS
tbl_revisarencuesta.getTableHeader().setResizingAllowed(false);
tbl_revisarencuesta.getTableHeader().setReorderingAllowed(false);

    tbl_revisarencuesta.getColumnModel().getColumn(0).setPreferredWidth(100); 
    tbl_revisarencuesta.getColumnModel().getColumn(1).setPreferredWidth(170);
    tbl_revisarencuesta.getColumnModel().getColumn(2).setPreferredWidth(109);
    tbl_revisarencuesta.getColumnModel().getColumn(3).setPreferredWidth(110);
    tbl_revisarencuesta.getColumnModel().getColumn(4).setPreferredWidth(133);
    tbl_revisarencuesta.getColumnModel().getColumn(5).setPreferredWidth(175);
    tbl_revisarencuesta.getColumnModel().getColumn(6).setPreferredWidth(199);
    tbl_revisarencuesta.getColumnModel().getColumn(7).setPreferredWidth(125);
    tbl_revisarencuesta.getColumnModel().getColumn(8).setPreferredWidth(300);
    tbl_revisarencuesta.getColumnModel().getColumn(9).setPreferredWidth(55);
    tbl_revisarencuesta.getColumnModel().getColumn(10).setPreferredWidth(145);
    tbl_revisarencuesta.getColumnModel().getColumn(11).setPreferredWidth(110);
    
    }
    
    public javax.swing.JPanel getPanelRevisarEncuesta() {
    return pnl_menuprincipalencuestador;
}

public javax.swing.JPanel getPanelMenu() {
    return pnl_menu;
}
    

public void mostrarGastos(java.util.List<modelo.DatosGastoDiario> gastos) {

    javax.swing.table.DefaultTableModel modelo =
            (javax.swing.table.DefaultTableModel) tbl_revisarencuesta.getModel();

    modelo.setRowCount(0);

    for (modelo.DatosGastoDiario gasto : gastos) {

        modelo.addRow(new Object[]{
            gasto.getNumeroOrden(),
            gasto.getProducto(),
            gasto.getCantidadAdquirida(),
            gasto.getUnidadMedida(),
            gasto.getCantidadEquivalencia(),
            gasto.getTipoMedida(),
            gasto.getUnidadEquivalencia(),
            gasto.getFormaAdquisicion(),
            gasto.getLugarCompra(),
            gasto.getValor(),
            gasto.getFrecuenciaCompra(),
            gasto.getTipoGasto()
        });
    }
}

public void mostrarDia(int dia, String fecha) {
    lblDia.setText(String.valueOf(dia));
    lbl_fechadia.setText(fecha);
}

public void habilitarNavegacion(boolean anterior, boolean continuar) {
    btn_anterior.setEnabled(anterior);
    btn_continuar.setEnabled(continuar);
}

public int obtenerFilaSeleccionada() {
    return tbl_revisarencuesta.getSelectedRow();
}

public void configurarNavegacion(
        Runnable accionAnterior,
        Runnable accionContinuar) {

    btn_anterior.addActionListener(e -> accionAnterior.run());

    btn_continuar.addActionListener(e -> accionContinuar.run());
}

public javax.swing.JTable getTablaGastos() {
    return tbl_revisarencuesta;
}

public javax.swing.JButton getBtnAnterior() {
    return btn_anterior;
}

public javax.swing.JButton getBtnContinuar() {
    return btn_continuar;
}

public javax.swing.JButton getBtnEditar() {
    return btn_editar;
}

public javax.swing.JButton getBtnEliminar() {
    return btn_eliminar;
}

private java.util.List<Long> idsGastos =
        new java.util.ArrayList<>();

public void mostrarGastosConIds(
        java.util.List<modelo.DatosGastoDiario> gastos) {

    javax.swing.table.DefaultTableModel modelo =
            (javax.swing.table.DefaultTableModel)
                    tbl_revisarencuesta.getModel();

    modelo.setRowCount(0);
    idsGastos.clear();

    for (modelo.DatosGastoDiario gasto : gastos) {

        idsGastos.add((long) gasto.getId());

        modelo.addRow(new Object[]{
            gasto.getNumeroOrden(),
            gasto.getProducto(),
            gasto.getCantidadAdquirida(),
            gasto.getUnidadMedida(),
            gasto.getCantidadEquivalencia(),
            gasto.getTipoMedida(),
            gasto.getUnidadEquivalencia(),
            gasto.getFormaAdquisicion(),
            gasto.getLugarCompra(),
            gasto.getValor(),
            gasto.getFrecuenciaCompra(),
            gasto.getTipoGasto()
        });
    }
}

public Long getIdGastoSeleccionado() {

    int fila = tbl_revisarencuesta.getSelectedRow();

    if (fila == -1) {
        return null;
    }

    return idsGastos.get(
            tbl_revisarencuesta.convertRowIndexToModel(fila)
    );
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
        lbl_encuestador = new javax.swing.JLabel();
        lbl_nombreencuestador = new javax.swing.JLabel();
        pnl_menuprincipalencuestador = new javax.swing.JPanel();
        pnl_menu = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btn_representanteencargado = new javax.swing.JButton();
        btn_revisarencuesta = new javax.swing.JButton();
        btn_sincronizar = new javax.swing.JButton();
        btn_cerrarsesion = new javax.swing.JButton();
        lbl_revisarencuesta = new javax.swing.JLabel();
        lbl_gastosdiarios = new javax.swing.JLabel();
        lbl_fecha = new javax.swing.JLabel();
        lbl_día = new javax.swing.JLabel();
        lbl_14 = new javax.swing.JLabel();
        scrlpn_tabla = new javax.swing.JScrollPane();
        tbl_revisarencuesta = new javax.swing.JTable();
        btn_salir = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        btn_editar = new javax.swing.JButton();
        btn_anterior = new javax.swing.JButton();
        btn_continuar = new javax.swing.JButton();
        lblDia = new javax.swing.JLabel();
        lbl_fechadia = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnl_logos.setBackground(new java.awt.Color(250, 211, 219));
        pnl_logos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lbl_dane.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/Logo1.png"))); // NOI18N

        lbl_enph.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/Logo2.png"))); // NOI18N

        lbl_encuestador.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_encuestador.setText("Encuestador:");

        lbl_nombreencuestador.setText("jLabel2");

        javax.swing.GroupLayout pnl_logosLayout = new javax.swing.GroupLayout(pnl_logos);
        pnl_logos.setLayout(pnl_logosLayout);
        pnl_logosLayout.setHorizontalGroup(
            pnl_logosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_logosLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lbl_dane, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_enph, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(152, 152, 152)
                .addGroup(pnl_logosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_encuestador)
                    .addComponent(lbl_nombreencuestador, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                                .addComponent(lbl_enph))))
                    .addGroup(pnl_logosLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(lbl_encuestador)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_nombreencuestador)))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pnl_menuprincipalencuestador.setBackground(new java.awt.Color(255, 255, 255));
        pnl_menuprincipalencuestador.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));

        pnl_menu.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setText("Menú");

        btn_representanteencargado.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_representanteencargado.setText("Representante encargado");

        btn_revisarencuesta.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_revisarencuesta.setText("Revisar encuesta");

        btn_sincronizar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_sincronizar.setText("Sincronizar");

        btn_cerrarsesion.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_cerrarsesion.setText("Cerrar sesión");

        javax.swing.GroupLayout pnl_menuLayout = new javax.swing.GroupLayout(pnl_menu);
        pnl_menu.setLayout(pnl_menuLayout);
        pnl_menuLayout.setHorizontalGroup(
            pnl_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_menuLayout.createSequentialGroup()
                .addGroup(pnl_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_menuLayout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jLabel1))
                    .addGroup(pnl_menuLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(pnl_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_revisarencuesta)
                            .addComponent(btn_representanteencargado)
                            .addComponent(btn_sincronizar)))
                    .addGroup(pnl_menuLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(btn_cerrarsesion)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        pnl_menuLayout.setVerticalGroup(
            pnl_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_menuLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addGap(28, 28, 28)
                .addComponent(btn_representanteencargado)
                .addGap(33, 33, 33)
                .addComponent(btn_revisarencuesta)
                .addGap(26, 26, 26)
                .addComponent(btn_sincronizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_cerrarsesion)
                .addGap(34, 34, 34))
        );

        lbl_revisarencuesta.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lbl_revisarencuesta.setText("Revisar Encuesta");

        lbl_gastosdiarios.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbl_gastosdiarios.setText("Gastos Diarios");

        lbl_fecha.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_fecha.setText("Fecha:");

        lbl_día.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_día.setText("Día");

        lbl_14.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbl_14.setText("de 14");

        scrlpn_tabla.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrlpn_tabla.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tbl_revisarencuesta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Número Orden", "Nombre del artículo o servicio", "Cantidad Adquirida", "Unidad de medida", "Cantidad equivalencia", "Tipo de medida de equivalencia", "Unidad de medida de equivalencia", "Forma de adquisición", "Lugar de compra del artículo o de obtención del servicio", "Valor", "Frecuencia de compra", "Gasto del hogar"
            }
        ));
        tbl_revisarencuesta.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        scrlpn_tabla.setViewportView(tbl_revisarencuesta);

        btn_salir.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_salir.setText("Salir");

        btn_eliminar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_eliminar.setText("Eliminar");

        btn_editar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_editar.setText("Editar");

        btn_anterior.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_anterior.setText("Anterior");

        btn_continuar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_continuar.setText("Continuar");
        btn_continuar.addActionListener(this::btn_continuarActionPerformed);

        lblDia.setText("jLabel2");

        lbl_fechadia.setText("jLabel2");

        javax.swing.GroupLayout pnl_menuprincipalencuestadorLayout = new javax.swing.GroupLayout(pnl_menuprincipalencuestador);
        pnl_menuprincipalencuestador.setLayout(pnl_menuprincipalencuestadorLayout);
        pnl_menuprincipalencuestadorLayout.setHorizontalGroup(
            pnl_menuprincipalencuestadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_menuprincipalencuestadorLayout.createSequentialGroup()
                .addComponent(pnl_menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnl_menuprincipalencuestadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_menuprincipalencuestadorLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(pnl_menuprincipalencuestadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_menuprincipalencuestadorLayout.createSequentialGroup()
                                .addComponent(lbl_día)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblDia)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_14))
                            .addGroup(pnl_menuprincipalencuestadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(pnl_menuprincipalencuestadorLayout.createSequentialGroup()
                                    .addComponent(lbl_fecha)
                                    .addGap(18, 18, 18)
                                    .addComponent(lbl_fechadia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(lbl_gastosdiarios)))
                        .addContainerGap())
                    .addGroup(pnl_menuprincipalencuestadorLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(btn_salir)
                        .addGap(26, 26, 26)
                        .addComponent(btn_eliminar)
                        .addGap(27, 27, 27)
                        .addComponent(btn_editar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_anterior)
                        .addGap(18, 18, 18)
                        .addComponent(btn_continuar)
                        .addGap(17, 17, 17))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_menuprincipalencuestadorLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addGroup(pnl_menuprincipalencuestadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_menuprincipalencuestadorLayout.createSequentialGroup()
                                .addComponent(scrlpn_tabla, javax.swing.GroupLayout.PREFERRED_SIZE, 647, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_menuprincipalencuestadorLayout.createSequentialGroup()
                                .addComponent(lbl_revisarencuesta)
                                .addGap(238, 238, 238))))))
        );
        pnl_menuprincipalencuestadorLayout.setVerticalGroup(
            pnl_menuprincipalencuestadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnl_menuprincipalencuestadorLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(lbl_revisarencuesta)
                .addGap(15, 15, 15)
                .addComponent(lbl_gastosdiarios)
                .addGap(18, 18, 18)
                .addGroup(pnl_menuprincipalencuestadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_fecha)
                    .addComponent(lbl_fechadia))
                .addGap(31, 31, 31)
                .addGroup(pnl_menuprincipalencuestadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_día)
                    .addComponent(lbl_14)
                    .addComponent(lblDia))
                .addGap(34, 34, 34)
                .addComponent(scrlpn_tabla, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(pnl_menuprincipalencuestadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_salir)
                    .addComponent(btn_eliminar)
                    .addComponent(btn_editar)
                    .addComponent(btn_anterior)
                    .addComponent(btn_continuar))
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnl_menuprincipalencuestador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnl_logos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnl_logos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnl_menuprincipalencuestador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_continuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_continuarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_continuarActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_anterior;
    private javax.swing.JButton btn_cerrarsesion;
    private javax.swing.JButton btn_continuar;
    private javax.swing.JButton btn_editar;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_representanteencargado;
    private javax.swing.JButton btn_revisarencuesta;
    private javax.swing.JButton btn_salir;
    private javax.swing.JButton btn_sincronizar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblDia;
    private javax.swing.JLabel lbl_14;
    private javax.swing.JLabel lbl_dane;
    private javax.swing.JLabel lbl_día;
    private javax.swing.JLabel lbl_encuestador;
    private javax.swing.JLabel lbl_enph;
    private javax.swing.JLabel lbl_fecha;
    private javax.swing.JLabel lbl_fechadia;
    private javax.swing.JLabel lbl_gastosdiarios;
    private javax.swing.JLabel lbl_nombreencuestador;
    private javax.swing.JLabel lbl_revisarencuesta;
    private javax.swing.JPanel pnl_logos;
    private javax.swing.JPanel pnl_menu;
    private javax.swing.JPanel pnl_menuprincipalencuestador;
    private javax.swing.JScrollPane scrlpn_tabla;
    private javax.swing.JTable tbl_revisarencuesta;
    // End of variables declaration//GEN-END:variables
}
