/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;
import javax.swing.JPanel;

/**
 *
 * @author usuario
 */
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
public class P17_EncuestadorHogaresAsignados1 extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(P17_EncuestadorHogaresAsignados1.class.getName());

    /**
     * Creates new form iniciarSesion
     */
public P17_EncuestadorHogaresAsignados1() {
    initComponents();
    configurarTabla(tblTabla, spHogares);
}

public JPanel obtenerPanelHogaresAsignados() {

    getContentPane().remove(pnlMenuInterfaz1);

    return pnlMenuInterfaz1;
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

    // Centrar el contenido de las celdas
    javax.swing.table.DefaultTableCellRenderer centrado =
            new javax.swing.table.DefaultTableCellRenderer();

    centrado.setHorizontalAlignment(
            javax.swing.SwingConstants.CENTER
    );

    for (int i = 0; i < tabla.getColumnCount(); i++) {
        tabla.getColumnModel().getColumn(i).setCellRenderer(centrado);
    }

    // Ajustar automáticamente el ancho de las columnas
    ajustarAnchoColumnas(tabla, scroll);

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

    // Calcular el ancho necesario para cada encabezado
    for (int i = 0; i < cantidadColumnas; i++) {

        javax.swing.table.TableColumn columna =
                modeloColumnas.getColumn(i);

        java.awt.Component componente =
                encabezado.getDefaultRenderer()
                        .getTableCellRendererComponent(
                                tabla,
                                columna.getHeaderValue(),
                                false,
                                false,
                                -1,
                                i
                        );

        anchos[i] = Math.max(
                65,
                componente.getPreferredSize().width + 30
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
        pnlMenu = new javax.swing.JPanel();
        lblMenu = new javax.swing.JLabel();
        btnRegistrarUsuarios = new javax.swing.JButton();
        btnUsuarios = new javax.swing.JButton();
        btnRegistrarPortatiles = new javax.swing.JButton();
        btnPortatiles = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();
        pnlMenuInterfaz1 = new javax.swing.JPanel();
        lblHogaresAsignadosInterfaz = new javax.swing.JLabel();
        lblHogaresasignadoss = new javax.swing.JLabel();
        spHogares = new javax.swing.JScrollPane();
        tblTabla = new javax.swing.JTable();
        btnVerHogar = new javax.swing.JButton();

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
        lblAdministrador.setText("Encuestador");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 272, Short.MAX_VALUE)
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

        pnlMenu.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));
        pnlMenu.setPreferredSize(new java.awt.Dimension(242, 521));

        lblMenu.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMenu.setText("Menú");

        btnRegistrarUsuarios.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnRegistrarUsuarios.setText("<html><center>Hogares<br>asignados</center></html>");
        btnRegistrarUsuarios.addActionListener(this::btnRegistrarUsuariosActionPerformed);

        btnUsuarios.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnUsuarios.setText("<html><center>Vincular<br>portátil</center></html>");
        btnUsuarios.addActionListener(this::btnUsuariosActionPerformed);

        btnRegistrarPortatiles.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnRegistrarPortatiles.setText("<html><center>Portátiles<br>asignados</center></html>");
        btnRegistrarPortatiles.addActionListener(this::btnRegistrarPortatilesActionPerformed);

        btnPortatiles.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnPortatiles.setText("Sincronizar");
        btnPortatiles.addActionListener(this::btnPortatilesActionPerformed);

        btnCerrarSesion.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnCerrarSesion.setText("Cerrar sesión");

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlMenuLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnPortatiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRegistrarPortatiles)
                            .addComponent(btnUsuarios)
                            .addComponent(btnRegistrarUsuarios)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlMenuLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(btnCerrarSesion)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(60, 60, 60))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblMenu)
                .addGap(98, 98, 98))
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lblMenu)
                .addGap(49, 49, 49)
                .addComponent(btnRegistrarUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btnUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btnRegistrarPortatiles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnPortatiles, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(btnCerrarSesion)
                .addGap(29, 29, 29))
        );

        getContentPane().add(pnlMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, -1, 510));

        pnlMenuInterfaz1.setBackground(new java.awt.Color(255, 255, 255));
        pnlMenuInterfaz1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));
        pnlMenuInterfaz1.setPreferredSize(new java.awt.Dimension(696, 522));

        lblHogaresAsignadosInterfaz.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        lblHogaresAsignadosInterfaz.setText("Hogares asignados");

        lblHogaresasignadoss.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblHogaresasignadoss.setText("Cantidad de hogares asignados:");

        tblTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Número vivienda", "Responsable del hogar", "Estado de la encuesta"
            }
        ));
        spHogares.setViewportView(tblTabla);

        btnVerHogar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnVerHogar.setText("Ver hogar");
        btnVerHogar.addActionListener(this::btnVerHogarActionPerformed);

        javax.swing.GroupLayout pnlMenuInterfaz1Layout = new javax.swing.GroupLayout(pnlMenuInterfaz1);
        pnlMenuInterfaz1.setLayout(pnlMenuInterfaz1Layout);
        pnlMenuInterfaz1Layout.setHorizontalGroup(
            pnlMenuInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuInterfaz1Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(pnlMenuInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMenuInterfaz1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(lblHogaresasignadoss))
                    .addGroup(pnlMenuInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pnlMenuInterfaz1Layout.createSequentialGroup()
                            .addGap(444, 444, 444)
                            .addComponent(btnVerHogar))
                        .addGroup(pnlMenuInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlMenuInterfaz1Layout.createSequentialGroup()
                                .addGap(130, 130, 130)
                                .addComponent(lblHogaresAsignadosInterfaz))
                            .addComponent(spHogares, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 545, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        pnlMenuInterfaz1Layout.setVerticalGroup(
            pnlMenuInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuInterfaz1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(lblHogaresAsignadosInterfaz)
                .addGap(42, 42, 42)
                .addComponent(lblHogaresasignadoss)
                .addGap(59, 59, 59)
                .addComponent(spHogares, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(btnVerHogar)
                .addGap(33, 33, 33))
        );

        getContentPane().add(pnlMenuInterfaz1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, 700, 510));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarUsuariosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRegistrarUsuariosActionPerformed

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUsuariosActionPerformed

    private void btnRegistrarPortatilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarPortatilesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRegistrarPortatilesActionPerformed

    private void btnPortatilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPortatilesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPortatilesActionPerformed

    private void btnVerHogarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerHogarActionPerformed
        // TODO add your handling code here:
    
    }//GEN-LAST:event_btnVerHogarActionPerformed

    
public javax.swing.JTable getTblTabla() {
    return tblTabla;
}

public javax.swing.JLabel getLblHogaresasignadoss() {
    return lblHogaresasignadoss;
}

public javax.swing.JButton getBtnVerHogar() {
    return btnVerHogar;
}

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
        java.awt.EventQueue.invokeLater(() -> new P17_EncuestadorHogaresAsignados1().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnPortatiles;
    private javax.swing.JButton btnRegistrarPortatiles;
    private javax.swing.JButton btnRegistrarUsuarios;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JButton btnVerHogar;
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
    private javax.swing.JLabel lblHogaresAsignadosInterfaz;
    private javax.swing.JLabel lblHogaresasignadoss;
    private javax.swing.JLabel lblMenu;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel pnlLogos;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlMenuInterfaz1;
    private javax.swing.JScrollPane spHogares;
    private javax.swing.JTable tblTabla;
    // End of variables declaration//GEN-END:variables
}
