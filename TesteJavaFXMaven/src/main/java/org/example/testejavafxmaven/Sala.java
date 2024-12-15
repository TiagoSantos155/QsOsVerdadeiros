package org.example.testejavafxmaven;

public class Sala {
    private int id;
    private String nome;
    private int capacidade;
    private int computadores;

    public Sala(int id, String nome, int capacidade, int computadores) {
        this.id = id;
        this.nome = nome;
        this.capacidade = capacidade;
        this.computadores = computadores;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public int getComputadores() {
        return computadores;
    }

    public void setComputadores(int computadores) {
        this.computadores = computadores;
    }

    @Override
    public String toString() {
        return "Sala{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", capacidade=" + capacidade +
                ", computadores=" + computadores +
                '}';
    }
}

