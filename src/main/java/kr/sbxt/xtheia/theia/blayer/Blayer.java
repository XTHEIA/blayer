package kr.sbxt.xtheia.theia.blayer;

import kr.sbxt.xtheia.theia.blayer.command.CommandBlayer;
import kr.sbxt.xtheia.theia.ink.Ink;
import kr.sbxt.xtheia.theia.ink.TheiaPaperPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Blayer extends TheiaPaperPlugin
{
	public static Blayer Plugin;
	
	@Override
	public void onEnable()
	{
		Plugin = this;
		getCommand("blayer").setExecutor(CommandBlayer.INSTANCE);
		getCommand("blayer").setTabCompleter(CommandBlayer.INSTANCE);
		Ink.logPluginLoaded(this);
		Blayer.log("TheiaPaperPlugin.log()");
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
