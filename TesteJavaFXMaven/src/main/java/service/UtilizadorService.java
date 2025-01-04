package service;

import dao.UtilizadorDAO;
import modelo.Utilizador;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UtilizadorService {
    private final UtilizadorDAO utilizadorDAO;

    public UtilizadorService(Connection conexao) {
        this.utilizadorDAO = new UtilizadorDAO(conexao);
    }

    public void salvarUtilizador(String nome, String tipo, String email, String senha) throws SQLException {
        // Você pode adicionar lógica adicional aqui, como hashing de senha
        Utilizador utilizador = new Utilizador(nome, tipo, email, senha);
        utilizadorDAO.salvar(utilizador);
    }

    public void atualizarUtilizador(int id, String nome, String tipo, String email, String senha) throws SQLException {
        // Adicionar lógica adicional se necessário
        Utilizador utilizador = new Utilizador(id, nome, tipo, email, senha);
        utilizadorDAO.atualizar(utilizador);
    }

    public void deletarUtilizador(int id) throws SQLException {
        utilizadorDAO.deletar(id);
    }

    public List<Utilizador> listarUtilizadores() throws SQLException {
        return utilizadorDAO.listar();
    }

    public Utilizador buscarUtilizadorPorEmail(String email) throws SQLException {
        return utilizadorDAO.buscarPorEmail(email);
    }
}
