/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

/**
 *
 * @author usuario
 */
import java.awt.Component;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.table.TableCellRenderer;
public class P10_RecuentistaVerHogares1 extends javax.swing.JFrame {
private String cedulaRecuentista;

public void setCedulaRecuentista(String cedulaRecuentista) {
    this.cedulaRecuentista = cedulaRecuentista;
}
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(P10_RecuentistaVerHogares1.class.getName());

    // Guarda el ancho inicial de cada columna.
    private int[] anchosIniciales;
    // Ancho mínimo deseado para cada columna.
private int[] anchosMinimos = {
    45,   // #
    150,  // Número vivienda
    180,  // Dirección
    220,  // Responsable del hogar
    120   // Teléfono
};

    public P10_RecuentistaVerHogares1() {
        initComponents();
        // LA TABLA SOLAMENTE PERMITE CONSULTAR INFORMACIÓN.
tblTabla.setDefaultEditor(Object.class, null);

// NO PERMITIR SELECCIONAR VARIAS FILAS.
tblTabla.setSelectionMode(
    javax.swing.ListSelectionModel.SINGLE_SELECTION
);

// EL BOTÓN INICIA DESACTIVADO.
btnInformacionDelHogar.setEnabled(false);

// ACTIVAR O DESACTIVAR EL BOTÓN SEGÚN LA SELECCIÓN.
tblTabla.getSelectionModel().addListSelectionListener(e -> {

    if (!e.getValueIsAdjusting()) {

        btnInformacionDelHogar.setEnabled(
            tblTabla.getSelectedRow() != -1
        );

    }

});

        // Configuración del encabezado.
        tblTabla.getTableHeader().setReorderingAllowed(false);
        tblTabla.getTableHeader().setResizingAllowed(false);
        // Centrar los nombres de las columnas.
javax.swing.table.DefaultTableCellRenderer encabezadoCentrado =
        (javax.swing.table.DefaultTableCellRenderer)
        tblTabla.getTableHeader().getDefaultRenderer();

encabezadoCentrado.setHorizontalAlignment(
        javax.swing.SwingConstants.CENTER
);
        // Aumentar la altura del encabezado.
tblTabla.getTableHeader().setPreferredSize(
        new java.awt.Dimension(0, 40)
);

        // Guardar los anchos iniciales.
        guardarAnchosIniciales();

        // Evitar que las columnas se compriman.
        tblTabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        // Detectar inserciones, modificaciones y eliminaciones.
        tblTabla.getModel().addTableModelListener(e ->
                javax.swing.SwingUtilities.invokeLater(this::ajustarAnchoColumnas)
        );

        // Ajustar las columnas al iniciar.
        ajustarAnchoColumnas();
        // Mostrar la tabla desde la primera columna.
javax.swing.SwingUtilities.invokeLater(() -> {
    spTabla.getHorizontalScrollBar().setValue(0);
    spTabla.getViewport().setViewPosition(new java.awt.Point(0, 0));
});
    }

    // Guardar los tamaños originales de las columnas.
    private void guardarAnchosIniciales() {

        anchosIniciales = new int[tblTabla.getColumnCount()];

        for (int columna = 0; columna < tblTabla.getColumnCount(); columna++) {

            anchosIniciales[columna] = tblTabla.getColumnModel()
                    .getColumn(columna).getWidth();
        }
    }

    // Ajustar dinámicamente el ancho de las columnas.
// Ajustar dinámicamente el ancho de las columnas.
private void ajustarAnchoColumnas() {

    if (tblTabla.getColumnCount() == 0 || anchosIniciales == null) {
        return;
    }

    for (int columna = 0; columna < tblTabla.getColumnCount(); columna++) {

        // Mantener el ancho inicial o el mínimo establecido.
        int anchoNecesario = Math.max(
                anchosIniciales[columna],
                anchosMinimos[columna]
        );

        // Revisar el encabezado.
        TableCellRenderer rendererEncabezado =
                tblTabla.getTableHeader().getDefaultRenderer();

        Component componenteEncabezado =
                rendererEncabezado.getTableCellRendererComponent(
                        tblTabla,
                        tblTabla.getColumnName(columna),
                        false,
                        false,
                        -1,
                        columna
                );

        anchoNecesario = Math.max(
                anchoNecesario,
                componenteEncabezado.getPreferredSize().width + 12
        );

        // Revisar los datos de cada fila.
        for (int fila = 0; fila < tblTabla.getRowCount(); fila++) {

            Object valor = tblTabla.getValueAt(fila, columna);

            if (valor != null) {

                TableCellRenderer renderer =
                        tblTabla.getCellRenderer(fila, columna);

                Component componente =
                        renderer.getTableCellRendererComponent(
                                tblTabla,
                                valor,
                                false,
                                false,
                                fila,
                                columna
                        );

                anchoNecesario = Math.max(
                        anchoNecesario,
                        componente.getPreferredSize().width + 12
                );
            }
        }

        // Aplicar el ancho calculado.
        javax.swing.table.TableColumn tableColumn =
                tblTabla.getColumnModel().getColumn(columna);

        tableColumn.setPreferredWidth(anchoNecesario);
        tableColumn.setWidth(anchoNecesario);
    }

    tblTabla.revalidate();
    tblTabla.repaint();
}



public void eliminarFilaTabla(String numeroVivienda) {

    javax.swing.table.DefaultTableModel modelo =
            (javax.swing.table.DefaultTableModel) tblTabla.getModel();

    for (int fila = 0; fila < modelo.getRowCount(); fila++) {

        Object valor = modelo.getValueAt(fila, 1);

        if (valor != null &&
                valor.toString().trim().equals(numeroVivienda.trim())) {

            modelo.removeRow(fila);
            break;
        }
    }

    tblTabla.clearSelection();
    btnInformacionDelHogar.setEnabled(false);

    ajustarAnchoColumnas();

    tblTabla.revalidate();
    tblTabla.repaint();
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
        btnInformacionDelHogar = new javax.swing.JButton();
        spTabla = new javax.swing.JScrollPane();
        tblTabla = new javax.swing.JTable();
        lblVerHogaresInterfaz = new javax.swing.JLabel();
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
        lblUsuario.setText("Juan Camilo Perez");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 265, Short.MAX_VALUE)
                .addGroup(pnlLogosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLogosLayout.createSequentialGroup()
                        .addComponent(lblAdministrador)
                        .addGap(103, 103, 103))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLogosLayout.createSequentialGroup()
                        .addComponent(lblUsuario)
                        .addGap(95, 95, 95))))
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
        pnlMenuInterfaz1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(1, 1, 1)));
        pnlMenuInterfaz1.setPreferredSize(new java.awt.Dimension(692, 522));

        btnInformacionDelHogar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnInformacionDelHogar.setText("Ver informacion del hogar");
        btnInformacionDelHogar.addActionListener(this::btnInformacionDelHogarActionPerformed);

        spTabla.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        spTabla.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tblTabla.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        tblTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "<html><center>#</center></html>", "<html><center>Número vivienda</center></html>", "<html><center>Dirección</center></html>", "<html><center>Responsable del<br>hogar</cneter></html>", "<html><center>Teléfono</center></html>"
            }
        ));
        spTabla.setViewportView(tblTabla);

        lblVerHogaresInterfaz.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        lblVerHogaresInterfaz.setText("Ver hogares");

        javax.swing.GroupLayout pnlMenuInterfaz1Layout = new javax.swing.GroupLayout(pnlMenuInterfaz1);
        pnlMenuInterfaz1.setLayout(pnlMenuInterfaz1Layout);
        pnlMenuInterfaz1Layout.setHorizontalGroup(
            pnlMenuInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuInterfaz1Layout.createSequentialGroup()
                .addGap(0, 35, Short.MAX_VALUE)
                .addGroup(pnlMenuInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuInterfaz1Layout.createSequentialGroup()
                        .addGroup(pnlMenuInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(spTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnInformacionDelHogar))
                        .addGap(42, 42, 42))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuInterfaz1Layout.createSequentialGroup()
                        .addComponent(lblVerHogaresInterfaz)
                        .addGap(250, 250, 250))))
        );
        pnlMenuInterfaz1Layout.setVerticalGroup(
            pnlMenuInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuInterfaz1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(lblVerHogaresInterfaz)
                .addGap(46, 46, 46)
                .addComponent(spTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addComponent(btnInformacionDelHogar)
                .addGap(39, 39, 39))
        );

        getContentPane().add(pnlMenuInterfaz1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 110, 690, 510));

        pnlMenu.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 0, new java.awt.Color(1, 1, 1)));
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCerrarSesion)
                .addGap(29, 29, 29))
        );

        getContentPane().add(pnlMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 250, 510));

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

    private void btnInformacionDelHogarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInformacionDelHogarActionPerformed
        // TODO add your handling code here:
         abrirInformacionHogar();
    }//GEN-LAST:event_btnInformacionDelHogarActionPerformed
private void abrirInformacionHogar() {

    int respuesta = javax.swing.JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de que desea ver la información del hogar?",
            "Confirmar",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE
    );

    if (respuesta == javax.swing.JOptionPane.YES_OPTION) {

        String numeroVivienda = getNumeroViviendaSeleccionada();

        if (numeroVivienda == null) {
            return;
        }

        P11_RecuentistaInformacionFamilia pantalla =
                new P11_RecuentistaInformacionFamilia(
                        numeroVivienda,
                        this
                );

        pantalla.setLocationRelativeTo(this);

        this.setVisible(false);

        pantalla.setVisible(true);
    }
} /**
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
        java.awt.EventQueue.invokeLater(() -> new P10_RecuentistaVerHogares1().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnInformacionDelHogar;
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
    private javax.swing.JLabel lblMenu;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lblVerHogaresInterfaz;
    private javax.swing.JPanel pnlLogos;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlMenuInterfaz1;
    private javax.swing.JScrollPane spTabla;
    private javax.swing.JTable tblTabla;
    // End of variables declaration//GEN-END:variables


public javax.swing.JPanel getPanelVerHogares() {
    return pnlMenuInterfaz1;
}

public javax.swing.JTable getTablaHogares() {
    return tblTabla;
}

public String getNumeroViviendaSeleccionada() {

    int fila = tblTabla.getSelectedRow();

    if (fila == -1) {
        return null;
    }

    return tblTabla.getValueAt(fila, 1).toString();
}

public void actualizarTabla() {

    controlador.P10_RecuentistaVerHogaresControlador controlador =
            new controlador.P10_RecuentistaVerHogaresControlador(this);

    controlador.cargarHogares();
}

public void actualizarFilaEliminada(String numeroVivienda) {

    eliminarFilaTabla(numeroVivienda);

}
}


