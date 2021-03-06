package fr.utbm.world;

import fr.utbm.block.Block;
import fr.utbm.block.BlockGlass;
import fr.utbm.render.RenderManager;

public class Chunk {
	public final static int CHUNK_WIDTH = 50;
	public final static int CHUNK_HEIGHT = 400;
	private int chunkID;
	private Block[][] blocks;
	// private Biome biome;

	public Chunk() {
		chunkID = 0;
		blocks = new Block[CHUNK_WIDTH][CHUNK_HEIGHT];
		// biome = new Biome()
	}

	public Chunk(int ID /* Biome b */) {
		chunkID = ID;
		blocks = new Block[CHUNK_WIDTH][CHUNK_HEIGHT];
		// biome = b;
	}

	public int getID() {
		return this.chunkID;
	}

	public Block getBlock(int i, int j) {
		return this.blocks[i][j];
	}

	public void setBlock(int i, int j, Block block) {
		Block b = this.blocks[i][j];
		if (b instanceof BlockGlass) {
			
		} else {
			if (b != null) {

				b.kill();
			}
			this.blocks[i][j] = block;
			RenderManager.addToBlockRender(block);
		}
	}

	public void render() {
		for (int i = 0; i < CHUNK_HEIGHT; i++) {
			for (int j = 0; j < CHUNK_WIDTH; j++) {
				if (blocks[j][i] != null) {
					RenderManager.addToBlockRender(blocks[j][i]);
				}

			}
		}
	}

	public void update() {
		for (int i = 0; i < CHUNK_HEIGHT; i++) {
			for (int j = 0; j < CHUNK_WIDTH; j++) {
				if (blocks[j][i] != null) {
					if (blocks[j][i].isDead()) {
						blocks[j][i] = null;
					} else {
						blocks[j][i].update();
					}
				}

			}
		}
	}

}
