package org.cathal02.enchantments;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.cathal02.customenchants.CustomEnchants;

public class ImplantsEnchantment extends BaseEnchant implements Listener {
    private int multiplier;

    public ImplantsEnchantment(int id, CustomEnchants instance) {
        super(id, instance, "implantsEnchantment");
        multiplier = getMultiplier(instance, "implantsEnchantment");
    }

    @Override
    public String getName() {
        return "Implants";
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
        return item.getType().name().endsWith("HELMET");
    }


    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e)
    {
        if(e.getEntity().getType() == EntityType.PLAYER)
        {
            Player player = (Player)e.getEntity();
            ItemStack helmet = player.getInventory().getHelmet();
            if(helmet != null)
            {
                if(helmet.containsEnchantment(this))
                {
                    e.setCancelled(true);
                }
            }
        }
    }

}
