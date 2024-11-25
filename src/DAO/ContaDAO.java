package DAO;

import model.Conta;
import java.util.logging.Logger;
import java.util.logging.Level;

import util.DBUtil;

import java.sql.*;

public class ContaDAO {
	
	private static final Logger logger = Logger.getLogger(ContaDAO.class.getName());


    // Salvar nova conta
	public boolean salvarConta(Conta conta) throws SQLException {
	    if (conta == null) {
	        throw new IllegalArgumentException("Conta não pode ser nula.");
	    }

	    String sql = "INSERT INTO conta (numero_conta, agencia, saldo, tipo_conta, id_cliente) VALUES (?, ?, ?, ?, ?)";
	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

	        stmt.setString(1, conta.getNumeroConta());
	        stmt.setString(2, conta.getAgencia());
	        stmt.setDouble(3, conta.getSaldo());
	        stmt.setString(4, conta.getTipoConta());
	        stmt.setInt(5, conta.getId_cliente());

	        int rowsAffected = stmt.executeUpdate();
	        if (rowsAffected > 0) {
	            try (ResultSet rs = stmt.getGeneratedKeys()) {
	                if (rs.next()) {
	                    conta.setId_conta(rs.getInt(1));  // Recupera o ID gerado
	                    return true;
	                }
	            }
	        }
	    } catch (SQLException e) {
	    	logger.log(Level.SEVERE, "Erro ao salvar conta no banco de dados.", e);
	    }
	    return false;
	}


    // Buscar conta por ID
    public Conta buscarContaPorId(int idConta) throws SQLException {
        if (idConta <= 0) {
            throw new IllegalArgumentException("ID da conta deve ser maior que zero.");
        }

        String sql = "SELECT * FROM conta WHERE id_conta = ?";
        Conta conta = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idConta);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    conta = new Conta(
                            rs.getString("numero_conta"),
                            rs.getString("agencia"),
                            rs.getDouble("saldo"),
                            rs.getString("tipo_conta"),
                            rs.getInt("id_cliente")
                    );
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar conta por ID: " + e.getMessage(), e);
        }

        if (conta == null) {
            throw new SQLException("Conta com ID " + idConta + " não encontrada.");
        }

        return conta;
    }

    // Atualizar conta
    public void atualizarConta(Conta conta) throws SQLException {
        if (conta == null) {
            throw new IllegalArgumentException("Conta não pode ser nula.");
        }

        String sql = "UPDATE conta SET numero_conta = ?, agencia = ?, saldo = ?, tipo_conta = ?, id_cliente = ? WHERE id_conta = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, conta.getNumeroConta());
            stmt.setString(2, conta.getAgencia());
            stmt.setDouble(3, conta.getSaldo());
            stmt.setString(4, conta.getTipoConta());
            stmt.setInt(5, conta.getId_cliente());
            stmt.setInt(6, conta.getId_conta());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao atualizar a conta: " + e.getMessage(), e);
        }
    }

    // Deletar conta
    public void deletarConta(int idConta) throws SQLException {
        if (idConta <= 0) {
            throw new IllegalArgumentException("ID da conta deve ser maior que zero.");
        }

        String sql = "DELETE FROM conta WHERE id_conta = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idConta);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao deletar conta: " + e.getMessage(), e);
        }
    }
}
