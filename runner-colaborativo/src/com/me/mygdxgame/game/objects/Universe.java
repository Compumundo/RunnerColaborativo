package com.me.mygdxgame.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.me.mygdxgame.assets.Assets;

public class Universe extends AbstractGameObject{
	private TextureRegion regUniverse;
	
	private int length;
	
	public Universe(int length) {
		this.length = length;
		init();
	}
	
	private void init () {
		dimension.set(10, 6);
		regUniverse = Assets.instance.levelDecoration.fondo;
		
		// shift universe and extend length
		origin.x = -dimension.x*2;
		origin.y = -dimension.y*1.4f;
		length += dimension.x * 4;
	}
	
	
	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		float xRel = dimension.x;
		float yRel = dimension.y;
		
		// Universe background spans the whole level
		int universeLength = 0;
		universeLength += MathUtils.ceil(length / (2 * dimension.x));
		universeLength += MathUtils.ceil(0.5f);
		
		for (int i = 0; i < universeLength; i++) {
			for (int j = 0; j < universeLength; j++) {
				reg = regUniverse;
				batch.draw(reg.getTexture(),
						origin.x + xRel,   position.y + origin.y + yRel,
						origin.x,   origin.y,
						dimension.x, dimension.y,
						scale.x, scale.y,
						rotation,
						reg.getRegionX(), reg.getRegionY(),
						reg.getRegionWidth(), reg.getRegionHeight(),
						false, false);
				xRel += dimension.x;
			}
			yRel += dimension.y;
			xRel = dimension.x;
		}
		// reset color to white
		batch.setColor(1, 1, 1, 1);
	}
	
}
