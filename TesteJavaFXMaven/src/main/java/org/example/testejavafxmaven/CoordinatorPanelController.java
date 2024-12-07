package org.example.testejavafxmaven;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.testejavafxmaven.Cursos;
import org.example.testejavafxmaven.Sala;
import org.example.testejavafxmaven.UC;

import java.util.ArrayList;
import java.util.List;

public class CoordinatorPanelController {
    @FXML private ListView<String> listView;
    @FXML private TextField inputNome, inputNumeroAlunos, inputCapacidade, inputUC;

    private final List<Cursos> cursos = new ArrayList<>();
    private final List<Sala> salas = new ArrayList<>();
    private final List<UC> ucs = new ArrayList<>();

    @FXML
    public void initialize() {
        // Exemplos de dados para inicializar
        cursos.add(new Cursos("Engenharia Informática", 200));
        cursos.add(new Cursos("Gestão Empresarial", 150));

        salas.add(new Sala("Sala A", 50));
        salas.add(new Sala("Sala B", 100));

        ucs.add(new UC("Programação"));
        ucs.add(new UC("Matemática Aplicada"));
    }

    // Método para listar todos os cursos
    public void listarCursos() {
        listView.getItems().clear();
        for (Cursos curso : cursos) {
            listView.getItems().add(curso.getNome() + " (" + curso.getNumeroAlunos() + " alunos)");
        }
    }

    // Método para listar todas as salas
    public void listarSalas() {
        listView.getItems().clear();
        for (Sala sala : salas) {
            listView.getItems().add(sala.getNome() + " (Capacidade: " + sala.getCapacidade() + ")");
        }
    }

    // Método para listar todas as UCs
    public void listarUCs() {
        listView.getItems().clear();
        for (UC uc : ucs) {
            listView.getItems().add(uc.getNome());
        }
    }

    // Método para adicionar um novo curso
    public void adicionarCurso() {
        String nome = inputNome.getText();
        String numeroAlunosStr = inputNumeroAlunos.getText();

        if (nome.isEmpty() || numeroAlunosStr.isEmpty()) {
            listView.getItems().add("Erro: Preencha todos os campos para adicionar um curso.");
            return;
        }

        try {
            int numeroAlunos = Integer.parseInt(numeroAlunosStr);
            Cursos novoCurso = new Cursos(nome, numeroAlunos);
            cursos.add(novoCurso);
            listarCursos();
        } catch (NumberFormatException e) {
            listView.getItems().add("Erro: Número de alunos deve ser um valor numérico.");
        }
    }

    // Método para adicionar uma nova sala
    public void adicionarSala() {
        String nome = inputNome.getText();
        String capacidadeStr = inputCapacidade.getText();

        if (nome.isEmpty() || capacidadeStr.isEmpty()) {
            listView.getItems().add("Erro: Preencha todos os campos para adicionar uma sala.");
            return;
        }

        try {
            int capacidade = Integer.parseInt(capacidadeStr);
            Sala novaSala = new Sala(nome, capacidade);
            salas.add(novaSala);
            listarSalas();
        } catch (NumberFormatException e) {
            listView.getItems().add("Erro: Capacidade deve ser um valor numérico.");
        }
    }

    // Método para adicionar uma nova UC
    public void adicionarUC() {
        String nomeUC = inputUC.getText();

        if (nomeUC.isEmpty()) {
            listView.getItems().add("Erro: Preencha o campo para adicionar uma UC.");
            return;
        }

        UC novaUC = new UC(nomeUC);
        ucs.add(novaUC);
        listarUCs();
    }
}
