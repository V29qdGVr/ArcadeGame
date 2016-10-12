package kazior.game.entities;

import kazior.game.gfx.Font;
import kazior.game.gfx.Screen;
import kazior.game.level.Level;
import kazior.game.level.tiles.Tile;

public abstract class Mob extends Entity {

	protected String name;
	protected int speed;
	protected int numSteps = 0;
	protected boolean isMoving;
	protected int movingDir = 1;
	protected int scale = 1;

	public Mob(Level level, String name, int x, int y, int speed) {
		super(level);
		this.name = name;
		this.x = x;
		this.y = y;
		this.speed = speed;
	}

	public void move(int xa, int ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			numSteps--;
			return;
		}
		numSteps++;
		if (!hasCollided(xa, ya)) {
			x += xa * speed;
			y += ya * speed;
		}
	}

	public boolean hasCollided(int xa, int ya) { // czy moj collision box (nie
		// postac 32x32) sasiaduje z
		// solidami
		int xMin = 0;
		int xMax = Tile.DIMENSION - 1;
		int yMin = Tile.DIMENSION / 2 - 1;
		int yMax = Tile.DIMENSION - 1;

		for (int x = xMin; x < xMax; x++)
			// if (isSolidTile(xa, ya, x, yMin))
			if (isDifferentTile(xa, ya, x, yMin))
				return true;

		for (int x = xMin; x < xMax; x++)
			// if (isSolidTile(xa, ya, x, yMax))
			if (isDifferentTile(xa, ya, x, yMax))
				return true;

		for (int y = yMin; y < yMax; y++)
			// if (isSolidTile(xa, ya, xMin, y))
			if (isDifferentTile(xa, ya, xMin, y))
				return true;

		for (int y = yMin; y < yMax; y++)
			// if (isSolidTile(xa, ya, xMax, y))
			if (isDifferentTile(xa, ya, xMax, y))
				return true;

		return false;
	}

	protected boolean isDifferentTile(int xa, int ya, int x, int y) {
		if (level == null)
			return false;

		int currentPointX = this.x + x;
		int currentPointY = this.y + y;

		int currentTileX;
		if (currentPointX >= 0)
			currentTileX = currentPointX / Tile.DIMENSION;
		else
			currentTileX = currentPointX / Tile.DIMENSION - 1; // zamiast >> 4

		int currentTileY;
		if (currentPointY >= 0)
			currentTileY = currentPointY / Tile.DIMENSION;
		else
			currentTileY = currentPointY / Tile.DIMENSION - 1; // zamiast >> 4

		Tile currentTile = level.getTile(currentTileX, currentTileY);

		//////////////////////////////////////////////////////////////

		int checkedPointX = this.x + x + xa;
		int checkedPointY = this.y + y + ya;

		int checkedTileX;
		if (checkedPointX >= 0)
			checkedTileX = checkedPointX / Tile.DIMENSION;
		else
			checkedTileX = checkedPointX / Tile.DIMENSION - 1; // zamiast >> 4

		int checkedTileY;
		if (checkedPointY >= 0)
			checkedTileY = checkedPointY / Tile.DIMENSION;
		else
			checkedTileY = checkedPointY / Tile.DIMENSION - 1; // zamiast >> 4

		Tile checkedTile = level.getTile(checkedTileX, checkedTileY);

		///////////////////////////////////////////////////////////////

		return (!currentTile.equals(checkedTile));

	}

	protected boolean isSolidTile(int xa, int ya, int x, int y) {
		if (level == null)
			return false;

		int checkedPointX = this.x + x + xa;
		int checkedPointY = this.y + y + ya;

		int checkedTileX;
		if (checkedPointX >= 0)
			checkedTileX = checkedPointX / Tile.DIMENSION;
		else
			checkedTileX = checkedPointX / Tile.DIMENSION - 1; // zamiast >> 4

		int checkedTileY;
		if (checkedPointY >= 0)
			checkedTileY = checkedPointY / Tile.DIMENSION;
		else
			checkedTileY = checkedPointY / Tile.DIMENSION - 1; // zamiast >> 4

		Tile checkedTile = level.getTile(checkedTileX, checkedTileY);

		return checkedTile.isSolid();


		//		int lastTileX = (this.x + x) / Tile.DIMENSION;
		//		int lastTileY = (this.y + y)  / Tile.DIMENSION;
		//		
		//		int newTileX = (this.x + x + xa) / Tile.DIMENSION;
		//		int newTileY = (this.y + y + ya) / Tile.DIMENSION;
		//		
		//		Tile lastTile = level.getTile(lastTileX, lastTileY);
		//		Tile newTile = level.getTile(newTileX, newTileY);
		//		
		//		if (!lastTile.equals(newTile) && newTile.isSolid())
		//			return true;
		//
		//		return false;
	}

	public String getName() {
		return name;
	}
	
}
