package com.pets_space.jstl_tags;

import com.pets_space.models.UserEssence;
import org.slf4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

public class MapContainsTag extends TagSupport {
    private static Logger LOG = getLogger(MapContainsTag.class);
    private Map<UUID, UserEssence> essenceMap;
    private UUID userEssence;

    public void setEssenceMap(Map<UUID, UserEssence> essenceMap) {
        this.essenceMap = essenceMap;
    }

    public void setUserEssence(UUID userEssence) {
        this.userEssence = userEssence;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            if (this.essenceMap.containsKey(userEssence)) {
                this.pageContext.getOut().write("checked=\"true\"");
            }
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        return SKIP_BODY;
    }
}
