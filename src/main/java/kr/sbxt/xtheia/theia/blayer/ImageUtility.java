package kr.sbxt.xtheia.theia.blayer;

import org.jcodec.common.Vector2Int;

import java.awt.image.BufferedImage;

public final class ImageUtility
{
	public static BufferedImage resizeImage(BufferedImage source, int maxLength)
	{
		final var sourceWidth = source.getWidth();
		final var sourceHeight = source.getHeight();
		int _newHeight, _newWidth;
		if (Math.max(sourceHeight, sourceWidth) <= maxLength)
		{
			_newHeight = sourceHeight;
			_newWidth = sourceWidth;
		}
		else
		{
			if (sourceWidth >= sourceHeight)
			{
				final var scale = (double) maxLength / sourceWidth;
				_newWidth = maxLength;
				_newHeight = (int) (scale * sourceHeight);
			}
			else
			{
				final var scale = (double) maxLength / sourceHeight;
				_newHeight = maxLength;
				_newWidth = (int) (scale * sourceWidth);
			}
		}
		return resizeImage(source, _newWidth, _newHeight);
	}
	
	public static BufferedImage resizeImage(BufferedImage source, int newWidth, int newHeight)
	{
		final var resizedSource = new BufferedImage(newWidth, newHeight, source.getType());
		final var graphic = resizedSource.createGraphics();
		graphic.drawImage(source, 0, 0, newWidth, newHeight, null);
		graphic.dispose();
		return resizedSource;
	}
	
	public static int[][] getImagePixelColors(BufferedImage image)
	{
		int width = image.getWidth(), height = image.getHeight();
		var arr = new int[width][height];
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				var pixelColor = image.getRGB(x, y);
				arr[x][y] = pixelColor;
				//					Xylo.log(String.valueOf(pixelColor));
			}
		}
		return arr;
	}
}
