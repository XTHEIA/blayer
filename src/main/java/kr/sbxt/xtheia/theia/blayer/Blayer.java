package kr.sbxt.xtheia.theia.blayer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.RespawnAnchor;
import org.bukkit.plugin.java.JavaPlugin;
import org.jcodec.api.FrameGrab;
import org.jcodec.scale.AWTUtil;

import java.io.File;

public final class Blayer extends JavaPlugin
{
	public static Blayer Plugin;
	
	@Override
	public void onEnable()
	{
		final var logger = getLogger();
		getServer().getPluginManager().registerEvents(new ServerEventListener(), this);
		
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
