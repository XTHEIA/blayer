package kr.sbxt.xtheia.theia.blayer.color;

import org.bukkit.Material;

public final class ColorUtility
{
	public static MaterialColor getNearestNamedColor(int r, int g, int b)
	{
		int dif = 999999999;
		MaterialColor def = null;
		for (final var colorDef : _MATERIAL_COLOR_DEFINITIONS)
		{
			final int
					dif_R = colorDef.r - r,
					dif_G = colorDef.g - g,
					dif_B = colorDef.b - b;
			
			final int _dif = (dif_R * dif_R) + (dif_G * dif_G) + (dif_B * dif_B);
			
			if (_dif < dif)
			{
				dif = _dif;
				def = colorDef;
			}
		}
		return def;
	}
	
	private final static MaterialColor[] _MATERIAL_COLOR_DEFINITIONS = {
			new MaterialColor(Material.REDSTONE_BLOCK, 143, 22, 5),
			new MaterialColor(Material.BLUE_CONCRETE, 44, 45, 137),
			new MaterialColor(Material.PURPLE_CONCRETE, 98, 30, 149),
			new MaterialColor(Material.WHITE_CONCRETE, 236, 251, 246),
			new MaterialColor(Material.YELLOW_CONCRETE, 239, 174, 22),
			new MaterialColor(Material.LIME_CONCRETE, 92, 163, 23),
			new MaterialColor(Material.ORANGE_CONCRETE, 221, 97, 1),
			new MaterialColor(Material.BLACK_CONCRETE, 10, 11, 15),
			new MaterialColor(Material.LIGHT_BLUE_CONCRETE, 37, 136, 195),
			new MaterialColor(Material.CYAN_CONCRETE, 20, 116, 132),
			new MaterialColor(Material.SNOW_BLOCK, 239, 251, 247),
			new MaterialColor(Material.LIGHT_GRAY_CONCRETE, 121, 121, 109),
			new MaterialColor(Material.SANDSTONE, 225, 214, 169),
			new MaterialColor(Material.END_STONE_BRICKS, 228, 242, 183),
			new MaterialColor(Material.GOLD_BLOCK, 245, 203, 47),
			new MaterialColor(Material.RAW_IRON_BLOCK, 207, 170, 144),
			new MaterialColor(Material.NETHER_BRICKS, 44, 24, 26),
			new MaterialColor(Material.GREEN_WOOL, 87, 110, 22),
			new MaterialColor(Material.DIAMOND_BLOCK, 109, 240, 223),
			new MaterialColor(Material.RED_SANDSTONE, 188, 103, 36),
			new MaterialColor(Material.HAY_BLOCK, 194, 170, 35),
			new MaterialColor(Material.RAW_GOLD_BLOCK, 212, 151, 31),
			new MaterialColor(Material.COPPER_BLOCK, 178, 96, 67),
			new MaterialColor(Material.CRYING_OBSIDIAN, 36, 0, 60),
			new MaterialColor(Material.MYCELIUM, 95, 84, 85),
			new MaterialColor(Material.OAK_PLANKS, 165, 135, 80),
			new MaterialColor(Material.STRIPPED_ACACIA_LOG, 161, 83, 44),
			new MaterialColor(Material.STRIPPED_JUNGLE_LOG, 159, 111, 75),
			new MaterialColor(Material.GRASS_BLOCK, 130, 155, 74),
			new MaterialColor(Material.DIRT, 213, 155, 106),
			new MaterialColor(Material.EMERALD_BLOCK, 139, 225, 161),
			new MaterialColor(Material.LIGHT_BLUE_TERRACOTTA, 186, 183, 197),
			new MaterialColor(Material.LIME_TERRACOTTA, 177, 185, 153),
			new MaterialColor(Material.PACKED_ICE, 200, 219, 253),
			new MaterialColor(Material.PRISMARINE_BRICKS, 168, 203, 192),
	};
}
