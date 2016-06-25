package com.astronstudios.commandtools;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandItem {

    private static Class<?> craftitem;
    private static Class<?> itemstack;
    private static Class<?> nbtTag;
    private static Class<?> nbtList;
    private ItemStack ITEM_STACK;
    private boolean hideEnchantments = false;
    private boolean hideAttributes = false;
    private boolean hideUnbreakable = false;
    private boolean hideCanDestroy = false;
    private boolean hideCanPlace = false;
    private boolean hideOther = false;
    private boolean removeAttributes = false;
    private boolean unbreakable = false;
    private boolean clearHidden = false;
    private boolean runCommandAdmin = false;
    private String command_left = "";
    private String command_right = "";

    static {
        craftitem = UtilReflection.getOBCClass("inventory.CraftItemStack");
        itemstack = UtilReflection.getNMSClass("ItemStack");
        nbtTag = UtilReflection.getNMSClass("NBTTagCompound");
        nbtList = UtilReflection.getNMSClass("NBTTagList");
    }

    public CommandItem(Material mat) {
        this.ITEM_STACK = new ItemStack(mat);
    }

    public CommandItem(ItemStack item) {
        this.ITEM_STACK = item;
    }

    public CommandItem withDurability(int durability) {
        this.ITEM_STACK.setDurability((short) durability);
        return this;
    }

    public CommandItem setType(Material mat) {
        this.ITEM_STACK.setType(mat);
        return this;
    }

    public CommandItem setAmount(int amount) {
        this.ITEM_STACK.setAmount(amount);
        return this;
    }

    public CommandItem withEnchant(Enchantment enchantment, int level) {
        this.ITEM_STACK.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public CommandItem hideEnchantments(boolean flag) {
        this.hideEnchantments = flag;
        return this;
    }

    public CommandItem hideAttributes(boolean flag) {
        this.hideAttributes = flag;
        return this;
    }

    public CommandItem hideUnbreakable(boolean flag) {
        this.hideUnbreakable = flag;
        return this;
    }

    public CommandItem hideCanDestroy(boolean flag) {
        this.hideCanDestroy = flag;
        return this;
    }

    public CommandItem hideCanPlaceOn(boolean flag) {
        this.hideCanPlace = flag;
        return this;
    }

    public CommandItem hideOther(boolean flag) {
        this.hideOther = flag;
        return this;
    }

    public CommandItem isUnbreakable(boolean flag) {
        this.unbreakable = flag;
        return this;
    }

    public CommandItem hideAll() {
        this.hideAttributes(true);
        this.hideEnchantments(true);
        this.hideUnbreakable(true);
        this.hideCanDestroy(true);
        this.hideCanPlaceOn(true);
        this.hideOther(true);
        return this;
    }

    public CommandItem resetNBT(boolean b) {
        this.clearHidden = b;
        return this;
    }

    public CommandItem removeAttributes(boolean flag) {
        this.removeAttributes = flag;
        return this;
    }

    public CommandItem setName(String name) {
        ItemMeta m = this.ITEM_STACK.getItemMeta();
        m.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        this.ITEM_STACK.setItemMeta(m);
        return this;
    }

    public CommandItem setCommandLeft(String command) {
        this.command_left = command;
        return this;
    }

    public CommandItem setCommandRight(String command) {
        this.command_right = command;
        return this;
    }

    public CommandItem setRunCommandAdmin(boolean v) {
        this.runCommandAdmin = v;
        return this;
    }

    public boolean isRunCommandAdmin() {
        try {
            return (Boolean) this.getTag().getClass().getMethod("getBoolean", String.class)
                    .invoke(this.getTag(), "CommandTools.CommandPermission");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public CommandItem addLoreLine(String text) {
        text = ChatColor.translateAlternateColorCodes('&', "&7" + text);
        ItemMeta meta = this.ITEM_STACK.getItemMeta();
        List<String> lore = meta.getLore();
        if (lore == null) lore = new ArrayList<>();
        lore.add(text);
        meta.setLore(lore);
        this.ITEM_STACK.setItemMeta(meta);
        return this;
    }

    public CommandItem setLoreLine(int line, String text) {
        if (line <= 0) line = 0;
        else --line;
        if (line > 300) line = 300;
        text = ChatColor.translateAlternateColorCodes('&', "&7" + text);
        ItemMeta meta = this.ITEM_STACK.getItemMeta();
        List<String> lore = meta.getLore();
        if (lore == null) lore = new ArrayList<>();

        if (lore.size() <= line) {
            while (lore.size() <= line) lore.add(" ");
        }

        lore.set(line, text);
        meta.setLore(lore);
        this.ITEM_STACK.setItemMeta(meta);
        return this;
    }

    public CommandItem clearLore() {
        ItemMeta meta = this.ITEM_STACK.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        meta.setLore(lore);
        this.ITEM_STACK.setItemMeta(meta);
        return this;
    }

    public CommandItem removeLoreLine(int line) {
        ItemMeta meta = this.ITEM_STACK.getItemMeta();
        List<String> lore = meta.getLore();
        if (lore == null) lore = new ArrayList<>();
        if (lore.size() > line) lore.remove(line);
        meta.setLore(lore);
        this.ITEM_STACK.setItemMeta(meta);
        return this;
    }

    public String getCommandLeft() {
        try {
            Method e = craftitem.getDeclaredMethod("asNMSCopy", ItemStack.class);
            Object o = itemstack.cast(e.invoke(itemstack, ITEM_STACK));
            Object t = o.getClass().getMethod("getTag").invoke(o);
            if (t == null) {
                t = nbtTag.getConstructor().newInstance();
            }

            Method s = t.getClass().getMethod("getString", String.class);
            return (String) s.invoke(t, "CommandTools.Command");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getCommandRight() {
        try {
            Method e = craftitem.getDeclaredMethod("asNMSCopy", ItemStack.class);
            Object o = itemstack.cast(e.invoke(itemstack, ITEM_STACK));
            Object t = o.getClass().getMethod("getTag").invoke(o);
            if (t == null) {
                t = nbtTag.getConstructor().newInstance();
            }

            Method s = t.getClass().getMethod("getString", String.class);
            return (String) s.invoke(t, "CommandTools.CommandRight");
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    private Object getTag() {
        try {
            Method e = craftitem.getDeclaredMethod("asNMSCopy", ItemStack.class);
            Object o = itemstack.cast(e.invoke(itemstack, ITEM_STACK));
            Object t = o.getClass().getMethod("getTag").invoke(o);
            return t != null ? t : nbtTag.getConstructor().newInstance();
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public String getNBTTagString(String tag) {
        try {
            return (String) getTag().getClass().getMethod("getString", String.class).invoke(getTag(), tag);
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public int getNBTTagInt(String tag) {
        try {
            return (Integer) getTag().getClass().getMethod("getInt", String.class).invoke(getTag(), tag);
        } catch (Exception var3) {
            var3.printStackTrace();
            return 0;
        }
    }

    public int getNBTTagDouble(String tag) {
        try {
            return (Integer) getTag().getClass().getMethod("getDouble", String.class).invoke(getTag(), tag);
        } catch (Exception var3) {
            var3.printStackTrace();
            return 0;
        }
    }

    public ItemStack build() {
        try {
            Method e = craftitem.getDeclaredMethod("asNMSCopy", ItemStack.class);
            Object o = itemstack.cast(e.invoke(itemstack, this.ITEM_STACK));
            Object tag = o.getClass().getMethod("getTag", new Class[0]).invoke(o);
            if (tag == null) {
                tag = nbtTag.getConstructor(new Class[0]).newInstance();
            }

            Object list = nbtList.getConstructor(new Class[0]).newInstance();
            int hide = 0;

            if (hideEnchantments) ++hide;
            if (hideAttributes) hide += 2;
            if (hideUnbreakable) hide += 4;
            if (hideCanDestroy) hide += 8;
            if (hideCanPlace) hide += 16;
            if (hideOther) hide += 32;

            Method m;
            if (clearHidden || hide > 0) {
                m = tag.getClass().getMethod("setInt", String.class, Integer.TYPE);
                m.invoke(tag, "HideFlags", hide);
            }

            if (removeAttributes) {
                m = tag.getClass().getMethod("set", String.class, nbtTag);
                m.invoke(tag, "AttributeModifiers", list);
            }

            if (unbreakable) {
                m = tag.getClass().getMethod("setBoolean", String.class, Boolean.TYPE);
                m.invoke(tag, "Unbreakable", unbreakable);
            }

            if (command_left.length() > 0) {
                m = tag.getClass().getMethod("setString", String.class, String.class);
                m.invoke(tag, "CommandTools.Command", command_left);
            }

            if (command_right.length() > 0) {
                m = tag.getClass().getMethod("setString", String.class, String.class);
                m.invoke(tag, "CommandTools.CommandRight", command_right);
            }

            m = tag.getClass().getMethod("setBoolean", String.class, Boolean.TYPE);
            m.invoke(tag, "CommandTools.CommandPermission", runCommandAdmin);
            Method setTag = o.getClass().getMethod("setTag", nbtTag);
            setTag.invoke(o, tag);
            Method getItem = craftitem.getDeclaredMethod("asBukkitCopy", itemstack);
            this.ITEM_STACK = (ItemStack) getItem.invoke(o, o);
        } catch (Exception var10) {
            var10.printStackTrace();
        }

        return this.ITEM_STACK;
    }
}
