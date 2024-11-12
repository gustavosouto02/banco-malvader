package DAO;

import model.Cliente;
import model.Conta;
import model.Endereco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {
    private Connection connection;

    // Construtor que recebe uma conexão
    public ClienteDAO(Connection connection) {
        this.connection = connection;
    }

    // Construtor padrão sem parâmetros, para ser utilizado quando necessário
    public ClienteDAO() {
        // Aqui você pode inicializar conexões ou outras coisas necessárias
    }

    // Método para buscar um cliente pelo ID
    public Cliente buscarClientePorId(int id) {
        Cliente cliente = null;
        String sql = "SELECT * FROM clientes WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cliente = new Cliente(
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
                    new Conta(
                        rs.getInt("conta_id"), 
                        rs.getString("agencia_conta"), 
                        null,
                        rs.getDouble("saldo")
                    )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }

    // Método para atualizar os dados de um cliente
    public void atualizarCliente(Cliente cliente) {
        String sql = "UPDATE clientes SET nome = ?, cpf = ?, data_nascimento = ?, telefone = ?, cep = ?, local = ?, numeroCasa = ?, bairro = ?, cidade = ?, estado = ?, senha = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setDate(3, java.sql.Date.valueOf(cliente.getDataNascimento()));
            stmt.setString(4, cliente.getTelefone());

            // Atualizando o endereço do cliente
            stmt.setString(5, cliente.getEndereco().getCep());
            stmt.setString(6, cliente.getEndereco().getLocal());
            stmt.setInt(7, cliente.getEndereco().getNumeroCasa());
            stmt.setString(8, cliente.getEndereco().getBairro());
            stmt.setString(9, cliente.getEndereco().getCidade());
            stmt.setString(10, cliente.getEndereco().getEstado());

            stmt.setString(11, cliente.getSenha());
            stmt.setInt(12, cliente.getId());

            stmt.executeUpdate();  // Executa a atualização no banco de dados
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para buscar o saldo de uma conta através do número da conta
    public double buscarSaldoPorContaId(int contaId) {
        String sql = "SELECT saldo FROM contas WHERE numeroConta = ?";
        double saldo = 0.0;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, contaId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                saldo = rs.getDouble("saldo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return saldo;
    }

    // Método para atualizar o saldo de uma conta (depósito ou saque)
    public void atualizarSaldo(int contaId, double valor) {
        String sql = "UPDATE contas SET saldo = saldo + ? WHERE numeroConta = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, valor); // valor positivo (depósito) ou negativo (saque)
            stmt.setInt(2, contaId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
