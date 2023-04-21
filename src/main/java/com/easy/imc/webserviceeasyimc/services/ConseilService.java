package com.easy.imc.webserviceeasyimc.services;

import com.easy.imc.webserviceeasyimc.dao.Database;
import com.easy.imc.webserviceeasyimc.dao.Table;
import com.easy.imc.webserviceeasyimc.models.Category;
import com.easy.imc.webserviceeasyimc.models.Conseil;
import com.easy.imc.webserviceeasyimc.models.IMCResponse;
import org.springframework.http.HttpStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConseilService {
    public static IMCResponse initConseilsTable(List<Conseil> conseils){
        IMCResponse result = new IMCResponse();
        for (Conseil conseil:conseils) {
            IMCResponse ir = create(conseil);
            if(ir.status == HttpStatus.CREATED.value()){
                result.values.add(conseil);
            }
        }
        result.status = HttpStatus.CREATED.value();
        result.name = HttpStatus.CREATED.name();
        result.message = conseils.size()+" conseils créés avec succès";
        return result;
    }
    public static IMCResponse create(Conseil conseil){
        String query = "INSERT INTO "+ Table.CONSEILS.getValue() +" (idCategorie, conseil) VALUES (?, ?)";
        IMCResponse result = new IMCResponse();
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, conseil.idCategory);
            statement.setString(2, conseil.conseil);

            int nbRows = statement.executeUpdate();
            List<Object> values = new ArrayList<>();
            if(nbRows > 0){
                result.status = HttpStatus.CREATED.value();
                result.name = HttpStatus.CREATED.name();
                result.message = "Conseil ajouté avec succès";
                values.add(nbRows);
            }else{
                result.status = HttpStatus.BAD_REQUEST.value();
                result.name = HttpStatus.BAD_REQUEST.name();
                result.message = "Conseil non ajouté avec succès";
            }
            result.values = values;
        }catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());
        }
        return result;
    }
}
