package com.pets_space.servlets;

import com.pets_space.models.UserEntry;
import com.pets_space.servlets.helpers.PathHelper;
import com.pets_space.storages.UserEntryStorage;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger LOG = getLogger(LoginServlet.class);
    private final UserEntryStorage userEntryStorage = UserEntryStorage.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("logout") != null) req.getSession().removeAttribute("user");
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<UserEntry> result = this.userEntryStorage.findByCredential(req.getParameter("nickname"), req.getParameter("password"));
        if (result.isPresent()) {
            UserEntry user = result.get();
            String path = PathHelper.createPathForRedirectDependencyRole(user);
            req.getSession().setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + path);
        } else {
            this.doGet(req, resp);
        }
    }
}
