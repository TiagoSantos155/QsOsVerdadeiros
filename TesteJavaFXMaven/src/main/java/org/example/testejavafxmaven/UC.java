package org.example.testejavafxmaven;

public class UC {
    private String nome;

    // Array com os tipos de UC disponíveis
    public static final String[] TIPOS_DE_UC = {"Matemática", "Programação", "Física", "Química", "Espanhol"};

    // Construtor para inicializar a UC com um nome
    public UC(String nome) {
        this.nome = nome;
    }

    // Getter para obter o nome da UC
    public String getNome() {
        return nome;
    }

    // Setter para alterar o nome da UC
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String toString() {
        return nome;
    }
}
