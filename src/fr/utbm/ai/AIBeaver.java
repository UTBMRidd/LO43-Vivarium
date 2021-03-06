package fr.utbm.ai;

import fr.utbm.entity.EntityAnimalBeaver;

public class AIBeaver extends AIAnimal {

	private EntityAnimalBeaver animal;
	private boolean treeChoosen;
	private boolean isHomeFinish;
	private AIGoTo pathFinder;
	private AIBeaverHouse construction;
	
	public AIBeaver(EntityAnimalBeaver e) {
		super(e);
		this.animal = e;
		
		this.isHomeFinish = false;
		this.treeChoosen = false;
		
		this.pathFinder = new AIGoTo(e);
		this.pathFinder.setControls(1, 4);
		this.pathFinder.setObjective(this.animal.getNearestTree());
		
		this.construction = new AIBeaverHouse(e);

	}
	
	@Override
	public Action updateTask() {
		Action actionDecided = null;
		switch (objective) {
		case 0:
			actionDecided = this.pathFinder.updateTask();
			break;
		case 1:
			actionDecided = this.pathFinder.updateTask();
			break;
		case 2:
			actionDecided = new Action(1,0,true);
			break;
		case 3:
			actionDecided = this.construction.updateTask();
			break;
		}
		if(actionDecided.isFinish()){
			objSwitch();
		}
		
		actionDecided.setFinish(isHomeFinish);
		return actionDecided;
	}
	
	@Override
	public void objSwitch(){
		switch(objective){
		case 0 :
			this.pathFinder.setObjective(this.animal.getNearestTree());
			this.objective = 1;
			break;
		case 1 :
			this.objective = 2;
			break;
		case 2 :
			if(this.animal.getCapacity() <=4){
				this.pathFinder.setObjective(this.animal.getNearestTree());
				this.objective = 1;
			}else{
				this.objective = 3;
			}
			break;
		case 3 :
			this.pathFinder.setObjective(this.animal.getNearestTree());
			this.objective = 0;
			break;
		}
		
	}

	
	
	

}
