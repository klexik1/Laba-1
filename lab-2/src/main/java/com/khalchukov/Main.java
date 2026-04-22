package com.khalchukov;

import com.khalchukov.dao.JdbcSmartphoneRepository;
import com.khalchukov.dao.SmartphoneRepository;
import com.khalchukov.db.ConnectionConfig;
import com.khalchukov.entity.SmartphoneEntity;
import com.khalchukov.exception.SmartphoneNotFoundException;
import com.khalchukov.service.SmartphoneService;
import com.khalchukov.service.SmartphoneServiceImpl;

import javax.sql.DataSource;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Запуск приложения Smartphone App ===");

        DataSource dataSource = new ConnectionConfig().createDataSource();
        SmartphoneRepository smartphoneRepository = new JdbcSmartphoneRepository(dataSource);
        SmartphoneService smartphoneService = new SmartphoneServiceImpl(smartphoneRepository);

        demonstrateCrudOperations(smartphoneService);

        System.out.println("=== Приложение завершило работу ===");
    }

    private static void demonstrateCrudOperations(SmartphoneService smartphoneService) {


        System.out.println("--- CREATE: сохранение смартфонов ---");

        int firstId = smartphoneService.save("Samsung Galaxy S24", 79999.99);
        System.out.println("Сохранён смартфон с id=" + firstId);

        int secondId = smartphoneService.save("Apple iPhone 16", 99999.99);
        System.out.println("Сохранён смартфон с id=" + secondId);

        int thirdId = smartphoneService.save("Google Pixel 9", 69999.99);
        System.out.println("Сохранён смартфон с id=" + thirdId);

        System.out.println("--- READ: поиск по id ---");

        SmartphoneEntity found = smartphoneService.findById(firstId);
        System.out.println("Найден: " + found);

        System.out.println("--- READ: поиск по модели ---");

        SmartphoneEntity byModel = smartphoneService.findByModel("Apple iPhone 16");
        System.out.println("Найден по модели: " + byModel);

        System.out.println("--- READ: все смартфоны ---");

        List<SmartphoneEntity> all = smartphoneService.findAll();
        for (SmartphoneEntity smartphone : all) {
            System.out.println("  " + smartphone);
        }

        System.out.println("--- UPDATE: обновление смартфона ---");

        SmartphoneEntity toUpdate = new SmartphoneEntity(firstId, "Samsung Galaxy S24 Ultra", 89999.99);
        smartphoneService.update(toUpdate);
        System.out.println("Обновление id=" + firstId + " выполнено");

        SmartphoneEntity afterUpdate = smartphoneService.findById(firstId);
        System.out.println("После обновления: " + afterUpdate);

        System.out.println("--- UPDATE несуществующего смартфона ---");
        try {
            smartphoneService.update(new SmartphoneEntity(999999, "Нет такого", 1.0));
        } catch (SmartphoneNotFoundException e) {
            System.out.println("Ожидаемое исключение: " + e.getMessage());
        }

        System.out.println("--- DELETE: удаление смартфона ---");

        smartphoneService.deleteById(thirdId);
        System.out.println("Удалён смартфон id=" + thirdId);

        System.out.println("Смартфоны после удаления:");
        smartphoneService.findAll().forEach(s -> System.out.println("  " + s));

        System.out.println("--- findById для удалённого id=" + thirdId + " ---");
        try {
            smartphoneService.findById(thirdId);
        } catch (SmartphoneNotFoundException e) {
            System.out.println("Ожидаемое исключение: " + e.getMessage());
        }
    }
}
