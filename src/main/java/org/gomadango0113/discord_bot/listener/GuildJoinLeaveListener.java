package org.gomadango0113.discord_bot.listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.gomadango0113.discord_bot.manager.ConfigManager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

public class GuildJoinLeaveListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Guild guild = event.getGuild();
        Member member = event.getMember();
        User user = event.getUser();
        JDA jda = event.getJDA();

        try {
            if (ConfigManager.getJoinLeaveMessageChannelId(guild.getId()) != null) {
                String id = ConfigManager.getJoinLeaveMessageChannelId(guild.getId());
                TextChannel send_channel = jda.getTextChannelById(id);

                OffsetDateTime created = user.getTimeCreated();
                ZoneId zone = ZoneId.systemDefault();
                ZonedDateTime zonedDateTime = ZonedDateTime.of(created.toLocalDateTime(), zone);
                Instant instant = zonedDateTime.toInstant();
                String created_string = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date.from(instant));

                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle(user.getName() + "が参加しました");
                embed.addField("アカウント作成時間", created_string, true);
                embed.setThumbnail(member.getAvatarUrl());
                embed.setColor(member.getColor());

                embed.setTimestamp(LocalDateTime.now().atZone(ZoneId.systemDefault()));

                send_channel.sendMessageEmbeds(embed.build()).queue();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        Guild guild = event.getGuild();
        User user = event.getUser();
        JDA jda = event.getJDA();

        try {
            if (ConfigManager.getJoinLeaveMessageChannelId(guild.getId()) != null) {
                String id = ConfigManager.getJoinLeaveMessageChannelId(guild.getId());
                TextChannel send_channel = jda.getTextChannelById(id);

                OffsetDateTime created = user.getTimeCreated();
                ZoneId zone = ZoneId.systemDefault();
                ZonedDateTime zonedDateTime = ZonedDateTime.of(created.toLocalDateTime(), zone);
                Instant instant = zonedDateTime.toInstant();
                String created_string = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date.from(instant));

                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle(user.getName() + "が抜けました。");
                embed.addField("アカウント作成時間", created_string, true);

                embed.setTimestamp(LocalDateTime.now().atZone(ZoneId.systemDefault()));

                send_channel.sendMessageEmbeds(embed.build()).queue();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
