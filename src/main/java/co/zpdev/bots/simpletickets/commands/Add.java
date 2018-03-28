package co.zpdev.bots.simpletickets.commands;

import co.zpdev.bots.core.command.Command;
import co.zpdev.bots.simpletickets.SimpleTickets;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;

import java.time.Instant;

public class Add {

    @Command(aliases = "add")
    public void onCommand(Message message) {
        if (message.getMember().getRoles().stream().noneMatch(r -> SimpleTickets.roles.contains(r.getIdLong()))) return;
        if (message.getMentionedMembers().size() != 1 && message.getMentionedChannels().size() != 1) return;
        if (!message.getMentionedChannels().get(0).getName().startsWith("ticket-")) return;

        message.delete().queue();

        TextChannel ticket = message.getMentionedChannels().get(0);
        ticket.createPermissionOverride(message.getMentionedMembers().get(0)).setAllow(3072).complete();

        String name = message.getMentionedMembers().get(0).getEffectiveName();
        MessageEmbed log = new EmbedBuilder().setColor(SimpleTickets.embed)
                .setAuthor("Member Added")
                .setDescription("**" + message.getMember().getEffectiveName() + "** added **" + name + "** to #"
                        + ticket.getName())
                .setTimestamp(Instant.now()).build();
        message.getGuild().getTextChannelById(SimpleTickets.logs).sendMessage(log).queue();
    }

}
