package org.example.testejavafxmaven;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EpocaExames {

    public static final String[] TIPOS_DE_EPOCAS = {"Normal", "Recurso", "Especial"};

    private final List<Epoca> epocas = new ArrayList<>();

    public void adicionarEpoca(String tipo, String inicio, String fim, Semestre semestre) {
        // Exemplo: uso do objeto semestre (se necessário)
        System.out.println("Semestre usado: " + semestre);

        epocas.add(new Epoca(tipo, inicio, fim));
    }

    public String getEpocas() {
        StringBuilder sb = new StringBuilder();
        for (Epoca epoca : epocas) {
            sb.append("Tipo: ").append(epoca.getTipo()).append("\n")
                    .append("Data Início: ").append(epoca.getInicio()).append("\n")
                    .append("Data Fim: ").append(epoca.getFim()).append("\n\n");
        }
        return sb.toString();
    }

    public boolean verificarSobreposicao(LocalDate inicio, LocalDate fim) {
        for (Epoca epoca : epocas) {
            LocalDate dataInicio = LocalDate.parse(epoca.inicio);
            LocalDate dataFim = LocalDate.parse(epoca.fim);

            if (!(fim.isBefore(dataInicio) || inicio.isAfter(dataFim))) {
                return true;
            }
        }
        return false;
    }

    public String listarEpocas() {
        StringBuilder sb = new StringBuilder();
        for (Epoca epoca : epocas) {
            sb.append(epoca.tipo)
                    .append(": ").append(epoca.inicio).append(" a ").append(epoca.fim)
                    .append("\n");
        }
        return sb.toString().trim();
    }

    private static class Epoca {
        String tipo;
        String inicio;
        String fim;

        Epoca(String tipo, String inicio, String fim) {
            this.tipo = tipo;
            this.inicio = inicio;
            this.fim = fim;
        }

        public String getInicio() {
            return inicio;
        }

        public void setInicio(String inicio) {
            this.inicio = inicio;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public String getFim() {
            return fim;
        }

        public void setFim(String fim) {
            this.fim = fim;
        }

    }
}
