package service;

import dao.CursoDAO;
import modelo.Curso;

import java.util.List;

public class CursoService {
    private final CursoDAO cursoDAO;

    public CursoService() {
        this.cursoDAO = new CursoDAO();
    }

    public void salvarCurso(Curso curso) {
        if (curso.getNome() == null || curso.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome do curso é obrigatório.");
        }
        cursoDAO.salvar(curso);
    }

    public List<Curso> listarCursos() {
        return cursoDAO.listarTodos();
    }

    public void deletarCurso(int id) {
        cursoDAO.deletar(id);
    }
}
