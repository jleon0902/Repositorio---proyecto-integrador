/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {

    public static Connection conectar() {

        String ip = "192.168.254.215";
        String localh = "localhost";
        String puerto = "1521";
        String sid = "XE";

        // Conexion local
        String url = "jdbc:oracle:thin:@" 
                + localh + ":" + puerto + ":" + sid;

        String usuario = "enph_vcaj";
        String contraseña = "enph_vcaj_1234";

        try {

            Connection conexion = DriverManager.getConnection(
                    url,
                    usuario,
                    contraseña
            );

            System.out.println("Conexion exitosa");

            return conexion;

        } catch (SQLException e) {

            System.out.println("Error de conexion");
            e.printStackTrace();

            return null;
        }
    }
}
