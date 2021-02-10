package de.quicksystems.quickpoll.listener;

import de.quicksystems.quickpoll.PollSaveUtils;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/******************************************************************************
 * Urheberrechtshinweis                                                       *
 * Copyright © QuickSystems 2018                                              *
 *                                                                            *
 * Alle Inhalte dieses Quelltextes sind urheberrechtlich geschützt.           *
 * Das Urheberrecht liegt, soweit nicht ausdrücklich anders gekennzeichnet,   *
 * bei QuickSystems. Alle Rechte vorbehalten.                                 *
 *                                                                            *
 * Jede Art der Vervielfältigung, Verbreitung, Vermietung, Verleihung,        *
 * öffentliche Zugänglichmachung oder andere Nutzung                          *
 * bedarf der ausdrücklichen, schriftlichen Zustimmung von QuickSystems       *
 ******************************************************************************/

public class MessageReactionRemoveEvent extends ListenerAdapter {

    @Override
    public void onMessageReactionRemove(net.dv8tion.jda.core.events.message.react.MessageReactionRemoveEvent event) {
        if (event.getUser().isBot()) {
            return;
        }
        if (!PollSaveUtils.messages.containsKey(event.getMessageId())){
            return;
        }
        PollSaveUtils.messages.get(event.getMessageId()).remove(event.getMember());
    }
}

