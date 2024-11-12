package model;

import java.time.LocalDate;
import DAO.ClienteDAO;
import DAO.ContaDAO;

public class Funcionario extends Usuario {

    private String codigoFuncionario;
    private String cargo;
    private String senha;
    private ContaDAO contaDAO;
    private ClienteDAO clienteDAO;

    // Construtor com parâmetros
    public Funcionario(int id, String nome, String cpf, LocalDate dataNascimento,
    		String telefone, Endereco endereco, String codigoFuncionario,
    		String cargo, String senha, ContaDAO contaDAO, ClienteDAO clienteDAO)
 {
        super(id, nome, cpf, dataNascimento, telefone, endereco);  // Chama o construtor da classe pai
        this.codigoFuncionario = codigoFuncionario;
        this.cargo = cargo;
        this.senha = senha;
        this.contaDAO = contaDAO;
        this.clienteDAO = clienteDAO;
    }
    
    @Override
    public boolean login(String senha) {
        return this.senha.equals(senha);
    }

    @Override
    public void logout() {
        // Remover a lógica de impressão
    }

    @Override
    public String consultarDados() {
        return "Nome: " + getNome() + ", Cargo: " + cargo;
    }

    // Métodos de manipulação de contas e clientes
    public void abrirConta(Conta conta) {
        if (conta != null && conta.getCliente() != null) {
            contaDAO.salvarConta(conta);
        }
    }

    public void encerrarConta(Conta conta) {
        Integer numeroConta = conta.getNumeroConta(); // Integer para comparar com null
        if (numeroConta != null) {
            contaDAO.deletarConta(numeroConta); 
        }
    }

    public Conta consultarDadosConta(int numeroConta) {
        return contaDAO.buscarContaPorNumero(String.valueOf(numeroConta));
    }

    public Cliente consultarDadosCliente(int idCliente) {
        return clienteDAO.buscarClientePorId(idCliente);
    }

    public void alterarDadosConta(Conta conta) {
        contaDAO.atualizarConta(conta);
    }

    public void alterarDadosCliente(Cliente cliente) {
        clienteDAO.atualizarCliente(cliente);
    }

    // Métodos getter
    public String getCodigoFuncionario() {
        return codigoFuncionario;
    }

    public String getCargo() {
        return cargo;
    }

    public String getSenha() {
        return senha;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "codigoFuncionario='" + codigoFuncionario + '\'' +
                ", cargo='" + cargo + '\'' +
                ", nome='" + getNome() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                ", telefone='" + getTelefone() + '\'' +
                ", endereco=" + getEndereco() +
                '}';
    }
}
