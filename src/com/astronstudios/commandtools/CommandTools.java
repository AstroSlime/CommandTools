package com.astronstudios.commandtools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandTools extends JavaPlugin implements Listener {

    private final int resource = 24882;
    private static String prefix = "§e[CommandTools]§f ";
    private static CommandTools instance;
    private final String version = this.getDescription().getVersion();
    public static boolean outdated = false;

    public static CommandTools getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(this, this);
        pm.registerEvents(new Listeners(), this);
        this.getCommand("commandtools").setExecutor(new CommandToolsCommand());
        this.log("&e&m==========================================");
        this.log(" ");
        this.log("       COMMAND TOOLS &8 v" + this.version);
        this.log("          &8by AstroSlime");
        this.log(" ");

        try {
            HttpURLConnection ex = (HttpURLConnection) (
                    new URL("http://www.spigotmc.org/api/general.php")).openConnection();
            ex.setDoOutput(true);
            ex.setRequestMethod("POST");
            ex.getOutputStream().write((
                    "key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource="
                            + resource).getBytes("UTF-8"));
            String version = (new BufferedReader(new InputStreamReader(ex.getInputStream()))).readLine();
            int versionT = Integer.parseInt(version.replaceAll("\\.", ""));
            int versionTC = Integer.parseInt(this.version.replaceAll("\\.", "").replaceFirst("0", ""));
            if (versionT > versionTC) {
                this.log("&e   New version available!");
                this.log("     Latest: v" + version);
                this.log("     DOWNLOAD: &bhttps://www.spigotmc.org/resources/commandtools.24882/");
                outdated = true;
            } else {
                this.log("     &aUp to date!");
            }

            if (versionTC > versionT) {
                this.log("     You are running a dev build! Expect bugs.");
            }
        } catch (Exception var6) {
            this.log("     &cFailed to check for updates!");
        }

        this.log(" ");
        this.log("&e&m==========================================");

        Bukkit.getOnlinePlayers().forEach(this::sendUpdateMessage);
    }

    public void onDisable() {
        log("&cShutting down...");
    }

    private void log(Object... obj) {
        String s = "";
        Object[] var3 = obj;
        int var4 = obj.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            Object o = var3[var5];
            s = s + o.toString();
        }

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', s));
    }

    public static void sendMessage(CommandSender sender, String msg) {
        msg = prefix + msg;
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static void sendMessageEmpty(CommandSender sender, String msg) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public void sendUpdateMessage(Player player) {
        if ((player.isOp() || player.hasPermission("commandtools.use")) && outdated) {
            BaseComponent[] hovertext = new BaseComponent[]{new TextComponent("Click to open download page.")};
            HoverEvent hoverEvent = new HoverEvent(Action.SHOW_TEXT, hovertext);
            ClickEvent clickEvent = new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.OPEN_URL,
                    "https://www.spigotmc.org/resources/commandtools.24882/");
            TextComponent com = new TextComponent("§e§lDownload");
            com.setHoverEvent(hoverEvent);
            com.setClickEvent(clickEvent);
            player.spigot().sendMessage(new TextComponent(prefix + "New version available! "), com);
        }

    }
}
