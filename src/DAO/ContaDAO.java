package DAO;

import model.Cliente;
import model.Conta;
import model.Endereco;
import util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ContaDAO {

    // Exemplo de melhoria no método salvarConta
	public void salvarConta(Conta conta) {
	    String sql = "INSERT INTO contas (numeroConta, agenciaConta, saldo, clienteId) VALUES (?, ?, ?, ?)";
	    try (Connection conn = ConnectionFactory.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setInt(1, conta.getNumeroConta());
	        stmt.setString(2, conta.getAgenciaConta());
	        stmt.setDouble(3, conta.getSaldo());
	        stmt.setInt(4, conta.getCliente().getId());
	        stmt.executeUpdate();
	        System.out.println("Conta salva no banco de dados.");
	    } catch (SQLException e) {
	        System.out.println("Erro ao salvar a conta: " + e.getMessage());
	    }
	}

    // Método para deletar uma conta
    public void deletarConta(int contaId) {
        String sql = "DELETE FROM contas WHERE numeroConta = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        int rowsAffected = 0;

        try {
            conn = ConnectionFactory.getConnection(); 
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, contaId);
            rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Conta removida do banco de dados.");
            } else {
                System.out.println("Conta não encontrada. Não foi possível deletá-la.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao tentar deletar a conta: " + e.getMessage());
            e.printStackTrace(); 
        } finally {
            DBUtil.closeStatement(stmt);
            DBUtil.closeConnection(conn);
        }
    }

    // Método para buscar uma conta pelo número da conta
    public Conta buscarContaPorNumero(String numeroConta) {
        String sql = "SELECT * FROM contas WHERE numeroConta = ?";
        Conta conta = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, numeroConta);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int clienteId = rs.getInt("clienteId");
                Cliente cliente = buscarClientePorId(clienteId);

                conta = new Conta(
                    rs.getInt("numeroConta"),
                    rs.getString("agenciaConta"),
                    cliente,
                    rs.getDouble("saldo")
                );
                conta.depositar(rs.getDouble("saldo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
            DBUtil.closeConnection(conn);
        }

        return conta;
    }

    // Método para atualizar uma conta existente
    public void atualizarConta(Conta conta) {
        String sql = "UPDATE contas SET agenciaConta = ?, saldo = ? WHERE numeroConta = ?";

        try (Connection conn = ConnectionFactory.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, conta.getAgenciaConta());
            stmt.setDouble(2, conta.getSaldo());
            stmt.setInt(3, conta.getNumeroConta());
            
            stmt.executeUpdate();
            System.out.println("Conta atualizada com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método auxiliar para buscar o cliente pelo ID
    private Cliente buscarClientePorId(int clienteId) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        Cliente cliente = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, clienteId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                LocalDate dataNascimento = rs.getDate("dataNascimento").toLocalDate(); 
                String telefone = rs.getString("telefone");
                Endereco endereco = new Endereco(
                    rs.getString("cep"),
                    rs.getString("local"),
                    rs.getInt("numeroCasa"),
                    rs.getString("bairro"),
                    rs.getString("cidade"),
                    rs.getString("estado")
                );
                String senha = rs.getString("senha");

                cliente = new Cliente(
                    clienteId, 
                    nome,
                    cpf,
                    dataNascimento,
                    telefone,
                    endereco,
                    senha,
                    null
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
            DBUtil.closeConnection(conn);
        }

        return cliente;
    }
}
