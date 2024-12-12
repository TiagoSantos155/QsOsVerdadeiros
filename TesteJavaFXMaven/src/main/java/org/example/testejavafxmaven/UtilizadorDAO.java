package org.example.testejavafxmaven;


import java.util.List;

public interface UtilizadorDAO {
    /**
     * Salva um novo utilizador no banco de dados.
     *
     * @param utilizador O objeto Utilizador a ser salvo.
     */
    void salvar(Utilizador utilizador);

    /**
     * Busca todos os utilizadores do banco de dados.
     *
     * @return Uma lista contendo todos os utilizadores.
     */
    List<Utilizador> buscarTodos();

    /**
     * Busca um utilizador no banco de dados pelo email.
     *
     * @param email O email do utilizador a ser buscado.
     * @return O objeto Utilizador encontrado, ou null se não existir.
     */
    Utilizador buscarPorEmail(String email);

    /**
     * Atualiza as informações de um utilizador no banco de dados.
     *
     * @param utilizador O objeto Utilizador com os dados atualizados.
     */
    void atualizar(Utilizador utilizador);

    /**
     * Exclui um utilizador do banco de dados pelo email.
     *
     * @param email O email do utilizador a ser excluído.
     */
    void excluir(String email);
}
