/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;
import modelo.HogarDAO;
import java.sql.SQLException;
import modelo.conexion;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author VALENTINA
 */
public class P13_pantalla_Encuestador extends javax.swing.JFrame {
    
    private String cedulaEncuestador;
    private String nombreEncuestador;
    private List<String> cedulasRepresentantes = new ArrayList<>();
    
private final java.util.Map<String, java.util.LinkedHashSet<String>>
        opcionesPendientes = new java.util.LinkedHashMap<>();

private final java.util.Set<String> unidadesPersonalizadasPendientes =
        new java.util.LinkedHashSet<>();

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(P13_pantalla_Encuestador.class.getName());

public List<String> getCedulasRepresentantes() {
    return new ArrayList<>(cedulasRepresentantes);
}

    /**
     * Creates new form P13_pantalla_Encuestador
     */

public P13_pantalla_Encuestador() {
    initComponents();
    
// Impedir editar las columnas de texto
tblTablaFamilias.setDefaultEditor(Object.class, null);

// Permitir marcar la casilla de verificación
tblTablaFamilias.setDefaultEditor(
    Boolean.class,
    new javax.swing.DefaultCellEditor(new javax.swing.JCheckBox())
);

tblTablaFamilias.setSelectionMode(
    javax.swing.ListSelectionModel.SINGLE_SELECTION
);

    // Permitir seleccionar una sola familia a la vez
    tblTablaFamilias.setSelectionMode(
        javax.swing.ListSelectionModel.SINGLE_SELECTION
    );

    tblTablaFamilias.setRowSelectionAllowed(true);
    tblTablaFamilias.setColumnSelectionAllowed(false);
    
    tblTablaFamilias.getSelectionModel().addListSelectionListener(e -> {
    if (!e.getValueIsAdjusting()) {
        int fila = tblTablaFamilias.getSelectedRow();

        javax.swing.table.DefaultTableModel modelo =
            (javax.swing.table.DefaultTableModel)
            tblTablaFamilias.getModel();

        // Desmarcar las demás familias
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.setValueAt(false, i, 4);
        }

        // Marcar la familia seleccionada
        if (fila != -1) {
            int filaModelo = tblTablaFamilias.convertRowIndexToModel(fila);
            modelo.setValueAt(true, filaModelo, 4);
        }
    }
});

    btnVerFamilias.addActionListener(evt -> {
        int fila = tblTablaFamilias.getSelectedRow();

        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(
                this,
                "Seleccione una familia de la tabla."
            );
            return;
        }

        int filaModelo = tblTablaFamilias.convertRowIndexToModel(fila);

        String cedulaRepresentante =
            cedulasRepresentantes.get(filaModelo);

        P14_pantalla_Encuestador pantalla =
            new P14_pantalla_Encuestador();

        pantalla.setCedulaRepresentante(cedulaRepresentante);
        pantalla.setNombreEncuestador(nombreEncuestador);
        pantalla.setLocationRelativeTo(this);
        pantalla.setVisible(true);
    });

    btnVincularPortatil.addActionListener(evt -> {

        if (cedulaEncuestador == null
                || cedulaEncuestador.trim().isEmpty()) {

            javax.swing.JOptionPane.showMessageDialog(
                this,
                "No se recibió la cédula del encuestador."
            );
            return;
        }

        if (cedulasRepresentantes.isEmpty()) {
            cargarFamilias(cedulaEncuestador);
        }

        if (cedulasRepresentantes.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                this,
                "Este encuestador no tiene familias asignadas."
            );
            return;
        }

        P15_pantalla_Encuestador pantalla =
            new P15_pantalla_Encuestador();

        pantalla.setCedulasRepresentantes(cedulasRepresentantes);
        pantalla.setLocationRelativeTo(this);
        pantalla.setVisible(true);
    });
}



public void cargarFamilias(String cedulaEncuestador) {

    if (cedulaEncuestador == null
            || cedulaEncuestador.trim().isEmpty()) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "No se recibió la cédula del encuestador."
        );

        return;
    }

    javax.swing.table.DefaultTableModel modelo =
            (javax.swing.table.DefaultTableModel)
            tblTablaFamilias.getModel();

    modelo.setRowCount(0);

    cedulasRepresentantes.clear(); // NUEVO: limpiar las cédulas anteriores

    try {

        HogarDAO dao = new HogarDAO();

        List<Object[]> familias =
                dao.obtenerFamiliasEncuestador(cedulaEncuestador);

        int numero = 1;

        for (Object[] familia : familias) {

            if (numero > 4) {
                break;
            }

            cedulasRepresentantes.add((String) familia[1]);
            // NUEVO: guardar la cédula del representante

            modelo.addRow(new Object[]{
                numero,
                familia[0],   // Número de vivienda
                familia[2],   // Nombre del representante
                familia[3],   // Estado de la encuesta
                Boolean.FALSE
            });

            numero++;
        }

        if (familias.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "No hay familias pendientes para este encuestador."
            );
        }

    } catch (SQLException e) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Error al cargar las familias: " + e.getMessage()
        );

        e.printStackTrace();
    }
}



    
    public void setNombreEncuestador(String nombre) {
         this.nombreEncuestador = nombre;
    lblEncuestador.setText("Encuestador: " + nombre);
}
    
    
public javax.swing.JPanel getPanelContenido() {
    return pnlFamiliasAsignadas;
}

    
    public void setCedulaEncuestador(String cedula) {
    this.cedulaEncuestador = cedula;
    cargarFamilias(cedula);
}
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlLogos = new javax.swing.JPanel();
        lblDane = new javax.swing.JLabel();
        lblEnph = new javax.swing.JLabel();
        lblEncuestador = new javax.swing.JLabel();
        pnlFamiliasAsignadas = new javax.swing.JPanel();
        lblFamiliasAsignadas = new javax.swing.JLabel();
        lblTextoFamiliasAsignadas = new javax.swing.JLabel();
        btnVerFamilias = new javax.swing.JButton();
        spTablaFamilias = new javax.swing.JScrollPane();
        tblTablaFamilias = new javax.swing.JTable();
        pnlMenu = new javax.swing.JPanel();
        lblMenu = new javax.swing.JLabel();
        btnFamiliasAsignadas = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();
        btnVincularPortatil = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlLogos.setBackground(new java.awt.Color(255, 235, 229));
        pnlLogos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblDane.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/logo1.png"))); // NOI18N

        lblEnph.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/logo2.png"))); // NOI18N

        lblEncuestador.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        lblEncuestador.setText("Encuestador:");

        javax.swing.GroupLayout pnlLogosLayout = new javax.swing.GroupLayout(pnlLogos);
        pnlLogos.setLayout(pnlLogosLayout);
        pnlLogosLayout.setHorizontalGroup(
            pnlLogosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLogosLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblDane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblEnph)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblEncuestador)
                .addGap(77, 77, 77))
        );
        pnlLogosLayout.setVerticalGroup(
            pnlLogosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLogosLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlLogosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblDane)
                    .addComponent(lblEnph)
                    .addGroup(pnlLogosLayout.createSequentialGroup()
                        .addComponent(lblEncuestador)
                        .addGap(17, 17, 17)))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pnlFamiliasAsignadas.setBackground(new java.awt.Color(255, 255, 255));
        pnlFamiliasAsignadas.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 1, new java.awt.Color(0, 0, 0)));

        lblFamiliasAsignadas.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        lblFamiliasAsignadas.setText("Hogares asignados");

        lblTextoFamiliasAsignadas.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblTextoFamiliasAsignadas.setText("Total hogares asignados:");

        btnVerFamilias.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnVerFamilias.setText("Ver Hogares");
        btnVerFamilias.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btnVerFamilias.setBorderPainted(false);

        spTablaFamilias.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tblTablaFamilias.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tblTablaFamilias.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tblTablaFamilias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Número de vivienda", "Responsable del hogar", "Estado de la encuesta", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblTablaFamilias.setColumnSelectionAllowed(true);
        spTablaFamilias.setViewportView(tblTablaFamilias);
        tblTablaFamilias.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout pnlFamiliasAsignadasLayout = new javax.swing.GroupLayout(pnlFamiliasAsignadas);
        pnlFamiliasAsignadas.setLayout(pnlFamiliasAsignadasLayout);
        pnlFamiliasAsignadasLayout.setHorizontalGroup(
            pnlFamiliasAsignadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFamiliasAsignadasLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(btnVerFamilias, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 653, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFamiliasAsignadasLayout.createSequentialGroup()
                .addContainerGap(214, Short.MAX_VALUE)
                .addComponent(lblFamiliasAsignadas)
                .addGap(296, 296, 296))
            .addGroup(pnlFamiliasAsignadasLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnlFamiliasAsignadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTextoFamiliasAsignadas)
                    .addComponent(spTablaFamilias, javax.swing.GroupLayout.PREFERRED_SIZE, 699, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlFamiliasAsignadasLayout.setVerticalGroup(
            pnlFamiliasAsignadasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFamiliasAsignadasLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(lblFamiliasAsignadas)
                .addGap(47, 47, 47)
                .addComponent(lblTextoFamiliasAsignadas)
                .addGap(18, 18, 18)
                .addComponent(spTablaFamilias, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(btnVerFamilias, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlMenu.setBackground(new java.awt.Color(227, 223, 223));
        pnlMenu.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 0, new java.awt.Color(0, 0, 0)));

        lblMenu.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        lblMenu.setText("Menú");

        btnFamiliasAsignadas.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnFamiliasAsignadas.setText("Hogares asignados");
        btnFamiliasAsignadas.addActionListener(this::btnFamiliasAsignadasActionPerformed);

        btnCerrarSesion.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnCerrarSesion.setText("Cerrar sesión");
        btnCerrarSesion.addActionListener(this::btnCerrarSesionActionPerformed);

        btnVincularPortatil.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnVincularPortatil.setText("Vincular portátil");

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnVincularPortatil, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFamiliasAsignadas, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlMenuLayout.createSequentialGroup()
                            .addGap(93, 93, 93)
                            .addComponent(lblMenu))
                        .addGroup(pnlMenuLayout.createSequentialGroup()
                            .addGap(21, 21, 21)
                            .addComponent(btnCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblMenu)
                .addGap(64, 64, 64)
                .addComponent(btnFamiliasAsignadas)
                .addGap(18, 18, 18)
                .addComponent(btnVincularPortatil)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 251, Short.MAX_VALUE)
                .addComponent(btnCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlLogos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnlFamiliasAsignadas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlLogos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlFamiliasAsignadas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(pnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
   
    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void btnFamiliasAsignadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFamiliasAsignadasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnFamiliasAsignadasActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new P13_pantalla_Encuestador().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnFamiliasAsignadas;
    private javax.swing.JButton btnVerFamilias;
    private javax.swing.JButton btnVincularPortatil;
    private javax.swing.JLabel lblDane;
    private javax.swing.JLabel lblEncuestador;
    private javax.swing.JLabel lblEnph;
    private javax.swing.JLabel lblFamiliasAsignadas;
    private javax.swing.JLabel lblMenu;
    private javax.swing.JLabel lblTextoFamiliasAsignadas;
    private javax.swing.JPanel pnlFamiliasAsignadas;
    private javax.swing.JPanel pnlLogos;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JScrollPane spTablaFamilias;
    private javax.swing.JTable tblTablaFamilias;
    // End of variables declaration//GEN-END:variables
}
