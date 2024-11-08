package model;

public class Endereco {
	private String cep;
    private String local;
    private int numeroCasa;
    private String bairro;
    private String cidade;
    private String estado;
    
    @Override
    public String toString() {
        return local + ", " + numeroCasa + ", " + bairro + ", " + cidade + ", " + estado + " - " + cep;
    }
}
