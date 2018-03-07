package co.zpdev.bots.ethereal.commands;

import co.zpdev.bots.core.command.Command;
import co.zpdev.bots.ethereal.Ethereal;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class Help {

    @Command(aliases = "help")
    public void onCommand(Message message) {
        if (message.getMember().getRoles().stream().noneMatch(r -> r.getIdLong() == 388818308172087307L)) return;

        MessageEmbed embed = new EmbedBuilder().setColor(Ethereal.EMBED)
                .setAuthor("Ethereal Bot Commands", null, message.getJDA().getSelfUser().getEffectiveAvatarUrl())
                .setDescription("**-new** Creates a ticket\n" +
                        "**-add @user #ticket** Adds a member to a ticket\n" +
                        "**-claim** Claims a ticket\n" +
                        "**-status <aip|ip|afp|c>** Sets the status of a ticket")
                .setFooter("Made by ZP4RKER#3333", null).build();

        message.getChannel().sendMessage(embed).queue();
    }

}
