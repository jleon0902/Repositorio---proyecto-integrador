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
public class P14_pantalla_Encuestador extends javax.swing.JFrame {
    private String cedulaRepresentante;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(P14_pantalla_Encuestador.class.getName());

    /**
     * Creates new form P14_pantalla_Encuestador
     */
    public P14_pantalla_Encuestador() {
    initComponents();

    // Datos del hogar
    txtNumeroVivienda4.setEditable(false);
    txtUbicacion4.setEditable(false);
    txtDireccion4.setEditable(false);
    txtBarrio4.setEditable(false);
    txtEstrato4.setEditable(false);

    // Datos del representante
    txtRepresentanteHogar4.setEditable(false);
    txtCedula4.setEditable(false);
    txtTelefono4.setEditable(false);
    txtCorreo4.setEditable(false);

    // Datos de la encuesta
    txtEstado4.setEditable(false);
    txtFechaInicio4.setEditable(false);
    txtFechaFinalizacion4.setEditable(false);

    // Datos del portátil
    txtNombre4.setEditable(false);

    // Otros campos que aparecen en el formulario
    textDia.setEditable(false);
    jTextField6.setEditable(false);
    jTextField9.setEditable(false);
    jlbInfo.setVisible(false);
}
    
    public void setCedulaRepresentante(String cedula) {
    this.cedulaRepresentante = cedula;
    cargarDatosFamilia();
}
    
    public void setNombreEncuestador(String nombre) {
    lblUsuario.setText("Encuestador: " + nombre);
}
    
    
private void cargarDatosFamilia() {

    
String sql =
    "SELECT H.NUMERO_VIVIENDA, " +
    "       REG.NOMBRE AS REGION, " +
    "       DEP.NOMBRE AS DEPARTAMENTO, " +
    "       MUN.NOMBRE AS MUNICIPIO, " +
    "       H.DIRECCION, " +
    "       H.BARRIO, " +
    "       H.ESTRATO, " +
    "       REP.NOMBRE AS NOMBRE_REPRESENTANTE, " +
    "       REP.CEDULA, " +
    "       REP.TELEFONO, " +
    "       REP.CORREO, " +
    "       ENC.ESTADO AS ESTADO_ENCUESTA, " +
    "       ENC.FECHA_INICIO, " +
    "       ENC.FECHA_FIN, " +
    "       POR.NUMERO_SERIE " +
    "FROM HOGAR H " +
    "INNER JOIN REPRESENTANTE REP " +
    "    ON H.CED_REPRESENTANTE = REP.CEDULA " +
    "INNER JOIN MUNICIPIOS MUN " +
    "    ON H.ID_MUNICIPIOS = MUN.ID " +
    "INNER JOIN DEPARTAMENTO DEP " +
    "    ON MUN.ID_DEPARTAMENTO = DEP.ID " +
    "INNER JOIN REGION REG " +
    "    ON DEP.ID_REGION = REG.ID " +
    "LEFT JOIN ENCUESTA ENC " +
    "    ON REP.CEDULA = ENC.CEDULA_REPRESENTANTE " +
    "LEFT JOIN PORTATIL POR " +
    "    ON ENC.ID_PORTATIL = POR.ID " +
    "WHERE REP.CEDULA = ?";


    try (Connection cn = modelo.conexion.conectar();
         PreparedStatement ps = cn.prepareStatement(sql)) {

        ps.setString(1, cedulaRepresentante);

        try (ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {

                txtNumeroVivienda4.setText(
                    rs.getString("NUMERO_VIVIENDA")
                );

                txtUbicacion4.setText(
                    rs.getString("REGION") + " / " +
                    rs.getString("DEPARTAMENTO") + " / " +
                    rs.getString("MUNICIPIO")
                );

                txtDireccion4.setText(
                    rs.getString("DIRECCION")
                );

                txtBarrio4.setText(
                    rs.getString("BARRIO")
                );

                txtEstrato4.setText(
                    rs.getString("ESTRATO")
                );

                txtRepresentanteHogar4.setText(
                    rs.getString("NOMBRE_REPRESENTANTE")
                );

                txtCedula4.setText(
                    rs.getString("CEDULA")
                );

                txtTelefono4.setText(
                    rs.getString("TELEFONO")
                );

                txtCorreo4.setText(
                    rs.getString("CORREO")
                );
                
                
java.sql.Date fechaInicio = rs.getDate("FECHA_INICIO");
java.sql.Date fechaFin = rs.getDate("FECHA_FIN");

// Mostrar los datos solamente si existe una encuesta.
if (fechaInicio != null) {

    jlbInfo.setVisible(false);
    // Estado de la encuesta
    txtEstado4.setText(
        rs.getString("ESTADO_ENCUESTA")
    );

    // Fecha de inicio
    txtFechaInicio4.setText(
        fechaInicio.toLocalDate().toString()
    );

    // Fecha de finalización
    if (fechaFin != null) {
        txtFechaFinalizacion4.setText(
            fechaFin.toLocalDate().toString()
        );
    } else {
        txtFechaFinalizacion4.setText("");
    }

    // Calcular los días contando el inicio como día 1.
    long diasTranscurridos =
        java.time.temporal.ChronoUnit.DAYS.between(
            fechaInicio.toLocalDate(),
            java.time.LocalDate.now()
        ) + 1;

    // Si la fecha de inicio es hoy, mostrar 1.
    diasTranscurridos = Math.max(1, diasTranscurridos);

    textDia.setText(
        String.valueOf(diasTranscurridos)
    );

    // Número de serie del portátil asignado
    String numeroSerie = rs.getString("NUMERO_SERIE");

    txtNombre4.setText(
        numeroSerie == null ? "" : numeroSerie
    );

} else {

    // Si no existe encuesta, dejar los campos vacíos.
    txtEstado4.setText("");
    txtFechaInicio4.setText("");
    txtFechaFinalizacion4.setText("");
    textDia.setText("");
    txtNombre4.setText("");
    
    jlbInfo.setVisible(true);
    
}


            } else {

                JOptionPane.showMessageDialog(
                    this,
                    "No se encontró información del hogar " +
                    "para el representante seleccionado."
                );
            }

        }

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
            this,
            "Error al consultar los datos de la familia: " +
            e.getMessage(),
            "Error de base de datos",
            JOptionPane.ERROR_MESSAGE
        );

        e.printStackTrace();
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

        jLabel16 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        pnlVerFamilia = new javax.swing.JPanel();
        pnlDatosFamilias4 = new javax.swing.JPanel();
        lblNumeroVivienda4 = new javax.swing.JLabel();
        btnVolver4 = new javax.swing.JButton();
        lblVerFamilia4 = new javax.swing.JLabel();
        lblEncuesta4 = new javax.swing.JLabel();
        lblEstrato4 = new javax.swing.JLabel();
        lblEstado4 = new javax.swing.JLabel();
        lblDiasSeguimiento4 = new javax.swing.JLabel();
        lblFechaFinalizacion4 = new javax.swing.JLabel();
        lblFechaInicio4 = new javax.swing.JLabel();
        lblCedula4 = new javax.swing.JLabel();
        lblRepresentanteDelHogar4 = new javax.swing.JLabel();
        lblTelefono4 = new javax.swing.JLabel();
        lblCorreo4 = new javax.swing.JLabel();
        lblPortatil4 = new javax.swing.JLabel();
        lblNombre4 = new javax.swing.JLabel();
        txtNumeroVivienda4 = new javax.swing.JTextField();
        txtUbicacion4 = new javax.swing.JTextField();
        txtDireccion4 = new javax.swing.JTextField();
        txtBarrio4 = new javax.swing.JTextField();
        txtEstrato4 = new javax.swing.JTextField();
        txtEstado4 = new javax.swing.JTextField();
        txtFechaFinalizacion4 = new javax.swing.JTextField();
        txtFechaInicio4 = new javax.swing.JTextField();
        txtRepresentanteHogar4 = new javax.swing.JTextField();
        txtCedula4 = new javax.swing.JTextField();
        txtTelefono4 = new javax.swing.JTextField();
        txtCorreo4 = new javax.swing.JTextField();
        txtNombre4 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Ibldia_de14 = new javax.swing.JLabel();
        textDia = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jlbInfo = new javax.swing.JLabel();
        pnlLogos = new javax.swing.JPanel();
        sepFranjas = new javax.swing.JSeparator();
        lblDane = new javax.swing.JLabel();
        lblEnph = new javax.swing.JLabel();
        pnlLogo = new javax.swing.JPanel();
        sepLogos = new javax.swing.JSeparator();
        lblUsuario = new javax.swing.JLabel();
        sepFranja = new javax.swing.JSeparator();

        jLabel16.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel16.setText("Estrato:");

        jTextField6.addActionListener(this::jTextField6ActionPerformed);

        jTextField9.addActionListener(this::jTextField9ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlVerFamilia.setBackground(new java.awt.Color(255, 255, 255));

        pnlDatosFamilias4.setBackground(new java.awt.Color(255, 255, 255));
        pnlDatosFamilias4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));

        lblNumeroVivienda4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblNumeroVivienda4.setText("Numero vivienda:");

        btnVolver4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnVolver4.setText("Volver");
        btnVolver4.addActionListener(this::btnVolver4ActionPerformed);

        lblVerFamilia4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblVerFamilia4.setText("Ver Familia");

        lblEncuesta4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblEncuesta4.setText("Encuesta:");

        lblEstrato4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblEstrato4.setText("Estrato:");

        lblEstado4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblEstado4.setText("Estado:");

        lblDiasSeguimiento4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblDiasSeguimiento4.setText("Día ");

        lblFechaFinalizacion4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblFechaFinalizacion4.setText("Fecha finalización:");

        lblFechaInicio4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblFechaInicio4.setText("Fecha de inicio:");

        lblCedula4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblCedula4.setText("Cédula:");

        lblRepresentanteDelHogar4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblRepresentanteDelHogar4.setText("Representante del hogar:");

        lblTelefono4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblTelefono4.setText("Télefono:");

        lblCorreo4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblCorreo4.setText("Correo:");

        lblPortatil4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblPortatil4.setText("Portátil asignado");

        lblNombre4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblNombre4.setText("Numero Serie:");

        txtNumeroVivienda4.addActionListener(this::txtNumeroViviendaActionPerformed);

        txtUbicacion4.addActionListener(this::txtUbicacionActionPerformed);

        txtDireccion4.addActionListener(this::txtDireccionActionPerformed);

        txtBarrio4.addActionListener(this::txtBarrioActionPerformed);

        txtEstrato4.addActionListener(this::txtEstratoActionPerformed);

        txtEstado4.addActionListener(this::txtEstadoActionPerformed);

        txtFechaFinalizacion4.addActionListener(this::txtFechaFinalizacionActionPerformed);

        txtFechaInicio4.addActionListener(this::txtFechaInicioActionPerformed);

        txtRepresentanteHogar4.addActionListener(this::txtRepresentanteHogarActionPerformed);

        txtCedula4.addActionListener(this::txtCedulaActionPerformed);

        txtTelefono4.addActionListener(this::txtTelefonoActionPerformed);

        txtCorreo4.addActionListener(this::txtCorreoActionPerformed);

        txtNombre4.addActionListener(this::txtNombreActionPerformed);

        jLabel15.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel15.setText("Dirección");

        jLabel17.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel17.setText("Barrio");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel1.setText("Ubicación");

        Ibldia_de14.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        Ibldia_de14.setText("de 14");

        textDia.addActionListener(this::textDiaActionPerformed);

        jlbInfo.setText("Los campos de la encuesta y del portátil están vacíos porque la familia seleccionada aún no ha iniciado la encuesta.");

        javax.swing.GroupLayout pnlDatosFamilias4Layout = new javax.swing.GroupLayout(pnlDatosFamilias4);
        pnlDatosFamilias4.setLayout(pnlDatosFamilias4Layout);
        pnlDatosFamilias4Layout.setHorizontalGroup(
            pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDatosFamilias4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(381, 381, 381)
                .addComponent(btnVolver4)
                .addGap(50, 50, 50))
            .addGroup(pnlDatosFamilias4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosFamilias4Layout.createSequentialGroup()
                        .addComponent(jlbInfo)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlDatosFamilias4Layout.createSequentialGroup()
                        .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlDatosFamilias4Layout.createSequentialGroup()
                                .addComponent(lblNumeroVivienda4)
                                .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlDatosFamilias4Layout.createSequentialGroup()
                                        .addGap(448, 448, 448)
                                        .addComponent(txtCorreo4))
                                    .addGroup(pnlDatosFamilias4Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtNumeroVivienda4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                                            .addComponent(txtUbicacion4, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtDireccion4, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtBarrio4))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(pnlDatosFamilias4Layout.createSequentialGroup()
                                .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlDatosFamilias4Layout.createSequentialGroup()
                                        .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblEncuesta4, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblEstado4)
                                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel15)
                                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblEstrato4, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(pnlDatosFamilias4Layout.createSequentialGroup()
                                                .addComponent(lblDiasSeguimiento4)
                                                .addGap(18, 18, 18)
                                                .addComponent(textDia, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(pnlDatosFamilias4Layout.createSequentialGroup()
                                                .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(pnlDatosFamilias4Layout.createSequentialGroup()
                                                        .addGap(18, 18, 18)
                                                        .addComponent(Ibldia_de14, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(pnlDatosFamilias4Layout.createSequentialGroup()
                                                        .addGap(55, 55, 55)
                                                        .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(txtEstrato4, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                                                            .addComponent(txtEstado4))))
                                                .addGap(51, 51, 51)
                                                .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDatosFamilias4Layout.createSequentialGroup()
                                                        .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(lblTelefono4)
                                                            .addComponent(lblCedula4)
                                                            .addComponent(lblCorreo4)
                                                            .addComponent(lblRepresentanteDelHogar4))
                                                        .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(pnlDatosFamilias4Layout.createSequentialGroup()
                                                                .addGap(18, 18, 18)
                                                                .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                    .addComponent(txtCedula4, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                                                                    .addComponent(txtRepresentanteHogar4)))
                                                            .addGroup(pnlDatosFamilias4Layout.createSequentialGroup()
                                                                .addGap(18, 18, 18)
                                                                .addComponent(txtTelefono4, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDatosFamilias4Layout.createSequentialGroup()
                                                        .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(lblPortatil4)
                                                            .addComponent(lblNombre4))
                                                        .addGap(93, 93, 93)
                                                        .addComponent(txtNombre4, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDatosFamilias4Layout.createSequentialGroup()
                                                .addGap(266, 266, 266)
                                                .addComponent(lblVerFamilia4)
                                                .addGap(262, 262, 262))))
                                    .addGroup(pnlDatosFamilias4Layout.createSequentialGroup()
                                        .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblFechaFinalizacion4)
                                            .addComponent(lblFechaInicio4))
                                        .addGap(18, 18, 18)
                                        .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtFechaFinalizacion4, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                                            .addComponent(txtFechaInicio4))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(104, 104, 104))))
        );
        pnlDatosFamilias4Layout.setVerticalGroup(
            pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosFamilias4Layout.createSequentialGroup()
                .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosFamilias4Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNumeroVivienda4)
                            .addComponent(txtNumeroVivienda4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDatosFamilias4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblVerFamilia4)
                        .addGap(13, 13, 13)
                        .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblRepresentanteDelHogar4)
                            .addComponent(txtRepresentanteHogar4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosFamilias4Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUbicacion4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDireccion4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBarrio4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEstrato4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEstrato4)))
                    .addGroup(pnlDatosFamilias4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCedula4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCedula4))
                        .addGap(18, 18, 18)
                        .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTelefono4)
                            .addComponent(txtTelefono4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCorreo4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCorreo4))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(jlbInfo)
                .addGap(18, 18, 18)
                .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDatosFamilias4Layout.createSequentialGroup()
                        .addComponent(lblEncuesta4)
                        .addGap(18, 18, 18)
                        .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEstado4)
                            .addComponent(txtEstado4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDiasSeguimiento4)
                            .addComponent(Ibldia_de14)
                            .addComponent(textDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblFechaInicio4)
                            .addComponent(txtFechaInicio4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblFechaFinalizacion4)
                            .addComponent(txtFechaFinalizacion4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDatosFamilias4Layout.createSequentialGroup()
                        .addComponent(lblPortatil4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombre4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNombre4))
                        .addGap(104, 104, 104)
                        .addGroup(pnlDatosFamilias4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnVolver4, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))))
                .addGap(44, 44, 44))
        );

        javax.swing.GroupLayout pnlVerFamiliaLayout = new javax.swing.GroupLayout(pnlVerFamilia);
        pnlVerFamilia.setLayout(pnlVerFamiliaLayout);
        pnlVerFamiliaLayout.setHorizontalGroup(
            pnlVerFamiliaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlVerFamiliaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlDatosFamilias4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlVerFamiliaLayout.setVerticalGroup(
            pnlVerFamiliaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlDatosFamilias4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlLogos.setBackground(new java.awt.Color(255, 235, 229));
        pnlLogos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        sepFranjas.setForeground(new java.awt.Color(0, 0, 0));

        lblDane.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/logo1.png"))); // NOI18N

        lblEnph.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/logo2.png"))); // NOI18N

        pnlLogo.setBackground(new java.awt.Color(255, 255, 255));

        sepLogos.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout pnlLogoLayout = new javax.swing.GroupLayout(pnlLogo);
        pnlLogo.setLayout(pnlLogoLayout);
        pnlLogoLayout.setHorizontalGroup(
            pnlLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sepLogos, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
        );
        pnlLogoLayout.setVerticalGroup(
            pnlLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLogoLayout.createSequentialGroup()
                .addComponent(sepLogos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 293, Short.MAX_VALUE))
        );

        lblUsuario.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        lblUsuario.setText("lblUsuario");

        javax.swing.GroupLayout pnlLogosLayout = new javax.swing.GroupLayout(pnlLogos);
        pnlLogos.setLayout(pnlLogosLayout);
        pnlLogosLayout.setHorizontalGroup(
            pnlLogosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLogosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sepFranja, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlLogosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlLogosLayout.createSequentialGroup()
                        .addComponent(lblDane)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEnph)
                        .addGap(71, 71, 71)
                        .addComponent(lblUsuario)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlLogosLayout.createSequentialGroup()
                        .addComponent(pnlLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sepFranjas)))
                .addContainerGap())
        );
        pnlLogosLayout.setVerticalGroup(
            pnlLogosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLogosLayout.createSequentialGroup()
                .addGroup(pnlLogosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlLogosLayout.createSequentialGroup()
                        .addGroup(pnlLogosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblDane)
                            .addComponent(lblEnph))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlLogosLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(lblUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(pnlLogosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlLogosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(sepFranja, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sepFranjas, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlLogos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlVerFamilia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlLogos, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlVerFamilia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9ActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoActionPerformed

    private void txtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoActionPerformed

    private void txtCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCedulaActionPerformed

    private void txtRepresentanteHogarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRepresentanteHogarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRepresentanteHogarActionPerformed

    private void txtFechaInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaInicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaInicioActionPerformed

    private void txtFechaFinalizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaFinalizacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaFinalizacionActionPerformed

    private void txtEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEstadoActionPerformed

    private void txtEstratoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstratoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEstratoActionPerformed

    private void txtBarrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBarrioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBarrioActionPerformed

    private void txtDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDireccionActionPerformed

    private void txtUbicacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUbicacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUbicacionActionPerformed

    private void txtNumeroViviendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroViviendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroViviendaActionPerformed

    private void btnVolver4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolver4ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnVolver4ActionPerformed

    private void textDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textDiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textDiaActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new P14_pantalla_Encuestador().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Ibldia_de14;
    private javax.swing.JButton btnVolver4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JLabel jlbInfo;
    private javax.swing.JLabel lblCedula4;
    private javax.swing.JLabel lblCorreo4;
    private javax.swing.JLabel lblDane;
    private javax.swing.JLabel lblDiasSeguimiento4;
    private javax.swing.JLabel lblEncuesta4;
    private javax.swing.JLabel lblEnph;
    private javax.swing.JLabel lblEstado4;
    private javax.swing.JLabel lblEstrato4;
    private javax.swing.JLabel lblFechaFinalizacion4;
    private javax.swing.JLabel lblFechaInicio4;
    private javax.swing.JLabel lblNombre4;
    private javax.swing.JLabel lblNumeroVivienda4;
    private javax.swing.JLabel lblPortatil4;
    private javax.swing.JLabel lblRepresentanteDelHogar4;
    private javax.swing.JLabel lblTelefono4;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lblVerFamilia4;
    private javax.swing.JPanel pnlDatosFamilias4;
    private javax.swing.JPanel pnlLogo;
    private javax.swing.JPanel pnlLogos;
    private javax.swing.JPanel pnlVerFamilia;
    private javax.swing.JSeparator sepFranja;
    private javax.swing.JSeparator sepFranjas;
    private javax.swing.JSeparator sepLogos;
    private javax.swing.JTextField textDia;
    private javax.swing.JTextField txtBarrio4;
    private javax.swing.JTextField txtCedula4;
    private javax.swing.JTextField txtCorreo4;
    private javax.swing.JTextField txtDireccion4;
    private javax.swing.JTextField txtEstado4;
    private javax.swing.JTextField txtEstrato4;
    private javax.swing.JTextField txtFechaFinalizacion4;
    private javax.swing.JTextField txtFechaInicio4;
    private javax.swing.JTextField txtNombre4;
    private javax.swing.JTextField txtNumeroVivienda4;
    private javax.swing.JTextField txtRepresentanteHogar4;
    private javax.swing.JTextField txtTelefono4;
    private javax.swing.JTextField txtUbicacion4;
    // End of variables declaration//GEN-END:variables
}
