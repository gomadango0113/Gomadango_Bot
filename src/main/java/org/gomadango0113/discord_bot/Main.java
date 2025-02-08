package org.gomadango0113.discord_bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.gomadango0113.discord_bot.command.MusicCommand;
import org.gomadango0113.discord_bot.listener.GuildJoinLeaveListener;
import org.gomadango0113.discord_bot.manager.ConfigManager;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

public class Main extends ListenerAdapter implements EventListener {

    private static JDA jda;

    public static void main(String[] strings) throws InterruptedException, IOException {

        if (jda == null) {
            jda = JDABuilder.createDefault(ConfigManager.getToken())
                    .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT)
                    .addEventListeners(new Main())
                    .addEventListeners(new MusicCommand())
                    .addEventListeners(new GuildJoinLeaveListener())
                    .build();

            jda.updateCommands()
                    .addCommands(Commands.slash("play", "音楽を再生するコマンド")
                            .addOption(OptionType.STRING, "url", "再生したい音楽のURL（もしくはキーワード）"))
                    .addCommands(Commands.slash("skip", "現在再生している音楽をスキップ"))
                    .addCommands(Commands.slash("play-info", "現在再生している音楽"))
                    .queue();

            jda.awaitReady();
        }
    }

    public static JDA getJda() {
        return jda;
    }
}
