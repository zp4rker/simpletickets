package co.zpdev.bots.simpletickets;

import co.zpdev.bots.core.command.handler.CommandHandler;
import co.zpdev.bots.simpletickets.listeners.MemberJoin;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.hooks.AnnotatedEventManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleTickets {

    private static String prefix, token;

    public static long logs, cat;
    public static long join = 0L;

    public static Color embed;

    public static List<Long> roles;

    public static void main(String[] args) throws Exception {
        loadConfig();

        CommandHandler handler = new CommandHandler(prefix, "co.zpdev.bots.simpletickets.commands");

        new JDABuilder(AccountType.BOT).setToken(token)
                .setEventManager(new AnnotatedEventManager())
                .addEventListener(handler)
                .addEventListener(new MemberJoin()).buildBlocking();
    }

    private static void loadConfig() throws IOException {
        File file = new File("config.json");
        if (!file.exists()) throw new IllegalStateException("Config file does not exist!");

        FileReader rd = new FileReader(file); int c; StringBuilder sb = new StringBuilder();
        while ((c = rd.read()) != -1) sb.append((char) c);
        String raw = sb.toString().trim();

        try {
            JSONObject json = new JSONObject(raw);

            prefix = json.getString("prefix");
            token = json.getString("token");

            logs = json.getLong("log-channel");
            join = json.getLong("join-channel");
            cat = json.getLong("category");

            embed = Color.decode(json.getString("embed-colour"));

            JSONArray rolesArray = json.getJSONArray("roles");
            roles = new ArrayList<>();
            for (int i = 0; i < rolesArray.length(); i++) roles.add(rolesArray.getLong(i));
        } catch (JSONException | NumberFormatException e) {
            throw new IllegalStateException("Config file is corrupted or invalid!");
        }
    }

}
