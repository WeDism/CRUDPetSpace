package com.pet_space.servlets.filters;

import com.google.common.base.Strings;
import com.pet_space.models.essences.UserEssence;
import com.pet_space.servlets.helpers.PathHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@WebFilter(urlPatterns = {"/*"}, initParams = {@WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param")})
public class AuthFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(AuthFilter.class);
    private String setUpCharacterEncoding;

    @Override
    public void init(FilterConfig filterConfig) {
        this.setUpCharacterEncoding = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        this.validateAndSetUpCharacterEncoding(request, response);

        HttpSession session = req.getSession();
        UserEssence user = ((UserEssence) session.getAttribute("user"));
        String path = (String) session.getAttribute(PathHelper.HOME_PAGE);

        if (req.getRequestURI().contains(PathHelper.LOGIN_PATH))
            chain.doFilter(request, response);
        else if (user == null && !req.getRequestURI().contains("web_resources"))
            resp.sendRedirect(req.getContextPath() + PathHelper.LOGIN_PATH);
        else if ((!Strings.isNullOrEmpty(path) && req.getRequestURI().contains(path))
                || req.getRequestURI().contains("web_resources")
                || req.getRequestURI().contains("essence"))
            chain.doFilter(request, response);
        else if (req.getRequestURI().equalsIgnoreCase(req.getContextPath() + "/"))
            resp.sendRedirect(req.getContextPath() + path);
        else
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    private void validateAndSetUpCharacterEncoding(ServletRequest request, ServletResponse response) throws UnsupportedEncodingException {
        final String characterEncoding = request.getCharacterEncoding();
        if (Strings.isNullOrEmpty(characterEncoding) && !this.setUpCharacterEncoding.equalsIgnoreCase(characterEncoding)) {
            request.setCharacterEncoding(this.setUpCharacterEncoding);
            response.setCharacterEncoding(this.setUpCharacterEncoding);
        }
    }

    @Override
    public void destroy() {
        setUpCharacterEncoding = null;
    }
}
