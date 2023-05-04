package com.easy.imc.webserviceeasyimc.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Helper {
    public static String[] getCurrentDateTime(){
        String pattern = "dd/MM/yyyy HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.now();
        String formattedDate = formatter.format(localDateTime);

        String[] dt = formattedDate.split("\\s+");

        return dt;
    }
}
