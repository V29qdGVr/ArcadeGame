package kazior.game.level;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import kazior.game.entities.Enemy;
import kazior.game.entities.Entity;
import kazior.game.entities.Player;
import kazior.game.gfx.Screen;
import kazior.game.level.tiles.Tile;

public class Level {

	private byte[] tiles;
	public int levelWidth;
	public int levelHeight;
	
	private int imageWidth;
	private int imageHeight;
	
//	public List<Entity> entities;
	public List<Enemy> enemies;
	public List<Player> players; 
	
	private String imagePath;
	private BufferedImage image;

	public Level(String imagePath) {
		if (imagePath != null) {
			this.imagePath = imagePath;
			this.loadLevelFromFile();
		} else {
			this.levelWidth = 64;
			this.levelHeight = 64;
			tiles = new byte[levelWidth * levelHeight];
			this.generateLevel();
		}

//		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		players = new ArrayList<Player>();		
	}


	private void loadLevelFromFile() {
		try {
			this.image = ImageIO.read(Level.class.getResource(this.imagePath));
			this.imageWidth = image.getWidth();
			this.imageHeight = image.getHeight();
			
			this.levelWidth = imageWidth * 2;
			this.levelHeight = imageWidth * 2;
			
			tiles = new byte[levelWidth * levelHeight];
			this.loadTiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadTiles() {
		int[] tileColours = this.image.getRGB(0, 0, imageWidth, imageHeight, null, 0, imageWidth);
		for (int y = 0; y < imageHeight; y++)
			for (int x = 0; x < imageWidth; x++)
				tileCheck: for (Tile t : Tile.tiles)
					if (t != null && t.getLevelColour() == tileColours[x + y * imageWidth]) {
						this.tiles[(x * 2 + 0) + (y * 2 + 0) * levelWidth] = t.getId();
						this.tiles[(x * 2 + 1) + (y * 2 + 0) * levelWidth] = t.getId();
						this.tiles[(x * 2 + 0) + (y * 2 + 1) * levelWidth] = t.getId();
						this.tiles[(x * 2 + 1) + (y * 2 + 1) * levelWidth] = t.getId();
						break tileCheck;
					}
	}

	private void saveLevelToFile() {
		try {
			ImageIO.write(image, "png", new File(Level.class.getResource(this.imagePath).getFile()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void alterTile(int x, int y, Tile newTile) {
		this.tiles[x + y * levelWidth] = newTile.getId();
		//image.setRGB(x, y, newTile.getLevelColour());
	}

	public void generateLevel() {
		for (int y = 0; y < levelHeight; y++)
			for (int x = 0; x < levelWidth; x++)
				if (x * y % 10 < 7)
					tiles[x + y * levelWidth] = Tile.GRASS.getId();
				else
					tiles[x + y * levelWidth] = Tile.GROUND.getId();
	}

	public void update() {

//		for (Entity e : entities)
//			e.update();

		for (Enemy e : enemies)
			e.update();
		
		for (Player p : players)
			p.update();
		

		for (Tile t : Tile.tiles) {
			if (t == null)
				break;
			t.update();
		}
	}
	
//	public List<Entity> getEntities() {
//		return entities;
//	}
	
	public List<Enemy> getEnemies() {
		return enemies;
	}
	
	public List<Player> getPlayers() {
		return players;
	}

	public void renderTiles(Screen screen, int xOffset, int yOffset) { // CAMERA
		if (xOffset < 0)
			xOffset = 0;
		if (xOffset > ((levelWidth * Tile.DIMENSION)) - screen.width)
			xOffset = ((levelWidth * Tile.DIMENSION) - screen.width);
		if (yOffset < 0)
			yOffset = 0;
		if (yOffset > ((levelHeight * Tile.DIMENSION)) - screen.height)
			yOffset = ((levelHeight * Tile.DIMENSION) - screen.height);

		screen.setOffset(xOffset, yOffset);

		for (int y = (yOffset / Tile.DIMENSION); y < (yOffset + screen.height) / Tile.DIMENSION + 1; y++)
			for (int x = (xOffset / Tile.DIMENSION); x < (xOffset + screen.width) / Tile.DIMENSION + 1; x++)
				getTile(x, y).render(screen, this, x * Tile.DIMENSION, y * Tile.DIMENSION);
	}

//	public void renderEntities(Screen screen) {
//		for (Entity e : entities)
//			e.render(screen);
//	}
	
	public void renderEnemies(Screen screen) {
		for (Enemy e : enemies)
			e.render(screen);
	}
	
	public void renderPlayers(Screen screen) {
		for (Player p : players)
			p.render(screen);
	}

	public Tile getTile(int x, int y) {
		if (0 > x || x >= levelWidth || 0 > y || y >= levelHeight)
			return Tile.VOID;
		return Tile.tiles[tiles[x + y * levelWidth]];

	}

//	public void addEntity(Entity entity) {
//		this.entities.add(entity);
//	}
	
	public void addEnemy(Enemy enemy) {
		this.enemies.add(enemy);
	}

	public void addPlayer(Player player) {
		this.players.add(player);
	}
	
	public void printTilesIds () {
		System.out.println("printing tiles ids");
		for (int i = 0 ; i < tiles.length ; i++) {
			if (i % levelWidth == 0)
				System.out.println();
			System.out.print(tiles[i] + " ");
			
		}
		
	}
}
