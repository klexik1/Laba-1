package com.khalchukov.dao;

import com.khalchukov.entity.SmartphoneEntity;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcSmartphoneRepository implements SmartphoneRepository {

    private static final String SQL_INSERT =
            "INSERT INTO smartphone (model, price) VALUES (?, ?) RETURNING id";

    private static final String SQL_FIND_BY_ID =
            "SELECT id, model, price FROM smartphone WHERE id = ?";

    private static final String SQL_FIND_ALL =
            "SELECT id, model, price FROM smartphone";

    private static final String SQL_UPDATE =
            "UPDATE smartphone SET model = ?, price = ? WHERE id = ?";

    private static final String SQL_DELETE_BY_ID =
            "DELETE FROM smartphone WHERE id = ?";

    private final DataSource dataSource;

    public JdbcSmartphoneRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int save(SmartphoneEntity smartphone) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {

            statement.setString(1, smartphone.getModel());
            statement.setDouble(2, smartphone.getPrice());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении смартфона", e);
        }

        throw new RuntimeException("Не удалось получить id после сохранения смартфона");
    }

    @Override
    public SmartphoneEntity findById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRow(resultSet);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске смартфона по id=" + id, e);
        }

        return null;
    }

    @Override
    public List<SmartphoneEntity> findAll() {
        List<SmartphoneEntity> smartphones = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                smartphones.add(mapRow(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении списка смартфонов", e);
        }

        return smartphones;
    }

    @Override
    public boolean update(SmartphoneEntity smartphone) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {

            statement.setString(1, smartphone.getModel());
            statement.setDouble(2, smartphone.getPrice());
            statement.setInt(3, smartphone.getId());

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении смартфона id=" + smartphone.getId(), e);
        }
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_ID)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении смартфона id=" + id, e);
        }
    }

    private SmartphoneEntity mapRow(ResultSet resultSet) throws SQLException {
        SmartphoneEntity smartphone = new SmartphoneEntity();
        smartphone.setId(resultSet.getInt("id"));
        smartphone.setModel(resultSet.getString("model"));
        smartphone.setPrice(resultSet.getDouble("price"));
        return smartphone;
    }
}
