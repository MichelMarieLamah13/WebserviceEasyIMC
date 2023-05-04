package com.easy.imc.webserviceeasyimc.services;

import com.easy.imc.webserviceeasyimc.dao.Database;
import com.easy.imc.webserviceeasyimc.dao.Table;
import com.easy.imc.webserviceeasyimc.entities.IMCResponse;
import com.easy.imc.webserviceeasyimc.entities.User;
import com.easy.imc.webserviceeasyimc.entities.UserRole;
import com.easy.imc.webserviceeasyimc.models.UserModel;
import com.github.javafaker.Faker;
import org.springframework.cglib.proxy.Factory;
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

public class UserService {

    public static IMCResponse<UserModel> initUsersTable(List<User> users){
        IMCResponse<UserModel> result = new IMCResponse<>();
        for (User user:users) {
            IMCResponse<UserModel> ir = create(user);
            if(ir.status == HttpStatus.OK.value()){
                result.values.add(user.toUserModel());
            }
        }
        result.status = HttpStatus.OK.value();
        result.name = HttpStatus.OK.name();
        result.message = users.size()+" utilisateurs crées avec succès";
        return result;
    }

    public static IMCResponse<UserModel> create(User user){
        Faker faker = new Faker();
        user.avatar = "user0"+faker.random().nextInt(1, 5)+".png";
        user.role = faker.random().nextInt(1, UserRole.values().length);
        String query = "INSERT INTO "+ Table.USERS.getValue() +" (login, password, avatar, role) VALUES (?, ?, ?, ?)";
        IMCResponse<UserModel> result = new IMCResponse<>();
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.login);
            statement.setString(2, user.password);
            statement.setString(3, user.avatar);
            statement.setInt(4, user.role);

            int nbRows = statement.executeUpdate();
            List<UserModel> values = new ArrayList<>();
            if(nbRows > 0){
                result.status = HttpStatus.OK.value();
                result.name = HttpStatus.OK.name();
                result.message = "Utilisateur ajouté avec succès";
                values.add(user.toUserModel());
            }else{
                result.status = HttpStatus.BAD_REQUEST.value();
                result.name = HttpStatus.BAD_REQUEST.name();
                result.message = "Utilisateur non ajouté avec succès";
            }
            result.values = values;
        }catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());
            throw new RuntimeException("Erreur ajout Utilisateur: "+e.getMessage());
        }
        return result;
    }


    public static IMCResponse<UserModel> login(User user){
        IMCResponse<UserModel> result = new IMCResponse<>();
        String query = "SELECT * FROM "+ Table.USERS.getValue() +" WHERE login = ? AND password = ?";
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.login);
            statement.setString(2, user.password);

            ResultSet rs = statement.executeQuery();
            List<UserModel> values = new ArrayList<>();
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            if(rs.next()){
                result.message = "Utilisateur trouvé avec succès";
                UserModel u = getInfo(rs);
                values.add(u);
            }else{
                result.message = "Pas d'utilisateur avec ces identifiants";
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


    public static IMCResponse<UserModel> findById(int id){
        IMCResponse<UserModel> result = new IMCResponse<>();
        String query = "SELECT * FROM "+ Table.USERS.getValue() +" WHERE id = ?";
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            List<UserModel> values = new ArrayList<>();
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            if(rs.next()){
                result.message = "Utilisateur trouvé avec succès";
                UserModel user = getInfo(rs);
                values.add(user);
            }else{
                result.message = "Pas d'utilisateur avec ces identifiants";
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


    public static IMCResponse<UserModel> delete(int id){
        IMCResponse<UserModel> result = new IMCResponse<>();
        String query = "DELETE FROM "+ Table.USERS.getValue() +" WHERE id = ?";
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            int nbRows = statement.executeUpdate();
            List<UserModel> values = new ArrayList<>();
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            if(nbRows > 0){
                result.message = "Utilisateur supprimé avec succès";
                UserModel user = new UserModel();
                user.id = id;
                values.add(user);
            }else{
                result.message = "Pas d'utilisateur avec ces identifiants";
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


    public static IMCResponse<UserModel> update(User user){
        String query = "UPDATE "+ Table.USERS.getValue() +" SET login = ?, password = ?, role = ? WHERE id = ?";
        IMCResponse<UserModel> result = new IMCResponse<>();
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.login);
            statement.setString(2, user.password);
            statement.setInt(3, user.role);
            statement.setInt(4, user.id);

            int nbRows = statement.executeUpdate();
            List<UserModel> values = new ArrayList<>();
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            if(nbRows>0){
                result.message = "Utilisateur mise à jour avec succès";
                values.add(user.toUserModel());
            }else{
                result.message = "Utilisateur non mise à jour";
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

    public static IMCResponse<UserModel> findAll(){
        IMCResponse<UserModel> result = new IMCResponse<>();
        String query = "SELECT * FROM "+ Table.USERS.getValue();
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet rs = statement.executeQuery();
            List<UserModel> values = new ArrayList<>();
            while(rs.next()){
                UserModel user = getInfo(rs);
                values.add(user);
            }
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            result.message = "Tous les utilisateurs obtenus avec succès";
            result.values = values;
        }catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ":" +e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    public static UserModel getInfo(ResultSet rs) throws SQLException {
        UserModel user = new UserModel();
        user.role = rs.getInt("role");
        user.avatar = rs.getString("avatar");
        user.login = rs.getString("login");
        user.password = rs.getString("password");
        user.id = rs.getInt("id");
        return user;
    }


}
