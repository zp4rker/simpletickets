package com.zp4rker.simpletickets

import com.zp4rker.core.discord.command.CommandHandler
import com.zp4rker.core.discord.config.ConfigManager
import com.zp4rker.simpletickets.commands.AddUser
import com.zp4rker.simpletickets.commands.CloseTicket
import com.zp4rker.simpletickets.commands.CreateTicket
import com.zp4rker.simpletickets.commands.RemoveUser
import net.dv8tion.jda.api.AccountType
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.hooks.AnnotatedEventManager

const val embedColour = 0x353940

fun main() {
    val handler = CommandHandler(SimpleTickets.prefix, true).apply { registerCommands(
            CreateTicket,
            CloseTicket,
            AddUser,
            RemoveUser
    ) }

    JDABuilder(AccountType.BOT).apply {
        setToken(SimpleTickets.token)

        setEventManager(AnnotatedEventManager())
        addEventListeners(handler)
    }.build()
}

object SimpleTickets {
    private val config = ConfigManager.loadConfig("config.json")

    val prefix: String
    val token: String
    val logs: Long
    val category: Long
    val roles = mutableListOf<Long>()

    init {
        prefix = config.getString("prefix")
        token = config.getString("token")
        logs = config.getLong("log-channel")
        category = config.getLong("category")
        roles.addAll(config.getJSONArray("roles").toMutableSet().map { it as Long })
    }
}