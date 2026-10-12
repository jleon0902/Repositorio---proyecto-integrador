/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;


/**
 *
 * @author VALENTINA
 */
public class P3_pantalla_psi extends javax.swing.JFrame {
private int idEncuesta = -1;
private int cantidadProductos = 1;
private int productoActual = 1;

private final java.util.List<Object[]> datosProductos =
        new java.util.ArrayList<>();

private javax.swing.JScrollPane scrollFormulario;



// Tipo de gasto seleccionado para el producto actual
private String tipoGastoSeleccionado = null;
/*
 * Opciones nuevas pendientes de guardar en Oracle.
 * La clave es el nombre de la tabla y el conjunto contiene
 * los nombres agregados durante esta sesión.
 */
private final java.util.Map<String, java.util.LinkedHashSet<String>>
        opcionesNuevas = new java.util.HashMap<>();
// ID de la encuesta a la que pertenecen los gastos.
// Debe recibirse o consultarse antes de guardar.


    /**
     * Creates new form P3_pantalla_psi
     */
    public P3_pantalla_psi() {
        initComponents();
        
        btnHogar.addActionListener(e -> {
    tipoGastoSeleccionado = "Hogar";
    actualizarBotonesTipoGasto();
});

btnPersonal.addActionListener(e -> {
    tipoGastoSeleccionado = "Personal";
    actualizarBotonesTipoGasto();
});
        
       cargarUnidadesMedida();
cargarFormasAdquisicion();
ajustarTextoFormaAdquisicion();
cargarLugaresCompra();
ajustarTextoLugarCompra();
cargarFrecuenciasCompra();

// Configurar la opción "Otros..." de cada combo
configurarComboOtros(cmbUnidadMedida, "UNIDAD_MEDIDA", "unidad de medida");
configurarComboOtros(cmbFormaAdquisicion, "FORMA_ADQUISICION", "forma de adquisición");
configurarComboOtros(cmbLugarCompra, "LUGAR_COMPRA", "lugar de compra");
 
     
cmbFormaAdquisicion.addActionListener(
    e -> actualizarLugarCompra()
);

actualizarLugarCompra();
   
        
        
// Reducir el espacio vertical antes de los botones

        pnlPantalla.setPreferredSize(new java.awt.Dimension(900, 65));
pnlPantalla.setMinimumSize(new java.awt.Dimension(0, 65));
pnlPantalla.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 65));
 // Ocultar la barra manual que no desplaza el formulario
    scrGastosDiarios.setVisible(false);

    // Obtener el tamaño original del formulario
    java.awt.Dimension tam =
        pnlGastosDiarios.getPreferredSize();

    // Darle suficiente altura para permitir el desplazamiento
    pnlGastosDiarios.setPreferredSize(
        new java.awt.Dimension(
            tam.width,
            tam.height
        )
    );

    // Crear el scroll que contiene el formulario completo
    scrollFormulario =
        new javax.swing.JScrollPane(pnlGastosDiarios);

    // Mantener visible la barra vertical
    scrollFormulario.setVerticalScrollBarPolicy(
        javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
    );

    // Desactivar desplazamiento horizontal
    scrollFormulario.setHorizontalScrollBarPolicy(
        javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
    );

    // Configurar la velocidad
    scrollFormulario.getVerticalScrollBar()
        .setUnitIncrement(20);

    // Organizar la ventana
    getContentPane().removeAll();
    getContentPane().setLayout(
        new java.awt.BorderLayout()
    );

    // Encabezado fijo

pnlPantalla.setPreferredSize(
    new java.awt.Dimension(900, 65)
);

pnlPantalla.setMinimumSize(
    new java.awt.Dimension(0, 65)
);

pnlPantalla.setMaximumSize(
    new java.awt.Dimension(Integer.MAX_VALUE, 65)
);


getContentPane().add(
    pnlPantalla,
    java.awt.BorderLayout.NORTH
);


    // Formulario desplazable
    getContentPane().add(
        scrollFormulario,
        java.awt.BorderLayout.CENTER
    );

    // Mostrar la ventana
    setSize(900, 700);
    setLocationRelativeTo(null);

    getContentPane().revalidate();
    getContentPane().repaint();

     // Preparar un registro inicial para el constructor sin parámetros
datosProductos.add(new Object[9]);

// Configurar el botón Anterior
BtnAnterior.addActionListener(e -> {
    if (productoActual > 1) {
        guardarDatosProductoActual();
        productoActual--;
        cargarDatosProductoActual();
        actualizarNavegacion();
    }
});   
        
    } 
    
    public P3_pantalla_psi(int cantidadProductos, int idEncuesta) {
    this(cantidadProductos);
    this.idEncuesta = idEncuesta;
}
    
public P3_pantalla_psi(int cantidadProductos) {
    this();

    if (cantidadProductos < 1) {
        cantidadProductos = 1;
    }

    this.cantidadProductos = cantidadProductos;
    this.productoActual = 1;

    // Crear un espacio para guardar los datos de cada producto
    datosProductos.clear();

    for (int i = 0; i < cantidadProductos; i++) {
        datosProductos.add(new Object[9]);
    }

    actualizarNavegacion();
}

    
    
private void cargarUnidadesMedida() {

    cmbUnidadMedida.removeAllItems();

    // Primera opción del combo
    cmbUnidadMedida.addItem("Seleccione");

    String sql = "SELECT NOMBRE FROM UNIDAD_MEDIDA ORDER BY ID";

    try (
        java.sql.Connection conexion = modelo.conexion.conectar();
        java.sql.PreparedStatement consulta =
                conexion.prepareStatement(sql);
        java.sql.ResultSet resultado = consulta.executeQuery()
    ) {

        while (resultado.next()) {
            cmbUnidadMedida.addItem(
                resultado.getString("NOMBRE")
            );
        }

        // Última opción del combo
        cmbUnidadMedida.addItem("Otros...");

    } catch (java.sql.SQLException e) {

        javax.swing.JOptionPane.showMessageDialog(
            this,
            "No se pudieron cargar las unidades de medida.\n"
            + e.getMessage(),
            "Error de conexión",
            javax.swing.JOptionPane.ERROR_MESSAGE
        );
    }
}


private void cargarFormasAdquisicion() {

    cmbFormaAdquisicion.removeAllItems();

    // Opción inicial
    cmbFormaAdquisicion.addItem("Seleccione");

    String sql = "SELECT NOMBRE FROM FORMA_ADQUISICION ORDER BY ID";

    try (
        java.sql.Connection conexion = modelo.conexion.conectar();
        java.sql.PreparedStatement consulta =
                conexion.prepareStatement(sql);
        java.sql.ResultSet resultado = consulta.executeQuery()
    ) {

        while (resultado.next()) {
            cmbFormaAdquisicion.addItem(
                resultado.getString("NOMBRE")
            );
        }

        // Opción final
        cmbFormaAdquisicion.addItem("Otros...");

    } catch (java.sql.SQLException e) {

        javax.swing.JOptionPane.showMessageDialog(
            this,
            "No se pudieron cargar las formas de adquisición.\n"
            + e.getMessage(),
            "Error de conexión",
            javax.swing.JOptionPane.ERROR_MESSAGE
        );
    }
}


private void ajustarTextoFormaAdquisicion() {

    cmbFormaAdquisicion.setRenderer(
        new javax.swing.DefaultListCellRenderer() {

            @Override
            public java.awt.Component getListCellRendererComponent(
                    javax.swing.JList<?> lista,
                    Object valor,
                    int indice,
                    boolean seleccionado,
                    boolean tieneFoco) {

                super.getListCellRendererComponent(
                    lista, valor, indice, seleccionado, tieneFoco
                );

                if (valor != null) {
                    setText("<html><div style='width:220px;'>"
                            + valor.toString()
                            + "</div></html>");
                }

                return this;
            }
        }
    );
}


private void cargarLugaresCompra() {

    cmbLugarCompra.removeAllItems();

    // Opción inicial
    cmbLugarCompra.addItem("Seleccione");

    String sql = "SELECT NOMBRE FROM LUGAR_COMPRA ORDER BY ID";

    try (
        java.sql.Connection conexion = modelo.conexion.conectar();
        java.sql.PreparedStatement consulta =
                conexion.prepareStatement(sql);
        java.sql.ResultSet resultado = consulta.executeQuery()
    ) {

        while (resultado.next()) {
            cmbLugarCompra.addItem(
                resultado.getString("NOMBRE")
            );
        }

        // Opción final
        cmbLugarCompra.addItem("Otros...");

    } catch (java.sql.SQLException e) {

        javax.swing.JOptionPane.showMessageDialog(
            this,
            "No se pudieron cargar los lugares de compra.\n"
            + e.getMessage(),
            "Error de conexión",
            javax.swing.JOptionPane.ERROR_MESSAGE
        );
    }
}


private void ajustarTextoLugarCompra() {

    cmbLugarCompra.setRenderer(
        new javax.swing.DefaultListCellRenderer() {

            @Override
            public java.awt.Component getListCellRendererComponent(
                    javax.swing.JList<?> lista,
                    Object valor,
                    int indice,
                    boolean seleccionado,
                    boolean tieneFoco) {

                super.getListCellRendererComponent(
                    lista, valor, indice, seleccionado, tieneFoco
                );

                if (valor != null) {
                    setText(
                        "<html><div style='width:230px; white-space:normal;'>"
                        + valor.toString()
                        + "</div></html>"
                    );
                }

                return this;
            }
        }
    );
}

private void cargarFrecuenciasCompra() {
    cmbFrecuenciaDeCompra.removeAllItems();
    cmbFrecuenciaDeCompra.addItem("Seleccione");

    String sql = "SELECT NOMBRE FROM FRECUENCIA_COMPRA ORDER BY ID";

    try (
        java.sql.Connection conexion = modelo.conexion.conectar();
        java.sql.PreparedStatement consulta =
            conexion.prepareStatement(sql);
        java.sql.ResultSet resultado = consulta.executeQuery()
    ) {

        while (resultado.next()) {
            cmbFrecuenciaDeCompra.addItem(
                resultado.getString("NOMBRE")
            );
        }

    } catch (java.sql.SQLException e) {
        javax.swing.JOptionPane.showMessageDialog(
            this,
            "No se pudieron cargar las frecuencias de compra.\n"
                + e.getMessage(),
            "Error de conexión",
            javax.swing.JOptionPane.ERROR_MESSAGE
        );
    }
}


private void configurarComboOtros(
        javax.swing.JComboBox<String> combo,
        String tabla,
        String descripcion) {

    combo.putClientProperty(
        "seleccionAnterior",
        combo.getSelectedItem()
    );

    combo.addActionListener(e -> {

        Object seleccionado = combo.getSelectedItem();

        if ("Otros...".equals(seleccionado)) {

            Object anterior =
                combo.getClientProperty("seleccionAnterior");

            javax.swing.JTextField campo =
                new javax.swing.JTextField(20);

            javax.swing.JPanel panel =
                new javax.swing.JPanel(
                    new java.awt.BorderLayout(5, 5)
                );

            panel.add(
                new javax.swing.JLabel(
                    "Escriba la nueva " + descripcion + ":"
                ),
                java.awt.BorderLayout.NORTH
            );

            panel.add(campo, java.awt.BorderLayout.CENTER);

            int respuesta =
                javax.swing.JOptionPane.showConfirmDialog(
                    this,
                    panel,
                    "Agregar " + descripcion,
                    javax.swing.JOptionPane.OK_CANCEL_OPTION,
                    javax.swing.JOptionPane.PLAIN_MESSAGE
                );

            if (respuesta != javax.swing.JOptionPane.OK_OPTION) {
                combo.setSelectedItem(anterior);
                return;
            }

            String nombre = campo.getText().trim();

            if (nombre.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Escriba un nombre."
                );
                combo.setSelectedItem(anterior);
                return;
            }

            // Verificar duplicados en las opciones cargadas
            // y en las agregadas temporalmente.
            for (int i = 0; i < combo.getItemCount(); i++) {
                String item = combo.getItemAt(i);

                if (item != null
                        && !"Otros...".equals(item)
                        && item.equalsIgnoreCase(nombre)) {

                    javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Ese valor ya existe."
                    );
                    combo.setSelectedItem(anterior);
                    return;
                }
            }

            // Guardar solamente en memoria.
            opcionesNuevas
                .computeIfAbsent(
                    tabla,
                    k -> new java.util.LinkedHashSet<>()
                )
                .add(nombre);

            // Agregar la opción antes de "Otros..."
            int posicionOtros = combo.getItemCount() - 1;

            combo.insertItemAt(nombre, posicionOtros);
            combo.setSelectedItem(nombre);

            combo.putClientProperty(
                "seleccionAnterior",
                nombre
            );

        } else if (seleccionado != null) {

            combo.putClientProperty(
                "seleccionAnterior",
                seleccionado
            );
        }
    });
}



private void guardarDatosProductoActual() {
    Object[] datos = datosProductos.get(productoActual - 1);

    datos[0] = txtCampoDeNombreDeProducto.getText();
    datos[1] = txtCampoDeCantidadAdquirida.getText();
    datos[2] = cmbUnidadMedida.getSelectedItem();
    datos[3] = cmbFormaAdquisicion.getSelectedItem();
    datos[4] = txtEquivalencia.getText();
    datos[5] = cmbLugarCompra.isEnabled()
        ? cmbLugarCompra.getSelectedItem()
        : null;
    datos[6] = txtValor.getText();
    datos[7] = cmbFrecuenciaDeCompra.getSelectedItem();
    datos[8] = tipoGastoSeleccionado;
}

private void cargarDatosProductoActual() {
    Object[] datos = datosProductos.get(productoActual - 1);

    txtCampoDeNombreDeProducto.setText(
        datos[0] == null ? "" : datos[0].toString()
    );

    txtCampoDeCantidadAdquirida.setText(
        datos[1] == null ? "" : datos[1].toString()
    );

    seleccionarODejarVacio(cmbUnidadMedida, datos[2]);
    seleccionarODejarVacio(cmbFormaAdquisicion, datos[3]);

    txtEquivalencia.setText(
        datos[4] == null ? "" : datos[4].toString()
    );

    seleccionarODejarVacio(cmbLugarCompra, datos[5]);

    txtValor.setText(
        datos[6] == null ? "" : datos[6].toString()
    );

    seleccionarODejarVacio(cmbFrecuenciaDeCompra, datos[7]);

    tipoGastoSeleccionado =
        datos[8] == null ? null : datos[8].toString();

    actualizarBotonesTipoGasto();
    
    actualizarLugarCompra();
}

private void seleccionarODejarVacio(
        javax.swing.JComboBox<String> combo,
        Object valor) {

    if (valor == null) {
        combo.setSelectedIndex(
            combo.getItemCount() > 0 ? 0 : -1
        );
    } else {
        combo.setSelectedItem(valor);
    }
}

private void actualizarNavegacion() {

    lblNumeroProducto.setText(
        "Producto " + productoActual + " de " + cantidadProductos
    );

    // Ocultar Anterior en el primer producto
    BtnAnterior.setVisible(productoActual > 1);

    // Cambiar el texto del botón según el producto
    if (productoActual == cantidadProductos) {
        BtnContinuar.setText("Finalizar");
    } else {
        BtnContinuar.setText("Continuar");
    }
}



private boolean validarProductoActual() {

    if (txtCampoDeNombreDeProducto.getText().trim().isEmpty()) {
        mostrarErrorCampo(
            "Ingrese el nombre del producto.",
            txtCampoDeNombreDeProducto
        );
        return false;
    }

    if (txtCampoDeNombreDeProducto.getText().trim().length() > 50) {
        mostrarErrorCampo(
            "El nombre no puede superar los 50 caracteres.",
            txtCampoDeNombreDeProducto
        );
        return false;
    }

    if (txtCampoDeCantidadAdquirida.getText().trim().isEmpty()) {
        mostrarErrorCampo(
            "Ingrese la cantidad adquirida.",
            txtCampoDeCantidadAdquirida
        );
        return false;
    }

    if (txtValor.getText().trim().isEmpty()) {
        mostrarErrorCampo(
            "Ingrese el valor del producto.",
            txtValor
        );
        return false;
    }

    if (!esNumeroValido(txtCampoDeCantidadAdquirida.getText())) {
        mostrarErrorCampo(
            "La cantidad debe ser un número entero positivo.",
            txtCampoDeCantidadAdquirida
        );
        return false;
    }
    
    
String unidadSeleccionada =
    cmbUnidadMedida.getSelectedItem() == null
        ? ""
        : cmbUnidadMedida.getSelectedItem().toString();

boolean unidadPersonalizada =
    opcionesNuevas
        .getOrDefault(
            "UNIDAD_MEDIDA",
            new java.util.LinkedHashSet<>()
        )
        .contains(unidadSeleccionada);

String equivalencia =
    txtEquivalencia.getText().trim();

if (unidadPersonalizada && equivalencia.isEmpty()) {
    mostrarErrorCampo(
        "Ingrese la equivalencia para la unidad de medida personalizada.",
        txtEquivalencia
    );
    return false;
}

if (!equivalencia.isEmpty()
        && !esNumeroValido(equivalencia)) {
    mostrarErrorCampo(
        "La equivalencia debe ser un número entero positivo.",
        txtEquivalencia
    );
    return false;
}


    if (!esNumeroValido(txtValor.getText())) {
        mostrarErrorCampo(
            "El valor debe ser un número entero positivo.",
            txtValor
        );
        return false;
    }

    if (!comboTieneSeleccion(cmbUnidadMedida)) {
        mostrarErrorCampo(
            "Seleccione una unidad de medida.",
            cmbUnidadMedida
        );
        return false;
    }

    if (!comboTieneSeleccion(cmbFormaAdquisicion)) {
        mostrarErrorCampo(
            "Seleccione una forma de adquisición.",
            cmbFormaAdquisicion
        );
        return false;
    }

    if (cmbLugarCompra.isEnabled()
        && !comboTieneSeleccion(cmbLugarCompra)) {

    mostrarErrorCampo(
        "Seleccione un lugar de compra.",
        cmbLugarCompra
    );
    return false;
}

    if (!comboTieneSeleccion(cmbFrecuenciaDeCompra)) {
        mostrarErrorCampo(
            "Seleccione una frecuencia de compra.",
            cmbFrecuenciaDeCompra
        );
        return false;
    }

    if (tipoGastoSeleccionado == null
            || (!tipoGastoSeleccionado.equals("Hogar")
            && !tipoGastoSeleccionado.equals("Personal"))) {
        javax.swing.JOptionPane.showMessageDialog(
            this,
            "Seleccione el tipo de gasto: Hogar o Personal.",
            "Campo obligatorio",
            javax.swing.JOptionPane.WARNING_MESSAGE
        );
        return false;
    }

    return true;
}

private boolean esNumeroValido(String texto) {
    try {
        int numero = Integer.parseInt(texto.trim());
        return numero > 0;
    } catch (NumberFormatException e) {
        return false;
    }
}

private void mostrarErrorCampo(
        String mensaje,
        javax.swing.JComponent campo) {

    javax.swing.JOptionPane.showMessageDialog(
        this,
        mensaje,
        "Campo obligatorio o inválido",
        javax.swing.JOptionPane.WARNING_MESSAGE
    );

    campo.requestFocusInWindow();
}

private boolean comboTieneSeleccion(
        javax.swing.JComboBox<?> combo) {

    Object seleccionado = combo.getSelectedItem();

    if (seleccionado == null) {
        return false;
    }

    String texto = seleccionado.toString().trim();

    return !texto.isEmpty()
            && !texto.equalsIgnoreCase("Seleccione")
            && !texto.equalsIgnoreCase("Seleccione...");
}


private void actualizarBotonesTipoGasto() {
    btnHogar.setOpaque(true);
    btnPersonal.setOpaque(true);

    btnHogar.setBackground(
        "Hogar".equals(tipoGastoSeleccionado)
            ? new java.awt.Color(180, 220, 180)
            : javax.swing.UIManager.getColor("Button.background")
    );

    btnPersonal.setBackground(
        "Personal".equals(tipoGastoSeleccionado)
            ? new java.awt.Color(180, 220, 180)
            : javax.swing.UIManager.getColor("Button.background")
    );
}


private void actualizarLugarCompra() {

    Object seleccion =
        cmbFormaAdquisicion.getSelectedItem();

    boolean esCompra =
        seleccion != null
        && "Compra".equalsIgnoreCase(
            seleccion.toString().trim()
        );

    cmbLugarCompra.setEnabled(esCompra);
    lblLugarDeCompra.setEnabled(esCompra);

    if (!esCompra) {
        cmbLugarCompra.setSelectedIndex(
            cmbLugarCompra.getItemCount() > 0 ? 0 : -1
        );
    }
}


private void guardarProductosEnOracle() {
    if (idEncuesta <= 0) {
        javax.swing.JOptionPane.showMessageDialog(
            this,
            "No se recibió un ID de encuesta válido.",
            "Error",
            javax.swing.JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    java.sql.Connection cn = null;

    try {
        cn = modelo.conexion.conectar();

        if (cn == null) {
            throw new java.sql.SQLException(
                "No fue posible conectarse a Oracle."
            );
        }

        // La encuesta y todos sus productos se guardan
        // como una sola transacción.
        cn.setAutoCommit(false);

        for (int i = 0; i < datosProductos.size(); i++) {
            Object[] p = datosProductos.get(i);

            String nombre = p[0].toString().trim();
            int cantidad = Integer.parseInt(p[1].toString());
            String unidad = p[2].toString();
            String forma = p[3].toString();
            String equivalenciaTexto =
                    p[4] == null ? "" : p[4].toString().trim();
            String lugar = p[5] == null ? null : p[5].toString();
            int valor = Integer.parseInt(p[6].toString());
            String frecuencia = p[7].toString();
            String tipoGasto = p[8].toString();

            // Buscar o crear las opciones de los catálogos.
            int idUnidad = obtenerOCrearCatalogo(
                cn, "UNIDAD_MEDIDA", unidad, 50
            );

            int idForma = obtenerOCrearCatalogo(
                cn, "FORMA_ADQUISICION", forma, 50
            );

            int idFrecuencia = obtenerOCrearCatalogo(
                cn, "FRECUENCIA_COMPRA", frecuencia, 50
            );

            int idLugar = -1;
            if ("Compra".equalsIgnoreCase(forma)) {
                if (lugar == null || lugar.trim().isEmpty()
                        || "Seleccione".equalsIgnoreCase(lugar)) {
                    throw new java.sql.SQLException(
                        "Falta el lugar de compra del producto "
                        + (i + 1) + "."
                    );
                }

                idLugar = obtenerOCrearCatalogo(
                    cn, "LUGAR_COMPRA", lugar, 150
                );
            }

            // Crear el producto.
            int idProducto = obtenerSiguienteId(cn, "PRODUCTO");

            String sqlProducto =
                "INSERT INTO PRODUCTO (ID, NOMBRE) VALUES (?, ?)";

            try (java.sql.PreparedStatement ps =
                    cn.prepareStatement(sqlProducto)) {
                ps.setInt(1, idProducto);
                ps.setString(2, nombre);
                ps.executeUpdate();
            }

            // Crear el gasto diario asociado a la encuesta.
            int idGasto = obtenerSiguienteId(cn, "GASTOS_DIARIOS");

            String sqlGasto =
                "INSERT INTO GASTOS_DIARIOS ("
                + "ID, NUMERO_ORDEN, FECHA, CANTIDAD_ADQUIRIDA, "
                + "VALOR, TIPO_GASTO, EQUIVALENCIA, ID_PRODUCTO, "
                + "ID_ENCUESTA, ID_LUGCOMPRA, ID_FRECOMPRA, "
                + "ID_UNIDADMEDIDA, ID_FORMAADQUISICION"
                + ") VALUES (?, ?, SYSDATE, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (java.sql.PreparedStatement ps =
                    cn.prepareStatement(sqlGasto)) {

                ps.setInt(1, idGasto);
                ps.setInt(2, i + 1);
                ps.setInt(3, cantidad);
                ps.setInt(4, valor);
                ps.setString(5, tipoGasto);

                if (equivalenciaTexto.isEmpty()) {
                    ps.setNull(6, java.sql.Types.NUMERIC);
                } else {
                    ps.setInt(6, Integer.parseInt(equivalenciaTexto));
                }

                ps.setInt(7, idProducto);
                ps.setInt(8, idEncuesta);

                if (idLugar == -1) {
                    ps.setNull(9, java.sql.Types.NUMERIC);
                } else {
                    ps.setInt(9, idLugar);
                }

                ps.setInt(10, idFrecuencia);
                ps.setInt(11, idUnidad);
                ps.setInt(12, idForma);

                ps.executeUpdate();
            }
        }

        cn.commit();

        javax.swing.JOptionPane.showMessageDialog(
            this,
            "Se guardaron correctamente los "
            + datosProductos.size()
            + " productos de la encuesta " + idEncuesta + ".",
            "Guardado exitoso",
            javax.swing.JOptionPane.INFORMATION_MESSAGE
        );

    } catch (Exception e) {
        if (cn != null) {
            try {
                cn.rollback();
            } catch (java.sql.SQLException ex) {
                e.addSuppressed(ex);
            }
        }

        javax.swing.JOptionPane.showMessageDialog(
            this,
            "No se pudieron guardar los productos.\n"
            + e.getMessage()
            + "\nSe intentó deshacer la transacción.",
            "Error al guardar",
            javax.swing.JOptionPane.ERROR_MESSAGE
        );

        e.printStackTrace();

    } finally {
        if (cn != null) {
            try {
                cn.setAutoCommit(true);
                cn.close();
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


private int obtenerSiguienteId(
        java.sql.Connection cn, String tabla)
        throws java.sql.SQLException {

    // Solo se permiten estas tablas.
    if (!"PRODUCTO".equals(tabla)
            && !"GASTOS_DIARIOS".equals(tabla)
            && !"UNIDAD_MEDIDA".equals(tabla)
            && !"FORMA_ADQUISICION".equals(tabla)
            && !"LUGAR_COMPRA".equals(tabla)
            && !"FRECUENCIA_COMPRA".equals(tabla)) {
        throw new java.sql.SQLException(
            "Tabla no permitida: " + tabla
        );
    }

    String sql = "SELECT NVL(MAX(ID), 0) + 1 FROM " + tabla;

    try (java.sql.PreparedStatement ps =
            cn.prepareStatement(sql);
         java.sql.ResultSet rs = ps.executeQuery()) {

        rs.next();
        return rs.getInt(1);
    }
}

private int obtenerOCrearCatalogo(
        java.sql.Connection cn,
        String tabla,
        String nombre,
        int longitudMaxima)
        throws java.sql.SQLException {

    if (!"UNIDAD_MEDIDA".equals(tabla)
            && !"FORMA_ADQUISICION".equals(tabla)
            && !"LUGAR_COMPRA".equals(tabla)
            && !"FRECUENCIA_COMPRA".equals(tabla)) {
        throw new java.sql.SQLException(
            "Catálogo no permitido: " + tabla
        );
    }

    nombre = nombre.trim();

    if (nombre.isEmpty() || nombre.length() > longitudMaxima) {
        throw new java.sql.SQLException(
            "Nombre inválido para " + tabla + ": " + nombre
        );
    }

    String sqlBuscar =
        "SELECT ID FROM " + tabla + " WHERE UPPER(NOMBRE) = UPPER(?)";

    try (java.sql.PreparedStatement ps =
            cn.prepareStatement(sqlBuscar)) {
        ps.setString(1, nombre);

        try (java.sql.ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("ID");
            }
        }
    }

    int nuevoId = obtenerSiguienteId(cn, tabla);

    String sqlInsertar =
        "INSERT INTO " + tabla + " (ID, NOMBRE) VALUES (?, ?)";

    try (java.sql.PreparedStatement ps =
            cn.prepareStatement(sqlInsertar)) {
        ps.setInt(1, nuevoId);
        ps.setString(2, nombre);
        ps.executeUpdate();
    }

    return nuevoId;
}


  /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton5 = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jButton6 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jComboBox5 = new javax.swing.JComboBox<>();
        jComboBox6 = new javax.swing.JComboBox<>();
        jButton8 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        pnlPantalla = new javax.swing.JPanel();
        sepPantalla = new javax.swing.JSeparator();
        lblDane = new javax.swing.JLabel();
        lblEnph = new javax.swing.JLabel();
        pnlLogos = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        lblUsuario = new javax.swing.JLabel();
        lblDia = new javax.swing.JLabel();
        sepLogos = new javax.swing.JSeparator();
        pnlGastosDiarios = new javax.swing.JPanel();
        lblGastosDiarios = new javax.swing.JLabel();
        lblNumeroProducto = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        lblNombreDelProducto = new javax.swing.JLabel();
        lblCantidadAdquirida = new javax.swing.JLabel();
        lblUnidadDeMedida = new javax.swing.JLabel();
        lblFormaDeAdquisicion = new javax.swing.JLabel();
        txtCampoDeCantidadAdquirida = new javax.swing.JTextField();
        txtCampoDeNombreDeProducto = new javax.swing.JTextField();
        cmbUnidadMedida = new javax.swing.JComboBox<>();
        cmbFormaAdquisicion = new javax.swing.JComboBox<>();
        cmbLugarCompra = new javax.swing.JComboBox<>();
        cmbFrecuenciaDeCompra = new javax.swing.JComboBox<>();
        lblEquivalencia = new javax.swing.JLabel();
        txtEquivalencia = new javax.swing.JTextField();
        lblLugarDeCompra = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        lblValor = new javax.swing.JLabel();
        btnHogar = new javax.swing.JButton();
        lblFrecuenciaDeCompra = new javax.swing.JLabel();
        lblTipoDeGasto = new javax.swing.JLabel();
        btnPersonal = new javax.swing.JButton();
        scrGastosDiarios = new javax.swing.JScrollBar();
        BtnAnterior = new javax.swing.JButton();
        BtnContinuar = new javax.swing.JButton();
        BtnCancelar = new javax.swing.JButton();

        jButton5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton5.setText("Cancelar");

        jComboBox3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox4.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton6.setText("Aceptar");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel11.setText("<html>Equivalencia en gramos<br>o en centímetros cúbicos de la unidad de medida</html>");

        jLabel12.setBackground(new java.awt.Color(0, 0, 0));
        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel12.setText("<html>Lugar de compra del artículo o<br>de obtención del servicio:</html>");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel13.setText("Valor");

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setText("Hogar");

        jLabel14.setBackground(new java.awt.Color(0, 0, 0));
        jLabel14.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel14.setText("Describa la frecuencia de compra");

        jLabel15.setBackground(new java.awt.Color(0, 0, 0));
        jLabel15.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel15.setText("Seleccione el tipo de gasto");

        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton4.setText("Personal");

        jButton7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton7.setText("Cancelar");

        jComboBox5.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox6.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton8.setText("Aceptar");

        jLabel16.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel16.setText("<html>Equivalencia en gramos<br>o en centímetros cúbicos de la unidad de medida</html>");

        jTextField8.addActionListener(this::jTextField8ActionPerformed);

        jLabel17.setBackground(new java.awt.Color(0, 0, 0));
        jLabel17.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel17.setText("<html>Lugar de compra del artículo o<br>de obtención del servicio:</html>");

        jTextField9.addActionListener(this::jTextField9ActionPerformed);

        jLabel18.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel18.setText("Valor");

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton2.setText("Hogar");

        jLabel19.setBackground(new java.awt.Color(0, 0, 0));
        jLabel19.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel19.setText("Describa la frecuencia de compra");

        jLabel20.setBackground(new java.awt.Color(0, 0, 0));
        jLabel20.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel20.setText("Seleccione el tipo de gasto");

        jButton9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton9.setText("Personal");

        jButton10.setText("jButton10");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlPantalla.setBackground(new java.awt.Color(255, 235, 229));

        sepPantalla.setForeground(new java.awt.Color(0, 0, 0));

        lblDane.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/logo1.png"))); // NOI18N

        lblEnph.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/logo2.png"))); // NOI18N

        pnlLogos.setBackground(new java.awt.Color(255, 255, 255));

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout pnlLogosLayout = new javax.swing.GroupLayout(pnlLogos);
        pnlLogos.setLayout(pnlLogosLayout);
        pnlLogosLayout.setHorizontalGroup(
            pnlLogosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
        );
        pnlLogosLayout.setVerticalGroup(
            pnlLogosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLogosLayout.createSequentialGroup()
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 293, Short.MAX_VALUE))
        );

        lblUsuario.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblUsuario.setText("lblUsuario");

        lblDia.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblDia.setText("lblDia");

        javax.swing.GroupLayout pnlPantallaLayout = new javax.swing.GroupLayout(pnlPantalla);
        pnlPantalla.setLayout(pnlPantallaLayout);
        pnlPantallaLayout.setHorizontalGroup(
            pnlPantallaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPantallaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sepLogos, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPantallaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPantallaLayout.createSequentialGroup()
                        .addComponent(lblDane)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEnph)
                        .addGap(43, 43, 43)
                        .addGroup(pnlPantallaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUsuario)
                            .addComponent(lblDia))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlPantallaLayout.createSequentialGroup()
                        .addComponent(pnlLogos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sepPantalla)))
                .addContainerGap())
        );
        pnlPantallaLayout.setVerticalGroup(
            pnlPantallaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPantallaLayout.createSequentialGroup()
                .addGroup(pnlPantallaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPantallaLayout.createSequentialGroup()
                        .addGroup(pnlPantallaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblDane)
                            .addComponent(lblEnph))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlPantallaLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDia)
                        .addGap(12, 12, 12)))
                .addGroup(pnlPantallaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlLogos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlPantallaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(sepLogos, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sepPantalla, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pnlGastosDiarios.setBackground(new java.awt.Color(255, 255, 255));

        lblGastosDiarios.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        lblGastosDiarios.setText("Gastos diarios");

        lblNumeroProducto.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblNumeroProducto.setText("Producto 1 de ");

        jSeparator5.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));

        lblNombreDelProducto.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblNombreDelProducto.setText("Nombre del producto o servicio que adquirió");

        lblCantidadAdquirida.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblCantidadAdquirida.setText("Cantidad adquirida");

        lblUnidadDeMedida.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblUnidadDeMedida.setText("Unidad de medida");

        lblFormaDeAdquisicion.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblFormaDeAdquisicion.setText("Forma de adquisición");

        txtCampoDeCantidadAdquirida.addActionListener(this::txtCampoDeCantidadAdquiridaActionPerformed);

        txtCampoDeNombreDeProducto.addActionListener(this::txtCampoDeNombreDeProductoActionPerformed);

        cmbUnidadMedida.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cmbUnidadMedida.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbFormaAdquisicion.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cmbFormaAdquisicion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbLugarCompra.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cmbLugarCompra.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbFrecuenciaDeCompra.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cmbFrecuenciaDeCompra.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblEquivalencia.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblEquivalencia.setText("<html>Equivalencia en gramos<br>o en centímetros cúbicos de la unidad de medida</html>");

        txtEquivalencia.addActionListener(this::txtEquivalenciaActionPerformed);

        lblLugarDeCompra.setBackground(new java.awt.Color(0, 0, 0));
        lblLugarDeCompra.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblLugarDeCompra.setText("<html>Lugar de compra del artículo o<br>de obtención del servicio:</html>");

        txtValor.addActionListener(this::txtValorActionPerformed);

        lblValor.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblValor.setText("Valor");

        btnHogar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnHogar.setText("Hogar");
        btnHogar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnHogar.setBorderPainted(false);

        lblFrecuenciaDeCompra.setBackground(new java.awt.Color(0, 0, 0));
        lblFrecuenciaDeCompra.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblFrecuenciaDeCompra.setText("Frecuencia de compra");

        lblTipoDeGasto.setBackground(new java.awt.Color(0, 0, 0));
        lblTipoDeGasto.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblTipoDeGasto.setText("Seleccione el tipo de gasto");

        btnPersonal.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnPersonal.setText("Personal");
        btnPersonal.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnPersonal.setBorderPainted(false);

        BtnAnterior.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        BtnAnterior.setText("Anterior");

        BtnContinuar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        BtnContinuar.setText("Continuar");
        BtnContinuar.addActionListener(this::BtnContinuarActionPerformed);

        BtnCancelar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        BtnCancelar.setText("Cancelar");
        BtnCancelar.addActionListener(this::BtnCancelarActionPerformed);

        javax.swing.GroupLayout pnlGastosDiariosLayout = new javax.swing.GroupLayout(pnlGastosDiarios);
        pnlGastosDiarios.setLayout(pnlGastosDiariosLayout);
        pnlGastosDiariosLayout.setHorizontalGroup(
            pnlGastosDiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(pnlGastosDiariosLayout.createSequentialGroup()
                .addGap(290, 290, 290)
                .addComponent(lblGastosDiarios)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlGastosDiariosLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(pnlGastosDiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNumeroProducto)
                    .addGroup(pnlGastosDiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(pnlGastosDiariosLayout.createSequentialGroup()
                            .addGroup(pnlGastosDiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblCantidadAdquirida)
                                .addComponent(lblUnidadDeMedida)
                                .addComponent(lblFormaDeAdquisicion)
                                .addComponent(lblEquivalencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblLugarDeCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblValor)
                                .addComponent(lblFrecuenciaDeCompra))
                            .addGap(77, 77, 77)
                            .addGroup(pnlGastosDiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtEquivalencia, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmbFormaAdquisicion, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCampoDeNombreDeProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtCampoDeCantidadAdquirida, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmbUnidadMedida, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmbLugarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmbFrecuenciaDeCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlGastosDiariosLayout.createSequentialGroup()
                            .addComponent(lblTipoDeGasto)
                            .addGap(56, 56, 56)
                            .addComponent(btnHogar)
                            .addGap(48, 48, 48)
                            .addComponent(btnPersonal))
                        .addGroup(pnlGastosDiariosLayout.createSequentialGroup()
                            .addComponent(BtnCancelar)
                            .addGap(35, 35, 35)
                            .addComponent(BtnAnterior)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BtnContinuar)))
                    .addComponent(lblNombreDelProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addComponent(scrGastosDiarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        pnlGastosDiariosLayout.setVerticalGroup(
            pnlGastosDiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGastosDiariosLayout.createSequentialGroup()
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlGastosDiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlGastosDiariosLayout.createSequentialGroup()
                        .addComponent(lblGastosDiarios)
                        .addGap(19, 19, 19)
                        .addGroup(pnlGastosDiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlGastosDiariosLayout.createSequentialGroup()
                                .addComponent(lblNumeroProducto)
                                .addGap(18, 18, 18)
                                .addGroup(pnlGastosDiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblNombreDelProducto)
                                    .addComponent(txtCampoDeNombreDeProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(28, 28, 28)
                                .addGroup(pnlGastosDiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCampoDeCantidadAdquirida, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCantidadAdquirida))
                                .addGap(31, 31, 31)
                                .addComponent(cmbUnidadMedida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblUnidadDeMedida))
                        .addGap(51, 51, 51)
                        .addGroup(pnlGastosDiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbFormaAdquisicion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFormaDeAdquisicion))
                        .addGap(38, 38, 38)
                        .addGroup(pnlGastosDiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblEquivalencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEquivalencia, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addGroup(pnlGastosDiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbLugarCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLugarDeCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlGastosDiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblValor))
                        .addGap(33, 33, 33)
                        .addGroup(pnlGastosDiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblFrecuenciaDeCompra)
                            .addComponent(cmbFrecuenciaDeCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(pnlGastosDiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTipoDeGasto)
                            .addComponent(btnHogar)
                            .addComponent(btnPersonal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                        .addGroup(pnlGastosDiariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtnAnterior)
                            .addComponent(BtnContinuar)
                            .addComponent(BtnCancelar))
                        .addGap(41, 41, 41))
                    .addComponent(scrGastosDiarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPantalla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlGastosDiarios, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlPantalla, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnlGastosDiarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCampoDeCantidadAdquiridaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCampoDeCantidadAdquiridaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCampoDeCantidadAdquiridaActionPerformed

    private void txtCampoDeNombreDeProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCampoDeNombreDeProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCampoDeNombreDeProductoActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9ActionPerformed

    private void txtEquivalenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEquivalenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEquivalenciaActionPerformed

    private void txtValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorActionPerformed

    private void BtnContinuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnContinuarActionPerformed
        // TODO add your handling code here:
        // Guardar temporalmente los datos del producto actual
     // No permitir avanzar con datos incompletos o inválidos
     // Validar los campos del producto actual
   // 1. Validar el producto actual
    if (!validarProductoActual()) {
        return;
    }

    // 2. Guardar temporalmente los datos en la lista
    guardarDatosProductoActual();

    // 3. Verificar que exista una encuesta válida
    if (idEncuesta <= 0) {
        javax.swing.JOptionPane.showMessageDialog(
            this,
            "No hay un ID de encuesta válido. "
            + "Vuelve a iniciar el proceso desde el registro de encuesta.",
            "Error",
            javax.swing.JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    // 4. Si todavía hay productos por recorrer, avanzar
    if (productoActual < cantidadProductos) {

        productoActual++;

        cargarDatosProductoActual();
        actualizarNavegacion();

    } else {

        // 5. Estamos en el último producto.
        // Verificar que todos tengan sus datos.
        for (int i = 0; i < datosProductos.size(); i++) {

            Object[] p = datosProductos.get(i);

            if (p == null || p[0] == null
                    || p[0].toString().trim().isEmpty()) {

                javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Faltan los datos del producto " + (i + 1) + ".",
                    "Datos incompletos",
                    javax.swing.JOptionPane.WARNING_MESSAGE
                );

                return;
            }
        }

        // 6. Guardar todos los productos en Oracle
        guardarProductosEnOracle();
    }
    
    }//GEN-LAST:event_BtnContinuarActionPerformed

    private void BtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCancelarActionPerformed

// Buscar la pantalla 1 que está oculta
    for (java.awt.Window ventana : java.awt.Window.getWindows()) {

        if (ventana instanceof P1_pantalla_psi
                && ventana.isDisplayable()) {

            // Mostrar nuevamente la pantalla 1
            ventana.setVisible(true);
            ventana.toFront();
            ventana.requestFocus();
            break;
        }
    }

    // Cerrar únicamente la pantalla 3
    this.dispose();
    }//GEN-LAST:event_BtnCancelarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new P3_pantalla_psi().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAnterior;
    private javax.swing.JButton BtnCancelar;
    private javax.swing.JButton BtnContinuar;
    private javax.swing.JButton btnHogar;
    private javax.swing.JButton btnPersonal;
    private javax.swing.JComboBox<String> cmbFormaAdquisicion;
    private javax.swing.JComboBox<String> cmbFrecuenciaDeCompra;
    private javax.swing.JComboBox<String> cmbLugarCompra;
    private javax.swing.JComboBox<String> cmbUnidadMedida;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JLabel lblCantidadAdquirida;
    private javax.swing.JLabel lblDane;
    private javax.swing.JLabel lblDia;
    private javax.swing.JLabel lblEnph;
    private javax.swing.JLabel lblEquivalencia;
    private javax.swing.JLabel lblFormaDeAdquisicion;
    private javax.swing.JLabel lblFrecuenciaDeCompra;
    private javax.swing.JLabel lblGastosDiarios;
    private javax.swing.JLabel lblLugarDeCompra;
    private javax.swing.JLabel lblNombreDelProducto;
    private javax.swing.JLabel lblNumeroProducto;
    private javax.swing.JLabel lblTipoDeGasto;
    private javax.swing.JLabel lblUnidadDeMedida;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lblValor;
    private javax.swing.JPanel pnlGastosDiarios;
    private javax.swing.JPanel pnlLogos;
    private javax.swing.JPanel pnlPantalla;
    private javax.swing.JScrollBar scrGastosDiarios;
    private javax.swing.JSeparator sepLogos;
    private javax.swing.JSeparator sepPantalla;
    private javax.swing.JTextField txtCampoDeCantidadAdquirida;
    private javax.swing.JTextField txtCampoDeNombreDeProducto;
    private javax.swing.JTextField txtEquivalencia;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
