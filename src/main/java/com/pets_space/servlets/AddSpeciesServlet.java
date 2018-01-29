package com.pets_space.servlets;

import com.pets_space.models.SpeciesPet;
import com.pets_space.storages.SpeciesPetStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet({"/admin/add_species", "/root/add_species"})
public class AddSpeciesServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(AddSpeciesServlet.class);
    private final SpeciesPetStorage species = SpeciesPetStorage.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("speciesPetIsAdded");
        req.getRequestDispatcher("/WEB-INF/views/addSpecies.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<SpeciesPet> speciesPet = this.species.add(new SpeciesPet(req.getParameter("name")));
        req.getSession().setAttribute("speciesPetIsAdded", speciesPet);
        req.getRequestDispatcher("/WEB-INF/views/addSpecies.jsp").forward(req, resp);
    }
}
