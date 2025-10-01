package org.example;

import java.sql.*;

public class Main {
    static Connection conn;
    static Statement stmt;

    public static void main(String[] args) {


        try {
            //Registramos el controlador JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Abrimos la conexión a la BBDD
            System.out.println("Conectando a la base de datos...");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pruebas", "root", "");



            /*
            // Ejecutamos la consulta
            System.out.println("Creando la tabla en la base de datos...");
            stmt = conn.createStatement();
            String sql = "CREATE TABLE empleados " +
                    "(id INTEGER not NULL, " +
                    " nombre VARCHAR(255), " +
                    " puesto VARCHAR(255), " +
                    " salario DECIMAL(10, 2), " +
                    " PRIMARY KEY ( id ))";
           */

            System.out.println("Insertando datos en la talba");
            stmt = conn.createStatement();
            String sql = "INSERT into empleados values (null,'Albán','jefecillo',100)";
            String sql1 = "delete from empleados where id=1";
            String sql2 = "select * from empleados";



//            stmt.executeUpdate(sql);
            stmt.execute(sql2);
            System.out.println(stmt.execute(sql2));


            ResultSet resultado = stmt.executeQuery(sql2);
            while(resultado.next()){
                System.out.println("ID: "+ resultado.getInt("id") +
                        ", Nombre: " + resultado.getString("nombre") +
                        ", Salario: "+resultado.getString("salario"));
            }


            // PreparedStatement

            String sqlP = "SELECT * FROM empleados WHERE nombre like ?";
            PreparedStatement pst = conn.prepareStatement(sqlP);
            pst.setString(1,"Albán");
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                System.out.println("ID: "+ rs.getInt("id") +
                        ", Nombre: " + rs.getString("nombre"));
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
