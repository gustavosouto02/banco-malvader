package model;

public class UsuarioInfo {
	private final int idUsuario;
    private final String senhaHash;
    private final boolean isFuncionario;
    private final boolean isCliente;

    public UsuarioInfo(int idUsuario, String senhaHash, boolean isFuncionario, boolean isCliente) {
    	this.idUsuario = idUsuario;
    	this.senhaHash = senhaHash;
        this.isFuncionario = isFuncionario;
        this.isCliente = isCliente;
    }
    

    public int getIdUsuario() {
        return idUsuario;  // Getter para idUsuario
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public boolean isFuncionario() {
        return isFuncionario;
    }

    public boolean isCliente() {
        return isCliente;
    }
}