/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sc.senac.mms.appsus.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Milton
 */
public class CriaConexao {

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Conectando ao banco");

            return DriverManager.getConnection("jdbc:mysql://localhost/list_sus", "root", "");
        } catch (ClassNotFoundException e) {
            System.out.println("Erro ao conectar verifique CriaConexao");
            throw new SQLException(e.getMessage());

        }

    }

}
