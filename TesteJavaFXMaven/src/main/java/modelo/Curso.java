package modelo;

public class Curso {
    private int id;
    private String nome;
    private int numeroAlunos;

    // Construtores
    public Curso() {
    }

    public Curso(int id, String nome, int numeroAlunos) {
        this.id = id;
        this.nome = nome;
        this.numeroAlunos = numeroAlunos;
    }

    public Curso(String nome, int numeroAlunos) {
        this.nome = nome;
        this.numeroAlunos = numeroAlunos;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumeroAlunos() {
        return numeroAlunos;
    }

    public void setNumeroAlunos(int numeroAlunos) {
        this.numeroAlunos = numeroAlunos;
    }

    // Método toString (opcional, mas útil para depuração)
    @Override
    public String toString() {
        return "Curso{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", numeroAlunos=" + numeroAlunos +
                '}';
    }
}
