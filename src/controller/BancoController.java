package controller;

import model.*;
import exception.SaldoInsuficienteException;
import exception.ValorInvalidoException;
import util.DataManager;
import DAO.ConnectionFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BancoController {

    private List<Conta> contas;
    private List<Funcionario> funcionarios;
    private static final String CAMINHO_ARQUIVO_CONTAS = "contas.dat";  // Caminho do arquivo onde as contas são armazenadas

    public BancoController() {
    	
        this.contas = new ArrayList<>();
        this.funcionarios = new ArrayList<>();
        carregarDados();
    }

    // Carregar dados ao iniciar o sistema
    public void carregarDados() {
        contas = DataManager.carregarContas("contas.dat");
        funcionarios = DataManager.carregarFuncionarios("funcionarios.dat");
        System.out.println("Dados carregados com sucesso!");
    }

    // Salvar dados no arquivo
    private void salvarDados() {
        DataManager.salvarContas(contas, "contas.dat");
        DataManager.salvarFuncionarios(funcionarios, "funcionarios.dat");
        System.out.println("Dados salvos com sucesso!");
    }

    // Métodos para manipulação de contas
    public void abrirConta(Conta novaConta) throws SQLException {
        if (isCpfCadastrado(novaConta.getCliente().getCpf())) {
            System.out.println("CPF já cadastrado! Não é possível abrir outra conta.");
            return;
        }
        contas.add(novaConta);
        salvarDados();
        System.out.println("Conta aberta com sucesso.");
    }

    public void encerrarConta(String numeroConta) {
        Conta contaParaRemover = contas.stream()
            .filter(conta -> conta.getNumeroConta().equals(numeroConta))
            .findFirst()
            .orElse(null);

        if (contaParaRemover != null) {
            contas.remove(contaParaRemover);
            salvarDados();
            System.out.println("Conta encerrada com sucesso.");
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    public Conta consultarConta(String numeroConta) {
        // Verifica se as contas foram carregadas corretamente
        if (contas == null || contas.isEmpty()) {
            System.out.println("Erro: Nenhuma conta carregada.");
            return null;
        }
        
        System.out.println("Contas carregadas: ");
        contas.forEach(conta -> System.out.println(conta.getNumeroConta()));  // Mostra os números das contas carregadas

        // Busca pela conta
        return contas.stream()
                .filter(conta -> conta.getNumeroConta().equals(numeroConta))
                .findFirst()
                .orElse(null);
    }


    public boolean isCpfCadastrado(String cpfCliente) {
        String sql = "SELECT COUNT(*) FROM cliente WHERE cpf = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpfCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao verificar CPF: " + ex.getMessage());
        }
        return false;
    }

    // Métodos de operações bancárias
    public double consultarSaldo(String numeroConta) {
        Conta conta = consultarConta(numeroConta);
        return conta != null ? conta.consultarSaldo() : -1;
    }

    public void realizarDeposito(String numeroConta, double valor) throws ValorInvalidoException {
        Conta conta = consultarConta(numeroConta);
        if (conta != null) {
            conta.depositar(valor);
            registrarTransacao("DEPOSITO", valor, numeroConta);
            salvarDados();
            System.out.println("Depósito realizado com sucesso.");
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    public void realizarSaque(String numeroConta, double valor) {
        Conta conta = consultarConta(numeroConta);
        if (conta != null) {
            try {
                conta.sacar(valor);
                registrarTransacao("SAQUE", valor, numeroConta);
                salvarDados();
                System.out.println("Saque realizado com sucesso.");
            } catch (SaldoInsuficienteException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    public void realizarTransferencia(String numeroContaOrigem, String numeroContaDestino, double valor) {
        Conta contaOrigem = consultarConta(numeroContaOrigem);
        Conta contaDestino = consultarConta(numeroContaDestino);

        if (contaOrigem != null && contaDestino != null) {
            try {
                contaOrigem.sacar(valor);
                contaDestino.depositar(valor);
                registrarTransacao("TRANSFERENCIA", valor, numeroContaOrigem);
                registrarTransacao("TRANSFERENCIA", valor, numeroContaDestino);
                salvarDados();
                System.out.println("Transferência realizada com sucesso.");
            } catch (SaldoInsuficienteException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Uma das contas não foi encontrada.");
        }
    }

    private void registrarTransacao(String tipoTransacao, double valor, String numeroConta) {
        String sql = "INSERT INTO transacao (tipo_transacao, valor, data_hora, id_conta) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipoTransacao);
            stmt.setDouble(2, valor);
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(4, numeroConta);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao registrar transação: " + e.getMessage());
        }
    }

    public void gerarRelatorios(List<Conta> contas) {
        // Carregar os dados dos funcionários
        List<Funcionario> funcionarios = carregarFuncionarios();

        // Construir o conteúdo do relatório incluindo as contas e funcionários
        StringBuilder conteudoRelatorio = new StringBuilder();
        conteudoRelatorio.append("Detalhes do Relatório Geral com Informações de Contas e Funcionários.\n\n");

        // Adiciona detalhes das contas
        conteudoRelatorio.append("Contas:\n");
        for (Conta conta : contas) {
            conteudoRelatorio.append(conta.toString()).append("\n");
        }

        // Adiciona detalhes dos funcionários
        conteudoRelatorio.append("\nFuncionários:\n");
        for (Funcionario funcionario : funcionarios) {
            conteudoRelatorio.append(funcionario.toString()).append("\n");
        }

        // Cria o relatório
        Relatorio relatorio = new Relatorio(
            "Relatório Geral",  // Tipo do Relatório
            LocalDateTime.now(),  // Data de Geração
            conteudoRelatorio.toString(),  // Conteúdo do Relatório
            contas  // Lista de contas associadas
        );

        // Gera o relatório em formato CSV
        relatorio.gerarRelatorioGeral();
    }

    // Método para exportar o relatório para Excel
    public void exportarRelatorioParaExcel(List<Conta> contas) {
        // Carregar os dados dos funcionários
        List<Funcionario> funcionarios = carregarFuncionarios();

        // Construir o conteúdo do relatório para exportação
        StringBuilder conteudoRelatorio = new StringBuilder();
        conteudoRelatorio.append("Detalhes da Exportação de Relatório com Dados de Contas e Funcionários.\n\n");

        // Adiciona detalhes das contas
        conteudoRelatorio.append("Contas:\n");
        for (Conta conta : contas) {
            conteudoRelatorio.append(conta.toString()).append("\n");
        }

        // Adiciona detalhes dos funcionários
        conteudoRelatorio.append("\nFuncionários:\n");
        for (Funcionario funcionario : funcionarios) {
            conteudoRelatorio.append(funcionario.toString()).append("\n");
        }

        // Cria o relatório
        Relatorio relatorio = new Relatorio(
            "Exportação Relatório",  // Tipo do Relatório
            LocalDateTime.now(),  // Data de Geração
            conteudoRelatorio.toString(),  // Conteúdo do Relatório
            contas  // Lista de contas associadas
        );

        // Exporta o relatório para Excel (no caso, CSV)
        relatorio.exportarParaExcel();
    }

    public List<Transacao> getExtrato(String numeroConta) {
        List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT tipo_transacao, valor, data_hora FROM transacao WHERE id_conta = ? ORDER BY data_hora DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numeroConta);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                transacoes.add(new Transacao(
                    rs.getString("tipo_transacao"),
                    rs.getDouble("valor"),
                    rs.getTimestamp("data_hora")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar extrato: " + e.getMessage());
        }
        return transacoes;
    }

    public void alterarDados(String numeroConta, String novoNome) throws SQLException {
        Conta conta = consultarConta(numeroConta);
        if (conta != null && conta.getCliente() != null) {
            Cliente cliente = conta.getCliente();
            cliente.setNome(novoNome);
            salvarDados(); // Atualiza as alterações
            System.out.println("Nome do cliente alterado com sucesso.");
        } else {
            System.out.println("Conta ou cliente não encontrados.");
        }
    }


    public void consultarDados() {
        System.out.println("Contas cadastradas:");
        contas.forEach(System.out::println);
        System.out.println("\nFuncionários cadastrados:");
        funcionarios.forEach(System.out::println);
    }

    public void cadastrarConta(Conta novaConta, String cpfCliente) {
        String sqlConta = "INSERT INTO conta (numero_conta, agencia, saldo, tipo, cpf_cliente) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlConta)) {

            // Verifica se o cliente existe antes de criar a conta
            if (!isCpfCadastrado(cpfCliente)) {
                throw new IllegalArgumentException("Cliente não encontrado no sistema.");
            }

            // Insere a nova conta no banco
            stmt.setString(1, novaConta.getNumeroConta());
            stmt.setString(2, novaConta.getAgencia());
            stmt.setDouble(3, novaConta.getSaldo());
            stmt.setString(4, novaConta.getTipoConta());
            stmt.setString(5, cpfCliente);
            stmt.executeUpdate();

            System.out.println("Conta cadastrada com sucesso para o cliente com CPF: " + cpfCliente);
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar conta: " + e.getMessage());
        }
    }

    public List<Conta> obterContas() {
        // Usa o DataManager para carregar as contas de um arquivo
        List<Conta> contas = DataManager.carregarContas(CAMINHO_ARQUIVO_CONTAS);
        
        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta encontrada no arquivo.");
        }
        
        return contas;
    }
    public List<Funcionario> carregarFuncionarios() {
        List<Funcionario> funcionarios = DataManager.carregarFuncionarios("funcionarios.dat");
        if (funcionarios == null || funcionarios.isEmpty()) {
            // Caso o arquivo não exista ou esteja vazio, cria uma lista vazia de funcionários
            System.out.println("Nenhum funcionário encontrado. Criando lista vazia.");
            funcionarios = new ArrayList<>();
        }
        return funcionarios;
    }

}