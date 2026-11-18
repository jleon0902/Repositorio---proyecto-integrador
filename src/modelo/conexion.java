/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {

    private static final String URL =
            "jdbc:oracle:thin:@localhost:1521:XE";

    private static final String USUARIO =
            "PERSONA_PORTATIL";

    private static final String CONTRASENA =
            "PERSONA_PORTATIL_VCAJ";

    public static Connection conectar() {

        try {

            Connection conexion = DriverManager.getConnection(
                    URL,
                    USUARIO,
                    CONTRASENA
            );

            System.out.println("Conexion exitosa con PERSONA_PORTATIL");

            return conexion;

        } catch (SQLException e) {

            System.out.println("Error al conectar con Oracle:");
            System.out.println(e.getMessage());

            return null;
        }
    }
}


