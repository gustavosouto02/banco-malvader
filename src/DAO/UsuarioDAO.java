package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    private Connection connection;

    // Construtor que propaga a exceção SQLException
    public UsuarioDAO() throws SQLException {
        this.connection = ConnectionFactory.getConnection();  // Chama o método estático da classe ConnectionFactory
    }

    // Método para buscar a senha do usuário no banco de dados
    public String buscarSenhaPorUsuario(int idUsuario) {
        String sql = "SELECT senha FROM usuarios WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("senha");  // Retorna a senha (hash) do banco de dados
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Loga o erro, mas não interrompe a execução
        }
        return null;  // Retorna null se não encontrar o usuário ou ocorrer erro
    }

    // Método para salvar a senha (hash) no banco de dados
    public void salvarSenha(int idUsuario, String senhaHash) {
        String sql = "UPDATE usuarios SET senha = ? WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, senhaHash);  // Define o hash da senha
            stmt.setInt(2, idUsuario);     // Define o id do usuário
            stmt.executeUpdate();  // Executa a atualização
        } catch (SQLException e) {
            e.printStackTrace();  // Loga o erro, mas não interrompe a execução
        }
    }
}
