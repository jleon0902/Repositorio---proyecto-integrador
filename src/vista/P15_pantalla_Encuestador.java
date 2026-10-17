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
import java.util.List;
/**
 *
 * @author VALENTINA
 */
public class P15_pantalla_Encuestador extends javax.swing.JFrame {
    private String cedulaEncuestador;
    private int idEncuestaCreada = -1;
    
    
    private List<String> cedulasRepresentantes = new java.util.ArrayList<>();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(P15_pantalla_Encuestador.class.getName());

    /**
     * Creates new form P15_pantalla_Encuestador
     */
    public P15_pantalla_Encuestador() {
        initComponents();
        
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);
        // Cargar los portátiles disponibles al abrir la pantalla
    cargarPortatilesDisponibles();

    // Actualizar la lista cuando se despliegue el combo
    cmbPortatilFamilia.addPopupMenuListener(
        new javax.swing.event.PopupMenuListener() {

            @Override
            public void popupMenuWillBecomeVisible(
                    javax.swing.event.PopupMenuEvent e) {
                cargarPortatilesDisponibles();
            }

            @Override
            public void popupMenuWillBecomeInvisible(
                    javax.swing.event.PopupMenuEvent e) {
            }

            @Override
            public void popupMenuCanceled(
                    javax.swing.event.PopupMenuEvent e) {
            }
        }
    );
        
    }
    
    private void cargarRepresentantes(java.util.List<String> cedulasRepresentantes) {
    cmbRepresentanteHogar.removeAllItems();
    cmbRepresentanteHogar.addItem("Seleccione");

    if (cedulasRepresentantes == null || cedulasRepresentantes.isEmpty()) {
        return;
    }

    String sql =
        "SELECT R.CEDULA, R.NOMBRE " +
        "FROM REPRESENTANTE R " +
        "WHERE R.CEDULA IN (" +
        String.join(",", java.util.Collections.nCopies(
            cedulasRepresentantes.size(), "?")) +
        ") " +
        "AND NOT EXISTS (" +
        "    SELECT 1 FROM ENCUESTA E " +
        "    WHERE E.CEDULA_REPRESENTANTE = R.CEDULA" +
        ") " +
        "ORDER BY R.NOMBRE";

    try (java.sql.Connection cn = modelo.conexion.conectar();
         java.sql.PreparedStatement ps = cn.prepareStatement(sql)) {

        for (int i = 0; i < cedulasRepresentantes.size(); i++) {
            ps.setString(i + 1, cedulasRepresentantes.get(i));
        }

        try (java.sql.ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String cedula = rs.getString("CEDULA");
                String nombre = rs.getString("NOMBRE");

                cmbRepresentanteHogar.addItem(cedula + " / " + nombre);
            }
        }

    } catch (java.sql.SQLException e) {
        javax.swing.JOptionPane.showMessageDialog(
            this,
            "Error al cargar representantes: " + e.getMessage()
        );
    }
}
    
    public P15_pantalla_Encuestador(
        Connection conexion,
        List<String> cedulasRepresentantes) {

    initComponents();

    cargarRepresentantes(conexion, cedulasRepresentantes);
}
    
    public void setNombreEncuestador(String nombre) {
    lblUsuario.setText("Encuestador: " + nombre);
}
    
    
public void setCedulaEncuestador(String cedula) {
    this.cedulaEncuestador = cedula;
    cargarTablaPortatilesVinculados();
}




public javax.swing.JPanel getPanelContenido() {

    javax.swing.JPanel contenedor =
            new javax.swing.JPanel(new java.awt.BorderLayout());

    javax.swing.JScrollPane scroll =
            new javax.swing.JScrollPane(
                    pnlRegistrarEntregaFormato);

    scroll.setVerticalScrollBarPolicy(
            javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

    scroll.setHorizontalScrollBarPolicy(
            javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    contenedor.add(scroll, java.awt.BorderLayout.CENTER);

    return contenedor;
}




public void setCedulasRepresentantes(List<String> cedulas) {

    if (cedulas == null) {
        this.cedulasRepresentantes = new java.util.ArrayList<>();
    } else {
        this.cedulasRepresentantes =
                new java.util.ArrayList<>(cedulas);
    }

    cargarRepresentantes(this.cedulasRepresentantes);

    // Actualizar la tabla con las cédulas recibidas desde P13.
    cargarTablaPortatilesVinculados();
}





public void cargarRepresentantes(
        Connection conexion,
        List<String> cedulasRepresentantes) {

    cmbRepresentanteHogar.removeAllItems();
    cmbRepresentanteHogar.addItem("Seleccione");

    if (conexion == null) {
        JOptionPane.showMessageDialog(
                this,
                "No hay conexión con la base de datos.",
                "Error de conexión",
                JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    if (cedulasRepresentantes == null
            || cedulasRepresentantes.isEmpty()) {
        return;
    }

    String marcadores = String.join(
            ",",
            java.util.Collections.nCopies(
                    cedulasRepresentantes.size(), "?")
    );

    String sql =
            "SELECT R.CEDULA, R.NOMBRE " +
            "FROM REPRESENTANTE R " +
            "WHERE R.CEDULA IN (" + marcadores + ") " +
            "AND NOT EXISTS ( " +
            "    SELECT 1 " +
            "    FROM ENCUESTA E " +
            "    WHERE E.CEDULA_REPRESENTANTE = R.CEDULA " +
            ") " +
            "ORDER BY R.NOMBRE";

    try (PreparedStatement ps = conexion.prepareStatement(sql)) {

        for (int i = 0; i < cedulasRepresentantes.size(); i++) {
            ps.setString(i + 1, cedulasRepresentantes.get(i));
        }

        try (ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                String cedula = rs.getString("CEDULA");
                String nombre = rs.getString("NOMBRE");

                cmbRepresentanteHogar.addItem(
                        cedula + " / " + nombre
                );
            }
        }

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
                this,
                "Error al cargar los representantes: "
                        + e.getMessage(),
                "Error de base de datos",
                JOptionPane.ERROR_MESSAGE
        );
    }
}


private void cargarPortatilesDisponibles() {

    cmbPortatilFamilia.removeAllItems();
    cmbPortatilFamilia.addItem("Seleccione");

    String sql =
        "SELECT NUMERO_SERIE FROM ( " +
        "    SELECT P.NUMERO_SERIE " +
        "    FROM PORTATIL P " +
        "    WHERE NOT EXISTS ( " +
        "        SELECT 1 FROM ENCUESTA E " +
        "        WHERE E.ID_PORTATIL = P.ID " +
        "    ) " +
        "    ORDER BY P.NUMERO_SERIE " +
        ") WHERE ROWNUM <= 10";

    try (Connection cn = modelo.conexion.conectar();
         PreparedStatement ps = cn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            cmbPortatilFamilia.addItem(
                rs.getString("NUMERO_SERIE")
            );
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
            this,
            "Error al cargar los portátiles disponibles: "
                + e.getMessage(),
            "Error de base de datos",
            JOptionPane.ERROR_MESSAGE
        );
    }
}


private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {

    // Validar que se haya seleccionado un representante
    if (cmbRepresentanteHogar.getSelectedIndex() <= 0) {
        JOptionPane.showMessageDialog(
            this,
            "Seleccione un representante del hogar."
        );
        return;
    }

    // Validar que se haya seleccionado un portátil
    if (cmbPortatilFamilia.getSelectedIndex() <= 0) {
        JOptionPane.showMessageDialog(
            this,
            "Seleccione un portátil disponible."
        );
        return;
    }

    // Validar la fecha
    String fechaTexto = txtFechaEntrega.getText().trim();

    java.text.SimpleDateFormat formato =
        new java.text.SimpleDateFormat("dd/MM/yyyy");

    formato.setLenient(false);

    java.util.Date fechaInicio;

    try {
        fechaInicio = formato.parse(fechaTexto);

        // Evitar fechas con caracteres adicionales
        if (!formato.format(fechaInicio).equals(fechaTexto)) {
            throw new java.text.ParseException(
                "Formato de fecha inválido", 0
            );
        }

    } catch (java.text.ParseException e) {
        JOptionPane.showMessageDialog(
            this,
            "Ingrese una fecha válida con el formato dd/MM/yyyy."
        );
        return;
    }

    // El primer día cuenta como día 1: sumar 13 días
    java.util.Calendar calendario =
        java.util.Calendar.getInstance();

    calendario.setTime(fechaInicio);
    calendario.add(java.util.Calendar.DAY_OF_MONTH, 13);

    java.util.Date fechaFin = calendario.getTime();

    // Obtener la cédula del representante seleccionado
    String representanteSeleccionado =
        cmbRepresentanteHogar.getSelectedItem().toString();

    String cedulaRepresentante =
        representanteSeleccionado.split(" / ", 2)[0].trim();
    
    String nombreRepresentante =
    representanteSeleccionado.split(" / ", 2).length > 1
        ? representanteSeleccionado.split(" / ", 2)[1].trim()
        : representanteSeleccionado;

    // Obtener el número de serie del portátil
    String numeroSerie =
        cmbPortatilFamilia.getSelectedItem().toString();

    String sqlPortatil =
        "SELECT ID FROM PORTATIL WHERE NUMERO_SERIE = ?";

    String sqlNuevoId =
        "SELECT NVL(MAX(ID), 0) + 1 AS NUEVO_ID FROM ENCUESTA";

    String sqlInsertar =
        "INSERT INTO ENCUESTA " +
        "(ID, ESTADO, FECHA_INICIO, FECHA_FIN, " +
        "CEDULA_REPRESENTANTE, ID_PORTATIL) " +
        "VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection cn = modelo.conexion.conectar()) {

        if (cn == null) {
            JOptionPane.showMessageDialog(
                this,
                "No se pudo conectar con la base de datos."
            );
            return;
        }

        cn.setAutoCommit(false);

        try {
            // Buscar el ID del portátil por su número de serie
            int idPortatil;

            try (PreparedStatement ps =
                    cn.prepareStatement(sqlPortatil)) {

                ps.setString(1, numeroSerie);

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException(
                            "No se encontró el portátil seleccionado."
                        );
                    }

                    idPortatil = rs.getInt("ID");
                }
            }

            // Generar el ID de la encuesta
            // (si todavía no existe una secuencia o IDENTITY)
            int idEncuesta;
            
            

            try (PreparedStatement ps =
                    cn.prepareStatement(sqlNuevoId);
                 ResultSet rs = ps.executeQuery()) {

                rs.next();
                idEncuesta = rs.getInt("NUEVO_ID");
            }

            // Insertar la encuesta
            try (PreparedStatement ps =
                    cn.prepareStatement(sqlInsertar)) {

                ps.setInt(1, idEncuesta);
                ps.setString(2, "Iniciado");

                ps.setDate(
                    3,
                    new java.sql.Date(fechaInicio.getTime())
                );

                ps.setDate(
                    4,
                    new java.sql.Date(fechaFin.getTime())
                );

                ps.setString(5, cedulaRepresentante);
                ps.setInt(6, idPortatil);

                ps.executeUpdate();
            }

            cn.commit();
            
            cargarTablaPortatilesVinculados();
            
            idEncuestaCreada = idEncuesta;
            
            javax.swing.JOptionPane.showMessageDialog(
    this,
    "La encuesta se registró correctamente.\n" +
    "ID de encuesta: " + idEncuestaCreada
);


            JOptionPane.showMessageDialog(
                this,
                "La encuesta se registró correctamente.\n" +
                "Fecha de inicio: " + formato.format(fechaInicio) +
                "\nFecha de finalización: " + formato.format(fechaFin)
            );

            // Limpiar los campos después de guardar
            cmbRepresentanteHogar.setSelectedIndex(0);
            cmbPortatilFamilia.setSelectedIndex(0);
            txtFechaEntrega.setText("");

            // Actualizar los portátiles disponibles
            cargarPortatilesDisponibles();

            // Actualizar los representantes disponibles
            cargarRepresentantes(cedulasRepresentantes);

        } catch (SQLException e) {
            cn.rollback();

            JOptionPane.showMessageDialog(
                this,
                "No se pudo guardar la encuesta: " + e.getMessage(),
                "Error de base de datos",
                JOptionPane.ERROR_MESSAGE
            );

        } finally {
            cn.setAutoCommit(true);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
            this,
            "Error de conexión: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}

public int getIdEncuestaCreada() {
    return idEncuestaCreada;
}



private void cargarTablaPortatilesVinculados() {

    javax.swing.table.DefaultTableModel modeloTabla =
        (javax.swing.table.DefaultTableModel)
            jtbPortatilesVinculados.getModel();

    modeloTabla.setRowCount(0);

    // Necesitamos conocer al encuestador.
    if (cedulaEncuestador == null
            || cedulaEncuestador.trim().isEmpty()) {
        return;
    }

    // Si P13 no ha enviado las cédulas, no mostrar filas.
    if (cedulasRepresentantes == null
            || cedulasRepresentantes.isEmpty()) {
        return;
    }

    String marcadores = String.join(
        ",",
        java.util.Collections.nCopies(
            cedulasRepresentantes.size(), "?")
    );

    String sql =
        "SELECT DISTINCT " +
        "R.CEDULA, R.NOMBRE, P.NUMERO_SERIE, E.ID " +
        "FROM HOGAR H " +
        "INNER JOIN REPRESENTANTE R " +
        "ON R.CEDULA = H.CED_REPRESENTANTE " +
        "INNER JOIN ENCUESTA E " +
        "ON E.CEDULA_REPRESENTANTE = R.CEDULA " +
        "INNER JOIN PORTATIL P " +
        "ON P.ID = E.ID_PORTATIL " +
        "WHERE H.CED_ENCUESTADOR = ? " +
        "AND R.CEDULA IN (" + marcadores + ") " +
        "ORDER BY E.ID ASC";

    try (Connection cn = modelo.conexion.conectar();
         PreparedStatement ps = cn.prepareStatement(sql)) {

        int parametro = 1;

        // Filtrar por el encuestador que inició sesión.
        ps.setString(parametro++, cedulaEncuestador);

        // Filtrar únicamente por las cédulas que llegan de P13.
        for (String cedula : cedulasRepresentantes) {
            ps.setString(parametro++, cedula);
        }

        try (ResultSet rs = ps.executeQuery()) {

            int numero = 1;

            while (rs.next()) {
                Object[] fila = {
                    numero,
                    rs.getString("CEDULA"),
                    rs.getString("NOMBRE"),
                    rs.getString("NUMERO_SERIE")
                };

                modeloTabla.addRow(fila);
                numero++;
            }
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
            this,
            "Error al cargar los portátiles vinculados: "
                + e.getMessage(),
            "Error de base de datos",
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

        jLabel18 = new javax.swing.JLabel();
        pnlRegistrarEntrega = new javax.swing.JPanel();
        pnlRegistrarEntregaFormato = new javax.swing.JPanel();
        lblSeleccioneRepresentante = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        lblRegistrarEntrega = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        cmbRepresentanteHogar = new javax.swing.JComboBox<>();
        lblPortatilVinculadoFamilia = new javax.swing.JLabel();
        txtFechaEntrega = new javax.swing.JTextField();
        lblFechaEntrega = new javax.swing.JLabel();
        cmbPortatilFamilia = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbPortatilesVinculados = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jbnEliminar = new javax.swing.JButton();
        jbnEditar = new javax.swing.JButton();
        pnlLogos = new javax.swing.JPanel();
        lblDane = new javax.swing.JLabel();
        lblEnph = new javax.swing.JLabel();
        pnlLogo = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        lblUsuario = new javax.swing.JLabel();
        sepLogo = new javax.swing.JSeparator();

        jLabel18.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel18.setText("Pórtatil vinculado con la familia:");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlRegistrarEntrega.setBackground(new java.awt.Color(255, 255, 255));

        pnlRegistrarEntregaFormato.setBackground(new java.awt.Color(255, 255, 255));
        pnlRegistrarEntregaFormato.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblSeleccioneRepresentante.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblSeleccioneRepresentante.setText("<html>Seleccione el representante del hogar<br>del que desea registrar la entrega:</html>");

        btnGuardar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnGuardar.setText("Guardar");

        lblRegistrarEntrega.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblRegistrarEntrega.setText("Vincular Portátil");

        btnCancelar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        cmbRepresentanteHogar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblPortatilVinculadoFamilia.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblPortatilVinculadoFamilia.setText("Seleccione el portatil que desea vincular:");

        txtFechaEntrega.addActionListener(this::txtFechaEntregaActionPerformed);

        lblFechaEntrega.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblFechaEntrega.setText("Fecha");

        cmbPortatilFamilia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jtbPortatilesVinculados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "#", "Cédula", "Representante", "Portatil Asignado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jtbPortatilesVinculados);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setText("Portatiles Vinculados a Representantes del Hogar");

        jbnEliminar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jbnEliminar.setText("Eliminar");

        jbnEditar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jbnEditar.setText("Editar");
        jbnEditar.addActionListener(this::jbnEditarActionPerformed);

        javax.swing.GroupLayout pnlRegistrarEntregaFormatoLayout = new javax.swing.GroupLayout(pnlRegistrarEntregaFormato);
        pnlRegistrarEntregaFormato.setLayout(pnlRegistrarEntregaFormatoLayout);
        pnlRegistrarEntregaFormatoLayout.setHorizontalGroup(
            pnlRegistrarEntregaFormatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRegistrarEntregaFormatoLayout.createSequentialGroup()
                .addGroup(pnlRegistrarEntregaFormatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRegistrarEntregaFormatoLayout.createSequentialGroup()
                        .addGap(264, 264, 264)
                        .addComponent(lblRegistrarEntrega))
                    .addGroup(pnlRegistrarEntregaFormatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlRegistrarEntregaFormatoLayout.createSequentialGroup()
                            .addGap(40, 40, 40)
                            .addComponent(lblSeleccioneRepresentante, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(94, 94, 94)
                            .addComponent(cmbRepresentanteHogar, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRegistrarEntregaFormatoLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(pnlRegistrarEntregaFormatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel1)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRegistrarEntregaFormatoLayout.createSequentialGroup()
                                    .addComponent(lblFechaEntrega)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtFechaEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRegistrarEntregaFormatoLayout.createSequentialGroup()
                                    .addComponent(lblPortatilVinculadoFamilia)
                                    .addGap(94, 94, 94)
                                    .addComponent(cmbPortatilFamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRegistrarEntregaFormatoLayout.createSequentialGroup()
                                    .addComponent(btnCancelar)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnGuardar))
                                .addComponent(jScrollPane1)
                                .addGroup(pnlRegistrarEntregaFormatoLayout.createSequentialGroup()
                                    .addComponent(jbnEditar)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jbnEliminar))))))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        pnlRegistrarEntregaFormatoLayout.setVerticalGroup(
            pnlRegistrarEntregaFormatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRegistrarEntregaFormatoLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblRegistrarEntrega)
                .addGap(27, 27, 27)
                .addGroup(pnlRegistrarEntregaFormatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cmbRepresentanteHogar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSeleccioneRepresentante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(pnlRegistrarEntregaFormatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbPortatilFamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPortatilVinculadoFamilia, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(pnlRegistrarEntregaFormatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFechaEntrega)
                    .addComponent(txtFechaEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(pnlRegistrarEntregaFormatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(pnlRegistrarEntregaFormatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbnEliminar)
                    .addComponent(jbnEditar))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlRegistrarEntregaLayout = new javax.swing.GroupLayout(pnlRegistrarEntrega);
        pnlRegistrarEntrega.setLayout(pnlRegistrarEntregaLayout);
        pnlRegistrarEntregaLayout.setHorizontalGroup(
            pnlRegistrarEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlRegistrarEntregaFormato, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlRegistrarEntregaLayout.setVerticalGroup(
            pnlRegistrarEntregaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlRegistrarEntregaFormato, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlLogos.setBackground(new java.awt.Color(255, 235, 229));
        pnlLogos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblDane.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/logo1.png"))); // NOI18N

        lblEnph.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/logo2.png"))); // NOI18N

        pnlLogo.setBackground(new java.awt.Color(255, 255, 255));

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout pnlLogoLayout = new javax.swing.GroupLayout(pnlLogo);
        pnlLogo.setLayout(pnlLogoLayout);
        pnlLogoLayout.setHorizontalGroup(
            pnlLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
        );
        pnlLogoLayout.setVerticalGroup(
            pnlLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLogoLayout.createSequentialGroup()
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 293, Short.MAX_VALUE))
        );

        lblUsuario.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblUsuario.setText("lblUsuario");

        javax.swing.GroupLayout pnlLogosLayout = new javax.swing.GroupLayout(pnlLogos);
        pnlLogos.setLayout(pnlLogosLayout);
        pnlLogosLayout.setHorizontalGroup(
            pnlLogosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLogosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sepLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlLogosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlLogosLayout.createSequentialGroup()
                        .addComponent(lblDane)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEnph)
                        .addGap(43, 43, 43)
                        .addComponent(lblUsuario))
                    .addComponent(pnlLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addGap(35, 35, 35)
                        .addComponent(lblUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(pnlLogosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sepLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlRegistrarEntrega, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlLogos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlLogos, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlRegistrarEntrega, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtFechaEntregaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaEntregaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaEntregaActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        int respuesta = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de que quiere cancelar?",
            "Confirmar cancelación",
            JOptionPane.YES_NO_OPTION
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            cmbRepresentanteHogar.setSelectedIndex(0);
            cmbPortatilFamilia.setSelectedIndex(0);
            txtFechaEntrega.setText("");
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void jbnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnEditarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbnEditarActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new P15_pantalla_Encuestador().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmbPortatilFamilia;
    private javax.swing.JComboBox<String> cmbRepresentanteHogar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JButton jbnEditar;
    private javax.swing.JButton jbnEliminar;
    private javax.swing.JTable jtbPortatilesVinculados;
    private javax.swing.JLabel lblDane;
    private javax.swing.JLabel lblEnph;
    private javax.swing.JLabel lblFechaEntrega;
    private javax.swing.JLabel lblPortatilVinculadoFamilia;
    private javax.swing.JLabel lblRegistrarEntrega;
    private javax.swing.JLabel lblSeleccioneRepresentante;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel pnlLogo;
    private javax.swing.JPanel pnlLogos;
    private javax.swing.JPanel pnlRegistrarEntrega;
    private javax.swing.JPanel pnlRegistrarEntregaFormato;
    private javax.swing.JSeparator sepLogo;
    private javax.swing.JTextField txtFechaEntrega;
    // End of variables declaration//GEN-END:variables
}
