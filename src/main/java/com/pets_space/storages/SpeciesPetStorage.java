package com.pets_space.storages;

import com.pets_space.models.SpeciesPet;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;

public class SpeciesPetStorage {
    private static final Logger LOG = getLogger(SpeciesPetStorage.class);
    private static final SpeciesPetStorage INSTANCE = new SpeciesPetStorage();

    private SpeciesPetStorage() {
    }

    public static SpeciesPetStorage getInstance() {
        return SpeciesPetStorage.INSTANCE;
    }

    public Optional<SpeciesPet> add(SpeciesPet speciesPet) {
        Optional<SpeciesPet> speciesPetOptional = Optional.of(speciesPet);
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO species_pet VALUES (?)")) {
            statement.setString(1, speciesPet.getName());
            statement.execute();
        } catch (SQLException e) {
            LOG.error("Error occurred in creating speciesPet", e);
            speciesPetOptional = Optional.empty();
        }
        return speciesPetOptional;
    }

    public Set<SpeciesPet> getAll() {
        Set<SpeciesPet> result = null;
        try (Connection connection = Pool.getDataSource().getConnection();
             ResultSet rs = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                     .executeQuery("SELECT * FROM species_pet")) {
            rs.last();
            result = new HashSet<>(rs.getRow());
            rs.beforeFirst();
            while (rs.next()) {
                result.add(new SpeciesPet(rs.getString("name")));
            }
        } catch (SQLException e) {
            LOG.error("Error occurred in getting all speciesPet", e);
        }
        return result;
    }

    public boolean validateSpecies(SpeciesPet speciesPet) {
        boolean isValidate = false;
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM species_pet WHERE name=?")) {
            statement.setString(1, speciesPet.getName());
            isValidate = statement.executeQuery().next();
        } catch (SQLException e) {
            LOG.error("Error occurred in getting all speciesPet", e);
        }
        return isValidate;
    }
}
