package org.example.testejavafxmaven;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.example.testejavafxmaven.Semestre;
import org.example.testejavafxmaven.EpocaExames;

public class AdminPanelController {
    @FXML private TextField inicioSemestre1, fimSemestre1, inicioSemestre2, fimSemestre2;
    @FXML private ComboBox<String> tipoEpoca;
    @FXML private TextField dataInicioEpoca, dataFimEpoca;

    private final Semestre semestre = new Semestre();
    private final EpocaExames epocaExames = new EpocaExames();

    @FXML
    public void initialize() {
        tipoEpoca.getItems().addAll(EpocaExames.TIPOS_DE_EPOCAS);
    }

    public void definirSemestre1() {
        semestre.definirSemestre1(inicioSemestre1.getText(), fimSemestre1.getText());
        System.out.println(semestre);
    }

    public void definirSemestre2() {
        semestre.definirSemestre2(inicioSemestre2.getText(), fimSemestre2.getText());
        System.out.println(semestre);
    }

    public void adicionarEpoca() {
        epocaExames.adicionarEpoca(tipoEpoca.getSelectionModel().getSelectedIndex(),
                dataInicioEpoca.getText(), dataFimEpoca.getText(), null);
        System.out.println("Épocas: " + epocaExames.getEpocas());
    }
}

