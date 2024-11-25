package DAO;

import model.Cliente;
import model.Endereco;
import model.Conta;
import util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private static final System.Logger logger = System.getLogger(ClienteDAO.class.getName());

    public ClienteDAO() {
        new UsuarioDAO();
    }

    public int salvarCliente(Cliente cliente) {
        Connection conn = null;
        PreparedStatement stmtCliente = null;
        PreparedStatement stmtEndereco = null;
        int idUsuarioInserido = -1;

        String sqlEndereco = "INSERT INTO endereco (cep, local, numero_casa, bairro, cidade, estado) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlCliente = """
            INSERT INTO cliente (nome, cpf, data_nascimento, telefone, senha, id_conta) 
            VALUES (?, ?, ?, ?, ?, ?)
        """; 

        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            // Salva o endereço
            stmtEndereco = conn.prepareStatement(sqlEndereco, Statement.RETURN_GENERATED_KEYS);
            stmtEndereco.setString(1, cliente.getEndereco().getCep());
            stmtEndereco.setString(2, cliente.getEndereco().getLocal());
            stmtEndereco.setInt(3, cliente.getEndereco().getNumeroCasa());
            stmtEndereco.setString(4, cliente.getEndereco().getBairro());
            stmtEndereco.setString(5, cliente.getEndereco().getCidade());
            stmtEndereco.setString(6, cliente.getEndereco().getEstado());
            stmtEndereco.executeUpdate();

            ResultSet rsEndereco = stmtEndereco.getGeneratedKeys();
            int idEndereco = 0;
            if (rsEndereco.next()) {
                idEndereco = rsEndereco.getInt(1);
            }

            cliente.getEndereco().setIdEndereco(idEndereco);

            // Salva o cliente
            stmtCliente = conn.prepareStatement(sqlCliente, Statement.RETURN_GENERATED_KEYS);
            stmtCliente.setString(1, cliente.getNome());
            stmtCliente.setString(2, cliente.getCpf());
            stmtCliente.setDate(3, Date.valueOf(cliente.getDataNascimento()));
            stmtCliente.setString(4, cliente.getTelefone());
            stmtCliente.setString(5, cliente.getSenhaHash());

            if (cliente.getConta() != null) {
                stmtCliente.setInt(6, cliente.getConta().getId_conta());
            } else {
                stmtCliente.setNull(6, Types.INTEGER);
            }

            int rowsAffected = stmtCliente.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = stmtCliente.getGeneratedKeys()) {
                    if (rs.next()) {
                        idUsuarioInserido = rs.getInt(1);  // Atribui o id gerado
                        cliente.setId_cliente(idUsuarioInserido);  // Atualiza o id_cliente após inserção
                    }
                }
            }

            if (idUsuarioInserido != -1) {
                conn.commit();  // Commit apenas se a operação foi bem-sucedida
            }
        } catch (SQLException e) {
            logger.log(System.Logger.Level.ERROR, "Erro ao salvar cliente no banco de dados.", e);
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    logger.log(System.Logger.Level.ERROR, "Erro ao fazer rollback.", ex);
                }
            }
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                logger.log(System.Logger.Level.ERROR, "Erro ao restaurar auto commit.", e);
            }
            DBUtil.closeStatement(stmtCliente);
            DBUtil.closeStatement(stmtEndereco);
            DBUtil.closeConnection(conn);
        }

        return idUsuarioInserido;  // Retorna o id gerado
    }

    public Cliente buscarClientePorId(int id) {
        String sql = """
            SELECT c.id_cliente, c.nome, c.cpf, c.data_nascimento, c.telefone, c.senha, 
                   ct.id_conta, ct.numero_conta, ct.agencia, ct.saldo, ct.tipo_conta,
                   e.cep, e.local, e.numero_casa, e.bairro, e.cidade, e.estado
            FROM cliente c
            INNER JOIN conta ct ON c.id_conta = ct.id_conta
            INNER JOIN endereco e ON c.endereco = e.id_endereco
            WHERE c.id_cliente = ?
        """; 

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearCliente(rs);
            }
        } catch (SQLException e) {
            logger.log(System.Logger.Level.ERROR, "Erro ao buscar cliente por ID.", e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
            DBUtil.closeConnection(conn);
        }
        return null;
    }

    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        Endereco endereco = new Endereco(
            rs.getString("cep"),
            rs.getString("local"),
            rs.getInt("numero_casa"),
            rs.getString("bairro"),
            rs.getString("cidade"),
            rs.getString("estado")
        );

        Conta conta = new Conta();
        conta.setId_conta(rs.getInt("id_conta"));
        conta.setNumeroConta(rs.getString("numero_conta"));
        conta.setAgencia(rs.getString("agencia"));
        conta.setSaldo(rs.getDouble("saldo"));
        conta.setTipoConta(rs.getString("tipo_conta"));

        Cliente cliente = new Cliente();
        cliente.setId_cliente(rs.getInt("id_cliente"));
        cliente.setNome(rs.getString("nome"));
        cliente.setCpf(rs.getString("cpf"));
        cliente.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
        cliente.setTelefone(rs.getString("telefone"));
        cliente.setEndereco(endereco);
        cliente.setConta(conta);

        endereco.setCliente(cliente);

        return cliente;
    }
    
 // Método para buscar saldo por id de cliente
    public double buscarSaldoPorContaId(int idCliente) throws SQLException {
        String sql = "SELECT saldo FROM conta WHERE id_cliente = ?";
        double saldo = 0.0;
        Connection conn = null;

        try {
            conn = ConnectionFactory.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {  
                stmt.setInt(1, idCliente);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        saldo = rs.getDouble("saldo");
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar saldo da conta do cliente " + idCliente + ": " + e.getMessage(), e);
        } finally {
            DBUtil.closeConnection(conn);  
        }

        return saldo;
    }

    // Atualizar saldo de uma conta
    public void atualizarSaldo(int idCliente, double valor) throws SQLException {
        if (valor == 0) {
            throw new IllegalArgumentException("Valor para atualização do saldo não pode ser zero.");
        }

        String sql = "UPDATE conta SET saldo = saldo + ? WHERE id_cliente = ?";
        Connection conn = null;

        try {
            conn = ConnectionFactory.getConnection();  // Corrigido para obter a conexão
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDouble(1, valor);
                stmt.setInt(2, idCliente);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Nenhuma conta encontrada para o cliente com ID " + idCliente);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao atualizar saldo da conta do cliente " + idCliente + ": " + e.getMessage(), e);
        } finally {
            DBUtil.closeConnection(conn);  // Fechando a conexão corretamente
        }
    }

    public List<Cliente> listarTodosClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = """
            SELECT c.id_cliente, c.nome, c.cpf, c.data_nascimento, c.telefone, c.senha, 
                   ct.id_conta, ct.numero_conta, ct.agencia, ct.saldo, ct.tipo_conta,
                   e.cep, e.local, e.numero_casa, e.bairro, e.cidade, e.estado
            FROM cliente c
            INNER JOIN conta ct ON c.id_conta = ct.id_conta
            INNER JOIN endereco e ON c.endereco = e.id_endereco
        """; 

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                clientes.add(mapearCliente(rs));
            }
        } catch (SQLException e) {
            logger.log(System.Logger.Level.ERROR, "Erro ao listar todos os clientes.", e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
            DBUtil.closeConnection(conn);
        }

        return clientes;
    }
}
