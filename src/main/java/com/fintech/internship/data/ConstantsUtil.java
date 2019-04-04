package com.fintech.internship.data;

import java.text.SimpleDateFormat;

public class ConstantsUtil {
    public static final String[] columns = {"Имя", "Отчество", "Фамилия", "Возраст",
            "Пол", "Дата рождения", "ИНН", "Почтовый индекс", "Страна",
            "Область", "Город", "Улица", "Дом", "Квартира"};

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    public static final String RANDOM_USER_URL = "https://randomuser.me/api/?nat=us&results=%d";
    public static final int USER_GENERATION_LIMIT = 30;
}