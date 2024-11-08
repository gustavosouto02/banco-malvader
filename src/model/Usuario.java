package model;

import java.time.LocalDate;

public abstract class Usuario {
	protected int id;
	protected String nome;
	protected String cpf;
	protected LocalDate dataNascimento;
	protected String Telefone;
	protected Endereco endereco;
	
	public boolean login(String senha) {
		if (senha.equals(senha)) {
			return true;
		}else { 
			System.out.println("Senha incorreta.");
			return false;
	    }
	}
		public void logout() {
			
		}
		
		public String consultarDados() {
			return nome + " - " + cpf;
		}
}
