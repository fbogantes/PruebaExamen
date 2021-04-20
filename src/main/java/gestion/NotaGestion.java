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
import model.Nota;

/**
 *
 * @author Frey
 */
public class NotaGestion {
    
    private static final String SQL_GETNOTAS = "SELECT * FROM notas";
    
    public static String generarJson() {
        Nota nota = null;
        String tiraJson = "";
        try {
            PreparedStatement sentencia = Conexion.getConexion()
                    .prepareStatement(SQL_GETNOTAS);
            ResultSet rs = sentencia.executeQuery();
            while (rs != null && rs.next()) {
                nota = new Nota(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDouble(5)
                );

                JsonObjectBuilder creadorJson = Json.createObjectBuilder();
                JsonObject objectJson = creadorJson.add("id", nota.getId())
                        .add("idEstudiante", nota.getIdEstudiante())
                        .add("idMateria", nota.getIdMateria())
                        .add("idPeriodo", nota.getIdMateria())
                        .add("nota", nota.getNota())
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
            Logger.getLogger(NotaGestion.class.getName()).log(Level.SEVERE, null, ex);

        }
        return tiraJson;
    }
}
