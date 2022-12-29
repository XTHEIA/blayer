package kr.sbxt.xtheia.theia.blayer.color;

import org.bukkit.Material;

public class MaterialColor
{
	private final Material mat;
	final int r, g, b;
	
	public MaterialColor(Material name, int r, int g, int b)
	{
		this.mat = name;
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public Material getMaterial()
	{
		return mat;
	}
}
