package com.me.mygdxgame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.mygdxgame.assets.Assets;

public class Juego implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	
	//Set variable to rotate
	private int degree = 1;
	
	private static final String TAG = Juego.class.getName();
	private WorldController worldController;
	private WorldRenderer worldRenderer;
	
	private boolean paused;
			
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
	/*  •  LOG_NONE:  This prints no logs. The logging is completely disabled.
		•  LOG_ERROR: This prints error logs only.
		•  LOG_INFO:  This prints error and info logs.
		•  LOG_DEBUG: This prints error, info, and debug logs */
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		// Load assets
		Assets.instance.init(new AssetManager());
		
		worldController = new WorldController();
		worldRenderer = new WorldRenderer(worldController);
		
		// Game world is active on start
		paused = false;
		
		//Getting preferences
		Preferences pref = Gdx.app.getPreferences("settings.prefs");
		int ki = pref.getInteger("ki", 0);
		Gdx.app.debug("ki", Integer.toString(ki));
		
		//In plataforms other than Android, version will be 0
		Gdx.app.log("Version", Integer.toString(Gdx.app.getVersion()));
		
		//Querying plataform type
		String type="";
		switch(Gdx.app.getType()){
		case Desktop:
			type = "Desktop";
			break;
		case Android:
			type = "Android";
			break;
		case WebGL:
			type = "WebGL";
			break;
		default: 
			type = "Other";
		}
		Gdx.app.log("Plataform", type);
		
//		camera = new OrthographicCamera(1, h/w);
//		batch = new SpriteBatch();
//		
//		texture = new Texture(Gdx.files.internal("data/libgdx.png"));
//		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
//		
//		TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);
//		
//		sprite = new Sprite(region);
//		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
//		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
//		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
	}

	@Override
	public void dispose() {
		Gdx.app.log("Dispose Method", "Calling to dispose method");
		worldRenderer.dispose();
		Assets.instance.dispose();
//		batch.dispose();
//		texture.dispose();
	}

	@Override
	public void render() {		
		
		// Do not update game world when paused.
		if (!paused) {
			// Update game world by the time that has passed
			// since last rendered frame.
			worldController.update(Gdx.graphics.getDeltaTime());
		}
		// Sets the clear screen color to: Cornflower Blue
		Gdx.gl.glClearColor(100/255.0f, 149/255.0f, 237/255.0f, 255/255.0f);
//		Each color component needs to be expressed as a 
//		floating-point value ranging between 0 and 1 with a resolution of 8 bits. This is the 
//		reason why we are also dividing each color component by the value of 255.0f 
//		(8 bit = 2^8 = 256 = [0..255] distinct levels per color component).
		
		
		// Clears the screen
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		// Render game world to screen
		worldRenderer.render();
		
		
//		batch.setProjectionMatrix(camera.combined);
//		batch.begin();
//		
//		//Playing with logging
////		Gdx.app.debug("Rotate", ""+sprite.getRotation());
//		if(sprite.getRotation() > 15 || sprite.getRotation() < -15)
//			degree *= -1;
//		sprite.rotate(degree);
//		
//		sprite.draw(batch);
//		batch.end();
		
//		Querying memory usage
//		long memUsageJavaHeap = Gdx.app.getJavaHeap();
//		Gdx.app.debug("Java heap", ""+memUsageJavaHeap/1024+"KB");
//		long memUsageNativeHeap = Gdx.app.getNativeHeap();
//		Gdx.app.debug("Native heap", ""+memUsageNativeHeap/1024+"KB");
	}

	@Override
	public void resize(int width, int height) {
		worldRenderer.resize(width, height);
		
//		Gdx.app.log("Width", Integer.toString(width));
//		Gdx.app.log("Height", Integer.toString(height));
	}

	@Override
	public void pause() { //This is a Android event only, in others is called at the end, before dispose
		paused = true;
		
		//Logging a error
		Gdx.app.error("Pause method", "Why did you pause me?, PLAY WITH ME!");
		
		//Storing preferences before leaving
		Preferences pref = Gdx.app.getPreferences("settings.prefs");
		int ki = pref.getInteger("ki");
		pref.putInteger("ki", ++ki);
		pref.flush();
	}

	@Override
	public void resume() { //This is a Android event only
		Assets.instance.init(new AssetManager());
		paused = false;
		Gdx.app.log("Resume method", "Calling to resume method");
	}
}
