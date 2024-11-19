package DAO;

import model.Cliente;
import model.Conta;
import model.Endereco;
import util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContaDAO {

    // Salvar nova conta no banco de dados
    public void salvarConta(Conta conta) throws SQLException {
        String sql = "INSERT INTO contas (numeroConta, agenciaConta, saldo, clienteId) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, conta.getNumeroConta());
            stmt.setString(2, conta.getNumeroAgencia());
            stmt.setDouble(3, conta.getSaldo());
            stmt.setInt(4, conta.getCliente().getId());

            stmt.executeUpdate();
            System.out.println("Conta salva com sucesso.");
        } catch (SQLException e) {
            throw new SQLException("Erro ao salvar a conta: " + e.getMessage(), e);
        } finally {
            DBUtil.closeStatement(stmt);
            DBUtil.closeConnection(conn);
        }
    }

    // Deletar uma conta pelo número
    public void deletarConta(int contaId) throws SQLException {
        String sql = "DELETE FROM contas WHERE numeroConta = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, contaId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Conta não encontrada para exclusão.");
            } else {
                System.out.println("Conta excluída com sucesso.");
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao deletar a conta: " + e.getMessage(), e);
        } finally {
            DBUtil.closeStatement(stmt);
            DBUtil.closeConnection(conn);
        }
    }

    // Buscar uma conta pelo número
    public Conta buscarContaPorNumero(int numeroConta) throws SQLException {
        String sql = "SELECT * FROM contas WHERE numeroConta = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Conta conta = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, numeroConta);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Cliente cliente = buscarClientePorId(rs.getInt("clienteId"));
                conta = new Conta(
                        rs.getInt("numeroConta"),
                        rs.getString("agenciaConta"),
                        cliente,
                        rs.getDouble("saldo")
                );
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar a conta: " + e.getMessage(), e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
            DBUtil.closeConnection(conn);
        }

        return conta;
    }

    // Atualizar uma conta existente
    public void atualizarConta(Conta conta) throws SQLException {
        String sql = "UPDATE contas SET agenciaConta = ?, saldo = ? WHERE numeroConta = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, conta.getNumeroAgencia());
            stmt.setDouble(2, conta.getSaldo());
            stmt.setInt(3, conta.getNumeroConta());

            stmt.executeUpdate();
            System.out.println("Conta atualizada com sucesso.");
        } catch (SQLException e) {
            throw new SQLException("Erro ao atualizar a conta: " + e.getMessage(), e);
        } finally {
            DBUtil.closeStatement(stmt);
            DBUtil.closeConnection(conn);
        }
    }

    // Buscar cliente associado pelo ID
    private Cliente buscarClientePorId(int clienteId) throws SQLException {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cliente cliente = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, clienteId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = mapearCliente(rs);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar o cliente: " + e.getMessage(), e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
            DBUtil.closeConnection(conn);
        }

        return cliente;
    }

    // Mapeamento de ResultSet para objeto Cliente
    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("cpf"),
                rs.getDate("dataNascimento").toLocalDate(),
                rs.getString("telefone"),
                new Endereco(
                        rs.getString("cep"),
                        rs.getString("local"),
                        rs.getInt("numeroCasa"),
                        rs.getString("bairro"),
                        rs.getString("cidade"),
                        rs.getString("estado")
                ),
                rs.getString("senha"),
                null // Contas podem ser carregadas separadamente
        );
    }
}
