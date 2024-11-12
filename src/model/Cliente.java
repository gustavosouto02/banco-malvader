package model;

import java.time.LocalDate;

public class Cliente extends Usuario {
    private String senha;  
    private Conta conta;  

    // Construtor 
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
        return "Nome: " + nome + ", CPF: " + cpf;
    }

    // Getter e Setter para a senha, se necessário
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    // Getter e Setter para a conta, se necessário
    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }
    
 // Método toString() para exibir os dados do Cliente
    @Override
    public String toString() {
        return "Cliente [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", telefone=" + telefone 
               + ", endereco=" + endereco + ", conta=" + (conta != null ? conta.getNumeroConta() : "sem conta") + "]";
    }
}
