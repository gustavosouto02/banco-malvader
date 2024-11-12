package DAO;

import model.Endereco;
import model.Funcionario;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class FuncionarioDAO {

    // Método para salvar o funcionário no banco de dados
    public void salvarFuncionario(Funcionario funcionario) {
        String senhaHash = BCrypt.hashpw(funcionario.getSenha(), BCrypt.gensalt());
        String sql = "INSERT INTO funcionarios (nome, cpf, dataNascimento, telefone, endereco, cargo, senha) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setDate(3, Date.valueOf(funcionario.getDataNascimento()));
            stmt.setString(4, funcionario.getTelefone());
            stmt.setString(5, enderecoToString(funcionario.getEndereco()));
            stmt.setString(6, funcionario.getCargo());
            stmt.setString(7, senhaHash); // Armazena a senha hasheada no banco

            stmt.executeUpdate();
            System.out.println("Funcionario " + funcionario.getNome() + " salvo no banco de dados.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para atualizar o funcionário no banco de dados
    public void atualizarFuncionario(Funcionario funcionario) {
        String sql = "UPDATE funcionarios SET nome = ?, cpf = ?, dataNascimento = ?, telefone = ?, endereco = ?, cargo = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setDate(3, Date.valueOf(funcionario.getDataNascimento()));
            stmt.setString(4, funcionario.getTelefone());
            stmt.setString(5, enderecoToString(funcionario.getEndereco()));
            stmt.setString(6, funcionario.getCargo());
            stmt.setInt(7, funcionario.getId());

            stmt.executeUpdate();
            System.out.println("Funcionario " + funcionario.getNome() + " atualizado no banco de dados.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para deletar o funcionário do banco de dados
    public void deletarFuncionario(int idFuncionario) {
        String sql = "DELETE FROM funcionarios WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFuncionario);
            stmt.executeUpdate();
            System.out.println("Funcionario com ID " + idFuncionario + " deletado do banco de dados.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para buscar um funcionário por ID
    public Funcionario buscarFuncionarioPorId(int id, ContaDAO contaDAO, ClienteDAO clienteDAO) {
        Funcionario funcionario = null;
        String sql = "SELECT * FROM funcionarios WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String senhaHash = rs.getString("senha");

                    funcionario = new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getDate("dataNascimento").toLocalDate(),
                        rs.getString("telefone"),
                        stringToEndereco(rs.getString("endereco")),
                        rs.getString("codigoFuncionario"),
                        rs.getString("cargo"),
                        senhaHash, // Agora a senha real está sendo passada
                        contaDAO,
                        clienteDAO
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return funcionario;
    }

    private String enderecoToString(Endereco endereco) {
        return endereco.getLocal() + ", " + endereco.getNumeroCasa() + ", " +
               endereco.getBairro() + ", " + endereco.getCidade() + ", " + endereco.getEstado();
    }

    private Endereco stringToEndereco(String enderecoString) {
        String[] enderecoParts = enderecoString.split(",");
        return new Endereco(
            enderecoParts[0].trim(),  // local
            enderecoParts[1].trim(),  // cep
            Integer.parseInt(enderecoParts[2].trim()),  // Número da casa
            enderecoParts[3].trim(),  // Bairro
            enderecoParts[4].trim(),  // Cidade
            enderecoParts[5].trim()   // Estado
        );
    }
}
