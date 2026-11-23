
package controlador;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Map;
import javax.swing.JOptionPane;

import modelo.P18_EncuestadorVerHogarDAO;
import vista.P17_EncuestadorHogaresAsignados1;
import vista.P18_EncuestadorVerHogar1;

public class P18_EncuestadorVerHogarControlador {

    private final P18_EncuestadorVerHogar1 vista;
    private final P17_EncuestadorHogaresAsignados1 vistaAnterior;
    private final P18_EncuestadorVerHogarDAO dao;
    private final int numeroVivienda;

    public P18_EncuestadorVerHogarControlador(
            P18_EncuestadorVerHogar1 vista,
            P17_EncuestadorHogaresAsignados1 vistaAnterior,
            Connection conexionUsuario,
            int numeroVivienda) {

        if (conexionUsuario == null) {
            throw new IllegalStateException(
                    "No hay una conexión activa con Oracle."
            );
        }

        this.vista = vista;
        this.vistaAnterior = vistaAnterior;
        this.numeroVivienda = numeroVivienda;

        this.dao = new P18_EncuestadorVerHogarDAO(
                conexionUsuario
        );
    }

    public void iniciar() {

        vista.getBtnVolver().addActionListener(e -> volver());

        cargarInformacionHogar();
    }

    private void cargarInformacionHogar() {

        try {

            Map<String, Object> datos =
                    dao.consultarHogar(numeroVivienda);

            if (datos.isEmpty()) {

                JOptionPane.showMessageDialog(
                        vista,
                        "No se encontró información del hogar."
                );

                return;
            }

            vista.mostrarNumeroVivienda(
                    texto(datos, "NUMERO_VIVIENDA")
            );

            vista.mostrarUbicacion(
                    texto(datos, "UBICACION")
            );

            vista.mostrarDireccion(
                    texto(datos, "DIRECCION")
            );

            vista.mostrarBarrio(
                    texto(datos, "BARRIO")
            );

            vista.mostrarEstrato(
                    texto(datos, "ESTRATO")
            );

            vista.mostrarEstado(
                    texto(datos, "ESTADO")
            );

            vista.mostrarDiaEncuesta(
                    texto(datos, "DIA_ENCUESTA")
            );

            vista.mostrarFechaInicio(
                    fecha(datos.get("FECHA_INICIO"))
            );

            vista.mostrarFechaFinal(
                    fecha(datos.get("FECHA_FIN"))
            );

            vista.mostrarRepresentante(
                    texto(datos, "REPRESENTANTE")
            );

            vista.mostrarCedula(
                    texto(datos, "CEDULA")
            );

            vista.mostrarTelefono(
                    texto(datos, "TELEFONO")
            );

            vista.mostrarCorreo(
                    texto(datos, "CORREO")
            );

            vista.mostrarNumeroSerie(
                    texto(datos, "NUMERO_SERIE_PORTATIL")
            );

            vista.mostrarMarca(
                    texto(datos, "MARCA")
            );

            vista.mostrarModelo(
                    texto(datos, "MODELO")
            );

            vista.mostrarFechaRecogida(
                    fecha(datos.get("FECHA_RECOGIDA"))
            );

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    vista,
                    "Error al consultar el hogar: "
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private String texto(
            Map<String, Object> datos,
            String columna) {

        Object valor = datos.get(columna);

        return valor == null ? "No disponible" : valor.toString();
    }

    private String fecha(Object valor) {

        if (valor == null) {
            return "No disponible";
        }

        java.util.Date fecha = (java.util.Date) valor;

        return new SimpleDateFormat("dd/MM/yyyy").format(fecha);
    }

    private void volver() {

        vista.dispose();

    }
}
