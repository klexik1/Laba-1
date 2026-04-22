package com.khalchukov.service;

import com.khalchukov.entity.SmartphoneEntity;

import java.util.List;

public interface SmartphoneService {

    // Create — возвращает id созданной записи
    int save(String model, double price);

    // Read (бросает исключение, если запись не найдена)
    SmartphoneEntity findById(int id);

    // Read (бросает исключение, если запись не найдена)
    SmartphoneEntity findByModel(String model);

    List<SmartphoneEntity> findAll();

    // Update (бросает исключение, если запись не найдена)
    void update(SmartphoneEntity smartphone);

    // Delete
    void deleteById(int id);
}
