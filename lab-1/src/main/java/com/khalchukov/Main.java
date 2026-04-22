package com.khalchukov;

import com.khalchukov.model.Character;
import com.khalchukov.model.Gender;
import com.khalchukov.service.CharacterCrudService;
import com.khalchukov.service.GenderCountService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.EnumMap;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        URL resource = Main.class.getClassLoader().getResource("characters.csv");
        if (resource == null) {
            System.err.println("Файл characters.csv не найден!");
            return;
        }

        Path workDir = Paths.get("output");
        Files.createDirectories(workDir);
        Path csvPath = workDir.resolve("characters.csv");
        Files.copy(Paths.get(resource.toURI()), csvPath, StandardCopyOption.REPLACE_EXISTING);

        CharacterCrudService crud = new CharacterCrudService(csvPath);
        GenderCountService genderService = new GenderCountService();

        System.out.println("=== ТЕСТ CRUD ОПЕРАЦИЙ ===\n");

        System.out.println("1. Чтение всех персонажей:");
        List<Character> all = crud.findAll();
        System.out.println("   Загружено: " + all.size() + " персонажей\n");

        System.out.println("2. Поиск по ID=1:");
        crud.findById(1).ifPresentOrElse(
                c -> System.out.println("   Найден: " + c.getName() + "\n"),
                () -> System.out.println("   Не найден\n")
        );

        System.out.println("3. Создание нового персонажа:");
        Character newChar = crud.create(
                "Test Character",
                "Alive",
                "Human",
                "",
                Gender.MALE,
                "Earth",
                "Earth"
        );
        System.out.println();


        System.out.println("4. Обновление персонажа (ID=" + newChar.getId() + "):");
        crud.update(newChar.getId(), "Updated Name", null, null, null,
                Gender.FEMALE, null, null);
        System.out.println();


        System.out.println("5. Все персонажи после изменений:");
        List<Character> updated = crud.findAll();
        System.out.println("   Теперь их: " + updated.size() + "\n");


        System.out.println("6. Удаление тестового персонажа:");
        crud.delete(newChar.getId());
        System.out.println();


        System.out.println("=== СТАТИСТИКА ПО ПОЛУ ===");
        List<Character> characters = crud.findAll();
        EnumMap<Gender, Integer> stats = genderService.countByGender(characters);

        Path statsPath = workDir.resolve("gender_stats.txt");
        genderService.saveToFile(statsPath, stats);

        System.out.println("Результат сохранён в: " + statsPath.toAbsolutePath());
        System.out.println("\nСодержимое файла:");
        Files.lines(statsPath).forEach(System.out::println);
    }
}