package org.cathal02.enchantments;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.cathal02.ArmorEquip.ArmorEquipEvent;
import org.cathal02.customenchants.CustomEnchants;

public class InitialEnchantment extends BaseEnchant implements Listener {
    int multiplier = 0;

    public InitialEnchantment(int id, CustomEnchants instance) {
        super(id, instance, "init");
        multiplier = getMultiplier(instance, "init");
    }

    @Override
    public String getName() {
        return "Initial";
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
        return false;
    }

    @EventHandler
    public void onArmorEquip(ArmorEquipEvent e)
    {

    }
}
