package co.zpdev.bots.ethereal;

import co.zpdev.bots.core.command.handler.CommandHandler;
import co.zpdev.bots.ethereal.listeners.MemberJoin;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.hooks.AnnotatedEventManager;

import java.awt.*;

public class Ethereal {

    public static long LOGS = 419702896221749248L;
    public static long JOIN = 388053430607806465L;

    public static long SR = 388141768559820812L;
    public static long CAT = 388075127645929475L;

    public static Color EMBED = Color.decode("#7254BF");

    public static void main(String[] args) throws Exception {
        CommandHandler handler = new CommandHandler("-", "co.zpdev.bots.ethereal.commands");

        new JDABuilder(AccountType.BOT).setToken(args[0])
                .setEventManager(new AnnotatedEventManager())
                .addEventListener(handler)
                .addEventListener(new MemberJoin()).buildBlocking();
    }

}
