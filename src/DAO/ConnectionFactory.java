package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    // Variáveis para conexão
    private static final String URL = "jdbc:mysql://localhost:3306/db_malvadeza";
    private static final String USER = "root";
    private static final String PASSWORD = "Gustavo02";

    // Método estático para obter a conexão
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new SQLException("Erro ao estabelecer conexão com o banco de dados", e);
        }
    }
}
