package fr.utbm.ai;

import fr.utbm.block.Block;
import fr.utbm.entity.EntityAnimal;

public class AIGoTo extends AIBase{
	
	private float objX;
	private int jumpC,walkC;
	
	
	public AIGoTo(EntityAnimal e) {
		super(e);
	}
	public void setControls(int j, int w){
		this.jumpC = j;
		this.walkC = w;
	}
	public void setObjective(float x){
		this.objX = x;
		this.isFinish = false;
	}
	
	@Override
	public Action updateTask() {
		Action choice = null;
		if(Math.abs(objX-animal.getPosX()) > 8){
			int dir = 0,act = 0;
			boolean isBlock = false;
			Block b;
			
			if(objX-animal.getPosX() > 0){
				dir = 1;
				b = animal.getWorldIn().getBlock((animal.getPosX() + animal.getWidth())/16 + 1, animal.getPosY()/16);
				if(b != null){
					if(b.isSolid()){
						isBlock = true;
					}
				}
				b = animal.getWorldIn().getBlock((animal.getPosX() + animal.getWidth())/16 + 1, animal.getPosY()/16 +1);
				if(b != null){
					if(b.isSolid()){
						isBlock = true;
					}
				}
				b = animal.getWorldIn().getBlock(animal.getPosX()/16 +1, animal.getPosY()/16 - 1);
				if(b == null && animal.isOnGround()){
					isBlock = true;
				}
				
				
				
			}else{
				dir = -1;
				b = animal.getWorldIn().getBlock((animal.getPosX())/16 - 1, animal.getPosY()/16);
				if(b != null){
					if(b.isSolid()){
						isBlock = true;
					}
				}
				b = animal.getWorldIn().getBlock((animal.getPosX())/16 - 1, animal.getPosY()/16 + 1);
				if(b != null){
					if(b.isSolid()){
						isBlock = true;
					}
				}
				b = animal.getWorldIn().getBlock((animal.getPosX())/16 , animal.getPosY()/16 - 1);
				if(b == null && animal.isOnGround()){
					isBlock = true;
				}
			}
			if(isBlock){
				act = jumpC;
			}else{
				act = walkC;
			}
			
			
			
			
			
			choice = new Action(dir,act);
		}
		
		
		
		return choice;
	}

}