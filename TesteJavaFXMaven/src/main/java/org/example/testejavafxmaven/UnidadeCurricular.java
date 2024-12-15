package org.example.testejavafxmaven;

public class UnidadeCurricular {
    private int id;
    private String nome;
    private String tipoAvaliacao; // Valores: "MISTA" ou "CONTINUA"

    public UnidadeCurricular() {}

    public UnidadeCurricular(int id, String nome, String tipoAvaliacao) {
        this.id = id;
        this.nome = nome;
        this.tipoAvaliacao = tipoAvaliacao;
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

    public String getTipoAvaliacao() {
        return tipoAvaliacao;
    }

    public void setTipoAvaliacao(String tipoAvaliacao) {
        this.tipoAvaliacao = tipoAvaliacao;
    }

    @Override
    public String toString() {
        return "Unidade Curricular: " + nome + " (ID: " + id + ", Tipo: " + tipoAvaliacao + ")";
    }

}