/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

public class Usuario {

    private String numero;
    private String cedula;
    private String nombre;
    private String apellido;
    private String rol;
    private String usuario;
    private String contrasena;

    private Integer idRegion;
    private Integer idDepartamento;
    private Integer idMunicipio;

    public Usuario(String numero, String cedula, String nombre,
                   String apellido, String rol) {
        this.numero = numero;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.rol = rol;
    }

    public String getNumero() {
        return numero;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getRol() {
        return rol;
    }
    
    public Usuario(
        String cedula,
        String nombre,
        String apellido,
        String usuario,
        String contrasena,
        String rol,
        Integer idRegion,
        Integer idDepartamento,
        Integer idMunicipio) {

    this.cedula = cedula;
    this.nombre = nombre;
    this.apellido = apellido;
    this.usuario = usuario;
    this.contrasena = contrasena;
    this.rol = rol;
    this.idRegion = idRegion;
    this.idDepartamento = idDepartamento;
    this.idMunicipio = idMunicipio;
}

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public Integer getIdRegion() {
        return idRegion;
    }

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public Integer getIdMunicipio() {
        return idMunicipio;
    }
}