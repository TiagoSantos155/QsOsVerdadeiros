package org.example.testejavafxmaven;

public class Admin extends Utilizador {

    public Admin(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    public Admin() {
        super("Admin", "admin@example.com", "admin123");
    }
}
