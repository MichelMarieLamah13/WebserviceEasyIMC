package com.easy.imc.webserviceeasyimc.entities;

import com.easy.imc.webserviceeasyimc.models.CategoryModel;
import com.easy.imc.webserviceeasyimc.models.HistoryModel;
import com.easy.imc.webserviceeasyimc.models.UnitePoidsModel;
import com.easy.imc.webserviceeasyimc.models.UniteTailleModel;
import com.easy.imc.webserviceeasyimc.services.CategoryService;
import com.easy.imc.webserviceeasyimc.services.IMCService;
import com.easy.imc.webserviceeasyimc.services.UnitePoidsService;
import com.easy.imc.webserviceeasyimc.services.UniteTailleService;
import com.github.javafaker.Faker;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class InitDB {
    public  List<User> users;
    public  List<Category> categories;
    public  List<Conseil>  conseils;
    public  List<Description> descriptions;
    public  List<History> histories;
    public  List<UnitePoids> unitePoids;
    public  List<UniteTaille> uniteTailles;

    public static int idPoidsInsuffisant = 1;
    public static int idPoidsNormal = 2;
    public static int idSurpoids = 3;
    public static int idObesiteI = 4;
    public static int idObesiteII = 5;
    public static int idObesiteIII = 6;

    public static UniteTaille defaultUniteTaille = new UniteTaille(1,"m", 1);
    public static UnitePoids defaultUnitePoids = new UnitePoids(1, "kg", 1);
    private Faker faker = new Faker();

    public InitDB() {
        createUsers();
        createCategories();
        createConseils();
        createDescriptions();
        createUnitePoids();
        createUniteTaille();
        createHistories();
    }

    private void createUsers(){
        users = new ArrayList<>();
        int nbUsers = faker.random().nextInt(10, 20);
        for (int i = 0; i < nbUsers; i++) {
            User user = new User();
            user.id = i + 1;
            user.login = faker.name().firstName();
            user.password = "123";
            user.avatar = "user0"+faker.random().nextInt(1, 5)+".png";
            user.role = faker.random().nextInt(1, UserRole.values().length);
            users.add(user);
        }
    }

    private void createCategories(){
        categories =  new ArrayList<>();

        Category poidsInsuffisant = new Category();
        poidsInsuffisant.id = idPoidsInsuffisant;
        poidsInsuffisant.title = "Poids Insuffisant";
        poidsInsuffisant.subtitle = "Maigreur";
        poidsInsuffisant.min = 0;
        poidsInsuffisant.max = 18.59;
        poidsInsuffisant.avatar = "categorie0"+idPoidsInsuffisant+".png";
        categories.add(poidsInsuffisant);


        Category poidsNormal = new Category();
        poidsNormal.id = idPoidsNormal;
        poidsNormal.title = "Poids Normal";
        poidsNormal.subtitle = "Corpulence Normale";
        poidsNormal.min = 18.60;
        poidsNormal.max = 24.99;
        poidsNormal.avatar = "categorie0"+idPoidsNormal+".png";
        categories.add(poidsNormal);

        Category surpoids = new Category();
        surpoids.id = idSurpoids;
        surpoids.title = "Surpoids";
        surpoids.subtitle = "Surpoids";
        surpoids.min = 25;
        surpoids.max = 29.99;
        surpoids.avatar = "categorie0"+idSurpoids+".png";
        categories.add(surpoids);

        Category obesiteI = new Category();
        obesiteI.id = idObesiteI;
        obesiteI.title = "Obésite I";
        obesiteI.subtitle = "Obsésité Modérée";
        obesiteI.min = 30;
        obesiteI.max = 34.99;
        obesiteI.avatar = "categorie0"+idObesiteI+".png";
        categories.add(obesiteI);

        Category obesiteII = new Category();
        obesiteII.id = idObesiteII;
        obesiteII.title = "Obésite II";
        obesiteII.subtitle = "Obsésité Sévère";
        obesiteII.min = 35;
        obesiteII.max = 39.99;
        obesiteII.avatar = "categorie0"+idObesiteII+".png";
        categories.add(obesiteII);

        Category obesiteIII = new Category();
        obesiteIII.id = idObesiteIII;
        obesiteIII.title = "Obésite III";
        obesiteIII.subtitle = "Obsésité Morbide";
        obesiteIII.min = 40;
        obesiteIII.max = Double.POSITIVE_INFINITY;
        obesiteIII.avatar = "categorie0"+idObesiteIII+".png";
        categories.add(obesiteIII);

    }

    private void addToConseil(int idCategory, String conseil){
        Conseil c = new Conseil();
        c.idCategory = idCategory;
        c.conseil = conseil;
        conseils.add(c);
    }
    private void createConseils(){
        conseils = new ArrayList<>();

        String cPoidsInsuffisant = "Consultez un professionnel de la santé pour évaluer la cause de votre poids insuffisant et élaborer un plan de gestion approprié." +
                "Adoptez une alimentation équilibrée et variée pour fournir à votre corps les nutriments dont il a besoin." +
                "Faites de l'exercice régulièrement pour renforcer vos muscles, améliorer votre endurance et maintenir un bon état de santé général.";

        addToConseil(idPoidsInsuffisant, cPoidsInsuffisant);


        String cPoidsNormal = "Continuez à maintenir un mode de vie sain en faisant de l'exercice régulièrement et en mangeant une alimentation équilibrée." +
                "Veillez à conserver un poids stable et évitez les fluctuations excessives de poids." +
                "Restez attentif à votre santé globale en consultant régulièrement un professionnel de la santé.";
        addToConseil(idPoidsNormal, cPoidsNormal);

        String cSurpoids = "Essayez de perdre du poids progressivement en adoptant un régime alimentaire équilibré et en augmentant votre niveau d'activité physique." +
                "Évitez les régimes restrictifs ou les méthodes de perte de poids rapides qui peuvent être nuisibles pour la santé." +
                "Consultez un professionnel de la santé pour obtenir des conseils personnalisés sur la gestion du poids et la prévention des maladies liées au surpoids.";

        addToConseil(idSurpoids, cSurpoids);

        String cObesiteI = "Consultez un professionnel de la santé pour élaborer un plan de perte de poids sûr et efficace, qui inclut une alimentation équilibrée, de l'exercice régulier et d'autres approches appropriées." +
                "Évitez les régimes yo-yo ou les méthodes de perte de poids drastiques qui peuvent être préjudiciables à long terme." +
                "Suivez les conseils de votre professionnel de la santé pour améliorer votre condition physique et gérer les risques de santé associés à l'obésité.";

        addToConseil(idObesiteI, cObesiteI);

        String cObesiteII = "Consultez un professionnel de la santé pour obtenir un soutien médical approprié et élaborer un plan de gestion de l'obésité." +
                "Adoptez une alimentation équilibrée, augmentez votre niveau d'activité physique et envisagez d'autres approches, telles que la thérapie comportementale ou la prise en charge médicale de l'obésité." +
                "Soyez vigilant quant à la prévention et à la gestion des maladies liées à l'obésité, et suivez les recommandations de votre professionnel de la santé.";

        addToConseil(idObesiteII, cObesiteII);

        String cObesiteIII = "Consultez immédiatement un professionnel de la santé pour obtenir une prise en charge médicale intensive de l'obésité." +
                "Suivez un plan de gestion de l'obésité élaboré en collaboration avec votre professionnel de la santé, qui peut inclure des approches médicales, nutritionnelles, comportementales et autres." +
                "Soyez diligent dans la prévention et la gestion des complications de santé graves associées à l'obés";
        addToConseil(idObesiteIII, cObesiteIII);

    }

    private void addToDescription(int idCategory, String desc){
        Description d = new Description();
        d.idCategory = idCategory;
        d.description = desc;
        descriptions.add(d);
    }
    private void createDescriptions(){
        descriptions = new ArrayList<>();

        String dPoidsInsuffisant = "Cette catégorie correspond à un poids insuffisant. " +
                "Les personnes ayant un IMC inférieur à 18,5 peuvent être à risque de malnutrition, " +
                "d'ostéoporose, de problèmes de fertilité et d'autres problèmes de santé.";
        addToDescription(idPoidsInsuffisant, dPoidsInsuffisant);

        String dPoidsNormal = "Cette catégorie correspond à un poids normal et est considérée comme " +
                "étant saine pour la plupart des adultes. Les personnes ayant un IMC dans cette plage " +
                "sont moins susceptibles de développer des problèmes de santé liés au poids.";
        addToDescription(idPoidsNormal, dPoidsNormal);

        String dSurpoids = "Cette catégorie correspond à un surpoids. Les personnes ayant un IMC dans " +
                "cette plage peuvent être à risque accru de développer des maladies chroniques telles " +
                "que le diabète, les maladies cardiovasculaires et l'hypertension artérielle.";
        addToDescription(idSurpoids, dSurpoids);

        String dObesiteI = "Cette catégorie correspond à l'obésité de classe I. Les personnes ayant un " +
                "IMC dans cette plage sont à risque accru de développer des problèmes de santé liés au " +
                "poids.";
        addToDescription(idObesiteI, dObesiteI);

        String dObesiteII = "Cette catégorie correspond à l'obésité de classe II. Les personnes ayant " +
                "un IMC dans cette plage sont à risque très élevé de développer des problèmes de santé " +
                "liés au poids, y compris le diabète de type 2, les maladies cardiaques et les accidents " +
                "vasculaires cérébraux.";
        addToDescription(idObesiteII, dObesiteII);

        String dObesiteIII = "Cette catégorie correspond à l'obésité de classe III, également appelée obésité" +
                " morbide. Les personnes ayant un IMC dans cette plage sont à risque extrêmement élevé de" +
                " développer des problèmes de santé liés au poids, notamment le diabète de type 2, les maladies" +
                " cardiaques et l'apnée du sommeil.";
        addToDescription(idObesiteIII, dObesiteIII);

    }

    private UnitePoids findUnitePoidsById(int id){
        for (UnitePoids item:unitePoids) {
            if(item.id == id){
                return item;
            }
        }
        return null;
    }

    private UniteTaille findUniteTailleById(int id){
        for (UniteTaille item:uniteTailles) {
            if(item.id == id){
                return item;
            }
        }
        return null;
    }

    private Category findCategoryByIMCValue(double value){
        for (Category item:categories) {
            if(value >= item.min && value<= item.max){
                return item;
            }
        }
        return null;
    }

    private void createHistories(){
        histories = new ArrayList<>();
        int nbHistory = faker.random().nextInt(10, 50);
        for (int i = 0; i < nbHistory; i++) {
            History h = new History();
            h.idUser = faker.random().nextInt(1, users.size());
            h.poids = faker.random().nextInt(100, 200);
            h.taille = faker.number().randomDouble(2, 1, 2);
            h.idUnitePoids = faker.number().numberBetween(1, 2);
            h.idUniteTaille = faker.number().numberBetween(1, 2);
            UniteTaille uniteTaille = findUniteTailleById(h.idUniteTaille);
            UnitePoids unitePoids = findUnitePoidsById(h.idUnitePoids);
            if(uniteTaille != null && unitePoids!= null){
                IMC nImc = new IMC(h.taille, h.poids, unitePoids, uniteTaille);
                h.imc = IMCService.getValue(nImc).value;
            }
            String[] dt = Helper.getCurrentDateTime();
            h.date = dt[0];
            h.heure = dt[1];
            Category category = findCategoryByIMCValue(h.imc);
            if(category != null){
                h.idCategory = category.id;
            }
            histories.add(h);
        }
    }

    private void createUnitePoids(){
        unitePoids = new ArrayList<>();
        unitePoids.add(new UnitePoids(1, "kg", 1));
        unitePoids.add(new UnitePoids(2, "g", 0.001));
    }

    private void createUniteTaille(){
        uniteTailles = new ArrayList<>();
        uniteTailles.add(new UniteTaille(1,"m", 1));
        uniteTailles.add(new UniteTaille(2,"cm", 0.01));
    }

}
