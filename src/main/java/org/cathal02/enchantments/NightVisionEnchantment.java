package org.cathal02.enchantments;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.cathal02.ArmorEquip.ArmorEquipEvent;
import org.cathal02.customenchants.CustomEnchants;

public class NightVisionEnchantment extends BaseEnchant implements Listener {
    private int multiplier;

    public NightVisionEnchantment(int id, CustomEnchants instance) {
        super(id, instance, "nightVisionEnchantment");
        multiplier = getMultiplier(instance, "nightVisionEnchantment");
    }

    @Override
    public String getName() {
        return "Night Vision";
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
    public void onArmorEquip(ArmorEquipEvent e)
    {
        addOrRemoveEnchantArmor(PotionEffectType.NIGHT_VISION, multiplier, e);
    }
}
