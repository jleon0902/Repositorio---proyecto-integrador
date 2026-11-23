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
import controlador.P11_RecuentistaInformacionFamiliaControlador;
import javax.swing.JOptionPane;
public class P11_RecuentistaInformacionFamilia extends javax.swing.JFrame {
    private P10_RecuentistaVerHogares1 pantallaAnterior;
    private String numeroVivienda;
    private P11_RecuentistaInformacionFamiliaControlador controlador;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(P11_RecuentistaInformacionFamilia.class.getName());

    /**
     * Creates new form iniciarSesion
     */

public P11_RecuentistaInformacionFamilia() {
    initComponents();
    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
}

public P11_RecuentistaInformacionFamilia(
        String numeroVivienda,
        P10_RecuentistaVerHogares1 pantallaAnterior) {

    initComponents();
    this.pantallaAnterior = pantallaAnterior;
setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    this.numeroVivienda = numeroVivienda;
    this.pantallaAnterior = pantallaAnterior;

    lblMostrarNumeroVivienda.setText(numeroVivienda);

    lblContraseña.setVisible(false);
    lblMostrarContraseña.setVisible(false);

    controlador =
            new P11_RecuentistaInformacionFamiliaControlador(
                    this,
                    numeroVivienda
            );

    controlador.cargarInformacion();
}



// MOSTRAR INFORMACIÓN RECIBIDA DEL CONTROLADOR
public void mostrarInformacion(Object[] informacion) {

    lblMostrarDireccion.setText(
            informacion[0] == null ? "" : informacion[0].toString()
    );

    lblMostrarBarrio.setText(
            informacion[1] == null ? "" : informacion[1].toString()
    );

    lblMostrarEstrato.setText(
            informacion[2] == null ? "" : informacion[2].toString()
    );

    String region = informacion[3] == null ? "" : informacion[3].toString();
    String departamento = informacion[4] == null ? "" : informacion[4].toString();
    String municipio = informacion[5] == null ? "" : informacion[5].toString();

    lblMostrarUbicacion.setText(
            region + " / " + departamento + " / " + municipio
    );

    // REPRESENTANTE
    lblMostrarCedula.setText(
            informacion[6] == null ? "" : informacion[6].toString()
    );

    String nombre = informacion[7] == null ? "" : informacion[7].toString();
    String apellido = informacion[8] == null ? "" : informacion[8].toString();

    lblMostrarRepresentante.setText(
            nombre + " " + apellido
    );

    lblMostrarTelefono.setText(
            informacion[9] == null ? "" : informacion[9].toString()
    );

    lblMostrarCorreo.setText(
            informacion[10] == null ? "" : informacion[10].toString()
    );

    // USUARIO
    lblMostrarUsuario.setText(
            informacion[11] == null ? "" : informacion[11].toString()
    );

    // No mostrar la contraseña del usuario.
}

// CONFIRMAR ELIMINACIÓN
public boolean confirmarEliminacion() {

    int respuesta = javax.swing.JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de que quiere eliminar la información de esta familia?",
            "Confirmar eliminación",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.WARNING_MESSAGE
    );

    return respuesta == javax.swing.JOptionPane.YES_OPTION;
}

// CONFIRMAR SALIDA
public boolean confirmarSalida() {

    int respuesta = javax.swing.JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de que desea salir de la información de la familia?",
            "Confirmar salida",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE
    );

    return respuesta == javax.swing.JOptionPane.YES_OPTION;
}

// MOSTRAR MENSAJE
public void mostrarMensaje(String mensaje) {

    javax.swing.JOptionPane.showMessageDialog(
            this,
            mensaje,
            "Información",
            javax.swing.JOptionPane.INFORMATION_MESSAGE
    );
}

// MOSTRAR ADVERTENCIA
public void mostrarAdvertencia(String mensaje) {

    javax.swing.JOptionPane.showMessageDialog(
            this,
            mensaje,
            "Advertencia",
            javax.swing.JOptionPane.WARNING_MESSAGE
    );
}

// MOSTRAR ERROR
public void mostrarError(String mensaje) {

    javax.swing.JOptionPane.showMessageDialog(
            this,
            mensaje,
            "Error",
            javax.swing.JOptionPane.ERROR_MESSAGE
    );
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
        lblMostrarDireccion = new javax.swing.JLabel();
        lblRepresentanteHogar = new javax.swing.JLabel();
        lblCedula = new javax.swing.JLabel();
        lblTelefono = new javax.swing.JLabel();
        lblCorreo = new javax.swing.JLabel();
        lblUsuario1 = new javax.swing.JLabel();
        lblContraseña = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        lblMostrarNumeroVivienda = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        lblMostrarUbicacion = new javax.swing.JLabel();
        btnEditar = new javax.swing.JButton();
        lblMostrarContraseña = new javax.swing.JLabel();
        lblMostrarUsuario = new javax.swing.JLabel();
        lblMostrarCorreo = new javax.swing.JLabel();
        lblMostrarTelefono = new javax.swing.JLabel();
        lblMostrarCedula = new javax.swing.JLabel();
        lblMostrarRepresentante = new javax.swing.JLabel();
        lblMostrarEstrato = new javax.swing.JLabel();
        lblInformacionFamilia = new javax.swing.JLabel();
        lblMostrarBarrio = new javax.swing.JLabel();
        lblNumeroVivienda = new javax.swing.JLabel();
        lblUbicacion = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        lblBarrio = new javax.swing.JLabel();
        lblEstrato = new javax.swing.JLabel();

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
        pnlInterfaz1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        pnlInterfaz1.setPreferredSize(new java.awt.Dimension(938, 522));

        lblMostrarDireccion.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMostrarDireccion.setText("jLabel3");

        lblRepresentanteHogar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblRepresentanteHogar.setText("Representante del hogar:");

        lblCedula.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblCedula.setText("Cédula:");

        lblTelefono.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblTelefono.setText("Teléfono:");

        lblCorreo.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblCorreo.setText("Correo:");

        lblUsuario1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblUsuario1.setText("Usuario:");

        lblContraseña.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblContraseña.setText("Contraseña:");

        btnSalir.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(this::btnSalirActionPerformed);

        lblMostrarNumeroVivienda.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMostrarNumeroVivienda.setText("jLabel3");

        btnEliminar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        lblMostrarUbicacion.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMostrarUbicacion.setText("COMPUESTA POR REGION,DEP,MUNI TOMAR DEL RECUENTISTA");

        btnEditar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(this::btnEditarActionPerformed);

        lblMostrarContraseña.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMostrarContraseña.setText("jLabel3");

        lblMostrarUsuario.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMostrarUsuario.setText("jLabel3");

        lblMostrarCorreo.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMostrarCorreo.setText("jLabel3");

        lblMostrarTelefono.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMostrarTelefono.setText("jLabel3");

        lblMostrarCedula.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMostrarCedula.setText("jLabel3");

        lblMostrarRepresentante.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMostrarRepresentante.setText("jLabel3");

        lblMostrarEstrato.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMostrarEstrato.setText("jLabel3");

        lblInformacionFamilia.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        lblInformacionFamilia.setText("Información del hogar");

        lblMostrarBarrio.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMostrarBarrio.setText("jLabel3");

        lblNumeroVivienda.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblNumeroVivienda.setText("Número vivienda:");

        lblUbicacion.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblUbicacion.setText("Ubicación: ");

        lblDireccion.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblDireccion.setText("Dirección:");

        lblBarrio.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblBarrio.setText("Barrio:");

        lblEstrato.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblEstrato.setText("Estrato:");

        javax.swing.GroupLayout pnlInterfaz1Layout = new javax.swing.GroupLayout(pnlInterfaz1);
        pnlInterfaz1.setLayout(pnlInterfaz1Layout);
        pnlInterfaz1Layout.setHorizontalGroup(
            pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                            .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                                    .addComponent(lblEstrato)
                                    .addGap(36, 36, 36)
                                    .addComponent(lblMostrarEstrato, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                                    .addComponent(lblNumeroVivienda)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(lblMostrarNumeroVivienda, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                                    .addComponent(lblDireccion)
                                    .addGap(18, 18, 18)
                                    .addComponent(lblMostrarDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                                    .addComponent(lblUbicacion)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lblMostrarUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(19, 19, 19))
                        .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                            .addComponent(lblBarrio)
                            .addGap(38, 38, 38)
                            .addComponent(lblMostrarBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                        .addComponent(btnSalir)
                        .addGap(378, 378, 378)))
                .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                        .addGap(0, 163, Short.MAX_VALUE)
                        .addComponent(btnEliminar)
                        .addGap(70, 70, 70)
                        .addComponent(btnEditar)
                        .addContainerGap(46, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInterfaz1Layout.createSequentialGroup()
                        .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                                .addComponent(lblUsuario1)
                                .addGap(219, 376, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInterfaz1Layout.createSequentialGroup()
                                .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblContraseña)
                                    .addComponent(lblCorreo)
                                    .addComponent(lblCedula))
                                .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblMostrarContraseña, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(lblMostrarCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                                                .addComponent(lblMostrarCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addComponent(lblMostrarUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                            .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                                .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                                        .addComponent(lblTelefono)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblMostrarTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                                        .addComponent(lblRepresentanteHogar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblMostrarRepresentante, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
            .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                .addGap(295, 295, 295)
                .addComponent(lblInformacionFamilia)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlInterfaz1Layout.setVerticalGroup(
            pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(lblInformacionFamilia)
                .addGap(57, 57, 57)
                .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                        .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNumeroVivienda)
                            .addComponent(lblMostrarNumeroVivienda, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblUbicacion)
                            .addComponent(lblMostrarUbicacion))
                        .addGap(21, 21, 21)
                        .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDireccion)
                            .addComponent(lblMostrarDireccion))
                        .addGap(26, 26, 26)
                        .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblBarrio)
                            .addComponent(lblMostrarBarrio))
                        .addGap(28, 28, 28)
                        .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEstrato)
                            .addComponent(lblMostrarEstrato)))
                    .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                        .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblRepresentanteHogar)
                            .addComponent(lblMostrarRepresentante))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCedula)
                            .addComponent(lblMostrarCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTelefono)
                            .addComponent(lblMostrarTelefono))
                        .addGap(24, 24, 24)
                        .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCorreo)
                            .addComponent(lblMostrarCorreo))
                        .addGap(30, 30, 30)
                        .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblUsuario1)
                            .addComponent(lblMostrarUsuario))
                        .addGap(30, 30, 30)
                        .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblContraseña)
                            .addComponent(lblMostrarContraseña))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar)
                    .addComponent(btnSalir))
                .addGap(45, 45, 45))
        );

        getContentPane().add(pnlInterfaz1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 940, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents
// DEVOLVER LA PANTALLA ANTERIOR
public P10_RecuentistaVerHogares1 getPantallaAnterior() {
    return pantallaAnterior;
}
    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
  if (pantallaAnterior != null) {
        this.dispose();

    } else {

        JOptionPane.showMessageDialog(
                this,
                "No se encontró la pantalla anterior.",
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        if (controlador != null) {
        controlador.eliminarHogar();
    }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
       if (controlador != null) {
        controlador.editarHogar();
    }
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
        java.awt.EventQueue.invokeLater(() -> new P11_RecuentistaInformacionFamilia().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnSalir;
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
    private javax.swing.JLabel lblBarrio;
    private javax.swing.JLabel lblCedula;
    private javax.swing.JLabel lblContraseña;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblEstrato;
    private javax.swing.JLabel lblInformacionFamilia;
    private javax.swing.JLabel lblMostrarBarrio;
    private javax.swing.JLabel lblMostrarCedula;
    private javax.swing.JLabel lblMostrarContraseña;
    private javax.swing.JLabel lblMostrarCorreo;
    private javax.swing.JLabel lblMostrarDireccion;
    private javax.swing.JLabel lblMostrarEstrato;
    private javax.swing.JLabel lblMostrarNumeroVivienda;
    private javax.swing.JLabel lblMostrarRepresentante;
    private javax.swing.JLabel lblMostrarTelefono;
    private javax.swing.JLabel lblMostrarUbicacion;
    private javax.swing.JLabel lblMostrarUsuario;
    private javax.swing.JLabel lblNumeroVivienda;
    private javax.swing.JLabel lblRepresentanteHogar;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblUbicacion;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lblUsuario1;
    private javax.swing.JPanel pnlInterfaz1;
    private javax.swing.JPanel pnlLogos;
    // End of variables declaration//GEN-END:variables
}
