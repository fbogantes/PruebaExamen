/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import gestion.MateriaGestion;
import gestion.NotaGestion;
import gestion.ProfesorGestion;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.inject.Named;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import model.Profesor;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
//import javax.enterprise.context.RequestScoped;
//import javax.json.JsonArray;
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.Entity;
//import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;

/**
 *
 * @author Frey
 */
@Named(value = "profesorController")
@RequestScoped
public class ProfesorController extends Profesor implements Serializable{
    
    private int id;
    private String tiraJson = "";
    private String salida; 
    private String codigo; 
    private String nombreCompleto;
    private String materia;
    private double notaProfesor;
    private Date fechaCreacion;
    private Date fechaNaci;
    private String genero;


    private final String URI = "http://localhost:8080/PruebaExamen-1.0-SNAPSHOT/resources/profesor";
    /**
     * Creates a new instance of ProfesorController
     */
    public ProfesorController() {
    }
    
    
    public void recupera(String codigo) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(URI + "/" + codigo);
        JsonObject response = target.request(MediaType.APPLICATION_JSON).get(JsonObject.class);
            codigo = response.asJsonObject().getString("codigo");
            nombreCompleto = response.asJsonObject().getString("nombreCompleto");
            materia = response.asJsonObject().getString("materia");
            notaProfesor = response.asJsonObject().getInt("notaProfesor");
            fechaCreacion = format.parse(response.asJsonObject().getString("fechaCreacion"));
            fechaNaci = format.parse(response.asJsonObject().getString("fechaNaci"));
            genero = response.asJsonObject().getString("genero");
            salida = response.toString();
    }
    
    public void creaJson() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String fecha1 = format.format(this.getFechaCreacion());
        String fecha2= format.format(this.getFechaNaci());
        JsonObjectBuilder creadorJson = Json.createObjectBuilder();
        JsonObject objectJson = creadorJson.add("codigo", this.getCodigo())
                .add("nombreCompleto", this.getNombreCompleto())
                .add("materia", this.getMateria())
                .add("notaProfesor", this.getNotaProfesor())
                .add("fechaCreacion", fecha1)
                .add("fechaNacimiento", fecha2)
                .add("genero", this.getGenero())
                .build();
        StringWriter tira = new StringWriter();
        JsonWriter jsonWriter = Json.createWriter(tira);
        jsonWriter.writeObject(objectJson);
        setTiraJson(tira.toString());
    }
    
    public void crearObjetoProfesor() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            JsonReader lectorJson = Json.createReader(new StringReader(tiraJson));
            JsonObject objectJson = lectorJson.readObject();
            this.setCodigo(objectJson.getString("codigo"));
            this.setNombreCompleto(objectJson.getString("nombreCompleto"));
            this.setMateria(objectJson.getString("materia"));
             this.setNotaProfesor(objectJson.getInt("notaProfesor"));
            this.setFechaCreacion(format.parse(objectJson.getString("fechaCreacion")));
            this.setFechaNaci(format.parse(objectJson.getString("fechaNaci")));
            this.setGenero(objectJson.getString("genero"));
        } catch (ParseException ex) {
            Logger.getLogger(ProfesorController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

    }
       
    public void respaldo() {
        ZipOutputStream out = null;
        try {
            
            String json = ProfesorGestion.generarJson() + MateriaGestion.generarJson() + NotaGestion.generarJson();

            File f = new File(FacesContext
                    .getCurrentInstance().
                    getExternalContext()
                    .getRealPath("/respaldo") + "profesor.zip");
            out = new ZipOutputStream(new FileOutputStream(f));
            ZipEntry e = new ZipEntry("profesor.json");
            out.putNextEntry(e);
            byte[] data = json.getBytes();
            out.write(data, 0, data.length);
            out.closeEntry();
            out.close();

            File zipPath = new File(FacesContext
                    .getCurrentInstance().
                    getExternalContext()
                    .getRealPath("/respaldo") + "profesor.zip");

            byte[] zip = Files.readAllBytes(zipPath.toPath());

            HttpServletResponse respuesta = (HttpServletResponse) FacesContext.getCurrentInstance()
                    .getExternalContext().getResponse();
            ServletOutputStream flujo = respuesta.getOutputStream();

            respuesta.setContentType("application/pdf");
            respuesta.addHeader("Content-disposition", "attachment; filename=profes.zip");

            flujo.write(zip);
            flujo.flush();
            FacesContext.getCurrentInstance().responseComplete();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProfesorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProfesorController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(ProfesorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTiraJson() {
        return tiraJson;
    }

    public void setTiraJson(String tiraJson) {
        this.tiraJson = tiraJson;
    }

    public String getSalida() {
        return salida;
    }

    public void setSalida(String salida) {
        this.salida = salida;
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
}