/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package controlador;

import java.io.File;
import modelo.ExportarDatosDAO;

public class ExportarDatosControlador {

    private final ExportarDatosDAO dao;

    public ExportarDatosControlador() {
        dao = new ExportarDatosDAO();
    }

    public File exportar(File carpetaDestino) throws Exception {
        return dao.exportarDatos(carpetaDestino);
    }
    
    public void iniciarExportacion(vista.P11_Sincronizar_Encuestador pantalla) {

    java.io.File carpeta = pantalla.seleccionarCarpetaDestino();

    // Si cancela la selección, no se realiza la exportación.
    if (carpeta == null) {
        return;
    }

    try {
        java.io.File archivoZip = exportar(carpeta);

        pantalla.mostrarMensaje(
            "La exportación se realizó correctamente.\n"
            + "Archivo generado: " + archivoZip.getAbsolutePath()
        );

    } catch (Exception e) {
        pantalla.mostrarMensaje(
            "No fue posible realizar la exportación.\n"
            + e.getMessage()
        );
    }
}
}
