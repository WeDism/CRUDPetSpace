package com.pet_space.servlets;

import com.pet_space.models.essences.StateFriend;
import com.pet_space.models.essences.UserEssence;
import com.pet_space.storages.UserEssenceStorage;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet({"/admin/friend_controller", "/user/friend_controller"})
public class FriendController extends HttpServlet {
    private static final Logger LOG = getLogger(AddPetServlet.class);
    private final UserEssenceStorage users = UserEssenceStorage.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserEssence user = (UserEssence) req.getSession().getAttribute("user");
        UUID friendEssenceId = UUID.fromString(req.getParameter("user_essence_id"));
        if (this.users.setFriendsRequest(user.getUserEssenceId(), friendEssenceId)) {
            user.getRequestedFriendsFrom().put(friendEssenceId, StateFriend.REQUESTED);
            resp.setStatus(HttpServletResponse.SC_OK);
        } else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserEssence user = (UserEssence) req.getSession().getAttribute("user");
        UUID friendEssenceId = UUID.fromString(req.getParameter("user_essence_id"));
        if (this.users.deleteFriendsRequest(user.getUserEssenceId(), friendEssenceId)) {
            resp.setStatus(HttpServletResponse.SC_OK);
            user.getRequestedFriendsFrom().remove(friendEssenceId);
        } else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserEssence user = (UserEssence) req.getSession().getAttribute("user");
        UUID friendEssenceId = UUID.fromString(req.getParameter("user_essence_id"));
        StateFriend stateFriend = Arrays.stream(StateFriend.values())
                .filter(e -> e.name().equalsIgnoreCase(req.getParameter("state_friend")))
                .findFirst().orElse(null);

        UUID essence = null;
        UUID friend = null;
        boolean isFriendsToContains = false;

        if (user.getRequestedFriendsTo().containsKey(friendEssenceId)) {
            essence = user.getUserEssenceId();
            friend = friendEssenceId;
            isFriendsToContains = true;
        } else if (user.getRequestedFriendsFrom().containsKey(friendEssenceId)) {
            essence = friendEssenceId;
            friend = user.getUserEssenceId();
        }

        if (!user.getRequestedFriendsFrom().containsKey(essence)
                && Stream.of(essence, friend, stateFriend).allMatch(Objects::nonNull)
                && this.users.setFriendState(essence, friend, stateFriend)) {
            if (isFriendsToContains) user.getRequestedFriendsTo().put(friendEssenceId, stateFriend);
            else user.getRequestedFriendsFrom().put(friendEssenceId, stateFriend);
            resp.setStatus(HttpServletResponse.SC_OK);
        } else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
