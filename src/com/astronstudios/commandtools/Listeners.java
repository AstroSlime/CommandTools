package com.astronstudios.commandtools;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class Listeners implements Listener {

    @EventHandler
    public void on(PlayerInteractEvent event) {
        if (event.getAction().toString().contains("CLICK")) {
            Player p = event.getPlayer();
            ItemStack is = null;
            if (this.nullCheck(p.getInventory().getItemInMainHand())) {
                is = p.getInventory().getItemInMainHand();
            } else if (this.nullCheck(p.getInventory().getItemInOffHand())) {
                is = p.getInventory().getItemInOffHand();
            }

            CommandItem i = new CommandItem(is);
            if (event.getAction().toString().contains("RIGHT")) {
                if (i.getCommandRight().length() > 0) {
                    if (!i.isRunCommandAdmin()) {
                        p.performCommand(i.getCommandRight().replaceAll("%name%", p.getName()));
                    } else {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), i.getCommandRight().replaceAll("%name%", p.getName()));
                    }

                    Bukkit.getPluginManager().callEvent(new CommandToolsItemUseEvent(p, i, is, event.getAction()));
                }
            } else if (event.getAction().toString().contains("LEFT") && i.getCommandLeft().length() > 0) {
                if (!i.isRunCommandAdmin()) {
                    p.performCommand(i.getCommandLeft().replaceAll("%name%", p.getName()));
                } else {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), i.getCommandLeft().replaceAll("%name%", p.getName()));
                }

                Bukkit.getPluginManager().callEvent(new CommandToolsItemUseEvent(p, i, is, event.getAction()));
            }
        }

    }

    @EventHandler
    public void on(PlayerJoinEvent event) {
        CommandTools.getInstance().sendUpdateMessage(event.getPlayer());
    }

    private boolean nullCheck(ItemStack i) {
        return i != null && i.getType() != Material.AIR;
    }
}
