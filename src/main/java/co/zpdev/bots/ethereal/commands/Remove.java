package co.zpdev.bots.ethereal.commands;

import co.zpdev.bots.core.command.Command;
import co.zpdev.bots.ethereal.Ethereal;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;

import java.time.Instant;

public class Remove {

    @Command(aliases = "remove")
    public void onCommand(Message message) {
        if (!message.getMember().hasPermission(Permission.ADMINISTRATOR)) return;
        if (message.getMentionedMembers().size() != 1 && message.getMentionedChannels().size() != 1) return;
        if (!message.getMentionedChannels().get(0).getName().startsWith("ticket-")) return;

        message.delete().queue();

        TextChannel ticket = message.getMentionedChannels().get(0);
        if (ticket.getPermissionOverride(message.getMentionedMembers().get(0)) == null) return;
        ticket.getPermissionOverride(message.getMentionedMembers().get(0)).delete().complete();

        String name = message.getMentionedMembers().get(0).getEffectiveName();
        TextChannel logs = message.getGuild().getTextChannelById(Ethereal.LOGS);
        MessageEmbed log = new EmbedBuilder().setColor(Ethereal.EMBED)
                .setAuthor("Member Removed")
                .setDescription("**" + message.getMember().getEffectiveName() + "** removed **" + name + "** from #"
                        + ticket.getName())
                .setTimestamp(Instant.now()).build();
        logs.sendMessage(log).queue();
    }

}
