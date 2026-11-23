/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ExportarDatosDAO {

    public File exportarDatos(File carpetaDestino) throws Exception {

        File archivoZip = new File(
            carpetaDestino,
            "EXPORTACION_ENPH.zip"
        );

        try (Connection con = conexion.conectar()) {

            if (con == null) {
                throw new Exception(
                    "No fue posible conectar con PORTATIL_ENPH."
                );
            }

            try (ZipOutputStream zip = new ZipOutputStream(
                    new FileOutputStream(archivoZip))) {

                exportarTabla(con, zip,
                    "PRODUCTO",
                    "{call PORTATIL_ENPH.PKG_EXPORTAR_ENPH.SP_EXP_PRODUCTO(?)}");

                exportarTabla(con, zip,
                    "LUGAR_COMPRA_SERVICIO",
                    "{call PORTATIL_ENPH.PKG_EXPORTAR_ENPH.SP_EXP_LUGAR_COMPRA(?)}");

                exportarTabla(con, zip,
                    "UNIDAD_MEDIDA",
                    "{call PORTATIL_ENPH.PKG_EXPORTAR_ENPH.SP_EXP_UNIDAD_MEDIDA(?)}");

                exportarTabla(con, zip,
                    "FORMA_ADQUISICION",
                    "{call PORTATIL_ENPH.PKG_EXPORTAR_ENPH.SP_EXP_FORMA_ADQUISICION(?)}");

                exportarTabla(con, zip,
                    "GASTOS_DIARIOS",
                    "{call PORTATIL_ENPH.PKG_EXPORTAR_ENPH.SP_EXP_GASTOS_DIARIOS(?)}");
            }
        }

        return archivoZip;
    }

    private void exportarTabla(
            Connection con,
            ZipOutputStream zip,
            String nombreArchivo,
            String procedimiento) throws Exception {

        try (CallableStatement cs =
                con.prepareCall(procedimiento)) {

            cs.registerOutParameter(1, Types.REF_CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                if (!rs.isBeforeFirst()) {
                    return;
                }

                zip.putNextEntry(
                    new ZipEntry(nombreArchivo + ".csv")
                );

                try (BufferedWriter writer =
                        new BufferedWriter(
                            new OutputStreamWriter(
                                new ZipEntryOutputStream(zip),
                                StandardCharsets.UTF_8))) {

                    ResultSetMetaData meta = rs.getMetaData();
                    int columnas = meta.getColumnCount();

                    // Encabezados
                    for (int i = 1; i <= columnas; i++) {
                        if (i > 1) writer.write(";");
                        writer.write(meta.getColumnName(i));
                    }

                    writer.newLine();

                    // Registros
                    while (rs.next()) {

                        for (int i = 1; i <= columnas; i++) {

                            if (i > 1) writer.write(";");

                            Object valor = rs.getObject(i);

                            if (valor != null) {
                                String texto = valor.toString()
                                    .replace("\"", "\"\"");

                                writer.write("\"" + texto + "\"");
                            }
                        }

                        writer.newLine();
                    }
                }

                zip.closeEntry();
            }
        }
    }

    private static class ZipEntryOutputStream
            extends java.io.OutputStream {

        private final ZipOutputStream zip;

        public ZipEntryOutputStream(ZipOutputStream zip) {
            this.zip = zip;
        }

        @Override
        public void write(int b) throws java.io.IOException {
            zip.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len)
                throws java.io.IOException {
            zip.write(b, off, len);
        }

        @Override
        public void close() {
            // No cerrar el ZIP desde el BufferedWriter.
        }
    }
}
