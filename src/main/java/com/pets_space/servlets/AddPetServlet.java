package com.pets_space.servlets;

import com.google.common.base.Strings;
import com.pets_space.models.Pet;
import com.pets_space.models.GenusPet;
import com.pets_space.models.essences.UserEssence;
import com.pets_space.servlets.helpers.PathHelper;
import com.pets_space.storages.PetStorage;
import com.pets_space.storages.GenusPetStorage;
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
    private final GenusPetStorage genus = GenusPetStorage.getInstance();
    private final PetStorage pets = PetStorage.getInstance();
    private final UserEssenceStorage users = UserEssenceStorage.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("genusPet", this.genus.getAll());
        req.getRequestDispatcher("/WEB-INF/views/addPet.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        Double weight = Double.valueOf(req.getParameter("weight"));
        LocalDateTime birthday = LocalDateTime.parse(req.getParameter("birthday"));
        UserEssence user = (UserEssence) req.getSession().getAttribute("user");
        String genusPetParameter = req.getParameter("genusPet");
        GenusPet genusPet = new GenusPet(genusPetParameter);
        if (!(Strings.isNullOrEmpty(name) && Strings.isNullOrEmpty(genusPetParameter)) && this.genus.validateGenus(genusPet)) {
            user.setPet(Pet.builder()
                    .petId(UUID.randomUUID())
                    .name(name)
                    .owner(user.getUserEssenceId())
                    .genusPet(genusPet)
                    .weight(weight)
                    .birthday(birthday)
                    .build());
            this.users.update(user);
            resp.sendRedirect(req.getContextPath() + req.getSession().getAttribute(PathHelper.HOME_PAGE));
        } else resp.sendRedirect("/WEB-INF/views/errors/error404.jsp");
    }
}
