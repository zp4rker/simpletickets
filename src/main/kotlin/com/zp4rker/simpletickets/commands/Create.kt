package com.zp4rker.simpletickets.commands

import com.zp4rker.core.discord.command.Command
import com.zp4rker.simpletickets.SimpleTickets
import com.zp4rker.simpletickets.embedColour
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.TextChannel
import java.awt.Color
import java.time.Instant

object Create : Command(aliases = arrayOf("create"), description = "Creates a new ticket.", autoDelete = true) {
    override fun handle(message: Message, channel: TextChannel, guild: Guild, args: List<String>) {
        val category = guild.getCategoryById(SimpleTickets.category) ?: return
        val member = message.member ?: return

        if (category.textChannels.any { it.getPermissionOverride(member) != null }) {
            channel.sendMessage(EmbedBuilder().setColor(Color.RED).setDescription(":x: You already have an open ticket!").build()).queue()
            return
        }

        val logs = guild.getTextChannelById(SimpleTickets.logs) ?: return
        val ticketNo = logs.topic?.run {
            when {
                isNotEmpty() -> toInt() + 1
                else -> 0
            }
        }

        guild.createTextChannel("ticket-${String.format("%04d", ticketNo)}").apply {
            setParent(category)

            addPermissionOverride(guild.publicRole, 0, 3072)
            addPermissionOverride(member, 3072, 0)
            SimpleTickets.roles.forEach { guild.getRoleById(it)?.also { r -> addPermissionOverride(r, 3072, 0) } }

            setTopic("Status: Inquiry")
        }.queue {
            EmbedBuilder().setColor(embedColour).apply {
                setTimestamp(Instant.now())
                setAuthor("New Ticket")

                setDescription("A new ticket was created by **${member.user.asTag}**")
            }.build().also { embed -> logs.sendMessage(embed).queue() }

            EmbedBuilder().setColor(embedColour).apply {
                setAuthor("Thank you for contacting us, a team member will be with you shortly.")
            }.build().also { embed -> it.sendMessage(embed).queue() }
        }

        logs.manager.setTopic("Tickets: $ticketNo").queue()
    }
}