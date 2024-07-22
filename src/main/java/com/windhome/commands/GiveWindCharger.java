package com.windhome.commands;

import com.windhome.items.WindChargerCreate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveWindCharger implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            player.getInventory().addItem(WindChargerCreate.windChargeCustom);
            player.sendMessage("Você recebeu algo!");
            return true;
        }
        return false;
    }

}
