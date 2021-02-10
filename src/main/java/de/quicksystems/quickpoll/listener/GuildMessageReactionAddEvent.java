package de.quicksystems.quickpoll.listener;

import de.quicksystems.quickpoll.PollSaveUtils;
import de.quicksystems.quickpoll.QuickPollBot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.concurrent.TimeUnit;

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

public class GuildMessageReactionAddEvent extends ListenerAdapter {

    @Override
    public void onGuildMessageReactionAdd(net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent event) {
        if (event.getUser().isBot()) {
            return;
        }
        if (!PollSaveUtils.messages.containsKey(event.getMessageId())){
            return;
        }



        event.getReaction().removeReaction(event.getUser()).queueAfter(3, TimeUnit.SECONDS);

        if (event.getReaction().getReactionEmote().getName().equalsIgnoreCase("\uD83C\uDD70")){
            if (event.getMember().hasPermission(Permission.MESSAGE_MANAGE)){
                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setColor(Color.MAGENTA);

                event.getChannel().getMessageById(event.getMessageId()).queue(message -> {
                    MessageEmbed messageEmbed = message.getEmbeds().get(0);
                    embedBuilder.setTitle("Auswertung der Umfrage: " + messageEmbed.getTitle());
                });

                embedBuilder.appendDescription("Comming soon!");

                event.getChannel().sendMessage(embedBuilder.build()).queue(message -> message.delete().queueAfter(10, TimeUnit.SECONDS));
                event.getReaction().removeReaction(event.getUser()).queueAfter(5, TimeUnit.SECONDS);
            }else {
                event.getReaction().removeReaction(event.getUser()).queueAfter(5, TimeUnit.SECONDS);
                QuickPollBot.sendMessageAndDelete(event.getChannel(), new EmbedBuilder()
                        .setColor(Color.RED)
                        .setTitle(":warning:  Sorry " + event.getMember().getNickname() + "!")
                        .setDescription("You don't have the permissions to use this!")
                        .setAuthor("QuickPoll","https://discordapp.com/oauth2/authorize?client_id=511662393244516363&scope=bot&permissions=1141066816", "https://cdn.discordapp.com/attachments/507605960307769365/511919997153574923/11.11.18.png")
                        .build());

            }
        }

        if (PollSaveUtils.messages.get(event.getMessageId()).contains(event.getMember())){
            event.getReaction().removeReaction(event.getUser()).queueAfter(5, TimeUnit.SECONDS);
            QuickPollBot.sendMessageAndDelete(event.getChannel(), new EmbedBuilder()
                    .setColor(Color.RED)
                    .setTitle("Entscheide dich für ein Emote! | Please chose one emote!")
                    .setDescription("Du musst dich für ein Emote entscheiden! You can only chose one emote!")
                    .setAuthor("QuickPoll","https://discordapp.com/oauth2/authorize?client_id=511662393244516363&scope=bot&permissions=1141066816", "https://cdn.discordapp.com/attachments/507605960307769365/511919997153574923/11.11.18.png")
                    .build());
            return;

        }
        PollSaveUtils.messages.get(event.getMessageId()).add(event.getMember());
    }

}
