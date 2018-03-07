package co.zpdev.bots.ethereal.commands;

import co.zpdev.bots.core.command.Command;
import co.zpdev.bots.ethereal.Ethereal;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;

import java.time.Instant;

public class Close {

    @Command(aliases = "close")
    public void onCommand(Message message) {
        if (!message.getMember().hasPermission(Permission.ADMINISTRATOR)) return;
        if (!message.getChannel().getName().startsWith("ticket-")) return;

        message.delete().queue();

        TextChannel c = message.getTextChannel();
        c.delete().complete();

        MessageEmbed log = new EmbedBuilder().setColor(Ethereal.EMBED)
                .setAuthor("Ticket Closed")
                .setDescription("#" + c.getName() + " closed by **" + message.getMember().getEffectiveName() + "**.")
                .setTimestamp(Instant.now()).build();
        message.getGuild().getTextChannelById(Ethereal.LOGS).sendMessage(log).queue();
    }

}
