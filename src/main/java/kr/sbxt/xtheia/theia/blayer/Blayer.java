package kr.sbxt.xtheia.theia.blayer;

import kr.sbxt.xtheia.theia.blayer.command.CommandBlayer;
import org.bukkit.plugin.java.JavaPlugin;

public final class Blayer extends JavaPlugin
{
	public static Blayer Plugin;
	
	@Override
	public void onEnable()
	{
		getCommand("blayer").setExecutor(CommandBlayer.INSTANCE);
		getCommand("blayer").setTabCompleter(CommandBlayer.INSTANCE);
		Plugin = this;
	}
	
	@Override
	public void onDisable()
	{
		getServer().getScheduler().getPendingTasks().forEach(task ->
		{
			if (task.getOwner().getName().equals(Plugin.getName()))
			{
				task.cancel();
			}
		});
		// Plugin shutdown logic
	}
}
