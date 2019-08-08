package com.zp4rker.simpletickets.commands

import com.zp4rker.core.discord.command.Command
import com.zp4rker.simpletickets.SimpleTickets
import com.zp4rker.simpletickets.embedColour
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.TextChannel
import java.time.Instant

object CreateTicket : Command(aliases = arrayOf("new", "createticket"), description = "Creates a new ticket.", autoDelete = true) {
    override fun handle(message: Message, channel: TextChannel, guild: Guild, args: List<String>) {
        val member = message.member ?: return
        val category = guild.getCategoryById(SimpleTickets.category) ?: return
        val logs = guild.getTextChannelById(SimpleTickets.logs) ?: return

        if (category.textChannels.any { it.getPermissionOverride(member) != null }) {
            channel.sendMessage(EmbedBuilder().setColor(0xFF0000).setDescription(":x: You already have an open ticket!").build()).queue()
            return
        }

        val ticketNo = logs.topic?.run {
            when {
                isNotEmpty() -> replace("Tickets: ", "").toInt() + 1
                else -> 1
            }
        } ?: 1

        guild.createTextChannel("ticket-${String.format("%04d", ticketNo)}").apply {
            setParent(category)

            addPermissionOverride(guild.publicRole, 0, 3072)
            addPermissionOverride(member, 3072, 0)
            SimpleTickets.roles.forEach { guild.getRoleById(it)?.also { r -> addPermissionOverride(r, 3072, 0) } }
        }.queue {
            EmbedBuilder().setColor(embedColour).apply {
                setAuthor("Thank you for contacting us, a team member will be with you shortly.")
            }.build().apply { it.sendMessage(this).queue() }

            EmbedBuilder().setColor(embedColour).apply {
                setAuthor("A new ticket has been created (#${it.name})")
                setFooter("by ${member.user.asTag}", member.user.effectiveAvatarUrl)
                setTimestamp(Instant.now())
            }.build().apply { logs.sendMessage(this).queue() }
        }

        logs.manager.setTopic("Tickets: $ticketNo").queue()
    }
}