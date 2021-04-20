/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Frey
 */
public class Nota {
    private int id;
    private String idEstudiante;
    private String idMateria;
    private String idPeriodo;
    private double nota;

    
    public Nota() {
    }
    
    public Nota(int id, String idEstudiante, String idMateria, String idPeriodo, double nota) {
        this.id = id;
        this.idEstudiante = idEstudiante;
        this.idMateria = idMateria;
        this.idPeriodo = idPeriodo;
        this.nota = nota;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(String idMateria) {
        this.idMateria = idMateria;
    }

    public String getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(String idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
    
    
}
