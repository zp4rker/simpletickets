package co.zpdev.bots.ethereal.commands;

import co.zpdev.bots.core.command.Command;
import co.zpdev.bots.ethereal.Ethereal;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.time.Instant;

public class Status {

    @Command(aliases = "status")
    public void onCommand(Message message, String[] args) {
        if (message.getMember().getRoles().stream().noneMatch(r -> r.getIdLong() == Ethereal.SR)) return;
        if (!message.getMentionedChannels().get(0).getName().startsWith("ticket-")) return;
        System.out.println("Debugging");
        if (args.length != 1 || !args[0].matches("(?i:aip|ip|afp|c)")) return;
        System.out.println("Debugging");
        if (message.getTextChannel().getTopic().split(" \\| ").length < 2) return;
        System.out.println("Debugging");

        System.out.println("Debugging");
        String salesRep = message.getTextChannel().getTopic().split(" \\| ")[0];
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
        System.out.println("Debugging");

        message.getTextChannel().getManager().setTopic(salesRep + " | Status: " + status).queue();
        System.out.println("Debugging");

        MessageEmbed log = new EmbedBuilder().setColor(Ethereal.EMBED)
                .setAuthor("Status Updated")
                .setDescription("**" + salesRep.substring(10) + "** updated the status of #" + message.getChannel().getName())
                .setTimestamp(Instant.now()).build();
        message.getGuild().getTextChannelById(Ethereal.LOGS).sendMessage(log).queue();
        System.out.println("Debugging");
    }

}
