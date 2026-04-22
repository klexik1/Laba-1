package com.khalchukov.dao;

import com.khalchukov.entity.SmartphoneEntity;

import java.util.List;

public interface SmartphoneRepository {

    // Create — возвращает id созданной записи
    int save(SmartphoneEntity smartphone);

    // Read — возвращает null, если запись не найдена
    SmartphoneEntity findById(int id);

    List<SmartphoneEntity> findAll();

    // Update — возвращает false, если запись не найдена
    boolean update(SmartphoneEntity smartphone);

    // Delete
    void deleteById(int id);
}
