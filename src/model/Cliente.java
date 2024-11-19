package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Cliente extends Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
    private String senha;
    private Conta conta;

    public Cliente(int id, String nome, String cpf, LocalDate dataNascimento, String telefone, Endereco endereco, String senha, Conta conta) {
        super(id, nome, cpf, dataNascimento, telefone, endereco);
        this.senha = senha;
        this.conta = conta;
    }

    @Override
    public boolean login(String senha) {
        return this.senha.equals(senha);
    }

    @Override
    public void logout() {
        System.out.println("Cliente " + nome + " deslogado.");
    }

    @Override
    public String consultarDados() {
        return String.format("Cliente: %s, CPF: %s", nome, cpf);
    }

    @Override
    public String toString() {
        return String.format("Cliente [ID: %d, Nome: %s, CPF: %s, Conta: %s]", id, nome, cpf,
                (conta != null ? conta.getNumeroConta() : "Sem Conta"));
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if (senha != null && senha.length() >= 6) {
            this.senha = senha;
        } else {
            throw new IllegalArgumentException("Senha inválida. A senha deve ter pelo menos 6 caracteres.");
        }
    }
    
    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }
}

