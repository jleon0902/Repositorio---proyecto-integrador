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
public class P14_RecuentistaHogaresAsignados1 extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(P14_RecuentistaHogaresAsignados1.class.getName());

    /**
     * Creates new form iniciarSesion
     */

public P14_RecuentistaHogaresAsignados1() {
    initComponents();

    configurarTabla(tblTabla1, sp1);

    asignarEventos();
}

public javax.swing.JPanel getPanelHogaresAsignados() {
    return pnlMenuInterfaz1;
}
private void asignarEventos() {

    // Eliminar los eventos anteriores de NetBeans
    for (java.awt.event.ActionListener evento : btnEliminar.getActionListeners()) {
        btnEliminar.removeActionListener(evento);
    }

    for (java.awt.event.ActionListener evento : btnEditar.getActionListeners()) {
        btnEditar.removeActionListener(evento);
    }

    // Asignar los nuevos eventos
    btnEliminar.addActionListener(e -> accionEliminar());

    btnEditar.addActionListener(e -> accionEditar());
}
private void accionEliminar() {

    int respuesta = javax.swing.JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de que desea eliminar este hogar asignado?",
            "Confirmar eliminación",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE
    );

    if (respuesta == javax.swing.JOptionPane.YES_OPTION) {

        System.out.println("Eliminación de hogar asignado confirmada.");

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Se ha confirmado la eliminación del hogar asignado.",
                "Eliminación confirmada",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );
    }
}
private void accionEditar() {

    int respuesta = javax.swing.JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de que desea editar este hogar asignado?",
            "Confirmar edición",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE
    );

    if (respuesta == javax.swing.JOptionPane.YES_OPTION) {

        System.out.println("Edición de hogar asignado confirmada.");

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Se ha confirmado la edición del hogar asignado.",
                "Edición confirmada",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );
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

    // Centrar el texto de los encabezados
    javax.swing.table.DefaultTableCellRenderer encabezado =
            (javax.swing.table.DefaultTableCellRenderer)
            tabla.getTableHeader().getDefaultRenderer();

    encabezado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

    // Centrar el contenido de las celdas
    javax.swing.table.DefaultTableCellRenderer centrado =
            new javax.swing.table.DefaultTableCellRenderer();

    centrado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

    for (int i = 0; i < tabla.getColumnCount(); i++) {
        tabla.getColumnModel().getColumn(i).setCellRenderer(centrado);
    }

    // Ajustar el ancho de las columnas
// Ajustar el ancho de las columnas
ajustarAnchoColumnas(tabla);

// Ajustar la última columna para ocupar el espacio sobrante
ajustarUltimaColumna(tabla, scroll);

// Recalcular si cambia el tamaño de la ventana
scroll.getViewport().addComponentListener(
        new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                ajustarUltimaColumna(tabla, scroll);
            }
        }
);


}

private void ajustarAnchoColumnas(javax.swing.JTable tabla) {

    javax.swing.table.TableColumnModel modeloColumnas =
            tabla.getColumnModel();

    javax.swing.table.JTableHeader encabezado =
            tabla.getTableHeader();

    int[] anchos = new int[tabla.getColumnCount()];
    int anchoTotal = 0;

    // Calcular el ancho mínimo necesario para cada encabezado
    for (int i = 0; i < tabla.getColumnCount(); i++) {

        javax.swing.table.TableColumn columna =
                modeloColumnas.getColumn(i);

        Object valorEncabezado = columna.getHeaderValue();

        java.awt.Component componente =
                encabezado.getDefaultRenderer().getTableCellRendererComponent(
                        tabla,
                        valorEncabezado,
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

    // Obtener el espacio disponible en la tabla
    int anchoDisponible = tabla.getParent().getWidth();

    // Calcular el espacio sobrante
    int espacioSobrante = Math.max(0, anchoDisponible - anchoTotal);

    // Repartir el espacio sobrante entre todas las columnas
    int espacioPorColumna = espacioSobrante / tabla.getColumnCount();
    int residuo = espacioSobrante % tabla.getColumnCount();

    for (int i = 0; i < tabla.getColumnCount(); i++) {

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

    // Altura del encabezado
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
private void ajustarUltimaColumna(
        javax.swing.JTable tabla,
        javax.swing.JScrollPane scroll
) {
    javax.swing.SwingUtilities.invokeLater(() -> {

        int anchoDisponible = scroll.getViewport().getWidth();

        int anchoTotal = 0;

        for (int i = 0; i < tabla.getColumnCount() - 1; i++) {
            anchoTotal += tabla.getColumnModel()
                    .getColumn(i).getPreferredWidth();
        }

        javax.swing.table.TableColumn ultimaColumna =
                tabla.getColumnModel().getColumn(
                        tabla.getColumnCount() - 1
                );

        int anchoNecesario = ultimaColumna.getPreferredWidth();

        int anchoFinal = Math.max(
                anchoNecesario,
                anchoDisponible - anchoTotal
        );

        ultimaColumna.setMinWidth(anchoNecesario);
        ultimaColumna.setPreferredWidth(anchoFinal);
        ultimaColumna.setWidth(anchoFinal);

        tabla.revalidate();
        tabla.repaint();
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
        pnlMenuInterfaz1 = new javax.swing.JPanel();
        lblHogaresAsignadosInterfaz = new javax.swing.JLabel();
        sp1 = new javax.swing.JScrollPane();
        tblTabla1 = new javax.swing.JTable();
        btnEliminar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        pnlMenu = new javax.swing.JPanel();
        lblMenu = new javax.swing.JLabel();
        btnRegistrarUsuarios = new javax.swing.JButton();
        btnUsuarios = new javax.swing.JButton();
        btnRegistrarPortatiles = new javax.swing.JButton();
        btnPortatiles = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();

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
        lblAdministrador.setText("Recuentista");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 279, Short.MAX_VALUE)
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

        pnlMenuInterfaz1.setBackground(new java.awt.Color(255, 255, 255));
        pnlMenuInterfaz1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(1, 1, 1)));
        pnlMenuInterfaz1.setPreferredSize(new java.awt.Dimension(692, 522));

        lblHogaresAsignadosInterfaz.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        lblHogaresAsignadosInterfaz.setText("Hogares Asignados");

        sp1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tblTabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "<html><center>Número <br> vivienda</center></html>", "Dirección", "<html><center>Nombre<br>encuestador</center></html>", "Cédula"
            }
        ));
        sp1.setViewportView(tblTabla1);

        btnEliminar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        btnEditar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(this::btnEditarActionPerformed);

        javax.swing.GroupLayout pnlMenuInterfaz1Layout = new javax.swing.GroupLayout(pnlMenuInterfaz1);
        pnlMenuInterfaz1.setLayout(pnlMenuInterfaz1Layout);
        pnlMenuInterfaz1Layout.setHorizontalGroup(
            pnlMenuInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuInterfaz1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblHogaresAsignadosInterfaz)
                .addGap(196, 196, 196))
            .addGroup(pnlMenuInterfaz1Layout.createSequentialGroup()
                .addContainerGap(79, Short.MAX_VALUE)
                .addGroup(pnlMenuInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlMenuInterfaz1Layout.createSequentialGroup()
                        .addComponent(btnEliminar)
                        .addGap(383, 383, 383)
                        .addComponent(btnEditar))
                    .addComponent(sp1, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 73, Short.MAX_VALUE))
        );
        pnlMenuInterfaz1Layout.setVerticalGroup(
            pnlMenuInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuInterfaz1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lblHogaresAsignadosInterfaz)
                .addGap(51, 51, 51)
                .addComponent(sp1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(pnlMenuInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar)
                    .addComponent(btnEditar))
                .addGap(41, 41, 41))
        );

        getContentPane().add(pnlMenuInterfaz1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, 700, 520));

        pnlMenu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlMenu.setPreferredSize(new java.awt.Dimension(242, 521));

        lblMenu.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMenu.setText("Menú");

        btnRegistrarUsuarios.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnRegistrarUsuarios.setText("<html><center>Registrar<br>hogares</center></html>");
        btnRegistrarUsuarios.addActionListener(this::btnRegistrarUsuariosActionPerformed);

        btnUsuarios.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnUsuarios.setText("Ver hogares");
        btnUsuarios.addActionListener(this::btnUsuariosActionPerformed);

        btnRegistrarPortatiles.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnRegistrarPortatiles.setText("<html><center>Asignar hogar<br>al encuestador</center></html>");
        btnRegistrarPortatiles.addActionListener(this::btnRegistrarPortatilesActionPerformed);

        btnPortatiles.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnPortatiles.setText("<html><center>Hogares<br>asignados</center></html>");
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
                            .addComponent(btnPortatiles)
                            .addComponent(btnRegistrarPortatiles)
                            .addComponent(btnUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(btnUsuarios)
                .addGap(29, 29, 29)
                .addComponent(btnRegistrarPortatiles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnPortatiles, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addComponent(btnCerrarSesion)
                .addGap(29, 29, 29))
        );

        getContentPane().add(pnlMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, -1, 520));

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

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditarActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new P14_RecuentistaHogaresAsignados1().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnPortatiles;
    private javax.swing.JButton btnRegistrarPortatiles;
    private javax.swing.JButton btnRegistrarUsuarios;
    private javax.swing.JButton btnUsuarios;
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
    private javax.swing.JLabel lblMenu;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel pnlLogos;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlMenuInterfaz1;
    private javax.swing.JScrollPane sp1;
    private javax.swing.JTable tblTabla1;
    // End of variables declaration//GEN-END:variables
}
