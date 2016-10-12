package kazior.game.level.tiles;

import kazior.game.gfx.Colours;
import kazior.game.gfx.Screen;
import kazior.game.level.Level;

public abstract class Tile {
	
	public static final int DIMENSION = 16;

	public static final Tile[] tiles = new Tile[256];
	public static final Tile VOID = new VoidTile(0, true, false, 0xFF000000);
	public static final Tile GROUND = new GroundTile(1, false, false, 0xFF965C0B, 2);
	public static final Tile GRASS = new GrassTile(2, true, false, 0xFF00FF00, 1);
	public static final Tile HEDGE = new HedgeTile(3, false, false, 0xFF7F7F7F, 3);
	
	//public static final Tile Test = new GrassTile(2, false, false, 0x0000FF, 2);

	private byte id;
	
	private boolean solid;
	private boolean emitter;
	private int levelColour;

	public Tile(int id, boolean isSolid, boolean isEmitter, int levelColour) {
		this.id = (byte) id;

		if (tiles[id] != null)
			throw new RuntimeException("Duplicate tile id on " + id);
		tiles[id] = this;

		this.solid = isSolid;
		this.emitter = isEmitter;
		this.levelColour = levelColour;
	}

	public byte getId() {
		return id;
	}

	public boolean isSolid() {
		return solid;
	}

	public boolean isEmitter() {
		return emitter;
	}

	public int getLevelColour() {
		return levelColour;
	}

	public void update() {

	}
	
	public abstract void render (Screen screen, Level level, int x, int y);

//	public void render(Screen screen, Level level, int x, int y) {
//
//		int sheetTileWidth = 32;
//
//		int tileXSheetIndex = 0;
//
//		if (level.getTile(x / Tile.DIMENSION, (y / Tile.DIMENSION) - 1).getId() == this.getId())
//			tileXSheetIndex += 1;
//		if (level.getTile(x / Tile.DIMENSION, (y / Tile.DIMENSION) + 1).getId() == this.getId())
//			tileXSheetIndex += 4;
//		if (level.getTile((x / Tile.DIMENSION) - 1, y / Tile.DIMENSION).getId() == this.getId())
//			tileXSheetIndex += 8;
//		if (level.getTile((x / Tile.DIMENSION) + 1, y / Tile.DIMENSION).getId() == this.getId())
//			tileXSheetIndex += 2;
//
//		if (tileXSheetIndex == 15) {
//			if (level.getTile((x / Tile.DIMENSION) + 1, (y / Tile.DIMENSION) - 1).getId() != this.getId())
//				tileXSheetIndex += 1;
//			else if (level.getTile((x / Tile.DIMENSION) + 1, (y / Tile.DIMENSION) + 1).getId() != this.getId())
//				tileXSheetIndex += 2;
//			else if (level.getTile((x / Tile.DIMENSION) - 1, (y / Tile.DIMENSION) + 1).getId() != this.getId())
//				tileXSheetIndex += 3;
//			else if (level.getTile((x / Tile.DIMENSION) - 1, (y / Tile.DIMENSION) - 1).getId() != this.getId())
//				tileXSheetIndex += 4;
//		}
//
//		int myNewTileId = tileXSheetIndex + tileYSheetIndex * sheetTileWidth;
//
//		screen.render(x,y,myNewTileId, 99999999, 0x00, 1);
//
//	}

}
