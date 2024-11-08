package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionFactory {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/db_malvadeza"; 
        String user = "root"; 
        String password = "Gustavo02"; 

        // Defina a consulta de exclusão para excluir múltiplos usuários
        String query = "DELETE FROM usuario WHERE id_usuario IN (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            PreparedStatement stmt = connection.prepareStatement(query);

            // Defina os IDs dos usuários que você deseja excluir
            stmt.setInt(1, 1); // Excluir o usuário com id_usuario = 1
            stmt.setInt(2, 3); // Excluir o usuário com id_usuario = 3

            // Execute a query
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Usuários excluídos com sucesso!");
            } else {
                System.out.println("Nenhum usuário encontrado com os ids fornecidos.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
