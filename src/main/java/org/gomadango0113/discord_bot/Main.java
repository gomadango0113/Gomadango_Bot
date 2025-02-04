package org.gomadango0113.discord_bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
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
                    .build();
            jda.awaitReady();

            jda.updateCommands()
                    .queue();
        }
    }

    public static JDA getJda() {
        return jda;
    }
}
