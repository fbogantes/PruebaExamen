/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion;


import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import model.Conexion;
import model.Materia;


/**
 *
 * @author Frey
 */
public class MateriaGestion {
    
    private static final String SQL_GETMATERIAS = "SELECT * FROM materia";

    public static String generarJson() {
        Materia materia = null;
        String tiraJson = "";
        try {
            PreparedStatement sentencia = Conexion.getConexion()
                    .prepareStatement(SQL_GETMATERIAS);
            ResultSet rs = sentencia.executeQuery();
            while (rs != null && rs.next()) {
                materia = new Materia(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3)
                );

                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                JsonObjectBuilder creadorJson = Json.createObjectBuilder();
                JsonObject objectJson = creadorJson.add("id", materia.getId())
                        .add("idMateria", materia.getIdMateria())
                        .add("nombre", materia.getNombre())
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
            Logger.getLogger(MateriaGestion.class.getName()).log(Level.SEVERE, null, ex);

        }
        return tiraJson;
    }
}
