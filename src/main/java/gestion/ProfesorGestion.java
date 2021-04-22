package gestion;

import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import model.Conexion;
import model.Profesor;

/**
 *
 * @author Frey
 */
public class ProfesorGestion {

    private static final String SQL_GETPROFESOR = "SELECT * FROM PROFESOR WHERE codigo=?";
    private static final String SQL_GETPROFESORES = "SELECT * FROM profesor";

    public static ArrayList<Profesor> getProfesores() {
        ArrayList<Profesor> lista = new ArrayList<>();
        try {
            PreparedStatement consulta = Conexion.getConexion().prepareStatement(SQL_GETPROFESORES);
            ResultSet datos = consulta.executeQuery();
            while (datos != null && datos.next()) {
                lista.add(new Profesor(
                        datos.getString(1),
                        datos.getString(2),
                        datos.getString(3),
                        datos.getDouble(4),
                        datos.getDate(5),
                        datos.getDate(6),
                        datos.getString(7)
                ));
            }
        } catch (SQLException e) {
            Logger.getLogger(ProfesorGestion.class.getName()).log(Level.SEVERE, null, e);
        }
        return lista;
    }
    
    
    public static Profesor getProfesor(String codigo) {
        Profesor profesor = null;
        try {
            PreparedStatement sentencia = Conexion.getConexion().prepareStatement(SQL_GETPROFESOR);
            sentencia.setString(1, codigo);
            ResultSet datos = sentencia.executeQuery();
            while (datos != null && datos.next()) {
                profesor = new Profesor(
                        datos.getString(1),
                        datos.getString(2),
                        datos.getString(3),
                        datos.getDouble(4),
                        datos.getDate(5),
                        datos.getDate(6),
                        datos.getString(7)
                );
            }
        } catch (SQLException e) {
            Logger.getLogger(ProfesorGestion.class.getName()).log(Level.SEVERE, null, e);
        }
        return profesor;
    }

    public static String generarJson() {
        Profesor profesor = null;
        String tiraJson = "";
        String fecha1 = "";
        String fecha2 = "";
        try {
            PreparedStatement sentencia = Conexion.getConexion()
                    .prepareStatement(SQL_GETPROFESORES);
            ResultSet rs = sentencia.executeQuery();
            while (rs != null && rs.next()) {
                profesor = new Profesor(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getDate(5),
                        rs.getDate(6),
                        rs.getString(7)
                );

                DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                fecha1 = sdf.format(profesor.getFechaCreacion());
                fecha2 = sdf.format(profesor.getFechaNaci());
                JsonObjectBuilder creadorJson = Json.createObjectBuilder();
                JsonObject objectJson = creadorJson.add("codigo", profesor.getCodigo())
                        .add("nombreCompleto", profesor.getNombreCompleto())
                        .add("materia", profesor.getMateria())
                        .add("notaProfesor", profesor.getNotaProfesor())
                        .add("fechaCreacion", fecha1)
                        .add("fechaNaci", fecha2)
                        .add("apellido2", profesor.getGenero())
                        .build();
                StringWriter tira = new StringWriter();
                JsonWriter jsonWriter = Json.createWriter(tira);
                jsonWriter.writeObject(objectJson);
                if (tiraJson == null) {
                    tiraJson = tira.toString() + "\n";
                } else {
                    tiraJson = tiraJson + tira.toString() + "\n";
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(ProfesorGestion.class.getName()).log(Level.SEVERE, null, ex);

        }
        return tiraJson;
    }
}
