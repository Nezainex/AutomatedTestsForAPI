package org.example.utils;

import java.util.UUID;

public class RandomUtils {

    // Используем UUID для генерации случайного имени
    public static String getRandomName() {
        return UUID.randomUUID().toString().substring(0, 8);
    }


    // Используем UUID для генерации случайного job, обрезаем до первых 5 символов
    public static String getRandomJob() {
        return generateUUID().substring(0, 5);
    }

    // Генерация UUID
    private static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
