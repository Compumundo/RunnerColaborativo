package com.me.mygdxgame.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.mygdxgame.assets.Assets;

public class Platform extends AbstractGameObject{
	private TextureRegion regEdge;
	private TextureRegion regMiddle;
	private int length;
	
	public Platform() {
		init();
	}
	
	private void init () {
		dimension.set(1, 1);
		regEdge = Assets.instance.puente.puenteOrilla;
		Gdx.app.log("regEdge", "Es: " + Assets.instance.puente.puenteOrilla);
		regMiddle = Assets.instance.puente.puenteCentro;
		
		// Start length of this platform
		setLength(1);
	}
	
	public void setLength (int length) {
		this.length = length;
	}
	
	public void increaseLength (int amount) {
		setLength(length + amount);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		float relX = 0;
		float relY = 0;
		
		// Draw left edge
		reg = regEdge;
		relX -= dimension.x / 4;
		
//		public void draw (Texture texture,
//				float x, float y,
//				float originX, float originY,
//				float width, float height,
//				float scaleX, float scaleY,
//				float rotation,
//				int srcX, int srcY,
//				int srcWidth, int srcHeight,
//				boolean flipX, boolean flipY);
		batch.draw(reg.getTexture(),
				position.x + relX, position.y + relY,
				origin.x, origin.y,
				dimension.x / 4, dimension.y,
				scale.x, scale.y,
				rotation,
				reg.getRegionX(), reg.getRegionY(),
				reg.getRegionWidth(), reg.getRegionHeight(),
				false, false);
		
		// Draw middle
		relX = 0;
		reg = regMiddle;
		for (int i = 0; i < length; i++) {
			batch.draw(reg.getTexture(),
					position.x + relX, position.y + relY,
					origin.x, origin.y,
					dimension.x, dimension.y,
					scale.x, scale.y,
					rotation,
					reg.getRegionX(), reg.getRegionY(),
					reg.getRegionWidth(), reg.getRegionHeight(),
					false, false);
			relX += dimension.x;
		}
		
		// Draw right edge
		reg = regEdge;
		batch.draw(reg.getTexture(),
				position.x + relX, position.y + relY,
				origin.x + dimension.x / 8, origin.y,
				dimension.x / 4, dimension.y,
				scale.x, scale.y,
				rotation,
				reg.getRegionX(), reg.getRegionY(),
				reg.getRegionWidth(), reg.getRegionHeight(),
				true, false);
	}

}
