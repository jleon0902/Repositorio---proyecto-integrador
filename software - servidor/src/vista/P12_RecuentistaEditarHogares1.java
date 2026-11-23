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
import javax.swing.text.DocumentFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JOptionPane;


public class P12_RecuentistaEditarHogares1 extends javax.swing.JFrame {
private boolean cargandoBarrios = false;
private String numeroVivienda;
private String cedulaRecuentista;

private java.util.List<Integer> idsBarrios = new java.util.ArrayList<>();
private P11_RecuentistaInformacionFamilia pantalla11;

private controlador.P12_RecuentistaEditarHogaresControlador controlador;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(P12_RecuentistaEditarHogares1.class.getName());
    private javax.swing.JScrollPane spRegistrarHogares;

    /**
     * Creates new form iniciarSesion
     */
public P12_RecuentistaEditarHogares1() {
        initComponents();
        configurarValidaciones();

        // 1. Fijar dimensiones de la ventana principal
        this.setSize(956, 670);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        // 2. Establecer AbsoluteLayout explícito en el contenedor principal
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        // 3. Posicionar franja de logos en la cabecera
        getContentPane().add(pnlLogos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 110));

        // 4. Crear contenedor recortador (máscara) ocupando todo el ancho inferior
        javax.swing.JPanel pnlContenedor = new javax.swing.JPanel();
        pnlContenedor.setLayout(null);
        pnlContenedor.setBounds(0, 110, 915, 520);

        // Quitar el panel principal del frame y colocarlo dentro de la máscara
        getContentPane().remove(pnlEditarUsuarios1);
        pnlContenedor.add(pnlEditarUsuarios1);
        getContentPane().add(pnlContenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 915, 520));

        // Establecer el tamaño real del panel interno a 1300
        pnlEditarUsuarios1.setBounds(0, 0, 915, 1300);

        // 5. Ubicar jScrollBar1 a la derecha
        getContentPane().remove(jScrollBar1);
        getContentPane().add(jScrollBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(915, 110, 20, 520));

        // 6. Configurar el comportamiento del scrollbar con el alto real de 1300
        int altoContenido = 1300; // <--- Cambiado a 1300
        int altoVisible = 520;

        jScrollBar1.setMinimum(0);
        jScrollBar1.setMaximum(altoContenido - altoVisible);
        jScrollBar1.setValue(0);
        jScrollBar1.setUnitIncrement(20);
        jScrollBar1.setBlockIncrement(100);

        jScrollBar1.addAdjustmentListener(e -> {
            int valorScroll = e.getValue();
            pnlEditarUsuarios1.setLocation(0, -valorScroll);
            pnlContenedor.repaint();
        });

        // Habilitar desplazamiento con la rueda del ratón
        pnlContenedor.addMouseWheelListener(e -> {
            int pasos = e.getWheelRotation();
            int nuevoValor = jScrollBar1.getValue() + (pasos * 25);
            jScrollBar1.setValue(nuevoValor);
        });

        getContentPane().revalidate();
        getContentPane().repaint();
    }

private void configurarValidaciones() {

    // NÚMERO DE VIVIENDA.
    configurarCampo(
            txtNumeroVivienda1,
            "[0-9]*",
            "El número de vivienda solo permite números."
    );

    // CÉDULA.
    configurarCampo(
            txtCedula1,
            "[0-9]*",
            "La cédula solo permite números."
    );

    // TELÉFONO.
    configurarCampo(
            txtTelefono1,
            "[0-9]*",
            "El teléfono solo permite números."
    );

    // DIRECCIÓN.
    configurarCampo(
            txtDireccion1,
            "[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s#.,°\\-/]*",
            "La dirección contiene caracteres no permitidos."
    );

    // NOMBRES.
    configurarCampo(
            txtNombres,
            "[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]*",
            "Los nombres solo permiten letras."
    );

    // APELLIDOS.
    configurarCampo(
            txtapellidos,
            "[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]*",
            "Los apellidos solo permiten letras."
    );

    // USUARIO.
    configurarCampo(
            txtUsuario1,
            "[a-zA-Z0-9]*",
            "El usuario solo permite letras y números."
    );

    // CONTRASEÑA.
    configurarCampo(
            txtContraseña1,
            "[a-zA-Z0-9]*",
            "La contraseña solo permite letras y números."
    );
}

private void configurarCampo(
        javax.swing.JTextField campo,
        String patron,
        String mensaje) {

    campo.addKeyListener(new java.awt.event.KeyAdapter() {

        @Override
        public void keyTyped(java.awt.event.KeyEvent evt) {

            char caracter = evt.getKeyChar();

            // Permitir teclas de control.
            if (Character.isISOControl(caracter)) {
                return;
            }

            String textoNuevo = campo.getText()
                    + caracter;

            if (!textoNuevo.matches(patron)) {

                evt.consume();

                mostrarError(mensaje);
            }
        }
    });
}

private void aplicarFiltro(
        javax.swing.JTextField campo,
        String caracteresPermitidos,
        int longitudMaxima) {

    AbstractDocument documento =
            (AbstractDocument) campo.getDocument();

    documento.setDocumentFilter(new DocumentFilter() {

        private boolean mostrarAdvertencia = true;

        private boolean esValido(String texto) {

            return texto.matches(
                    caracteresPermitidos + "*"
            );
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
                    0, fb.getDocument().getLength()
            );

            String nuevo = new StringBuilder(actual)
                    .insert(offset, texto)
                    .toString();

            if (!esValido(texto)) {

                if (mostrarAdvertencia) {
                    mostrarAdvertencia = false;

                    JOptionPane.showMessageDialog(
                            P12_RecuentistaEditarHogares1.this,
                            "Este campo contiene caracteres no permitidos.",
                            "Advertencia",
                            JOptionPane.WARNING_MESSAGE
                    );

                    mostrarAdvertencia = true;
                }

                return;
            }

            if (nuevo.length() > longitudMaxima) {
                return;
            }

            super.insertString(fb, offset, texto, attr);
        }

        @Override
        public void replace(
                FilterBypass fb,
                int offset,
                int length,
                String texto,
                AttributeSet attrs)
                throws BadLocationException {

            if (texto == null) {
                texto = "";
            }

            String actual = fb.getDocument().getText(
                    0, fb.getDocument().getLength()
            );

            String nuevo = new StringBuilder(actual)
                    .replace(offset, offset + length, texto)
                    .toString();

            if (!esValido(texto)) {

                JOptionPane.showMessageDialog(
                        P12_RecuentistaEditarHogares1.this,
                        "Este campo contiene caracteres no permitidos.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (nuevo.length() > longitudMaxima) {
                return;
            }

            super.replace(fb, offset, length, texto, attrs);
        }
    });
}


private boolean validarCampos() {

    String numero = txtNumeroVivienda1.getText().trim();
    String cedula = txtCedula1.getText().trim();
    String telefono = txtTelefono1.getText().trim();
    String direccion = txtDireccion1.getText().trim();

    String nombres = txtNombres.getText().trim();
    String apellidos = txtapellidos.getText().trim();

    String usuario = txtUsuario1.getText().trim();
    String contrasena = txtContraseña1.getText().trim();

    String correo = txtCorreo1.getText().trim();

    if (!numero.matches("[0-9]{3,10}")) {
        mostrarError("El número de vivienda debe tener entre 3 y 10 números.");
        txtNumeroVivienda1.requestFocus();
        return false;
    }

    if (cedula.isEmpty() || !cedula.matches("[0-9]+")) {
        mostrarError("La cédula debe contener solamente números.");
        txtCedula1.requestFocus();
        return false;
    }

    if (telefono.isEmpty() || !telefono.matches("[0-9]+")) {
        mostrarError("El teléfono debe contener solamente números.");
        txtTelefono1.requestFocus();
        return false;
    }

    if (direccion.isEmpty()) {
        mostrarError("Ingrese la dirección del hogar.");
        txtDireccion1.requestFocus();
        return false;
    }

    if (nombres.isEmpty()
            || !nombres.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+")) {
        mostrarError("Los nombres solamente deben contener letras.");
        txtNombres.requestFocus();
        return false;
    }

    if (apellidos.isEmpty()
            || !apellidos.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+")) {
        mostrarError("Los apellidos solamente deben contener letras.");
        txtapellidos.requestFocus();
        return false;
    }

    if (!usuario.matches("[a-zA-Z0-9]{4,10}")) {
        mostrarError("El usuario debe tener entre 4 y 10 caracteres y contener solamente letras y números.");
        txtUsuario1.requestFocus();
        return false;
    }

    if (!contrasena.matches("[a-zA-Z0-9]{4,10}")) {
        mostrarError("La contraseña debe tener entre 4 y 10 caracteres y contener solamente letras y números.");
        txtContraseña1.requestFocus();
        return false;
    }

    if (!correo.isEmpty()
            && !correo.matches(
                    "^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {

        mostrarError("Ingrese un correo válido. Ejemplo: usuario@gmail.com");
        txtCorreo1.requestFocus();
        return false;
    }

    return true;
}
public P12_RecuentistaEditarHogares1(
        String numeroVivienda,
        String cedulaRecuentista,
        P11_RecuentistaInformacionFamilia pantalla11) {

    initComponents();
     configurarValidaciones();
configurarEstratos();
    this.numeroVivienda = numeroVivienda;
    this.cedulaRecuentista = cedulaRecuentista;
    this.pantalla11 = pantalla11;

    this.setSize(956, 670);
    this.setResizable(false);
    this.setLocationRelativeTo(null);

    getContentPane().setLayout(
            new org.netbeans.lib.awtextra.AbsoluteLayout()
    );

    getContentPane().add(
            pnlLogos,
            new org.netbeans.lib.awtextra.AbsoluteConstraints(
                    0, 0, 940, 110
            )
    );

    javax.swing.JPanel pnlContenedor = new javax.swing.JPanel();
    pnlContenedor.setLayout(null);
    pnlContenedor.setBounds(0, 0, 915, 520);

    getContentPane().remove(pnlEditarUsuarios1);
    pnlContenedor.add(pnlEditarUsuarios1);

    getContentPane().add(
            pnlContenedor,
            new org.netbeans.lib.awtextra.AbsoluteConstraints(
                    0, 110, 915, 520
            )
    );

    pnlEditarUsuarios1.setBounds(0, 0, 915, 1300);

    getContentPane().remove(jScrollBar1);

    getContentPane().add(
            jScrollBar1,
            new org.netbeans.lib.awtextra.AbsoluteConstraints(
                    915, 110, 20, 520
            )
    );

    int altoContenido = 1300;
    int altoVisible = 520;

    jScrollBar1.setMinimum(0);
    jScrollBar1.setMaximum(altoContenido - altoVisible);
    jScrollBar1.setValue(0);
    jScrollBar1.setUnitIncrement(20);
    jScrollBar1.setBlockIncrement(100);

    jScrollBar1.addAdjustmentListener(e -> {
        pnlEditarUsuarios1.setLocation(
                0, -e.getValue()
        );
        pnlContenedor.repaint();
    });

    pnlContenedor.addMouseWheelListener(e -> {
        jScrollBar1.setValue(
                jScrollBar1.getValue() + e.getWheelRotation() * 25
        );
    });


controlador = new controlador.P12_RecuentistaEditarHogaresControlador(
        this,
        numeroVivienda,
        cedulaRecuentista,
        pantalla11
);

// Escuchar los cambios del combo de barrios.
cmbBarrio.addActionListener(e -> {

    if (cargandoBarrios || controlador == null) {
        return;
    }

    int id = obtenerIdBarrioSeleccionado();

    if (id == -1) {

        controlador.seleccionarOtra();

    } else {

        controlador.actualizarBarrioSeleccionado(id);
    }
});

// Cargar la información del hogar.
controlador.cargarInformacion();


    getContentPane().revalidate();
    getContentPane().repaint();
}



public void configurarListas() {

    cmbEstrato.removeAllItems();

    for (int i = 1; i <= 6; i++) {
        cmbEstrato.addItem(String.valueOf(i));
    }

    cmbBarrio.removeAllItems();
}
private void accionCancelar() {
    int respuesta = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de que desea cancelar la edición?",
            "Confirmar cancelación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
    );

    if (respuesta == JOptionPane.YES_OPTION) {
        P11_RecuentistaInformacionFamilia pantalla11 =
                new P11_RecuentistaInformacionFamilia();

        pantalla11.setLocationRelativeTo(this);
        pantalla11.setVisible(true);
        this.dispose();
    }
}



public void cargarBarrios(
        java.util.List<Object[]> barrios,
        int idBarrioSeleccionado) {

    cargandoBarrios = true;

    try {

        cmbBarrio.removeAllItems();
        idsBarrios.clear();

        for (Object[] barrio : barrios) {

            int id = Integer.parseInt(
                    barrio[0].toString()
            );

            String nombre = barrio[1].toString();

            idsBarrios.add(id);
            cmbBarrio.addItem(nombre);
        }

        // Agregar la opción especial.
        idsBarrios.add(-1);
        cmbBarrio.addItem("Otra...");

        // Seleccionar el barrio correspondiente por su ID.
        int posicion = idsBarrios.indexOf(
                idBarrioSeleccionado
        );

        if (posicion >= 0) {
            cmbBarrio.setSelectedIndex(posicion);
        }

    } finally {
        cargandoBarrios = false;
    }
}



// SOLICITAR EL NOMBRE DEL NUEVO BARRIO.
public String pedirNombreBarrio() {

    return JOptionPane.showInputDialog(
            this,
            "Ingrese el nombre del nuevo barrio:",
            "Registrar barrio",
            JOptionPane.QUESTION_MESSAGE
    );
}


// SELECCIONAR UN BARRIO POR SU ID.
public void seleccionarBarrio(int idBarrio) {

    cargandoBarrios = true;

    try {

        int posicion = idsBarrios.indexOf(idBarrio);

        if (posicion >= 0) {
            cmbBarrio.setSelectedIndex(posicion);
        }

    } finally {
        cargandoBarrios = false;
    }
}


// MOSTRAR ADVERTENCIAS.
public void mostrarAdvertencia(String mensaje) {

    JOptionPane.showMessageDialog(
            this,
            mensaje,
            "Advertencia",
            JOptionPane.WARNING_MESSAGE
    );
}


public int obtenerIdBarrioSeleccionado() {

    int posicion = cmbBarrio.getSelectedIndex();

    if (posicion < 0 || posicion >= idsBarrios.size()) {
        return -1;
    }

    return idsBarrios.get(posicion);
}


public void cargarEstratos() {

    cmbEstrato.removeAllItems();

    for (int i = 1; i <= 6; i++) {
        cmbEstrato.addItem(String.valueOf(i));
    }
}

public String obtenerBarrioSeleccionado() {
    return (String) cmbBarrio.getSelectedItem();
}


public String obtenerEstratoSeleccionado() {
    return (String) cmbEstrato.getSelectedItem();
}

public void configurarEstratos() {

    cmbEstrato.removeAllItems();

    for (int i = 1; i <= 6; i++) {
        cmbEstrato.addItem(String.valueOf(i));
    }
}





public void mostrarDatos(Object[] datos) {

    txtNumeroVivienda1.setText(numeroVivienda);
    txtDireccion1.setText(String.valueOf(datos[0]));

    cmbEstrato.setSelectedItem(String.valueOf(datos[2]));

    txtCedula1.setText(String.valueOf(datos[6]));
    txtNombres.setText(String.valueOf(datos[7]));
    txtapellidos.setText(String.valueOf(datos[8]));
    txtTelefono1.setText(String.valueOf(datos[9]));

    txtCorreo1.setText(
            datos[10] == null ? "" : String.valueOf(datos[10])
    );

    txtUsuario1.setText(String.valueOf(datos[11]));
    txtContraseña1.setText(String.valueOf(datos[12]));
}



public void configurarEdicion(boolean encuestaIniciada) {

    // Número de vivienda.
    txtNumeroVivienda1.setEditable(true);

    // Datos del hogar.
    txtDireccion1.setEditable(true);

    // Estrato y barrio son listas desplegables.
    cmbEstrato.setEnabled(true);
    cmbBarrio.setEnabled(true);

    // Datos del representante.
    txtCedula1.setEditable(!encuestaIniciada);
    txtUsuario1.setEditable(!encuestaIniciada);
    txtContraseña1.setEditable(!encuestaIniciada);

    txtNombres.setEditable(true);
    txtapellidos.setEditable(true);
    txtTelefono1.setEditable(true);
    txtCorreo1.setEditable(true);
}

public void mostrarError(String mensaje) {
    JOptionPane.showMessageDialog(
            this, mensaje, "Error",
            JOptionPane.ERROR_MESSAGE
    );
}

public void mostrarMensaje(String mensaje) {
    JOptionPane.showMessageDialog(
            this, mensaje, "Información",
            JOptionPane.INFORMATION_MESSAGE
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
        pnlEditarUsuarios1 = new javax.swing.JPanel();
        lblEditarHogares = new javax.swing.JLabel();
        lblCedula = new javax.swing.JLabel();
        lblSeleccioneUbicacion1 = new javax.swing.JLabel();
        lblSeleccionRolYUsuario1 = new javax.swing.JLabel();
        lblUsuario2 = new javax.swing.JLabel();
        lblContraseña1 = new javax.swing.JLabel();
        txtCedula1 = new javax.swing.JTextField();
        txtUsuario1 = new javax.swing.JTextField();
        txtContraseña1 = new javax.swing.JTextField();
        btnCancelar1 = new javax.swing.JButton();
        btnGuardar1 = new javax.swing.JButton();
        lblNumeroVivienda = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        lblEstrato = new javax.swing.JLabel();
        lblBarrio = new javax.swing.JLabel();
        txtNumeroVivienda1 = new javax.swing.JTextField();
        txtDireccion1 = new javax.swing.JTextField();
        lblTelefono = new javax.swing.JLabel();
        lblCorreo = new javax.swing.JLabel();
        txtTelefono1 = new javax.swing.JTextField();
        txtCorreo1 = new javax.swing.JTextField();
        lblAsignarUsuario = new javax.swing.JLabel();
        txtapellidos = new javax.swing.JTextField();
        lblapellidos = new javax.swing.JLabel();
        txtNombres = new javax.swing.JTextField();
        lblNombres = new javax.swing.JLabel();
        jScrollBar1 = new javax.swing.JScrollBar();
        cmbBarrio = new javax.swing.JComboBox<>();
        cmbEstrato = new javax.swing.JComboBox<>();

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

        pnlEditarUsuarios1.setBackground(new java.awt.Color(255, 255, 255));
        pnlEditarUsuarios1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));
        pnlEditarUsuarios1.setPreferredSize(new java.awt.Dimension(700, 1200));

        lblEditarHogares.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        lblEditarHogares.setText("Editar hogares");

        lblCedula.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblCedula.setText("Cédula:");

        lblSeleccioneUbicacion1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblSeleccioneUbicacion1.setText("Ingrese la información del hogar");

        lblSeleccionRolYUsuario1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblSeleccionRolYUsuario1.setText("<html>Informacion del representante de ese hogar<br>(será el encargado de llenar la encuesta)</html>");

        lblUsuario2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblUsuario2.setText("Usuario:");

        lblContraseña1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblContraseña1.setText("Contraseña:");

        txtCedula1.addActionListener(this::txtCedula1ActionPerformed);

        btnCancelar1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnCancelar1.setText("Cancelar");
        btnCancelar1.addActionListener(this::btnCancelar1ActionPerformed);

        btnGuardar1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnGuardar1.setText("Guardar");
        btnGuardar1.addActionListener(this::btnGuardar1ActionPerformed);

        lblNumeroVivienda.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblNumeroVivienda.setText("Número vivienda:");

        lblDireccion.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblDireccion.setText("Dirección:");

        lblEstrato.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblEstrato.setText("Estrato:");

        lblBarrio.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblBarrio.setText("Barrio:");

        lblTelefono.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblTelefono.setText("Teléfono:");

        lblCorreo.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblCorreo.setText("Correo:");

        txtTelefono1.addActionListener(this::txtTelefono1ActionPerformed);

        txtCorreo1.addActionListener(this::txtCorreo1ActionPerformed);

        lblAsignarUsuario.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblAsignarUsuario.setText("Usuario y contraseña del representante");

        txtapellidos.addActionListener(this::txtapellidosActionPerformed);

        lblapellidos.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblapellidos.setText("Apellidos:");

        txtNombres.addActionListener(this::txtNombresActionPerformed);

        lblNombres.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblNombres.setText("Nombres:");

        cmbBarrio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbEstrato.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pnlEditarUsuarios1Layout = new javax.swing.GroupLayout(pnlEditarUsuarios1);
        pnlEditarUsuarios1.setLayout(pnlEditarUsuarios1Layout);
        pnlEditarUsuarios1Layout.setHorizontalGroup(
            pnlEditarUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarUsuarios1Layout.createSequentialGroup()
                .addGroup(pnlEditarUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEditarUsuarios1Layout.createSequentialGroup()
                        .addGap(345, 345, 345)
                        .addComponent(lblEditarHogares))
                    .addGroup(pnlEditarUsuarios1Layout.createSequentialGroup()
                        .addGap(151, 151, 151)
                        .addGroup(pnlEditarUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlEditarUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pnlEditarUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSeleccioneUbicacion1)
                                    .addGroup(pnlEditarUsuarios1Layout.createSequentialGroup()
                                        .addGroup(pnlEditarUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblEstrato)
                                            .addComponent(lblBarrio)
                                            .addComponent(lblDireccion)
                                            .addComponent(lblNumeroVivienda))
                                        .addGap(32, 32, 32)
                                        .addGroup(pnlEditarUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtNumeroVivienda1, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                                            .addComponent(txtDireccion1)
                                            .addComponent(cmbEstrato, 0, 334, Short.MAX_VALUE)
                                            .addComponent(cmbBarrio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addComponent(btnGuardar1, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addComponent(lblSeleccionRolYUsuario1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(pnlEditarUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlEditarUsuarios1Layout.createSequentialGroup()
                                        .addComponent(lblCedula)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtCedula1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlEditarUsuarios1Layout.createSequentialGroup()
                                        .addComponent(lblNombres)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                                        .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(pnlEditarUsuarios1Layout.createSequentialGroup()
                                .addGroup(pnlEditarUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(pnlEditarUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblAsignarUsuario)
                                        .addGroup(pnlEditarUsuarios1Layout.createSequentialGroup()
                                            .addGroup(pnlEditarUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(lblUsuario2)
                                                .addComponent(lblContraseña1))
                                            .addGap(37, 37, 37)
                                            .addGroup(pnlEditarUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txtContraseña1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtUsuario1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(pnlEditarUsuarios1Layout.createSequentialGroup()
                                            .addGroup(pnlEditarUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEditarUsuarios1Layout.createSequentialGroup()
                                                    .addComponent(lblCorreo)
                                                    .addGap(98, 98, 98))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEditarUsuarios1Layout.createSequentialGroup()
                                                    .addComponent(lblapellidos)
                                                    .addGap(87, 87, 87)))
                                            .addGroup(pnlEditarUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txtapellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtTelefono1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtCorreo1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(lblTelefono))
                                    .addGroup(pnlEditarUsuarios1Layout.createSequentialGroup()
                                        .addComponent(btnCancelar1)
                                        .addGap(340, 340, 340)))
                                .addGap(70, 70, 70)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 268, Short.MAX_VALUE)
                .addComponent(jScrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlEditarUsuarios1Layout.setVerticalGroup(
            pnlEditarUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarUsuarios1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(lblEditarHogares)
                .addGap(29, 29, 29)
                .addComponent(lblSeleccioneUbicacion1)
                .addGap(45, 45, 45)
                .addGroup(pnlEditarUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNumeroVivienda)
                    .addComponent(txtNumeroVivienda1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(pnlEditarUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDireccion1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDireccion))
                .addGap(39, 39, 39)
                .addGroup(pnlEditarUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEstrato)
                    .addComponent(cmbEstrato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(pnlEditarUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBarrio)
                    .addComponent(cmbBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addComponent(lblSeleccionRolYUsuario1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(pnlEditarUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCedula1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCedula))
                .addGap(34, 34, 34)
                .addGroup(pnlEditarUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombres)
                    .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(pnlEditarUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblapellidos)
                    .addComponent(txtapellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(pnlEditarUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefono1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTelefono))
                .addGap(33, 33, 33)
                .addGroup(pnlEditarUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCorreo)
                    .addComponent(txtCorreo1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addComponent(lblAsignarUsuario)
                .addGap(34, 34, 34)
                .addGroup(pnlEditarUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuario2)
                    .addComponent(txtUsuario1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(pnlEditarUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblContraseña1)
                    .addComponent(txtContraseña1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlEditarUsuarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(260, 260, 260))
            .addGroup(pnlEditarUsuarios1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 1272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(pnlEditarUsuarios1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 940, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCedula1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCedula1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCedula1ActionPerformed

    private void btnCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar1ActionPerformed
        // TODO add your handling code here:
           if (controlador != null) {
    controlador.cancelar();
}
    }//GEN-LAST:event_btnCancelar1ActionPerformed

    private void btnGuardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar1ActionPerformed
        // TODO add your handling code here:
        
  try {

        String numeroNuevo = txtNumeroVivienda1.getText().trim();
        String direccion = txtDireccion1.getText().trim();

        String cedula = txtCedula1.getText().trim();
        String nombre = txtNombres.getText().trim();
        String apellido = txtapellidos.getText().trim();

        String telefono = txtTelefono1.getText().trim();
        String correo = txtCorreo1.getText().trim();

        String usuario = txtUsuario1.getText().trim();
        String contrasena = txtContraseña1.getText().trim();

        // VALIDAR NÚMERO DE VIVIENDA.
        if (!numeroNuevo.matches("[0-9]{3,10}")) {
            mostrarError(
                    "El número de vivienda debe contener entre 3 y 10 números."
            );
            return;
        }

        // VALIDAR CÉDULA.
        if (!cedula.matches("[0-9]+")) {
            mostrarError("La cédula solo permite números.");
            return;
        }

        // VALIDAR TELÉFONO.
        if (!telefono.matches("[0-9]+")) {
            mostrarError("El teléfono solo permite números.");
            return;
        }

        // VALIDAR DIRECCIÓN.
        if (direccion.isEmpty()
                || !direccion.matches("[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s#.,°\\-/]+")) {

            mostrarError("Ingrese una dirección válida.");
            return;
        }

        // VALIDAR NOMBRES.
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            mostrarError("Los nombres solo permiten letras.");
            return;
        }

        // VALIDAR APELLIDOS.
        if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            mostrarError("Los apellidos solo permiten letras.");
            return;
        }

        // VALIDAR USUARIO.
        if (!usuario.matches("[a-zA-Z0-9]{4,10}")) {
            mostrarError(
                    "El usuario debe tener entre 4 y 10 caracteres y solo permite letras y números."
            );
            return;
        }

        // VALIDAR CONTRASEÑA.
        if (!contrasena.matches("[a-zA-Z0-9]{4,10}")) {
            mostrarError(
                    "La contraseña debe tener entre 4 y 10 caracteres y solo permite letras y números."
            );
            return;
        }

        // VALIDAR CORREO.
        if (!correo.isEmpty()
                && !correo.matches(
                        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {

            mostrarError("Ingrese un correo electrónico válido.");
            return;
        }

        // VALIDAR ESTRATO.
        if (cmbEstrato.getSelectedItem() == null) {
            mostrarError("Seleccione un estrato.");
            return;
        }

        int estrato = Integer.parseInt(
                cmbEstrato.getSelectedItem().toString()
        );

        // VALIDAR BARRIO.
        int idBarrio = obtenerIdBarrioSeleccionado();

        if (idBarrio == -1) {
            mostrarError("Seleccione un barrio.");
            return;
        }

        // GUARDAR LOS CAMBIOS.
        controlador.guardar(
                numeroNuevo,
                direccion,
                estrato,
                idBarrio,
                cedula,
                nombre,
                apellido,
                telefono,
                correo,
                usuario,
                contrasena
        );

    } catch (NumberFormatException e) {

        mostrarError("Verifique los datos numéricos ingresados.");

    }
    }//GEN-LAST:event_btnGuardar1ActionPerformed

    private void txtTelefono1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefono1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefono1ActionPerformed

    private void txtCorreo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreo1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreo1ActionPerformed

    private void txtapellidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtapellidosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtapellidosActionPerformed

    private void txtNombresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombresActionPerformed

    /**
     * @param args the command line arguments
     */
public static void main(String args[]) {
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

        java.awt.EventQueue.invokeLater(() -> new P12_RecuentistaEditarHogares1().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar1;
    private javax.swing.JButton btnGuardar1;
    private javax.swing.JComboBox<String> cmbBarrio;
    private javax.swing.JComboBox<String> cmbEstrato;
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
    private javax.swing.JLabel lblAsignarUsuario;
    private javax.swing.JLabel lblBarrio;
    private javax.swing.JLabel lblCedula;
    private javax.swing.JLabel lblContraseña1;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblEditarHogares;
    private javax.swing.JLabel lblEstrato;
    private javax.swing.JLabel lblNombres;
    private javax.swing.JLabel lblNumeroVivienda;
    private javax.swing.JLabel lblSeleccionRolYUsuario1;
    private javax.swing.JLabel lblSeleccioneUbicacion1;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lblUsuario2;
    private javax.swing.JLabel lblapellidos;
    private javax.swing.JPanel pnlEditarUsuarios1;
    private javax.swing.JPanel pnlLogos;
    private javax.swing.JTextField txtCedula1;
    private javax.swing.JTextField txtContraseña1;
    private javax.swing.JTextField txtCorreo1;
    private javax.swing.JTextField txtDireccion1;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtNumeroVivienda1;
    private javax.swing.JTextField txtTelefono1;
    private javax.swing.JTextField txtUsuario1;
    private javax.swing.JTextField txtapellidos;
    // End of variables declaration//GEN-END:variables
}