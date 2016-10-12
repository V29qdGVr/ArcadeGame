package kazior.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import kazior.game.entities.Enemy;
import kazior.game.entities.Player;
import kazior.game.gfx.Screen;
import kazior.game.gfx.SpriteSheet;
import kazior.game.level.Level;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	//public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 1;
	public static final String NAME = "Game";

	private JFrame frame;

	public boolean running = false;
	public int tickCount = 0;

	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	private int[] colours = new int[6 * 6 * 6];

	private Screen screen;
	public InputHandler input;
	public Level level;
	private Player player;

	public Game() {
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		frame = new JFrame(NAME);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		frame.add(this, BorderLayout.CENTER);
		frame.pack();

		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void init() { 
		int index = 0;
		for (int r = 0; r < 6; r++)
			for (int g = 0; g < 6; g++)
				for (int b = 0; b < 6; b++) {
					int rr = (r * 255 / 5);
					int gg = (g * 255 / 5);
					int bb = (b * 255 / 5);

					colours[index++] = rr << 16 | gg << 8 | bb;
				}

		screen = new Screen(WIDTH, HEIGHT, new SpriteSheet("/sprite_sheet.png"), pixels);
		input = new InputHandler(this);
		level = new Level("/levels/water_test_level.png");
		
		
		String playerName = "";
		//playerName = JOptionPane.showInputDialog(this, "Please enter a username");
		player = new Player(level, 0, 0, input, playerName);
		
		
		
		//level.addEntity...
		level.addPlayer(player);
		level.addEnemy(new Enemy(level,"Enemy",50,50,1));
		level.addEnemy(new Enemy(level,"Enemy",50,50,1));
		level.addEnemy(new Enemy(level,"Enemy",50,50,1));
		level.addEnemy(new Enemy(level,"Enemy",50,50,1));
		level.addEnemy(new Enemy(level,"Enemy",50,50,1));

	}

	public synchronized void start() {
		running = true;
		new Thread(this).start();
	}

	public synchronized void stop() {
		running = false;
	}

	public void run() {

		long lastTime = System.nanoTime(); // czas rozpoczêcia ostatniego obrotu
		long lastTimer = System.currentTimeMillis(); // sluzy nam do odmierzania
		// sekundy

		double TARGET_UPS = 60; // zamierzony UPS
		double OPTIMAL_UPDATE_TIME = 1000000000D / TARGET_UPS; // zamierzony
		// czas Update'a

		int current_ups = 0; // obecna ilosc wywolan update'a
		int current_fps = 0; // obecna ilosc wywolan render'a

		/*----------------------*/
		double delta = 0;
		/*----------------------*/

		init();

		while (running) { // g³owna petla gry

			long now = System.nanoTime();
			long turnLength = now - lastTime; // dlugosc ostatniego obrotu pêtli
			lastTime = now;

			/*----------------------------------------------*/
			delta += turnLength / OPTIMAL_UPDATE_TIME;
			/*----------------------------------------------*/

			boolean shouldRender = true; // jeœli to bêdzie "false" to render()
			// bedzie wywolywane wtedy co
			// update()
			// a jeœli true, to render() bedzie wywolywane "ile wlezie"

			/*-----------------------------*/
			while (delta >= 1) {
				current_ups++;
				update();
				delta--;
				shouldRender = true;
			}
			/*-----------------------------*/

//			try {
//				Thread.sleep(2); // œpi 2 milisekundy, ¿eby zmniejszyæ liczbe
//				// FPS
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}

			if (shouldRender) { // JEŒLI MO¯NA RYSOWAÆ, INKREMENTUJE KLATKÊ I
				// RYSUJE
				current_fps++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer >= 1000) { // CO 1000
				// MILISEKUND
				// WYPISUJE
				// LICZBE
				// KLATEK I
				// UPDATOW
				lastTimer += 1000;
				System.out.println("fps: " + current_fps + " ups: " + current_ups);
				current_fps = 0;
				current_ups = 0;
			}
		}
	}

	public void update() {
		tickCount++;
		level.update();
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		int xOffset = player.x - (screen.width / 2);
		int yOffset = player.y - (screen.height / 2);

		level.renderTiles(screen, xOffset, yOffset);
		
		//level.renderEntities(screen);
		level.renderEnemies(screen);
		level.renderPlayers(screen);

		Graphics g = bs.getDrawGraphics();
		//g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		g.fillRect(0, 0, getWidth(), getHeight());
		int ww = WIDTH * SCALE;
		int hh = HEIGHT * SCALE;
		int xo = (getWidth() - ww) / 2;
		int yo = (getHeight() - hh) / 2;
		g.drawImage(image, xo, yo, ww, hh, null);
		
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		new Game().start();
	}
}