package com.easy.imc.webserviceeasyimc.services;

import com.easy.imc.webserviceeasyimc.dao.Database;
import com.easy.imc.webserviceeasyimc.dao.Table;
import com.easy.imc.webserviceeasyimc.models.IMCResponse;
import com.easy.imc.webserviceeasyimc.models.User;
import com.github.javafaker.Faker;
import org.springframework.http.HttpStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService {

    public static IMCResponse initUsersTable(List<User> users){
        IMCResponse result = new IMCResponse();
        for (User user:users) {
            IMCResponse ir = create(user);
            if(ir.status == HttpStatus.CREATED.value()){
                result.values.add(user);
            }
        }
        result.status = HttpStatus.CREATED.value();
        result.name = HttpStatus.CREATED.name();
        result.message = users.size()+" utilisateurs crées avec succès";
        return result;
    }

    public static IMCResponse create(User user){
        String query = "INSERT INTO "+ Table.USERS.getValue() +" (login, password, avatar) VALUES (?, ?, ?)";
        IMCResponse result = new IMCResponse();
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.login);
            statement.setString(2, user.password);
            statement.setString(3, user.avatar);

            int nbRows = statement.executeUpdate();
            List<Object> values = new ArrayList<>();
            if(nbRows > 0){
                result.status = HttpStatus.CREATED.value();
                result.name = HttpStatus.CREATED.name();
                result.message = "Utilisateur ajouté avec succès";
                values.add(nbRows);
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
        }
        return result;
    }


}
