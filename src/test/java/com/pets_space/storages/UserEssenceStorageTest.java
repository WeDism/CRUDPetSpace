package com.pets_space.storages;

import com.pets_space.models.essences.Role;
import com.pets_space.models.essences.UserEssence;
import org.junit.Test;

import java.util.Set;
import java.util.UUID;

import static com.pets_space.storages.UserEssenceStorageTestData.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class UserEssenceStorageTest extends DbInit {

    @Test
    public void add() {
        UserEssence userEssence = getUseerEssence();
        assertTrue(this.userEssenceStorage.add(userEssence));
        assertThat(userEssence, is(this.userEssenceStorage.findById(userEssence.getUserEssenceId()).orElse(null)));
    }

    @Test
    public void getAll() {
        UserEssence userEssence1 = getUseerEssence1();
        UserEssence userEssence2 = getUseerEssence2();
        assertTrue(this.userEssenceStorage.add(userEssence1));
        assertTrue(this.userEssenceStorage.add(userEssence2));
        Set<UserEssence> all = this.userEssenceStorage.getAll();
        assertThat(all.size(), is(2));
        assertThat(all.size(), not(3));
    }

    @Test
    public void delete() {
        UserEssence userEssence = getUseerEssence();
        assertTrue(this.userEssenceStorage.add(userEssence));
        Set<UserEssence> all = this.userEssenceStorage.getAll();
        assertThat(all.size(), is(1));
        assertTrue(this.userEssenceStorage.delete(userEssence.getUserEssenceId()));
        all = this.userEssenceStorage.getAll();
        assertThat(all.size(), not(1));
        assertThat(all.size(), is(0));
    }


    @Test
    public void update() {
        UUID uuid = UUID.randomUUID();
        UserEssence userEssence1 = getUseerEssence1().setUserEssenceId(uuid);
        UserEssence userEssence2 = getUseerEssence2().setUserEssenceId(uuid);
        assertTrue(this.userEssenceStorage.add(userEssence1));
        assertThat(userEssence1, is(this.userEssenceStorage.findById(userEssence1.getUserEssenceId()).orElse(null)));
        assertTrue(this.userEssenceStorage.update(userEssence2));
        assertThat(userEssence2, is(this.userEssenceStorage.findById(userEssence2.getUserEssenceId()).orElse(null)));
    }

    @Test
    public void findByCredential() {
        UserEssence userEssence = getUseerEssence();
        assertTrue(this.userEssenceStorage.add(userEssence));
        UserEssence userEssence1 = this.userEssenceStorage.findByCredential(userEssence.getNickname(), userEssence.getPassword()).orElse(null);
        assertThat(userEssence, is(userEssence1));
    }

    @Test
    public void findByNickname() {
        UserEssence userEssence = getUseerEssence();
        assertTrue(this.userEssenceStorage.add(userEssence));
        UserEssence userEssence1 = this.userEssenceStorage.findByNickname(userEssence.getNickname()).orElse(null);
        assertThat(userEssence, is(userEssence1));
    }

    @Test
    public void updateRole() {
        UserEssence userEssence = getUseerEssence();
        assertTrue(this.userEssenceStorage.add(userEssence));
        assertTrue(this.userEssenceStorage.updateRole(userEssence.setRole(Role.USER)));
        userEssence = this.userEssenceStorage.findByNickname(userEssence.getNickname()).orElse(null);
        assertNotNull(userEssence);
        assertThat(Role.USER, is(userEssence.getRole()));
    }

    @Test
    public void findById() {
        UserEssence userEssence = getUseerEssence();
        assertTrue(this.userEssenceStorage.add(userEssence));
        assertThat(userEssence, is(this.userEssenceStorage.findById(userEssence.getUserEssenceId()).orElse(null)));
        assertTrue(this.userEssenceStorage.delete(userEssence.getUserEssenceId()));
        assertThat(userEssence, not(this.userEssenceStorage.findById(userEssence.getUserEssenceId()).orElse(null)));
    }
}