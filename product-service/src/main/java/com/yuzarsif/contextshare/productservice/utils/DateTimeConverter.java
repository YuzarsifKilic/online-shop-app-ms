package com.yuzarsif.contextshare.productservice.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeConverter {

    public static LocalDateTime toLocalDateTime(String date) {

        if (date.contains("T")) {
            return LocalDateTime.parse(date);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return LocalDateTime.parse(date, formatter);
    }
}
