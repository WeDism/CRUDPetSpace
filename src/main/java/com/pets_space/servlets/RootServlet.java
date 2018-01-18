package com.pets_space.servlets;

import com.pets_space.models.UserEntry;
import com.pets_space.storages.UserEntryStorage;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet("/root")
public class RootServlet extends HttpServlet {
    private static final Logger LOG = getLogger(RootServlet.class);
    private final UserEntryStorage users = UserEntryStorage.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", this.users.getAll());
        req.getRequestDispatcher("/WEB-INF/views/root.jsp").forward(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        Optional<UUID> user = Optional.of(UUID.fromString(req.getParameter("user")));
        if (user.isPresent() && !((UserEntry) req.getSession().getAttribute("user")).getUserEntryId().equals(user.get())) {
            this.users.delete(user.get());
            resp.setStatus(HttpServletResponse.SC_OK);
        } else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
