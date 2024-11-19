package controller;

import DAO.ClienteDAO;
import DAO.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteController {
    private ClienteDAO clienteDAO;

    public ClienteController(Connection connection) {
        this.clienteDAO = new ClienteDAO(connection);
    }


    public ClienteController() throws SQLException {
        this(ConnectionFactory.getConnection()); 
    }

    public void exibirSaldo(int contaId) throws SQLException {
        double saldo = clienteDAO.buscarSaldoPorContaId(contaId);
        System.out.printf("Saldo atual da conta %d: R$ %.2f\n", contaId, saldo);
    }

    public void realizarDeposito(int contaId, double valor) throws SQLException {
        if (valor > 0) {
            clienteDAO.atualizarSaldo(contaId, valor);
            System.out.println("Depósito realizado com sucesso.");
        } else {
            System.out.println("Valor de depósito inválido.");
        }
    }

    public void realizarSaque(int contaId, double valor) throws SQLException {
        double saldo = clienteDAO.buscarSaldoPorContaId(contaId);
        if (saldo >= valor && valor > 0) {
            clienteDAO.atualizarSaldo(contaId, -valor);
            System.out.println("Saque realizado com sucesso.");
        } else {
            System.out.println("Saldo insuficiente ou valor inválido.");
        }
    }

    public void exibirExtrato(int contaId) {
        String sql = "SELECT tipo_transacao, valor, data_hora FROM transacao WHERE id_conta = ? ORDER BY data_hora DESC";
        executarConsultaGenerica(sql, contaId);
    }

    private void executarConsultaGenerica(String sql, int contaId) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, contaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.printf("Tipo: %s, Valor: %.2f, Data/Hora: %s\n",
                        rs.getString("tipo_transacao"),
                        rs.getDouble("valor"),
                        rs.getTimestamp("data_hora"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar dados: " + e.getMessage());
            e.printStackTrace(); 
        }
    }
}
