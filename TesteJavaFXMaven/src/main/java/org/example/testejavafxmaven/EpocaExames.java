package org.example.testejavafxmaven;

import java.util.ArrayList;
import java.util.List;

public class EpocaExames {
    private List<Epoca> epocas = new ArrayList<>();

    public static final String[] TIPOS_DE_EPOCAS = {"Normal", "Recurso", "Especial"};

    public void adicionarEpoca(int tipo, String dataInicio, String dataFim, Integer semestre) {
        if (tipo < 0 || tipo >= TIPOS_DE_EPOCAS.length) {
            throw new IllegalArgumentException("Tipo de época inválido.");
        }
        epocas.add(new Epoca(TIPOS_DE_EPOCAS[tipo], dataInicio, dataFim, semestre));
    }

    public List<Epoca> getEpocas() {
        return epocas;
    }

    public static class Epoca {
        private String nome;
        private String dataInicio;
        private String dataFim;
        private Integer semestre;

        public Epoca(String nome, String dataInicio, String dataFim, Integer semestre) {
            this.nome = nome;
            this.dataInicio = dataInicio;
            this.dataFim = dataFim;
            this.semestre = semestre;
        }

        @Override
        public String toString() {
            return nome + ": " + dataInicio + " - " + dataFim;
        }
    }
}
