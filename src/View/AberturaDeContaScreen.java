package View;

import javax.swing.*;
import controller.BancoController;
import model.Conta;
import model.Cliente;
import model.Endereco;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class AberturaDeContaScreen extends JFrame {

    private static final long serialVersionUID = 1L;
    public AberturaDeContaScreen(BancoController bancoController) {
        setTitle("Abertura de Conta");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2)); // Aumentando para 7 linhas para um layout mais organizado
        add(panel);

        JLabel nomeLabel = new JLabel("Nome do Cliente:");
        JTextField nomeField = new JTextField();
        panel.add(nomeLabel);
        panel.add(nomeField);

        JLabel cpfLabel = new JLabel("CPF do Cliente:");
        JTextField cpfField = new JTextField();
        panel.add(cpfLabel);
        panel.add(cpfField);

        JLabel enderecoLabel = new JLabel("Endereço do Cliente:");
        JTextField enderecoField = new JTextField();
        panel.add(enderecoLabel);
        panel.add(enderecoField);

        JLabel numeroContaLabel = new JLabel("Número da Conta:");
        JTextField numeroContaField = new JTextField();
        panel.add(numeroContaLabel);
        panel.add(numeroContaField);

        JLabel saldoLabel = new JLabel("Saldo Inicial:");
        JTextField saldoField = new JTextField();
        panel.add(saldoLabel);
        panel.add(saldoField);

        JLabel tipoContaLabel = new JLabel("Tipo de Conta:");
        JComboBox<String> tipoContaCombo = new JComboBox<>(new String[] {"Corrente", "Poupança"});
        panel.add(tipoContaLabel);
        panel.add(tipoContaCombo);

        JButton confirmarButton = new JButton("Confirmar");
        panel.add(new JLabel()); // Espaço vazio
        panel.add(confirmarButton);

        // Action listener para o botão de confirmar
        confirmarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String nomeCliente = nomeField.getText();
                    String cpfCliente = cpfField.getText();
                    String enderecoCliente = enderecoField.getText();
                    String numeroContaText = numeroContaField.getText();
                    String saldoInicialText = saldoField.getText();
                    String tipoConta = (String) tipoContaCombo.getSelectedItem();

                    if (nomeCliente.isEmpty() || cpfCliente.isEmpty() || enderecoCliente.isEmpty() || numeroContaText.isEmpty() || saldoInicialText.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos!");
                        return;
                    }

                    if (!isValidCPF(cpfCliente)) {
                        JOptionPane.showMessageDialog(null, "CPF inválido!");
                        return;
                    }

                    int numeroConta = Integer.parseInt(numeroContaText);
                    double saldoInicial = Double.parseDouble(saldoInicialText);

                    if (bancoController.isCpfCadastrado(cpfCliente)) {
                        JOptionPane.showMessageDialog(null, "CPF já registrado!");
                        return;
                    }

                    Endereco endereco = new Endereco("CEP_EXEMPLO", enderecoCliente, 123, "Bairro", "Cidade", "Estado");
                    Cliente cliente = new Cliente(1, nomeCliente, cpfCliente, LocalDate.now(), "123456789", endereco, "senha123", null);

                    Conta novaConta = null;
                    if ("Corrente".equals(tipoConta)) {
                        novaConta = new Conta(numeroConta, "1234", cliente, saldoInicial);
                    } else if ("Poupança".equals(tipoConta)) {
                        novaConta = new Conta(numeroConta, "1234", cliente, saldoInicial);
                    }

                    bancoController.abrirConta(novaConta);

                    JOptionPane.showMessageDialog(null, "Conta criada com sucesso!");
                    dispose(); // Fecha a janela de abertura de conta

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao criar conta: " + ex.getMessage());
                }
            }
        });
    }

    // Função para validar CPF (exemplo simples)
    private boolean isValidCPF(String cpf) {
        return cpf != null && cpf.length() == 11 && cpf.matches("[0-9]+");
    }
}
