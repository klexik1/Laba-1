package com.khalchukov.service;

import com.khalchukov.model.Character;
import com.khalchukov.model.Gender;
import java.nio.charset.StandardCharsets;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.EnumMap;
import java.util.List;

public class GenderCountService {

    public EnumMap<Gender, Integer> countByGender(List<Character> characters) {
        EnumMap<Gender, Integer> counts = new EnumMap<>(Gender.class);
        for (Gender g : Gender.values()) {
            counts.put(g, 0);
        }

        for (Character character : characters) {
            Gender gender = character.getGender();
            counts.put(gender, counts.get(gender) + 1);
        }
        return counts;
    }

    public void saveToFile(Path path, EnumMap<Gender, Integer> counts) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path,
                StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {

            for (Gender gender : Gender.values()) {
                int count = counts.getOrDefault(gender, 0);
                writer.write("%s → %d".formatted(gender.name(), count));
                writer.newLine();
            }
        }
    }
}