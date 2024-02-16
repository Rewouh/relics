package com.rewu.relics.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.loot.LootTables;

import com.rewu.relics.Main;
import com.rewu.relics.RandomCollection;
import com.rewu.relics.RelicUtils;
import com.rewu.relics.RelicsLibrary;
import com.rewu.relics.Abstract.Relic;
import com.rewu.relics.Enums.RelicType;

public class CmdRelicsGet implements CommandExecutor 
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by a player.");

            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("Usage: /rget <relic_id>");

            return true;
        }

        Player player = (Player) sender;

        RelicType relicType = RelicType.valueOf(args[0].toUpperCase());

        if (relicType == null) {
            sender.sendMessage("Relic not found!");

            return true;
        }

        Relic relic = RelicsLibrary.GetRelicFromType(relicType);

        player.getInventory().addItem(relic.GetItem());

        sender.sendMessage("Received relic " + RelicUtils.GetColoredName(relic) + "!");

        return true;
    }
}
