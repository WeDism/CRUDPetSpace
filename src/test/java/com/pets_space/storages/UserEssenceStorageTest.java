package com.pets_space.storages;

import com.pets_space.models.EssenceForSearchFriend;
import com.pets_space.models.essences.Role;
import com.pets_space.models.essences.StateFriend;
import com.pets_space.models.essences.UserEssence;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.UUID;

import static com.pets_space.storages.UserEssenceStorageTestData.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

public class UserEssenceStorageTest extends DbInit {

    @Test
    public void add() {
        UserEssence userEssenceSteven = getUserEssenceSteven();
        assertTrue(this.userEssenceStorage.add(userEssenceSteven));
        assertThat(userEssenceSteven, is(this.userEssenceStorage.findById(userEssenceSteven.getUserEssenceId()).orElse(null)));
    }

    @Test
    public void getAll() {
        UserEssence userEssenceAndrey = getUserEssenceAndrey();
        UserEssence userEssenceRichard = getUserEssenceRichard();
        assertTrue(this.userEssenceStorage.add(userEssenceAndrey));
        assertTrue(this.userEssenceStorage.add(userEssenceRichard));
        Set<UserEssence> all = this.userEssenceStorage.getAll();
        assertFalse(all.size() == 3);
        assertTrue(all.size() == 2);
    }

    @Test
    public void delete() {
        UserEssence userEssenceSteven = getUserEssenceSteven();
        assertTrue(this.userEssenceStorage.add(userEssenceSteven));
        Set<UserEssence> all = this.userEssenceStorage.getAll();
        assertTrue(all.size() == 1);
        assertTrue(this.userEssenceStorage.delete(userEssenceSteven.getUserEssenceId()));
        all = this.userEssenceStorage.getAll();
        assertFalse(all.size() == 1);
        assertTrue(all.size() == 0);
    }


    @Test
    public void update() {
        UUID uuid = UUID.randomUUID();
        UserEssence userEssenceAndrey = getUserEssenceAndrey().setUserEssenceId(uuid);
        UserEssence userEssenceRichard = getUserEssenceRichard().setUserEssenceId(uuid);
        assertTrue(this.userEssenceStorage.add(userEssenceAndrey));
        assertThat(userEssenceAndrey, is(this.userEssenceStorage.findById(userEssenceAndrey.getUserEssenceId()).orElse(null)));
        assertTrue(this.userEssenceStorage.update(userEssenceRichard));
        assertThat(userEssenceRichard, is(this.userEssenceStorage.findById(userEssenceRichard.getUserEssenceId()).orElse(null)));
    }

    @Test
    public void findByCredential() {
        UserEssence userEssenceSteven = getUserEssenceSteven();
        assertTrue(this.userEssenceStorage.add(userEssenceSteven));
        UserEssence userEssence = this.userEssenceStorage.findByCredential(userEssenceSteven.getNickname(), userEssenceSteven.getPassword()).orElse(null);
        assertThat(userEssenceSteven, is(userEssence));
    }

    @Test
    public void findByNickname() {
        UserEssence userEssenceSteven = getUserEssenceSteven();
        assertTrue(this.userEssenceStorage.add(userEssenceSteven));
        UserEssence userEssence = this.userEssenceStorage.findByNickname(userEssenceSteven.getNickname()).orElse(null);
        assertThat(userEssenceSteven, is(userEssence));
    }

    @Test
    public void updateRole() {
        UserEssence userEssenceSteven = getUserEssenceSteven();
        assertTrue(this.userEssenceStorage.add(userEssenceSteven));
        assertTrue(this.userEssenceStorage.updateRole(userEssenceSteven.setRole(Role.USER)));
        userEssenceSteven = this.userEssenceStorage.findByNickname(userEssenceSteven.getNickname()).orElse(null);
        assertNotNull(userEssenceSteven);
        assertThat(Role.USER, is(userEssenceSteven.getRole()));
    }

    @Test
    public void findById() {
        UserEssence userEssenceSteven = getUserEssenceSteven();
        assertTrue(this.userEssenceStorage.add(userEssenceSteven));
        assertThat(userEssenceSteven, is(this.userEssenceStorage.findById(userEssenceSteven.getUserEssenceId()).orElse(null)));
        assertTrue(this.userEssenceStorage.delete(userEssenceSteven.getUserEssenceId()));
        assertThat(userEssenceSteven, not(this.userEssenceStorage.findById(userEssenceSteven.getUserEssenceId()).orElse(null)));
    }

    @Test
    public void getInstance() {
        assertNotNull(UserEssenceStorage.getInstance());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setFriendState() {
        UserEssence userEssenceAndrey = getUserEssenceAndrey();
        UserEssence userEssenceRichard = getUserEssenceRichard();

        assertTrue(this.userEssenceStorage.add(userEssenceAndrey));
        assertTrue(this.userEssenceStorage.add(userEssenceRichard));
        assertTrue(this.userEssenceStorage.setFriendState(userEssenceAndrey.getUserEssenceId(), userEssenceRichard.getUserEssenceId(), StateFriend.REQUESTED));
        assertTrue(this.userEssenceStorage.setFriendState(userEssenceAndrey.getUserEssenceId(), userEssenceAndrey.getUserEssenceId(), StateFriend.REQUESTED));
    }

    @Test
    public void findEssences() {
        UserEssence userEssenceSteven = getUserEssenceSteven();
        HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(mockHttpServletRequest.getParameter("name")).thenReturn(userEssenceSteven.getName());
        EssenceForSearchFriend essenceForSearchFriend = new EssenceForSearchFriend(mockHttpServletRequest);

        assertTrue(this.userEssenceStorage.add(userEssenceSteven));
        Set<UserEssence> userEssences = this.userEssenceStorage.findEssences(essenceForSearchFriend).orElse(null);
        assertNotNull(userEssences);
        assertTrue(userEssences.contains(userEssenceSteven));

        mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(mockHttpServletRequest.getParameter("name")).thenReturn(userEssenceSteven.getName());
        Mockito.when(mockHttpServletRequest.getParameter("surname")).thenReturn(userEssenceSteven.getSurname());
        Mockito.when(mockHttpServletRequest.getParameter("patronymic")).thenReturn(userEssenceSteven.getPatronymic());
        essenceForSearchFriend = new EssenceForSearchFriend(mockHttpServletRequest);
        userEssences = this.userEssenceStorage.findEssences(essenceForSearchFriend).orElse(null);
        assertNotNull(userEssences);
        assertTrue(userEssences.contains(userEssenceSteven));
    }

    @Test
    public void setFriendsRequest() {
        UserEssence userEssenceAndrey = getUserEssenceAndrey();
        UserEssence userEssenceRichard = getUserEssenceRichard();

        assertTrue(this.userEssenceStorage.add(userEssenceAndrey));
        assertTrue(this.userEssenceStorage.add(userEssenceRichard));
        assertTrue(this.userEssenceStorage.setFriendsRequest(userEssenceAndrey.getUserEssenceId(), userEssenceRichard.getUserEssenceId()));

        userEssenceAndrey = this.userEssenceStorage.findById(userEssenceAndrey.getUserEssenceId()).orElse(null);
        userEssenceRichard = this.userEssenceStorage.findById(userEssenceRichard.getUserEssenceId()).orElse(null);
        assertNotNull(userEssenceAndrey);
        assertNotNull(userEssenceRichard);
        assertTrue(userEssenceAndrey.getRequestedFriendsFrom().containsKey(userEssenceRichard.getUserEssenceId()));
        assertTrue(userEssenceRichard.getRequestedFriendsTo().containsKey(userEssenceAndrey.getUserEssenceId()));
    }

    @Test
    public void deleteFriendsRequest() {
        UserEssence userEssenceAndrey = getUserEssenceAndrey();
        UserEssence userEssenceRichard = getUserEssenceRichard();

        assertTrue(this.userEssenceStorage.add(userEssenceAndrey));
        assertTrue(this.userEssenceStorage.add(userEssenceRichard));
        assertTrue(this.userEssenceStorage.setFriendsRequest(userEssenceAndrey.getUserEssenceId(), userEssenceRichard.getUserEssenceId()));

        userEssenceAndrey = this.userEssenceStorage.findById(userEssenceAndrey.getUserEssenceId()).orElse(null);
        userEssenceRichard = this.userEssenceStorage.findById(userEssenceRichard.getUserEssenceId()).orElse(null);
        assertNotNull(userEssenceAndrey);
        assertNotNull(userEssenceRichard);
        assertTrue(userEssenceAndrey.getRequestedFriendsFrom().containsKey(userEssenceRichard.getUserEssenceId()));
        assertTrue(userEssenceRichard.getRequestedFriendsTo().containsKey(userEssenceAndrey.getUserEssenceId()));

        assertTrue(this.userEssenceStorage.deleteFriendsRequest(userEssenceAndrey.getUserEssenceId(), userEssenceRichard.getUserEssenceId()));

        userEssenceAndrey = this.userEssenceStorage.findById(userEssenceAndrey.getUserEssenceId()).orElse(null);
        userEssenceRichard = this.userEssenceStorage.findById(userEssenceRichard.getUserEssenceId()).orElse(null);
        assertNotNull(userEssenceAndrey);
        assertNotNull(userEssenceRichard);
        assertFalse(userEssenceAndrey.getRequestedFriendsFrom().containsKey(userEssenceRichard.getUserEssenceId()));
        assertFalse(userEssenceRichard.getRequestedFriendsTo().containsKey(userEssenceAndrey.getUserEssenceId()));

    }
}