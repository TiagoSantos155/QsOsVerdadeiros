package org.example.testejavafxmaven;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminPanelController {
    @FXML private DatePicker inicioSemestre1, fimSemestre1, inicioSemestre2, fimSemestre2;
    @FXML private ComboBox<String> tipoEpoca;
    @FXML private DatePicker dataInicioEpoca, dataFimEpoca;

    private final Semestre semestre = new Semestre();
    private final EpocaExames epocaExames = new EpocaExames();

    @FXML
    public void initialize() {
        tipoEpoca.getItems().addAll(EpocaExames.TIPOS_DE_EPOCAS);
    }

    public void definirSemestre1() {
        if (inicioSemestre1.getValue() != null && fimSemestre1.getValue() != null) {
            String inicio = inicioSemestre1.getValue().toString();
            String fim = fimSemestre1.getValue().toString();
            semestre.definirSemestre1(inicio, fim);
            System.out.println("Semestre 1 definido: " + semestre);
        } else {
            System.out.println("Erro: As datas do Semestre 1 devem ser preenchidas.");
        }
    }

    public void definirSemestre2() {
        if (inicioSemestre2.getValue() != null && fimSemestre2.getValue() != null) {
            String inicio = inicioSemestre2.getValue().toString();
            String fim = fimSemestre2.getValue().toString();
            semestre.definirSemestre2(inicio, fim);
            System.out.println("Semestre 2 definido: " + semestre);
        } else {
            System.out.println("Erro: As datas do Semestre 2 devem ser preenchidas.");
        }
    }

    public void adicionarEpoca() {
        if (tipoEpoca.getSelectionModel().getSelectedIndex() != -1 &&
                dataInicioEpoca.getValue() != null &&
                dataFimEpoca.getValue() != null) {
            int tipoIndex = tipoEpoca.getSelectionModel().getSelectedIndex();
            String dataInicio = dataInicioEpoca.getValue().toString();
            String dataFim = dataFimEpoca.getValue().toString();
            epocaExames.adicionarEpoca(tipoIndex, dataInicio, dataFim, null);
            System.out.println("Épocas: " + epocaExames.getEpocas());
        } else {
            System.out.println("Erro: Todos os campos para adicionar uma época devem ser preenchidos.");
        }
    }

    @FXML
    public void voltar() {
        try {
            // Carregar a página de login (Login.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/testejavafxmaven/Login.fxml"));
            Scene scene = new Scene(loader.load());

            // Obter o Stage atual e definir a nova cena
            Stage stage = (Stage) tipoEpoca.getScene().getWindow(); // Obter o Stage de qualquer controle
            stage.setScene(scene);
        } catch (IOException e) {
            System.err.println("Erro ao voltar para a tela de login.");
            e.printStackTrace();
        }
    }
}
