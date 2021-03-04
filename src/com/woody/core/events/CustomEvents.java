package com.woody.core.events;

import com.woody.core.Config;
import com.woody.core.events.custom.*;
import com.woody.core.util.StringManager;

public class CustomEvents {
    
    public void onBadWords(ChatBadWordEvent e)
    {
        if(Config.censor)
        {
            e.setCancelled(true);
            e.getSender().sendMessage(StringManager.Colorize("&c&lNie wolno używać takich słów!"));
            // TODO: Swear Penalty
        }
    }
}
