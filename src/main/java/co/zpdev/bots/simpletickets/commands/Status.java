package co.zpdev.bots.simpletickets.commands;

import co.zpdev.bots.core.command.Command;
import co.zpdev.bots.simpletickets.SimpleTickets;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

public class Status {

    @Command(aliases = "status")
    public void onCommand(Message message, String[] args) {
        if (message.getMember().getRoles().stream().noneMatch(r -> SimpleTickets.roles.contains(r.getIdLong()))) return;
        if (!message.getChannel().getName().startsWith("ticket-")) return;
        //if (message.getTextChannel().getTopic().split(" \\| ").length < 2) return;
        message.delete().queue();

        if (args.length != 1 || !args[0].matches("(?i:aip|ip|afp|c)")) {
            message.getChannel().sendMessage("Please provide a proper status! " +
                    "```\nStatuses\n" +
                    "aip = awaiting initial payment\n" +
                    "ip = in progress\n" +
                    "afp = awaiting final payment\n" +
                    "c = completed```").queue(m ->
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            m.delete().queue();
                        }
                    }, 15000)
            );
            return;
        }

        String status;
        if (args[0].matches("(?i:aip)")) {
            status = "Awaiting initial payment";
        } else if (args[0].matches("(?i:ip)")) {
            status = "In progress";
        } else if (args[0].matches("(?i:afp)")) {
            status = "Awaiting final payment";
        } else {
            status = "Completed";
        }

        message.getTextChannel().getManager().setTopic("Status: " + status).queue();

        String name = message.getAuthor().getName() + "#" + message.getAuthor().getDiscriminator();
        MessageEmbed log = new EmbedBuilder().setColor(SimpleTickets.embed)
                .setAuthor("Status Updated")
                .setDescription("**" + name + "** updated the status of #" + message.getChannel().getName())
                .setFooter("Status: " + status, null)
                .setTimestamp(Instant.now()).build();
        message.getGuild().getTextChannelById(SimpleTickets.logs).sendMessage(log).queue();
    }

}
