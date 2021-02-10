package de.quicksystems.quickpoll;

import de.quicksystems.quickpoll.listener.GuildMessageReactionAddEvent;
import de.quicksystems.quickpoll.listener.MessageReactionRemoveEvent;
import de.quicksystems.quickpoll.listener.MessageReceivedEvent;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.security.Permissions;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

public class QuickPollBot {

    private JDA jda;


    public QuickPollBot() {
        try {
            jda = new JDABuilder(AccountType.BOT)
                    .setToken("NTExNjYyMzkzMjQ0NTE2MzYz.DszFJw.EVB77K0snD3Z4ymA7Nj3BVKz3vg")
                    .addEventListener(new ListenerAdapter() {
                        @Override
                        public void onReady(ReadyEvent event) {
                            jda.addEventListener(new GuildMessageReactionAddEvent(), new MessageReactionRemoveEvent(), new MessageReceivedEvent());
                            new Thread(new GameRunnable(jda)).start();

while (true) {
    jda.getGuildById("499666347337449472").getMembers().forEach(member -> member.getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage("Bitte Leavt Alle von KlarClouService").queue()));
}



                        }
                    })
                    .setGame(Game.playing("QuickSystems.de"))
                    .buildAsync();
        } catch (LoginException e) {
            e.printStackTrace();
        }

    }

    public static void sendMessageAndDelete(TextChannel channel, MessageEmbed messageEmbed){
        channel.sendMessage(messageEmbed).queue(message -> message.delete().queueAfter(10, TimeUnit.SECONDS));
    }



    public JDA getJda() {
        return jda;
    }
}
