package org.example.testejavafxmaven;

public class Semestre {
    private String inicioSemestre1;
    private String fimSemestre1;
    private String inicioSemestre2;
    private String fimSemestre2;

    // Métodos para definir o primeiro semestre
    public void definirSemestre1(String inicio, String fim) {
        this.inicioSemestre1 = inicio;
        this.fimSemestre1 = fim;
    }

    // Métodos para definir o segundo semestre
    public void definirSemestre2(String inicio, String fim) {
        this.inicioSemestre2 = inicio;
        this.fimSemestre2 = fim;
    }

    // Getters
    public String getInicioSemestre1() {
        return inicioSemestre1;
    }

    public String getFimSemestre1() {
        return fimSemestre1;
    }

    public String getInicioSemestre2() {
        return inicioSemestre2;
    }

    public String getFimSemestre2() {
        return fimSemestre2;
    }

    @Override
    public String toString() {
        return "Semestre 1: " + inicioSemestre1 + " a " + fimSemestre1 + "\n" +
                "Semestre 2: " + inicioSemestre2 + " a " + fimSemestre2;
    }
}