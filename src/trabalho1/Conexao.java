/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalho1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author todesco
 */
public class Conexao {
 
    public Connection conecta(){
        String dbURL = "jdbc:mysql://localhost:3306/Trabalho1?useTimezone=true&serverTimezone=UTC";
        String username = "root";
        String password = "root";
        
        try {
            
            Connection conexao = DriverManager.getConnection(dbURL, username, password);
            
            if (conexao != null){
                System.out.println("Conectado");
            }
            
            return conexao;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
}

