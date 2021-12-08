package me.aarow.nobelium.commands;

import me.aarow.nobelium.Hub;
import me.aarow.nobelium.manager.impl.*;
import me.aarow.nobelium.utils.chat.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class BaseCommand implements CommandExecutor {

    private final CommandInfo commandInfo;

    public Hub plugin = Hub.getInstance();

    public ProfileManager profileManager = plugin.getProfileManager();
    public BungeeManager bungeeManager = plugin.getBungeeManager();
    public ReplaceManager replaceManager = plugin.getReplaceManager();
    public ProviderManager providerManager = plugin.getProviderManager();
    public ServerManager serverManager = plugin.getServerManager();
    public QueueManager queueManager = plugin.getQueueManager();

    public BaseCommand(){
        commandInfo = getClass().getDeclaredAnnotation(CommandInfo.class);

        Hub.getInstance().getCommand(commandInfo.name()).setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!commandInfo.permission().isEmpty() && !commandSender.hasPermission(commandInfo.permission())){
            commandSender.sendMessage(Color.translate("&cNo permission."));
            return true;
        }
        if(commandInfo.playerOnly()){
            if(commandSender instanceof ConsoleCommandSender){
                commandSender.sendMessage(Color.translate("&cOnly players can execute this command!"));
                return true;
            }
            execute((Player) commandSender, strings);
            return true;
        }
        execute(commandSender, strings);
        return true;
    }

    public void execute(CommandSender sender, String args[]){}
    public void execute(Player player, String args[]){}
}
