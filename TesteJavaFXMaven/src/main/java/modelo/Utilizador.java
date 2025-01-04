package modelo;

public class Utilizador {
    private int id;
    private String nome;
    private String tipo; // Exemplo: "Administrador", "Coordenador"
    private String email;
    private String senha;

    // Construtor completo
    public Utilizador(int id, String nome, String tipo, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.email = email;
        this.senha = senha;
    }

    // Construtor sem ID (para inserções)
    public Utilizador(String nome, String tipo, String email, String senha) {
        this.nome = nome;
        this.tipo = tipo;
        this.email = email;
        this.senha = senha;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Utilizador{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}
