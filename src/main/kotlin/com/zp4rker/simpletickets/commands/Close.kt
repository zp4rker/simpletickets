package com.zp4rker.simpletickets.commands

import com.zp4rker.core.discord.command.Command
import com.zp4rker.simpletickets.SimpleTickets
import com.zp4rker.simpletickets.embedColour
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.TextChannel
import java.time.Instant

object Close : Command(aliases = arrayOf("close"), description = "Closes an open ticket.", autoDelete = true) {
    override fun handle(message: Message, channel: TextChannel, guild: Guild, args: List<String>) {
        val member = message.member ?: return
        val logs = guild.getTextChannelById(SimpleTickets.logs) ?: return

        if (member.roles.none { SimpleTickets.roles.contains(it.idLong) }) return
        if (!channel.name.startsWith("ticket-")) return

        channel.delete().queue {
            EmbedBuilder().setColor(embedColour).apply {
                setTitle("Ticket closed")
                setDescription("#${channel.name} closed by **${member.user.asTag}**.")
                setTimestamp(Instant.now())
            }.build().apply { logs.sendMessage(this).queue() }
        }
    }
}