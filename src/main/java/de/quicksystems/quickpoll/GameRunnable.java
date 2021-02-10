package de.quicksystems.quickpoll;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Game;

import java.util.Random;

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

public class GameRunnable implements Runnable{

    private JDA jda;

    public GameRunnable(JDA jda) {
        this.jda = jda;
    }

    @Override
    public void run() {
        while (true){
            Game game = getRandomGame();
            while (jda.getPresence().getGame().equals(game)){
                game = getRandomGame();
            }
            jda.getPresence().setGame(game);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Game getRandomGame(){
        int i = new Random().nextInt(8);
        switch (i){
            case 0:
                return Game.playing("auf Fee-Hosting.com");
            case 1:
                return Game.watching("auf Twitter (@QuickSystemsDE)");
            case 2:
                return Game.listening("iLoveRadio Musik");
            case 3:
                return Game.watching("auf QuickSystems.de");
            case 4:
                return Game.playing("auf " + jda.getGuilds().size() + " Guilds");
            case 5:
                return Game.watching("auf sich Discord-List.xyz an");
            case 6:
                return Game.watching("auf Twitter (@realHerrSammy) beim Tweeten zu");
            case 7:
                return Game.listening("Spotify Newspicel zu");
        }
        return Game.watching("auf QuickSystems.de nach");
    }
}
