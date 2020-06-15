package org.cathal02.enchantments;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.cathal02.ArmorEquip.ArmorEquipEvent;
import org.cathal02.customenchants.CustomEnchants;

public class JumpBoostEnchantment extends BaseEnchant implements Listener {
    int multiplier = 0;

    public JumpBoostEnchantment(int id, CustomEnchants instance) {
        super(id, instance, "jumpBoostEnchantment");
        multiplier = getMultiplier(instance, "jumpBoostEnchantment");
    }

    @Override
    public String getName() {
        return "Jump Boost";
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
        return item.getType().name().endsWith("BOOTS");
    }

    @EventHandler
    public void onArmorEquip(ArmorEquipEvent e)
    {
        addOrRemoveEnchantArmor(PotionEffectType.JUMP,multiplier, e);
    }
}
