package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    // Tornando as variáveis estáticas
    private static String url = "jdbc:mysql://localhost:3306/db_malvadeza"; 
    private static String user = "root"; 
    private static String password = "Gustavo02"; 

    // Método estático que retorna a conexão
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new SQLException("Erro ao estabelecer conexão com o banco de dados", e);
        }
    }
}
