package com.khalchukov.service;

import com.khalchukov.dao.SmartphoneRepository;
import com.khalchukov.entity.SmartphoneEntity;
import com.khalchukov.exception.SmartphoneNotFoundException;

import java.util.List;

public class SmartphoneServiceImpl implements SmartphoneService {

    private final SmartphoneRepository smartphoneRepository;

    public SmartphoneServiceImpl(SmartphoneRepository smartphoneRepository) {
        this.smartphoneRepository = smartphoneRepository;
    }

    @Override
    public int save(String model, double price) {
        SmartphoneEntity smartphone = new SmartphoneEntity(model, price);
        return smartphoneRepository.save(smartphone);
    }

    @Override
    public SmartphoneEntity findById(int id) {
        SmartphoneEntity smartphone = smartphoneRepository.findById(id);
        if (smartphone == null) {
            throw new SmartphoneNotFoundException(id);
        }
        return smartphone;
    }

    @Override
    public SmartphoneEntity findByModel(String model) {
        return smartphoneRepository.findAll().stream()
                .filter(s -> s.getModel().equals(model))
                .findFirst()
                .orElseThrow(() -> new SmartphoneNotFoundException(model));
    }

    @Override
    public List<SmartphoneEntity> findAll() {
        return smartphoneRepository.findAll();
    }

    @Override
    public void update(SmartphoneEntity smartphone) {
        boolean updated = smartphoneRepository.update(smartphone);
        if (!updated) {
            throw new SmartphoneNotFoundException(smartphone.getId());
        }
    }

    @Override
    public void deleteById(int id) {
        smartphoneRepository.deleteById(id);
    }
}
