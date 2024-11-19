package DAO;

import model.Cliente;
import model.Endereco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {
    private final Connection connection;

    public ClienteDAO(Connection connection) {
        this.connection = connection;
    }

    public Cliente buscarClientePorId(int id) throws SQLException {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        Cliente cliente = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = mapearCliente(rs);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar cliente com ID " + id + ": " + e.getMessage(), e);
        }

        return cliente;
    }

    public void atualizarSaldo(int contaId, double valor) throws SQLException {
        String sql = "UPDATE contas SET saldo = saldo + ? WHERE numeroConta = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, valor);
            stmt.setInt(2, contaId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao atualizar saldo para a conta " + contaId + ": " + e.getMessage(), e);
        }
    }

    public double buscarSaldoPorContaId(int contaId) throws SQLException {
        String sql = "SELECT saldo FROM contas WHERE numeroConta = ?";
        double saldo = 0.0;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, contaId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                saldo = rs.getDouble("saldo");
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar saldo da conta " + contaId + ": " + e.getMessage(), e);
        }

        return saldo;
    }

    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        return new Cliente(
            rs.getInt("id"),
            rs.getString("nome"),
            rs.getString("cpf"),
            rs.getDate("data_nascimento").toLocalDate(),
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
            null 
        );
    }
}
