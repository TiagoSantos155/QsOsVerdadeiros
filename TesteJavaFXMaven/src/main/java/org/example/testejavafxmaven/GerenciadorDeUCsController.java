package org.example.testejavafxmaven;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GerenciadorDeUCsController {

    @FXML
    private Label labelCursoSelecionado;

    @FXML
    private ListView<CheckBox> listViewUCs;

    private String cursoSelecionado;

    private Stage stage;  // Para armazenar o stage atual

    private UCDAO ucDAO = new UCBD();  // Usando a implementação de UCDAO

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

        // Buscar o id do curso no banco de dados para associar as UCs
        int cursoId = buscarCursoId(curso);  // Buscar o id do curso
        List<CheckBox> ucs = getUCsParaCurso(cursoId);  // Buscar UCs para o curso selecionado
        listViewUCs.getItems().addAll(ucs);
    }

    // Método para buscar o id do curso no banco de dados
    private int buscarCursoId(String cursoNome) {
        // Realizar uma consulta para buscar o id do curso pelo nome
        String sql = "SELECT id FROM Cursos WHERE nome = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cursoNome);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");  // Retorna o id do curso
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar id do curso: " + e.getMessage());
        }
        return -1;  // Retorna -1 se o curso não for encontrado
    }

    // Método para buscar as UCs para o curso selecionado
    private List<CheckBox> getUCsParaCurso(int cursoId) {
        List<UC> ucs = ucDAO.buscarUcsPorCurso(cursoId);  // Buscar as UCs do banco de dados
        // Converter as UCs em CheckBoxs para exibição na interface
        return ucs.stream()
                .map(uc -> new CheckBox(uc.getNome()))
                .toList();
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

    @FXML
    public void voltar() {
        try {
            // Carregar a tela de Gerenciar Cursos
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/testejavafxmaven/coordinator_panel.fxml"));
            Scene scene = new Scene(loader.load());

            // Obter o controlador da nova cena
            CoordinatorPanelController coordinatorPanelController = loader.getController();

            // Passar o Stage atual para o controlador da nova cena
            coordinatorPanelController.setStage(stage);

            // Alterar a cena no Stage atual
            stage.setScene(scene);
        } catch (IOException e) {
            System.err.println("Erro ao voltar para a tela de Coordenador.");
            e.printStackTrace();
        }
    }

    // Método para passar o Stage para o controlador
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
