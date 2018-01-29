package com.pets_space.servlets;

import com.pets_space.models.essences.UserEssence;
import com.pets_space.servlets.helpers.PathHelper;
import com.pets_space.storages.UserEssenceStorage;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet("/essence")
public class EssenceServlet extends HttpServlet {
    private static final Logger LOG = getLogger(AddPetServlet.class);
    private final UserEssenceStorage users = UserEssenceStorage.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nickname = req.getParameter("nickname");
        HttpSession session = req.getSession();
        UserEssence user = (UserEssence) session.getAttribute("user");
        Optional<UserEssence> essence = this.users.findByNickname(nickname);
        if (!nickname.equalsIgnoreCase(user.getNickname()) && essence.isPresent()) {
            session.setAttribute("foundEssence", essence.get());
            req.getRequestDispatcher("WEB-INF/views/essence.jsp").forward(req, resp);
        } else resp.sendRedirect(req.getContextPath() + PathHelper.createPathForRedirectDependencyRole(user));
    }
}
