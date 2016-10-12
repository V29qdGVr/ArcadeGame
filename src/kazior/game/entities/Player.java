package kazior.game.entities;

import kazior.game.InputHandler;
import kazior.game.gfx.Font;
import kazior.game.gfx.Screen;
import kazior.game.level.Level;
import kazior.game.level.tiles.Tile;
import kazior.game.menus.Bar;
import kazior.game.menus.DiggingBar;
import kazior.game.menus.FiringBar;

public class Player extends Mob {

	private InputHandler input;
	private int scale = 1;
	protected boolean isSwimming = false;
	private String username;
	private Bar diggingBar;
	private Bar firingBar;

	public Player(Level level, int x, int y, InputHandler input, String username) {
		super(level, "Player", x, y, 1);
		this.input = input;
		this.username = username;
		diggingBar = new DiggingBar();
		firingBar = new FiringBar();
	}

	@Override
	public void update() {
		int xa = 0;
		int ya = 0;

		if (input.x.isPressed() && diggingBar.canDig()) {
			dig();
			diggingBar.setCurrentValue(0);
		}

		if (input.left.isPressed()) {
			xa--;
			movingDir = 2;
		}
		if (input.right.isPressed()) {
			xa++;
			movingDir = 3;
		}
		if (input.up.isPressed()) {
			ya--;
			movingDir = 0;
		}
		if (input.down.isPressed()) {
			ya++;
			movingDir = 1;
		}

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			isMoving = true;
		} else
			isMoving = false;
		
		for (Enemy e : level.getEnemies()) {
			if (Math.abs(x - e.getX()) <= Tile.DIMENSION && Math.abs(y-e.getY()) <= Tile.DIMENSION)
				System.out.println("koko");
		}
		
		diggingBar.update();
		firingBar.update();

	}

	public void dig() {

		int newDigX = x / Tile.DIMENSION;
		int newDigY = y / Tile.DIMENSION;

		int leftX, rightX;
		int upY, downY;

		if (movingDir == 0) { // gora

			newDigX += 0;
			newDigY += 0;

		} else if (movingDir == 1) { // dol

			newDigX += 0;
			newDigY += 1;

		} else if (movingDir == 2) { // lewo

			newDigX += -1;
			newDigY += 0;

		} else if (movingDir == 3) { // prawo

			newDigX += 1;
			newDigY += 0;
		}

		if (newDigX % 2 == 0) {
			leftX = newDigX;
			rightX = newDigX + 1;
		} else {
			leftX = newDigX - 1;
			rightX = newDigX;
		}

		if (newDigY % 2 == 0) {
			upY = newDigY;
			downY = newDigY + 1;
		} else {
			upY = newDigY - 1;
			downY = newDigY;
		}

		level.alterTile(leftX, upY, Tile.GROUND);
		level.alterTile(rightX, upY, Tile.GROUND);
		level.alterTile(leftX, downY, Tile.GROUND);
		level.alterTile(rightX, downY, Tile.GROUND);
	}
	
	@Override
	public void render(Screen screen) {
		int beginningXTile = 0;
		int beginningYTile = 22;

		
		int walkingSpeed = 3;
		int stepIndex = (numSteps >> walkingSpeed) % 3;

		
		int xTile = beginningXTile;
		int yTile = beginningYTile;

		xTile = beginningXTile + stepIndex * 2;

		if (movingDir == 0)
			yTile = beginningYTile + 0;
		else if (movingDir == 1)
			yTile = beginningYTile + 2;
		else if (movingDir == 2)
			yTile = beginningYTile + 4;
		else if (movingDir == 3)
			yTile = beginningYTile + 6;

		// 0 - gora
		// 1 - dol
		// 2 - lewo
		// 3 - prawo

		int modifier = Tile.DIMENSION * scale;
		int xOffset = x - modifier / 2;
		int yOffset = y - modifier / 2 - 4;

		screen.render(xOffset, yOffset, xTile + yTile * 32, 0, scale);
		screen.render(xOffset + Tile.DIMENSION, yOffset, xTile + 1 + yTile * 32, 0, scale);
		screen.render(xOffset, yOffset + Tile.DIMENSION, xTile + (yTile + 1) * 32, 0, scale);
		screen.render(xOffset + Tile.DIMENSION, yOffset + Tile.DIMENSION, xTile + 1 + (yTile + 1) * 32, 0, scale);

		if (username != null) {
			Font.render(username, screen, xOffset - ((username.length() - 1) / 2 * Tile.DIMENSION), yOffset - 10, 1);
		}

		diggingBar.render(screen.xOffset + Tile.DIMENSION * 1, screen.yOffset + Tile.DIMENSION * 1, screen);
		firingBar.render(screen.xOffset + Tile.DIMENSION * 1, screen.yOffset + Tile.DIMENSION * 2, screen);
		
	}
}