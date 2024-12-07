package org.example.testejavafxmaven;

import java.util.ArrayList;
import java.util.List;

public class Cursos {
    private String nome;
    private int numeroAlunos;
    private List<UC> ucs; // Lista de UCs associadas ao curso.

    // Construtor
    public Cursos(String nome, int numeroAlunos) {
        this.nome = nome;
        this.numeroAlunos = numeroAlunos;
        this.ucs = new ArrayList<>(); // Inicialização da lista
    }

    // Getters e Setters
    public int getNumeroAlunos() {
        return numeroAlunos;
    }

    public void setNumeroAlunos(int numeroAlunos) {
        this.numeroAlunos = numeroAlunos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Metodo para associar UCs ao curso
    public void associarUCs(List<UC> ucsParaAssociar) {
        if (ucsParaAssociar == null || ucsParaAssociar.isEmpty()) {
            System.out.println("Nenhuma UC foi fornecida para associação.");
            return;
        }

        for (UC uc : ucsParaAssociar) {
            if (!this.ucs.contains(uc)) { // Verifica se a UC já está associada
                this.ucs.add(uc);
                System.out.println("UC " + uc.getNome() + " associada ao curso " + this.nome + ".");
            } else {
                System.out.println("A UC " + uc.getNome() + " já está associada ao curso " + this.nome + ".");
            }
        }
    }

    // Metodo para calcular o espaço necessário em salas
    public List<Sala> calcularEspacoNecessario(List<Sala> salas) {
        List<Sala> salasUtilizadas = new ArrayList<>();
        int alunosRestantes = this.numeroAlunos;

        for (Sala sala : salas) {
            if (alunosRestantes <= 0) break; // Se todos os alunos foram acomodados, sair do loop

            if (sala.getCapacidade() >= alunosRestantes) {
                salasUtilizadas.add(sala);
                alunosRestantes = 0; // Todos os alunos foram acomodados
            } else {
                salasUtilizadas.add(sala);
                alunosRestantes -= sala.getCapacidade();
            }
        }

        if (alunosRestantes > 0) {
            System.out.println("Não há salas suficientes para acomodar todos os alunos do curso " + this.nome + ".");
        } else {
            System.out.println("Espaço alocado com sucesso para o curso " + this.nome + ".");
        }

        return salasUtilizadas;
    }

    // Metodo para listar UCs associadas
    public void listarUCs() {
        if (ucs.isEmpty()) {
            System.out.println("Nenhuma UC associada ao curso " + this.nome + ".");
        } else {
            System.out.println("UCs associadas ao curso " + this.nome + ":");
            for (UC uc : ucs) {
                System.out.println("- " + uc.getNome());
            }
        }
    }

    @Override
    public String toString() {
        return nome + " (" + numeroAlunos + " alunos)";
    }
}
