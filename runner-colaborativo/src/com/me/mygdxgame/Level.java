package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.me.mygdxgame.game.objects.*;

public class Level {
	public static final String TAG = Level.class.getName();
	
	public enum BLOCK_TYPE {
		EMPTY(0, 0, 0), // black
		BRIDGE(58, 241, 49), // green
		POKEBALL(255, 255, 255); // white
		
		private int color;
		
		private BLOCK_TYPE (int r, int g, int b) {
			color = r << 24 | g << 16 | b << 8 | 0xff;
		}
		public boolean sameColor (int color) {
			return this.color == color;
		}
		public int getColor () {
			return color;
		}
	}
	
	// objects
	public Array<Platform> bridge;
	// decoration
	public Universe universe;
	
	public Level (String filename) {
		init(filename);
	}
	
	private void init (String filename) {
		// objects
		bridge = new Array<Platform>();
		
		// load image file that represents the level data
		Pixmap pixmap = new Pixmap(Gdx.files.internal(filename));
		
		// scan pixels from top-left to bottom-right
		int lastPixel = -1;
		for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) {
		for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++) {
		AbstractGameObject obj = null;
		float offsetHeight = 0;
		
		// height grows from bottom to top
		float baseHeight = pixmap.getHeight() - pixelY;
		
		// get color of current pixel as 32-bit RGBA value
		int currentPixel = pixmap.getPixel(pixelX, pixelY);
		// find matching color value to identify block type at (x,y)
		// point and create the corresponding game object if there is
		// a match
		// empty space
		if (!BLOCK_TYPE.EMPTY.sameColor(currentPixel)) 
			if (BLOCK_TYPE.BRIDGE.sameColor(currentPixel)) {
				if (lastPixel != currentPixel) {
					obj = new Platform();
					float heightIncreaseFactor = 0.25f;
					offsetHeight = -2.5f;
					obj.position.set(pixelX,
							baseHeight * obj.dimension.y
							* heightIncreaseFactor
							+ offsetHeight);
					bridge.add((Platform)obj);
				} else {
					bridge.get(bridge.size - 1).increaseLength(1);
			}
		}
		// player spawn point
		else if
		(BLOCK_TYPE.POKEBALL.sameColor(currentPixel)) {
		}
		// unknown object/pixel color
		else {
			int r = 0xff & (currentPixel >>> 24); //red color channel
			int g = 0xff & (currentPixel >>> 16); //green color channel
			int b = 0xff & (currentPixel >>> 8); //blue color channel
			int a = 0xff & currentPixel; //alpha channel
			Gdx.app.error(TAG, "Unknown object at x<" + pixelX
					+ "> y<" + pixelY
					+ ">: r<" + r
					+ "> g<" + g
					+ "> b<" + b
					+ "> a<" + a + ">");
		}
		lastPixel = currentPixel;
	
		}
		}

		// decoration
		universe = new Universe(pixmap.getWidth());
		universe.position.set(-1, -1);
		// free memory
		pixmap.dispose();
		Gdx.app.debug(TAG, "level '" + filename + "' loaded");
	}

	public void render (SpriteBatch batch) {
		// Draw Mountains
		universe.render(batch);
		// Draw platforms
		for (Platform puente : bridge){
			puente.render(batch);
		}
		
	}
	
}
