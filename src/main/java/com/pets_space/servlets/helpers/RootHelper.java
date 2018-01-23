package com.pets_space.servlets.helpers;

import com.pets_space.models.UserEssence;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

public interface RootHelper {
    static Optional<UUID> validateRequest(HttpServletRequest req){
        Optional<UUID> user = Optional.of(UUID.fromString(req.getParameter("user")));
        if (user.isPresent() && !((UserEssence) req.getSession().getAttribute("user")).getUserEssenceId().equals(user.get()))
            return user;
        return Optional.empty();
        }
}
