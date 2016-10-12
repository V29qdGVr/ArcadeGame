package kazior.game.level.tiles;

import kazior.game.gfx.Screen;
import kazior.game.level.Level;

public class VoidTile extends Tile {

	public VoidTile(int id, boolean isSolid, boolean isEmitter, int levelColour) {
		super(id, isSolid, isEmitter, levelColour);

	}

	@Override
	public void render(Screen screen, Level level, int x, int y) {
	
	}

}
