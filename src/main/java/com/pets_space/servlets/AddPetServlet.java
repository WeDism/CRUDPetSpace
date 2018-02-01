package com.pets_space.servlets;

import com.google.common.base.Strings;
import com.pets_space.models.Pet;
import com.pets_space.models.SpeciesPet;
import com.pets_space.models.essences.UserEssence;
import com.pets_space.servlets.helpers.PathHelper;
import com.pets_space.storages.PetStorage;
import com.pets_space.storages.SpeciesPetStorage;
import com.pets_space.storages.UserEssenceStorage;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet({"/user/add_pet", "/admin/add_pet"})
public class AddPetServlet extends HttpServlet {
    private static final Logger LOG = getLogger(AddPetServlet.class);
    private final SpeciesPetStorage species = SpeciesPetStorage.getInstance();
    private final PetStorage pets = PetStorage.getInstance();
    private final UserEssenceStorage users = UserEssenceStorage.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("species", this.species.getAll());
        req.getRequestDispatcher("/WEB-INF/views/addPet.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        Double weight = Double.valueOf(req.getParameter("weight"));
        LocalDateTime birthday = LocalDateTime.parse(req.getParameter("birthday"));
        UserEssence user = (UserEssence) req.getSession().getAttribute("user");
        String species = req.getParameter("species");
        SpeciesPet speciesPet = new SpeciesPet(species);
        if (!(Strings.isNullOrEmpty(name) && Strings.isNullOrEmpty(species)) && this.species.validateSpecies(speciesPet)) {
            Pet pet = new Pet();
            pet.setName(name);
            pet.setPetId(UUID.randomUUID());
            pet.setSpecies(speciesPet);
            pet.setWeight(weight);
            pet.setBirthday(birthday);
            user.setPet(pet);
            this.users.update(user);
            resp.sendRedirect(req.getContextPath() + req.getSession().getAttribute(PathHelper.HOME_PAGE));
        } else resp.sendRedirect("/WEB-INF/views/errors/error404.jsp");
    }
}
