/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import gestion.ProfesorGestion;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Profesor;


/**
 *
 * @author Frey
 */
@Named(value = "profesorController")
@SessionScoped
public class ProfesorController extends Profesor implements Serializable{
    
//    private int id; 
    private String tiraJson = "xxxx";
//    private String salida; 


    private final String URI = "http://localhost:8080/PruebaExamen-1.0-SNAPSHOT/resources/profesor";
    /**
     * Creates a new instance of ProfesorController
     */
    public ProfesorController() {
    }
    
    public List<Profesor> getProfesores() {
        return ProfesorGestion.getProfesores();
    }
    
    public void recupera(String codigo) {
        Profesor e = ProfesorGestion.getProfesor(codigo);
        if (e != null) {
            this.setCodigo(e.getCodigo());
            this.setNombreCompleto(e.getNombreCompleto());
            this.setMateria(e.getMateria());
            this.setNotaProfesor(e.getNotaProfesor());
            this.setFechaCreacion(e.getFechaCreacion());
            this.setFechaNaci(e.getFechaNaci());
            this.setGenero(e.getGenero());
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                    "Posiblemente el registro no exista");
            FacesContext.getCurrentInstance().addMessage("profesorJsonForm:codigo", msg);

        }
    }
    
    public void creaJson() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String fecha1 = format.format(this.getFechaCreacion());
        String fecha2= format.format(this.getFechaNaci());
        JsonObjectBuilder creadorJson = Json.createObjectBuilder();
        JsonObject objectJson = creadorJson.add("codigo", this.getCodigo())
                .add("nombreCopleto", this.getNombreCompleto())
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
            this.setFechaNaci(format.parse(objectJson.getString("fechaNacimiento")));
            this.setGenero(objectJson.getString("genero").charAt(0));
        } catch (ParseException ex) {
            Logger.getLogger(ProfesorController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
    public String getTiraJson() {
        return tiraJson;
    }

    public void setTiraJson(String tiraJson) {
        this.tiraJson = tiraJson;
    }
//
//    public String getSalida() {
//        return salida;
//    }
//
//    public void setSalida(String salida) {
//        this.salida = salida;
//    }
//    
//    public void hacerGetAll() {
//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target(URI);
//        JsonArray response = target.request(MediaType.APPLICATION_JSON)
//                .get(JsonArray.class);
//        salida = response.toString();
//    }
//
//
//    public void hacerGet() {
//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target(URI + "/" + id);
//        JsonObject response = target.request(MediaType.APPLICATION_JSON)
//                .get(JsonObject.class);
//        salida = response.toString();
//    }
//
//
//    public void hacerDelete() {
//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target(URI + "/" + id);
//        JsonObject response = target.request(MediaType.APPLICATION_JSON)
//                .delete(JsonObject.class);
//        salida = response.asJsonObject().toString();
//    }
//
//
//    public void hacerPut() {
//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target(URI);
//        JsonReader lectorJson = Json.createReader(new StringReader(tiraJson));
//        JsonObject jsonObject = lectorJson.readObject();
//        Response response = target.request(MediaType.APPLICATION_JSON)
//                .put(Entity.json(jsonObject));
//        salida = response.readEntity(String.class);
//    }
//
//
//    public void hacerPost() {
//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target(URI);
//        JsonReader lectorJson = Json.createReader(new StringReader(tiraJson));
//        JsonObject jsonObject = lectorJson.readObject();
//        Response response = target.request(MediaType.APPLICATION_JSON)
//                .post(Entity.json(jsonObject));
//        salida = response.readEntity(String.class);
//    }
    
}