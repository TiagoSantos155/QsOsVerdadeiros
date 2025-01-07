package modelo;

import java.time.LocalDate;

public class Epoca {
    private int id;
    private String tipo; // "Normal", "Recurso", "Especial"
    private LocalDate inicio;
    private LocalDate fim;
    private int semestreId;

    public Epoca(int id, String tipo, LocalDate inicio, LocalDate fim, int semestreId) {
        this.id = id;
        this.tipo = tipo;
        this.inicio = inicio;
        this.fim = fim;
        this.semestreId = semestreId;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public void setFim(LocalDate fim) {
        this.fim = fim;
    }

    public int getSemestreId() {
        return semestreId;
    }

    public void setSemestreId(int semestreId) {
        this.semestreId = semestreId;
    }

    @Override
    public String toString() {
        return "Epoca{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", inicio=" + inicio +
                ", fim=" + fim +
                ", semestreId=" + semestreId +
                '}';
    }
}
