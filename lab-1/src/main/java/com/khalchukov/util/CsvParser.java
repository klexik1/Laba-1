package com.khalchukov.util;

import com.khalchukov.model.Character;
import com.khalchukov.model.Gender;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {

    private CsvParser() {}

    public static List<Character> readAll(Path path) throws IOException {
        List<Character> characters = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line = reader.readLine();
            if (line == null) return characters;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;

                try {
                    characters.add(parseLine(line));
                } catch (Exception e) {
                    System.err.println("Пропускаем некорректную строку: " + line);
                    System.err.println("Причина: " + e.getMessage());
                }
            }
        }
        return characters;
    }

    private static Character parseLine(String line) {
        String[] parts = line.split(",", -1);

        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        int    id           = Integer.parseInt(parts[0]);
        String name         = parts[1];
        String status       = parts[2];
        String species      = parts[3];
        String type         = parts[4];
        Gender gender       = Gender.fromString(parts[5]);
        String originName   = parts[6];
        String locationName = parts[7];
        String created      = parts.length > 8 ? parts[8] : "";

        return new Character(id, name, status, species, type,
                gender, originName, locationName, created);
    }


    public static void writeAll(Path path, List<Character> characters) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write(Character.csvHeader());
            writer.newLine();

            for (Character c : characters) {
                writer.write(String.join(",",
                        String.valueOf(c.getId()),
                        c.getName(),
                        c.getStatus(),
                        c.getSpecies(),
                        c.getType(),
                        c.getGender().name().charAt(0) + c.getGender().name().substring(1).toLowerCase(),
                        c.getOriginName(),
                        c.getLocationName(),
                        c.getCreated()
                ));
                writer.newLine();
            }
        }
    }
}