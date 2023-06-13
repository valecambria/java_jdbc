package org.example;

import java.sql.*;

public class EjemploJDBC {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/java_curso?serverTimezone=America/Argentina/Cordoba";
        String username = "root";
        String password = "vale123";
        try {
            Connection conn = DriverManager.getConnection(url, username, password); //se encarga de administrar las conexiones y drivers que tenemos disponibles
            Statement stmt = conn.createStatement();
            ResultSet resultado = stmt.executeQuery("SELECT * FROM productos");

            while (resultado.next()){
                System.out.println(resultado.getString("nombre"));
            }
            resultado.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}