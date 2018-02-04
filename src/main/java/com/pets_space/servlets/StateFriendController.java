package com.pets_space.servlets;


import com.pets_space.models.essences.StateFriend;
import com.pets_space.models.essences.UserEssence;
import com.pets_space.storages.UserEssenceStorage;
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

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet({"/admin/state_friend_controller", "/user/state_friend_controller"})
public class StateFriendController extends HttpServlet {
    private static final Logger LOG = getLogger(StateFriendController.class);
    private final UserEssenceStorage userEssenceStorage = UserEssenceStorage.getInstance();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserEssence user = (UserEssence) req.getSession().getAttribute("user");
        StateFriend stateFriend = Arrays.stream(StateFriend.values())
                .filter(e -> e.name().equalsIgnoreCase(req.getParameter("state_friend")))
                .findFirst().orElse(null);
        UUID userFriendId = UUID.fromString(req.getParameter("user_friend_id"));
        if (Objects.nonNull(stateFriend) && this.userEssenceStorage.setFriendState(user.getUserEssenceId(), userFriendId, stateFriend)) {
            user.getRequestedFriendsTo().put(userFriendId, stateFriend);
            resp.setStatus(HttpServletResponse.SC_OK);
        } else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);


    }
}
