package com.easy.imc.webserviceeasyimc.services;

import com.easy.imc.webserviceeasyimc.dao.Database;
import com.easy.imc.webserviceeasyimc.dao.Table;
import com.easy.imc.webserviceeasyimc.entities.Description;
import com.easy.imc.webserviceeasyimc.entities.IMCResponse;
import com.easy.imc.webserviceeasyimc.models.CategoryModel;
import com.easy.imc.webserviceeasyimc.models.DescriptionModel;
import com.easy.imc.webserviceeasyimc.models.UserModel;
import org.springframework.http.HttpStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DescriptionService {
    public static IMCResponse<DescriptionModel> initDescriptionsTable(List<Description> descriptions){
        IMCResponse<DescriptionModel> result = new IMCResponse<>();
        for (Description description:descriptions) {
            IMCResponse<DescriptionModel> ir = create(description);
            if(ir.status == HttpStatus.OK.value()){
                result.values.add(description.toModel());
            }
        }
        result.status = HttpStatus.OK.value();
        result.name = HttpStatus.OK.name();
        result.message = descriptions.size()+" descriptions créées avec succès";
        return result;
    }
    public static IMCResponse<DescriptionModel> create(Description description){
        String query = "INSERT INTO "+ Table.DESCRIPTIONS.getValue() +" (idCategorie, description) VALUES (?, ?)";
        IMCResponse<DescriptionModel> result = new IMCResponse<>();
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, description.idCategory);
            statement.setString(2, description.description);

            int nbRows = statement.executeUpdate();
            List<DescriptionModel> values = new ArrayList<>();
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            if(nbRows > 0){
                result.message = "Description ajoutée avec succès";
                values.add(description.toModel());
            }else{
                result.message = "Conseil non ajoutée avec succès";
            }
            result.values = values;
        }catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());

            throw new RuntimeException("Erreur ajout Description: "+e.getMessage());
        }
        return result;
    }

    public static IMCResponse<DescriptionModel> findByIdCategory(int id){
        IMCResponse<DescriptionModel> result = new IMCResponse<>();
        String query = "SELECT * FROM "+ Table.DESCRIPTIONS.getValue() +" WHERE idCategorie = ?";
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            List<DescriptionModel> values = new ArrayList<>();
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            if(rs.next()){
                result.message = "Description trouvé avec succès";
                DescriptionModel model = getInfo(rs);
                values.add(model);
            }else{
                result.message = "Pas de description pour cette catégorie";
            }
            result.values = values;
        }catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());

            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    public static DescriptionModel getInfo(ResultSet rs) throws SQLException {
        DescriptionModel model = new DescriptionModel();
        model.id = rs.getInt("id");
        int idCategorie = rs.getInt("idCategorie");
        IMCResponse<CategoryModel> catReq = CategoryService.findById(idCategorie);
        if(catReq.status == HttpStatus.OK.value()){
            model.category = catReq.values.get(0);
        }
        model.description = rs.getString("description");
        return model;
    }
}
