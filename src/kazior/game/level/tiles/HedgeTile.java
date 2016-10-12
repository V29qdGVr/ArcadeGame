package kazior.game.level.tiles;

import kazior.game.gfx.Screen;
import kazior.game.level.Level;

public class HedgeTile extends Tile {
	
	private int tileYSheetIndex;

	public HedgeTile(int id, boolean isSolid, boolean isEmitter, int levelColour, int tileYSheetIndex) {
		super(id, isSolid, isEmitter, levelColour);
		this.tileYSheetIndex = tileYSheetIndex;
	}

	@Override
	public void render(Screen screen, Level level, int x, int y) {

		int sheetTileWidth = 32;

		int tileXSheetIndex = 0;

		if (level.getTile(x / Tile.DIMENSION, (y / Tile.DIMENSION) - 1).getId() == this.getId())
			tileXSheetIndex += 1;
		if (level.getTile(x / Tile.DIMENSION, (y / Tile.DIMENSION) + 1).getId() == this.getId())
			tileXSheetIndex += 4;
		if (level.getTile((x / Tile.DIMENSION) - 1, y / Tile.DIMENSION).getId() == this.getId())
			tileXSheetIndex += 8;
		if (level.getTile((x / Tile.DIMENSION) + 1, y / Tile.DIMENSION).getId() == this.getId())
			tileXSheetIndex += 2;

		if (tileXSheetIndex == 15) {
			if (level.getTile((x / Tile.DIMENSION) + 1, (y / Tile.DIMENSION) - 1).getId() != this.getId())
				tileXSheetIndex += 1;
			else if (level.getTile((x / Tile.DIMENSION) + 1, (y / Tile.DIMENSION) + 1).getId() != this.getId())
				tileXSheetIndex += 2;
			else if (level.getTile((x / Tile.DIMENSION) - 1, (y / Tile.DIMENSION) + 1).getId() != this.getId())
				tileXSheetIndex += 3;
			else if (level.getTile((x / Tile.DIMENSION) - 1, (y / Tile.DIMENSION) - 1).getId() != this.getId())
				tileXSheetIndex += 4;
		}

		int myNewTileId = tileXSheetIndex + tileYSheetIndex * sheetTileWidth;

		screen.render(x,y,myNewTileId, 0x00, 1);

	}

}
