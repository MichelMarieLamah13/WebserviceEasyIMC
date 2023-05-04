package com.easy.imc.webserviceeasyimc.services;

import com.easy.imc.webserviceeasyimc.dao.Database;
import com.easy.imc.webserviceeasyimc.dao.Table;
import com.easy.imc.webserviceeasyimc.entities.Conseil;
import com.easy.imc.webserviceeasyimc.entities.IMCResponse;
import com.easy.imc.webserviceeasyimc.models.CategoryModel;
import com.easy.imc.webserviceeasyimc.models.ConseilModel;
import com.easy.imc.webserviceeasyimc.models.DescriptionModel;
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

public class ConseilService {
    public static IMCResponse<ConseilModel> initConseilsTable(List<Conseil> conseils){
        IMCResponse<ConseilModel> result = new IMCResponse<>();
        for (Conseil conseil:conseils) {
            IMCResponse<ConseilModel> ir = create(conseil);
            if(ir.status == HttpStatus.OK.value()){
                result.values.add(conseil.toModel());
            }
        }
        result.status = HttpStatus.OK.value();
        result.name = HttpStatus.OK.name();
        result.message = conseils.size()+" conseils créés avec succès";
        return result;
    }
    public static IMCResponse<ConseilModel> create(Conseil conseil){
        String query = "INSERT INTO "+ Table.CONSEILS.getValue() +" (idCategorie, conseil) VALUES (?, ?)";
        IMCResponse<ConseilModel> result = new IMCResponse<>();
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, conseil.idCategory);
            statement.setString(2, conseil.conseil);

            int nbRows = statement.executeUpdate();
            List<ConseilModel> values = new ArrayList<>();
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            if(nbRows > 0){
                result.message = "Conseil ajouté avec succès";
                values.add(conseil.toModel());
            }else{
                result.message = "Conseil non ajouté avec succès";
            }
            result.values = values;
        }catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());
            throw new RuntimeException("Erreur ajout Conseil: "+e.getMessage());
        }
        return result;
    }

    public static IMCResponse<ConseilModel> findByIdCategory(int id){
        IMCResponse<ConseilModel> result = new IMCResponse<>();
        String query = "SELECT * FROM "+ Table.CONSEILS.getValue() +" WHERE idCategorie = ?";
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            List<ConseilModel> values = new ArrayList<>();
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            if(rs.next()){
                result.message = "Conseil trouvé avec succès";
                ConseilModel model = getInfo(rs);
                values.add(model);
            }else{
                result.message = "Pas de conseil pour cette catégorie";
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

    public static ConseilModel getInfo(ResultSet rs) throws SQLException {
        ConseilModel model = new ConseilModel();
        model.id = rs.getInt("id");
        int idCategorie = rs.getInt("idCategorie");
        IMCResponse<CategoryModel> catReq = CategoryService.findById(idCategorie);
        if(catReq.status == HttpStatus.OK.value()){
            model.category = catReq.values.get(0);
        }
        model.conseil = rs.getString("conseil");
        return model;
    }
}
