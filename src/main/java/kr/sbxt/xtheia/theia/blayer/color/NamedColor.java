package kr.sbxt.xtheia.theia.blayer.color;


import kr.sbxt.xtheia.theia.ink.color.RGB;

public class NamedColor<T>
{
	private final T name;
	private final RGB colorValue;
	
	public NamedColor(T name, RGB colorValue)
	{
		this.name = name;
		this.colorValue = colorValue;
	}
	
	public NamedColor(T name, int r, int g, int b)
	{
		this.name = name;
		this.colorValue = new RGB(r, g, b);
	}
}
