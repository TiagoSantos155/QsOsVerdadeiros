package org.example.testejavafxmaven;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class GerenciadorDeUCsController {

    @FXML
    private Label labelCursoSelecionado;

    @FXML
    private ListView<CheckBox> listViewUCs;

    private String cursoSelecionado;

    private Stage stage;  // Para armazenar o stage atual

    // Método para definir o curso selecionado e carregar suas UCs
    public void setCursoSelecionado(String curso) {
        this.cursoSelecionado = curso;
        labelCursoSelecionado.setText("Curso Selecionado: " + curso);

        // Aqui você carrega as UCs dependendo do curso selecionado
        carregarUCs(curso);  // Carregar as UCs para o curso selecionado
    }

    // Método para carregar as UCs com base no curso selecionado
    private void carregarUCs(String curso) {
        listViewUCs.getItems().clear();  // Limpar a lista de UCs antes de adicionar novas

        // Simulação das UCs dependendo do curso (em uma aplicação real, você puxaria isso de uma base de dados)
        List<CheckBox> ucs = getUCsParaCurso(curso);
        listViewUCs.getItems().addAll(ucs);
    }

    // Método para simular a obtenção das UCs do curso (aqui você faria uma consulta ao banco de dados)
    private List<CheckBox> getUCsParaCurso(String curso) {
        if ("Curso de Computação".equals(curso)) {
            return List.of(new CheckBox("Estruturas de Dados"), new CheckBox("Algoritmos"), new CheckBox("Banco de Dados"));
        } else if ("Curso de Matemática".equals(curso)) {
            return List.of(new CheckBox("Cálculo I"), new CheckBox("Álgebra Linear"), new CheckBox("Geometria"));
        } else if ("Curso de Física".equals(curso)) {
            return List.of(new CheckBox("Física Clássica"), new CheckBox("Física Quântica"));
        } else {
            return List.of(new CheckBox("Biologia Geral"), new CheckBox("Genética"));
        }
    }

    // Método para definir as UCs (como no exemplo anterior)
    @FXML
    private void definirUCs() {
        List<CheckBox> selectedUCs = listViewUCs.getItems().stream()
                .filter(CheckBox::isSelected)
                .toList();

        // Aqui você pode tratar as UCs selecionadas (ex: salvar no banco de dados)
        for (CheckBox uc : selectedUCs) {
            System.out.println("UC selecionada: " + uc.getText());
        }
    }

    // Método para voltar para a tela de Gerenciar Cursos
    @FXML
    public void voltar() {
        try {
            // Carregar a tela de Gerenciar Cursos
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/testejavafxmaven/GerenciadorDeCursos.fxml"));
            Scene scene = new Scene(loader.load());

            // Obter o controlador da nova cena
            GerenciadorDeCursosController cursosController = loader.getController();

            // Passar o Stage atual para o controlador da nova cena
            cursosController.setStage(stage); // Passando o stage atual para o próximo controlador

            // Alterar a cena no Stage atual
            stage.setScene(scene);
        } catch (IOException e) {
            System.err.println("Erro ao voltar para a tela de Gerenciar Cursos.");
            e.printStackTrace();
        }
    }

    // Método para passar o Stage para o controlador
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
