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

    public void definirSemestres() {
        if (inicioSemestre1.getValue() != null && fimSemestre1.getValue() != null &&
                inicioSemestre2.getValue() != null && fimSemestre2.getValue() != null) {

            LocalDate inicio1 = inicioSemestre1.getValue();
            LocalDate fim1 = fimSemestre1.getValue();
            LocalDate inicio2 = inicioSemestre2.getValue();
            LocalDate fim2 = fimSemestre2.getValue();

            if (fim1.isBefore(inicio1)) {
                mostrarAlerta("Erro", "A data de fim do Semestre 1 não pode ser anterior à data de início.");
                return;
            }

            if (fim2.isBefore(inicio2)) {
                mostrarAlerta("Erro", "A data de fim do Semestre 2 não pode ser anterior à data de início.");
                return;
            }

            if (inicio2.isBefore(fim1)) {
                mostrarAlerta("Erro", "A data de início do Semestre 2 não pode ser anterior à data de fim do Semestre 1.");
                return;
            }

            // Definir semestres no objeto Semestre
            semestre.definirSemestre1(inicio1.toString(), fim1.toString());
            semestre.definirSemestre2(inicio2.toString(), fim2.toString());

            // Salvar na base de dados
            SemestreDAO semestreDAO = new SemestreDAO();
            semestreDAO.salvarSemestres(inicio1.toString(), fim1.toString(), inicio2.toString(), fim2.toString());

            mostrarAlerta("Sucesso", "Semestres definidos e salvos na base de dados com sucesso!");
            atualizarListaEpocas();
        } else {
            mostrarAlerta("Erro", "Todas as datas dos semestres devem ser preenchidas.");
        }
    }



    public void adicionarEpoca() {
        if (tipoEpoca.getSelectionModel().getSelectedIndex() != -1 &&
                dataInicioEpoca.getValue() != null &&
                dataFimEpoca.getValue() != null) {

            LocalDate inicio = dataInicioEpoca.getValue();
            LocalDate fim = dataFimEpoca.getValue();

            if (fim.isBefore(inicio)) {
                mostrarAlerta("Erro", "A data de fim da época não pode ser anterior à data de início.");
                return;
            }

            String tipo = EpocaExames.TIPOS_DE_EPOCAS[tipoEpoca.getSelectionModel().getSelectedIndex()];

            // Salvar na base de dados sem depender do semestre
            EpocaDAO epocaDAO = new EpocaDAO();
            epocaDAO.salvar(tipo, inicio.toString(), fim.toString());

            mostrarAlerta("Sucesso", "Época adicionada e salva na base de dados com sucesso!");
            atualizarListaEpocas();
        } else {
            mostrarAlerta("Erro", "Todos os campos para adicionar uma época devem ser preenchidos.");
        }
    }



    private void atualizarListaEpocas() {
        StringBuilder sb = new StringBuilder();

        // Adicionar informações das épocas de exames da base de dados
        EpocaDAO epocaDAO = new EpocaDAO();
        String epocas = epocaDAO.listarEpocas();
        sb.append("Épocas de Exames:\n").append(epocas);

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