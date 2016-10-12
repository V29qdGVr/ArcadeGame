package kazior.game.menus;

import kazior.game.gfx.Screen;
import kazior.game.level.tiles.Tile;

public class DiggingBar extends Bar {

	public DiggingBar() {
		maxValue = 80;
		currentValue = 0;
		lastIncreaseTime = System.currentTimeMillis();
		loadingSpeed = 25;
	}

	public void render(int xPos, int yPos, Screen screen) {

		int endId = 0 + 18 * 32;
		int emptyId = endId + 1;
		int fullId = emptyId + 1;

		screen.render(xPos - 2, yPos, endId, 0, 1);

		for (int i = 0; i < currentValue * 2; i += 2)
			screen.render(xPos + i, yPos, fullId, 0, 1);

		for (int i = currentValue * 2; i < maxValue * 2; i += 2)
			screen.render(xPos + i, yPos, emptyId, 0, 1);

		screen.render(xPos + maxValue * 2 + 0, yPos, endId, 0, 1);
	}

	public void render2(int xPos, int yPos, Screen screen) {

		int sheetTileWidth = 32;

		int fullY = 21;
		int leftFullId = 0 + fullY * sheetTileWidth;
		int middleFullId = leftFullId + 1;
		int rightFullId = middleFullId + 1;

		for (int i = 0; i < currentValue; i++) {
			if (i == 0)
				screen.render(xPos, yPos, leftFullId, 0, 1);
			else if (i == maxValue - 1)
				screen.render(xPos + i * Tile.DIMENSION, yPos, rightFullId, 0, 1);
			else
				screen.render(xPos + i * Tile.DIMENSION, yPos, middleFullId, 0, 1);
		}

		int emptyY = 20;
		int leftEmptyId = 0 + emptyY * sheetTileWidth;
		int middleEmptyId = leftEmptyId + 1;
		int rightEmptyId = middleEmptyId + 1;

		for (int i = currentValue; i < maxValue; i++) {
			if (i == 0)
				screen.render(xPos, yPos, leftEmptyId, 0, 1);
			else if (i == maxValue - 1)
				screen.render(xPos + i * Tile.DIMENSION, yPos, rightEmptyId, 0, 1);
			else
				screen.render(xPos + i * Tile.DIMENSION, yPos, middleEmptyId, 0, 1);
		}
	}
}
