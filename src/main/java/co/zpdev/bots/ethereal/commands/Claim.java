package co.zpdev.bots.ethereal.commands;

import co.zpdev.bots.core.command.Command;
import co.zpdev.bots.ethereal.Ethereal;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;

import java.time.Instant;

public class Claim {

    @Command(aliases = "claim")
    public void onCommand(Message message) {
        if (!message.getChannel().getName().startsWith("ticket-")) return;
        message.delete().queue();

        if (message.getMember().getRoles().stream().noneMatch(r -> r.getIdLong() == Ethereal.SR)) return;
        if (!message.getTextChannel().getTopic().equals("Unclaimed")) return;

        String name = message.getAuthor().getName() + "#" + message.getAuthor().getDiscriminator();
        message.getTextChannel().getManager().setTopic("Claimed by " + name).queue();

        TextChannel logs = message.getGuild().getTextChannelById(Ethereal.LOGS);
        MessageEmbed log = new EmbedBuilder().setColor(Ethereal.EMBED)
                .setAuthor("Ticket Claimed")
                .setDescription("#" + message.getChannel().getName() + " claimed by **" + message.getMember().getEffectiveName() + "**")
                .setTimestamp(Instant.now()).build();
        logs.sendMessage(log).queue();
    }

}
