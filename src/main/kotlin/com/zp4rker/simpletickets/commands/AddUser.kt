package com.zp4rker.simpletickets.commands

import com.zp4rker.core.discord.command.Command
import com.zp4rker.simpletickets.SimpleTickets
import com.zp4rker.simpletickets.embedColour
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.TextChannel
import java.time.Instant

object AddUser : Command(aliases = arrayOf("add", "adduser"), description = "Adds a member to the ticket.", usage = "add @member", mentionedMembers= 1, autoDelete = true) {
    override fun handle(message: Message, channel: TextChannel, guild: Guild, args: List<String>) {
        val member = message.member ?: return
        val logs = guild.getTextChannelById(SimpleTickets.logs) ?: return

        if (member.roles.none { SimpleTickets.roles.contains(it.idLong) }) return
        if (!channel.name.startsWith("ticket-")) return

        val mentioned = message.mentionedMembers[0]
        channel.createPermissionOverride(mentioned).apply { allow = 3072 }.queue()

        EmbedBuilder().setColor(embedColour).apply {
            setAuthor("${mentioned.effectiveName} was added to #${channel.name}", null, mentioned.user.effectiveAvatarUrl)
            setFooter("by ${member.effectiveName}", member.user.effectiveAvatarUrl)
            setTimestamp(Instant.now())
        }.build().apply { logs.sendMessage(this).queue() }
    }
}