package com.zp4rker.simpletickets

import com.zp4rker.core.discord.command.CommandHandler
import com.zp4rker.core.discord.config.ConfigManager
import com.zp4rker.simpletickets.commands.Close
import com.zp4rker.simpletickets.commands.Create
import net.dv8tion.jda.api.AccountType
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.hooks.AnnotatedEventManager

const val embedColour = 0x353940

fun main() {
    SimpleTickets.loadConfig()

    val handler = CommandHandler(SimpleTickets.prefix).apply { registerCommands(
            Create,
            Close
    ) }

    JDABuilder(AccountType.BOT).apply {
        setToken(SimpleTickets.token)

        setEventManager(AnnotatedEventManager())
        addEventListeners(handler)
    }.build()
}

object SimpleTickets {
    private val config = ConfigManager.loadConfig("config.json")

    lateinit var prefix: String
    lateinit var token: String
    var logs = 0L
    var category = 0L
    val roles = mutableListOf<Long>()

    fun loadConfig() {
        prefix = config.getString("prefix")
        token = config.getString("token")
        logs = config.getLong("log-channel")
        category = config.getLong("category")
        roles.addAll(config.getJSONArray("roles").toMutableSet().map { it as Long })
    }
}