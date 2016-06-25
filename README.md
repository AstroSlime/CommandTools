<h1>CommandTools</h1>
<h3>Latest: 1.2.1</h3>
Add or edit NBT tags on items, set custom names and lores or add commands on items. 
CommandTools lets you set the display name of an item, add up to 300 lines in the 
lore, hide nbt attributes and set a command that will be run when you interact with 
something while holding the item. 

___
<h2>Commands</h2>
*/ct help* - Display a help message with all these commands in it. <br>
*/ct hide attributes* - Hide all nbt attributes on the item. <br>
*/ct show attributes* - Show all nbt attributes on the item. <br>
*/ct set name <name>* - Set the item's display name. <br>
*/ct command RIGHT_CLICK:LEFT_CLICK <command>* - Set the command that the item will 
execute when you interact with the item. This will not run if the plugin is uninstalled later. <br>
*/ct command remove RIGHT_CLICK:LEFT_CLICK* - Remove the command that is applied to the item. <br>
*/ct console t:f* - Set if commands are run through the console (/ct console t:f), 
You can use %name% in place of where the players name should be if running a command such as teleport. <br>

___
<h2>Permissions</h2>
There is only 1 permission for this plugin to keep it lightweight and easy to use.commandtools.use - 
Gives permission to use CommandTools. 

___
<h2>API</h2>
You can interface with some of the classes in the plugin to get events fired when plyers interact with these items or create
items from your own plugin to add into inventorys or give to players.<br>
You can use our custom even to get when a player inertacts with an item:
```java
    @EventHandler
    public void on(CommandToolsItemUseEvent event) {
        ...
    }
```
or make items using the builder:
```java
    public ItemStack make() {
        CommandItem item = new CommandItem(Material.WOOD);
        item.setCommandRight("help");
        item.setName("&6WOOOOOD!");
        item.setLoreLine(10, "This is the 10th line");
        return item.build();
    }
```
___
<h2>Support</h2>
Find this plugin helpful or like the concept? Consider dontaing to help development of 
this plugin and ones alike. [You can find a donation link here if interested]
(https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=MR96W6T2ZYBTC).
