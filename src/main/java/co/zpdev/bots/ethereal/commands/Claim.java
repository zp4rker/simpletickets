package co.zpdev.bots.ethereal.commands;

import co.zpdev.bots.core.command.Command;
import co.zpdev.bots.ethereal.Ethereal;
import net.dv8tion.jda.core.entities.Message;

public class Claim {

    @Command(aliases = "claim")
    public void onCommand(Message message) {
        if (!message.getChannel().getName().startsWith("ticket-")) return;
        message.delete().queue();

        if (message.getMember().getRoles().stream().noneMatch(r -> r.getIdLong() == Ethereal.SR)) return;
        if (message.getTextChannel().getTopic().equals("Unclaimed")) return;

        String name = message.getAuthor().getName() + "#" + message.getAuthor().getDiscriminator();
        message.getTextChannel().getManager().setTopic("Claimed by " + name).queue();
    }

}
