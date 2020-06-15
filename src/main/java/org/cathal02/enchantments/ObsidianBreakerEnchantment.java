package org.cathal02.enchantments;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.cathal02.ArmorEquip.ArmorEquipEvent;
import org.cathal02.customenchants.CustomEnchants;

public class ObsidianBreakerEnchantment extends BaseEnchant implements Listener {

    public ObsidianBreakerEnchantment(int id, CustomEnchants instance) {
        super(id, instance, "obsidianBreakerEnchantment");
    }

    @Override
    public String getName() {
        return "Obisidan Breaker";
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public int getStartLevel() {
        return 0;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return null;
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return item.getType().name().endsWith("PICKAXE");
    }

    @EventHandler
    public void onBlockBreak(PlayerInteractEvent e)
    {
        ItemStack itemInHand = e.getPlayer().getItemInHand();
        if(itemInHand != null)
        {
            if(e.getClickedBlock() != null && e.getClickedBlock().getType() != null)
            {
                if(e.getClickedBlock().getType().equals(Material.OBSIDIAN))
                {
                    if(itemInHand.containsEnchantment(this))
                    {
                        e.getClickedBlock().breakNaturally(itemInHand);
                    }
                }
            }

        }
    }

}
