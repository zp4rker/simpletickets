package co.zpdev.bots.ethereal.commands;

import co.zpdev.bots.core.command.Command;
import co.zpdev.bots.ethereal.Ethereal;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;

import java.time.Instant;

public class New {

    @Command(aliases = "new")
    public void onCommand(Message message) {
        message.delete().queue();

        TextChannel logs = message.getGuild().getTextChannelById(Ethereal.LOGS);
        String topic = logs.getTopic();
        int ticket = 0;
        if (!topic.isEmpty()) ticket = Integer.parseInt(topic.replace("Tickets: ", ""));
        ticket++;

        TextChannel c = (TextChannel) message.getGuild().getController()
        .createTextChannel("ticket-" + String.format("%04d", ticket))
        .setParent(message.getGuild().getCategoryById(Ethereal.CAT))
        .addPermissionOverride(message.getGuild().getPublicRole(), 0, 3072)
        .addPermissionOverride(message.getMember(), 3072, 0)
        .addPermissionOverride(message.getGuild().getRoleById(Ethereal.SR), 3072, 0).complete();

        logs.getManager().setTopic("Tickets: " + ticket).queue();

        String name = message.getAuthor().getName() + "#" + message.getAuthor().getDiscriminator();
        MessageEmbed log = new EmbedBuilder().setColor(Ethereal.EMBED).setAuthor("New Ticket")
                .setDescription("New ticket created by **" + name + "**.")
                .setFooter("Ticket #" + String.format("%04d", ticket), null)
                .setTimestamp(Instant.now()).build();
        logs.sendMessage(log).queue();

        MessageEmbed embed = new EmbedBuilder()
                .setColor(Ethereal.EMBED)
                .setAuthor("Thank you for contacting DigiDev, a team member will be with you shortly.").build();
        c.sendMessage(embed).queue();
    }

}
