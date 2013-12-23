package com.me.mygdxgame.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import util.Constants;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;


public class Assets implements Disposable, AssetErrorListener  {
	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();
	private AssetManager assetManager;
	
	//Images
	public AssetPokebola pokebola;
	public AssetLevelDecoration levelDecoration;
	public AssetPuente puente;
	
	// singleton: prevent instantiation from other classes
	private Assets() {}
	
	public void init (AssetManager assetManager) {
		this.assetManager = assetManager;
		
		// set asset manager error handler
		assetManager.setErrorListener(this);
		
		// load texture atlas
		assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS,TextureAtlas.class);
		
		// start loading assets and wait until finished
		assetManager.finishLoading();
		
		Gdx.app.debug(TAG, "# of assets loaded: "+ assetManager.getAssetNames().size);
		
		for (String a : assetManager.getAssetNames()){
			Gdx.app.debug(TAG, "asset: " + a);
		}
		
		TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);
		
		// enable texture filtering for pixel smoothing
		for (Texture t : atlas.getTextures()){
//			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			t.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		}
		
		// create game resource objects
		pokebola = new AssetPokebola(atlas);
		levelDecoration = new AssetLevelDecoration(atlas);
		puente = new AssetPuente(atlas);
		
	}
	
	@Override
	public void error(String fileName, Class type, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '"+ fileName + "'", (Exception)throwable);
	}

	@Override
	public void dispose() {
		assetManager.dispose();
	}
	
	public class AssetPokebola {
		public final AtlasRegion bola;
		public AssetPokebola (TextureAtlas atlas) {
			bola = atlas.findRegion("pokebola");
			if(bola==null)
				Gdx.app.error("Pokebola", "bola is null");
		}
	}
	
	public class AssetLevelDecoration {
		public final AtlasRegion fondo;
		public AssetLevelDecoration (TextureAtlas atlas) {
			fondo = atlas.findRegion("fondo");
			if(fondo==null)
				Gdx.app.error("LevelDecoration", "fondo is null");
		}
	}
	
	public class AssetPuente {
		public final AtlasRegion puenteOrilla;
		public final AtlasRegion puenteCentro;
		public AssetPuente (TextureAtlas atlas) {
			puenteOrilla = atlas.findRegion("puenteorilla");
			puenteCentro = atlas.findRegion("puentecentro");
			if(puenteCentro==null || puenteOrilla==null)
				Gdx.app.error("Puente", "Puente is null");
		}
	}

}
