package co.zpdev.bots.simpletickets.commands;

import co.zpdev.bots.core.command.Command;
import co.zpdev.bots.simpletickets.SimpleTickets;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.requests.restaction.ChannelAction;

import java.time.Instant;

public class New {

    @Command(aliases = "new")
    public void onCommand(Message message) {
        message.delete().queue();

        if (message.getGuild().getCategoryById(SimpleTickets.cat).getTextChannels().stream().anyMatch(c -> c.getPermissionOverride(message.getMember()) != null)) {
            message.getChannel().sendMessage(new EmbedBuilder().setColor(SimpleTickets.embed).setAuthor("You have already created a ticket!").build()).queue();
            return;
        }

        TextChannel logs = message.getGuild().getTextChannelById(SimpleTickets.logs);
        String topic = logs.getTopic();
        int ticket = 0;
        if (!topic.isEmpty()) ticket = Integer.parseInt(topic.replace("Tickets: ", ""));
        ticket++;

        ChannelAction ca = message.getGuild().getController()
        .createTextChannel("ticket-" + String.format("%04d", ticket))
        .setParent(message.getGuild().getCategoryById(SimpleTickets.cat))
        .addPermissionOverride(message.getGuild().getPublicRole(), 0, 3072)
        .addPermissionOverride(message.getMember(), 3072, 0)
        .setTopic("Status: Inquiry");
        SimpleTickets.roles.forEach(r -> ca.addPermissionOverride(message.getGuild().getRoleById(r), 3072, 0));
        TextChannel c = (TextChannel) ca.complete();

        logs.getManager().setTopic("Tickets: " + ticket).queue();

        String name = message.getAuthor().getName() + "#" + message.getAuthor().getDiscriminator();
        MessageEmbed log = new EmbedBuilder().setColor(SimpleTickets.embed).setAuthor("New Ticket")
                .setDescription("New ticket created by **" + name + "**.")
                .setFooter("Ticket #" + String.format("%04d", ticket), null)
                .setTimestamp(Instant.now()).build();
        logs.sendMessage(log).queue();

        MessageEmbed embed = new EmbedBuilder()
                .setColor(SimpleTickets.embed)
                .setAuthor("Thank you for contacting SimpleTickets Services, a team member will be with you shortly.").build();
        c.sendMessage(embed).queue();
    }

}
