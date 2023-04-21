package com.easy.imc.webserviceeasyimc.services;

import com.easy.imc.webserviceeasyimc.dao.Database;
import com.easy.imc.webserviceeasyimc.dao.Table;
import com.easy.imc.webserviceeasyimc.models.Conseil;
import com.easy.imc.webserviceeasyimc.models.Description;
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

public class DescriptionService {
    public static IMCResponse initDescriptionsTable(List<Description> descriptions){
        IMCResponse result = new IMCResponse();
        for (Description description:descriptions) {
            IMCResponse ir = create(description);
            if(ir.status == HttpStatus.CREATED.value()){
                result.values.add(description);
            }
        }
        result.status = HttpStatus.CREATED.value();
        result.name = HttpStatus.CREATED.name();
        result.message = descriptions.size()+" descriptions créées avec succès";
        return result;
    }
    public static IMCResponse create(Description description){
        String query = "INSERT INTO "+ Table.DESCRIPTIONS.getValue() +" (idCategorie, description) VALUES (?, ?)";
        IMCResponse result = new IMCResponse();
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, description.idCategory);
            statement.setString(2, description.description);

            int nbRows = statement.executeUpdate();
            List<Object> values = new ArrayList<>();
            if(nbRows > 0){
                result.status = HttpStatus.CREATED.value();
                result.name = HttpStatus.CREATED.name();
                result.message = "Description ajoutée avec succès";
                values.add(nbRows);
            }else{
                result.status = HttpStatus.BAD_REQUEST.value();
                result.name = HttpStatus.BAD_REQUEST.name();
                result.message = "Conseil non ajoutée avec succès";
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
