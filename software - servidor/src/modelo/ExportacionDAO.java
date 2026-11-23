
package modelo;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ExportacionDAO {

    private static final String RUTA_ORACLE =
            "C:\\EXPORTACION_ORACLE";

    private static final String EXPDP =
            "C:\\oraclexe\\app\\oracle\\product\\10.2.0\\server\\BIN\\expdp.exe";

    private static final String RUTA_PARAMETROS =
            "C:\\EXPORTACION_ENPH";

    public File exportar(int idSincronizacion, File carpetaDestino)
            throws Exception {

        if (idSincronizacion <= 0) {
            throw new Exception("ID de sincronización inválido.");
        }

        if (carpetaDestino == null ||
                !carpetaDestino.isDirectory()) {
            throw new Exception("La carpeta seleccionada no es válida.");
        }

        if (!carpetaDestino.canWrite()) {
            throw new Exception("No hay permisos para escribir en la carpeta.");
        }

        File ejecutable = new File(EXPDP);

        if (!ejecutable.exists()) {
            throw new Exception(
                "No se encontró expdp.exe en:\n" + EXPDP
            );
        }

        // Crear carpeta para los parámetros y registros
        File carpetaParametros = new File(RUTA_PARAMETROS);

        Files.createDirectories(carpetaParametros.toPath());

        String nombre = "ENPH_" + idSincronizacion;

        File parametros = new File(
                carpetaParametros,
                "parametros_exportacion.par"
        );

        File logProceso = new File(
                carpetaParametros,
                "proceso_exportacion.txt"
        );

        // Crear archivo de parámetros
        try (FileWriter writer = new FileWriter(parametros)) {

            writer.write(
                "USERID=ENCUESTA_CUADERNILLO_ENPH/ENCUESTA_CUADERNILLO_ENPH_VCAJ\n"
                + "DIRECTORY=EXPORTACIONES\n"
                + "DUMPFILE=" + nombre + ".DMP\n"
                + "LOGFILE=" + nombre + ".LOG\n"
                + "TABLES=TMP_EXP_REPRESENTANTE,TMP_EXP_ENCUESTADOR,TMP_EXP_ENCUESTA\n"
                + "QUERY=TMP_EXP_REPRESENTANTE:\"WHERE ID_SINCRONIZACION="
                + idSincronizacion
                + "\",TMP_EXP_ENCUESTADOR:\"WHERE ID_SINCRONIZACION="
                + idSincronizacion
                + "\",TMP_EXP_ENCUESTA:\"WHERE ID_SINCRONIZACION="
                + idSincronizacion + "\""
            );
        }

        // Comprobar la ruta física donde Oracle genera el DMP
        File archivoDmp = new File(
                RUTA_ORACLE,
                nombre + ".DMP"
        );

        // Evitar copiar un archivo anterior
        Files.deleteIfExists(archivoDmp.toPath());

        // Ejecutar Data Pump
        ProcessBuilder pb = new ProcessBuilder(
                EXPDP,
                "parfile=" + parametros.getAbsolutePath()
        );

        pb.redirectErrorStream(true);
        pb.redirectOutput(logProceso);

        Process proceso = pb.start();

        int resultado = proceso.waitFor();

        if (resultado != 0) {

            throw new Exception(
                "Oracle no pudo realizar la exportación.\n"
                + "Revisa el registro:\n"
                + logProceso.getAbsolutePath()
            );
        }

        // Comprobar que Oracle generó el archivo
        if (!archivoDmp.exists() || archivoDmp.length() == 0) {

            throw new Exception(
                "Oracle terminó, pero no se encontró el archivo DMP.\n"
                + "Verifica el DIRECTORY EXPORTACIONES y su ruta física.\n"
                + "Revisa: " + logProceso.getAbsolutePath()
            );
        }

        // Copiar el archivo a la carpeta o USB seleccionada
        File destino = new File(
                carpetaDestino,
                nombre + ".DMP"
        );

        Files.copy(
                archivoDmp.toPath(),
                destino.toPath(),
                StandardCopyOption.REPLACE_EXISTING
        );

        if (!destino.exists() ||
                destino.length() != archivoDmp.length()) {

            throw new Exception(
                "No se pudo verificar la copia del archivo DMP."
            );
        }

        return destino;
    }
}
