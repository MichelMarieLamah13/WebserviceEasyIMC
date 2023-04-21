package com.easy.imc.webserviceeasyimc.services;

import com.easy.imc.webserviceeasyimc.dao.Database;
import com.easy.imc.webserviceeasyimc.dao.Table;
import com.easy.imc.webserviceeasyimc.models.Category;
import com.easy.imc.webserviceeasyimc.models.IMCResponse;
import com.easy.imc.webserviceeasyimc.models.User;
import org.springframework.http.HttpStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryService {
    public static IMCResponse initCategoriesTable(List<Category> categories){
        IMCResponse result = new IMCResponse();
        for (Category category:categories) {
            IMCResponse ir = create(category);
            if(ir.status == HttpStatus.CREATED.value()){
                result.values.add(category);
            }
        }
        result.status = HttpStatus.CREATED.value();
        result.name = HttpStatus.CREATED.name();
        result.message = categories.size()+" catégories créées avec succès";
        return result;
    }
    public static IMCResponse create(Category category){
        String query = "INSERT INTO "+ Table.CATEGORIES +" (title, subtitle, min, max, avatar) VALUES (?, ?, ?, ?, ?)";
        IMCResponse result = new IMCResponse();
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, category.title);
            statement.setString(2, category.subtitle);
            statement.setDouble(3, category.min);
            statement.setDouble(4, category.max);
            statement.setString(5, category.avatar);


            int nbRows = statement.executeUpdate();
            List<Object> values = new ArrayList<>();
            if(nbRows > 0){
                result.status = HttpStatus.CREATED.value();
                result.name = HttpStatus.CREATED.name();
                result.message = "Catégorie ajoutée avec succès";
                values.add(nbRows);
            }else{
                result.status = HttpStatus.BAD_REQUEST.value();
                result.name = HttpStatus.BAD_REQUEST.name();
                result.message = "Catégorie non ajouté avec succès";
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
