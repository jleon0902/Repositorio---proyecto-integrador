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
import javax.swing.JOptionPane;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;




public class P2_AdministradorRegistrarUsuarios extends javax.swing.JFrame {
    
    private Runnable accionCancelar;

private controlador.P2_AdministradorRegistrarUsuariosControlador controlador;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(P2_AdministradorRegistrarUsuarios.class.getName());
private javax.swing.JPanel pnlContenedor;

    /**
     * Creates new form iniciarSesion
     */
public P2_AdministradorRegistrarUsuarios() {

    initComponents();
    controlador = new controlador.P2_AdministradorRegistrarUsuariosControlador(this);
    configurarCamposTexto();
   
    pnlRegistrarUsuarios4.setBackground(java.awt.Color.WHITE);

    // Configurar el panel que contiene el formulario.
    pnlContenedor = new javax.swing.JPanel();

    pnlContenedor.setLayout(null);
    pnlContenedor.setBackground(java.awt.Color.WHITE);

    // Tamaño del panel visible.
    pnlContenedor.setPreferredSize(new java.awt.Dimension(670, 510));

    // Retirar el formulario de su ubicación original.
    getContentPane().remove(pnlRegistrarUsuarios4);

    // Agregar el formulario al contenedor.
    pnlContenedor.add(pnlRegistrarUsuarios4);

    // Tamaño completo del formulario.
    pnlRegistrarUsuarios4.setBounds(0, 0, 670, 1153);

    // Configurar el desplazamiento.
    sbRegistrarUsuarios.setMinimum(0);
    sbRegistrarUsuarios.setMaximum(1153);
    sbRegistrarUsuarios.setVisibleAmount(510);
    sbRegistrarUsuarios.setValue(0);
    sbRegistrarUsuarios.setUnitIncrement(20);
    sbRegistrarUsuarios.setBlockIncrement(100);

    sbRegistrarUsuarios.addAdjustmentListener(e -> {

        int valorScroll = e.getValue();

        pnlRegistrarUsuarios4.setLocation(0, -valorScroll);

        pnlContenedor.repaint();
    });

    pnlContenedor.addMouseWheelListener(e -> {

        int nuevoValor = sbRegistrarUsuarios.getValue()
                + (e.getWheelRotation() * 25);

        sbRegistrarUsuarios.setValue(nuevoValor);
    });

cmbDepartamento.addActionListener(e -> {
    if (controlador != null) {
        controlador.departamentoSeleccionado();
    }
});

cmbRoles4.addActionListener(e -> {
    if (controlador != null) {
        controlador.rolSeleccionado();
    }
});

cmbRoles4.removeAllItems();
cmbRoles4.setEnabled(false);
btnGuardar4.setEnabled(false);

limpiarCombosUbicacion();

getContentPane().removeAll();

getContentPane().setLayout(new java.awt.BorderLayout());

getContentPane().add(pnlContenedor, java.awt.BorderLayout.CENTER);

sbRegistrarUsuarios.setVisible(true);

getContentPane().add(sbRegistrarUsuarios, java.awt.BorderLayout.EAST);

setSize(690, 510);

    getContentPane().revalidate();
    getContentPane().repaint();
}


// Obtener los datos escritos en los campos.
public String getCedula() {
    return txtCedula4.getText().trim();
}

public String getNombre() {
    return txtNombres4.getText().trim();
}

public String getApellido() {
    return txtApellido.getText().trim();
}

public String getUsuario() {
    return txtUsuario4.getText().trim();
}

public String getContrasena() {
    return txtContraseña4.getText().trim();
}

// Obtener las selecciones de los combos.
public String getRolSeleccionado() {
    return (String) cmbRoles4.getSelectedItem();
}

public String getRegionSeleccionada() {
    return (String) cmbRegion4.getSelectedItem();
}

public String getDepartamentoSeleccionado() {
    return (String) cmbDepartamento.getSelectedItem();
}

public String getMunicipioSeleccionado() {
    return (String) cmbMunicipio.getSelectedItem();
}

// Métodos para cargar los roles.
public void limpiarRoles() {
    cmbRoles4.removeAllItems();
}

public void agregarRol(String rol) {
    cmbRoles4.addItem(rol);
}

public void seleccionarNingunRol() {
    cmbRoles4.setSelectedIndex(-1);
}

public void habilitarRoles(boolean habilitar) {
    cmbRoles4.setEnabled(habilitar);
}

// Métodos para cargar las regiones.
public void agregarRegion(String region) {
    cmbRegion4.addItem(region);
}

public void seleccionarNingunaRegion() {
    cmbRegion4.setSelectedIndex(-1);
}

public void habilitarRegion(boolean habilitar) {
    cmbRegion4.setEnabled(habilitar);
}

// Métodos para cargar los departamentos.
public void agregarDepartamento(String departamento) {
    cmbDepartamento.addItem(departamento);
}

public void seleccionarNingunDepartamento() {
    cmbDepartamento.setSelectedIndex(-1);
}

public void habilitarDepartamento(boolean habilitar) {
    cmbDepartamento.setEnabled(habilitar);
}

// Métodos para cargar los municipios.
public void agregarMunicipio(String municipio) {
    cmbMunicipio.addItem(municipio);
}

public void seleccionarNingunMunicipio() {
    cmbMunicipio.setSelectedIndex(-1);
}

public void habilitarMunicipio(boolean habilitar) {
    cmbMunicipio.setEnabled(habilitar);
}

// Limpiar los combos de ubicación.
public void limpiarCombosUbicacion() {

    cmbRegion4.removeAllItems();
    cmbDepartamento.removeAllItems();
    cmbMunicipio.removeAllItems();

    cmbRegion4.setSelectedIndex(-1);
    cmbDepartamento.setSelectedIndex(-1);
    cmbMunicipio.setSelectedIndex(-1);

    cmbRegion4.setEnabled(false);
    cmbDepartamento.setEnabled(false);
    cmbMunicipio.setEnabled(false);
}

public void limpiarDepartamentosYMunicipios() {

    cmbDepartamento.removeAllItems();
    cmbMunicipio.removeAllItems();

    cmbDepartamento.setSelectedIndex(-1);
    cmbMunicipio.setSelectedIndex(-1);

    cmbDepartamento.setEnabled(false);
    cmbMunicipio.setEnabled(false);
}

public void limpiarMunicipios() {

    cmbMunicipio.removeAllItems();
    cmbMunicipio.setSelectedIndex(-1);
    cmbMunicipio.setEnabled(false);
}

// Habilitar o deshabilitar el botón Guardar.
public void habilitarGuardar(boolean habilitar) {
    btnGuardar4.setEnabled(habilitar);
}

// Mostrar mensajes.
public void mostrarError(String mensaje) {
    JOptionPane.showMessageDialog(
            this,
            mensaje,
            "Error",
            JOptionPane.ERROR_MESSAGE
    );
}

public void mostrarAdvertencia(String mensaje) {
    JOptionPane.showMessageDialog(
            this,
            mensaje,
            "Advertencia",
            JOptionPane.WARNING_MESSAGE
    );
}

public void mostrarInformacion(String mensaje) {
    JOptionPane.showMessageDialog(
            this,
            mensaje,
            "Información",
            JOptionPane.INFORMATION_MESSAGE
    );
}

// Confirmar el registro.
public boolean confirmarRegistro() {

    int respuesta = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de que desea registrar este usuario?",
            "Confirmar registro",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
    );

    return respuesta == JOptionPane.YES_OPTION;
}

// Limpiar los campos después de registrar.
public void limpiarFormulario() {

    txtCedula4.setText("");
    txtNombres4.setText("");
    txtApellido.setText("");
    txtUsuario4.setText("");
    txtContraseña4.setText("");

    cmbRoles4.setSelectedIndex(-1);

    limpiarCombosUbicacion();
}

// Confirmar si desea borrar la información ingresada.
public boolean confirmarCancelacion() {

    int respuesta = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de que desea borrar la información ingresada?",
            "Confirmar cancelación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
    );

    return respuesta == JOptionPane.YES_OPTION;
}
// Ejecutar la acción de cancelar que recibe desde el menú P1.
public void ejecutarAccionCancelar() {

    if (accionCancelar != null) {
        accionCancelar.run();
    } else {
        dispose();
    }
}

// El menú P1 establece qué hacer cuando se pulsa Cancelar.
public void setAccionCancelar(Runnable accionCancelar) {
    this.accionCancelar = accionCancelar;
}

public javax.swing.JPanel obtenerPanelRegistro() {
    return getContentPane() == null ? pnlContenedor : pnlContenedor;
}
public javax.swing.JScrollBar obtenerScrollBar() {
    return sbRegistrarUsuarios;
}

   // Configurar las restricciones de las cajas de texto.


private void configurarCamposTexto() {

    configurarFiltro(
            txtCedula4,
            "[0-9]*",
            10,
            "La cédula solamente debe contener números."
    );

    configurarFiltro(
            txtNombres4,
            "[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]*",
            50,
            "Los nombres solamente deben contener letras y espacios."
    );

    configurarFiltro(
            txtApellido,
            "[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]*",
            50,
            "Los apellidos solamente deben contener letras y espacios."
    );

    // USUARIO: máximo 20 caracteres.
    configurarFiltro(
            txtUsuario4,
            "[a-zA-Z0-9._]*",
            20,
            "El usuario solo permite letras, números, punto y guion bajo."
    );

    // CONTRASEÑA: máximo 64 caracteres.
    configurarFiltro(
            txtContraseña4,
            "[a-zA-Z0-9@#$%&*!?_.-]*",
            64,
            "La contraseña solo permite letras, números y los símbolos autorizados."
    );
}




// Método que aplica las restricciones a cada caja de texto.


private void configurarFiltro(
        javax.swing.JTextField campo,
        String expresion,
        int maximo,
        String mensaje) {

    AbstractDocument documento =
            (AbstractDocument) campo.getDocument();

    documento.setDocumentFilter(new DocumentFilter() {

        @Override
        public void insertString(
                FilterBypass fb,
                int offset,
                String texto,
                AttributeSet attrs) throws BadLocationException {

            if (texto == null) {
                return;
            }

            replace(fb, offset, 0, texto, attrs);
        }

        @Override
        public void replace(
                FilterBypass fb,
                int offset,
                int longitud,
                String texto,
                AttributeSet attrs) throws BadLocationException {

            if (texto == null) {
                texto = "";
            }

            String actual = fb.getDocument().getText(
                    0,
                    fb.getDocument().getLength()
            );

            String nuevo = actual.substring(0, offset)
                    + texto
                    + actual.substring(offset + longitud);

            if (nuevo.length() <= maximo
                    && nuevo.matches(expresion)) {

                super.replace(fb, offset, longitud, texto, attrs);

            } else {

                JOptionPane.showMessageDialog(
                        P2_AdministradorRegistrarUsuarios.this,
                        mensaje,
                        "Dato no permitido",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        }
    });
}

  



public boolean validarUsuarioYContrasena() {

    String usuario = getUsuario();
    String contrasena = getContrasena();

    String mensaje = "";

    // VALIDAR USUARIO
    if (usuario.isEmpty()) {

        mensaje += "Usuario: este campo es obligatorio y no puede "
                + "estar vacío.";

    } else if (usuario.length() < 5 || usuario.length() > 20
            || !usuario.matches("[a-zA-Z0-9._]+")) {

        mensaje += "Usuario: debe tener entre 5 y 20 caracteres "
                + "y solo puede contener letras sin tildes, "
                + "números, punto (.) y guion bajo (_).";
    }

    // VALIDAR CONTRASEÑA
    if (contrasena.isEmpty()) {

        if (!mensaje.isEmpty()) {
            mensaje += "\n\n- ";
        }

        mensaje += "Contraseña: este campo es obligatorio y no puede "
                + "estar vacío.";

    } else if (contrasena.length() < 8 || contrasena.length() > 64
            || !contrasena.matches(
                    "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])"
                    + "(?=.*[@#$%&*!?_.-])"
                    + "[a-zA-Z0-9@#$%&*!?_.-]+$")) {

        if (!mensaje.isEmpty()) {
            mensaje += "\n\n- ";
        }

        mensaje += "Contraseña: debe tener entre 8 y 64 caracteres, "
                + "incluir al menos una letra mayúscula, una minúscula, "
                + "un número y un símbolo permitido "
                + "(@ # $ % & * ! ? _ - .). "
                + "No debe contener espacios ni tildes.";
    }

    // MOSTRAR LOS ERRORES
    if (!mensaje.isEmpty()) {

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Validación de usuario y contraseña",
                JOptionPane.WARNING_MESSAGE
        );

        return false;
    }

    return true;
}





public controlador.P2_AdministradorRegistrarUsuariosControlador obtenerControlador() {
    return controlador;
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
        lblDane = new javax.swing.JLabel();
        lblEnph = new javax.swing.JLabel();
        pnlRegistrarUsuarios4 = new javax.swing.JPanel();
        lblRegistrarUsuariosInterfaz4 = new javax.swing.JLabel();
        lblIngreseDatos4 = new javax.swing.JLabel();
        lblCedula4 = new javax.swing.JLabel();
        lblNombres4 = new javax.swing.JLabel();
        lblApellidos4 = new javax.swing.JLabel();
        lblSeleccioneUbicacion4 = new javax.swing.JLabel();
        lblRegion4 = new javax.swing.JLabel();
        lblDepartamento4 = new javax.swing.JLabel();
        lblMunicipio4 = new javax.swing.JLabel();
        lblSeleccionRolYUsuario4 = new javax.swing.JLabel();
        lblUsuario5 = new javax.swing.JLabel();
        lblRoles4 = new javax.swing.JLabel();
        lblContraseña4 = new javax.swing.JLabel();
        cmbRoles4 = new javax.swing.JComboBox<>();
        txtCedula4 = new javax.swing.JTextField();
        txtNombres4 = new javax.swing.JTextField();
        txtUsuario4 = new javax.swing.JTextField();
        txtContraseña4 = new javax.swing.JTextField();
        cmbRegion4 = new javax.swing.JComboBox<>();
        btnCancelar4 = new javax.swing.JButton();
        btnGuardar4 = new javax.swing.JButton();
        cmbDepartamento = new javax.swing.JComboBox<>();
        cmbMunicipio = new javax.swing.JComboBox<>();
        lblUsuarioContraseña = new javax.swing.JLabel();
        sbRegistrarUsuarios = new javax.swing.JScrollBar();
        txtApellido = new javax.swing.JTextField();
        pnlMenu = new javax.swing.JPanel();
        lblMenu = new javax.swing.JLabel();
        btnRegistrarUsuarios = new javax.swing.JButton();
        btnUsuarios = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();
        btnRegistrarPortatiles = new javax.swing.JButton();
        btnPortatiles = new javax.swing.JButton();
        pnlInterfaz = new javax.swing.JPanel();

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

        lblDane.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/logoDane.png"))); // NOI18N

        lblEnph.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/logoEnph.png"))); // NOI18N

        javax.swing.GroupLayout pnlLogosLayout = new javax.swing.GroupLayout(pnlLogos);
        pnlLogos.setLayout(pnlLogosLayout);
        pnlLogosLayout.setHorizontalGroup(
            pnlLogosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLogosLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblDane)
                .addGap(18, 18, 18)
                .addComponent(lblEnph)
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
                    .addComponent(lblDane)
                    .addComponent(lblEnph)
                    .addGroup(pnlLogosLayout.createSequentialGroup()
                        .addComponent(lblAdministrador)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUsuario)
                        .addGap(8, 8, 8)))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        getContentPane().add(pnlLogos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 120));

        pnlRegistrarUsuarios4.setBackground(new java.awt.Color(255, 255, 255));
        pnlRegistrarUsuarios4.setBorder(new javax.swing.border.MatteBorder(null));
        pnlRegistrarUsuarios4.setPreferredSize(new java.awt.Dimension(700, 800));
        pnlRegistrarUsuarios4.setRequestFocusEnabled(false);

        lblRegistrarUsuariosInterfaz4.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        lblRegistrarUsuariosInterfaz4.setText("Registrar usuarios");

        lblIngreseDatos4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblIngreseDatos4.setText("Ingrese los datos de la persona que desea registrar");

        lblCedula4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblCedula4.setText("Cédula:");

        lblNombres4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblNombres4.setText("Nombres:");

        lblApellidos4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblApellidos4.setText("Apellidos:");

        lblSeleccioneUbicacion4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblSeleccioneUbicacion4.setText("Seleccione el lugar de ubicación");

        lblRegion4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblRegion4.setText("Región:");

        lblDepartamento4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblDepartamento4.setText("Departamento:");

        lblMunicipio4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMunicipio4.setText("Municipio:");

        lblSeleccionRolYUsuario4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblSeleccionRolYUsuario4.setText("Seleccione el rol que el usuario va a desempeñar ");

        lblUsuario5.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblUsuario5.setText("Usuario:");

        lblRoles4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblRoles4.setText("Roles:");

        lblContraseña4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblContraseña4.setText("Contraseña:");

        cmbRoles4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtCedula4.addActionListener(this::txtCedula4ActionPerformed);

        txtNombres4.addActionListener(this::txtNombres4ActionPerformed);

        cmbRegion4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbRegion4.addActionListener(this::cmbRegion4ActionPerformed);

        btnCancelar4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnCancelar4.setText("Cancelar");
        btnCancelar4.addActionListener(this::btnCancelar4ActionPerformed);

        btnGuardar4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnGuardar4.setText("Guardar");
        btnGuardar4.addActionListener(this::btnGuardar4ActionPerformed);

        cmbDepartamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbMunicipio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblUsuarioContraseña.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblUsuarioContraseña.setText("Asignele un usuario y contraseña:");

        txtApellido.addActionListener(this::txtApellidoActionPerformed);

        javax.swing.GroupLayout pnlRegistrarUsuarios4Layout = new javax.swing.GroupLayout(pnlRegistrarUsuarios4);
        pnlRegistrarUsuarios4.setLayout(pnlRegistrarUsuarios4Layout);
        pnlRegistrarUsuarios4Layout.setHorizontalGroup(
            pnlRegistrarUsuarios4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRegistrarUsuarios4Layout.createSequentialGroup()
                .addGroup(pnlRegistrarUsuarios4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRegistrarUsuarios4Layout.createSequentialGroup()
                        .addGap(230, 230, 230)
                        .addComponent(lblRegistrarUsuariosInterfaz4))
                    .addGroup(pnlRegistrarUsuarios4Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(pnlRegistrarUsuarios4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlRegistrarUsuarios4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRegistrarUsuarios4Layout.createSequentialGroup()
                                    .addGroup(pnlRegistrarUsuarios4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblIngreseDatos4)
                                        .addGroup(pnlRegistrarUsuarios4Layout.createSequentialGroup()
                                            .addGroup(pnlRegistrarUsuarios4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(lblUsuario5)
                                                .addComponent(lblContraseña4))
                                            .addGap(51, 51, 51)
                                            .addGroup(pnlRegistrarUsuarios4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtContraseña4)
                                                .addComponent(txtUsuario4, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(pnlRegistrarUsuarios4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(pnlRegistrarUsuarios4Layout.createSequentialGroup()
                                                .addComponent(lblCedula4)
                                                .addGap(34, 34, 34)
                                                .addComponent(txtCedula4, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlRegistrarUsuarios4Layout.createSequentialGroup()
                                                .addComponent(lblNombres4)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtNombres4, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(lblSeleccionRolYUsuario4))
                                    .addGap(231, 231, 231))
                                .addComponent(lblUsuarioContraseña)
                                .addComponent(lblSeleccioneUbicacion4)
                                .addGroup(pnlRegistrarUsuarios4Layout.createSequentialGroup()
                                    .addComponent(lblApellidos4)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlRegistrarUsuarios4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(pnlRegistrarUsuarios4Layout.createSequentialGroup()
                                        .addComponent(lblMunicipio4)
                                        .addGap(48, 48, 48)
                                        .addComponent(cmbMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlRegistrarUsuarios4Layout.createSequentialGroup()
                                        .addComponent(lblDepartamento4)
                                        .addGap(18, 18, 18)
                                        .addComponent(cmbDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlRegistrarUsuarios4Layout.createSequentialGroup()
                                        .addComponent(lblRegion4)
                                        .addGap(31, 31, 31)
                                        .addComponent(cmbRegion4, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(pnlRegistrarUsuarios4Layout.createSequentialGroup()
                                    .addComponent(lblRoles4)
                                    .addGap(40, 40, 40)
                                    .addComponent(cmbRoles4, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlRegistrarUsuarios4Layout.createSequentialGroup()
                                .addComponent(btnCancelar4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnGuardar4)
                                .addGap(4, 4, 4)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addComponent(sbRegistrarUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlRegistrarUsuarios4Layout.setVerticalGroup(
            pnlRegistrarUsuarios4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRegistrarUsuarios4Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(lblRegistrarUsuariosInterfaz4)
                .addGap(46, 46, 46)
                .addComponent(lblIngreseDatos4)
                .addGap(39, 39, 39)
                .addGroup(pnlRegistrarUsuarios4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCedula4)
                    .addComponent(txtCedula4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(pnlRegistrarUsuarios4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombres4)
                    .addComponent(txtNombres4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(pnlRegistrarUsuarios4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblApellidos4)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addComponent(lblSeleccionRolYUsuario4)
                .addGap(42, 42, 42)
                .addGroup(pnlRegistrarUsuarios4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRoles4)
                    .addComponent(cmbRoles4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
                .addComponent(lblSeleccioneUbicacion4)
                .addGap(40, 40, 40)
                .addGroup(pnlRegistrarUsuarios4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRegion4)
                    .addComponent(cmbRegion4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69)
                .addGroup(pnlRegistrarUsuarios4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDepartamento4)
                    .addComponent(cmbDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(71, 71, 71)
                .addGroup(pnlRegistrarUsuarios4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRegistrarUsuarios4Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lblMunicipio4))
                    .addComponent(cmbMunicipio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addComponent(lblUsuarioContraseña)
                .addGap(31, 31, 31)
                .addGroup(pnlRegistrarUsuarios4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuario5)
                    .addComponent(txtUsuario4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addGroup(pnlRegistrarUsuarios4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblContraseña4)
                    .addComponent(txtContraseña4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlRegistrarUsuarios4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
            .addGroup(pnlRegistrarUsuarios4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sbRegistrarUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 1139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(pnlRegistrarUsuarios4, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, -1, -1));

        pnlMenu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblMenu.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMenu.setText("Menú");

        btnRegistrarUsuarios.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnRegistrarUsuarios.setText("<html><center>Registrar<br>usuarios</center></html>");
        btnRegistrarUsuarios.addActionListener(this::btnRegistrarUsuariosActionPerformed);

        btnUsuarios.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnUsuarios.setText("Usuarios");
        btnUsuarios.addActionListener(this::btnUsuariosActionPerformed);

        btnCerrarSesion.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnCerrarSesion.setText("Cerrar sesión");
        btnCerrarSesion.addActionListener(this::btnCerrarSesionActionPerformed);

        btnRegistrarPortatiles.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnRegistrarPortatiles.setText("<html><center>Registrar<br>portátiles</center></html>");
        btnRegistrarPortatiles.addActionListener(this::btnRegistrarPortatilesActionPerformed);

        btnPortatiles.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnPortatiles.setText("Portatiles");
        btnPortatiles.addActionListener(this::btnPortatilesActionPerformed);

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlMenuLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnPortatiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRegistrarPortatiles)
                            .addComponent(btnUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRegistrarUsuarios)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlMenuLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(btnCerrarSesion)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(761, 761, 761))
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblMenu)
                .addGap(799, 799, 799))
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(lblMenu)
                .addGap(47, 47, 47)
                .addComponent(btnRegistrarUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnUsuarios)
                .addGap(26, 26, 26)
                .addComponent(btnRegistrarPortatiles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(btnPortatiles, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addComponent(btnCerrarSesion)
                .addGap(24, 24, 24))
        );

        getContentPane().add(pnlMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 240, 510));

        pnlInterfaz.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnlInterfazLayout = new javax.swing.GroupLayout(pnlInterfaz);
        pnlInterfaz.setLayout(pnlInterfazLayout);
        pnlInterfazLayout.setHorizontalGroup(
            pnlInterfazLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlInterfazLayout.setVerticalGroup(
            pnlInterfazLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        getContentPane().add(pnlInterfaz, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

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

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void txtCedula4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCedula4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCedula4ActionPerformed

    private void txtNombres4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombres4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombres4ActionPerformed

    private void cmbRegion4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRegion4ActionPerformed
        // TODO add your handling code here:
        if (controlador != null) {
        controlador.regionSeleccionada();
    }
    }//GEN-LAST:event_cmbRegion4ActionPerformed

    private void btnCancelar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar4ActionPerformed
        // TODO add your handling code here:
        controlador.cancelar();
    }//GEN-LAST:event_btnCancelar4ActionPerformed

    private void btnGuardar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar4ActionPerformed
        // TODO add your handling code here:
        controlador.guardar();
    }//GEN-LAST:event_btnGuardar4ActionPerformed

    private void txtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new P2_AdministradorRegistrarUsuarios().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar4;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnGuardar4;
    private javax.swing.JButton btnPortatiles;
    private javax.swing.JButton btnRegistrarPortatiles;
    private javax.swing.JButton btnRegistrarUsuarios;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JComboBox<String> cmbDepartamento;
    private javax.swing.JComboBox<String> cmbMunicipio;
    private javax.swing.JComboBox<String> cmbRegion4;
    private javax.swing.JComboBox<String> cmbRoles4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblAdministrador;
    private javax.swing.JLabel lblApellidos4;
    private javax.swing.JLabel lblCedula4;
    private javax.swing.JLabel lblContraseña4;
    private javax.swing.JLabel lblDane;
    private javax.swing.JLabel lblDepartamento4;
    private javax.swing.JLabel lblEnph;
    private javax.swing.JLabel lblIngreseDatos4;
    private javax.swing.JLabel lblMenu;
    private javax.swing.JLabel lblMunicipio4;
    private javax.swing.JLabel lblNombres4;
    private javax.swing.JLabel lblRegion4;
    private javax.swing.JLabel lblRegistrarUsuariosInterfaz4;
    private javax.swing.JLabel lblRoles4;
    private javax.swing.JLabel lblSeleccionRolYUsuario4;
    private javax.swing.JLabel lblSeleccioneUbicacion4;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lblUsuario5;
    private javax.swing.JLabel lblUsuarioContraseña;
    private javax.swing.JPanel pnlInterfaz;
    private javax.swing.JPanel pnlLogos;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlRegistrarUsuarios4;
    private javax.swing.JScrollBar sbRegistrarUsuarios;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCedula4;
    private javax.swing.JTextField txtContraseña4;
    private javax.swing.JTextField txtNombres4;
    private javax.swing.JTextField txtUsuario4;
    // End of variables declaration//GEN-END:variables
}
