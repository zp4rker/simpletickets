package co.zpdev.bots.ethereal.listeners;

import co.zpdev.bots.ethereal.Ethereal;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.SubscribeEvent;

import java.awt.*;

public class MemberJoin {

    @SubscribeEvent
    public void onJoin(GuildMemberJoinEvent event) {
        String name = event.getUser().getName() + "#" + event.getUser().getDiscriminator();
        TextChannel logs = event.getGuild().getTextChannelById(Ethereal.JOIN);
        MessageEmbed embed = new EmbedBuilder().setColor(Color.decode("#7254BF"))
                .setAuthor(name + " just joined!", null, event.getUser().getEffectiveAvatarUrl()).build();
        logs.sendMessage(embed).queue();
    }

}
