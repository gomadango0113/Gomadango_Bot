package org.gomadango0113.discord_bot.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.gomadango0113.discord_bot.Main;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class ConfigManager {

    private static final JDA jda;
    private static final Gson gson;

    static {
        jda = Main.getJda();
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public static String getJoinLeaveMessageChannelId(String guildid) throws IOException {
        JsonObject json = getJson();
        if (json.has("guild_setting")) {
            JsonObject guild_settings = json.getAsJsonObject("guild_setting");
            if (guild_settings.has(guildid)) {
                JsonObject guild_obj = guild_settings.getAsJsonObject(guildid);
                if (guild_obj.has("join-leave_channel")) {
                    return guild_obj.get("join-leave_channel").getAsString();
                }
            }
        }

        return null;
    }

    public static String getToken() throws IOException {
        return getJson().getAsJsonObject("bot_setting").get("token").getAsString();
    }

    public static void writeFile(String date) {
        try (BufferedWriter write = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(getFile().toPath()), StandardCharsets.UTF_8))) {
            write.write(date);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static JsonObject getJson() throws IOException {
        if (getFile().exists()) {
            StringBuilder file_read = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(getFile().toPath()), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    file_read.append(line);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return gson.fromJson(file_read.toString(), JsonObject.class);
        }
        else {
            throw new NullPointerException("ファイルが存在しません");
        }
    }

    private static File getFile() throws IOException {
        File file = new File("bot_config.json");

        if (!file.exists()) {
            file.createNewFile();

            try (BufferedWriter write = new BufferedWriter(new FileWriter(file))) {
                JsonObject bot_setting_obj = new JsonObject();
                bot_setting_obj.addProperty("token", "");

                JsonObject json = new JsonObject();
                json.add("bot_setting", bot_setting_obj);

                write.write(json.toString());
            }

            System.out.print("Botの設定ファイルを作成しました。\n");
        }
        return file;
    }

}
