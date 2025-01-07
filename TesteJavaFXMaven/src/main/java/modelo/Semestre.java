package modelo;

import java.time.LocalDate;

public class Semestre {
    private int id;
    private LocalDate inicio;
    private LocalDate fim;

    public Semestre(int id, LocalDate inicio, LocalDate fim) {
        this.id = id;
        this.inicio = inicio;
        this.fim = fim;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Semestre{" +
                "id=" + id +
                ", inicio=" + inicio +
                ", fim=" + fim +
                '}';
    }
}
