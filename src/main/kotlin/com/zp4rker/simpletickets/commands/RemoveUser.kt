package com.zp4rker.simpletickets.commands

import com.zp4rker.core.discord.command.Command
import com.zp4rker.simpletickets.SimpleTickets
import com.zp4rker.simpletickets.embedColour
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.TextChannel
import java.time.Instant

object RemoveUser : Command(aliases = arrayOf("remove", "removeuser"), description = "Removes a member from a ticket.", usage = "remove @member", mentionedMembers = 1, autoDelete = true) {
    override fun handle(message: Message, channel: TextChannel, guild: Guild, args: List<String>) {
        val member = message.member ?: return
        val logs = guild.getTextChannelById(SimpleTickets.logs) ?: return

        if (member.roles.none { SimpleTickets.roles.contains(it.idLong) }) return
        if (!channel.name.startsWith("ticket-")) return

        val mentioned = message.mentionedMembers[0]
        channel.getPermissionOverride(mentioned)?.also { it.delete().queue() }

        EmbedBuilder().setColor(embedColour).apply {
            setAuthor("${mentioned.effectiveName} was removed from #${channel.name}", null, mentioned.user.effectiveAvatarUrl)
            setFooter("by ${member.effectiveName}", member.user.effectiveAvatarUrl)
            setTimestamp(Instant.now())
        }.build().apply { logs.sendMessage(this).queue() }
    }
}