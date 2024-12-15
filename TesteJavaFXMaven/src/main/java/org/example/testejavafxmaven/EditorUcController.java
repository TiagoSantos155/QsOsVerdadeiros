package org.example.testejavafxmaven;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
/**
public class EditorUcController {

    @FXML
    private TextField txtNome;

    @FXML
    private ComboBox<String> comboTipoAvaliacao;

    @FXML
    private ListView<UnidadeCurricular> listUCs;

    private UnidadeCurricularDAO ucDAO = new UnidadeCurricularDAO();

    @FXML
    public void initialize() {
        // Inicializa os valores no ComboBox.
        comboTipoAvaliacao.getItems().addAll("MISTA", "CONTINUA");

        // Carrega a lista de UCs.
        try {
            listUCs.getItems().addAll(ucDAO.listarUnidadesCurriculares());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSalvar() {
        UnidadeCurricular ucSelecionada = listUCs.getSelectionModel().getSelectedItem();
        if (ucSelecionada == null) {
            System.out.println("Por favor, selecione uma UC para editar.");
            return;
        }

        String novoNome = txtNome.getText();
        String novoTipoAvaliacao = comboTipoAvaliacao.getValue();

        if (novoNome.isEmpty() || novoTipoAvaliacao == null) {
            System.out.println("Por favor, preencha todos os campos.");
            return;
        }

        try {
            ucSelecionada.setNome(novoNome);
            ucSelecionada.setTipoAvaliacao(novoTipoAvaliacao);
            ucDAO.update(ucSelecionada);
            System.out.println("Unidade Curricular atualizada com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
*/