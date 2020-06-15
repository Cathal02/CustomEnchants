package org.cathal02.customenchants;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.cathal02.enchantments.BaseEnchant;

import java.util.ArrayList;
import java.util.List;

public class EnchantmentHandler {

    CustomEnchants plugin;
    public EnchantmentHandler(CustomEnchants instance)
    {
        plugin = instance;
    }

    public void enchantItem(ItemStack itemStack, BaseEnchant enchantment, Player player)
    {
        if(itemStack.containsEnchantment(enchantment))
        {
            player.sendMessage(ChatColor.RED + "This item already has " + enchantment.getName());
            player.playSound(player.getLocation(), Sound.ANVIL_LAND, .5f, 1);

            return;
        }

        if(enchantment.canEnchantItem(itemStack))
        {
            int expCost = 0;
            try{
                expCost = plugin.getConfig().getInt(enchantment.getSection() + ".expCost");
                if(!(player.getExpToLevel() >= expCost))
                {
                    player.sendMessage(ChatColor.RED + "You do not have the required " + expCost + " levels to purchase this enchant.");
                    player.playSound(player.getLocation(), Sound.ANVIL_LAND, .5f, 1);

                    return;
                }
            } catch (Exception e)
            {
                System.out.println(ChatColor.RED + "[CustomEnchants] No expCost found for: " + enchantment.getName());
            }

            itemStack.addUnsafeEnchantment(enchantment, 1);
            ItemMeta meta = itemStack.getItemMeta();

            List<String> lore = new ArrayList<>();

            if(meta.getLore() != null)
            {
                lore = meta.getLore();
            }
            lore.add(ChatColor.GRAY + enchantment.getName());
            meta.setLore(lore);

            itemStack.setItemMeta(meta);
            player.setLevel(player.getLevel()-expCost);

            String msg = ChatColor.GREEN + "Purchase Successful";
            try{
                msg = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("enchantSuccessful"));
            } catch (Exception e)
            {
                System.out.println("[CustomEnchants] Could not find enchantSuccessful message in config.yml");
            }

            player.sendMessage(msg);
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 2);
        }
        else
        {
        player.sendMessage(ChatColor.RED + enchantment.getName() + " is not compatible with this item!");
            player.playSound(player.getLocation(), Sound.ANVIL_LAND, .5f, 1);
        }


    }

}
