package com.pets_space.servlets;

import com.pets_space.models.GenusPet;
import com.pets_space.storages.GenusPetStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/admin/add_genus_pet", "/root/add_genus_pet"})
public class AddGenusPetServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(AddGenusPetServlet.class);
    private final GenusPetStorage genusPetStorage = GenusPetStorage.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("genusPetIsAdded");
        req.getRequestDispatcher("/WEB-INF/views/addGenusPet.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("genusPetIsAdded", this.genusPetStorage.add(new GenusPet(req.getParameter("name"))));
        req.getRequestDispatcher("/WEB-INF/views/addGenusPet.jsp").forward(req, resp);
    }
}
