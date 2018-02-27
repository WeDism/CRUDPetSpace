package com.pet_space.storages;

import com.pet_space.models.GenusPet;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.slf4j.LoggerFactory.getLogger;

public class GenusPetStorage {
    private static final Logger LOG = getLogger(GenusPetStorage.class);
    private static final GenusPetStorage INSTANCE = new GenusPetStorage();

    private GenusPetStorage() {
    }

    public static GenusPetStorage getInstance() {
        return GenusPetStorage.INSTANCE;
    }

    public boolean add(@NotNull GenusPet genusPet) {
        checkNotNull(genusPet);

        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO genus_pet VALUES (?)")) {
            statement.setString(1, genusPet.getName());
            statement.execute();
        } catch (SQLException e) {
            LOG.error("Error occurred in creating genusPet", e);
            return false;
        }
        return true;
    }

    public Set<GenusPet> getAll() {
        Set<GenusPet> result = null;
        try (Connection connection = Pool.getDataSource().getConnection();
             ResultSet rs = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)
                     .executeQuery("SELECT * FROM genus_pet")) {
            rs.last();
            result = new HashSet<>(rs.getRow());
            rs.beforeFirst();
            while (rs.next()) {
                result.add(new GenusPet(rs.getString("name")));
            }
        } catch (SQLException e) {
            LOG.error("Error occurred in getting all genusPet", e);
        }
        return result;
    }

    public boolean validateGenus(@NotNull GenusPet genusPet) {
        checkNotNull(genusPet);

        boolean isValidate;
        try (Connection connection = Pool.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM genus_pet WHERE name=?")) {
            statement.setString(1, genusPet.getName());
            isValidate = statement.executeQuery().next();
        } catch (SQLException e) {
            LOG.error("Error occurred in getting all genusPet", e);
            return false;
        }
        return isValidate;
    }
}
