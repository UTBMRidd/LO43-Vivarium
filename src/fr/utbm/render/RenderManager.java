package fr.utbm.render;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.utbm.main.DesktopApplication;
import fr.utbm.texture.TextureManager;
import fr.utbm.view.Camera;
import fr.utbm.world.Chunk;
import fr.utbm.world.Map;

public class RenderManager {

	private static SpriteBatch batch;
	private static ArrayList<Renderable> blockRender =  new ArrayList<Renderable>();
	private static ArrayList<Renderable> entitiesRender =  new ArrayList<Renderable>();
	private static float x=0;
	private static float y=0;
	
	public static void setBatch(SpriteBatch sb){
		RenderManager.batch = sb;
	}

	public static void renderAll(){
		Gdx.gl.glClearColor(0.52f,0.66f,0.97f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		drawBackground();
		renderArray(blockRender);
		renderArray(entitiesRender);
		//System.out.println("RENDER-MANAGER : Show " + blockRender.size() + " elements");
		batch.end();
	}
	private static void renderArray(ArrayList<Renderable> rl){
			Iterator<Renderable> iter = rl.iterator();
			while (iter.hasNext()) {
				Renderable r = iter.next();
				if(r.isDead()){
					iter.remove();
				}else{
					r.render(batch);
				}
			}
	}
	public static void cleanRender(){
		blockRender.clear();
	}
	public static void addToBlockRender(Renderable r){
		RenderManager.blockRender.add(r);
	}
	public static void addToEntitiesRender(Renderable r){
		RenderManager.entitiesRender.add(r);
	}
	public static void drawBackground(){
		//500 -> sky [above forest.png]
		//501 -> forest [above Map.LIMIT_SURFACE]
		//502 -> cave [between Map.LIMIT_CAVE and Map.LIMIT_SURFACE]
		//503 -> hell [below Map.LIMIT_CAVE]
		//int backgroundsWidth = 800;
		int backgroundsHeight = 400;
		
		if (x<0) { //left border
			x -= x-16;
		}
		else if (x==0){
			x=16;
		}
		else if (x>Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH*16-Camera.WIDTH) { //right border
			x=Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH*16-Camera.WIDTH-16;
		}
		
		if (y<16) { //bottom of the map
			y -=y-16;
			batch.draw(TextureManager.getTexture(503),x,y);
			batch.draw(TextureManager.getTexture(503),x,y+backgroundsHeight);
		}
		else if (y+16>Chunk.CHUNK_HEIGHT*16-Camera.HEIGHT) { //top border
			y -= (y-Chunk.CHUNK_HEIGHT*16)+Camera.HEIGHT+16;
			batch.draw(TextureManager.getTexture(500),x,y);
			batch.draw(TextureManager.getTexture(500),x,y+backgroundsHeight,0,-200,800,200);
		}
		else if (y < Map.LIMIT_CAVE*16) { 
			batch.draw(TextureManager.getTexture(503),x,y);
			batch.draw(TextureManager.getTexture(503),x,y+400);
			if (y/16+Camera.HEIGHT/16 > Map.LIMIT_CAVE) { //if close to the caves
				batch.draw(TextureManager.getTexture(502),x,Map.LIMIT_CAVE*16);
				batch.draw(TextureManager.getTexture(502),x,Map.LIMIT_CAVE*16+backgroundsHeight);
			}
		}
		else if (y/16 < Map.LIMIT_SURFACE) {
			batch.draw(TextureManager.getTexture(502),x,y);
			batch.draw(TextureManager.getTexture(502),x,y+400);
			if (y/16+Camera.HEIGHT/16 > Map.LIMIT_SURFACE) { //if close to the surface
				batch.draw(TextureManager.getTexture(501),x,Map.LIMIT_SURFACE*16);
				batch.draw(TextureManager.getTexture(500),x,Map.LIMIT_SURFACE*16+backgroundsHeight);
			}
		}
		else if (y/16 < Map.LIMIT_SURFACE+backgroundsHeight/16) {
			batch.draw(TextureManager.getTexture(501),x,Map.LIMIT_SURFACE*16);
			batch.draw(TextureManager.getTexture(500),x,Map.LIMIT_SURFACE*16+backgroundsHeight);
			batch.draw(TextureManager.getTexture(500),x,Map.LIMIT_SURFACE*16+backgroundsHeight*2);
		}
		else {
			batch.draw(TextureManager.getTexture(500),x,y);
			batch.draw(TextureManager.getTexture(500),x,y+backgroundsHeight);
		}
		
		
		
	}
	public static void setBgPos(float x, float y){
		RenderManager.x = x;
		RenderManager.y = y;
	}
}
