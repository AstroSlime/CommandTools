package com.astronstudios.commandtools;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class CommandToolsItemUseEvent extends Event {

    private static HandlerList handlerList = new HandlerList();
    private Player player;
    private CommandItem cItem;
    private ItemStack item;
    private Action action;

    public CommandToolsItemUseEvent(Player player, CommandItem cItem, ItemStack item, Action action) {
        this.player = player;
        this.cItem = cItem;
        this.item = item;
        this.action = action;
    }

    public Player getPlayer() {
        return this.player;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public CommandItem getCommandItem() {
        return this.cItem;
    }

    public Action getAction() {
        return this.action;
    }

    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
