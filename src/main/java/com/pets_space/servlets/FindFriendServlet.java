package com.pets_space.servlets;

import com.pets_space.models.EssenceForSearchFriend;
import com.pets_space.models.UserEssence;
import com.pets_space.storages.UserEssenceStorage;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import static com.pets_space.models.UserEssence.Role.ADMIN;
import static com.pets_space.models.UserEssence.Role.ROOT;
import static com.pets_space.models.UserEssence.Role.USER;
import static org.slf4j.LoggerFactory.getLogger;

@WebServlet({"/admin/find_friend", "/user/find_friend"})
public class FindFriendServlet extends HttpServlet {
    private static final Logger LOG = getLogger(AddPetServlet.class);
    private final UserEssenceStorage users = UserEssenceStorage.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/findFriends.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<Set<UserEssence>> friends = this.users.findFriends(new EssenceForSearchFriend(req));
        req.getSession().setAttribute("friends", friends.get());
        UserEssence user = (UserEssence) req.getSession().getAttribute("user");
        if (user.getRole() == USER)
            resp.sendRedirect(req.getContextPath() + "/user/find_friend");
        else
            resp.sendRedirect(req.getContextPath() + "/admin/find_friend");

    }
}
