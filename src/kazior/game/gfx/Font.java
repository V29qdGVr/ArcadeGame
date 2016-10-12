package kazior.game.gfx;

import kazior.game.level.tiles.Tile;

public class Font {

	private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ      " + "0123456789.,:;'\"!?$%()-=+/";

	public static void render(String msg, Screen screen, int x, int y, int scale) {
		msg = msg.toUpperCase();

		for (int i = 0; i < msg.length(); i++) {
			int charIndex = chars.indexOf(msg.charAt(i));
			if (charIndex >= 0)
				screen.render(x + (i * Tile.DIMENSION), y, charIndex + 30 * 32, 0x00, scale);
		}
	}
}
