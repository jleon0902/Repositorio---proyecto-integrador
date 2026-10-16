/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author VALENTINA
 */
public class P24_pantalla_Criticodigi extends javax.swing.JFrame {
    private String nombreCriticoDigitador;
    private P23_pantalla_Criticodigi pantallaAnterior;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(P24_pantalla_Criticodigi.class.getName());

    /**
     * Creates new form P24_pantalla_Criticodigi
     */
    
    public P24_pantalla_Criticodigi() {
        initComponents();
        System.out.println("SE ABRIO LA PANTALLA P24");
        cargarGastoPromedioDia();
        cargarGastoTotal();
        cargarCantidadPromedioProductos();
        cargarDiaMayorGasto();
        cargarDiaMenorGasto();
        cargarGraficaGastos();
        
          // Evitar que el usuario modifique los datos mostrados
    txtGastoPromedioDia.setEditable(false);
    txtGastoTotal.setEditable(false);
    txtCantidadPromedioProductos.setEditable(false);
    txtDiaMayorGasto.setEditable(false);
    txtDiaMenorGasto.setEditable(false);
    jtfValorProHogar.setEditable(false);
    jtfValorProPersonal.setEditable(false);
 
    

    // Guardar el tamaño original del contenido
    java.awt.Dimension tamContenido = pnlResumenGasto.getPreferredSize();
    int anchoVentana = getWidth();

    // Calcular las posiciones de los componentes
    pnlResumenGasto.setSize(tamContenido);
    pnlResumenGasto.doLayout();
    pnlGrafica.doLayout();

    // Retirar la barra del panel blanco
    pnlResumenGasto.remove(scrResumenGasto);

    // Mantener las posiciones calculadas
    pnlResumenGasto.setLayout(null);
    pnlResumenGasto.setPreferredSize(tamContenido);

    // Crear el área de desplazamiento vertical
    javax.swing.JScrollPane desplazamiento =
            new javax.swing.JScrollPane(pnlResumenGasto);

    desplazamiento.setVerticalScrollBarPolicy(
            javax.swing.JScrollPane.VERTICAL_SCROLLBAR_NEVER
    );

    desplazamiento.setHorizontalScrollBarPolicy(
            javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
    );

    // Colocar el contenido y la barra vertical
    javax.swing.JPanel zona =
            new javax.swing.JPanel(new java.awt.BorderLayout());

    zona.add(desplazamiento, java.awt.BorderLayout.CENTER);
    zona.add(scrResumenGasto, java.awt.BorderLayout.EAST);

    // Conectar la barra existente con todo el contenido
    scrResumenGasto.setOrientation(
            javax.swing.JScrollBar.VERTICAL
    );

    scrResumenGasto.setModel(
            desplazamiento.getVerticalScrollBar().getModel()
    );

    // Dejar el encabezado fijo en la parte superior
    getContentPane().removeAll();
    getContentPane().setLayout(new java.awt.BorderLayout());

    getContentPane().add(pnlLogos, java.awt.BorderLayout.NORTH);
    getContentPane().add(zona, java.awt.BorderLayout.CENTER);

    // Tamaño de la ventana
    setSize(anchoVentana, 700);
    setLocationRelativeTo(null);

    revalidate();
    repaint();
        
    }
    
    public void setPantallaAnterior(P23_pantalla_Criticodigi pantalla) {
    this.pantallaAnterior = pantalla;
}
    
    public void setNombreCriticoDigitador(String nombre) {
        System.out.println(
        "P24 recibe el nombre: " + this.nombreCriticoDigitador
    );
    this.nombreCriticoDigitador = nombre;
    lblCriticoDigitador.setText("Crítico digitador: " + nombre);
}
    
private void cargarGastoPromedioDia() {

    String sql = "SELECT NVL(AVG(PROMEDIO_FAMILIA), 0) AS GASTO_PROMEDIO_DIA "
               + "FROM ( "
               + "    SELECT E.ID, SUM(G.VALOR) / 14 AS PROMEDIO_FAMILIA "
               + "    FROM ENCUESTA E "
               + "    INNER JOIN GASTOS_DIARIOS G ON E.ID = G.ID_ENCUESTA "
               + "    WHERE E.ESTADO = 'Finalizado' "
               + "    GROUP BY E.ID "
               + ")";

    try (Connection con = modelo.conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            double promedio = rs.getDouble("GASTO_PROMEDIO_DIA");

            txtGastoPromedioDia.setText(
                String.format(java.util.Locale.US, "%,.2f", promedio)
            );
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
            this,
            "Error al calcular el gasto promedio diario: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}


private void cargarGastoTotal() {

    String sql = "SELECT NVL(AVG(GASTO_FAMILIA), 0) AS GASTO_TOTAL_PROMEDIO "
               + "FROM ( "
               + "    SELECT E.ID, SUM(G.VALOR) AS GASTO_FAMILIA "
               + "    FROM ENCUESTA E "
               + "    INNER JOIN GASTOS_DIARIOS G "
               + "        ON E.ID = G.ID_ENCUESTA "
               + "    WHERE E.ESTADO = 'Finalizado' "
               + "    GROUP BY E.ID "
               + ")";

    try (Connection con = modelo.conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            double gastoTotal = rs.getDouble("GASTO_TOTAL_PROMEDIO");

            txtGastoTotal.setText(
                String.format(java.util.Locale.US, "%,.0f", gastoTotal)
            );
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
            this,
            "Error al calcular el gasto total promedio: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}


private void cargarCantidadPromedioProductos() {

    String sql = "SELECT NVL(AVG(CANTIDAD_PRODUCTOS), 0) AS PROMEDIO_PRODUCTOS "
               + "FROM ( "
               + "    SELECT E.ID, COUNT(G.ID) AS CANTIDAD_PRODUCTOS "
               + "    FROM ENCUESTA E "
               + "    INNER JOIN GASTOS_DIARIOS G "
               + "        ON E.ID = G.ID_ENCUESTA "
               + "    WHERE E.ESTADO = 'Finalizado' "
               + "    GROUP BY E.ID "
               + ")";

    try (Connection con = modelo.conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            double promedio = rs.getDouble("PROMEDIO_PRODUCTOS");

            txtCantidadPromedioProductos.setText(
                String.format(java.util.Locale.US, "%.2f", promedio)
            );
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
            this,
            "Error al calcular la cantidad promedio de productos: "
                + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}


private void cargarDiaMayorGasto() {

    String sql = "SELECT 'Día ' || DIA || ' de 14' AS DIA_MAYOR_GASTO "
               + "FROM ( "
               + "    SELECT DIA, COUNT(*) AS REPETICIONES "
               + "    FROM ( "
               + "        SELECT ID_ENCUESTA, DIA, GASTO_DIA, "
               + "               ROW_NUMBER() OVER ( "
               + "                   PARTITION BY ID_ENCUESTA "
               + "                   ORDER BY GASTO_DIA DESC, DIA ASC "
               + "               ) AS POSICION "
               + "        FROM ( "
               + "            SELECT E.ID AS ID_ENCUESTA, "
               + "                   TRUNC(G.FECHA) - TRUNC(E.FECHA_INICIO) + 1 AS DIA, "
               + "                   SUM(G.VALOR) AS GASTO_DIA "
               + "            FROM ENCUESTA E "
               + "            INNER JOIN GASTOS_DIARIOS G "
               + "                ON E.ID = G.ID_ENCUESTA "
               + "            WHERE E.ESTADO = 'Finalizado' "
               + "              AND TRUNC(G.FECHA) >= TRUNC(E.FECHA_INICIO) "
               + "              AND TRUNC(G.FECHA) < TRUNC(E.FECHA_INICIO) + 14 "
               + "            GROUP BY E.ID, E.FECHA_INICIO, TRUNC(G.FECHA) "
               + "        ) "
               + "    ) "
               + "    WHERE POSICION = 1 "
               + "    GROUP BY DIA "
               + "    ORDER BY REPETICIONES DESC, DIA ASC "
               + ") "
               + "WHERE ROWNUM = 1";

    try (Connection con = modelo.conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            txtDiaMayorGasto.setText(rs.getString("DIA_MAYOR_GASTO"));
        } else {
            txtDiaMayorGasto.setText("Sin datos");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
            this,
            "Error al calcular el día de mayor gasto: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}


private void cargarDiaMenorGasto() {

    String sql = "SELECT 'Día ' || DIA || ' de 14' AS DIA_MENOR_GASTO "
               + "FROM ( "
               + "    SELECT DIA, COUNT(*) AS REPETICIONES "
               + "    FROM ( "
               + "        SELECT ID_ENCUESTA, DIA, GASTO_DIA, "
               + "               ROW_NUMBER() OVER ( "
               + "                   PARTITION BY ID_ENCUESTA "
               + "                   ORDER BY GASTO_DIA ASC, DIA ASC "
               + "               ) AS POSICION "
               + "        FROM ( "
               + "            SELECT E.ID AS ID_ENCUESTA, "
               + "                   TRUNC(G.FECHA) - TRUNC(E.FECHA_INICIO) + 1 AS DIA, "
               + "                   SUM(G.VALOR) AS GASTO_DIA "
               + "            FROM ENCUESTA E "
               + "            INNER JOIN GASTOS_DIARIOS G "
               + "                ON E.ID = G.ID_ENCUESTA "
               + "            WHERE E.ESTADO = 'Finalizado' "
               + "              AND TRUNC(G.FECHA) >= TRUNC(E.FECHA_INICIO) "
               + "              AND TRUNC(G.FECHA) < TRUNC(E.FECHA_INICIO) + 14 "
               + "            GROUP BY E.ID, E.FECHA_INICIO, TRUNC(G.FECHA) "
               + "        ) "
               + "    ) "
               + "    WHERE POSICION = 1 "
               + "    GROUP BY DIA "
               + "    ORDER BY REPETICIONES DESC, DIA ASC "
               + ") "
               + "WHERE ROWNUM = 1";

    try (Connection con = modelo.conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            txtDiaMenorGasto.setText(rs.getString("DIA_MENOR_GASTO"));
        } else {
            txtDiaMenorGasto.setText("Sin datos");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
            this,
            "Error al calcular el día de menor gasto: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}


private void cargarGraficaGastos() {

    String sql =
        "SELECT " +
        "    NVL(SUM(TOTAL_HOGAR), 0) AS TOTAL_HOGAR, " +
        "    NVL(SUM(TOTAL_PERSONAL), 0) AS TOTAL_PERSONAL, " +
        "    NVL(AVG(TOTAL_HOGAR), 0) AS PROMEDIO_HOGAR, " +
        "    NVL(AVG(TOTAL_PERSONAL), 0) AS PROMEDIO_PERSONAL " +
        "FROM ( " +
        "    SELECT " +
        "        E.ID AS ID_ENCUESTA, " +
        "        SUM(CASE WHEN G.TIPO_GASTO = 'Hogar' " +
        "                 THEN G.VALOR ELSE 0 END) AS TOTAL_HOGAR, " +
        "        SUM(CASE WHEN G.TIPO_GASTO = 'Personal' " +
        "                 THEN G.VALOR ELSE 0 END) AS TOTAL_PERSONAL " +
        "    FROM ENCUESTA E " +
        "    INNER JOIN GASTOS_DIARIOS G " +
        "        ON E.ID = G.ID_ENCUESTA " +
        "    WHERE E.ESTADO = 'Finalizado' " +
        "    GROUP BY E.ID " +
        ")";

    try (Connection con = modelo.conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {

            double totalHogar = rs.getDouble("TOTAL_HOGAR");
            double totalPersonal = rs.getDouble("TOTAL_PERSONAL");

            double promedioHogar = rs.getDouble("PROMEDIO_HOGAR");
            double promedioPersonal = rs.getDouble("PROMEDIO_PERSONAL");

            // Mostrar el promedio de dinero gastado por familia
            jtfValorProHogar.setText(
                String.format(
                    java.util.Locale.US,
                    "%,.2f",
                    promedioHogar
                )
            );

            jtfValorProPersonal.setText(
                String.format(
                    java.util.Locale.US,
                    "%,.2f",
                    promedioPersonal
                )
            );

            // Calcular porcentajes sobre el gasto total registrado
            double gastoTotal = totalHogar + totalPersonal;

            double porcentajeHogar = 0;
            double porcentajePersonal = 0;

            if (gastoTotal > 0) {
                porcentajeHogar = totalHogar * 100.0 / gastoTotal;
                porcentajePersonal = totalPersonal * 100.0 / gastoTotal;
            }

            // Crear la gráfica circular
            org.jfree.data.general.DefaultPieDataset datos =
                new org.jfree.data.general.DefaultPieDataset();

            if (gastoTotal > 0) {
                datos.setValue("Hogar", totalHogar);
                datos.setValue("Personal", totalPersonal);
            }

            org.jfree.chart.JFreeChart grafica =
                org.jfree.chart.ChartFactory.createPieChart(
                    "Distribución del gasto total: Hogar y Personal",
                    datos,
                    true,
                    true,
                    false
                );

            org.jfree.chart.plot.PiePlot plot =
                (org.jfree.chart.plot.PiePlot) grafica.getPlot();

            plot.setSectionPaint(
                "Hogar",
                new java.awt.Color(52, 152, 219)
            );

            plot.setSectionPaint(
                "Personal",
                new java.awt.Color(243, 156, 18)
            );

            
            plot.setSimpleLabels(true);

            plot.setLabelGenerator(
                new org.jfree.chart.labels.StandardPieSectionLabelGenerator(
                    "{2}",
                    new java.text.DecimalFormat("#,##0.00"),
                    new java.text.DecimalFormat("0.00%")
                )
            );

            plot.setLabelFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
            plot.setLabelBackgroundPaint(java.awt.Color.WHITE);
            plot.setLabelOutlinePaint(null);
            plot.setLabelShadowPaint(null);


            // Mostrar la gráfica en pnlGrafica
            pnlGrafica.removeAll();
            pnlGrafica.setLayout(new java.awt.BorderLayout());

            
            org.jfree.chart.ChartPanel panelGrafica =
                    new org.jfree.chart.ChartPanel(grafica);

            panelGrafica.setMouseWheelEnabled(false);

            panelGrafica.setPreferredSize(
                    new java.awt.Dimension(260, 220)
            );
            panelGrafica.setMaximumDrawWidth(260);
            panelGrafica.setMaximumDrawHeight(220);
            panelGrafica.setMinimumDrawWidth(220);
            panelGrafica.setMinimumDrawHeight(180);

            pnlGrafica.removeAll();
            pnlGrafica.setLayout(new java.awt.GridBagLayout());

            pnlGrafica.add(panelGrafica);

            pnlGrafica.revalidate();
            pnlGrafica.repaint();


        }

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
            this,
            "Error al cargar la gráfica de gastos: "
                + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
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

        pnlLogos = new javax.swing.JPanel();
        lblDane = new javax.swing.JLabel();
        lblEnph = new javax.swing.JLabel();
        lblCriticoDigitador = new javax.swing.JLabel();
        pnlResumenGasto = new javax.swing.JPanel();
        lblResumenGasto = new javax.swing.JLabel();
        lblGastoTotal = new javax.swing.JLabel();
        lblGastoTotalPromedio = new javax.swing.JLabel();
        lblCantidadPromedioProductos = new javax.swing.JLabel();
        lblDiaMayorGasto = new javax.swing.JLabel();
        lblDiaMenorGasto = new javax.swing.JLabel();
        lblDistribucionGasto = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JButton();
        pnlGrafica = new javax.swing.JPanel();
        scrResumenGasto = new javax.swing.JScrollBar();
        txtCantidadPromedioProductos = new javax.swing.JTextField();
        txtGastoPromedioDia = new javax.swing.JTextField();
        txtGastoTotal = new javax.swing.JTextField();
        txtDiaMayorGasto = new javax.swing.JTextField();
        txtDiaMenorGasto = new javax.swing.JTextField();
        jlvalorPromedioH = new javax.swing.JLabel();
        jlvalorPromedioP = new javax.swing.JLabel();
        jtfValorProHogar = new javax.swing.JTextField();
        jtfValorProPersonal = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                .addGap(32, 32, 32))
        );

        pnlResumenGasto.setBackground(new java.awt.Color(255, 255, 255));
        pnlResumenGasto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));

        lblResumenGasto.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
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

        btnAceptar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(this::btnAceptarActionPerformed);

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

        javax.swing.GroupLayout pnlResumenGastoLayout = new javax.swing.GroupLayout(pnlResumenGasto);
        pnlResumenGasto.setLayout(pnlResumenGastoLayout);
        pnlResumenGastoLayout.setHorizontalGroup(
            pnlResumenGastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlResumenGastoLayout.createSequentialGroup()
                .addGroup(pnlResumenGastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlResumenGastoLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(pnlResumenGastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlResumenGastoLayout.createSequentialGroup()
                                .addGroup(pnlResumenGastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(pnlResumenGastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblGastoTotalPromedio)
                                        .addComponent(lblGastoTotal)
                                        .addComponent(lblDiaMayorGasto)
                                        .addComponent(lblDistribucionGasto)
                                        .addComponent(lblCantidadPromedioProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlResumenGastoLayout.createSequentialGroup()
                                        .addComponent(lblDiaMenorGasto)
                                        .addGap(20, 20, 20)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnlResumenGastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlResumenGastoLayout.createSequentialGroup()
                                        .addGroup(pnlResumenGastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtGastoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtDiaMayorGasto, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtDiaMenorGasto, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtCantidadPromedioProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(30, 30, 30))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlResumenGastoLayout.createSequentialGroup()
                                        .addComponent(txtGastoPromedioDia, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(137, 137, 137))))
                            .addGroup(pnlResumenGastoLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(pnlResumenGastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnAceptar)
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
                                                    .addComponent(jtfValorProPersonal, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlResumenGastoLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblResumenGasto)
                        .addGap(229, 229, 229)))
                .addComponent(scrResumenGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlResumenGastoLayout.setVerticalGroup(
            pnlResumenGastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlResumenGastoLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblResumenGasto)
                .addGap(31, 31, 31)
                .addGroup(pnlResumenGastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGastoTotal)
                    .addComponent(txtGastoPromedioDia, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlResumenGastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGastoTotalPromedio)
                    .addComponent(txtGastoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlResumenGastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCantidadPromedioProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCantidadPromedioProductos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlResumenGastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtDiaMayorGasto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDiaMayorGasto))
                .addGap(18, 18, 18)
                .addGroup(pnlResumenGastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDiaMenorGasto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDiaMenorGasto))
                .addGroup(pnlResumenGastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlResumenGastoLayout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addComponent(jlvalorPromedioH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfValorProHogar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jlvalorPromedioP)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtfValorProPersonal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlResumenGastoLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(pnlResumenGastoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnAceptar)
                            .addGroup(pnlResumenGastoLayout.createSequentialGroup()
                                .addComponent(lblDistribucionGasto)
                                .addGap(18, 18, 18)
                                .addComponent(pnlGrafica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(25, 25, Short.MAX_VALUE))
            .addGroup(pnlResumenGastoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrResumenGasto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlLogos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlResumenGasto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlLogos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlResumenGasto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        
    if (pantallaAnterior != null) {
        pantallaAnterior.setVisible(true);
        this.dispose();
    } else {
        JOptionPane.showMessageDialog(
            this,
            "No se encontró la pantalla anterior."
        );
    }

    }//GEN-LAST:event_btnAceptarActionPerformed

   
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
        java.awt.EventQueue.invokeLater(() -> new P24_pantalla_Criticodigi().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JLabel jlvalorPromedioH;
    private javax.swing.JLabel jlvalorPromedioP;
    private javax.swing.JTextField jtfValorProHogar;
    private javax.swing.JTextField jtfValorProPersonal;
    private javax.swing.JLabel lblCantidadPromedioProductos;
    private javax.swing.JLabel lblCriticoDigitador;
    private javax.swing.JLabel lblDane;
    private javax.swing.JLabel lblDiaMayorGasto;
    private javax.swing.JLabel lblDiaMenorGasto;
    private javax.swing.JLabel lblDistribucionGasto;
    private javax.swing.JLabel lblEnph;
    private javax.swing.JLabel lblGastoTotal;
    private javax.swing.JLabel lblGastoTotalPromedio;
    private javax.swing.JLabel lblResumenGasto;
    private javax.swing.JPanel pnlGrafica;
    private javax.swing.JPanel pnlLogos;
    private javax.swing.JPanel pnlResumenGasto;
    private javax.swing.JScrollBar scrResumenGasto;
    private javax.swing.JTextField txtCantidadPromedioProductos;
    private javax.swing.JTextField txtDiaMayorGasto;
    private javax.swing.JTextField txtDiaMenorGasto;
    private javax.swing.JTextField txtGastoPromedioDia;
    private javax.swing.JTextField txtGastoTotal;
    // End of variables declaration//GEN-END:variables
}
