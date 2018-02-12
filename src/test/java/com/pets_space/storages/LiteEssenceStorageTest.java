package com.pets_space.storages;

import com.pets_space.models.essences.Essence;
import com.pets_space.models.essences.LiteEssence;
import com.pets_space.models.essences.UserEssence;
import org.junit.Test;

import java.util.Set;

import static com.pets_space.storages.UserEssenceStorageTestData.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class LiteEssenceStorageTest extends DbInit {

    @Test
    public void getInstance() {
        assertNotNull(this.liteEssenceStorage);
    }

    @Test
    public void findById() {
        UserEssence userEssenceSteven = getUserEssenceSteven();
        assertTrue(this.userEssenceStorage.add(userEssenceSteven));
        LiteEssence liteEssence = this.liteEssenceStorage.findById(userEssenceSteven.getUserEssenceId()).orElse(null);
        assertNotNull(liteEssence);
        assertTrue(userEssenceSteven.getUserEssenceId().equals(liteEssence.getUserEssenceId()));
        assertTrue(userEssenceSteven.getName().equals(liteEssence.getName()));
        assertTrue(userEssenceSteven.getNickname().equals(liteEssence.getNickname()));
        assertTrue(userEssenceSteven.getPatronymic().equals(liteEssence.getPatronymic()));
        assertTrue(userEssenceSteven.getRole().equals(liteEssence.getRole()));
        assertTrue(userEssenceSteven.getStatusEssence().equals(liteEssence.getStatusEssence()));
        assertTrue(userEssenceSteven.getSurname().equals(liteEssence.getSurname()));
    }
}