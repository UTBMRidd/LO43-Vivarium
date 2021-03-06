package fr.utbm.entity;

import fr.utbm.texture.TextureManager;
import fr.utbm.world.World;

public class EntityVegetalBush extends EntityVegetal{
	
	public final String name = "Bush";

	public EntityVegetalBush(float x, float y, World worldIn) {
		super(x, y, 48, 26, worldIn);
		health = 30;
		maxHealth = 30;
		this.text = TextureManager.getTexture(215);
	}
	
	public void update()
	{
		suffocating();
		voidUnder();
	}
	
	public void voidUnder()
	{
		if(world.getBlock((int)(x/16), (int)((y/16)-1)) == null || world.getBlock((int)((x/16)+1), (int)((y/16)-1)) == null || world.getBlock((int)((x/16)+2), (int)((y/16)-1)) == null)
		{
			dead = true;
		}
	}
}
