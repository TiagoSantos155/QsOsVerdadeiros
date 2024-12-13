package org.example.testejavafxmaven;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class AdminPanelController {
    @FXML private DatePicker inicioSemestre1, fimSemestre1, inicioSemestre2, fimSemestre2;
    @FXML private ComboBox<String> tipoEpoca;
    @FXML private DatePicker dataInicioEpoca, dataFimEpoca;
    @FXML private TextArea listaEpocasArea;

    private final Semestre semestre = new Semestre();
    private final EpocaExames epocaExames = new EpocaExames();

    @FXML
    public void initialize() {
        tipoEpoca.getItems().addAll(EpocaExames.TIPOS_DE_EPOCAS);
        atualizarListaEpocas();
    }

    public void definirSemestre1() {
        if (inicioSemestre1.getValue() != null && fimSemestre1.getValue() != null) {
            LocalDate inicio = inicioSemestre1.getValue();
            LocalDate fim = fimSemestre1.getValue();

            if (fim.isBefore(inicio)) {
                mostrarAlerta("Erro", "A data de fim do Semestre 1 não pode ser anterior à data de início.");
                return;
            }

            semestre.definirSemestre1(inicio.toString(), fim.toString());
            mostrarAlerta("Sucesso", "Semestre 1 definido com sucesso!");
            atualizarListaEpocas();
        } else {
            mostrarAlerta("Erro", "As datas do Semestre 1 devem ser preenchidas.");
        }
    }

    public void definirSemestre2() {
        if (inicioSemestre2.getValue() != null && fimSemestre2.getValue() != null) {
            LocalDate inicio = inicioSemestre2.getValue();
            LocalDate fim = fimSemestre2.getValue();

            if (fim.isBefore(inicio)) {
                mostrarAlerta("Erro", "A data de fim do Semestre 2 não pode ser anterior à data de início.");
                return;
            }

            if (inicio.isBefore(LocalDate.parse(semestre.getFimSemestre1()))) {
                mostrarAlerta("Erro", "A data de início do Semestre 2 não pode ser anterior à data de fim do Semestre 1.");
                return;
            }

            semestre.definirSemestre2(inicio.toString(), fim.toString());
            mostrarAlerta("Sucesso", "Semestre 2 definido com sucesso!");
            atualizarListaEpocas();
        } else {
            mostrarAlerta("Erro", "As datas do Semestre 2 devem ser preenchidas.");
        }
    }

    public void adicionarEpoca() {
        if (tipoEpoca.getSelectionModel().getSelectedIndex() != -1 &&
                dataInicioEpoca.getValue() != null &&
                dataFimEpoca.getValue() != null) {
            LocalDate inicio = dataInicioEpoca.getValue();
            LocalDate fim = dataFimEpoca.getValue();
            int tipoIndex = tipoEpoca.getSelectionModel().getSelectedIndex();

            if (fim.isBefore(inicio)) {
                mostrarAlerta("Erro", "A data de fim da época não pode ser anterior à data de início.");
                return;
            }

            if (tipoIndex == 0 || tipoIndex == 1) { // Época normal ou recurso
                if (inicio.isBefore(LocalDate.parse(semestre.getInicioSemestre1())) ||
                        fim.isAfter(LocalDate.parse(semestre.getFimSemestre2()))) {
                    mostrarAlerta("Erro", "As datas da época devem estar dentro do período dos semestres.");
                    return;
                }
            }

            String tipo = EpocaExames.TIPOS_DE_EPOCAS[tipoIndex]; // Corrigido para pegar o tipo como String
            epocaExames.adicionarEpoca(tipo, inicio.toString(), fim.toString(), semestre);
            mostrarAlerta("Sucesso", "Época adicionada com sucesso!");
            atualizarListaEpocas();
        } else {
            mostrarAlerta("Erro", "Todos os campos para adicionar uma época devem ser preenchidos.");
        }
    }

    private void atualizarListaEpocas() {
        StringBuilder sb = new StringBuilder();

        // Adicionar informações dos semestres
        sb.append("1º Semestre:\n")
                .append("Data Início: ").append(semestre.getInicioSemestre1() != null ? semestre.getInicioSemestre1() : "Não definido").append("\n")
                .append("Data Fim: ").append(semestre.getFimSemestre1() != null ? semestre.getFimSemestre1() : "Não definido").append("\n\n")
                .append("2º Semestre:\n")
                .append("Data Início: ").append(semestre.getInicioSemestre2() != null ? semestre.getInicioSemestre2() : "Não definido").append("\n")
                .append("Data Fim: ").append(semestre.getFimSemestre2() != null ? semestre.getFimSemestre2() : "Não definido").append("\n\n");

        // Adicionar informações das épocas de exames
        sb.append("Épocas de Exames:\n").append(epocaExames.getEpocas());

        listaEpocasArea.setText(sb.toString());
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @FXML
    public void voltar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/testejavafxmaven/Login.fxml"));
            Scene scene = new Scene(loader.load());

            // Obter o controlador da nova cena
            MainController mainController = loader.getController();

            // Configurar o Stage no controlador
            Stage currentStage = (Stage) tipoEpoca.getScene().getWindow();
            mainController.setStage(currentStage);

            // Configurar a nova cena no Stage
            currentStage.setScene(scene);
        } catch (IOException e) {
            System.err.println("Erro ao voltar para a tela de login.");
            e.printStackTrace();
        }
    }
}