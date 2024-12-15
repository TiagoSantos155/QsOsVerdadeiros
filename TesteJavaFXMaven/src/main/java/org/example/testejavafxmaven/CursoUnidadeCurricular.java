package org.example.testejavafxmaven;

public class CursoUnidadeCurricular {
    private int idCurso;
    private int idUnidadeCurricular;

    public CursoUnidadeCurricular() {}

    public CursoUnidadeCurricular(int idCurso, int idUnidadeCurricular) {
        this.idCurso = idCurso;
        this.idUnidadeCurricular = idUnidadeCurricular;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public int getIdUnidadeCurricular() {
        return idUnidadeCurricular;
    }

    public void setIdUnidadeCurricular(int idUnidadeCurricular) {
        this.idUnidadeCurricular = idUnidadeCurricular;
    }
}
