package org.gomadango0113.discord_bot.manager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.gomadango0113.discord_bot.Main;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    public static String getToken() throws IOException {
        return getJson().getAsJsonObject("bot_setting").get("token").getAsString();
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

            return new JsonParser().parse(file_read.toString()).getAsJsonObject();
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
