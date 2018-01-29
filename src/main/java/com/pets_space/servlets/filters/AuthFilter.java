package com.pets_space.servlets.filters;

import com.google.common.base.Strings;
import com.pets_space.models.essences.UserEssence;
import com.pets_space.servlets.helpers.PathHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class AuthFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(true);
        UserEssence user = ((UserEssence) session.getAttribute("user"));
        session.setAttribute("homePage", PathHelper.createPathForRedirectDependencyRole(user));
        String path = PathHelper.createPathForRedirectDependencyRole(user);

        if (req.getRequestURI().contains(PathHelper.LOGIN_PATH))
            chain.doFilter(request, response);
        else if (user == null)
            resp.sendRedirect(req.getContextPath() + path);
        else if ((!Strings.isNullOrEmpty(path) && req.getRequestURI().contains(path))
                || req.getRequestURI().contains("web_resources")
                || req.getRequestURI().contains("essence"))
            chain.doFilter(request, response);
        else if (req.getRequestURI().equalsIgnoreCase(req.getContextPath() + "/"))
            resp.sendRedirect(req.getContextPath() + path);
        else
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    @Override
    public void destroy() {

    }
}
