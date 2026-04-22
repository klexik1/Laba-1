package com.khalchukov.service;

import com.khalchukov.dao.SmartphoneRepository;
import com.khalchukov.entity.SmartphoneEntity;
import com.khalchukov.exception.SmartphoneNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SmartphoneServiceImplTest {

    @Mock
    private SmartphoneRepository smartphoneRepository;

    @InjectMocks
    private SmartphoneServiceImpl smartphoneService;

    @Test
    void findById_shouldReturnSmartphone_whenExists() {
        SmartphoneEntity smartphone = new SmartphoneEntity(1, "Samsung Galaxy S24", 79999.99);
        when(smartphoneRepository.findById(1)).thenReturn(smartphone);

        SmartphoneEntity result = smartphoneService.findById(1);

        assertEquals(smartphone, result);
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {
        when(smartphoneRepository.findById(99)).thenReturn(null);

        assertThrows(SmartphoneNotFoundException.class, () -> smartphoneService.findById(99));
    }

    @Test
    void save_shouldReturnId_whenSaved() {
        SmartphoneEntity smartphone = new SmartphoneEntity("Apple iPhone 16", 99999.99);
        when(smartphoneRepository.save(any(SmartphoneEntity.class))).thenReturn(5);

        int id = smartphoneService.save("Apple iPhone 16", 99999.99);

        assertEquals(5, id);
    }

    @Test
    void update_shouldThrowException_whenNotFound() {
        SmartphoneEntity smartphone = new SmartphoneEntity(99, "Нет такого", 1.0);
        when(smartphoneRepository.update(smartphone)).thenReturn(false);

        assertThrows(SmartphoneNotFoundException.class, () -> smartphoneService.update(smartphone));
    }

    @Test
    void update_shouldNotThrow_whenExists() {
        SmartphoneEntity smartphone = new SmartphoneEntity(1, "Samsung Galaxy S24 Ultra", 89999.99);
        when(smartphoneRepository.update(smartphone)).thenReturn(true);

        assertDoesNotThrow(() -> smartphoneService.update(smartphone));
        verify(smartphoneRepository).update(smartphone);
    }

    @Test
    void findByModel_shouldReturnSmartphone_whenExists() {
        SmartphoneEntity smartphone = new SmartphoneEntity(2, "Google Pixel 9", 69999.99);
        when(smartphoneRepository.findAll()).thenReturn(List.of(smartphone));

        SmartphoneEntity result = smartphoneService.findByModel("Google Pixel 9");

        assertEquals(smartphone, result);
    }

    @Test
    void findByModel_shouldThrowException_whenNotFound() {
        when(smartphoneRepository.findAll()).thenReturn(List.of());

        assertThrows(SmartphoneNotFoundException.class,
                () -> smartphoneService.findByModel("Несуществующий"));
    }

    @Test
    void deleteById_shouldCallRepository() {
        smartphoneService.deleteById(1);

        verify(smartphoneRepository).deleteById(1);
    }

    @Test
    void findAll_shouldReturnAllSmartphones() {
        List<SmartphoneEntity> smartphones = List.of(
                new SmartphoneEntity(1, "Samsung Galaxy S24", 79999.99),
                new SmartphoneEntity(2, "Apple iPhone 16", 99999.99)
        );
        when(smartphoneRepository.findAll()).thenReturn(smartphones);

        List<SmartphoneEntity> result = smartphoneService.findAll();

        assertEquals(2, result.size());
    }
}
