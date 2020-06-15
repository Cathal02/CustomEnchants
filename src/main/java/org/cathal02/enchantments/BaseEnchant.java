package org.cathal02.enchantments;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.cathal02.ArmorEquip.ArmorEquipEvent;
import org.cathal02.customenchants.CustomEnchants;
import sun.tools.tree.LessOrEqualExpression;

import java.util.ArrayList;
import java.util.List;

public class BaseEnchant extends Enchantment {
    String section;
    int inventorySlot;
    CustomEnchants plugin;

    public BaseEnchant(int id, CustomEnchants instance, String _section) {
        super(id);
        if(!getName().equalsIgnoreCase("Initial"))
        {
            instance.enchantments.put(getName(), this);
        }

        section = _section;
        setInventorySlot(instance);
        plugin = instance;
    }

    @Override
    public String getName() {
        return "Base";
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

    public List<String> getLore()
    {
        List<String> lore = new ArrayList<>();
        try
        {
            List<String> tempLore =  plugin.getConfig().getStringList(section + ".lore");
            if(tempLore != null && tempLore.size() >0 )
            {
                for (String text : tempLore)
                {
                    int cost = plugin.getConfig().getInt(section + ".expCost");
                    if(cost != -1)
                    {
                        text = text.replaceAll("%cost%", Integer.toString(cost));
                    }
                    lore.add(ChatColor.translateAlternateColorCodes('&', text));
                }
            }
        } catch (Exception e)
        {
            System.out.println(ChatColor.RED + "[CustomEnchants] Failed to load " + section + " lore");
        }
        return lore;
    }

    private void setInventorySlot(CustomEnchants plugin)
    {
        try{
            inventorySlot = plugin.getConfig().getInt(section + ".slot");
        } catch (Exception e)
        {
            inventorySlot = 0;
            System.out.println(ChatColor.RED + "Invalid inventory slot!");
        }
    }

    public int getInventorySlot(){
        return  inventorySlot;
    }


    public void addOrRemoveEnchantArmor(PotionEffectType potionEffect, int multiplier, ArmorEquipEvent e)
    {
        ItemStack oldItem = e.getOldArmorPiece();
        ItemStack newItem = e.getNewArmorPiece();

        if(oldItem != null)
        {
            if(oldItem.containsEnchantment(this))
            {
                if(newItem != null)
                {
                    if(!newItem.containsEnchantment(this))
                    {
                        e.getPlayer().removePotionEffect(potionEffect);
                    }
                }
                else
                {
                    e.getPlayer().removePotionEffect(potionEffect);
                }
            }
        }

        if(newItem != null)
        {
            if(newItem.containsEnchantment(this))
            {
                if(!e.getPlayer().hasPotionEffect(potionEffect))
                {

                    e.getPlayer().addPotionEffect(new PotionEffect(potionEffect, 100000, multiplier));
                }
            }
        }
    }

    public Integer getMultiplier(CustomEnchants instance, String section )
    {
        if(instance.getConfig().getConfigurationSection(section) != null)
        {
            int value = instance.getConfig().getInt(section + ".multiplier");
            if(value != -1)
            {
                return value;
            }
            else
            {
                System.out.println(ChatColor.RED + "Cannot find" + section + ".multiplier");
                return 0;
            }
        }
        return 0;
    }

    public String getSection() {return  section;}

}
