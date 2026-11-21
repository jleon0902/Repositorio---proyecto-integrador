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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import java.awt.BorderLayout;
import java.text.DecimalFormat;

import controlador.P29CriticoResumenGastoControlador;
public class P29_CriticoResumenGasto extends javax.swing.JFrame {
    private P29CriticoResumenGastoControlador controlador;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(P29_CriticoResumenGasto.class.getName());

    /**
     * Creates new form iniciarSesion
     */
public P29_CriticoResumenGasto() {
        initComponents();
        // Bloquear cajas de texto del informe.
txtGastoTotal.setEditable(false);
txtGastoPromedioDia.setEditable(false);
txtCantidadPromedioProductos.setEditable(false);
txtDiaMayorGasto.setEditable(false);
txtDiaMenorGasto.setEditable(false);
jtfValorProHogar.setEditable(false);
jtfValorProPersonal.setEditable(false);

        // 1. Configuración de tamaño inicial y capacidad de redimensionar
         setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(true);
        this.setSize(956, 670);
        this.setLocationRelativeTo(null);

        // 2. Establecer AbsoluteLayout explícito en el frame principal
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // 3. Crear máscara contenedora para el panel deslizable de resumen de gasto
        javax.swing.JPanel pnlContenedor = new javax.swing.JPanel();
        pnlContenedor.setLayout(null);
        pnlContenedor.setBackground(java.awt.Color.WHITE);

        // Retirar el panel interno del frame e insertarlo en el contenedor recortador
        getContentPane().remove(pnlResumenGasto);
        pnlResumenGasto.setBorder(null);
        pnlContenedor.add(pnlResumenGasto);

        // 4. Configurar límites iniciales del panel deslizable (alto interno = 750)
        int altoContenido = 750;

        // 5. Configurar el comportamiento del JScrollBar manual
        jScrollBar1.setMinimum(0);
        jScrollBar1.setUnitIncrement(20);
        jScrollBar1.setBlockIncrement(100);

        jScrollBar1.addAdjustmentListener(e -> {
            int valorScroll = e.getValue();
            pnlResumenGasto.setLocation(0, -valorScroll);
            pnlContenedor.repaint();
        });

        // Habilitar desplazamiento con la rueda del ratón
        pnlContenedor.addMouseWheelListener(e -> {
            int pasos = e.getWheelRotation();
            int nuevoValor = jScrollBar1.getValue() + (pasos * 25);
            jScrollBar1.setValue(nuevoValor);
        });

        // 6. Listener para adaptar dinámicamente la ventana al maximizar o estirar
        Runnable reajustarDimensiones = () -> {
            int anchoVentana = getContentPane().getWidth();
            int altoVentana = getContentPane().getHeight();

            int altoHeader = 110;
            int anchoScroll = 20;
            int altoAreaInferior = Math.max(100, altoVentana - altoHeader);
            int anchoBlanco = Math.max(915, anchoVentana - anchoScroll);

            // Reubicar la barra superior de logos abarcando el 100% del ancho
            getContentPane().remove(pnlLogos);
            getContentPane().add(pnlLogos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, anchoVentana, altoHeader));

            // Reubicar la máscara recortadora con borde exterior continuo
            getContentPane().remove(pnlContenedor);
            pnlContenedor.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 0, java.awt.Color.BLACK));
            pnlContenedor.setBounds(0, altoHeader, anchoBlanco, altoAreaInferior);

            // Ajustar panel desplazable interno
            pnlResumenGasto.setBounds(0, pnlResumenGasto.getY(), anchoBlanco, altoContenido);

            // Reubicar barra de desplazamiento a la derecha de la ventana
            getContentPane().remove(jScrollBar1);
            getContentPane().add(jScrollBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(anchoVentana - anchoScroll, altoHeader, anchoScroll, altoAreaInferior));

            // Actualizar máximo del scrollbar en relación con el espacio visible actual
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

public void iniciarInforme() {
    if (controlador != null) {
        controlador.cargarResumenGasto();
    }
}

public void setControlador(
        P29CriticoResumenGastoControlador controlador) {

    this.controlador = controlador;
}

public void cerrarVentana() {
    this.dispose();
}

public javax.swing.JPanel getPanelResumenGasto() {
    return pnlResumenGasto;
}

public void mostrarGrafica(
        double porcentajeHogar,
        double porcentajePersonal) {

    // Crear los datos de la gráfica
    DefaultPieDataset<String> datos = new DefaultPieDataset<>();

    datos.setValue("Hogar", porcentajeHogar);
    datos.setValue("Personal", porcentajePersonal);

    // Crear gráfica de torta
    JFreeChart grafica = ChartFactory.createPieChart(
            "Distribución porcentual del gasto",
            datos,
            true,
            true,
            false
    );

    // Configurar etiquetas
    PiePlot<String> plot =
            (PiePlot<String>) grafica.getPlot();

    plot.setLabelGenerator(
            new StandardPieSectionLabelGenerator(
                    "{0}: {2}",
                    new DecimalFormat("0.00"),
                    new DecimalFormat("0.00%")
            )
    );

    // Crear panel de la gráfica
    ChartPanel panelGrafica = new ChartPanel(grafica);

    panelGrafica.setMouseWheelEnabled(true);
    panelGrafica.setPreferredSize(
            new Dimension(247, 216)
    );

    // Insertar gráfica en pnlGrafica
    pnlGrafica.removeAll();
    pnlGrafica.setLayout(new BorderLayout());
    pnlGrafica.add(panelGrafica, BorderLayout.CENTER);

    pnlGrafica.revalidate();
    pnlGrafica.repaint();
}


public javax.swing.JTextField getTxtGastoTotal() {
    return txtGastoTotal;
}

public javax.swing.JTextField getTxtGastoPromedioDia() {
    return txtGastoPromedioDia;
}

public javax.swing.JTextField getTxtCantidadPromedioProductos() {
    return txtCantidadPromedioProductos;
}

public javax.swing.JTextField getTxtDiaMayorGasto() {
    return txtDiaMayorGasto;
}

public javax.swing.JTextField getTxtDiaMenorGasto() {
    return txtDiaMenorGasto;
}

public javax.swing.JTextField getJtfValorProHogar() {
    return jtfValorProHogar;
}

public javax.swing.JTextField getJtfValorProPersonal() {
    return jtfValorProPersonal;
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
        pnlResumenGasto = new javax.swing.JPanel();
        lblResumenGasto = new javax.swing.JLabel();
        lblGastoTotal = new javax.swing.JLabel();
        lblGastoTotalPromedio = new javax.swing.JLabel();
        lblCantidadPromedioProductos = new javax.swing.JLabel();
        lblDiaMayorGasto = new javax.swing.JLabel();
        lblDiaMenorGasto = new javax.swing.JLabel();
        lblDistribucionGasto = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        pnlGrafica = new javax.swing.JPanel();
        txtCantidadPromedioProductos = new javax.swing.JTextField();
        txtGastoPromedioDia = new javax.swing.JTextField();
        txtGastoTotal = new javax.swing.JTextField();
        txtDiaMayorGasto = new javax.swing.JTextField();
        txtDiaMenorGasto = new javax.swing.JTextField();
        jlvalorPromedioH = new javax.swing.JLabel();
        jlvalorPromedioP = new javax.swing.JLabel();
        jtfValorProHogar = new javax.swing.JTextField();
        jtfValorProPersonal = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
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
        lblUsuario.setText("Felipe Gomez");

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

        pnlResumenGasto.setBackground(new java.awt.Color(255, 255, 255));
        pnlResumenGasto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));

        lblResumenGasto.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        lblResumenGasto.setText("Resumen del gasto");

        lblGastoTotal.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblGastoTotal.setText("Gasto promedio por día:");

        lblGastoTotalPromedio.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblGastoTotalPromedio.setText("Gasto total promedio:");

        lblCantidadPromedioProductos.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblCantidadPromedioProductos.setText("<html><div style='text-align:left; width:100%'>Cantidad promedio de productos<br>adquiridos por los hogares</div></html>");

        lblDiaMayorGasto.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblDiaMayorGasto.setText("Día promedio de mayor gasto:");

        lblDiaMenorGasto.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblDiaMenorGasto.setText("Día promedio de menor gasto:");

        lblDistribucionGasto.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblDistribucionGasto.setText("Distribución del gasto");

        btnVolver.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnVolver.setText("Volver");
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        javax.swing.GroupLayout pnlGraficaLayout = new javax.swing.GroupLayout(pnlGrafica);
        pnlGrafica.setLayout(pnlGraficaLayout);
        pnlGraficaLayout.setHorizontalGroup(
            pnlGraficaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 247, Short.MAX_VALUE)
        );
        pnlGraficaLayout.setVerticalGroup(
            pnlGraficaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 216, Short.MAX_VALUE)
        );

        jlvalorPromedioH.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jlvalorPromedioH.setText("Valor promedio de lo que gasto en el hogar:");

        jlvalorPromedioP.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jlvalorPromedioP.setText("Valor promedio de lo que gasto en gastos personales:");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel3.setText("Comportamiento promedio del gasto");

        javax.swing.GroupLayout pnlResumenGastoLayout = new javax.swing.GroupLayout(pnlResumenGasto);
        pnlResumenGasto.setLayout(pnlResumenGastoLayout);
        pnlResumenGastoLayout.setHorizontalGroup(
            pnlResumenGastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlResumenGastoLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(pnlResumenGastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlResumenGastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnVolver)
                        .addGroup(pnlResumenGastoLayout.createSequentialGroup()
                            .addComponent(pnlGrafica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(28, 28, 28)
                            .addGroup(pnlResumenGastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jlvalorPromedioH)
                                .addComponent(jlvalorPromedioP)
                                .addGroup(pnlResumenGastoLayout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addGroup(pnlResumenGastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jtfValorProHogar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jtfValorProPersonal, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(lblResumenGasto))))
                    .addComponent(jLabel3)
                    .addGroup(pnlResumenGastoLayout.createSequentialGroup()
                        .addGroup(pnlResumenGastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblGastoTotalPromedio)
                            .addComponent(lblGastoTotal)
                            .addComponent(lblDiaMayorGasto)
                            .addComponent(lblCantidadPromedioProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDiaMenorGasto))
                        .addGap(79, 79, 79)
                        .addGroup(pnlResumenGastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtGastoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDiaMayorGasto, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDiaMenorGasto, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCantidadPromedioProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGastoPromedioDia, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblDistribucionGasto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 220, Short.MAX_VALUE)
                .addComponent(jScrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlResumenGastoLayout.setVerticalGroup(
            pnlResumenGastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlResumenGastoLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(lblResumenGasto)
                .addGap(30, 30, 30)
                .addComponent(jLabel3)
                .addGap(37, 37, 37)
                .addGroup(pnlResumenGastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlResumenGastoLayout.createSequentialGroup()
                        .addComponent(lblGastoTotal)
                        .addGap(18, 18, 18)
                        .addComponent(lblGastoTotalPromedio)
                        .addGap(18, 18, 18)
                        .addComponent(lblCantidadPromedioProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(lblDiaMayorGasto)
                        .addGap(18, 18, 18)
                        .addComponent(lblDiaMenorGasto))
                    .addGroup(pnlResumenGastoLayout.createSequentialGroup()
                        .addComponent(txtGastoPromedioDia, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtGastoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtCantidadPromedioProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtDiaMayorGasto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtDiaMenorGasto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlResumenGastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlResumenGastoLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jlvalorPromedioH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfValorProHogar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jlvalorPromedioP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtfValorProPersonal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlResumenGastoLayout.createSequentialGroup()
                        .addComponent(lblDistribucionGasto)
                        .addGap(18, 18, 18)
                        .addComponent(pnlGrafica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(btnVolver)
                .addGap(26, 26, 26))
            .addGroup(pnlResumenGastoLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 719, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(pnlResumenGasto, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 109, 940, 700));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        // TODO add your handling code here:
  if (controlador != null) {
        controlador.volver();
    }
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
        java.awt.EventQueue.invokeLater(() -> new P29_CriticoResumenGasto().setVisible(true));
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JLabel jlvalorPromedioH;
    private javax.swing.JLabel jlvalorPromedioP;
    private javax.swing.JTextField jtfValorProHogar;
    private javax.swing.JTextField jtfValorProPersonal;
    private javax.swing.JLabel lblAdministrador;
    private javax.swing.JLabel lblCantidadPromedioProductos;
    private javax.swing.JLabel lblDiaMayorGasto;
    private javax.swing.JLabel lblDiaMenorGasto;
    private javax.swing.JLabel lblDistribucionGasto;
    private javax.swing.JLabel lblGastoTotal;
    private javax.swing.JLabel lblGastoTotalPromedio;
    private javax.swing.JLabel lblResumenGasto;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel pnlGrafica;
    private javax.swing.JPanel pnlLogos;
    private javax.swing.JPanel pnlResumenGasto;
    private javax.swing.JTextField txtCantidadPromedioProductos;
    private javax.swing.JTextField txtDiaMayorGasto;
    private javax.swing.JTextField txtDiaMenorGasto;
    private javax.swing.JTextField txtGastoPromedioDia;
    private javax.swing.JTextField txtGastoTotal;
    // End of variables declaration//GEN-END:variables
}
