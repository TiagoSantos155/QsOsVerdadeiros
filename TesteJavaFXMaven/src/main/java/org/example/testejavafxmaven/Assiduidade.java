package org.example.testejavafxmaven;

public class Assiduidade {
    private int id;
    private int idAvaliacao;
    private String obrigatorio; // SIM ou NAO

    public Assiduidade(int id, int idAvaliacao, String obrigatorio) {
        this.id = id;
        this.idAvaliacao = idAvaliacao;
        this.obrigatorio = obrigatorio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAvaliacao() {
        return idAvaliacao;
    }

    public void setIdAvaliacao(int idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    public String getObrigatorio() {
        return obrigatorio;
    }

    public void setObrigatorio(String obrigatorio) {
        this.obrigatorio = obrigatorio;
    }

    @Override
    public String toString() {
        return "Assiduidade{" +
                "id=" + id +
                ", idAvaliacao=" + idAvaliacao +
                ", obrigatorio='" + obrigatorio + '\'' +
                '}';
    }
}
