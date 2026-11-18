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
public class P25_CriticoHogaresEncuestadosTabla1 extends javax.swing.JFrame {
    private P24_CriticoHogaresEncuestados1 pantallaAnterior;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(P25_CriticoHogaresEncuestadosTabla1.class.getName());

    /**
     * Creates new form iniciarSesion
     */
public P25_CriticoHogaresEncuestadosTabla1() {
    this(null);
}

public P25_CriticoHogaresEncuestadosTabla1(
        P24_CriticoHogaresEncuestados1 pantallaAnterior) {

    this.pantallaAnterior = pantallaAnterior;

    initComponents();

    configurarTabla(tblTablaFamiliasEncuesta, spTablaFamiliasEncuesta);
}
private void abrirInformacionHogar() {
    P26_CriticoVerHogar1 pantalla = new P26_CriticoVerHogar1();
    pantalla.setLocationRelativeTo(this);
    pantalla.setVisible(true);
}
private void confirmarVolver() {

    int respuesta = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de que desea volver a la pantalla anterior?",
            "Confirmar regreso",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
    );

    if (respuesta == JOptionPane.YES_OPTION) {
        this.dispose();
    }
}
private void configurarTabla(
        javax.swing.JTable tabla,
        javax.swing.JScrollPane scroll
) {
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

    // Centrar el contenido de las celdas
    javax.swing.table.DefaultTableCellRenderer centrado =
            new javax.swing.table.DefaultTableCellRenderer();

    centrado.setHorizontalAlignment(
            javax.swing.SwingConstants.CENTER
    );

    for (int i = 0; i < tabla.getColumnCount(); i++) {

        // Conservar el renderizador de la columna de casillas
        if (tabla.getColumnClass(i) != Boolean.class) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }
    }

    // Bloquear la edición de las columnas, excepto la casilla Boolean
    tabla.setDefaultEditor(Object.class, null);

    // Mantener habilitada la edición de las casillas
    tabla.setDefaultEditor(Boolean.class, new javax.swing.DefaultCellEditor(
            new javax.swing.JCheckBox()
    ));

    // Ajustar los anchos de las columnas
    ajustarAnchoColumnas(tabla, scroll);

    // Actualizar los anchos cuando cambien los datos
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

    // Calcular el espacio disponible
    int anchoDisponible = scroll.getViewport().getWidth();

    int espacioSobrante = Math.max(
            0,
            anchoDisponible - anchoTotal
    );

    // Distribuir el espacio sobrante entre todas las columnas
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

    // Establecer la altura del encabezado
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
        pnlFamiliasEncuestadas = new javax.swing.JPanel();
        btnVerFamilia = new javax.swing.JButton();
        lblFamiliasEncuestadas = new javax.swing.JLabel();
        spTablaFamiliasEncuesta = new javax.swing.JScrollPane();
        tblTablaFamiliasEncuesta = new javax.swing.JTable();
        btnVolver = new javax.swing.JButton();
        lblMostrarDepartamento = new javax.swing.JLabel();
        lblMostrarMunicipio = new javax.swing.JLabel();
        lblRegion = new javax.swing.JLabel();
        lblDepartamento = new javax.swing.JLabel();
        lblMunicipio = new javax.swing.JLabel();
        lblMostrarRegion = new javax.swing.JLabel();

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

        pnlFamiliasEncuestadas.setBackground(new java.awt.Color(255, 255, 255));
        pnlFamiliasEncuestadas.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));
        pnlFamiliasEncuestadas.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        pnlFamiliasEncuestadas.setPreferredSize(new java.awt.Dimension(938, 522));

        btnVerFamilia.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnVerFamilia.setText("Ver hogar");
        btnVerFamilia.addActionListener(this::btnVerFamiliaActionPerformed);

        lblFamiliasEncuestadas.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        lblFamiliasEncuestadas.setText("Familias encuestadas");

        spTablaFamiliasEncuesta.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        spTablaFamiliasEncuesta.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tblTablaFamiliasEncuesta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Número vivienda", "Responsable del hogar", "Fecha fin encuesta", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblTablaFamiliasEncuesta.setColumnSelectionAllowed(true);
        spTablaFamiliasEncuesta.setViewportView(tblTablaFamiliasEncuesta);
        tblTablaFamiliasEncuesta.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        btnVolver.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnVolver.setText("Volver");
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        lblMostrarDepartamento.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMostrarDepartamento.setText("jLabel3");

        lblMostrarMunicipio.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMostrarMunicipio.setText("jLabel3");

        lblRegion.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblRegion.setText("Región:");

        lblDepartamento.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblDepartamento.setText("Departamento:");

        lblMunicipio.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMunicipio.setText("Municipio");

        lblMostrarRegion.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMostrarRegion.setText("jLabel3");

        javax.swing.GroupLayout pnlFamiliasEncuestadasLayout = new javax.swing.GroupLayout(pnlFamiliasEncuestadas);
        pnlFamiliasEncuestadas.setLayout(pnlFamiliasEncuestadasLayout);
        pnlFamiliasEncuestadasLayout.setHorizontalGroup(
            pnlFamiliasEncuestadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFamiliasEncuestadasLayout.createSequentialGroup()
                .addGap(304, 304, 304)
                .addComponent(lblFamiliasEncuestadas)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlFamiliasEncuestadasLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(pnlFamiliasEncuestadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFamiliasEncuestadasLayout.createSequentialGroup()
                        .addGroup(pnlFamiliasEncuestadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDepartamento)
                            .addComponent(lblRegion)
                            .addComponent(lblMunicipio))
                        .addGap(54, 54, 54)
                        .addGroup(pnlFamiliasEncuestadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblMostrarRegion, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
                            .addComponent(lblMostrarDepartamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMostrarMunicipio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlFamiliasEncuestadasLayout.createSequentialGroup()
                        .addGroup(pnlFamiliasEncuestadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(spTablaFamiliasEncuesta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 866, Short.MAX_VALUE)
                            .addGroup(pnlFamiliasEncuestadasLayout.createSequentialGroup()
                                .addComponent(btnVolver)
                                .addGap(685, 685, 685)
                                .addComponent(btnVerFamilia)))
                        .addGap(37, 37, 37))))
        );
        pnlFamiliasEncuestadasLayout.setVerticalGroup(
            pnlFamiliasEncuestadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFamiliasEncuestadasLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lblFamiliasEncuestadas)
                .addGap(30, 30, 30)
                .addGroup(pnlFamiliasEncuestadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRegion)
                    .addComponent(lblMostrarRegion))
                .addGap(15, 15, 15)
                .addGroup(pnlFamiliasEncuestadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblDepartamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMostrarDepartamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFamiliasEncuestadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMunicipio)
                    .addComponent(lblMostrarMunicipio))
                .addGap(29, 29, 29)
                .addComponent(spTablaFamiliasEncuesta, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(pnlFamiliasEncuestadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVerFamilia)
                    .addComponent(btnVolver))
                .addGap(3, 3, 3))
        );

        getContentPane().add(pnlFamiliasEncuestadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 940, 510));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVerFamiliaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerFamiliaActionPerformed
 abrirInformacionHogar();
    }//GEN-LAST:event_btnVerFamiliaActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
confirmarVolver();
    }//GEN-LAST:event_btnVolverActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new P25_CriticoHogaresEncuestadosTabla1().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnVerFamilia;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblAdministrador;
    private javax.swing.JLabel lblDepartamento;
    private javax.swing.JLabel lblFamiliasEncuestadas;
    private javax.swing.JLabel lblMostrarDepartamento;
    private javax.swing.JLabel lblMostrarMunicipio;
    private javax.swing.JLabel lblMostrarRegion;
    private javax.swing.JLabel lblMunicipio;
    private javax.swing.JLabel lblRegion;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel pnlFamiliasEncuestadas;
    private javax.swing.JPanel pnlLogos;
    private javax.swing.JScrollPane spTablaFamiliasEncuesta;
    private javax.swing.JTable tblTablaFamiliasEncuesta;
    // End of variables declaration//GEN-END:variables
}
