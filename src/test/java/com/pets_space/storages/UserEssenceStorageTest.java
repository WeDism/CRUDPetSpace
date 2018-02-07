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

    @Test
    public void getInstance() {
        assertNotNull(UserEssenceStorage.getInstance());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setFriendState() {
        UserEssence userEssence1 = getUseerEssence1();
        UserEssence userEssence2 = getUseerEssence2();

        assertTrue(this.userEssenceStorage.add(userEssence1));
        assertTrue(this.userEssenceStorage.add(userEssence2));
        assertTrue(this.userEssenceStorage.setFriendState(userEssence1.getUserEssenceId(), userEssence2.getUserEssenceId(), StateFriend.REQUESTED));
        assertTrue(this.userEssenceStorage.setFriendState(userEssence1.getUserEssenceId(), userEssence1.getUserEssenceId(), StateFriend.REQUESTED));
    }

    @Test
    public void findEssences() {
        UserEssence userEssence = getUseerEssence();
        HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(mockHttpServletRequest.getParameter("name")).thenReturn(userEssence.getName());
        EssenceForSearchFriend essenceForSearchFriend = new EssenceForSearchFriend(mockHttpServletRequest);

        assertTrue(this.userEssenceStorage.add(userEssence));
        Set<UserEssence> userEssences = this.userEssenceStorage.findEssences(essenceForSearchFriend).orElse(null);
        assertTrue(userEssences.contains(userEssence));

        mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(mockHttpServletRequest.getParameter("name")).thenReturn(userEssence.getName());
        Mockito.when(mockHttpServletRequest.getParameter("surname")).thenReturn(userEssence.getSurname());
        Mockito.when(mockHttpServletRequest.getParameter("patronymic")).thenReturn(userEssence.getPatronymic());
        essenceForSearchFriend = new EssenceForSearchFriend(mockHttpServletRequest);
        userEssences = this.userEssenceStorage.findEssences(essenceForSearchFriend).orElse(null);
        assertTrue(userEssences.contains(userEssence));
    }

    @Test
    public void setFriendsRequest() {
        UserEssence userEssence1 = getUseerEssence1();
        UserEssence userEssence2 = getUseerEssence2();

        assertTrue(this.userEssenceStorage.add(userEssence1));
        assertTrue(this.userEssenceStorage.add(userEssence2));
        assertTrue(this.userEssenceStorage.setFriendsRequest(userEssence1.getUserEssenceId(), userEssence2.getUserEssenceId()));

        userEssence1 = this.userEssenceStorage.findById(userEssence1.getUserEssenceId()).orElse(null);
        userEssence2 = this.userEssenceStorage.findById(userEssence2.getUserEssenceId()).orElse(null);
        assertNotNull(userEssence1);
        assertNotNull(userEssence2);
        assertTrue(userEssence1.getRequestedFriendsFrom().containsKey(userEssence2.getUserEssenceId()));
        assertTrue(userEssence2.getRequestedFriendsTo().containsKey(userEssence1.getUserEssenceId()));
    }

    @Test
    public void deleteFriendsRequest() {
        UserEssence userEssence1 = getUseerEssence1();
        UserEssence userEssence2 = getUseerEssence2();

        assertTrue(this.userEssenceStorage.add(userEssence1));
        assertTrue(this.userEssenceStorage.add(userEssence2));
        assertTrue(this.userEssenceStorage.setFriendsRequest(userEssence1.getUserEssenceId(), userEssence2.getUserEssenceId()));

        userEssence1 = this.userEssenceStorage.findById(userEssence1.getUserEssenceId()).orElse(null);
        userEssence2 = this.userEssenceStorage.findById(userEssence2.getUserEssenceId()).orElse(null);
        assertNotNull(userEssence1);
        assertNotNull(userEssence2);
        assertTrue(userEssence1.getRequestedFriendsFrom().containsKey(userEssence2.getUserEssenceId()));
        assertTrue(userEssence2.getRequestedFriendsTo().containsKey(userEssence1.getUserEssenceId()));

        assertTrue(this.userEssenceStorage.deleteFriendsRequest(userEssence1.getUserEssenceId(), userEssence2.getUserEssenceId()));

        userEssence1 = this.userEssenceStorage.findById(userEssence1.getUserEssenceId()).orElse(null);
        userEssence2 = this.userEssenceStorage.findById(userEssence2.getUserEssenceId()).orElse(null);
        assertNotNull(userEssence1);
        assertNotNull(userEssence2);
        assertFalse(userEssence1.getRequestedFriendsFrom().containsKey(userEssence2.getUserEssenceId()));
        assertFalse(userEssence2.getRequestedFriendsTo().containsKey(userEssence1.getUserEssenceId()));

    }
}