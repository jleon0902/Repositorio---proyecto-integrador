/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

/**
 *
 * @author usuario
 */
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
public class P27_CriticoVerEncuesta1 extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(P27_CriticoVerEncuesta1.class.getName());

    /**
     * Creates new form iniciarSesion
     */
public P27_CriticoVerEncuesta1() {
    initComponents();
    configurarTabla(tblTablaEncuesta, spTablaEncuesta);
}

private void confirmarSalir() {
    int respuesta = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de que desea volver?",
            "Confirmar regreso",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
    );

    if (respuesta == JOptionPane.YES_OPTION) {
        dispose();
    }
}
private void configurarTabla(
        javax.swing.JTable tabla,
        javax.swing.JScrollPane scroll
) {
    // Impedir la edición de las celdas
    tabla.setDefaultEditor(Object.class, null);

    // Impedir mover las columnas
    tabla.getTableHeader().setReorderingAllowed(false);

    // Impedir redimensionar manualmente las columnas
    tabla.getTableHeader().setResizingAllowed(false);

    // Evitar que las columnas se compriman automáticamente
    tabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

    // Mantener visibles las barras de desplazamiento
    scroll.setHorizontalScrollBarPolicy(
            javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
    );

    scroll.setVerticalScrollBarPolicy(
            javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
    );

    // Centrar los encabezados
    javax.swing.table.DefaultTableCellRenderer encabezado =
            (javax.swing.table.DefaultTableCellRenderer)
            tabla.getTableHeader().getDefaultRenderer();

    encabezado.setHorizontalAlignment(
            javax.swing.SwingConstants.CENTER
    );

    // Centrar el contenido de todas las celdas
    javax.swing.table.DefaultTableCellRenderer centrado =
            new javax.swing.table.DefaultTableCellRenderer();

    centrado.setHorizontalAlignment(
            javax.swing.SwingConstants.CENTER
    );

    for (int i = 0; i < tabla.getColumnCount(); i++) {
        tabla.getColumnModel().getColumn(i).setCellRenderer(centrado);
    }

    // Ajustar los anchos de las columnas
    ajustarAnchoColumnas(tabla, scroll);

    // Actualizar los anchos cuando cambien los datos de la tabla
    tabla.getModel().addTableModelListener(e -> {
        javax.swing.SwingUtilities.invokeLater(() -> {
            ajustarAnchoColumnas(tabla, scroll);
        });
    });

    // Recalcular cuando cambie el tamaño del área visible
    scroll.getViewport().addComponentListener(
            new java.awt.event.ComponentAdapter() {
        @Override
        public void componentResized(java.awt.event.ComponentEvent e) {
            ajustarAnchoColumnas(tabla, scroll);
        }
    });

    // Calcular nuevamente cuando termine la distribución inicial
    javax.swing.SwingUtilities.invokeLater(() -> {
        ajustarAnchoColumnas(tabla, scroll);
    });
}

private void ajustarAnchoColumnas(
        javax.swing.JTable tabla,
        javax.swing.JScrollPane scroll
) {
    javax.swing.table.TableColumnModel modeloColumnas =
            tabla.getColumnModel();

    javax.swing.table.JTableHeader encabezado =
            tabla.getTableHeader();

    int cantidadColumnas = tabla.getColumnCount();

    if (cantidadColumnas == 0) {
        return;
    }

    int[] anchos = new int[cantidadColumnas];
    int anchoTotal = 0;

    // Calcular el ancho necesario para cada columna
    for (int i = 0; i < cantidadColumnas; i++) {

        javax.swing.table.TableColumn columna =
                modeloColumnas.getColumn(i);

        // Medir el encabezado completo
        java.awt.Component componenteEncabezado =
                encabezado.getDefaultRenderer()
                        .getTableCellRendererComponent(
                                tabla,
                                columna.getHeaderValue(),
                                false,
                                false,
                                -1,
                                i
                        );

        int anchoEncabezado =
                componenteEncabezado.getPreferredSize().width + 30;

        // Medir el contenido de las celdas
        int anchoContenido = 0;

        for (int fila = 0; fila < tabla.getRowCount(); fila++) {

            java.awt.Component componenteCelda =
                    tabla.getCellRenderer(fila, i)
                            .getTableCellRendererComponent(
                                    tabla,
                                    tabla.getValueAt(fila, i),
                                    false,
                                    false,
                                    fila,
                                    i
                            );

            int anchoCelda =
                    componenteCelda.getPreferredSize().width + 20;

            anchoContenido = Math.max(anchoContenido, anchoCelda);
        }

        // Tomar el mayor ancho entre encabezado y contenido
        anchos[i] = Math.max(
                65,
                Math.max(anchoEncabezado, anchoContenido)
        );

        anchoTotal += anchos[i];
    }

    // Obtener el ancho disponible en el área de visualización
    int anchoDisponible = scroll.getViewport().getWidth();

    // Repartir el espacio sobrante entre todas las columnas
    int espacioSobrante = Math.max(0, anchoDisponible - anchoTotal);

    int espacioPorColumna = espacioSobrante / cantidadColumnas;

    int residuo = espacioSobrante % cantidadColumnas;

    for (int i = 0; i < cantidadColumnas; i++) {

        javax.swing.table.TableColumn columna =
                modeloColumnas.getColumn(i);

        int anchoFinal = anchos[i] + espacioPorColumna;

        if (i < residuo) {
            anchoFinal++;
        }

        columna.setMinWidth(anchoFinal);
        columna.setPreferredWidth(anchoFinal);
        columna.setWidth(anchoFinal);
    }

    // Mantener la altura de los encabezados
    encabezado.setPreferredSize(
            new java.awt.Dimension(
                    encabezado.getPreferredSize().width,
                    45
            )
    );

    encabezado.revalidate();
    tabla.revalidate();
    tabla.repaint();
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        pnlLogos = new javax.swing.JPanel();
        lblAdministrador = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pnlVerEncuesta = new javax.swing.JPanel();
        btnContinuar = new javax.swing.JButton();
        lblVerEncuesta = new javax.swing.JLabel();
        lblDia = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        btnAnterior = new javax.swing.JButton();
        spTablaEncuesta = new javax.swing.JScrollPane();
        tblTablaEncuesta = new javax.swing.JTable();
        lblDe14 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblMostrarRepresentateHogar = new javax.swing.JLabel();
        lblMostrarNumeroVivienda = new javax.swing.JLabel();
        lblNumeroVivienda = new javax.swing.JLabel();
        lblRepresentanteHogar = new javax.swing.JLabel();

        jPanel4.setBorder(new javax.swing.border.MatteBorder(null));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel9.setText("Menú");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel10.setText("Registrar usuarios");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel11.setText("Usuarios");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel12.setText("Registrar portátiles");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel13.setText("Portatiles");

        jLabel14.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel14.setText("Cerrar sesión");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(73, 73, 73))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel14)
                        .addComponent(jLabel12)
                        .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING)))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel9)
                .addGap(41, 41, 41)
                .addComponent(jLabel10)
                .addGap(48, 48, 48)
                .addComponent(jLabel11)
                .addGap(41, 41, 41)
                .addComponent(jLabel12)
                .addGap(44, 44, 44)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addGap(23, 23, 23))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlLogos.setBackground(new java.awt.Color(255, 235, 229));
        pnlLogos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblAdministrador.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        lblAdministrador.setText("Critico digitador");

        lblUsuario.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblUsuario.setText("lblUsuario");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/logoDane.png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/logoEnph.png"))); // NOI18N

        javax.swing.GroupLayout pnlLogosLayout = new javax.swing.GroupLayout(pnlLogos);
        pnlLogos.setLayout(pnlLogosLayout);
        pnlLogosLayout.setHorizontalGroup(
            pnlLogosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLogosLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 235, Short.MAX_VALUE)
                .addGroup(pnlLogosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAdministrador, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblUsuario, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(103, 103, 103))
        );
        pnlLogosLayout.setVerticalGroup(
            pnlLogosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLogosLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlLogosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addGroup(pnlLogosLayout.createSequentialGroup()
                        .addComponent(lblAdministrador)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUsuario)
                        .addGap(8, 8, 8)))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        getContentPane().add(pnlLogos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 110));

        pnlVerEncuesta.setBackground(new java.awt.Color(255, 255, 255));
        pnlVerEncuesta.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));
        pnlVerEncuesta.setPreferredSize(new java.awt.Dimension(938, 522));

        btnContinuar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnContinuar.setText("Continuar");
        btnContinuar.addActionListener(this::btnContinuarActionPerformed);

        lblVerEncuesta.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        lblVerEncuesta.setText(" Encuesta");

        lblDia.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblDia.setText("Día");

        btnSalir.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnSalir.setText("Volver");
        btnSalir.addActionListener(this::btnSalirActionPerformed);

        btnAnterior.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnAnterior.setText("Anterior");

        spTablaEncuesta.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        spTablaEncuesta.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tblTablaEncuesta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Número orden", "Nombre del articulo o servicio", "Cantidad adquirida", "Unidad de medida", "Cantidad equivalencia", "Tipo de medida de equivalencia", "Unidad de medida para la equivalencia", "Forma de adquisición", "Lugar de compra del articulo o de obtencion del servicio", "Valor", "Frecuencia de compra", "Gasto del hogar o personal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, false, true, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTablaEncuesta.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        spTablaEncuesta.setViewportView(tblTablaEncuesta);
        if (tblTablaEncuesta.getColumnModel().getColumnCount() > 0) {
            tblTablaEncuesta.getColumnModel().getColumn(9).setResizable(false);
        }

        lblDe14.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblDe14.setText("de 14");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel3.setText("lblDia");

        lblMostrarRepresentateHogar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMostrarRepresentateHogar.setText("jLabel4");

        lblMostrarNumeroVivienda.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMostrarNumeroVivienda.setText("jLabel4");

        lblNumeroVivienda.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblNumeroVivienda.setText("Número vivienda:");

        lblRepresentanteHogar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblRepresentanteHogar.setText("Representante del hogar:");

        javax.swing.GroupLayout pnlVerEncuestaLayout = new javax.swing.GroupLayout(pnlVerEncuesta);
        pnlVerEncuesta.setLayout(pnlVerEncuestaLayout);
        pnlVerEncuestaLayout.setHorizontalGroup(
            pnlVerEncuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVerEncuestaLayout.createSequentialGroup()
                .addGroup(pnlVerEncuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlVerEncuestaLayout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(spTablaEncuesta, javax.swing.GroupLayout.PREFERRED_SIZE, 757, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlVerEncuestaLayout.createSequentialGroup()
                        .addGap(391, 391, 391)
                        .addComponent(lblVerEncuesta)))
                .addGap(0, 91, Short.MAX_VALUE))
            .addGroup(pnlVerEncuestaLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlVerEncuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlVerEncuestaLayout.createSequentialGroup()
                        .addGroup(pnlVerEncuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlVerEncuestaLayout.createSequentialGroup()
                                .addComponent(lblNumeroVivienda)
                                .addGap(18, 18, 18)
                                .addComponent(lblMostrarNumeroVivienda, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlVerEncuestaLayout.createSequentialGroup()
                                .addComponent(lblRepresentanteHogar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblMostrarRepresentateHogar, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlVerEncuestaLayout.createSequentialGroup()
                        .addComponent(lblDia)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(lblDe14)
                        .addContainerGap(777, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlVerEncuestaLayout.createSequentialGroup()
                        .addComponent(btnSalir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAnterior)
                        .addGap(40, 40, 40)
                        .addComponent(btnContinuar)
                        .addGap(20, 20, 20))))
        );
        pnlVerEncuestaLayout.setVerticalGroup(
            pnlVerEncuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlVerEncuestaLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(lblVerEncuesta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(pnlVerEncuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNumeroVivienda)
                    .addComponent(lblMostrarNumeroVivienda))
                .addGap(7, 7, 7)
                .addGroup(pnlVerEncuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRepresentanteHogar)
                    .addComponent(lblMostrarRepresentateHogar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlVerEncuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDia)
                    .addComponent(lblDe14)
                    .addComponent(jLabel3))
                .addGap(30, 30, 30)
                .addComponent(spTablaEncuesta, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addGroup(pnlVerEncuestaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnContinuar)
                    .addComponent(btnSalir)
                    .addComponent(btnAnterior))
                .addGap(53, 53, 53))
        );

        getContentPane().add(pnlVerEncuesta, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 940, 510));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnContinuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContinuarActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btnContinuarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        confirmarSalir();
    }//GEN-LAST:event_btnSalirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new P27_CriticoVerEncuesta1().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnterior;
    private javax.swing.JButton btnContinuar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblAdministrador;
    private javax.swing.JLabel lblDe14;
    private javax.swing.JLabel lblDia;
    private javax.swing.JLabel lblMostrarNumeroVivienda;
    private javax.swing.JLabel lblMostrarRepresentateHogar;
    private javax.swing.JLabel lblNumeroVivienda;
    private javax.swing.JLabel lblRepresentanteHogar;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lblVerEncuesta;
    private javax.swing.JPanel pnlLogos;
    private javax.swing.JPanel pnlVerEncuesta;
    private javax.swing.JScrollPane spTablaEncuesta;
    private javax.swing.JTable tblTablaEncuesta;
    // End of variables declaration//GEN-END:variables
}
