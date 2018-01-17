package com.pets_space.servlets.helpers;

import com.pets_space.models.UserEntry;

public interface PathHelper {
    String LOGIN_PATH = "/login";
    String ROOT_PATH = "/root";
    String USER_PATH = "/user";
    String ADMIN_PATH = "/admin";

    static String createPathForRedirectDependencyRole(UserEntry userEntry) {
        String path = LOGIN_PATH;
        if (userEntry != null) {
            switch (userEntry.getRole()) {
                case ROOT:
                    path = ROOT_PATH;
                    break;
                case USER:
                    path = USER_PATH;
                    break;
                case ADMIN:
                    path = ADMIN_PATH;
                    break;
            }
        }
        return path;
    }
}
