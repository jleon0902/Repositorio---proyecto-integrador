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
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import modelo.Portatil;
import java.awt.Component;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import javax.swing.table.TableCellRenderer;
public class P6_AdministradorPortatiles1 extends javax.swing.JFrame {
private PortatilControlador controlador;

private javax.swing.Timer temporizadorConexion;
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(P6_AdministradorPortatiles1.class.getName());

    // Guarda el ancho inicial de cada columna.
    private int[] anchosIniciales;

public P6_AdministradorPortatiles1() {
    initComponents();
controlador = new PortatilControlador();
    // Al iniciar, los botones permanecen bloqueados.
    btnEditar.setEnabled(false);
    btnEliminar.setEnabled(false);

    // Permitir seleccionar una sola fila.
    tblTabla.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
    );

    // Cuando se selecciona una fila, actualizar el estado de los botones.
    tblTabla.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            actualizarEstadoBotones();
        }
    });

    // Comprobar periódicamente la conexión con Oracle.
    temporizadorConexion = new javax.swing.Timer(3000, e -> {
        actualizarEstadoBotones();
    });

    temporizadorConexion.start();

    // Cargar los portátiles solamente si hay conexión.
    if (controlador.estaConectado()) {
        cargarPortatiles();
    }

    // Impedir la edición directa de las celdas.
    tblTabla.setDefaultEditor(Object.class, null);

    // Configuración del encabezado.
    tblTabla.getTableHeader().setReorderingAllowed(false);
    tblTabla.getTableHeader().setResizingAllowed(false);

    // Guardar los anchos iniciales.
    guardarAnchosIniciales();

    // Permitir el desplazamiento horizontal.
    tblTabla.setAutoResizeMode(
            javax.swing.JTable.AUTO_RESIZE_OFF
    );

    // Detectar cambios en los datos de la tabla.
    tblTabla.getModel().addTableModelListener(e ->
            javax.swing.SwingUtilities.invokeLater(
                    this::ajustarAnchoColumnas
            )
    );

    ajustarAnchoColumnas();

    // Detener el temporizador al cerrar la ventana.
    addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosed(java.awt.event.WindowEvent e) {
            temporizadorConexion.stop();
        }
    });
}
    
public void actualizarEstadoBotones() {

    boolean conectado = controlador.estaConectado();

    boolean filaSeleccionada =
            tblTabla.getSelectedRow() != -1;

    btnEditar.setEnabled(conectado && filaSeleccionada);
    btnEliminar.setEnabled(conectado && filaSeleccionada);
}
    // CARGAR PORTÁTILES EN LA TABLA
public void cargarPortatiles() {
if (!controlador.estaConectado()) {
    actualizarEstadoBotones();
    return;
}
    try {

        List<Portatil> lista = controlador.listarPortatiles();

        DefaultTableModel modelo =
                (DefaultTableModel) tblTabla.getModel();

        // Limpiar los registros anteriores.
        modelo.setRowCount(0);

        // Agregar los portátiles recibidos desde Oracle.
        int numero = 1;

        for (Portatil portatil : lista) {

            modelo.addRow(new Object[]{
                numero,
                portatil.getNumeroSerie(),
                portatil.getMarca(),
                portatil.getModelo()
            });

            numero++;
        }

        // Quitar cualquier selección anterior.
        tblTabla.clearSelection();

        // Deshabilitar nuevamente los botones.
actualizarEstadoBotones();

        // Ajustar las columnas.
        ajustarAnchoColumnas();

    } catch (java.sql.SQLException e) {

        JOptionPane.showMessageDialog(
                this,
                "Error al cargar los portátiles:\n" + e.getMessage(),
                "Error de consulta",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
    public javax.swing.JPanel obtenerPanelPortatiles() {
    return pnlMenuInterfaz1;
}

    // Asignar eventos a los botones.
    private void asignarEventos() {
        btnEditar.addActionListener(e -> accionEditar());
        btnEliminar.addActionListener(e -> accionEliminar());
    }
    
    // OBTENER PORTÁTIL SELECCIONADO
public Portatil obtenerPortatilSeleccionado() {

    int filaSeleccionada = tblTabla.getSelectedRow();

    if (filaSeleccionada == -1) {
        return null;
    }

    int filaModelo =
            tblTabla.convertRowIndexToModel(filaSeleccionada);

    String numeroSerie = tblTabla.getModel()
            .getValueAt(filaModelo, 1).toString();

    String marca = tblTabla.getModel()
            .getValueAt(filaModelo, 2).toString();

    String modelo = tblTabla.getModel()
            .getValueAt(filaModelo, 3).toString();

    return new Portatil(
            numeroSerie,
            marca,
            modelo
    );
}

    // Guardar los tamaños originales de las columnas.
    private void guardarAnchosIniciales() {

        anchosIniciales = new int[tblTabla.getColumnCount()];

        for (int columna = 0; columna < tblTabla.getColumnCount(); columna++) {

            anchosIniciales[columna] = tblTabla.getColumnModel()
                    .getColumn(columna).getWidth();
        }
    }

    // Ajustar dinámicamente el ancho de las columnas.
    private void ajustarAnchoColumnas() {

        if (tblTabla.getColumnCount() == 0 || anchosIniciales == null) {
            return;
        }

        for (int columna = 0; columna < tblTabla.getColumnCount(); columna++) {

            int anchoNecesario = anchosIniciales[columna];

            // Revisar el encabezado.
            TableCellRenderer rendererEncabezado =
                    tblTabla.getTableHeader().getDefaultRenderer();

            Component componenteEncabezado =
                    rendererEncabezado.getTableCellRendererComponent(
                            tblTabla,
                            tblTabla.getColumnName(columna),
                            false,
                            false,
                            -1,
                            columna
                    );

            anchoNecesario = Math.max(
                    anchoNecesario,
                    componenteEncabezado.getPreferredSize().width + 12
            );

            // Revisar los datos de cada fila.
            for (int fila = 0; fila < tblTabla.getRowCount(); fila++) {

                Object valor = tblTabla.getValueAt(fila, columna);

                if (valor != null) {

                    TableCellRenderer renderer =
                            tblTabla.getCellRenderer(fila, columna);

                    Component componente =
                            renderer.getTableCellRendererComponent(
                                    tblTabla,
                                    valor,
                                    false,
                                    false,
                                    fila,
                                    columna
                            );

                    anchoNecesario = Math.max(
                            anchoNecesario,
                            componente.getPreferredSize().width + 12
                    );
                }
            }

            // Aplicar el ancho calculado.
            javax.swing.table.TableColumn tableColumn =
                    tblTabla.getColumnModel().getColumn(columna);

            tableColumn.setPreferredWidth(anchoNecesario);
            tableColumn.setWidth(anchoNecesario);
        }

        tblTabla.revalidate();
        tblTabla.repaint();
    }

    // Funcionalidad del botón Editar.
// FUNCIONALIDAD DEL BOTÓN EDITAR
private void accionEditar() {

    if (!controlador.estaConectado()) {
        actualizarEstadoBotones();

        JOptionPane.showMessageDialog(
                this,
                "No puede editar porque no existe conexión con Oracle usando el usuario ADMINISTRADOR.",
                "Sin conexión",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    controlador.editarPortatil(this);
}

private void accionEliminar() {

    if (!controlador.estaConectado()) {
        actualizarEstadoBotones();

        JOptionPane.showMessageDialog(
                this,
                "No puede eliminar porque no existe conexión con Oracle usando el usuario ADMINISTRADOR.",
                "Sin conexión",
                JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    controlador.eliminarPortatil(this);
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
        pnlMenu = new javax.swing.JPanel();
        lblMenu = new javax.swing.JLabel();
        btnRegistrarUsuarios = new javax.swing.JButton();
        btnUsuarios = new javax.swing.JButton();
        btnRegistrarPortatiles = new javax.swing.JButton();
        btnPortatiles = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();
        pnlInterfaz = new javax.swing.JPanel();
        pnlMenuInterfaz1 = new javax.swing.JPanel();
        lblPortatilesInterfaz = new javax.swing.JLabel();
        spTabla = new javax.swing.JScrollPane();
        tblTabla = new javax.swing.JTable();
        btnEliminar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();

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

        pnlMenu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlMenu.setPreferredSize(new java.awt.Dimension(242, 521));

        lblMenu.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblMenu.setText("Menú");

        btnRegistrarUsuarios.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnRegistrarUsuarios.setText("<html><center>Registrar<br>usuarios</center></html>");
        btnRegistrarUsuarios.addActionListener(this::btnRegistrarUsuariosActionPerformed);

        btnUsuarios.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnUsuarios.setText("Usuarios");
        btnUsuarios.addActionListener(this::btnUsuariosActionPerformed);

        btnRegistrarPortatiles.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnRegistrarPortatiles.setText("<html><center>Registrar<br>portátiles</center></html>");
        btnRegistrarPortatiles.addActionListener(this::btnRegistrarPortatilesActionPerformed);

        btnPortatiles.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnPortatiles.setText("Portatiles");
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
                            .addComponent(btnPortatiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addComponent(btnCerrarSesion)
                .addGap(29, 29, 29))
        );

        pnlInterfaz.setBackground(new java.awt.Color(255, 255, 255));

        pnlMenuInterfaz1.setBackground(new java.awt.Color(255, 255, 255));
        pnlMenuInterfaz1.setBorder(new javax.swing.border.MatteBorder(null));
        pnlMenuInterfaz1.setPreferredSize(new java.awt.Dimension(696, 480));

        lblPortatilesInterfaz.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        lblPortatilesInterfaz.setText("Portatiles");

        spTabla.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        spTabla.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tblTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Número serie", "Marca", "Modelo"
            }
        ));
        spTabla.setViewportView(tblTabla);

        btnEliminar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        btnEditar.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(this::btnEditarActionPerformed);

        javax.swing.GroupLayout pnlMenuInterfaz1Layout = new javax.swing.GroupLayout(pnlMenuInterfaz1);
        pnlMenuInterfaz1.setLayout(pnlMenuInterfaz1Layout);
        pnlMenuInterfaz1Layout.setHorizontalGroup(
            pnlMenuInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuInterfaz1Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(pnlMenuInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(spTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(pnlMenuInterfaz1Layout.createSequentialGroup()
                            .addComponent(btnEliminar)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEditar)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuInterfaz1Layout.createSequentialGroup()
                        .addComponent(lblPortatilesInterfaz)
                        .addGap(181, 181, 181)))
                .addContainerGap(98, Short.MAX_VALUE))
        );
        pnlMenuInterfaz1Layout.setVerticalGroup(
            pnlMenuInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuInterfaz1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(lblPortatilesInterfaz)
                .addGap(47, 47, 47)
                .addComponent(spTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addGroup(pnlMenuInterfaz1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar))
                .addGap(58, 58, 58))
        );

        javax.swing.GroupLayout pnlInterfazLayout = new javax.swing.GroupLayout(pnlInterfaz);
        pnlInterfaz.setLayout(pnlInterfazLayout);
        pnlInterfazLayout.setHorizontalGroup(
            pnlInterfazLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 696, Short.MAX_VALUE)
            .addGroup(pnlInterfazLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlInterfazLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pnlMenuInterfaz1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        pnlInterfazLayout.setVerticalGroup(
            pnlInterfazLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(pnlInterfazLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlInterfazLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pnlMenuInterfaz1, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout pnlMenuInterfazLayout = new javax.swing.GroupLayout(pnlMenuInterfaz);
        pnlMenuInterfaz.setLayout(pnlMenuInterfazLayout);
        pnlMenuInterfazLayout.setHorizontalGroup(
            pnlMenuInterfazLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuInterfazLayout.createSequentialGroup()
                .addComponent(pnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlInterfaz, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        pnlMenuInterfazLayout.setVerticalGroup(
            pnlMenuInterfazLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
            .addComponent(pnlInterfaz, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(pnlMenuInterfaz, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 96, 940, 530));

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

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        accionEliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
 accionEditar();
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
        java.awt.EventQueue.invokeLater(() -> new P6_AdministradorPortatiles1().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnPortatiles;
    private javax.swing.JButton btnRegistrarPortatiles;
    private javax.swing.JButton btnRegistrarUsuarios;
    private javax.swing.JButton btnUsuarios;
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
    private javax.swing.JLabel lblMenu;
    private javax.swing.JLabel lblPortatilesInterfaz;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel pnlInterfaz;
    private javax.swing.JPanel pnlLogos;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlMenuInterfaz;
    private javax.swing.JPanel pnlMenuInterfaz1;
    private javax.swing.JScrollPane spTabla;
    private javax.swing.JTable tblTabla;
    // End of variables declaration//GEN-END:variables
}
