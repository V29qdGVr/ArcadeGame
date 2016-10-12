package kazior.game.entities;

import kazior.game.gfx.Screen;
import kazior.game.level.Level;

public abstract class Entity {

	public int x, y;
	protected Level level;
	
	public Entity(Level level) {
		init(level);
	}
	
	public final void init (Level level) {
		this.level = level;
	}
	
	public void setCoords(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public abstract void update();
	
	public abstract void render(Screen screen);
}
