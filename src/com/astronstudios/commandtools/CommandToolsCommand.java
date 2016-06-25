package com.astronstudios.commandtools;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandToolsCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§e[CommandTools] §cYou must be a player to use CommandTools!");
            return true;
        } else if (args.length != 0 && !args[0].equalsIgnoreCase("help")) {
            Player player = (Player) sender;
            ItemStack itemHand = player.getInventory().getItemInMainHand();
            if (itemHand != null && itemHand.getType() != Material.AIR) {
                CommandItem line;
                if (!args[0].equalsIgnoreCase("console") || args.length < 2 || !args[1].equalsIgnoreCase("t") && !args[1].equalsIgnoreCase("f")) {
                    CommandItem var11;
                    int var14;
                    if (args[0].equalsIgnoreCase("command") && args.length >= 2) {
                        if (args[1].equalsIgnoreCase("remove")) {
                            if (args[2].equalsIgnoreCase("left_click")) {
                                var11 = (new CommandItem(itemHand)).setCommandLeft(" ");
                                player.getInventory().setItemInMainHand(var11.build());
                                CommandTools.sendMessage(sender, "&aRemoved command for left click!");
                                return true;
                            }

                            if (args[2].equalsIgnoreCase("right_click")) {
                                var11 = (new CommandItem(itemHand)).setCommandRight(" ");
                                player.getInventory().setItemInMainHand(var11.build());
                                CommandTools.sendMessage(sender, "&aRemoved command for right click!");
                                return true;
                            }
                        } else {
                            String var13;
                            if (args[1].equalsIgnoreCase("left_click")) {
                                var13 = "";

                                for (var14 = 2; var14 < args.length; ++var14) {
                                    var13 = var13 + args[var14] + " ";
                                }

                                line = (new CommandItem(itemHand)).setCommandLeft(var13);
                                player.getInventory().setItemInMainHand(line.build());
                                CommandTools.sendMessage(sender, "&aSet left click command to \'/" + var13 + "\'!");
                                return true;
                            }

                            if (args[1].equalsIgnoreCase("right_click")) {
                                var13 = "";

                                for (var14 = 2; var14 < args.length; ++var14) {
                                    var13 = var13 + args[var14] + " ";
                                }

                                line = (new CommandItem(itemHand)).setCommandRight(var13);
                                player.getInventory().setItemInMainHand(line.build());
                                CommandTools.sendMessage(sender, "&aSet right click command to \'/" + var13 + "\'!");
                                return true;
                            }
                        }
                    }

                    if (args[0].equalsIgnoreCase("hide")) {
                        if (args.length >= 2) {
                            var11 = new CommandItem(itemHand);
                            if (args[1].equalsIgnoreCase("Attributes")) {
                                var11.hideAll();
                            }

                            player.getInventory().setItemInMainHand(var11.build());
                            CommandTools.sendMessage(sender, "&aHidden attributes!");
                            return true;
                        } else {
                            return false;
                        }
                    } else if (args[0].equalsIgnoreCase("show")) {
                        if (args.length >= 2) {
                            var11 = (new CommandItem(itemHand)).resetNBT(true);
                            player.getInventory().setItemInMainHand(var11.build());
                            CommandTools.sendMessage(sender, "&aShowing attributes!");
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        int line1;
                        String var12;
                        if (args[0].equalsIgnoreCase("set")) {
                            if (args.length < 2) {
                                return false;
                            } else {
                                var11 = new CommandItem(itemHand);
                                if (args[1].equalsIgnoreCase("NAME") && args.length >= 3) {
                                    var12 = "";

                                    for (line1 = 2; line1 < args.length; ++line1) {
                                        var12 = var12 + args[line1] + " ";
                                    }

                                    var11.setName(this.ct(var12));
                                    CommandTools.sendMessage(sender, "&aUpdated Items name to \'" + var12 + "&a\'!");
                                }

                                player.getInventory().setItemInMainHand(var11.build());
                                return true;
                            }
                        } else if (args[0].equalsIgnoreCase("clear")) {
                            player.getInventory().setItemInMainHand(new ItemStack(player.getInventory().getItemInMainHand().getType()));
                            CommandTools.sendMessage(sender, "&aCleared Item!");
                            return true;
                        } else if (args[0].equalsIgnoreCase("lore") && args.length >= 2) {
                            var11 = new CommandItem(itemHand);
                            if (args[1].equalsIgnoreCase("ADD")) {
                                if (args.length >= 3) {
                                    var12 = "";

                                    for (line1 = 2; line1 < args.length; ++line1) {
                                        var12 = var12 + args[line1] + " ";
                                    }

                                    var11.addLoreLine(this.ct(var12));
                                    CommandTools.sendMessage(sender, "&aAdded \'" + var12 + "&a\' to the items lore!");
                                }
                            } else if (args[1].equalsIgnoreCase("SET")) {
                                if (args.length >= 3) {
                                    var12 = "";
                                    line1 = Integer.parseInt(args[2]);

                                    for (int i = 3; i < args.length; ++i) {
                                        var12 = var12 + args[i] + " ";
                                    }

                                    var11.setLoreLine(line1, var12);
                                    CommandTools.sendMessage(sender, "&aSet line " + line1 + " to \'" + var12 + "&a\' in the items lore!");
                                }
                            } else if (args[1].equalsIgnoreCase("REMOVE")) {
                                if (args.length >= 3) {
                                    var14 = Integer.parseInt(args[2]);
                                    var11.removeLoreLine(var14);
                                    CommandTools.sendMessage(sender, "&aRemoved line \'" + var14 + "&a\' from the items lore!");
                                }
                            } else if (args[1].equalsIgnoreCase("CLEAR")) {
                                var11.clearLore();
                            }

                            player.getInventory().setItemInMainHand(var11.build());
                            return true;
                        } else {
                            return false;
                        }
                    }
                } else {
                    boolean item = args[1].equalsIgnoreCase("t");
                    line = (new CommandItem(itemHand)).setRunCommandAdmin(item);
                    player.getInventory().setItemInMainHand(line.build());
                    if (item) {
                        CommandTools.sendMessage(sender, "&aThis item will now run commands through the console!");
                    } else {
                        CommandTools.sendMessage(sender, "&aThis item will now run commands through the player!");
                    }
                    return true;
                }
            } else {
                CommandTools.sendMessage(sender, "&cYou are not holding any item in your main hand! Hold an item in your main hand to use this.");
                return true;
            }
        } else {
            CommandTools.sendMessageEmpty(sender, "");
            CommandTools.sendMessageEmpty(sender, "&e&m------------&f&l CommandTools Help &e&m------------");
            CommandTools.sendMessageEmpty(sender, "&f/ct help &7- &oDisplay this help message.");
            CommandTools.sendMessageEmpty(sender, "&f/ct hide Attributes &7- &oHide NBT attributes on the item your holding.");
            CommandTools.sendMessageEmpty(sender, "&f/ct show Attributes &7- &oShow NBT attributes on the item your holding.");
            CommandTools.sendMessageEmpty(sender, "&f/ct set name <v> &7- &oSet the items display name.");
            CommandTools.sendMessageEmpty(sender, "&f/ct lore &6add:remove:set:clear&f [line] <v> &7- &oUpdate an items lore.");
            CommandTools.sendMessageEmpty(sender, "&f/ct clear &7- &oClear this tem, replace it with a non effected version.");
            CommandTools.sendMessageEmpty(sender, "&f/ct command RIGHT_CLICK:LEFT_CLICK <command> &7- &oSet the command ran when you hit anything with the item.");
            CommandTools.sendMessageEmpty(sender, "&f/ct command remove RIGHT_CLICK:LEFT_CLICK &7- &oRemove the command currently set.");
            CommandTools.sendMessageEmpty(sender, "&f/ct console t:f &7- &oToggle the commands to be run by the console.");
            return true;
        }
    }

    private String ct(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
