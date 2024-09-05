package com.example.dog_care_android.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Locale;

public class DateUtils {

    public static String convertToLocalTime(Date date) {
        // Obtener la zona horaria local
        TimeZone localTimeZone = TimeZone.getDefault();

        // Crear el formateador de fecha con la zona horaria local
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE d MMMM 'a las' HH:mm", new Locale("es", "ES"));
        sdf.setTimeZone(localTimeZone);

        // Formatear la fecha
        return sdf.format(date);
    }
}

