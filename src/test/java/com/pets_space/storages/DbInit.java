package com.pets_space.storages;

import com.google.common.io.ByteStreams;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DbInit {
    private static final Logger LOG = LoggerFactory.getLogger(DbInit.class);
    protected final UserEssenceStorage userEssenceStorage = UserEssenceStorage.getInstance();
    protected final PetStorage petStorage = PetStorage.getInstance();
    protected final GenusPetStorage genusPetStorage = GenusPetStorage.getInstance();
    protected final FollowPetStorage followPetStorage = FollowPetStorage.getInstance();
    protected final LiteEssenceStorage liteEssenceStorage = LiteEssenceStorage.getInstance();

    @Before
    public void initDb() {
        try (Connection collection = Pool.getDataSource().getConnection();
             Statement statement = collection.createStatement()) {
            statement.execute(new String(ByteStreams.toByteArray(
                    this.getClass().getClassLoader().getResourceAsStream("schema.sql")), Charset.forName("UTF-8")));
        } catch (final IOException | SQLException e) {
            LOG.error("error", e);
        }
    }
}
