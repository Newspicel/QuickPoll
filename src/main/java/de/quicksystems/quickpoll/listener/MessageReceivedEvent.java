package de.quicksystems.quickpoll.listener;

import de.quicksystems.quickpoll.EmojiUtils;
import de.quicksystems.quickpoll.PollSaveUtils;
import de.quicksystems.quickpoll.QuickPollBot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

public class MessageReceivedEvent extends ListenerAdapter {
    @Override
    public void onMessageReceived(net.dv8tion.jda.core.events.message.MessageReceivedEvent event) {
        if(event.getGuild().getId().equalsIgnoreCase("520683722002137098")){
            if (event.getMessage().getContentRaw().equalsIgnoreCase(".guilds")){

                for (Guild guild : event.getJDA().getGuilds()) {
                    List list = new ArrayList();
                    try {
                        guild.getInvites().queue(list::addAll);

                    }catch (Exception ex){
                        list.add("Keine Rechte");
                    }
                    event.getMessage().getChannel().sendMessage("ID: " + guild.getId() + " | Name: " + guild.getName() + " | Auf dem Guild sind " + guild.getMembers().size() + "Member | InviteLinks : " + list.toString()).queue();
                }
                event.getMessage().getChannel().sendMessage("Insgeamt ist der Bot auf " + event.getJDA().getGuilds().size() + " Guilds!").queue();
            }
        }
        if (!event.getMessage().getContentRaw().startsWith(".quickpoll")){
            return;
        }
        String[] args1 = event.getMessage().getContentRaw().split(" ");

        String[] args = new String[args1.length-1];

        for (int i = 1; i < args1.length; i++) {
            args[i-1] = args1[i];
        }

        event.getMessage().delete().queueAfter(3, TimeUnit.SECONDS);
        if (event.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            if (args.length >= 4 && args.length < 22) {
                ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(args).subList(2, args.length));

                EmbedBuilder embedBuilder =
                        new EmbedBuilder()
                                .setTitle(args[0].replaceAll(",", " "))
                                .setColor(Color.MAGENTA)
                                .setAuthor("Invite to your Server","https://discordapp.com/oauth2/authorize?client_id=511662393244516363&scope=bot&permissions=1141066816", "https://cdn.discordapp.com/attachments/507605960307769365/511919997153574923/11.11.18.png")
                                .setDescription(args[1].replaceAll(",", " "));
                for (int i = 0; i < arrayList.size(); i++) {
                    embedBuilder.addField(new MessageEmbed.Field(EmojiUtils.intInEmojiString(i)+ arrayList.get(i).replaceAll(",", " "),"", true));
                }

                embedBuilder.addField(new MessageEmbed.Field(":a: Auswertung \n", "", true));

                event.getChannel().sendMessage(embedBuilder.build()).queue(message -> {
                    PollSaveUtils.messages.put(message.getId(), new ArrayList<>());

                    for (int i = 0; i < arrayList.size(); i++) {
                        message.addReaction(EmojiUtils.intInEmojiStringCode(i)).queue();
                    }
                    message.addReaction("\uD83C\uDD70").queue();
                });


            } else {
                QuickPollBot.sendMessageAndDelete(event.getTextChannel(), new EmbedBuilder()
                        .setColor(Color.RED)
                        .setTitle((event.getMessage().getContentRaw()))
                        .setDescription("Bitte Benutze .quickpoll <Frage> <Description> <1. Antwort> <2. Antwort> ... und wenn du Lücken benutzten willst bitte schreib ein Komma!")
                        .setAuthor("QuickPoll","https://discordapp.com/oauth2/authorize?client_id=511662393244516363&scope=bot&permissions=1141066816", "https://cdn.discordapp.com/attachments/507605960307769365/511919997153574923/11.11.18.png")
                        .build());
            }
        } else {
            QuickPollBot.sendMessageAndDelete(event.getTextChannel(), new EmbedBuilder()
                    .setColor(Color.RED)
                    .setTitle(":warning:  Sorry " + event.getMember().getEffectiveName() + "!")
                    .setDescription("You don't have the permissions to use this!")
                    .setAuthor("QuickPoll","https://discordapp.com/oauth2/authorize?client_id=511662393244516363&scope=bot&permissions=1141066816", "https://cdn.discordapp.com/attachments/507605960307769365/511919997153574923/11.11.18.png")
                    .build());
        }
    }

}
