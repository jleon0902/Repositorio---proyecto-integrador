/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class P4_AdministradorEditarUsuarios extends javax.swing.JFrame {

    private controlador.P4_AdministradorEditarUsuariosControlador controlador;

    private boolean cargandoDatos = false;

    private List<modelo.Ubicacion> listaRegiones = new ArrayList<>();
    private List<modelo.Ubicacion> listaDepartamentos = new ArrayList<>();
    private List<modelo.Ubicacion> listaMunicipios = new ArrayList<>();
    private Runnable accionCancelar;
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(
                    P4_AdministradorEditarUsuarios.class.getName()
            );

public P4_AdministradorEditarUsuarios() {

    initComponents();

    aplicarValidaciones();
}

public void configurarControlador(
        controlador.P4_AdministradorEditarUsuariosControlador controlador) {

    this.controlador = controlador;

    asignarEventos();
}



private void asignarEventos() {

    btnCancelar.addActionListener(e -> {

        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de que desea cancelar la edición?",
                "Confirmar cancelación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (respuesta == JOptionPane.YES_OPTION) {

            if (controlador != null) {
                controlador.cancelar();
            }
        }
    });

    btnGuardar.addActionListener(e -> {
        if (controlador != null) {
            controlador.guardarCambios();
        }
    });

    cmbRegion.addActionListener(e -> {
        if (!cargandoDatos && controlador != null) {
            controlador.cambiarRegion();
        }
    });

    cmbDepartamento.addActionListener(e -> {
        if (!cargandoDatos && controlador != null) {
            controlador.cambiarDepartamento();
        }
    });
}

public String obtenerCedula() {
    return txtCedula.getText().trim();
}

public String obtenerNombre() {
    return txtNombres.getText().trim();
}

public String obtenerApellido() {
    return txtApellidos.getText().trim();
}

public String obtenerUsuario() {
    return txtUsuario.getText().trim();
}

public String obtenerContrasena() {
    return txtContraseña.getText().trim();
}

public String obtenerRol() {
    return (String) cmbRoles.getSelectedItem();
}

public int obtenerIndiceRegion() {
    return cmbRegion.getSelectedIndex();
}

public int obtenerIndiceDepartamento() {
    return cmbDepartamento.getSelectedIndex();
}

public java.util.List<modelo.Ubicacion> obtenerRegiones() {
    return listaRegiones;
}

public java.util.List<modelo.Ubicacion> obtenerDepartamentos() {
    return listaDepartamentos;
}

public Integer obtenerIdMunicipioSeleccionado() {

    int indice = cmbMunicipio.getSelectedIndex();

    if (indice < 0 || indice >= listaMunicipios.size()) {
        return null;
    }

    return listaMunicipios.get(indice).getId();
}

public void mostrarDatosUsuario(modelo.Usuario usuario) {

    cargandoDatos = true;

    txtCedula.setText(usuario.getCedula());
    txtNombres.setText(usuario.getNombre());
    txtApellidos.setText(usuario.getApellido());
    txtUsuario.setText(usuario.getUsuario());
    txtContraseña.setText(usuario.getContrasena());

    cmbRoles.setSelectedItem(usuario.getRol());

    cargandoDatos = false;
}

public void mostrarRoles(java.util.List<String> roles) {

    cmbRoles.removeAllItems();

    for (String rol : roles) {
        cmbRoles.addItem(rol);
    }
}

public void mostrarRegiones(java.util.List<modelo.Ubicacion> regiones) {

    cargandoDatos = true;

    listaRegiones = regiones;
    cmbRegion.removeAllItems();

    for (modelo.Ubicacion region : regiones) {
        cmbRegion.addItem(region.toString());
    }

    cargandoDatos = false;
}

public void mostrarDepartamentos(
        java.util.List<modelo.Ubicacion> departamentos) {

    cargandoDatos = true;

    listaDepartamentos = departamentos;
    cmbDepartamento.removeAllItems();

    for (modelo.Ubicacion departamento : departamentos) {
        cmbDepartamento.addItem(departamento.toString());
    }

    cargandoDatos = false;
}

public void mostrarMunicipios(
        java.util.List<modelo.Ubicacion> municipios) {

    cargandoDatos = true;

    listaMunicipios = municipios;
    cmbMunicipio.removeAllItems();

    for (modelo.Ubicacion municipio : municipios) {
        cmbMunicipio.addItem(municipio.toString());
    }

    cargandoDatos = false;
}

public void limpiarMunicipios() {

    cargandoDatos = true;

    listaMunicipios.clear();
    cmbMunicipio.removeAllItems();

    cargandoDatos = false;
}

public void cargarDepartamentos(
        java.util.List<modelo.Ubicacion> departamentos) {
    mostrarDepartamentos(departamentos);
}

public void cargarMunicipios(
        java.util.List<modelo.Ubicacion> municipios) {
    mostrarMunicipios(municipios);
}

public void seleccionarRegion(int id) {
    seleccionarUbicacion(cmbRegion, listaRegiones, id);
}

public void seleccionarDepartamento(int id) {
    seleccionarUbicacion(cmbDepartamento, listaDepartamentos, id);
}

public void seleccionarMunicipio(int id) {
    seleccionarUbicacion(cmbMunicipio, listaMunicipios, id);
}

private void seleccionarUbicacion(
        javax.swing.JComboBox<String> combo,
        java.util.List<modelo.Ubicacion> lista,
        int id) {

    for (int i = 0; i < lista.size(); i++) {
        if (lista.get(i).getId() == id) {
            combo.setSelectedIndex(i);
            return;
        }
    }
}

public void bloquearCamposSegunRol(String rol) {

    if (rol == null) {
        return;
    }

    String rolNormalizado = java.text.Normalizer.normalize(
            rol.trim().toUpperCase(),
            java.text.Normalizer.Form.NFD
    ).replaceAll("\\p{M}", "").replace("_", " ");

    boolean bloquearTodo =
            rolNormalizado.equals("ADMINISTRADOR")
            || rolNormalizado.equals("CRITICO DIGITADOR");

    boolean bloquearRol =
            bloquearTodo
            || rolNormalizado.equals("ENCUESTADOR")
            || rolNormalizado.equals("RECUENTISTA");

    cmbRoles.setEnabled(!bloquearRol);

    cmbRegion.setEnabled(!bloquearTodo);
    cmbDepartamento.setEnabled(!bloquearTodo);
    cmbMunicipio.setEnabled(!bloquearTodo);
}

public boolean confirmarActualizacion() {

    return JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de guardar los cambios del usuario?",
            "Confirmar actualización",
            JOptionPane.YES_NO_OPTION
    ) == JOptionPane.YES_OPTION;
}

public void mostrarError(String mensaje) {
    JOptionPane.showMessageDialog(
            this, mensaje, "Error", JOptionPane.ERROR_MESSAGE
    );
}

public void mostrarAdvertencia(String mensaje) {
    JOptionPane.showMessageDialog(
            this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE
    );
}

public void mostrarInformacion(String mensaje) {
    JOptionPane.showMessageDialog(
            this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE
    );
}




private void aplicarValidaciones() {

    // CÉDULA: únicamente números, máximo 10.
    configurarFiltro(
            txtCedula,
            10,
            "\\d*",
            "La cédula solo permite números del 0 al 9. "
            + "No se permiten letras, espacios ni símbolos."
    );

    // NOMBRES: letras, tildes, ñ, espacios y guiones.
    configurarFiltro(
            txtNombres,
            50,
            "[\\p{L}\\p{M} '-]*",
            "Los nombres solo permiten letras, incluyendo tildes "
            + "y ñ, espacios y guiones (-). "
            + "No se permiten números ni otros símbolos."
    );

    // APELLIDOS: letras, tildes, ñ, espacios y guiones.
    configurarFiltro(
            txtApellidos,
            50,
            "[\\p{L}\\p{M} '-]*",
            "Los apellidos solo permiten letras, incluyendo tildes "
            + "y ñ, espacios y guiones (-). "
            + "No se permiten números ni otros símbolos."
    );

    // USUARIO: máximo 20 caracteres.
    configurarFiltro(
            txtUsuario,
            20,
            "[a-zA-Z0-9._]*",
            "El usuario solo permite letras sin tildes, números, "
            + "puntos (.) y guiones bajos (_). "
            + "No se permiten espacios ni otros símbolos."
    );

    // CONTRASEÑA: máximo 64 caracteres.
    configurarFiltro(
            txtContraseña,
            64,
            "[a-zA-Z0-9@#$%&*!?_.-]*",
            "La contraseña solo permite letras sin tildes, números "
            + "y los símbolos (@ # $ % & * ! ? _ - .). "
            + "No se permiten espacios ni otros símbolos."
    );
}



public boolean validarUsuarioYContrasena() {

    String usuario = obtenerUsuario();
    String contrasena = obtenerContrasena();

    String mensaje = "";

    // CASO 1: AMBOS CAMPOS ESTÁN VACÍOS
    if (usuario.isEmpty() && contrasena.isEmpty()) {

        mensaje = "-Usuario: este campo es obligatorio y no puede estar vacío."
                + "\n\n- Contraseña: este campo es obligatorio y no puede estar vacío.";

    } else {

        // CASO 2: SOLAMENTE EL USUARIO ESTÁ VACÍO
        if (usuario.isEmpty()) {

            mensaje += "-Usuario: este campo es obligatorio y no puede estar vacío.";

        } else if (usuario.length() < 5 || usuario.length() > 20
                || !usuario.matches("[a-zA-Z0-9._]+")) {

            mensaje += "-Usuario: debe tener entre 5 y 20 caracteres "
                    + "y solo puede contener letras sin tildes, números, "
                    + "punto (.) y guion bajo (_).";
        }

        // CASO 3: SOLAMENTE LA CONTRASEÑA ESTÁ VACÍA
        if (contrasena.isEmpty()) {

            if (!mensaje.isEmpty()) {
                mensaje += "\n\n- ";
            }

            mensaje += "Contraseña: este campo es obligatorio y no puede estar vacío.";

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






private void configurarFiltro(
        javax.swing.JTextField campo,
        int limite,
        String patron,
        String mensaje) {

    ((javax.swing.text.AbstractDocument) campo.getDocument())
            .setDocumentFilter(new javax.swing.text.DocumentFilter() {

        @Override
        public void insertString(
                FilterBypass fb,
                int offset,
                String texto,
                javax.swing.text.AttributeSet attr)
                throws javax.swing.text.BadLocationException {

            replace(fb, offset, 0, texto, attr);
        }

        @Override
        public void replace(
                FilterBypass fb,
                int offset,
                int length,
                String texto,
                javax.swing.text.AttributeSet attr)
                throws javax.swing.text.BadLocationException {

            String actual = fb.getDocument().getText(
                    0, fb.getDocument().getLength());

            String nuevoTexto = actual.substring(0, offset)
                    + (texto == null ? "" : texto)
                    + actual.substring(offset + length);

            boolean caracteresValidos = nuevoTexto.matches(patron);
            boolean longitudValida = nuevoTexto.length() <= limite;

            // Si ambas condiciones se cumplen, permite escribir.
            if (caracteresValidos && longitudValida) {

                super.replace(fb, offset, length, texto, attr);

            } else {

                String mensajeError = "";

                if (!caracteresValidos && !longitudValida) {

                    mensajeError = mensaje
                            + "\n\nAdemás, este campo no puede superar "
                            + limite + " caracteres.";

                } else if (!caracteresValidos) {

                    mensajeError = mensaje;

                } else if (!longitudValida) {

                    mensajeError = "Este campo no puede superar "
                            + limite + " caracteres.";

                }

                JOptionPane.showMessageDialog(
                        P4_AdministradorEditarUsuarios.this,
                        mensajeError,
                        "Dato no permitido",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        }
    });
}



private String obtenerTexto(
        javax.swing.text.DocumentFilter.FilterBypass fb,
        int offset,
        int length,
        String texto) throws javax.swing.text.BadLocationException {

    String actual = fb.getDocument().getText(
            0, fb.getDocument().getLength());

    return actual.substring(0, offset)
            + (texto == null ? "" : texto)
            + actual.substring(offset + length);
}

private void configurarLimite(
        javax.swing.JTextField campo,
        int limite,
        String nombreCampo) {

    ((javax.swing.text.AbstractDocument) campo.getDocument())
            .setDocumentFilter(new javax.swing.text.DocumentFilter() {

        @Override
        public void insertString(FilterBypass fb, int offset,
                String string, javax.swing.text.AttributeSet attr)
                throws javax.swing.text.BadLocationException {

            replace(fb, offset, 0, string, attr);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length,
                String string, javax.swing.text.AttributeSet attr)
                throws javax.swing.text.BadLocationException {

            String nuevoTexto = obtenerTexto(fb, offset, length, string);

            if (nuevoTexto.length() > limite) {

                JOptionPane.showMessageDialog(
                        P4_AdministradorEditarUsuarios.this,
                        nombreCampo + " no pueden superar los "
                                + limite + " caracteres.",
                        "Límite de caracteres",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            super.replace(fb, offset, length, string, attr);
        }
    });
}


public javax.swing.JScrollPane obtenerScrollOriginal() {
    return spRegistrarUsuarios;
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
        pnlMenuInterfaz = new javax.swing.JPanel();
        spRegistrarUsuarios = new javax.swing.JScrollPane();
        pnlEditarUsuarios = new javax.swing.JPanel();
        lblRegistrarUsuariosInterfaz = new javax.swing.JLabel();
        lblIngreseDatos = new javax.swing.JLabel();
        lblCedula = new javax.swing.JLabel();
        lblNombres = new javax.swing.JLabel();
        lblApellidos = new javax.swing.JLabel();
        lblSeleccioneUbicacion = new javax.swing.JLabel();
        lblRegion = new javax.swing.JLabel();
        lblDepartamento = new javax.swing.JLabel();
        lblMunicipio = new javax.swing.JLabel();
        lblSeleccionRolYUsuario = new javax.swing.JLabel();
        lblUsuario1 = new javax.swing.JLabel();
        lblRoles = new javax.swing.JLabel();
        lblContraseña = new javax.swing.JLabel();
        cmbRoles = new javax.swing.JComboBox<>();
        txtCedula = new javax.swing.JTextField();
        txtNombres = new javax.swing.JTextField();
        txtApellidos = new javax.swing.JTextField();
        txtUsuario = new javax.swing.JTextField();
        txtContraseña = new javax.swing.JTextField();
        cmbRegion = new javax.swing.JComboBox<>();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        cmbDepartamento = new javax.swing.JComboBox<>();
        cmbMunicipio = new javax.swing.JComboBox<>();
        lblUsuarioContraseña = new javax.swing.JLabel();

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

        pnlMenuInterfaz.setBackground(new java.awt.Color(255, 255, 255));
        pnlMenuInterfaz.setBorder(new javax.swing.border.MatteBorder(null));

        spRegistrarUsuarios.setBackground(new java.awt.Color(255, 255, 255));
        spRegistrarUsuarios.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spRegistrarUsuarios.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        pnlEditarUsuarios.setBackground(new java.awt.Color(255, 255, 255));
        pnlEditarUsuarios.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlEditarUsuarios.setPreferredSize(new java.awt.Dimension(700, 1200));

        lblRegistrarUsuariosInterfaz.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        lblRegistrarUsuariosInterfaz.setText("Editar usuario");

        lblIngreseDatos.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblIngreseDatos.setText("Datos de la persona a registrar");

        lblCedula.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblCedula.setText("Cédula:");

        lblNombres.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblNombres.setText("Nombres:");

        lblApellidos.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblApellidos.setText("Apellidos:");

        lblSeleccioneUbicacion.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblSeleccioneUbicacion.setText("Ubicación de residencia");

        lblRegion.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblRegion.setText("Región");

        lblDepartamento.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblDepartamento.setText("Departamento:");

        lblMunicipio.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMunicipio.setText("Municipio:");

        lblSeleccionRolYUsuario.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblSeleccionRolYUsuario.setText("Rol que el va a desempeñar ");

        lblUsuario1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblUsuario1.setText("Usuario:");

        lblRoles.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblRoles.setText("Roles:");

        lblContraseña.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblContraseña.setText("Contraseña:");

        cmbRoles.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtCedula.addActionListener(this::txtCedulaActionPerformed);

        txtNombres.addActionListener(this::txtNombresActionPerformed);

        cmbRegion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbRegion.addActionListener(this::cmbRegionActionPerformed);

        btnCancelar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        btnGuardar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        cmbDepartamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbMunicipio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblUsuarioContraseña.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblUsuarioContraseña.setText("Usuario y contraseña asignados");

        javax.swing.GroupLayout pnlEditarUsuariosLayout = new javax.swing.GroupLayout(pnlEditarUsuarios);
        pnlEditarUsuarios.setLayout(pnlEditarUsuariosLayout);
        pnlEditarUsuariosLayout.setHorizontalGroup(
            pnlEditarUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarUsuariosLayout.createSequentialGroup()
                .addGap(349, 349, 349)
                .addComponent(lblRegistrarUsuariosInterfaz)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(pnlEditarUsuariosLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(pnlEditarUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEditarUsuariosLayout.createSequentialGroup()
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardar)
                        .addGap(46, 46, 46))
                    .addGroup(pnlEditarUsuariosLayout.createSequentialGroup()
                        .addGroup(pnlEditarUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUsuarioContraseña)
                            .addComponent(lblSeleccionRolYUsuario)
                            .addComponent(lblSeleccioneUbicacion)
                            .addComponent(lblIngreseDatos)
                            .addGroup(pnlEditarUsuariosLayout.createSequentialGroup()
                                .addComponent(lblRoles)
                                .addGap(92, 92, 92)
                                .addComponent(cmbRoles, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlEditarUsuariosLayout.createSequentialGroup()
                                .addComponent(lblApellidos)
                                .addGap(18, 18, 18)
                                .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlEditarUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(pnlEditarUsuariosLayout.createSequentialGroup()
                                    .addComponent(lblCedula)
                                    .addGap(34, 34, 34)
                                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlEditarUsuariosLayout.createSequentialGroup()
                                    .addComponent(lblNombres)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlEditarUsuariosLayout.createSequentialGroup()
                                .addGroup(pnlEditarUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblUsuario1)
                                    .addComponent(lblContraseña))
                                .addGap(51, 51, 51)
                                .addGroup(pnlEditarUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlEditarUsuariosLayout.createSequentialGroup()
                                .addGroup(pnlEditarUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblRegion)
                                    .addComponent(lblDepartamento)
                                    .addComponent(lblMunicipio))
                                .addGroup(pnlEditarUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(pnlEditarUsuariosLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(cmbRegion, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(151, 151, 151))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlEditarUsuariosLayout.createSequentialGroup()
                                        .addGap(30, 30, 30)
                                        .addGroup(pnlEditarUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cmbMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cmbDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(0, 320, Short.MAX_VALUE))))
        );
        pnlEditarUsuariosLayout.setVerticalGroup(
            pnlEditarUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditarUsuariosLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(lblRegistrarUsuariosInterfaz)
                .addGap(40, 40, 40)
                .addComponent(lblIngreseDatos)
                .addGap(41, 41, 41)
                .addGroup(pnlEditarUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCedula)
                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(pnlEditarUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombres)
                    .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addGroup(pnlEditarUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblApellidos)
                    .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63)
                .addComponent(lblSeleccionRolYUsuario)
                .addGap(47, 47, 47)
                .addGroup(pnlEditarUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRoles)
                    .addComponent(cmbRoles, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66)
                .addComponent(lblSeleccioneUbicacion)
                .addGap(33, 33, 33)
                .addGroup(pnlEditarUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRegion)
                    .addComponent(cmbRegion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69)
                .addGroup(pnlEditarUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDepartamento)
                    .addComponent(cmbDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addGroup(pnlEditarUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMunicipio)
                    .addComponent(cmbMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addComponent(lblUsuarioContraseña)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addGroup(pnlEditarUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuario1)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addGroup(pnlEditarUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblContraseña)
                    .addComponent(txtContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(83, 83, 83)
                .addGroup(pnlEditarUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45))
        );

        spRegistrarUsuarios.setViewportView(pnlEditarUsuarios);

        javax.swing.GroupLayout pnlMenuInterfazLayout = new javax.swing.GroupLayout(pnlMenuInterfaz);
        pnlMenuInterfaz.setLayout(pnlMenuInterfazLayout);
        pnlMenuInterfazLayout.setHorizontalGroup(
            pnlMenuInterfazLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spRegistrarUsuarios, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 938, Short.MAX_VALUE)
        );
        pnlMenuInterfazLayout.setVerticalGroup(
            pnlMenuInterfazLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuInterfazLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(spRegistrarUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(pnlMenuInterfaz, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 96, 940, 530));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCedulaActionPerformed

    private void txtNombresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombresActionPerformed

    private void cmbRegionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRegionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbRegionActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
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
        java.awt.EventQueue.invokeLater(() -> new P4_AdministradorEditarUsuarios().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmbDepartamento;
    private javax.swing.JComboBox<String> cmbMunicipio;
    private javax.swing.JComboBox<String> cmbRegion;
    private javax.swing.JComboBox<String> cmbRoles;
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
    private javax.swing.JLabel lblApellidos;
    private javax.swing.JLabel lblCedula;
    private javax.swing.JLabel lblContraseña;
    private javax.swing.JLabel lblDepartamento;
    private javax.swing.JLabel lblIngreseDatos;
    private javax.swing.JLabel lblMunicipio;
    private javax.swing.JLabel lblNombres;
    private javax.swing.JLabel lblRegion;
    private javax.swing.JLabel lblRegistrarUsuariosInterfaz;
    private javax.swing.JLabel lblRoles;
    private javax.swing.JLabel lblSeleccionRolYUsuario;
    private javax.swing.JLabel lblSeleccioneUbicacion;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lblUsuario1;
    private javax.swing.JLabel lblUsuarioContraseña;
    private javax.swing.JPanel pnlEditarUsuarios;
    private javax.swing.JPanel pnlLogos;
    private javax.swing.JPanel pnlMenuInterfaz;
    private javax.swing.JScrollPane spRegistrarUsuarios;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtContraseña;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
