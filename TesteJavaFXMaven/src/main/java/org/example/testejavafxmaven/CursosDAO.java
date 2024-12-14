package org.example.testejavafxmaven;

import java.util.List;

public interface CursosDAO {

    /**
     * Salva um novo curso no banco de dados.
     *
     * @param nome O nome do curso a ser salvo.
     */
    void salvarCurso(String nome);

    /**
     * Busca todos os cursos do banco de dados.
     *
     * @return Uma lista contendo todos os cursos.
     */
    List<Cursos> buscarTodosCursos();

    /**
     * Busca um curso específico pelo ID.
     *
     * @param id O ID do curso a ser buscado.
     * @return O objeto Cursos encontrado, ou null se não existir.
     */
    Cursos buscarCursoPorId(int id);

    /**
     * Atualiza as informações de um curso no banco de dados.
     *
     * @param curso O objeto Cursos com os dados atualizados.
     */
    void atualizarCurso(Cursos curso);

    /**
     * Exclui um curso do banco de dados pelo ID.
     *
     * @param id O ID do curso a ser excluído.
     */
    void excluirCurso(int id);
}
