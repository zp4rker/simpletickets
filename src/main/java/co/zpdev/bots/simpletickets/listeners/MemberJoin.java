package co.zpdev.bots.simpletickets.listeners;

import co.zpdev.bots.simpletickets.SimpleTickets;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.SubscribeEvent;

public class MemberJoin {

    @SubscribeEvent
    public void onJoin(GuildMemberJoinEvent event) {
        String name = event.getUser().getName() + "#" + event.getUser().getDiscriminator();
        TextChannel logs = event.getGuild().getTextChannelById(SimpleTickets.join);
        MessageEmbed embed = new EmbedBuilder().setColor(SimpleTickets.embed)
                .setAuthor(name + " just joined!", null, event.getUser().getEffectiveAvatarUrl()).build();
        logs.sendMessage(embed).queue();
    }

}
