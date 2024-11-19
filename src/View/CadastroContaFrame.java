package View;

import model.Cliente;
import model.Conta;
import model.Endereco;
import controller.ContaController;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class CadastroContaFrame extends JFrame implements CadastroContaView {

    private static final long serialVersionUID = 1L;

    private ContaController contaController;

    private JTextField numeroContaField;
    private JTextField numeroAgenciaField;
    private JTextField saldoField;
    private JTextField cpfClienteField;

    public CadastroContaFrame(ContaController contaController) {
        this.contaController = contaController;

        setTitle("Cadastro de Conta");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        add(panel);
        placeComponents(panel);
    }

    @Override
    public void exibirCadastroConta() {
        setVisible(true);
    }

    @Override
    public void exibirMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }

    @Override
    public void limparCampos() {
        numeroContaField.setText("");
        numeroAgenciaField.setText("");
        saldoField.setText("");
        cpfClienteField.setText("");
    }

    @Override
    public Conta obterDadosConta() {
        // Dados da conta
        String numeroContaStr = numeroContaField.getText();
        String numeroAgencia = numeroAgenciaField.getText();
        String saldoStr = saldoField.getText();
        String cpfCliente = cpfClienteField.getText();

        // Convertendo os dados do texto para os tipos corretos
        int numeroConta = Integer.parseInt(numeroContaStr);
        double saldo = Double.parseDouble(saldoStr);

        // Buscando o cliente a partir do CPF
        Cliente cliente = buscarClientePorCpf(cpfCliente);

        if (cliente == null) {
            exibirMensagem("Cliente não encontrado.");
            return null;
        }

        // Criando a Conta
        Conta conta = new Conta(numeroConta, numeroAgencia, cliente, saldo);
        cliente.setConta(conta); // Associando a conta ao cliente

        return conta;
    }

    private Cliente buscarClientePorCpf(String cpf) {
        // Criando o endereço fictício para o cliente
        Endereco endereco = new Endereco("12345-678", "Rua Exemplo", 123, "Bairro Exemplo", "Cidade Exemplo", "Estado Exemplo");

        // Criando o cliente fictício (deve ser substituído por uma busca real)
        Cliente cliente = new Cliente(1, "Cliente Exemplo", cpf, LocalDate.now(), "123456789", endereco, "senha123", null);
        return cliente;
    }

    private void placeComponents(JPanel panel) {
        panel.add(new JLabel("Número da Conta:"));
        numeroContaField = new JTextField(20);
        panel.add(numeroContaField);

        panel.add(new JLabel("Número da Agência:"));
        numeroAgenciaField = new JTextField(20);
        panel.add(numeroAgenciaField);

        panel.add(new JLabel("Saldo Inicial:"));
        saldoField = new JTextField(20);
        panel.add(saldoField);

        panel.add(new JLabel("CPF do Cliente:"));
        cpfClienteField = new JTextField(20);
        panel.add(cpfClienteField);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(e -> {
            Conta conta = obterDadosConta();
            if (conta != null) {
                contaController.abrirConta(conta); 
                limparCampos();
                exibirMensagem("Conta cadastrada com sucesso!");
            }
        });
        panel.add(cadastrarButton);
    }
}
