package org.example.testejavafxmaven;

public class AlocacoesSalas {
    private int id;
    private int idAvaliacao;
    private int idSala;

    public AlocacoesSalas(int id, int idAvaliacao, int idSala) {
        this.id = id;
        this.idAvaliacao = idAvaliacao;
        this.idSala = idSala;
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

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    @Override
    public String toString() {
        return "AlocacaoSala{" +
                "id=" + id +
                ", idAvaliacao=" + idAvaliacao +
                ", idSala=" + idSala +
                '}';
    }
}
