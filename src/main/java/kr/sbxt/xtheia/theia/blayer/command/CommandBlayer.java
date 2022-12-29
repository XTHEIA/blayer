package kr.sbxt.xtheia.theia.blayer.command;

import kr.sbxt.xtheia.theia.blayer.Blayer;
import kr.sbxt.xtheia.theia.blayer.color.ColorUtility;
import kr.sbxt.xtheia.theia.ink.Ink;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.RespawnAnchor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jcodec.api.FrameGrab;
import org.jcodec.scale.AWTUtil;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public final class CommandBlayer implements CommandExecutor, TabCompleter
{
	public static final CommandBlayer INSTANCE = new CommandBlayer();
	
	final static int delay = 0;
	final static int interval = 1;
	private static final String dataFolderPath = Blayer.Plugin.getDataFolder().getAbsolutePath() + "\\videos";
	//
	private static final String DELI_FRAMES = "_F_", DELI_PIXELS = ";";
	//
	private static final File sourcesFolder = new File(dataFolderPath);
	//
	final private static BlockData
			black = Bukkit.createBlockData(Material.RESPAWN_ANCHOR),
			white = Bukkit.createBlockData(Material.RESPAWN_ANCHOR, data -> ((RespawnAnchor) data).setCharges(4));
	//
	final static private Function<Integer, BlockData>
			converter_bw_respawnAnchor = (colorInt) -> Integer.parseInt(Integer.toHexString(new Color(colorInt, false).getRGB()).substring(2, 8), 16) <= (0xFFFFFF / 2) ? black : white,
			converter_color = (colorInt) ->
			{
				final var rgb = kr.sbxt.xtheia.theia.ink.color.ColorUtility.rgb(colorInt);
				return Bukkit.createBlockData(ColorUtility.getNearestNamedColor(rgb.r(), rgb.g(), rgb.b()).getMaterial());
			};
	//
	final static private ArrayList<Map<Location, BlockData>> renderData = new ArrayList<>();
	
	private static File getDataFile_color(String sourceName)
	{
		return new File(dataFolderPath, "%s_colors.txt".formatted(sourceName));
	}
	
	private static File getDataFile_blockData(String sourceName)
	{
		return new File(dataFolderPath, "%s_blocks.txt".formatted(sourceName));
	}
	
	private static final String
			// must use lower case!
			STEP_1_MAP = "1_map_color",
			STEP_2_CONVERT = "2_map_blocks",
			STEP_3_PLAY = "3_play";
	
	static
	{
		if (! sourcesFolder.exists())
		{
			sourcesFolder.mkdirs();
		}
	}
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
	{
		final var sourceName = args[0];
		final var sourceVideoFile = new File(dataFolderPath, sourceName);
		
		if (! sourceVideoFile.exists())
		{
			return false;
		}
		
		
		switch (args[1].toLowerCase())
		{
			case STEP_1_MAP ->
			{
				try
				{
					final var duration = Integer.parseInt(args[2]) * 20;
					final var _firstFrame = AWTUtil.toBufferedImage(FrameGrab.getFrameAtSec(sourceVideoFile, 0));
					//					final var _firstFrameResized = ImageUtility.resizeImage(_firstFrame, max);
					//					final var height = _firstFrameResized.getHeight();
					//					final var width = _firstFrameResized.getWidth();
					final var width = _firstFrame.getWidth();
					final var height = _firstFrame.getHeight();
					final var dataFile_color = getDataFile_color(sourceName);
					dataFile_color.delete();
					dataFile_color.createNewFile();
					final var colorWriter = new PrintWriter(new FileWriter(dataFile_color));
					renderData.clear();
					//					dataFile.createNewFile();
					Bukkit.getScheduler().runTaskAsynchronously(Blayer.Plugin, () ->
					{
						try
						{
							Ink.log("mapping...");
							for (int tick = 0; tick <= duration; tick += interval)
							{
								final var sec = 0.05d * tick;
								final var _frameSource = AWTUtil.toBufferedImage(FrameGrab.getFrameAtSec(sourceVideoFile, sec));
								//								final var resized = new BufferedImage(width, height, _frameSource.getType());
								//								final var _resizedGraphic = resized.createGraphics();
								//								_resizedGraphic.drawImage(_frameSource, 0, 0, width, height, null);
								//								_resizedGraphic.dispose();
								
								
								//								final var map = new HashMap<Location, BlockData>();
								
								//								colorWriter.print(tick);
								
								for (int y = 0; y < height; y++)
								{
									for (int x = 0; x < width; x++)
									{
										final var pixelColor = kr.sbxt.xtheia.theia.ink.color.ColorUtility.rgb(_frameSource.getRGB(x, y)).asRGBInt();
										//										map.put(new Location(world, startX + x, Y, startZ + y), colorParser.apply(pixelColor));
										colorWriter.print("%d,%d^%d%s".formatted(x, y, pixelColor, DELI_PIXELS));
									}
								}
								colorWriter.print(DELI_FRAMES);
								//								data.add(map);
								final var process = (float) tick / duration;
								Ink.log("mapping color ... [%d/%d] (%.2f)".formatted(tick, duration, process * 100) + "%");
							}
							colorWriter.close();
						} catch (Exception e)
						{
							e.printStackTrace();
						}
						Ink.log("mapping finished!");
					});
					return true;
					
				} catch (Exception e)
				{
					e.printStackTrace();
					return false;
				}
				
			}
			case STEP_2_CONVERT ->
			{
				try
				{
					final var dataFile_block = getDataFile_blockData(sourceName);
					dataFile_block.delete();
					dataFile_block.createNewFile();
					final var blockWriter = new PrintWriter(new FileWriter(dataFile_block));
					final var colorReader = new Scanner(getDataFile_color(sourceName)).useDelimiter(DELI_FRAMES);
					Bukkit.getScheduler().runTaskAsynchronously(Blayer.Plugin, () ->
					{
						Ink.log("converting...");
						int i = 0;
						while (colorReader.hasNext())
						{
							final var frameData = colorReader.next().replace(DELI_FRAMES, "");
							final var pixels = frameData.split(DELI_PIXELS);
							// 0,0:0
							for (final String pixel : pixels)
							{
								final var data = pixel.split("\\^"); // 0,0 --- 0
								final var colorRGBInt = data[1];
								final var blockData = converter_color.apply(Integer.parseInt(colorRGBInt));
								blockWriter.print("%s^%s%s".formatted(data[0], blockData.getAsString(true), DELI_PIXELS));
							}
							blockWriter.print(DELI_FRAMES);
							Ink.log("converting frame " + i++);
						}
						blockWriter.close();
						colorReader.close();
						Ink.log("converting finished!");
					});
					
					
					return true;
				} catch (Exception e)
				{
					e.printStackTrace();
					return false;
				}
			}
			case STEP_3_PLAY ->
			{
				if (sender instanceof Player player)
				{
					final var world = player.getWorld();
					final var location = player.getLocation();
					final var startX = location.getX();
					final var startZ = location.getZ();
					final var Y = location.getY() - 1;
					try
					{
						final var dataFile_block = getDataFile_blockData(sourceName);
						final var blockReader = new Scanner(dataFile_block).useDelimiter(DELI_FRAMES);
						final var frame = new AtomicInteger(0);
						Bukkit.getScheduler().runTaskTimer(Blayer.Plugin, task ->
						{
							if (blockReader.hasNext())
							{
								final var frameData = blockReader.next().replace(DELI_FRAMES, "");
								final var pixels = frameData.split(DELI_PIXELS);
								final var map = new LinkedHashMap<Location, BlockData>();
								for (final String pixel : pixels)
								{
									final var data = pixel.split("\\^"); // 0,0 --- 0
									final var coords = data[0].split(",");
									
									final var blockData = Bukkit.createBlockData(data[1]);
									map.put(new Location(world, startX + Integer.parseInt(coords[0]), Y, startZ + Integer.parseInt(coords[1])), blockData);
								}
								player.sendMultiBlockChange(map);
								map.clear();
								Ink.log("instant rendered frame " + frame.getAndAdd(1));
							}
							else
							{
								task.cancel();
							}
						}, 0, 1);
						
						return true;
					} catch (Exception e)
					{
					}
					
				}
				
			}
			
			case "_preload" ->
			{
				if (sender instanceof Player player)
				{
					final var world = player.getWorld();
					final var location = player.getLocation();
					final var startX = location.getX();
					final var startZ = location.getZ();
					final var Y = location.getY() - 1;
					try
					{
						Ink.log("loading render data ...");
						final var dataFile_block = getDataFile_blockData(sourceName);
						final var blockReader = new Scanner(dataFile_block).useDelimiter(DELI_FRAMES);
						Bukkit.getScheduler().runTaskAsynchronously(Blayer.Plugin, () ->
						{
							renderData.clear();
							int i = 0;
							while (blockReader.hasNext())
							{
								final var frameData = blockReader.next().replace(DELI_FRAMES, "");
								final var pixels = frameData.split(DELI_PIXELS);
								final var map = new LinkedHashMap<Location, BlockData>();
								for (final String pixel : pixels)
								{
									final var data = pixel.split("\\^"); // 0,0 --- 0
									final var coords = data[0].split(",");
									
									final var blockData = Bukkit.createBlockData(data[1]);
									map.put(new Location(world, startX + Integer.parseInt(coords[0]), Y, startZ + Integer.parseInt(coords[1])), blockData);
								}
								Ink.log("loading frame " + i++);
								renderData.add(map);
							}
							blockReader.close();
							Ink.log("loading finished!");
						});
						return true;
					} catch (Exception e)
					{
						return false;
					}
				}
				
				
			}
			case "_playloaded" ->
			{
				if (sender instanceof Player player)
				{
					final AtomicInteger a = new AtomicInteger(0);
					final var repeatTask = Bukkit.getScheduler().runTaskTimer(Blayer.Plugin, () ->
					{
						final var aVal = a.getAndAdd(1);
						player.sendMultiBlockChange(renderData.get(aVal));
						Ink.log("sent frame " + aVal);
					}, delay, interval);
					Bukkit.getScheduler().runTaskLater(Blayer.Plugin, () -> repeatTask.cancel(), renderData.size() * interval + delay);
					return true;
				}
				
			}
		}
		return false;
	}
	
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
	{
		switch (args.length)
		{
			case 1:
				final var txtFiles = sourcesFolder.listFiles(f -> ! f.getName().endsWith(".txt"));
				if (txtFiles == null)
				{
					return List.of("no txt file found!");
				}
				return Arrays.stream(txtFiles).map(File::getName).toList();
			case 2:
				return List.of(
						STEP_1_MAP, STEP_2_CONVERT, STEP_3_PLAY
						//,"_preload", "_playloaded"
				);
			default:
				return List.of("N/A");
		}
	}
}
