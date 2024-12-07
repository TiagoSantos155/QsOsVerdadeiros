package org.example.testejavafxmaven;

public class CoordenadorCurso extends Utilizador {

    public CoordenadorCurso(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    public CoordenadorCurso() {
        super("Coordenador", "coordenador@example.com", "coord123");
    }
}
