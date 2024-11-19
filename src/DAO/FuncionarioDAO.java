package DAO;

import model.Funcionario;
import model.Endereco;
import org.mindrot.jbcrypt.BCrypt;
import util.DBUtil;

import java.sql.*;

public class FuncionarioDAO {

    public void salvarFuncionario(Funcionario funcionario) {
        String sql = "INSERT INTO funcionarios (nome, cpf, dataNascimento, telefone, endereco, cargo, senha) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String senhaHash = BCrypt.hashpw(funcionario.getSenha(), BCrypt.gensalt());

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setDate(3, Date.valueOf(funcionario.getDataNascimento()));
            stmt.setString(4, funcionario.getTelefone());
            stmt.setString(5, enderecoToString(funcionario.getEndereco()));
            stmt.setString(6, funcionario.getCargo());
            stmt.setString(7, senhaHash);

            stmt.executeUpdate();
            System.out.println("Funcionário salvo com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao salvar o funcionário: " + e.getMessage());
        } finally {
            DBUtil.closeStatement(stmt);
            DBUtil.closeConnection(conn);
        }
    }

    public void atualizarFuncionario(Funcionario funcionario) {
        String sql = "UPDATE funcionarios SET nome = ?, cpf = ?, dataNascimento = ?, telefone = ?, endereco = ?, cargo = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setDate(3, Date.valueOf(funcionario.getDataNascimento()));
            stmt.setString(4, funcionario.getTelefone());
            stmt.setString(5, enderecoToString(funcionario.getEndereco()));
            stmt.setString(6, funcionario.getCargo());
            stmt.setInt(7, funcionario.getId());

            stmt.executeUpdate();
            System.out.println("Funcionário atualizado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o funcionário: " + e.getMessage());
        } finally {
            DBUtil.closeStatement(stmt);
            DBUtil.closeConnection(conn);
        }
    }

    public Funcionario buscarFuncionarioPorId(int id) {
        String sql = "SELECT * FROM funcionarios WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Funcionario funcionario = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                funcionario = new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getDate("dataNascimento").toLocalDate(),
                        rs.getString("telefone"),
                        stringToEndereco(rs.getString("endereco")),
                        rs.getString("codigoFuncionario"),
                        rs.getString("cargo"),
                        rs.getString("senha")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar o funcionário: " + e.getMessage());
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
            DBUtil.closeConnection(conn);
        }

        return funcionario;
    }

    // Converter objeto Endereco em String
    private String enderecoToString(Endereco endereco) {
        return endereco.getLocal() + ", " + endereco.getNumeroCasa() + ", " +
               endereco.getBairro() + ", " + endereco.getCidade() + ", " + endereco.getEstado();
    }

    // Converter String em objeto Endereco
    private Endereco stringToEndereco(String enderecoString) {
        String[] parts = enderecoString.split(",");
        return new Endereco(
                parts[0].trim(),
                parts[1].trim(),
                Integer.parseInt(parts[2].trim()),
                parts[3].trim(),
                parts[4].trim(),
                parts[5].trim()
        );
    }
}
