
package controlador;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import modelo.PortatilDAO;
import vista.P5_AdministradorRegistrarPortatiles1;

public class P5_AdministradorRegistrarPortatiles1Controlador {

    private final P5_AdministradorRegistrarPortatiles1 vista;
    private final PortatilDAO portatilDAO;

    private Connection conexionActiva;

    public P5_AdministradorRegistrarPortatiles1Controlador(
            P5_AdministradorRegistrarPortatiles1 vista) {

        this.vista = vista;
        this.portatilDAO = new PortatilDAO();
    }

    public void establecerConexion(Connection conexion) {

        try {
            if (conexion == null || conexion.isClosed()) {
                conexionActiva = null;
                vista.mostrarError(
                        "No hay una conexión activa con la base de datos."
                );
                return;
            }

            String usuario = conexion.getMetaData()
                    .getUserName();

            if (usuario == null
                    || !usuario.trim().equalsIgnoreCase("ADMINISTRADOR")) {

                conexionActiva = null;
                vista.mostrarError(
                        "La pantalla debe trabajar con la conexión de ADMINISTRADOR."
                );
                return;
            }

            conexionActiva = conexion;

        } catch (SQLException e) {
            conexionActiva = null;
            vista.mostrarError(
                    "No fue posible verificar la conexión: " + e.getMessage()
            );
        }
    }



public void guardar() {
    
    // Confirmar antes de iniciar el proceso de guardado
int respuesta = javax.swing.JOptionPane.showConfirmDialog(
        vista,
        "¿Está seguro de que desea guardar este portátil?",
        "Confirmar registro",
        javax.swing.JOptionPane.YES_NO_OPTION,
        javax.swing.JOptionPane.QUESTION_MESSAGE
);

// Si selecciona NO, cancelar el proceso
if (respuesta != javax.swing.JOptionPane.YES_OPTION) {
    return;
}

    if (conexionActiva == null) {
        vista.mostrarError(
                "No hay una conexión disponible para registrar el portátil."
        );
        return;
    }

    // Obtener los datos de las tres cajas de texto
    String numeroSerie = vista.getNumeroSerie();
    String marca = vista.getMarca();
    String modelo = vista.getModelo();

    // Expresiones regulares
    String regexNumeroSerie = "^[A-Za-z0-9-]+$";
    String regexMarca = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$";
    String regexModelo = "^[A-Za-z0-9 -]+$";

    // Revisar si cada campo está vacío
    boolean numeroSerieVacio = numeroSerie.isBlank();
    boolean marcaVacia = marca.isBlank();
    boolean modeloVacio = modelo.isBlank();

    // Revisar si cada campo tiene un formato incorrecto
    boolean numeroSerieIncorrecto =
            !numeroSerieVacio && !numeroSerie.matches(regexNumeroSerie);

    boolean marcaIncorrecta =
            !marcaVacia && !marca.matches(regexMarca);

    boolean modeloIncorrecto =
            !modeloVacio && !modelo.matches(regexModelo);

    // Determinar cuáles campos presentan errores
    boolean errorNumeroSerie = numeroSerieVacio || numeroSerieIncorrecto;
    boolean errorMarca = marcaVacia || marcaIncorrecta;
    boolean errorModelo = modeloVacio || modeloIncorrecto;

    int cantidadErrores = 0;

    if (errorNumeroSerie) {
        cantidadErrores++;
    }

    if (errorMarca) {
        cantidadErrores++;
    }

    if (errorModelo) {
        cantidadErrores++;
    }

    // Si existen errores, mostrar el mensaje correspondiente
    if (cantidadErrores > 0) {

        StringBuilder mensaje = new StringBuilder();

        if (cantidadErrores == 1) {

            mensaje.append("Se encontró un error en el siguiente campo:\n\n");

        } else if (cantidadErrores == 2) {

            mensaje.append("Se encontraron errores en dos campos:\n\n");

        } else {

            mensaje.append("Los tres campos presentan errores:\n\n");
        }

        // Validación independiente y combinada del número de serie
        if (errorNumeroSerie) {

            mensaje.append("NÚMERO DE SERIE:\n");

            if (numeroSerieVacio) {
                mensaje.append(
                        "- El campo está vacío. Debe ingresar el número de serie del portátil.\n\n"
                );
            } else {
                mensaje.append(
                        "- El formato es incorrecto. Solo se permiten letras sin tilde (A-Z), números (0-9) y guiones medios (-). No se permiten espacios, tildes, eñes ni otros símbolos.\n\n"
                );
            }
        }

        // Validación independiente y combinada de la marca
        if (errorMarca) {

            mensaje.append("MARCA:\n");

            if (marcaVacia) {
                mensaje.append(
                        "- El campo está vacío. Debe ingresar la marca del portátil.\n\n"
                );
            } else {
                mensaje.append(
                        "- El formato es incorrecto. Solo se permiten letras, tildes, eñes y espacios. No se permiten números, guiones ni símbolos especiales.\n\n"
                );
            }
        }

        // Validación independiente y combinada del modelo
        if (errorModelo) {

            mensaje.append("MODELO:\n");

            if (modeloVacio) {
                mensaje.append(
                        "- El campo está vacío. Debe ingresar el modelo del portátil.\n\n"
                );
            } else {
                mensaje.append(
                        "- El formato es incorrecto. Solo se permiten letras sin tilde, números, espacios y guiones medios (-). No se permiten símbolos especiales.\n\n"
                );
            }
        }

        mensaje.append(
                "Corrija los campos indicados e intente guardar nuevamente."
        );

        vista.mostrarAdvertencia(mensaje.toString());
        return;
    }

    // Registrar el portátil cuando los tres campos son válidos
    try {

        int resultado = portatilDAO.registrarPortatil(
                conexionActiva,
                numeroSerie,
                marca,
                modelo
        );

        if (resultado == 1) {

            if (!conexionActiva.getAutoCommit()) {
                conexionActiva.commit();
            }

            vista.mostrarInformacion(
                    "El portátil se registró correctamente."
            );

            vista.limpiarCampos();

        } else if (resultado == 0) {

            vista.mostrarAdvertencia(
                    "El número de serie " + numeroSerie
                    + " ya se encuentra registrado. "
                    + "Ingrese un número de serie diferente."
            );

        } else {

            vista.mostrarError(
                    "No fue posible registrar el portátil."
            );
        }

    } catch (SQLException e) {

        vista.mostrarError(
                "Error al registrar el portátil: " + e.getMessage()
        );
    }
}



    public void cancelar() {

        if (vista.confirmarCancelacion()) {
            vista.limpiarCampos();
        }
    }
}
