package com.pet_space.jstl_tags.functions;

import com.pet_space.models.essences.LiteEssence;
import com.pet_space.models.essences.UserEssence;
import com.pet_space.storages.LiteEssenceStorage;
import org.slf4j.Logger;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

public class GetFriendsFunction {
    private static final Logger LOG = getLogger(GetFriendsFunction.class);

    public static Set<LiteEssence> getFriends(UserEssence userEssence) {
        Set<UUID> friendHashMap = new HashSet<>();
        friendHashMap.addAll(userEssence.getRequestedFriendsTo().keySet());
        friendHashMap.addAll(userEssence.getRequestedFriendsFrom().keySet());
        return LiteEssenceStorage.getInstance().findByIds(friendHashMap).orElse(null);
    }
}
