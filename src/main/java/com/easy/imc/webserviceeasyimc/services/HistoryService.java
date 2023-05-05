package com.easy.imc.webserviceeasyimc.services;

import com.easy.imc.webserviceeasyimc.dao.Database;
import com.easy.imc.webserviceeasyimc.dao.Table;
import com.easy.imc.webserviceeasyimc.entities.*;
import com.easy.imc.webserviceeasyimc.models.*;
import org.springframework.http.HttpStatus;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HistoryService {
    public static IMCResponse<HistoryModel> initHistoriesTable(List<History> histories){
        IMCResponse<HistoryModel> result = new IMCResponse<>();
        for (History history:histories) {
            HistoryModel model = history.toHistoryModel();
            IMCResponse ir = create(model);
            if(ir.status == HttpStatus.OK.value()){
                result.values.add(model);
            }
        }
        result.status = HttpStatus.OK.value();
        result.name = HttpStatus.OK.name();
        result.message = histories.size()+" histories créées avec succès";
        return result;
    }
    public static IMCResponse<HistoryModel> create(HistoryModel history){
        IMCResponse<HistoryModel> result = new IMCResponse<>();
        List<HistoryModel> values = new ArrayList<>();
        if(history.save) {
            IMCResponse<UserModel> ur = UserService.login(history.user.toEntity());
            if(ur.status == HttpStatus.OK.value()){
                history.user = ur.values.get(0);
                String query = "INSERT INTO "+ Table.HISTORIES.getValue() +" (idUser, poids, taille, imc, date, heure, idCategory, idUnitePoids,idUniteTaille) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (Connection connection = Database.connect()) {
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, history.user.id);
                    statement.setDouble(2, history.poids);
                    statement.setDouble(3, history.taille);
                    statement.setDouble(4, history.imc);
                    statement.setString(5, history.date);
                    statement.setString(6, history.heure);
                    statement.setInt(7, history.category.id);
                    statement.setInt(8, history.unitePoids.id);
                    statement.setInt(9, history.uniteTaille.id);

                    int nbRows = statement.executeUpdate();
                    if(nbRows > 0){
                        result.status = HttpStatus.OK.value();
                        result.name = HttpStatus.OK.name();
                        result.message = "History ajoutée avec succès";
                        values.add(history);
                    }else{
                        result.status = HttpStatus.BAD_REQUEST.value();
                        result.name = HttpStatus.BAD_REQUEST.name();
                        result.message = "History non ajoutée avec succès";
                    }
                    result.values = values;
                }catch (SQLException e) {
                    Logger.getAnonymousLogger().log(
                            Level.SEVERE,
                            LocalDateTime.now() + ":" +e.getMessage());
                    throw new RuntimeException("Erreur ajout History: "+e.getMessage());
                }
            }else{
                result.status = HttpStatus.OK.value();
                result.name = HttpStatus.OK.name();
                result.message = "History ajoutée avec succès";
                values.add(history);
                result.values = values;
            }
        }else{
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            result.message = "History ajoutée avec succès";
            values.add(history);
            result.values = values;
        }


        return result;
    }

    public static IMCResponse<HistoryModel> create(IMC imc){
        HistoryModel history = new HistoryModel();
        history.imc = imc.value;
        history.poids = imc.poids;
        history.taille = imc.taille;
        String[] dt = Helper.getCurrentDateTime();
        history.date = dt[0];
        history.heure = dt[1];
        history.save = imc.forMe;
        if(imc.user != null){
            history.user = imc.user.toUserModel();
        }

        history.uniteTaille = InitDB.defaultUniteTaille.toModel();
        if(imc.uniteTaille != null){
            history.uniteTaille = imc.uniteTaille.toModel();
        }

        history.unitePoids = InitDB.defaultUnitePoids.toModel();
        if(imc.unitePoids != null){
            history.unitePoids = imc.unitePoids.toModel();
        }
        IMCResponse<CategoryModel> res = CategoryService.getCategorieByIMC(history.imc);
        if(res.status == HttpStatus.OK.value()){
            history.category = res.values.get(0);
        }
        return create(history);
    }

    public static IMCResponse<HistoryModel> findAll(User user, boolean recentlyAdded, int limit){
        IMCResponse<HistoryModel> result = new IMCResponse<>();
        String query = "SELECT * FROM "+ Table.HISTORIES.getValue()+" WHERE idUser = ?";
        if(recentlyAdded){
            query += " ORDER BY id DESC LIMIT "+limit;
        } else if (limit != -1) {
            query += " ORDER BY id DESC";
        }
        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user.id);
            ResultSet rs = statement.executeQuery();
            List<HistoryModel> values = new ArrayList<>();
            while(rs.next()){
                HistoryModel model = getInfo(rs);
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


    public static IMCResponse<HistoryModel> getHistoryByMultiCriteria(History history){

        String query = "SELECT * FROM "+Table.HISTORIES.getValue()+" WHERE idUser = "+history.idUser;
        IMCResponse<HistoryModel> result = new IMCResponse<>();
        List<HistoryModel> values = new ArrayList<>();
        try (Connection connection = Database.connect()) {
            String poidsPart = "";
            String taillePart = "";
            String imcPart = "";
            String heurePart = "";
            String datePart = "";
            String categoryPart ="";

            List<String> queryPart = new ArrayList<>();

            try {
                int x =(int) history.poids;
                if(x!=0){
                    List<String> xPart = new ArrayList<>();

                    poidsPart = " ( poids BETWEEN "+x+" AND "+(x+1)+" )";
                    xPart.add(poidsPart);

                    taillePart = " ( taille BETWEEN "+x+" AND "+(x+1)+" )";
                    xPart.add(taillePart);

                    imcPart = " ( imc BETWEEN "+x+" AND "+(x+1)+" )";
                    xPart.add(imcPart);

                    queryPart.add("( "+String.join(" OR ", xPart)+" )");
                }
            } catch (NumberFormatException e) {
                Logger.getAnonymousLogger().log(
                        Level.INFO,
                        LocalDateTime.now() + " : "+e.getMessage());
            }

            if(history.idCategory!=0){
                categoryPart = " idCategory = "+history.idCategory;
            }
            if(!categoryPart.isEmpty()){
                queryPart.add(categoryPart);
            }

            if(history.date!=null && !history.date.equals("null") && !history.date.isEmpty()){
                datePart = "date = '"+history.date+"'";
            }

            if(!datePart.isEmpty()){
                queryPart.add(datePart);
            }

            if(history.heure!=null && !history.heure.equals("null") && !history.heure.isEmpty()){
                heurePart = " heure LIKE '%"+history.heure+"%'";
            }

            if(!heurePart.isEmpty()){
                queryPart.add(heurePart);
            }

            if(!queryPart.isEmpty()){
                query += " AND ( "+String.join(" AND ", queryPart) + " ) ORDER BY id DESC";
            }else{
                query += " ORDER BY id DESC";
            }

            Logger.getAnonymousLogger().log(
                    Level.INFO,
                    LocalDateTime.now() + " : "+query);
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                HistoryModel model = getInfo(rs);
                values.add(model);
            }
            result.status = HttpStatus.OK.value();
            result.name = HttpStatus.OK.name();
            result.message = "Liste des histories obtenus avec succès";
            result.values = values;
        }catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + " : "+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }


    public static HistoryModel getInfo(ResultSet rs) throws SQLException {
        HistoryModel model = new HistoryModel();
        model.id = rs.getInt("id");
        model.poids = rs.getDouble("poids");
        model.taille = rs.getDouble("taille");
        model.date = rs.getString("date");
        model.heure = rs.getString("heure");
        model.imc = rs.getDouble("imc");

        int idUser = rs.getInt("idUser");
        IMCResponse<UserModel> userReq = UserService.findById(idUser);
        if(userReq.status == HttpStatus.OK.value()){
            model.user = userReq.values.get(0);
        }

        int idCategory = rs.getInt("idCategory");
        IMCResponse<CategoryModel> catReq = CategoryService.findById(idCategory);
        if(catReq.status == HttpStatus.OK.value()){
            model.category = catReq.values.get(0);
        }

        int idUnitePoids = rs.getInt("idUnitePoids");
        IMCResponse<UnitePoidsModel> upReq = UnitePoidsService.findById(idUnitePoids);
        if(upReq.status == HttpStatus.OK.value()){
            model.unitePoids = upReq.values.get(0);
        }

        int idUniteTaille = rs.getInt("idUniteTaille");
        IMCResponse<UniteTailleModel> utReq = UniteTailleService.findById(idUniteTaille);
        if(utReq.status == HttpStatus.OK.value()){
            model.uniteTaille = utReq.values.get(0);
        }
        return model;
    }
}
