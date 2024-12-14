package org.example.testejavafxmaven;

import java.util.List;

public interface UCDAO {
    void salvarUC(String nome, Integer cursoId); // Método para salvar uma UC
    List<UC> buscarUcsPorCurso(int cursoId);  // Método para buscar as UCs associadas a um curso
    List<UC> buscarTodasUCs();
}
