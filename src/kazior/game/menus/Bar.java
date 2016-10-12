package kazior.game.menus;

import kazior.game.gfx.Screen;
import kazior.game.level.tiles.Tile;

public abstract class Bar {

	protected int maxValue = 80;
	protected int currentValue = 0;
	protected long lastIncreaseTime;
	protected int loadingSpeed = 100;

	protected Bar() {
		lastIncreaseTime = System.currentTimeMillis();
	}

	public int getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(int currentValue) {
		this.currentValue = currentValue;
	}

	public boolean canDig() {
		if (currentValue == maxValue)
			return true;
		return false;
	}

	public void update() {
		if (currentValue != maxValue) {
			long now = System.currentTimeMillis();
			if (now - lastIncreaseTime >= loadingSpeed) {
				currentValue++;
				if (currentValue > maxValue)
					currentValue = maxValue;
				lastIncreaseTime = now;
			}
		}
	}

	public abstract void render(int xPos, int yPos, Screen screen);

	public abstract void render2(int xPos, int yPos, Screen screen);
}
