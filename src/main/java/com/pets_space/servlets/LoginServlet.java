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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger LOG = getLogger(LoginServlet.class);
    private final UserEssenceStorage userEssenceStorage = UserEssenceStorage.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("".equals(req.getParameter("logout"))) req.getSession().removeAttribute("user");
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<UserEssence> result = this.userEssenceStorage.findByCredential(req.getParameter("nickname"), req.getParameter("password"));
        if (result.isPresent()) {
            UserEssence user = result.get();
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + session.getAttribute(PathHelper.HOME_PAGE));
        } else {
            this.doGet(req, resp);
        }
    }
}
