/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion;

import controller.ProfesorController;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import model.Conexion;
import model.Profesor;

/**
 *
 * @author Frey
 */
public class ProfesorGestion {
    
    private static final String SQL_GETPROFESORES = "SELECT * FROM profesor";
    private static final String SQL_GETPROFESOR = "SELECT * FROM profesor where codigo=?";
    private static final String SQL_INSERTPROFESOR = "insert into profesor(codigo,nombreCompleto,materia,notaProfesor,fechaCreacion,fechaNaci,genero) values (?,?,?,?,?,?,?)";
    private static final String SQL_UPDATEPROFESOR = "update  profesor set nombreCompleto=?,materia=?,notaProfesor=?,fechaCreacion=?,fechaNaci=?,genero=? where codigo=?";
    private static final String SQL_DELETEPROFESOR = "Delete FROM profesor where codigo=?";
    
    public static ArrayList<Profesor> getProfesores() {
        ArrayList<Profesor> list = new ArrayList<>();
        try {
            PreparedStatement sentencia = Conexion.getConexion().prepareStatement(SQL_GETPROFESORES);
            ResultSet rs = sentencia.executeQuery();
            while (rs != null && rs.next()) {
                list.add(new Profesor(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getDate(5),
                        rs.getDate(6),
                        rs.getString(7).charAt(0)
                ));

            }

        } catch (SQLException ex) {
            Logger.getLogger(ProfesorGestion.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;

    }

    public static Profesor getProfesor(String codigo) {
        Profesor profesor = null;
        try {
            PreparedStatement sentencia = Conexion.getConexion().prepareStatement(SQL_GETPROFESOR);
            sentencia.setString(1, codigo);
            ResultSet rs = sentencia.executeQuery();
            while (rs != null && rs.next()) {
                profesor = new Profesor(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getDate(5),
                        rs.getDate(6),
                        rs.getString(7).charAt(0)
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProfesorGestion.class.getName()).log(Level.SEVERE, null, ex);

        }
        return profesor;
    }

    public static boolean insertProfesor(Profesor profesor) {
        try {
            PreparedStatement sentencia = Conexion.getConexion().prepareStatement(SQL_INSERTPROFESOR);
            sentencia.setString(1, profesor.getCodigo());
            sentencia.setString(2, profesor.getNombreCompleto());
            sentencia.setString(3, profesor.getMateria());
            sentencia.setDouble(4, profesor.getNotaProfesor());
            sentencia.setObject(5, profesor.getFechaCreacion());
            sentencia.setObject(6, profesor.getFechaNaci());
            sentencia.setString(7, "" + profesor.getGenero());
            return sentencia.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ProfesorGestion.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    public static boolean updateProfesor(Profesor profesor) {
        try {

            PreparedStatement sentencia = Conexion.getConexion().prepareCall(SQL_UPDATEPROFESOR);
            sentencia.setString(1, profesor.getNombreCompleto());
            sentencia.setString(2, profesor.getMateria());
            sentencia.setDouble(3, profesor.getNotaProfesor());
            sentencia.setObject(4, profesor.getFechaCreacion());
            sentencia.setObject(5, profesor.getFechaNaci());
            sentencia.setString(6, "" + profesor.getGenero());
            sentencia.setString(7, profesor.getCodigo());
            return sentencia.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ProfesorGestion.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

    public static boolean deleteProfesor(Profesor profesor) {
        try {
            PreparedStatement sentencia = Conexion.getConexion().prepareStatement(SQL_DELETEPROFESOR);
            sentencia.setString(1, profesor.getCodigo());
            return sentencia.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ProfesorGestion.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }
    
    public static String generarJson() {
        Profesor profesor = null;
        String tiraJson = "";
        String fechaCreacion;
        String fechaNaci;

        try {
            PreparedStatement consulta = Conexion.getConexion().prepareStatement(SQL_GETPROFESORES);
            ResultSet datos = consulta.executeQuery();
            while (datos != null && datos.next()) {
                profesor = new Profesor(
                        datos.getString(1),
                        datos.getString(2),
                        datos.getString(3),
                        datos.getDouble(4),
                        datos.getDate(5),
                        datos.getDate(6),
                        datos.getString(7).charAt(0));

                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                fechaCreacion = sdf.format(profesor.getFechaCreacion());
                fechaNaci = sdf.format(profesor.getFechaNaci());
                JsonObjectBuilder creadorJson = Json.createObjectBuilder();
                JsonObject objetoJson = creadorJson.add("codigo", profesor.getCodigo())
                        .add("nombreCompleto", profesor.getNombreCompleto())
                        .add("materia", profesor.getMateria())
                        .add("notaProfesor", profesor.getNotaProfesor())
                        .add("fechaCreacion", fechaCreacion)
                        .add("fechaNaci", fechaNaci)
                        .add("genero", profesor.getGenero()).build();

                StringWriter tira = new StringWriter();
                JsonWriter jsonWriter = Json.createWriter(tira);
                jsonWriter.writeObject(objetoJson);
                if (tiraJson == null) {
                    tiraJson = tira.toString() + "\n";
                } else {
                    tiraJson = tiraJson + tira.toString() + "\n";
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfesorGestion.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return tiraJson;
    }
    
    public void respaldo() {
        ZipOutputStream out = null;
        try {

            String json = ProfesorGestion.generarJson();

            File f = new File(FacesContext
                    .getCurrentInstance().
                    getExternalContext()
                    .getRealPath("/respaldo") + "profesor.zip");
            out = new ZipOutputStream(new FileOutputStream(f));
            ZipEntry e = new ZipEntry("estudiantes.json");
            out.putNextEntry(e);
            byte[] data = json.getBytes();
            out.write(data, 0, data.length);
            out.closeEntry();
            out.close();

            File zipPath = new File(FacesContext
                    .getCurrentInstance().
                    getExternalContext()
                    .getRealPath("/respaldo") + "estudiantes.zip");

            byte[] zip = Files.readAllBytes(zipPath.toPath());

            HttpServletResponse respuesta = (HttpServletResponse) FacesContext.getCurrentInstance()
                    .getExternalContext().getResponse();
            ServletOutputStream flujo = respuesta.getOutputStream();

            respuesta.setContentType("application/pdf");
            respuesta.addHeader("Content-disposition", "attachment; filename=estudiantes.zip");

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
}
