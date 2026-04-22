package com.khalchukov.service;

import com.khalchukov.model.Character;
import com.khalchukov.model.Gender;
import com.khalchukov.util.CsvParser;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class CharacterCrudService {
    private final Path csvPath;
    public CharacterCrudService(Path csvPath) {
        this.csvPath = csvPath;
    }
    public List<Character> findAll() throws IOException {
        return CsvParser.readAll(csvPath);
    }

    public Optional<Character> findById(int id) throws IOException {
        List<Character> all = findAll();
        for (Character character : all) {
            if (character.getId() == id) {
                return Optional.of(character);
            }
        }
        return Optional.empty();
    }

    public Character create(String name, String status, String species,
                            String type, Gender gender,
                            String originName, String locationName) throws IOException {

        List<Character> all = findAll();

        int newId = 1;
        for (Character character : all) {
            if (character.getId() >= newId) {
                newId = character.getId() + 1;
            }
        }

        Character newCharacter = new Character(
                newId, name, status, species, type, gender,
                originName, locationName, Instant.now().toString()
        );

        all.add(newCharacter);
        CsvParser.writeAll(csvPath, all);

        System.out.println(" Создан персонаж: ID=" + newId + ", имя=" + name);
        return newCharacter;
    }

    public Optional<Character> update(int id,
                                      String name, String status, String species,
                                      String type, Gender gender,
                                      String originName, String locationName) throws IOException {

        List<Character> all = findAll();

        Character characterToUpdate = null;
        for (Character character : all) {
            if (character.getId() == id) {
                characterToUpdate = character;
                break;
            }
        }

        if (characterToUpdate == null) {
            System.out.println(" Персонаж с ID=" + id + " не найден");
            return Optional.empty();
        }

        if (name != null) characterToUpdate.setName(name);
        if (status != null) characterToUpdate.setStatus(status);
        if (species != null) characterToUpdate.setSpecies(species);
        if (type != null) characterToUpdate.setType(type);
        if (gender != null) characterToUpdate.setGender(gender);
        if (originName != null) characterToUpdate.setOriginName(originName);
        if (locationName != null) characterToUpdate.setLocationName(locationName);

        CsvParser.writeAll(csvPath, all);

        System.out.println(" Обновлён персонаж: ID=" + id);
        return Optional.of(characterToUpdate);
    }


    public boolean delete(int id) throws IOException {
        List<Character> all = findAll();

        boolean removed = all.removeIf(c -> c.getId() == id);

        if (removed) {
            CsvParser.writeAll(csvPath, all);
            System.out.println(" Удалён персонаж: ID=" + id);
        } else {
            System.out.println(" Персонаж с ID=" + id + " не найден");
        }

        return removed;
    }
}