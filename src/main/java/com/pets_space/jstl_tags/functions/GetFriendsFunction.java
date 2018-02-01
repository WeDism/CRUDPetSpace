package com.pets_space.jstl_tags.functions;

import com.pets_space.models.essences.LiteEssence;
import com.pets_space.models.essences.StateFriend;
import com.pets_space.storages.LiteEssenceStorage;
import org.slf4j.Logger;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

public class GetFriendsFunction {
    private static Logger LOG = getLogger(GetFriendsFunction.class);

    public static Set<LiteEssence> getFriends(Map<UUID, StateFriend> friends) {
        return LiteEssenceStorage.getInstance().findByIds(friends.keySet()).orElse(null);
    }
}
