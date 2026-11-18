/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

/**
 *
 * @author usuario
 */

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.DefaultListCellRenderer;
import java.awt.Component;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
public class P9_RecuentistaRegistrarHogares1 extends javax.swing.JFrame {
    private String cedulaRecuentista;
    private String barrioNuevoPendiente = null;
    private boolean actualizandoCombo = false;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(P9_RecuentistaRegistrarHogares1.class.getName());

    /**
     * Creates new form iniciarSesion
     */
public P9_RecuentistaRegistrarHogares1() {
    initComponents();


    configurarValidaciones();
    configurarComboBarrio();
    configurarScroll();
}

public void cargarBarrios(java.util.List<String> barrios) {

    actualizandoCombo = true;

    try {

        cmbBarrio.removeAllItems();
        cmbBarrio.setEditable(false);

        if (barrios != null) {

            for (String barrio : barrios) {
                cmbBarrio.addItem(barrio);
            }
        }

        cmbBarrio.addItem("Otras...");

        // Dejar el combo vacío al entrar al perfil.
        cmbBarrio.setSelectedIndex(-1);

    } finally {
        actualizandoCombo = false;
    }
}

public boolean isBarrioEditable() {
    return cmbBarrio.isEditable();
}



private void configurarComboBarrio() {

cmbBarrio.addActionListener(e -> {

    if (actualizandoCombo) {
        return;
    }

    Object seleccionado = cmbBarrio.getSelectedItem();

        if (seleccionado == null ||
                !seleccionado.toString().equals("Otras...")) {
            return;
        }

        String barrio = JOptionPane.showInputDialog(
                this,
                "Ingrese el nombre del barrio:",
                "Registrar barrio",
                JOptionPane.QUESTION_MESSAGE
        );

        if (barrio == null) {

            if (cmbBarrio.getItemCount() > 1) {
                cmbBarrio.setSelectedIndex(0);
            } else {
                cmbBarrio.setSelectedIndex(-1);
            }

            return;
        }

        barrio = barrio.trim();

        if (barrio.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar el nombre del barrio."
            );

            cmbBarrio.setSelectedIndex(-1);
            return;
        }

        // Verificar si el barrio ya existe.
        for (int i = 0; i < cmbBarrio.getItemCount(); i++) {

            String existente = cmbBarrio.getItemAt(i);

            if (!existente.equals("Otras...") &&
                    existente.equalsIgnoreCase(barrio)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Este barrio ya está en la lista."
                );

                cmbBarrio.setSelectedItem(existente);
                barrioNuevoPendiente = null;
                return;
            }
        }

        // Guardar el barrio nuevo temporalmente.
        barrioNuevoPendiente = barrio;

        // Agregarlo antes de Otras...
        int posicionOtras = cmbBarrio.getItemCount() - 1;

        cmbBarrio.insertItemAt(barrio, posicionOtras);

        // Seleccionar el barrio ingresado.
        cmbBarrio.setSelectedItem(barrio);
    });
}


public boolean esBarrioNuevo() {
    return barrioNuevoPendiente != null;
}

public String getBarrioNuevoPendiente() {
    return barrioNuevoPendiente;
}
public P9_RecuentistaRegistrarHogares1(String cedulaRecuentista) {
    this();
    this.cedulaRecuentista = cedulaRecuentista;
}
public javax.swing.JButton getBtnGuardar() {
    return btnGuardar;
}

public javax.swing.JButton getBtnCancelar() {
    return btnCancelar;
}
public javax.swing.JComboBox<String> getCmbBarrio() {
    return cmbBarrio;
}
public String getCedulaRecuentista() {
    return cedulaRecuentista;
}

public String getNumeroVivienda() {
    return txtNumeroVivienda.getText().trim();
}

public String getDireccion() {
    return txtDireccion.getText().trim();
}

public String getEstrato() {
    return cmbEstrato.getSelectedItem().toString();
}

public Object getBarrioSeleccionado() {

    if (cmbBarrio.isEditable()) {
        return cmbBarrio.getEditor().getItem().toString().trim();
    }

    return cmbBarrio.getSelectedItem();
}
public String getCedula() {
    return txtCedula.getText().trim();
}

public String getNombres() {
    return txtNombres.getText().trim();
}

public String getApellidos() {
    return txtapellidos.getText().trim();
}

public String getTelefono() {
    return txtTelefono.getText().trim();
}

public String getCorreo() {
    return txtCorreo.getText().trim();
}

public String getUsuario() {
    return txtUsuario.getText().trim();
}

public String getContrasena() {
    return txtContraseña.getText();
}

public void mostrarMensaje(String mensaje) {
    JOptionPane.showMessageDialog(this, mensaje);
}

public void limpiarCampos() {
    txtNumeroVivienda.setText("");
    txtDireccion.setText("");
    cmbEstrato.setSelectedIndex(-1);
    cmbBarrio.setSelectedIndex(-1);
    txtCedula.setText("");
    txtNombres.setText("");
    txtapellidos.setText("");
    txtTelefono.setText("");
    txtCorreo.setText("");
    txtUsuario.setText("");
    txtContraseña.setText("");
    barrioNuevoPendiente = null;
}

public void iniciarControlador() {
    new controlador.P9_RecuentistaRegistrarHogaresControlador(this);
}
public javax.swing.JPanel getPanelRegistrarHogares() {
    return pnlRegistrarHogares;
}

public javax.swing.JScrollBar getScrollBarRegistrarHogares() {
    return jScrollBar1;
}
private void configurarValidaciones() {
    // CORREO
aplicarFiltro(txtCorreo,
        "[a-zA-Z0-9._%+@-]*",
        "El correo solo permite letras, números y los símbolos . _ % + @ -.");
    // SOLO NUMEROS
    aplicarFiltro(txtNumeroVivienda, "[0-9]*",
            "El número de vivienda solo permite números.");

    aplicarFiltro(txtCedula, "[0-9]*",
            "La cédula solo permite números.");

    aplicarFiltro(txtTelefono, "[0-9]*",
            "El teléfono solo permite números.");

    // DIRECCION
    aplicarFiltro(txtDireccion, "[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ0-9 #.,\\-/°]*",
            "La dirección no permite símbolos especiales.");

    // NOMBRES Y APELLIDOS
    aplicarFiltro(txtNombres, "[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]*",
            "Los nombres solo permiten letras y espacios.");

    aplicarFiltro(txtapellidos, "[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]*",
            "Los apellidos solo permiten letras y espacios.");

   // USUARIO Y CONTRASEÑA
aplicarFiltro(txtUsuario,
        "[a-zA-Z0-9]*",
        "El usuario solo permite letras y números.",
        10);

aplicarFiltro(txtContraseña,
        "[a-zA-Z0-9]*",
        "La contraseña solo permite letras y números.",
        10);

// ESTRATO
cmbEstrato.setSelectedIndex(-1);

cmbEstrato.setRenderer(new DefaultListCellRenderer() {
    @Override
    public Component getListCellRendererComponent(
            javax.swing.JList<?> lista,
            Object valor,
            int indice,
            boolean seleccionado,
            boolean enfocado) {

        super.getListCellRendererComponent(
                lista, valor, indice, seleccionado, enfocado);

        if (valor == null) {
            setText("Seleccionar");
        }

        return this;
    }
});

}

private void aplicarFiltro(
        javax.swing.JTextField campo,
        String expresion,
        String mensaje,
        int longitudMaxima) {

    AbstractDocument documento =
            (AbstractDocument) campo.getDocument();

    documento.setDocumentFilter(new DocumentFilter() {

        private boolean validar(String texto) {

            return texto.length() <= longitudMaxima
                    && texto.matches(expresion);
        }

        @Override
        public void insertString(
                FilterBypass fb,
                int offset,
                String texto,
                AttributeSet attr)
                throws BadLocationException {

            if (texto == null) {
                return;
            }

            String actual = fb.getDocument().getText(
                    0, fb.getDocument().getLength());

            String nuevo = new StringBuilder(actual)
                    .insert(offset, texto)
                    .toString();

            if (validar(nuevo)) {
                super.insertString(fb, offset, texto, attr);
            } else {
                mostrarError();
            }
        }

        @Override
        public void replace(
                FilterBypass fb,
                int offset,
                int longitud,
                String texto,
                AttributeSet attrs)
                throws BadLocationException {

            if (texto == null) {
                texto = "";
            }

            String actual = fb.getDocument().getText(
                    0, fb.getDocument().getLength());

            String nuevo = new StringBuilder(actual)
                    .replace(offset, offset + longitud, texto)
                    .toString();

            if (validar(nuevo)) {
                super.replace(fb, offset, longitud, texto, attrs);
            } else {
                mostrarError();
            }
        }

        private void mostrarError() {

            java.awt.Toolkit.getDefaultToolkit().beep();

            JOptionPane.showMessageDialog(
                    P9_RecuentistaRegistrarHogares1.this,
                    mensaje + "\nMáximo " + longitudMaxima + " caracteres.",
                    "Dato no válido",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    });
}

public boolean validarNumeroVivienda() {
    String numero = txtNumeroVivienda.getText().trim();

    if (numero.length() < 3 || numero.length() > 10) {
        JOptionPane.showMessageDialog(this,
                "El número de vivienda debe tener entre 3 y 10 caracteres.");
        txtNumeroVivienda.requestFocus();
        return false;
    }

    return true;
}
public boolean validarUsuarioYContrasena() {

    String usuario = txtUsuario.getText().trim();
    String contrasena = txtContraseña.getText();

    if (usuario.length() < 4 || usuario.length() > 10) {

        JOptionPane.showMessageDialog(
                this,
                "El usuario debe tener entre 4 y 10 caracteres."
        );

        txtUsuario.requestFocus();
        return false;
    }

    if (contrasena.length() < 4 || contrasena.length() > 10) {

        JOptionPane.showMessageDialog(
                this,
                "La contraseña debe tener entre 4 y 10 caracteres."
        );

        txtContraseña.requestFocus();
        return false;
    }

    return true;
}


public boolean validarDatosAcceso() {

    // TODOS LOS CAMPOS OBLIGATORIOS, EXCEPTO CORREO.
    if (txtNumeroVivienda.getText().trim().isEmpty()
            || txtDireccion.getText().trim().isEmpty()
            || cmbEstrato.getSelectedIndex() == -1
            || getBarrioSeleccionado() == null
            || getBarrioSeleccionado().toString().trim().isEmpty()
            || txtCedula.getText().trim().isEmpty()
            || txtNombres.getText().trim().isEmpty()
            || txtapellidos.getText().trim().isEmpty()
            || txtTelefono.getText().trim().isEmpty()
            || txtUsuario.getText().trim().isEmpty()
            || txtContraseña.getText().trim().isEmpty()) {

        JOptionPane.showMessageDialog(
                this,
                "Debe completar todos los campos obligatorios. El correo es opcional."
        );

        return false;
    }

    if (!validarNumeroVivienda()) {
        return false;
    }

    if (!validarCorreo()) {
        return false;
    }

    if (!validarUsuarioYContrasena()) {
        return false;
    }

    return true;
}

public boolean validarCorreo() {
    String correo = txtCorreo.getText().trim();

    // Si está vacío, permite continuar con el registro.
    if (correo.isEmpty()) {
        return true;
    }

    String expresion =
            "^[A-Za-z0-9_%+-]+(\\.[A-Za-z0-9_%+-]+)*"
            + "@[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?"
            + "(?:\\.[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?)+$";

    if (!correo.matches(expresion) || correo.length() > 254) {
        JOptionPane.showMessageDialog(this,
                "Ingresa un correo válido.");
        txtCorreo.requestFocus();
        return false;
    }

    return true;
}
private void aplicarFiltro(
        javax.swing.JTextField campo,
        String expresion,
        String mensaje) {

    AbstractDocument documento =
            (AbstractDocument) campo.getDocument();

    documento.setDocumentFilter(new DocumentFilter() {

        private boolean validar(String texto) {
            return texto.matches(expresion);
        }

        @Override
        public void insertString(
                FilterBypass fb,
                int offset,
                String texto,
                AttributeSet attr)
                throws BadLocationException {

            String actual = fb.getDocument().getText(
                    0, fb.getDocument().getLength());

            String nuevo = new StringBuilder(actual)
                    .insert(offset, texto)
                    .toString();

            if (validar(nuevo)) {
                super.insertString(fb, offset, texto, attr);
            } else {
                mostrarError();
            }
        }

        @Override
        public void replace(
                FilterBypass fb,
                int offset,
                int longitud,
                String texto,
                AttributeSet attrs)
                throws BadLocationException {

            String actual = fb.getDocument().getText(
                    0, fb.getDocument().getLength());

            String nuevo = new StringBuilder(actual)
                    .replace(offset, offset + longitud, texto)
                    .toString();

            if (validar(nuevo)) {
                super.replace(fb, offset, longitud, texto, attrs);
            } else {
                mostrarError();
            }
        }

        private void mostrarError() {
            java.awt.Toolkit.getDefaultToolkit().beep();

            javax.swing.JOptionPane.showMessageDialog(
                    P9_RecuentistaRegistrarHogares1.this,
                    mensaje,
                    "Dato no válido",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
        }
    });
}

public void configurarScroll() {

    int altoContenido = 1250;
    int altoVisible = 510;

    jScrollBar1.setMinimum(0);
    jScrollBar1.setMaximum(altoContenido);
    jScrollBar1.setVisibleAmount(altoVisible);
    jScrollBar1.setUnitIncrement(20);
    jScrollBar1.setBlockIncrement(100);

    jScrollBar1.addAdjustmentListener(e -> {

        int valor = e.getValue();

        pnlRegistrarHogares.setLocation(0, -valor);

    });
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
        pnlMenu = new javax.swing.JPanel();
        lblMenu = new javax.swing.JLabel();
        btnRegistrarUsuarios = new javax.swing.JButton();
        btnUsuarios = new javax.swing.JButton();
        btnRegistrarPortatiles = new javax.swing.JButton();
        btnPortatiles = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();
        pnlRegistrarHogares = new javax.swing.JPanel();
        lblRegistrarUsuariosInterfaz = new javax.swing.JLabel();
        lblCedula = new javax.swing.JLabel();
        lblNombres = new javax.swing.JLabel();
        lblSeleccioneUbicacion = new javax.swing.JLabel();
        lblSeleccionRolYUsuario = new javax.swing.JLabel();
        lblUsuario1 = new javax.swing.JLabel();
        lblContraseña = new javax.swing.JLabel();
        txtCedula = new javax.swing.JTextField();
        txtNombres = new javax.swing.JTextField();
        txtUsuario = new javax.swing.JTextField();
        txtContraseña = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNumeroVivienda = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtapellidos = new javax.swing.JTextField();
        lblapellidos = new javax.swing.JLabel();
        jScrollBar1 = new javax.swing.JScrollBar();
        cmbEstrato = new javax.swing.JComboBox<>();
        cmbBarrio = new javax.swing.JComboBox<>();

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 279, Short.MAX_VALUE)
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

        pnlMenu.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(1, 1, 1)));
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addComponent(btnCerrarSesion)
                .addGap(29, 29, 29))
        );

        getContentPane().add(pnlMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 240, -1));

        pnlRegistrarHogares.setBackground(new java.awt.Color(255, 255, 255));
        pnlRegistrarHogares.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));
        pnlRegistrarHogares.setPreferredSize(new java.awt.Dimension(700, 1200));

        lblRegistrarUsuariosInterfaz.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        lblRegistrarUsuariosInterfaz.setText("Registrar hogares");

        lblCedula.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblCedula.setText("Cédula:");

        lblNombres.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblNombres.setText("Nombres:");

        lblSeleccioneUbicacion.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblSeleccioneUbicacion.setText("Ingrese la información del hogar");

        lblSeleccionRolYUsuario.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblSeleccionRolYUsuario.setText("<html>Ingrese la informacion del representante de ese hogar<br>(será el encargado de llenar la encuesta)</html>");

        lblUsuario1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblUsuario1.setText("Usuario:");

        lblContraseña.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblContraseña.setText("Contraseña:");

        txtCedula.addActionListener(this::txtCedulaActionPerformed);

        txtNombres.addActionListener(this::txtNombresActionPerformed);

        btnCancelar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        btnGuardar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        jLabel16.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel16.setText("Número vivienda:");

        jLabel17.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel17.setText("Dirección:");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel3.setText("Estrato:");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel4.setText("Barrio:");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel5.setText("Teléfono:");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel6.setText("Correo:");

        txtTelefono.addActionListener(this::txtTelefonoActionPerformed);

        txtCorreo.addActionListener(this::txtCorreoActionPerformed);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel7.setText("Asignele un usuario y contraseña al representante");

        txtapellidos.addActionListener(this::txtapellidosActionPerformed);

        lblapellidos.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblapellidos.setText("Apellidos:");

        cmbEstrato.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6" }));
        cmbEstrato.addActionListener(this::cmbEstratoActionPerformed);

        cmbBarrio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbBarrio.addActionListener(this::cmbBarrioActionPerformed);

        javax.swing.GroupLayout pnlRegistrarHogaresLayout = new javax.swing.GroupLayout(pnlRegistrarHogares);
        pnlRegistrarHogares.setLayout(pnlRegistrarHogaresLayout);
        pnlRegistrarHogaresLayout.setHorizontalGroup(
            pnlRegistrarHogaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRegistrarHogaresLayout.createSequentialGroup()
                .addGroup(pnlRegistrarHogaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRegistrarHogaresLayout.createSequentialGroup()
                        .addGap(200, 200, 200)
                        .addComponent(lblRegistrarUsuariosInterfaz))
                    .addGroup(pnlRegistrarHogaresLayout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlRegistrarHogaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlRegistrarHogaresLayout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(lblCedula))
                            .addGroup(pnlRegistrarHogaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel7)
                                .addComponent(jLabel6)
                                .addGroup(pnlRegistrarHogaresLayout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlRegistrarHogaresLayout.createSequentialGroup()
                                    .addGroup(pnlRegistrarHogaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblUsuario1)
                                        .addComponent(lblContraseña))
                                    .addGap(37, 37, 37)
                                    .addGroup(pnlRegistrarHogaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(pnlRegistrarHogaresLayout.createSequentialGroup()
                                    .addComponent(lblapellidos)
                                    .addGroup(pnlRegistrarHogaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(pnlRegistrarHogaresLayout.createSequentialGroup()
                                            .addGap(86, 86, 86)
                                            .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRegistrarHogaresLayout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtapellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addComponent(lblSeleccionRolYUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCedula, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRegistrarHogaresLayout.createSequentialGroup()
                                    .addComponent(lblNombres)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(btnGuardar, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addGroup(pnlRegistrarHogaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(pnlRegistrarHogaresLayout.createSequentialGroup()
                                    .addGroup(pnlRegistrarHogaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel17)
                                        .addComponent(jLabel16))
                                    .addGap(32, 32, 32)
                                    .addGroup(pnlRegistrarHogaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtNumeroVivienda)
                                        .addComponent(txtDireccion)
                                        .addComponent(cmbEstrato, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cmbBarrio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(pnlRegistrarHogaresLayout.createSequentialGroup()
                                    .addComponent(lblSeleccioneUbicacion)
                                    .addGap(64, 64, 64))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                .addComponent(jScrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlRegistrarHogaresLayout.setVerticalGroup(
            pnlRegistrarHogaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRegistrarHogaresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(19, 19, 19))
            .addGroup(pnlRegistrarHogaresLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(lblRegistrarUsuariosInterfaz)
                .addGap(46, 46, 46)
                .addGroup(pnlRegistrarHogaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlRegistrarHogaresLayout.createSequentialGroup()
                        .addComponent(lblSeleccioneUbicacion)
                        .addGap(45, 45, 45)
                        .addGroup(pnlRegistrarHogaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtNumeroVivienda, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(pnlRegistrarHogaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addGap(39, 39, 39)
                        .addGroup(pnlRegistrarHogaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cmbEstrato, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addGroup(pnlRegistrarHogaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cmbBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addComponent(lblSeleccionRolYUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addGroup(pnlRegistrarHogaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCedula)
                            .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblNombres))
                .addGap(28, 28, 28)
                .addGroup(pnlRegistrarHogaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblapellidos)
                    .addComponent(txtapellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(pnlRegistrarHogaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(34, 34, 34)
                .addGroup(pnlRegistrarHogaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(jLabel7)
                .addGap(34, 34, 34)
                .addGroup(pnlRegistrarHogaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuario1)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(pnlRegistrarHogaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblContraseña)
                    .addComponent(txtContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlRegistrarHogaresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRegistrarHogaresLayout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRegistrarHogaresLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(180, 180, 180))))
        );

        getContentPane().add(pnlRegistrarHogares, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, -1, -1));

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

    private void txtCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCedulaActionPerformed

    private void txtNombresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombresActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoActionPerformed

    private void txtCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoActionPerformed

    private void txtapellidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtapellidosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtapellidosActionPerformed

    private void cmbEstratoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEstratoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbEstratoActionPerformed

    private void cmbBarrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBarrioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbBarrioActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new P9_RecuentistaRegistrarHogares1().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnPortatiles;
    private javax.swing.JButton btnRegistrarPortatiles;
    private javax.swing.JButton btnRegistrarUsuarios;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JComboBox<String> cmbBarrio;
    private javax.swing.JComboBox<String> cmbEstrato;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JLabel lblAdministrador;
    private javax.swing.JLabel lblCedula;
    private javax.swing.JLabel lblContraseña;
    private javax.swing.JLabel lblMenu;
    private javax.swing.JLabel lblNombres;
    private javax.swing.JLabel lblRegistrarUsuariosInterfaz;
    private javax.swing.JLabel lblSeleccionRolYUsuario;
    private javax.swing.JLabel lblSeleccioneUbicacion;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lblUsuario1;
    private javax.swing.JLabel lblapellidos;
    private javax.swing.JPanel pnlLogos;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlRegistrarHogares;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtContraseña;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtNumeroVivienda;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtUsuario;
    private javax.swing.JTextField txtapellidos;
    // End of variables declaration//GEN-END:variables
}
