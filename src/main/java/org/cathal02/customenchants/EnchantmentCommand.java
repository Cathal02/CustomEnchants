package org.cathal02.customenchants;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnchantmentCommand implements CommandExecutor {
    CustomEnchants plugin;

    public EnchantmentCommand(CustomEnchants customEnchants) {
        plugin = customEnchants;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))
        {
            return true;
        }

        plugin.guiHandler.handlePlayerOpenGUI((Player)sender);

        return true;
    }
}
