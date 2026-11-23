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
import java.util.List;
import controlador.P15_RecuentistaEditarHogaresAsignadosControlador;
    /**
     * Creates new form iniciarSesion
     */
public class P15_RecuentistaEditarHogaresAsignados1 extends javax.swing.JFrame {
   private P15_RecuentistaEditarHogaresAsignadosControlador controlador; 
private String cedulaRecuentista;
private String cedulaEncuestador;
private String numeroVivienda;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(P15_RecuentistaEditarHogaresAsignados1.class.getName());

    /**
     * Creates new form iniciarSesion
     */
    public P15_RecuentistaEditarHogaresAsignados1() {
        initComponents();
        tblTabla2.setSelectionMode(
    javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
);

tblTabla1.setSelectionMode(
    javax.swing.ListSelectionModel.SINGLE_SELECTION
);
        P15_RecuentistaEditarHogaresAsignadosControlador controlador =
        new P15_RecuentistaEditarHogaresAsignadosControlador(this);

controlador.iniciar();

        // 1. Fijar dimensiones de la ventana principal
        this.setSize(956, 670);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        // 2. Establecer AbsoluteLayout explícito en el contenedor principal
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // 3. Posicionar franja de logos en la cabecera
        getContentPane().add(pnlLogos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 110));

        // 4. Crear contenedor recortador (máscara) ocupando el área inferior
        javax.swing.JPanel pnlContenedor = new javax.swing.JPanel();
        pnlContenedor.setLayout(null);
        pnlContenedor.setBounds(0, 110, 915, 520);

        // Quitar el panel principal del frame y meterlo dentro de la máscara
        getContentPane().remove(pnlInterfaz1);
        pnlContenedor.add(pnlInterfaz1);
        getContentPane().add(pnlContenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 915, 520));

        // Establecer el tamaño real del panel interno desplazable a 1050 de alto
        pnlInterfaz1.setBounds(0, 0, 915, 1050);

        // 5. Ubicar jScrollBar1 a la derecha de extremo a extremo (Y=110 a Y=630)
        getContentPane().remove(jScrollBar1);
        getContentPane().add(jScrollBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(915, 110, 20, 520));

        // 6. Configurar el comportamiento del scrollbar con el alto de 1050
        int altoContenido = 1050; // Largo vertical ajustado a 1050
        int altoVisible = 520;

        jScrollBar1.setMinimum(0);
        jScrollBar1.setMaximum(altoContenido - altoVisible);
        jScrollBar1.setValue(0);
        jScrollBar1.setUnitIncrement(20);
        jScrollBar1.setBlockIncrement(100);

        jScrollBar1.addAdjustmentListener(e -> {
            int valorScroll = e.getValue();
            pnlInterfaz1.setLocation(0, -valorScroll);
            pnlContenedor.repaint();
        });

        // Habilitar desplazamiento con la rueda del ratón
        pnlContenedor.addMouseWheelListener(e -> {
            int pasos = e.getWheelRotation();
            int nuevoValor = jScrollBar1.getValue() + (pasos * 25);
            jScrollBar1.setValue(nuevoValor);
        });

        // 7. Configuración de tablas
        configurarTabla(tblTabla1, sptabla1);
        configurarTabla(tblTabla2, spTabla2);

        getContentPane().revalidate();
        getContentPane().repaint();
    }
    
    public void recibirDatos(
        String cedulaEncuestador,
        String numeroVivienda) {

    this.cedulaEncuestador = cedulaEncuestador;
    this.numeroVivienda = numeroVivienda;

}
    
    
public void mostrarEncuestadores(List<Object[]> encuestadores) {

    javax.swing.table.DefaultTableModel modelo =
            (javax.swing.table.DefaultTableModel)
            tblTabla1.getModel();

    modelo.setRowCount(0);

    for (Object[] fila : encuestadores) {
        modelo.addRow(fila);
    }
}

public void mostrarHogaresDisponibles(List<Object[]> hogares) {

    javax.swing.table.DefaultTableModel modelo =
            (javax.swing.table.DefaultTableModel)
            tblTabla2.getModel();

    modelo.setRowCount(0);

    for (Object[] fila : hogares) {
        modelo.addRow(fila);
    }
}


public java.util.List<String> obtenerHogaresSeleccionados() {

    java.util.List<String> hogares = new java.util.ArrayList<>();

    int[] filas = tblTabla2.getSelectedRows();

    for (int fila : filas) {

        fila = tblTabla2.convertRowIndexToModel(fila);

        String numeroVivienda =
                tblTabla2.getValueAt(fila, 1).toString();

        hogares.add(numeroVivienda);
    }

    return hogares;
}


public String obtenerCedulaEncuestadorSeleccionado() {

    int fila = tblTabla1.getSelectedRow();

    if (fila == -1) {
        return null;
    }

    fila = tblTabla1.convertRowIndexToModel(fila);

    return tblTabla1.getValueAt(fila, 1).toString();
}


    
    public String obtenerCedulaEncuestador() {
    return cedulaEncuestador;
}

public String obtenerNumeroVivienda() {
    return numeroVivienda;
}
    
public P15_RecuentistaEditarHogaresAsignados1(
        String cedulaRecuentista,
        String cedulaEncuestador,
        String numeroVivienda) {

    this();

    this.cedulaRecuentista = cedulaRecuentista;
    this.cedulaEncuestador = cedulaEncuestador;
    this.numeroVivienda = numeroVivienda;
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

        // Ajustar la altura de los encabezados
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
        pnlInterfaz1 = new javax.swing.JPanel();
        lblEditarAsignarHogarEncuestador = new javax.swing.JLabel();
        lblEncuestadorAHogar = new javax.swing.JLabel();
        sptabla1 = new javax.swing.JScrollPane();
        tblTabla1 = new javax.swing.JTable();
        lblSeleccioneHogares = new javax.swing.JLabel();
        spTabla2 = new javax.swing.JScrollPane();
        tblTabla2 = new javax.swing.JTable();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jScrollBar1 = new javax.swing.JScrollBar();

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 257, Short.MAX_VALUE)
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

        pnlInterfaz1.setBackground(new java.awt.Color(255, 255, 255));
        pnlInterfaz1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(1, 1, 1)));
        pnlInterfaz1.setPreferredSize(new java.awt.Dimension(660, 1000));

        lblEditarAsignarHogarEncuestador.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        lblEditarAsignarHogarEncuestador.setText("Editar asignación del hogar");

        lblEncuestadorAHogar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblEncuestadorAHogar.setText("Seleccione el encuestador ");

        sptabla1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        sptabla1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tblTabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Cédula", "Nombre", "Apellido", "Hogares asignados"
            }
        ));
        sptabla1.setViewportView(tblTabla1);

        lblSeleccioneHogares.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblSeleccioneHogares.setText("Seleccione los hogares ");

        spTabla2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        spTabla2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tblTabla2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                " #", "Número vivienda", "Dirección", "Responsable del hogar", "Teléfono"
            }
        ));
        spTabla2.setViewportView(tblTabla2);

        btnCancelar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        btnGuardar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        javax.swing.GroupLayout pnlInterfaz1Layout = new javax.swing.GroupLayout(pnlInterfaz1);
        pnlInterfaz1.setLayout(pnlInterfaz1Layout);
        pnlInterfaz1Layout.setHorizontalGroup(
            pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                        .addGap(254, 254, 254)
                        .addComponent(lblEditarAsignarHogarEncuestador))
                    .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                                .addComponent(btnCancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnGuardar))
                            .addComponent(lblEncuestadorAHogar)
                            .addComponent(sptabla1)
                            .addComponent(lblSeleccioneHogares)
                            .addComponent(spTabla2, javax.swing.GroupLayout.PREFERRED_SIZE, 633, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 154, Short.MAX_VALUE)
                .addComponent(jScrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlInterfaz1Layout.setVerticalGroup(
            pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(lblEditarAsignarHogarEncuestador)
                        .addGap(93, 93, 93)
                        .addComponent(lblEncuestadorAHogar)
                        .addGap(62, 62, 62)
                        .addComponent(sptabla1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65)
                        .addComponent(lblSeleccioneHogares)
                        .addGap(53, 53, 53)
                        .addComponent(spTabla2, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75)
                        .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCancelar)
                            .addComponent(btnGuardar)))
                    .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 1190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(pnlInterfaz1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 940, 890));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        if (controlador != null) {
    controlador.guardarAsignacion();
}
    }//GEN-LAST:event_btnGuardarActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new P15_RecuentistaEditarHogaresAsignados1().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JLabel lblAdministrador;
    private javax.swing.JLabel lblEditarAsignarHogarEncuestador;
    private javax.swing.JLabel lblEncuestadorAHogar;
    private javax.swing.JLabel lblSeleccioneHogares;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel pnlInterfaz1;
    private javax.swing.JPanel pnlLogos;
    private javax.swing.JScrollPane spTabla2;
    private javax.swing.JScrollPane sptabla1;
    private javax.swing.JTable tblTabla1;
    private javax.swing.JTable tblTabla2;
    // End of variables declaration//GEN-END:variables
}
