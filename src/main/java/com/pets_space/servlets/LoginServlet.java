package com.pets_space.servlets;

import com.pets_space.models.essences.UserEssence;
import com.pets_space.servlets.helpers.PathHelper;
import com.pets_space.storages.UserEssenceStorage;
import org.slf4j.Logger;

import javax.servlet.ServletConfig;
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
    public void init(ServletConfig config) throws ServletException {
        String contextPath = config.getServletContext().getContextPath();
        if (config.getServletContext().getVirtualServerName().contains("localhost")) {
            config.getServletContext().setAttribute("bootstrap.css", contextPath + "/web_resources/css/bootstrap.css");
            config.getServletContext().setAttribute("bootstrap-grid.css", contextPath + "/web_resources/css/bootstrap-grid.css");
            config.getServletContext().setAttribute("bootstrap-reboot.css", contextPath + "/web_resources/css/bootstrap-reboot.css");
            config.getServletContext().setAttribute("jquery", contextPath + "/web_resources/js/jquery-3.3.1.js");
            config.getServletContext().setAttribute("popper", contextPath + "/web_resources/js/popper.js");
            config.getServletContext().setAttribute("bootstrap.js", contextPath + "/web_resources/js/bootstrap.js");
        } else {
            config.getServletContext().setAttribute("bootstrap.css", contextPath + "/web_resources/css/bootstrap.min.css");
            config.getServletContext().setAttribute("bootstrap-grid.css", contextPath + "/web_resources/css/bootstrap-grid.min.css");
            config.getServletContext().setAttribute("bootstrap-reboot.css", contextPath + "/web_resources/css/bootstrap-reboot.min.css");
            config.getServletContext().setAttribute("jquery", contextPath + "/web_resources/js/jquery-3.3.1.min.js");
            config.getServletContext().setAttribute("popper", contextPath + "/web_resources/js/popper.min.js");
            config.getServletContext().setAttribute("bootstrap.js", contextPath + "/web_resources/js/bootstrap.min.js");
        }
    }

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
            final String path = PathHelper.createPathForRedirectDependencyRole(user);
            session.setAttribute(PathHelper.HOME_PAGE, path);
            resp.sendRedirect(req.getContextPath() + path);
        } else {
            this.doGet(req, resp);
        }
    }
}
