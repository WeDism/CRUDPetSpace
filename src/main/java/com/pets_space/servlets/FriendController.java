package com.pets_space.servlets;

import com.pets_space.models.UserEssence;
import com.pets_space.storages.UserEssenceStorage;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet({"/admin/friend_controller", "/user/friend_controller"})
public class FriendController extends HttpServlet {
    private static final Logger LOG = getLogger(AddPetServlet.class);
    private final UserEssenceStorage users = UserEssenceStorage.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserEssence user = (UserEssence) req.getSession().getAttribute("user");
        UUID friendEssenceId = UUID.fromString(req.getParameter("user_essence_id"));
        if (this.users.setFriendsRequest(user, friendEssenceId)) resp.setStatus(HttpServletResponse.SC_OK);
        else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserEssence user = (UserEssence) req.getSession().getAttribute("user");
        UUID friendEssenceId = UUID.fromString(req.getParameter("user_essence_id"));
        if (this.users.deleteFriendsRequest(user, friendEssenceId)) resp.setStatus(HttpServletResponse.SC_OK);
        else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }
}
