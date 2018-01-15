package com.pets_space.storages;

import com.pets_space.models.SpeciesPet;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.slf4j.LoggerFactory.getLogger;

public class SpeciesPetStorage {
    private static final Logger LOG = getLogger(SpeciesPetStorage.class);
    private static final SpeciesPetStorage INSTANCE = new SpeciesPetStorage();

    private SpeciesPetStorage() {
    }

    public static SpeciesPetStorage getInstance() {
        return SpeciesPetStorage.INSTANCE;
    }

    public SpeciesPet add(SpeciesPet speciesPet) {
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO species_pet VALUES (?)")) {
            statement.setObject(1, speciesPet.getName());
        } catch (SQLException e) {
            LOG.error("Error occurred in creating speciesPet", e);
        }
        return speciesPet;
    }
}
