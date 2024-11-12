package controller;

import DAO.ClienteDAO;
import DAO.ConnectionFactory;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ClienteController {

    private ClienteDAO clienteDAO;

    // Construtor que recebe a conexão
    public ClienteController(Connection connection) {
        this.clienteDAO = new ClienteDAO(connection);  // Passa a conexão para o DAO
    }
    
    public ClienteController() {
    	
    }

    // Métodos de operação do Cliente
    public void consultarSaldoCliente(int contaId) {
        double saldo = clienteDAO.buscarSaldoPorContaId(contaId);
        System.out.println("Consultando saldo...");
        System.out.println("Saldo atual: " + saldo);
    }

    public void depositoCliente(int contaId, double valor) {
        clienteDAO.atualizarSaldo(contaId, valor);
        System.out.println("Realizando depósito...");
        System.out.println("Depósito realizado com sucesso!");
    }

    public void saqueCliente(int contaId, double valor) {
        double saldo = clienteDAO.buscarSaldoPorContaId(contaId);
        if (saldo >= valor) {
            clienteDAO.atualizarSaldo(contaId, -valor);
            System.out.println("Saque realizado com sucesso!");
        } else {
            System.out.println("Saldo insuficiente para saque.");
        }
    }

    // Método para consultar o extrato de transações de um cliente
    public void consultarExtratoCliente(int contaId) {
        String sql = "SELECT t.tipo_transacao, t.valor, t.data_hora " +
                     "FROM db_malvadeza.transacao t " +
                     "JOIN db_malvadeza.conta c ON t.id_conta = c.id_conta " +
                     "WHERE c.id_conta = ? " +
                     "ORDER BY t.data_hora DESC";  // transações da mais recente para mais antiga
        executarConsulta(sql, contaId);
    }

    // Método para consultar o limite de um cliente específicp
    public double consultarLimiteCliente(int contaId) {
        String sql = "SELECT c.limite " +
                     "FROM db_malvadeza.conta_corrente cc " +
                     "JOIN db_malvadeza.conta c ON cc.id_conta = c.id_conta " +
                     "WHERE c.id_conta = ?";
        return executarConsultaLimite(sql, contaId);
    }

    //consulta genérica de extrato
    private void executarConsulta(String sql, int contaId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, contaId);
            rs = stmt.executeQuery();

            System.out.println("Consultando dados...");
            boolean hasRecords = false;
            while (rs.next()) {
                String tipoTransacao = rs.getString("tipo_transacao");
                double valor = rs.getDouble("valor");
                Timestamp dataHora = rs.getTimestamp("data_hora");
                System.out.println("Tipo: " + tipoTransacao + ", Valor: " + valor + ", Data/Hora: " + dataHora);
                hasRecords = true;
            }

            if (!hasRecords) {
                System.out.println("Nenhuma transação encontrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
            DBUtil.closeConnection(conn);
        }
    }
    
  //consulta genérica de limite
    private double executarConsultaLimite(String sql, int contaId) {
        double limite = 0.0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, contaId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                limite = rs.getDouble("limite");
                System.out.println("Limite disponível: " + limite);
            } else {
                System.out.println("Conta corrente não encontrada ou não possui limite.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
            DBUtil.closeConnection(conn);
        }

        return limite;
    }
}
