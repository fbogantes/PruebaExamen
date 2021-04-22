/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Frey
 */
public class Profesor {
    
    private String codigo;
    private String nombreCompleto;
    private String materia;
    private double notaProfesor;
    private Date fechaCreacion;
    private Date fechaNaci;
    private String genero;

    public Profesor() {
    }

    public Profesor(String codigo, String nombreCompleto, String materia, double notaProfesor, Date fechaCreacion, Date fechaNaci, String genero) {
        this.codigo = codigo;
        this.nombreCompleto = nombreCompleto;
        this.materia = materia;
        this.notaProfesor = notaProfesor;
        this.fechaCreacion = fechaCreacion;
        this.fechaNaci = fechaNaci;
        this.genero = genero;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public double getNotaProfesor() {
        return notaProfesor;
    }

    public void setNotaProfesor(double notaProfesor) {
        this.notaProfesor = notaProfesor;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaNaci() {
        return fechaNaci;
    }

    public void setFechaNaci(Date fechaNaci) {
        this.fechaNaci = fechaNaci;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String fecha1 = format.format(this.fechaCreacion);
        String fecha2 = format.format(this.fechaNaci);
      
        return "{\"Profesor\":{\n\"codigo\":\""
                + codigo + "\",\n\"nombreCompleto\":\""
                + nombreCompleto + "\",\n\"materia\":\""
                + materia + "\",\n\"notaprofesor\":\""
                + notaProfesor + "\",\n\"fechaCreacion\":\""
                + fecha1 + "\",\n\"fechaNaci\":\""
                + fecha2 +  "\",\n\"genero\":\""
                + genero +  "\"\n}\n}";

    }

}
