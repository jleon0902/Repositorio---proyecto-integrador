/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

/**
 *
 * @author usuario
 */
import controlador.PortatilControlador;
import modelo.Portatil;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;

public class P7_AdministradorEditarPortatiles1 extends javax.swing.JFrame {
private PortatilControlador controlador;
        private javax.swing.Timer temporizadorConexion;
    private String numeroSerieAnterior;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(P7_AdministradorEditarPortatiles1.class.getName());


public P7_AdministradorEditarPortatiles1() {
    initComponents();

    controlador = new PortatilControlador();

    // Bloquear los botones inicialmente.
    btnGuardar.setEnabled(false);
    btnCancelar.setEnabled(false);

// Comprobar periódicamente la conexión con Oracle.
temporizadorConexion = new javax.swing.Timer(3000, e -> {
    actualizarEstadoBotones();
});

temporizadorConexion.start();

// Detener el temporizador al cerrar la ventana.
addWindowListener(new java.awt.event.WindowAdapter() {
    @Override
    public void windowClosed(java.awt.event.WindowEvent e) {
        temporizadorConexion.stop();
    }
});
setDefaultCloseOperation(
        javax.swing.WindowConstants.DISPOSE_ON_CLOSE
);
        // 1. Permitir redimensionar y maximizar la ventana
        this.setResizable(true);
        this.setSize(956, 670);
        this.setLocationRelativeTo(null);

        // 2. Establecer AbsoluteLayout en el frame
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // 3. Crear panel contenedor de fondo blanco
        javax.swing.JPanel pnlFondoBlanco = new javax.swing.JPanel();
        pnlFondoBlanco.setBackground(java.awt.Color.WHITE);
        pnlFondoBlanco.setLayout(null);

        // Quitar la línea del borde derecho interna de pnlInterfaz1
        pnlInterfaz1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, java.awt.Color.BLACK));

        // Quitar el panel de formulario del frame y ponerlo dentro del fondo blanco
        getContentPane().remove(pnlInterfaz1);
        pnlFondoBlanco.add(pnlInterfaz1);

        // Agregar el fondo blanco a la ventana con borde inferior completo
        getContentPane().add(pnlFondoBlanco, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 940, 522));

        // 4. Listener para adaptar la franja rosa y el fondo blanco al maximizar
        Runnable reajustarDimensiones = () -> {
            int anchoVentana = getContentPane().getWidth();
            int altoVentana = getContentPane().getHeight();

            int altoHeader = 110;
            int altoAreaInferior = Math.max(100, altoVentana - altoHeader);
            int anchoFondo = Math.max(940, anchoVentana);

            // Reubicar la barra de logos extendida
            getContentPane().remove(pnlLogos);
            getContentPane().add(pnlLogos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, anchoFondo, altoHeader));

            // Reubicar el contenedor blanco abarcando todo el ancho de la pantalla y agregando borde dinámico
            getContentPane().remove(pnlFondoBlanco);
            pnlFondoBlanco.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, java.awt.Color.BLACK));
            
            // Mantener el contenido interno centrado o en la esquina sin línea divisoria
            pnlInterfaz1.setBounds(0, 0, anchoFondo, altoAreaInferior);

            getContentPane().add(pnlFondoBlanco, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, altoHeader, anchoFondo, altoAreaInferior));

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


public void actualizarEstadoBotones() {

    boolean conectado = controlador.estaConectado();

    btnGuardar.setEnabled(conectado);
    btnCancelar.setEnabled(conectado);
}
public P7_AdministradorEditarPortatiles1(Portatil portatil) {
    this();

    if (portatil == null) {
        throw new IllegalArgumentException("No se recibió ningún portátil.");
    }

    numeroSerieAnterior = portatil.getNumeroSerie();

    txtNumeroSerie.setText(portatil.getNumeroSerie());
    txtMarca.setText(portatil.getMarca());
    txtModelo.setText(portatil.getModelo());
}
    public String obtenerNumeroSerie() {
    return txtNumeroSerie.getText().trim();
}
    

public String obtenerMarca() {
    return txtMarca.getText().trim();
}

public String obtenerModelo() {
    return txtModelo.getText().trim();
}

public void cerrarVentana() {
    this.dispose();
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
        lblNumeroSerie = new javax.swing.JLabel();
        lblMarca = new javax.swing.JLabel();
        lblModelo = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        txtNumeroSerie = new javax.swing.JTextField();
        txtMarca = new javax.swing.JTextField();
        txtModelo = new javax.swing.JTextField();
        lblEditarPortatiles = new javax.swing.JLabel();

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
        lblAdministrador.setText("Administrador");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 254, Short.MAX_VALUE)
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
        pnlInterfaz1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 1, 1)));
        pnlInterfaz1.setPreferredSize(new java.awt.Dimension(938, 522));

        lblNumeroSerie.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblNumeroSerie.setText("Número de serie:");

        lblMarca.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMarca.setText("Marca:");

        lblModelo.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblModelo.setText("Modelo:");

        btnCancelar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        btnGuardar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        lblEditarPortatiles.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        lblEditarPortatiles.setText("Editar portátiles");

        javax.swing.GroupLayout pnlInterfaz1Layout = new javax.swing.GroupLayout(pnlInterfaz1);
        pnlInterfaz1.setLayout(pnlInterfaz1Layout);
        pnlInterfaz1Layout.setHorizontalGroup(
            pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                        .addComponent(lblNumeroSerie)
                        .addGap(18, 18, 18)
                        .addComponent(txtNumeroSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                        .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblModelo)
                            .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                                .addComponent(lblMarca)
                                .addGap(91, 91, 91)
                                .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtMarca)
                                    .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(56, 380, Short.MAX_VALUE))
                    .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardar)
                        .addGap(55, 55, 55))))
            .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                .addGap(340, 340, 340)
                .addComponent(lblEditarPortatiles)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlInterfaz1Layout.setVerticalGroup(
            pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInterfaz1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(lblEditarPortatiles)
                .addGap(72, 72, 72)
                .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNumeroSerie)
                    .addComponent(txtNumeroSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMarca)
                    .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblModelo)
                    .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                .addGroup(pnlInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnGuardar))
                .addGap(87, 87, 87))
        );

        getContentPane().add(pnlInterfaz1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 940, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
if (!controlador.estaConectado()) {
        actualizarEstadoBotones();

        JOptionPane.showMessageDialog(
                this,
                "No puede cancelar porque no existe conexión con Oracle usando el usuario ADMINISTRADOR.",
                "Sin conexión",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    controlador.cancelarEdicion(this);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
   if (!controlador.estaConectado()) {
        actualizarEstadoBotones();

        JOptionPane.showMessageDialog(
                this,
                "No puede guardar porque no existe conexión con Oracle usando el usuario ADMINISTRADOR.",
                "Sin conexión",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    controlador.guardarEdicion(this, numeroSerieAnterior);
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
        java.awt.EventQueue.invokeLater(() -> new P7_AdministradorEditarPortatiles1().setVisible(true));
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
    private javax.swing.JLabel lblAdministrador;
    private javax.swing.JLabel lblEditarPortatiles;
    private javax.swing.JLabel lblMarca;
    private javax.swing.JLabel lblModelo;
    private javax.swing.JLabel lblNumeroSerie;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel pnlInterfaz1;
    private javax.swing.JPanel pnlLogos;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtNumeroSerie;
    // End of variables declaration//GEN-END:variables
}
