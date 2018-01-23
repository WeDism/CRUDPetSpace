package com.pets_space.servlets;

import com.pets_space.models.UserEssence;
import com.pets_space.servlets.helpers.RootHelper;
import com.pets_space.storages.UserEssenceStorage;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet("/root")
public class RootServlet extends HttpServlet {
    private static final Logger LOG = getLogger(RootServlet.class);
    private final UserEssenceStorage users = UserEssenceStorage.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", this.users.getAll());
        req.setAttribute("roles", UserEssence.Role.values());
        req.getRequestDispatcher("/WEB-INF/views/root.jsp").forward(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        Optional<UUID> user = Optional.of(UUID.fromString(req.getParameter("user")));
        if (user.isPresent()) {
            this.users.delete(user.get());
            resp.setStatus(HttpServletResponse.SC_OK);
        } else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<UUID> user = RootHelper.validateRequest(req);
        Optional<UserEssence.Role> role =
                Arrays.stream(UserEssence.Role.values()).filter((r) -> req.getParameter("role").equalsIgnoreCase(r.name())).findFirst();
        if (user.isPresent() && role.isPresent()) {
            UserEssence userEssence = this.users.findById(user.get()).get();
            this.users.updateRole(userEssence.setRole(role.get()));
            resp.setStatus(HttpServletResponse.SC_OK);
        } else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
