package co.zpdev.bots.ethereal.commands;

import co.zpdev.bots.core.command.Command;
import co.zpdev.bots.ethereal.Ethereal;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.time.Instant;

public class Claim {

    @Command(aliases = "claim")
    public void onCommand(Message message) {
        if (!message.getChannel().getName().startsWith("ticket-")) return;
        message.delete().queue();

        if (message.getMember().getRoles().stream().noneMatch(r -> r.getIdLong() == Ethereal.SR)) return;
        if (!message.getTextChannel().getTopic().equals("Status: Awaiting Sales representative")) return;

        String name = message.getAuthor().getName() + "#" + message.getAuthor().getDiscriminator();
        message.getTextChannel().getManager().setTopic("Sales Rep: " + name + " | Status: Discussing details").queue();

        MessageEmbed log = new EmbedBuilder().setColor(Ethereal.EMBED)
                .setAuthor("Ticket Claimed")
                .setDescription("#" + message.getChannel().getName() + " claimed by **" + message.getMember().getEffectiveName() + "**")
                .setTimestamp(Instant.now()).build();
        message.getGuild().getTextChannelById(Ethereal.LOGS).sendMessage(log).queue();
    }

}
