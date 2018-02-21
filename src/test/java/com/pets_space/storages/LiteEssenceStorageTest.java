package com.pets_space.storages;

import com.pets_space.models.essences.Essence;
import com.pets_space.models.essences.LiteEssence;
import com.pets_space.models.essences.UserEssence;
import org.junit.Test;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.pets_space.storages.UserEssenceStorageTestData.getUserEssenceAndrey;
import static com.pets_space.storages.UserEssenceStorageTestData.getUserEssenceSteven;
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

    @Test
    public void findByIds() {
        UserEssence userEssenceSteven = getUserEssenceSteven();
        UserEssence userEssenceAndrey = getUserEssenceAndrey();
        assertTrue(this.userEssenceStorage.add(userEssenceSteven));
        assertTrue(this.userEssenceStorage.add(userEssenceAndrey));
        assertTrue(this.userEssenceStorage.setFriendsRequest(userEssenceSteven.getUserEssenceId(), userEssenceAndrey.getUserEssenceId()));

        userEssenceSteven=this.userEssenceStorage.findById(userEssenceSteven.getUserEssenceId()).orElse(null);
        userEssenceAndrey=this.userEssenceStorage.findById(userEssenceAndrey.getUserEssenceId()).orElse(null);
        assertNotNull(userEssenceSteven);
        assertNotNull(userEssenceAndrey);

        Set<LiteEssence> liteEssences = this.liteEssenceStorage.findByIds(userEssenceSteven.getRequestedFriendsFrom().keySet()).orElse(null);
        assertNotNull(liteEssences);
        Set<UUID> collectUuidSet = liteEssences.stream().map(Essence::getUserEssenceId).collect(Collectors.toSet());
        assertTrue(collectUuidSet.contains(userEssenceAndrey.getUserEssenceId()));


        liteEssences = this.liteEssenceStorage.findByIds(userEssenceAndrey.getRequestedFriendsTo().keySet()).orElse(null);
        assertNotNull(liteEssences);
        collectUuidSet = liteEssences.stream().map(Essence::getUserEssenceId).collect(Collectors.toSet());
        assertTrue(collectUuidSet.contains(userEssenceSteven.getUserEssenceId()));

    }
}