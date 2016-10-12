package kazior.game.entities;

import java.util.Random;

import kazior.game.gfx.Screen;
import kazior.game.level.Level;
import kazior.game.level.tiles.Tile;

public class Enemy extends Mob {

	int stepsRandom = 0; //number of steps to make in one direction
	int stepsCounter = 0;
	int directionRandom; // one of 8 possible moves, 0 up, 1 down, 2 left, 3 right and so on...
	int xa;
	int ya;

	public Enemy(Level level, String name, int x, int y, int speed) {
		super(level, name, x, y, speed);

	}

	public void randomStepsAndDirection() {
		Random generator = new Random();
		directionRandom = generator.nextInt(8);

		stepsRandom = (generator.nextInt(10) + 1)*10; // od 1 do 50 krokow

		xa = 0;
		ya = 0;
		
		switch (directionRandom) {
		case 0:
			ya--;
			movingDir = 0;
			break;
		case 1:
			ya++;
			movingDir = 1;
			break;
		case 2:
			xa--;
			movingDir = 2;
			break;
		case 3:
			xa++;
			movingDir = 3;
			break;
		case 4:
			ya--;
			xa++;
			movingDir = 0;
			break;
		case 5:
			ya++;
			xa++;
			movingDir = 1;
			break;
		case 6:
			ya++;
			xa--;
			movingDir = 1;
			break;
		case 7:
			ya--;
			xa--;
			movingDir = 0;
			break;
		}
	}

	@Override
	public void update() {
		
		if (stepsCounter == stepsRandom) {
			randomStepsAndDirection();
			stepsCounter=0;
		}
	


		if (xa != 0 || ya != 0) {
			move(xa, ya);
			isMoving = true;
		} else
			isMoving = false;
		
		
		stepsCounter++;

	}

	@Override
	public void render(Screen screen) {
		int beginningXTile = 0;
		int beginningYTile = 9;


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
	}

}
