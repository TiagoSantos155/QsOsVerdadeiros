package service;

import dao.CursoDAO;
import modelo.Curso;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CursoService {
    private final CursoDAO cursoDAO;

    public CursoService(Connection conexao) {
        this.cursoDAO = new CursoDAO(conexao);
    }

    public void salvarCurso(String nome, int numeroAlunos) throws SQLException {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome do curso é obrigatório.");
        }
        if (numeroAlunos <= 0) {
            throw new IllegalArgumentException("O número de alunos deve ser maior que zero.");
        }

        Curso curso = new Curso(nome, numeroAlunos);
        cursoDAO.inserirCurso(curso);
    }

    public List<Curso> listarCursos() throws SQLException {
        return cursoDAO.listarCursos();
    }

    public void atualizarCurso(int id, String nome, int numeroAlunos) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("O ID do curso é inválido.");
        }
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome do curso é obrigatório.");
        }
        if (numeroAlunos <= 0) {
            throw new IllegalArgumentException("O número de alunos deve ser maior que zero.");
        }

        Curso curso = new Curso(id, nome, numeroAlunos);
        cursoDAO.atualizarCurso(curso);
    }

    public void deletarCurso(int id) throws SQLException {
        if (id <= 0) {
            throw new IllegalArgumentException("O ID do curso é inválido.");
        }
        cursoDAO.deletarCurso(id);
    }
}
