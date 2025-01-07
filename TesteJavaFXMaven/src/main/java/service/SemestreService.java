package service;

import dao.SemestreDAO;
import modelo.Semestre;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SemestreService {
    private final SemestreDAO semestreDAO;

    public SemestreService() {
        this.semestreDAO = new SemestreDAO(); // Instância de SemestreDAO usando DataBaseConnection
    }

    public void salvarSemestre(LocalDate inicio, LocalDate fim) throws SQLException {
        Semestre semestre = new Semestre(0, inicio, fim); // ID será gerado automaticamente pelo banco
        semestreDAO.salvar(semestre);
    }

    // Buscar todos os semestres
    public List<Semestre> listarSemestres() {
        return semestreDAO.listarTodos();
    }

    public void atualizarSemestre(int id, LocalDate inicio, LocalDate fim) throws SQLException {
        Semestre semestre = new Semestre(id, inicio, fim);
        semestreDAO.atualizar(semestre);
    }

    // Excluir semestre
    public boolean excluirSemestre(int id) {
        return semestreDAO.excluir(id);
    }

    // Buscar semestre por ID
    public Semestre buscarSemestrePorId(int id) {
        return semestreDAO.buscarPorId(id);
    }
}
