package org.cathal02.GUI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.cathal02.customenchants.CustomEnchants;
import org.cathal02.enchantments.BaseEnchant;
import sun.jvm.hotspot.gc.shared.CardGeneration;
import sun.tools.asm.CatchData;

import java.util.ArrayList;
import java.util.List;

public class GUIHandler implements Listener {

    CustomEnchants plugin;

    public GUIHandler(CustomEnchants instance)
    {
        plugin = instance;
    }

    public void handlePlayerOpenGUI(Player p)
    {
        p.openInventory(createGUI(p));
    }

    private Inventory createGUI(Player player) {
        int invSize;
        try
        {
            invSize = plugin.getConfig().getInt("inventorySize");
        } catch (Exception e)
        {
            System.out.println(ChatColor.RED + "[CustomEnchants] Invalid inventorySize in config.yml");
            invSize = 54;
        }

        Inventory inv = Bukkit.createInventory(player, invSize, getInventoryName());

        for(BaseEnchant enchant : plugin.enchantments.values())
        {
            ItemStack item = new ItemStack(Material.NETHER_STAR);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(getItemName(enchant));
            meta.setLore(enchant.getLore());
            item.setItemMeta(meta);

            item = NBTEditor.set(item, enchant.getName(), "customEnchant", "enchant");
            inv.setItem(enchant.getInventorySlot(), item);
        }

        if(plugin.getConfig().getBoolean("fillInventory"))
        {
            for(int i =0; i < invSize; i++)
            {
                if(inv.getItem(i) == null)
                {
                    ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE,1 , DyeColor.BLACK.getData());

                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(" ");
                    item.setItemMeta(meta);

                    inv.setItem(i, item);
                }
            }
        }

        return inv;
    }

    private String getItemName(BaseEnchant enchant) {
        try
        {
            return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(enchant.getSection() + ".name"));
        } catch (Exception e)
        {
            System.out.println(ChatColor.RED + "[CustomEnchants] No name found for " + enchant.getName());
            return enchant.getName();
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        if(!(e.getWhoClicked() instanceof Player)) return;

        if(e.getView().getTitle().equalsIgnoreCase(getInventoryName()))
        {
            e.setCancelled(true);
            if(e.getCurrentItem() != null)
            {
                ItemStack item = e.getCurrentItem();
                if(NBTEditor.contains(item, "customEnchant", "enchant"))
                {
                    String itemName = NBTEditor.getString(item, "customEnchant", "enchant");
                    if(plugin.enchantments.containsKey(itemName))
                    {
                        ItemStack itemInHand = e.getWhoClicked().getItemInHand();
                        plugin.enchantmentHandler.enchantItem(itemInHand, plugin.enchantments.get(itemName), (Player)e.getWhoClicked());


                    }
                }
            }
        }
    }

    private String getInventoryName()
    {
        String name = "";
        try
        {
            name = plugin.getConfig().getString("inventoryName");
            name = ChatColor.translateAlternateColorCodes('&', name);
            return name;
        } catch (Exception e)
        {
            System.out.println(ChatColor.RED + "[CustomEnchants] Invalid inventoryName in config.yml");
            return ChatColor.RED + "Enchanter";
        }
    }



}
