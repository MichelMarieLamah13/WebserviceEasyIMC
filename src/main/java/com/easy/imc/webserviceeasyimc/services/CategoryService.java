package com.easy.imc.webserviceeasyimc.services;

import com.easy.imc.webserviceeasyimc.dao.Database;
import com.easy.imc.webserviceeasyimc.dao.Table;
import com.easy.imc.webserviceeasyimc.entities.Category;
import com.easy.imc.webserviceeasyimc.entities.IMCResponse;
import com.easy.imc.webserviceeasyimc.entities.User;
import com.easy.imc.webserviceeasyimc.models.CategoryCountModel;
import com.easy.imc.webserviceeasyimc.models.CategoryModel;
import com.easy.imc.webserviceeasyimc.models.UnitePoidsModel;
import org.springframework.http.HttpStatus;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryService {
    public static IMCResponse<CategoryModel> initCategoriesTable(List<Category> categories){
        IMCResponse<CategoryModel> result = new IMCResponse<>();
        for (Category category:categories) {
            IMCResponse<CategoryModel> ir = create(category);
            if(ir.status == HttpStatus.CREATED.value()){
                result.values.add(category.toModel());
            }
        }
        result.status = HttpStatus.OK.value();
        result.name = HttpStatus.OK.name();
        result.message = categories.size()+" catégories créées avec succès";
        return result;
    }
    public static IMCResponse<CategoryModel> create(Category category){
        String query = "INSERT INTO "+ Table.CATEGORIES.getValue() +" (title, subtitle, min, max, avatar) VALUES (?, ?, ?, ?, ?)";
        IMCResponse<CategoryModel> result = new IMCResponse<>();
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, category.title);
            statement.setString(2, category.subtitle);
            statement.setDouble(3, category.min);
            statement.setDouble(4, category.max);
            statement.setString(5, category.avatar);
            int nbRows = statement.executeUpdate();
            List<CategoryModel> values = new ArrayList<>();
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            if(nbRows > 0){

                result.message = "Catégorie ajoutée avec succès";
                values.add(category.toModel());
            }else{
                result.message = "Catégorie non ajouté avec succès";
            }
            result.values = values;
        }catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());
            throw new RuntimeException("Erreur ajout Category: "+e.getMessage());
        }
        return result;
    }

    public static IMCResponse<CategoryModel> getCategorieByIMC(Double imc){
        String query = "SELECT * FROM "+ Table.CATEGORIES.getValue() +" WHERE ? BETWEEN min AND max";
        IMCResponse<CategoryModel> result = new IMCResponse<>();
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDouble(1, imc);

            ResultSet rs = statement.executeQuery();
            List<CategoryModel> values = new ArrayList<>();
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            if(rs.next()){
                result.message = "Catégorie trouvée avec succès";
                CategoryModel category = getInfo(rs);
                values.add(category);
            }else{
                result.message = "Catégorie non trouvée";
            }
            result.values = values;
        }catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());
            throw new RuntimeException("Erreur recherche Category par valeur imc: "+e.getMessage());
        }
        return result;
    }

    public static IMCResponse<CategoryModel> findById(int id){
        String query = "SELECT * FROM "+ Table.CATEGORIES.getValue() +" WHERE id = ?";
        IMCResponse<CategoryModel> result = new IMCResponse<>();
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDouble(1, id);

            ResultSet rs = statement.executeQuery();
            List<CategoryModel> values = new ArrayList<>();
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            if(rs.next()){
                result.message = "Catégorie trouvée avec succès";
                CategoryModel category = getInfo(rs);
                values.add(category);
            }else{
                result.message = "Catégorie non trouvée";
            }
            result.values = values;
        }catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());
            throw new RuntimeException("Erreur recherche Category par valeur imc: "+e.getMessage());
        }
        return result;
    }

    public static IMCResponse<CategoryModel> findAll(){
        IMCResponse<CategoryModel> result = new IMCResponse<>();
        String query = "SELECT * FROM "+ Table.CATEGORIES.getValue();
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet rs = statement.executeQuery();
            List<CategoryModel> values = new ArrayList<>();
            while(rs.next()){
                CategoryModel model = getInfo(rs);
                values.add(model);
            }
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            result.message = "Toutes les catégories obtenus avec succès";
            result.values = values;
        }catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    public static IMCResponse<CategoryCountModel> findAllCount(User user){
        IMCResponse<CategoryCountModel> result = new IMCResponse<>();
        String query = "SELECT c.id, c.title, c.subtitle, c.min, c.max, c.avatar, COUNT(*) as count " +
                "FROM "+Table.HISTORIES.getValue()+" h " +
                "JOIN "+Table.CATEGORIES.getValue()+" c ON h.idCategory = c.id " +
                "WHERE h.idUser = ?" +
                " GROUP BY h.idCategory";

        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user.id);
            ResultSet rs = statement.executeQuery();
            List<CategoryCountModel> values = new ArrayList<>();
            while(rs.next()){
                CategoryCountModel model = getInfo(rs).toCountModel();
                model.count = rs.getInt("count");
                values.add(model);
            }
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            result.message = "Tous les histories obtenus avec succès";
            result.values = values;
        }catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }




    public static CategoryModel getInfo(ResultSet rs) throws SQLException {
        CategoryModel category = new CategoryModel();
        category.avatar = rs.getString("avatar");
        category.id = rs.getInt("id");
        category.max = rs.getDouble("max");
        category.min = rs.getDouble("min");
        category.title = rs.getString("title");
        category.subtitle = rs.getString("subtitle");
        return category;
    }
}
