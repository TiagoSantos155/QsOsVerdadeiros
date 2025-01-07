package service;

import dao.EpocaDAO;
import modelo.Epoca;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EpocaService {
    private final EpocaDAO epocaDAO;

    public EpocaService() {
        this.epocaDAO = new EpocaDAO();
    }

    public void salvarEpoca(String tipo, LocalDate inicio, LocalDate fim, int semestreId) throws SQLException {
        Epoca epoca = new Epoca(0, tipo, inicio, fim, semestreId); // ID será gerado automaticamente pelo banco
        epocaDAO.salvar(epoca);
    }

    // Listar todas as épocas
    public List<Epoca> listarEpocas() {
        return epocaDAO.listarTodos();
    }

    public void atualizarEpoca(int id, String tipo, LocalDate inicio, LocalDate fim, int semestreId) throws SQLException {
        Epoca epoca = new Epoca(id, tipo, inicio, fim, semestreId);
        epocaDAO.atualizar(epoca);
    }

    // Excluir época
    public boolean excluirEpoca(int id) {
        return epocaDAO.excluir(id);
    }

    // Buscar época por ID
    public Epoca buscarEpocaPorId(int id) {
        return epocaDAO.buscarPorId(id);
    }
}
