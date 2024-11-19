package DAO;

import util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    private static final System.Logger logger = System.getLogger(UsuarioDAO.class.getName());

    // Buscar senha pelo ID do usuário
    public String buscarSenhaPorUsuario(int idUsuario) {
        String sql = "SELECT senha FROM usuarios WHERE id_usuario = ?";
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = ConnectionFactory.getConnection();
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("senha");
            }
        } catch (SQLException e) {
            logger.log(System.Logger.Level.ERROR, "Erro ao buscar senha no banco para o usuário: " + idUsuario, e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(stmt);
            DBUtil.closeConnection(connection);
        }
        return null;
    }

    // Atualizar a senha do usuário
    public void salvarSenha(int idUsuario, String senhaHash) {
        String sql = "UPDATE usuarios SET senha = ? WHERE id_usuario = ?";
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = ConnectionFactory.getConnection();
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, senhaHash);
            stmt.setInt(2, idUsuario);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.log(System.Logger.Level.ERROR, "Erro ao salvar senha para o usuário: " + idUsuario, e);
        } finally {
            DBUtil.closeStatement(stmt);
            DBUtil.closeConnection(connection);
        }
    }
}
