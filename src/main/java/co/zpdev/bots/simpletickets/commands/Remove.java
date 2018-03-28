package co.zpdev.bots.simpletickets.commands;

import co.zpdev.bots.core.command.Command;
import co.zpdev.bots.simpletickets.SimpleTickets;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;

import java.time.Instant;

public class Remove {

    @Command(aliases = "remove")
    public void onCommand(Message message) {
        if (!message.getMember().isOwner() && message.getMember().getRoles().stream().noneMatch(r -> r.getId().equals("388055883172806656"))) return;
        if (message.getMentionedMembers().size() != 1 && message.getMentionedChannels().size() != 1) return;
        if (!message.getMentionedChannels().get(0).getName().startsWith("ticket-")) return;

        message.delete().queue();

        TextChannel ticket = message.getMentionedChannels().get(0);
        if (ticket.getPermissionOverride(message.getMentionedMembers().get(0)) == null) return;
        ticket.getPermissionOverride(message.getMentionedMembers().get(0)).delete().complete();

        String name = message.getMentionedMembers().get(0).getEffectiveName();
        MessageEmbed log = new EmbedBuilder().setColor(SimpleTickets.embed)
                .setAuthor("Member Removed")
                .setDescription("**" + message.getMember().getEffectiveName() + "** removed **" + name + "** from #"
                        + ticket.getName())
                .setTimestamp(Instant.now()).build();
        message.getGuild().getTextChannelById(SimpleTickets.logs).sendMessage(log).queue();
    }

}
