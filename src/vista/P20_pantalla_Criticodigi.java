/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

/**
 *
 * @author VALENTINA
 */
public class P20_pantalla_Criticodigi extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(P20_pantalla_Criticodigi.class.getName());

    /**
     * Creates new form P20_pantalla_Criticodigi
     */

public P20_pantalla_Criticodigi() {
    initComponents();
    
  configurarSeleccionUnica();

tblTablaFamiliasEncuesta.setColumnSelectionAllowed(false);
tblTablaFamiliasEncuesta.setRowSelectionAllowed(true);
tblTablaFamiliasEncuesta.setSelectionMode(
    javax.swing.ListSelectionModel.SINGLE_SELECTION
);

txtRegion.setEditable(false);
txtDepartamento.setEditable(false);
txtMunicipio.setEditable(false);

tblTablaFamiliasEncuesta.setSelectionMode(
    javax.swing.ListSelectionModel.SINGLE_SELECTION
);
tblTablaFamiliasEncuesta.setRowSelectionAllowed(true);
tblTablaFamiliasEncuesta.setColumnSelectionAllowed(false);
    txtRegion.setEditable(false);
    txtDepartamento.setEditable(false);
    txtMunicipio.setEditable(false);

    javax.swing.table.DefaultTableModel modelo =
        (javax.swing.table.DefaultTableModel)
            tblTablaFamiliasEncuesta.getModel();

    // La columna 4 ya existe y es de tipo Boolean.
    tblTablaFamiliasEncuesta.getColumnModel()
        .getColumn(4).setCellRenderer(
            tblTablaFamiliasEncuesta.getDefaultRenderer(Boolean.class));
   

    configurarSeleccionUnica();

    for (int i = 0;
            i < tblTablaFamiliasEncuesta.getColumnCount(); i++) {

        javax.swing.table.TableColumn columna =
            tblTablaFamiliasEncuesta.getColumnModel().getColumn(i);

        java.awt.FontMetrics metricas =
            tblTablaFamiliasEncuesta.getFontMetrics(
                tblTablaFamiliasEncuesta.getTableHeader().getFont());

        int anchoTitulo = metricas.stringWidth(
            columna.getHeaderValue().toString()) + 20;

        columna.setPreferredWidth(anchoTitulo);
    }
}


public void configurarFiltros(
        String region, String departamento, String municipio) {

    // Mostrar los filtros seleccionados
    txtRegion.setText(region == null ? "" : region);
    txtDepartamento.setText(
        departamento == null ? "" : departamento
    );
    txtMunicipio.setText(
        municipio == null ? "" : municipio
    );

    // Para la consulta, "Todos" significa no filtrar por región
    String regionFiltro =
        "Todos".equalsIgnoreCase(region) ? null : region;

    cargarFamiliasFinalizadas(
        regionFiltro,
        departamento,
        municipio
    );
}


private void cargarFamiliasFinalizadas(
        String region, String departamento, String municipio) {

    modelo.FamiliasDao dao = new modelo.FamiliasDao();

    javax.swing.table.DefaultTableModel modeloTabla =
        (javax.swing.table.DefaultTableModel)
            tblTablaFamiliasEncuesta.getModel();

    modeloTabla.setRowCount(0);

    try {
        java.util.List<Object[]> familias =
            dao.obtenerFamiliasFinalizadas(
                region, departamento, municipio);

        int numero = 1;

        for (Object[] familia : familias) {
            modeloTabla.addRow(new Object[]{
                numero++,
                familia[0],
                familia[1],
                familia[2],
                Boolean.FALSE
            });
        }

        if (familias.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                this,
                "No se encontraron familias con encuestas finalizadas "
                + "para los filtros seleccionados.");
        }

    } catch (java.sql.SQLException e) {
        javax.swing.JOptionPane.showMessageDialog(
            this,
            "Error al cargar las familias: " + e.getMessage());
    }
}








private void configurarSeleccionUnica() {

    javax.swing.table.DefaultTableModel modelo =
        new javax.swing.table.DefaultTableModel(
            new Object[][] {},
            new String[] {
                "#",
                "Número vivienda",
                "Responsable del hogar",
                "Estado de la encuesta",
                ""
            }
        ) {
            @Override
            public Class<?> getColumnClass(int columna) {
                if (columna == 4) {
                    return Boolean.class;
                }
                return Object.class;
            }

            @Override
            public void setValueAt(Object valor, int fila, int columna) {

                if (columna == 4 && Boolean.TRUE.equals(valor)) {
                    for (int i = 0; i < getRowCount(); i++) {
                        if (i != fila) {
                            super.setValueAt(Boolean.FALSE, i, 4);
                        }
                    }
                }

                super.setValueAt(valor, fila, columna);
            }
        };

    tblTablaFamiliasEncuesta.setModel(modelo);
}








    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlFamiliasEncuestadas = new javax.swing.JPanel();
        btnVerFamilia = new javax.swing.JButton();
        lblFamiliasEncuestadas = new javax.swing.JLabel();
        lblRegion = new javax.swing.JLabel();
        lblDepartamento = new javax.swing.JLabel();
        lblMunicipio = new javax.swing.JLabel();
        spTablaFamiliasEncuesta = new javax.swing.JScrollPane();
        tblTablaFamiliasEncuesta = new javax.swing.JTable();
        txtMunicipio = new javax.swing.JTextField();
        txtDepartamento = new javax.swing.JTextField();
        txtRegion = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        pnlLogos = new javax.swing.JPanel();
        lblDane = new javax.swing.JLabel();
        lblEnph = new javax.swing.JLabel();
        lblCriticoDigitador = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlFamiliasEncuestadas.setBackground(new java.awt.Color(255, 255, 255));
        pnlFamiliasEncuestadas.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));
        pnlFamiliasEncuestadas.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        btnVerFamilia.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnVerFamilia.setText("Ver hogar");
        btnVerFamilia.addActionListener(this::btnVerFamiliaActionPerformed);

        lblFamiliasEncuestadas.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblFamiliasEncuestadas.setText("Hogares encuestados");

        lblRegion.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblRegion.setText("Región:");

        lblDepartamento.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblDepartamento.setText("Departamento:");

        lblMunicipio.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMunicipio.setText("Municipio");

        spTablaFamiliasEncuesta.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tblTablaFamiliasEncuesta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Número vivienda", "Responsable del hogar", "Estado de la encuesta", ""
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

        jButton1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jButton1.setText("Volver");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        javax.swing.GroupLayout pnlFamiliasEncuestadasLayout = new javax.swing.GroupLayout(pnlFamiliasEncuestadas);
        pnlFamiliasEncuestadas.setLayout(pnlFamiliasEncuestadasLayout);
        pnlFamiliasEncuestadasLayout.setHorizontalGroup(
            pnlFamiliasEncuestadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFamiliasEncuestadasLayout.createSequentialGroup()
                .addGroup(pnlFamiliasEncuestadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlFamiliasEncuestadasLayout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVerFamilia))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlFamiliasEncuestadasLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(pnlFamiliasEncuestadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spTablaFamiliasEncuesta, javax.swing.GroupLayout.DEFAULT_SIZE, 1005, Short.MAX_VALUE)
                            .addGroup(pnlFamiliasEncuestadasLayout.createSequentialGroup()
                                .addGroup(pnlFamiliasEncuestadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(pnlFamiliasEncuestadasLayout.createSequentialGroup()
                                        .addComponent(lblMunicipio)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlFamiliasEncuestadasLayout.createSequentialGroup()
                                        .addComponent(lblRegion)
                                        .addGap(105, 105, 105)
                                        .addComponent(txtRegion, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlFamiliasEncuestadasLayout.createSequentialGroup()
                                        .addComponent(lblDepartamento)
                                        .addGap(54, 54, 54)
                                        .addComponent(txtDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(37, 37, 37))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFamiliasEncuestadasLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblFamiliasEncuestadas)
                .addGap(425, 425, 425))
        );
        pnlFamiliasEncuestadasLayout.setVerticalGroup(
            pnlFamiliasEncuestadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFamiliasEncuestadasLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(lblFamiliasEncuestadas)
                .addGap(18, 18, 18)
                .addGroup(pnlFamiliasEncuestadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRegion, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRegion))
                .addGap(11, 11, 11)
                .addGroup(pnlFamiliasEncuestadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDepartamento))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFamiliasEncuestadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMunicipio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(spTablaFamiliasEncuesta, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlFamiliasEncuestadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVerFamilia)
                    .addComponent(jButton1))
                .addGap(37, 37, 37))
        );

        pnlLogos.setBackground(new java.awt.Color(255, 235, 229));
        pnlLogos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblDane.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/logo1.png"))); // NOI18N

        lblEnph.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/logo2.png"))); // NOI18N

        lblCriticoDigitador.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        lblCriticoDigitador.setText("Crítico digitador");

        javax.swing.GroupLayout pnlLogosLayout = new javax.swing.GroupLayout(pnlLogos);
        pnlLogos.setLayout(pnlLogosLayout);
        pnlLogosLayout.setHorizontalGroup(
            pnlLogosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLogosLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblDane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblEnph)
                .addGap(80, 80, 80)
                .addComponent(lblCriticoDigitador)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlLogosLayout.setVerticalGroup(
            pnlLogosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLogosLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlLogosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblDane)
                    .addComponent(lblEnph))
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLogosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblCriticoDigitador)
                .addGap(35, 35, 35))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlLogos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 2, Short.MAX_VALUE)
                .addComponent(pnlFamiliasEncuestadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlLogos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlFamiliasEncuestadas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVerFamiliaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerFamiliaActionPerformed
int filaSeleccionada =
        tblTablaFamiliasEncuesta.getSelectedRow();

    if (filaSeleccionada == -1) {
        javax.swing.JOptionPane.showMessageDialog(
            this,
            "Selecciona una familia primero.",
            "Familia no seleccionada",
            javax.swing.JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    int respuesta = javax.swing.JOptionPane.showConfirmDialog(
        this,
        "¿Desea ver la información de la familia seleccionada?",
        "Confirmar",
        javax.swing.JOptionPane.YES_NO_OPTION
    );

    if (respuesta == javax.swing.JOptionPane.YES_OPTION) {

        P21_pantalla_Criticodigi pantalla =
            new P21_pantalla_Criticodigi();

        pantalla.setLocationRelativeTo(this);
        pantalla.setVisible(true);

        this.dispose();
    }
    }//GEN-LAST:event_btnVerFamiliaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
            P19_pantalla_Criticodigi pantalla =
            new P19_pantalla_Criticodigi();

    pantalla.setLocationRelativeTo(this);
    pantalla.setVisible(true);

    this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new P20_pantalla_Criticodigi().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnVerFamilia;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel lblCriticoDigitador;
    private javax.swing.JLabel lblDane;
    private javax.swing.JLabel lblDepartamento;
    private javax.swing.JLabel lblEnph;
    private javax.swing.JLabel lblFamiliasEncuestadas;
    private javax.swing.JLabel lblMunicipio;
    private javax.swing.JLabel lblRegion;
    private javax.swing.JPanel pnlFamiliasEncuestadas;
    private javax.swing.JPanel pnlLogos;
    private javax.swing.JScrollPane spTablaFamiliasEncuesta;
    private javax.swing.JTable tblTablaFamiliasEncuesta;
    private javax.swing.JTextField txtDepartamento;
    private javax.swing.JTextField txtMunicipio;
    private javax.swing.JTextField txtRegion;
    // End of variables declaration//GEN-END:variables
}
