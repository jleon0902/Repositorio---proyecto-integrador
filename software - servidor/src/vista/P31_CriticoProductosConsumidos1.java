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
public class P31_CriticoProductosConsumidos1 extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(P31_CriticoProductosConsumidos1.class.getName());

    /**
     * Creates new form iniciarSesion
     */
public P31_CriticoProductosConsumidos1() {
        initComponents();

        configurarTabla(tblProductosMasConsumidos, spProductosMasConsumidos);
        configurarTabla(tblProductosMenosConsumidos, spProductosMenosConsumidos);

        // 1. Configuración de tamaño inicial y permitir redimensionar
        this.setResizable(true);
        this.setSize(956, 670);
        this.setLocationRelativeTo(null);

        // 2. Layout absoluto en el frame principal
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // 3. Crear contenedor recortador (máscara) para la zona blanca
        javax.swing.JPanel pnlContenedor = new javax.swing.JPanel();
        pnlContenedor.setLayout(null);
        pnlContenedor.setBackground(java.awt.Color.WHITE);

        // Remover pnlProductosConsumidos del frame y moverlo dentro del contenedor recortador
        getContentPane().remove(pnlProductosConsumidos);
        pnlProductosConsumidos.setBorder(null);
        pnlContenedor.add(pnlProductosConsumidos);

        // 4. Establecer el tamaño vertical real del contenido deslizable a 1050px
        int altoContenido = 1050;

        // 5. Configurar el comportamiento del scrollbar manual
        jScrollBar1.setMinimum(0);
        jScrollBar1.setUnitIncrement(20);
        jScrollBar1.setBlockIncrement(100);

        jScrollBar1.addAdjustmentListener(e -> {
            int valorScroll = e.getValue();
            pnlProductosConsumidos.setLocation(0, -valorScroll);
            pnlContenedor.repaint();
        });

        // Habilitar desplazamiento con la rueda del ratón
        pnlContenedor.addMouseWheelListener(e -> {
            int pasos = e.getWheelRotation();
            int nuevoValor = jScrollBar1.getValue() + (pasos * 25);
            jScrollBar1.setValue(nuevoValor);
        });

        // 6. Listener para adaptar la franja rosa, el área blanca y el scrollbar al maximizar
        Runnable reajustarDimensiones = () -> {
            int anchoVentana = getContentPane().getWidth();
            int altoVentana = getContentPane().getHeight();

            int altoHeader = 110;
            int anchoScroll = 20;
            int altoAreaInferior = Math.max(100, altoVentana - altoHeader);
            int anchoBlanco = Math.max(915, anchoVentana - anchoScroll);

            // Reubicar la barra superior de logos al 100% del ancho
            getContentPane().remove(pnlLogos);
            getContentPane().add(pnlLogos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, anchoVentana, altoHeader));

            // Reubicar el contenedor con borde inferior/izquierdo continuo
            getContentPane().remove(pnlContenedor);
            pnlContenedor.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 0, java.awt.Color.BLACK));
            pnlContenedor.setBounds(0, altoHeader, anchoBlanco, altoAreaInferior);

            // Ajustar tamaño y posición del panel interno
            pnlProductosConsumidos.setBounds(0, pnlProductosConsumidos.getY(), anchoBlanco, altoContenido);

            // Reubicar la barra de desplazamiento a la derecha
            getContentPane().remove(jScrollBar1);
            getContentPane().add(jScrollBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(anchoVentana - anchoScroll, altoHeader, anchoScroll, altoAreaInferior));

            // Actualizar el valor máximo según el alto visible actual
            jScrollBar1.setMaximum(Math.max(0, altoContenido - altoAreaInferior));

            getContentPane().add(pnlContenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, altoHeader, anchoBlanco, altoAreaInferior));

            getContentPane().revalidate();
            getContentPane().repaint();
        };

        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                reajustarDimensiones.run();
            }
        });

        reajustarDimensiones.run();
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

private void volver() {
    int respuesta = javax.swing.JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de que desea volver?",
            "Confirmar regreso",
            javax.swing.JOptionPane.YES_NO_OPTION
    );

    if (respuesta == javax.swing.JOptionPane.YES_OPTION) {
        this.dispose();
    }
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
        pnlProductosConsumidos = new javax.swing.JPanel();
        lblProductosConsumidos = new javax.swing.JLabel();
        lblProductosMasMenosConsumidosFrecuenciaCompra = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        lblProductosMenosConsumidos = new javax.swing.JLabel();
        lblProdutosMasConsumidos = new javax.swing.JLabel();
        spProductosMasConsumidos = new javax.swing.JScrollPane();
        tblProductosMasConsumidos = new javax.swing.JTable();
        spProductosMenosConsumidos = new javax.swing.JScrollPane();
        tblProductosMenosConsumidos = new javax.swing.JTable();
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

        pnlProductosConsumidos.setBackground(new java.awt.Color(255, 255, 255));
        pnlProductosConsumidos.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));

        lblProductosConsumidos.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        lblProductosConsumidos.setText("Productos consumidos");

        lblProductosMasMenosConsumidosFrecuenciaCompra.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblProductosMasMenosConsumidosFrecuenciaCompra.setText("Productos más consumidos,menos consumidos y su frecuencia de compra");

        btnVolver.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnVolver.setText("Volver");
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        lblProductosMenosConsumidos.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblProductosMenosConsumidos.setText("Productos menos consumidos");

        lblProdutosMasConsumidos.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblProdutosMasConsumidos.setText("Productos más consumidos");

        spProductosMasConsumidos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        spProductosMasConsumidos.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        spProductosMasConsumidos.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tblProductosMasConsumidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre de los productos", "Frrecuecia de compra"
            }
        ));
        tblProductosMasConsumidos.setShowHorizontalLines(true);
        tblProductosMasConsumidos.setShowVerticalLines(true);
        spProductosMasConsumidos.setViewportView(tblProductosMasConsumidos);

        spProductosMenosConsumidos.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        spProductosMenosConsumidos.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tblProductosMenosConsumidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre de los productos", "Frecuencias de compra"
            }
        ));
        tblProductosMenosConsumidos.setShowHorizontalLines(true);
        tblProductosMenosConsumidos.setShowVerticalLines(true);
        spProductosMenosConsumidos.setViewportView(tblProductosMenosConsumidos);

        javax.swing.GroupLayout pnlProductosConsumidosLayout = new javax.swing.GroupLayout(pnlProductosConsumidos);
        pnlProductosConsumidos.setLayout(pnlProductosConsumidosLayout);
        pnlProductosConsumidosLayout.setHorizontalGroup(
            pnlProductosConsumidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProductosConsumidosLayout.createSequentialGroup()
                .addGroup(pnlProductosConsumidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlProductosConsumidosLayout.createSequentialGroup()
                        .addGap(287, 287, 287)
                        .addComponent(lblProductosConsumidos))
                    .addGroup(pnlProductosConsumidosLayout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addGroup(pnlProductosConsumidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnVolver)
                            .addGroup(pnlProductosConsumidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblProdutosMasConsumidos, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblProductosMasMenosConsumidosFrecuenciaCompra, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblProductosMenosConsumidos, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(spProductosMenosConsumidos, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(spProductosMasConsumidos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 707, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                .addComponent(jScrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlProductosConsumidosLayout.setVerticalGroup(
            pnlProductosConsumidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProductosConsumidosLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlProductosConsumidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlProductosConsumidosLayout.createSequentialGroup()
                        .addComponent(jScrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 1012, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlProductosConsumidosLayout.createSequentialGroup()
                        .addComponent(lblProductosConsumidos)
                        .addGap(59, 59, 59)
                        .addComponent(lblProductosMasMenosConsumidosFrecuenciaCompra)
                        .addGap(35, 35, 35)
                        .addComponent(lblProdutosMasConsumidos)
                        .addGap(52, 52, 52)
                        .addComponent(spProductosMasConsumidos, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblProductosMenosConsumidos)
                        .addGap(29, 29, 29)
                        .addComponent(spProductosMenosConsumidos, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(btnVolver)
                        .addGap(41, 41, 41))))
        );

        getContentPane().add(pnlProductosConsumidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 108, 940, 1000));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        // TODO add your handling code here:
          volver();
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
        java.awt.EventQueue.invokeLater(() -> new P31_CriticoProductosConsumidos1().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JLabel lblAdministrador;
    private javax.swing.JLabel lblProductosConsumidos;
    private javax.swing.JLabel lblProductosMasMenosConsumidosFrecuenciaCompra;
    private javax.swing.JLabel lblProductosMenosConsumidos;
    private javax.swing.JLabel lblProdutosMasConsumidos;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel pnlLogos;
    private javax.swing.JPanel pnlProductosConsumidos;
    private javax.swing.JScrollPane spProductosMasConsumidos;
    private javax.swing.JScrollPane spProductosMenosConsumidos;
    private javax.swing.JTable tblProductosMasConsumidos;
    private javax.swing.JTable tblProductosMenosConsumidos;
    // End of variables declaration//GEN-END:variables
}
