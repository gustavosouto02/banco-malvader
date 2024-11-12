package controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DAO.ConnectionFactory;
import DAO.FuncionarioDAO;
import model.Endereco;
import model.Funcionario;

public class FuncionarioController {
    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO(); 

    // Métodos de operação do Funcionário
    public Funcionario buscarFuncionarioPorId(int id) {
        Funcionario funcionario = null;
        String sql = "SELECT * FROM funcionarios WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, id); 
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Agora buscamos a senha real, que estará no banco de dados
                    String senha = rs.getString("senha");

                    funcionario = new Funcionario(
                        rs.getInt("id"), 
                        rs.getString("nome"), 
                        rs.getString("cpf"), 
                        rs.getDate("dataNascimento").toLocalDate(),
                        rs.getString("telefone"),
                        stringToEndereco(rs.getString("endereco")), 
                        rs.getString("codigoFuncionario"), 
                        rs.getString("cargo"), 
                        senha, 
                        null, //contaDAO, se necessário
                        null  // ClienteDAO, se necessário
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return funcionario;
    }


    private Endereco stringToEndereco(String enderecoString) {
        String[] enderecoParts = enderecoString.split(","); 
        if (enderecoParts.length != 6) {
            throw new IllegalArgumentException("Formato de endereço inválido.");
        }
        return new Endereco(
            enderecoParts[0].trim(),  // cep
            enderecoParts[1].trim(),  // local
            Integer.parseInt(enderecoParts[2].trim()),  // Número da casa
            enderecoParts[3].trim(),  // Bairro
            enderecoParts[4].trim(),  // Cidade
            enderecoParts[5].trim()   // Estado
        );
    }


	public void alterarDadosFuncionario(Funcionario funcionarioAlterado) {
        funcionarioDAO.atualizarFuncionario(funcionarioAlterado);
        System.out.println("Dados do funcionário alterados com sucesso.");
    }

    public void cadastrarFuncionario(Funcionario novoFuncionario) {
        funcionarioDAO.salvarFuncionario(novoFuncionario);
        System.out.println("Funcionário cadastrado com sucesso!");
    }

    public void gerarRelatorio() {
        System.out.println("Gerando relatório...");
        System.out.println("Relatório gerado com sucesso!");
    }
}
