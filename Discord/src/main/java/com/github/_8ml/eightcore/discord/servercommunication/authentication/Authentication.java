package com.github._8ml.eightcore.discord.servercommunication.authentication;
/*
Created by @8ML (https://github.com/8ML) on September 13 2021
*/

import com.github._8ml.eightcore.discord.Main;
import com.github._8ml.eightcore.discordcommapi.packet.packets.DiscordCommandPacket;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Authentication extends ListenerAdapter {

    private final String userID;
    private MessageChannel privateMessageChannel;

    public Authentication(String userID) {

        this.userID = userID;

    }

    public void verifyRequest() {

        for (Guild guild : Main.jda.getGuilds()) {
            Member member = guild.getMemberById(this.userID);
            if (member != null) {

                MessageEmbed content = new EmbedBuilder().setTitle("AUTHENTICATION")
                        .setDescription("You are currently trying to log in on the server.\nPress confirm if this is you." +
                                "\nIf this is not you, press deny and the player will be temporarily banned" +
                                "\nuntil you change your password!").build();

                member.getUser().openPrivateChannel()
                        .flatMap(channel -> channel.sendMessage(content).flatMap(message -> {

                            this.privateMessageChannel = channel;

                            message.addReaction("U+2713").queue();
                            message.addReaction("U+274C").queue();
                            return null;
                        })).queue();
                break;
            }
        }

    }

    private void confirm() {

        DiscordCommandPacket packet = new DiscordCommandPacket();
        packet.send(new String[]{"CONFIRMED", this.userID});

    }

    private void deny() {

        DiscordCommandPacket packet = new DiscordCommandPacket();
        packet.send(new String[]{"DENY", this.userID});

    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent e) {
        if (privateMessageChannel == null) return;
        if (e.getChannel().equals(this.privateMessageChannel)) {
            switch (e.getReaction().getReactionEmote().getName()) {
                case "U+2713":
                    confirm();
                    e.getChannel().deleteMessageById(e.getMessageId()).queue();
                    break;
                case "U+274C":
                    deny();
                    e.getChannel().deleteMessageById(e.getMessageId()).queue();
                    break;
            }
        }
    }
}
