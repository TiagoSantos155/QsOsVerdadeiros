package org.example.testejavafxmaven;

import java.util.List;

public class Cursos {
    private int id;
    private String nome;
    private List<UC> ucs; // Lista de UCs associadas ao curso

    // Construtores, getters e setters
    public Cursos(int id, String nome, List<UC> ucs) {
        this.id = id;
        this.nome = nome;
        this.ucs = ucs;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<UC> getUcs() {
        return ucs;
    }
}
