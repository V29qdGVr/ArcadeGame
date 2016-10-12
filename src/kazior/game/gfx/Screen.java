package kazior.game.gfx;

import kazior.game.level.tiles.Tile;

public class Screen {

	// public static final int MAP_WIDTH = 64;
	// public static final int MAP_WIDTH_MASK = MAP_WIDTH - 1;

	public static final byte BIT_MIRROR_X = 0x01;
	public static final byte BIT_MIRROR_Y = 0x02;

	public int[] pixels;

	public int xOffset = 0; // PRZESUNIÊCIE WYRA¯ONE W PIXELACH
	public int yOffset = 0; // PRZESUNIÊCIE WYRA¯ONE W PIXELACH

	public int width;
	public int height;

	public SpriteSheet sheet;

	public Screen(int width, int height, SpriteSheet sheet, int[] pixels) {
		this.width = width;
		this.height = height;
		this.sheet = sheet;

		this.pixels = pixels;
	}

	public void render(int xPos, int yPos, int tile, int mirrorDir, int scale) {
		
		int transparencyColour = 0xFF00FF00;

		xPos -= xOffset;
		yPos -= yOffset;


		boolean mirrorX = (mirrorDir & BIT_MIRROR_X) > 0;
		boolean mirrorY = (mirrorDir & BIT_MIRROR_Y) > 0;

		int scaleMap = scale - 1;
		int xTile = tile % 32;
		int yTile = tile / 32;
		int tileOffset = (xTile * Tile.DIMENSION) + (yTile * Tile.DIMENSION) * sheet.width; // sheetPixel

		for (int y = 0; y < Tile.DIMENSION; y++) {
			int ySheet = y;
			if (mirrorY)
				ySheet = Tile.DIMENSION - 1 - y;
			int yPixel = y + yPos + (y * scaleMap) - ((scaleMap * Tile.DIMENSION) / 2);
			for (int x = 0; x < Tile.DIMENSION; x++) {
				int xSheet = x;
				if (mirrorX)
					xSheet = Tile.DIMENSION - 1 - x;
				int xPixel = x + xPos + (x * scaleMap) - ((scaleMap * Tile.DIMENSION) / 2);



				for (int yScale = 0; yScale < scale; yScale++) {
					if (yPixel + yScale < 0 || yPixel + yScale >= height)
						continue;
					for (int xScale = 0; xScale < scale; xScale++) {
						if (xPixel + xScale < 0 || xPixel + xScale >= width)
							continue;

						int col = sheet.pixels[xSheet + ySheet * sheet.width + tileOffset];
						if (col != transparencyColour) 
							pixels[(xPixel + xScale) + (yPixel + yScale) * width] = col;
					}
				}
			}
		}
	}

	public void setOffset(int xOffset, int yOffset) {

		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

}
