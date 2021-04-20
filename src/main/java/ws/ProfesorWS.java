/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.fasterxml.jackson.annotation.JsonFormat;
import static com.mysql.cj.MysqlType.JSON;
import gestion.ProfesorGestion;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import model.Profesor;

/**
 * REST Web Service
 *
 * @author Frey
 */
@Path("profesor")
@RequestScoped
public class ProfesorWS {

    @Context
    private UriInfo context;
    /**
     * Creates a new instance of ProfesorWS
     */
    public ProfesorWS() {
    }

    /**
     * Retrieves representation of an instance of ws.ProfesorWS
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Profesor> getProfesores() {
        //TODO return proper representation object
        return ProfesorGestion.getProfesores();
    }

    /**
     * Retrieves representation of an instance of ws.ProfesorWS
     *
     * @return an instance of java.lang.String
     * http://localhost:8080/PruebaExamen-1.0-SNAPSHOT/resources/profesor/1;
     */
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Profesor getProfesor(@PathParam("id") String codigo) {
        
        Profesor profesor = new Profesor(
                
                );
        
        Profesor profesor2 = ProfesorGestion.getProfesor(codigo);
        if (profesor2 != null) {
            return profesor2;
        } else {
            return profesor;
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Profesor putProfesor(Profesor profesor) {
        Profesor profesor2 = ProfesorGestion.getProfesor(profesor.getCodigo());
        if (profesor2 != null) {
            ProfesorGestion.updateProfesor(profesor);
        } else {
            ProfesorGestion.insertProfesor(profesor);
        }
        return ProfesorGestion.getProfesor(profesor.getCodigo());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Profesor postProfesor(Profesor profesor) {
        Profesor profesor2 = ProfesorGestion.getProfesor(profesor.getCodigo());
        if (profesor2 != null) {
            return null;
        } else {
            ProfesorGestion.insertProfesor(profesor);
        }
        return ProfesorGestion.getProfesor(profesor.getCodigo());
    }

//    @DELETE
//    @Path("/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Profesor deleteProfesor(@PathParam("id") String codigo) {
//        Profesor profesor2 = ProfesorGestion.getProfesor(codigo);
//        if (profesor2 != null) {
//            if (ProfesorGestion.deleteProfesor(id)) {
//                return profesor2;
//            }
//        }
//        return null;
//    }
}
