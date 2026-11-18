
package controlador;

import modelo.P12_RecuentistaEditarHogaresDAO;
import vista.P12_RecuentistaEditarHogares1;
import vista.P11_RecuentistaInformacionFamilia;

import java.util.List;

public class P12_RecuentistaEditarHogaresControlador {

    private final P12_RecuentistaEditarHogares1 vista;
    private final P12_RecuentistaEditarHogaresDAO dao;

    private final String numeroVivienda;
    private final String cedulaRecuentista;

    private final P11_RecuentistaInformacionFamilia pantalla11;
private int idMunicipio;
private int idBarrioSeleccionado;
    public P12_RecuentistaEditarHogaresControlador(
            P12_RecuentistaEditarHogares1 vista,
            String numeroVivienda,
            String cedulaRecuentista,
            P11_RecuentistaInformacionFamilia pantalla11) {

        this.vista = vista;
        this.numeroVivienda = numeroVivienda;
        this.cedulaRecuentista = cedulaRecuentista;
        this.pantalla11 = pantalla11;

        this.dao = new P12_RecuentistaEditarHogaresDAO();
    }

    // 1. CARGAR TODA LA INFORMACION DEL HOGAR.
    public void cargarInformacion() {

        try {

            // Consultar información del hogar.
            Object[] datos = dao.consultarInformacion(
                    numeroVivienda,
                    cedulaRecuentista
            );

            // Mostrar los datos en los campos.
            vista.mostrarDatos(datos);

            // Obtener el ID del barrio actual.
idBarrioSeleccionado =
        ((Number) datos[13]).intValue();

idMunicipio =
        ((Number) datos[14]).intValue();

            // Consultar los barrios de ese municipio.
            List<Object[]> barrios = dao.obtenerBarrios(idMunicipio);

            // Cargar los nombres y seleccionar el barrio actual.
            vista.cargarBarrios(barrios, idBarrioSeleccionado);

            // Consultar el estado de la encuesta.
            String estado = dao.consultarEstado(
                    numeroVivienda,
                    cedulaRecuentista
            );

            boolean encuestaIniciada = false;

            if (estado != null) {

                String estadoNormalizado = estado.trim().toUpperCase();

                encuestaIniciada =
                        !estadoNormalizado.equals("SIN INICIAR")
                        && !estadoNormalizado.equals("PENDIENTE")
                        && !estadoNormalizado.equals("NO INICIADA");
            }

            // Configurar los campos que se pueden editar.
            vista.configurarEdicion(encuestaIniciada);

        } catch (Exception e) {

            vista.mostrarError(
                    "No fue posible cargar la información del hogar: "
                    + e.getMessage()
            );
        }
    }

 
    // 2. ACTUALIZAR EL BARRIO SELECCIONADO.
    public void actualizarBarrioSeleccionado(int idBarrio) {

        if (idBarrio > 0) {
            idBarrioSeleccionado = idBarrio;
        }
    }


    // 3. REGISTRAR UN BARRIO NUEVO DESDE LA OPCION OTRA.
    public void seleccionarOtra() {

        String nombreBarrio = vista.pedirNombreBarrio();

        // Si cancela, recuperar el barrio anterior.
        if (nombreBarrio == null) {

            vista.seleccionarBarrio(idBarrioSeleccionado);
            return;
        }

        nombreBarrio = nombreBarrio.trim();

        if (nombreBarrio.isEmpty()) {

            vista.mostrarAdvertencia(
                    "Debe ingresar el nombre del barrio."
            );

            vista.seleccionarBarrio(idBarrioSeleccionado);
            return;
        }

        try {

            // Comprobar si el barrio ya existe.
            boolean existe = dao.validarBarrio(
                    nombreBarrio,
                    idMunicipio
            );

            if (existe) {

                vista.mostrarAdvertencia(
                        "Este barrio ya existe en el municipio. "
                        + "Ingrese otro nombre."
                );

                vista.seleccionarBarrio(idBarrioSeleccionado);
                return;
            }

            // Insertar el barrio y recuperar su nuevo ID.
            int nuevoId = dao.insertarBarrio(
                    nombreBarrio,
                    idMunicipio
            );

            // Consultar nuevamente los barrios.
            List<Object[]> barrios =
                    dao.obtenerBarrios(idMunicipio);

            // Recargar el combo y seleccionar el nuevo barrio.
            idBarrioSeleccionado = nuevoId;

            vista.cargarBarrios(
                    barrios,
                    nuevoId
            );

            vista.mostrarMensaje(
                    "Barrio registrado correctamente."
            );

        } catch (Exception e) {

            vista.seleccionarBarrio(idBarrioSeleccionado);

            vista.mostrarError(
                    "No fue posible registrar el barrio: "
                    + e.getMessage()
            );
        }
    }

    
    
    
    // 2. GUARDAR LOS CAMBIOS.
    public void guardar(
            String numeroNuevo,
            String direccion,
            int estrato,
            int idBarrio,
            String cedulaNueva,
            String nombre,
            String apellido,
            String telefono,
            String correo,
            String usuario,
            String contrasena) {

        try {

            dao.editarHogar(
                    numeroVivienda,
                    numeroNuevo,
                    direccion,
                    estrato,
                    idBarrio,
                    cedulaRecuentista,
                    cedulaNueva,
                    nombre,
                    apellido,
                    telefono,
                    correo,
                    usuario,
                    contrasena
            );

            vista.mostrarMensaje(
                    "Hogar actualizado correctamente."
            );

            if (pantalla11 != null) {
                pantalla11.setLocationRelativeTo(null);
                pantalla11.setVisible(true);
                pantalla11.toFront();
            }

            vista.dispose();

        } catch (Exception e) {

            vista.mostrarError(
                    "No fue posible actualizar el hogar: "
                    + e.getMessage()
            );
        }
    }

    // 3. CANCELAR LA EDICION.
    public void cancelar() {

        if (pantalla11 != null) {
            pantalla11.setLocationRelativeTo(null);
            pantalla11.setVisible(true);
            pantalla11.toFront();
        }

        vista.dispose();
    }
    
    
    
    
}
