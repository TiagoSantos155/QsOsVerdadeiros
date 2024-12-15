package org.example.testejavafxmaven;

import java.sql.Timestamp;

public class Avaliacoes {
    private int id;
    private String tipoAvaliacao;
    private double ponderacao;
    private Timestamp dataHora;
    private String sala;
    private boolean horarioHabitual;
    private boolean requerComputador;
    private int idUc;
    private Integer idEpoca; // Pode ser null

    public Avaliacoes(int id, String tipoAvaliacao, double ponderacao, Timestamp dataHora, String sala, boolean horarioHabitual, boolean requerComputador, int idUc, Integer idEpoca) {
        this.id = id;
        this.tipoAvaliacao = tipoAvaliacao;
        this.ponderacao = ponderacao;
        this.dataHora = dataHora;
        this.sala = sala;
        this.horarioHabitual = horarioHabitual;
        this.requerComputador = requerComputador;
        this.idUc = idUc;
        this.idEpoca = idEpoca;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoAvaliacao() {
        return tipoAvaliacao;
    }

    public void setTipoAvaliacao(String tipoAvaliacao) {
        this.tipoAvaliacao = tipoAvaliacao;
    }

    public double getPonderacao() {
        return ponderacao;
    }

    public void setPonderacao(double ponderacao) {
        this.ponderacao = ponderacao;
    }

    public Timestamp getDataHora() {
        return dataHora;
    }

    public void setDataHora(Timestamp dataHora) {
        this.dataHora = dataHora;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public boolean isRequerComputador() {
        return requerComputador;
    }

    public void setRequerComputador(boolean requerComputador) {
        this.requerComputador = requerComputador;
    }

    public boolean isHorarioHabitual() {
        return horarioHabitual;
    }

    public void setHorarioHabitual(boolean horarioHabitual) {
        this.horarioHabitual = horarioHabitual;
    }

    public int getIdUc() {
        return idUc;
    }

    public void setIdUc(int idUc) {
        this.idUc = idUc;
    }

    public Integer getIdEpoca() {
        return idEpoca;
    }

    public void setIdEpoca(Integer idEpoca) {
        this.idEpoca = idEpoca;
    }

    @Override
    public String toString() {
        return "Avaliacao{" +
                "id=" + id +
                ", tipoAvaliacao='" + tipoAvaliacao + '\'' +
                ", ponderacao=" + ponderacao +
                ", dataHora=" + dataHora +
                ", sala='" + sala + '\'' +
                ", horarioHabitual=" + horarioHabitual +
                ", requerComputador=" + requerComputador +
                ", idUc=" + idUc +
                ", idEpoca=" + idEpoca +
                '}';
    }
}
