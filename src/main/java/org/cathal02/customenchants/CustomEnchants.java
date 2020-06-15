package org.cathal02.customenchants;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.cathal02.ArmorEquip.ArmorListener;
import org.cathal02.GUI.GUIHandler;
import org.cathal02.enchantments.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CustomEnchants extends JavaPlugin implements Listener {
    public SpeedEnchantment speedEnchantment;
    public StrengthEnchantment strengthEnchantment;
    public FireResistEnchantment fireResistEnchantment;
    public JumpBoostEnchantment jumpBoostEnchantment;
    public WaterBreathingEnchantment waterBreathingEnchantment;
    public NightVisionEnchantment nightVisionEnchantment;
    public ImplantsEnchantment implantsEnchantment;
    public ObsidianBreakerEnchantment obsidianBreakerEnchantment;
    InitialEnchantment initialEnchantment;

    public Map<String, BaseEnchant> enchantments;

    public GUIHandler guiHandler;

    public EnchantmentHandler enchantmentHandler = new EnchantmentHandler(this);

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new ArmorListener(getConfig().getStringList("blocked")), this);

        enchantments = new HashMap<>();

        guiHandler = new GUIHandler(this);
        initialEnchantment = new InitialEnchantment(101, this);
        speedEnchantment =  new SpeedEnchantment(102, this);
        strengthEnchantment = new StrengthEnchantment(103, this);
        fireResistEnchantment = new FireResistEnchantment(104, this);
        jumpBoostEnchantment = new JumpBoostEnchantment(105, this);
        waterBreathingEnchantment = new WaterBreathingEnchantment(106, this);
        nightVisionEnchantment = new NightVisionEnchantment(107, this);
        implantsEnchantment = new ImplantsEnchantment(108, this);
        obsidianBreakerEnchantment = new ObsidianBreakerEnchantment(109, this);

        LoadEnchantments();

        Bukkit.getPluginManager().registerEvents(initialEnchantment, this);
        Bukkit.getPluginManager().registerEvents(speedEnchantment, this);
        Bukkit.getPluginManager().registerEvents(strengthEnchantment, this);
        Bukkit.getPluginManager().registerEvents(fireResistEnchantment, this);
        Bukkit.getPluginManager().registerEvents(jumpBoostEnchantment, this);
        Bukkit.getPluginManager().registerEvents(waterBreathingEnchantment, this);
        Bukkit.getPluginManager().registerEvents(nightVisionEnchantment, this);
        Bukkit.getPluginManager().registerEvents(implantsEnchantment, this);
        Bukkit.getPluginManager().registerEvents(obsidianBreakerEnchantment, this);

        Bukkit.getPluginManager().registerEvents(guiHandler, this);

        getCommand("enchanter").setExecutor(new EnchantmentCommand(this));
    }

    private void LoadEnchantments() {
        try {
            try {
                Field f = Enchantment.class.getDeclaredField("acceptingNew");
                f.setAccessible(true);
                f.set(null, true);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try{
                Enchantment.registerEnchantment(initialEnchantment);
                Enchantment.registerEnchantment(strengthEnchantment);
                Enchantment.registerEnchantment(speedEnchantment);
                Enchantment.registerEnchantment(fireResistEnchantment);
                Enchantment.registerEnchantment(jumpBoostEnchantment);
                Enchantment.registerEnchantment(waterBreathingEnchantment);
                Enchantment.registerEnchantment(nightVisionEnchantment);
                Enchantment.registerEnchantment(implantsEnchantment);
                Enchantment.registerEnchantment(obsidianBreakerEnchantment);
            } catch (IllegalArgumentException e)
            {
                System.out.println("Enchantments already registered.");
            }
        } catch(Exception e)
        {
            e.printStackTrace();;
        }

    }

    @SuppressWarnings("unchecked")
    public void onDisable() {
        try{
            Field byIDField = Enchantment.class.getDeclaredField("byId");
            Field byNameField = Enchantment.class.getDeclaredField("byName");

            byIDField.setAccessible(true);
            byNameField.setAccessible(true);

            HashMap<Integer, Enchantment> byId = (HashMap<Integer, Enchantment>) byIDField.get(null);
            HashMap<Integer, Enchantment> byName = (HashMap<Integer, Enchantment>) byNameField.get(null);


            for(Enchantment enchant : enchantments.values())
            {
                if(byId.containsKey(enchant.getId())){
                    byId.remove(enchant.getId());
                }

                if(byName.containsKey(enchant.getName()))
                {
                    byName.remove(enchant.getName());
                }
            }


        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
