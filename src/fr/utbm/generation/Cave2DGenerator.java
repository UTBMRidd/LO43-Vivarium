package fr.utbm.generation;

import java.util.ArrayList;

import fr.utbm.world.Chunk;
import fr.utbm.world.Map;

public class Cave2DGenerator extends PseudoRandom{
	private int width = Map.NUMBER_OF_CHUNKS*Chunk.CHUNK_WIDTH;
	private int height = Map.LIMIT_SURFACE-Map.LIMIT_CAVE;
	private ArrayList<ArrayList<Integer>> caves;
	
	public Cave2DGenerator(double seed, long M, double initialPercentageOfFill) 
	{
		super(seed, M);
		initializeCells(initialPercentageOfFill);
	}
	
	
    public void initializeCells(double initialPercentageOfFill) {
    	this.caves = new ArrayList<>();
        for (int i=0; i<width; i++) {
        	ArrayList<Integer> tmp = new ArrayList<>();
            for (int j=0; j<height; j++) {
            	//changing initialPercentageOfFill from percentage to a value between -0.1 et 0.1 
            	if (i == 0 || i >= width || getNextRandom() < ((initialPercentageOfFill*2)-100)/1000){ 
            		tmp.add(1);
            	}
            	else{ tmp.add(0); }
            }
            caves.add(tmp);
        }
     }
    
    public ArrayList<ArrayList<Integer>> generateAndGetCaves()
    {
    	launchAutomaton(1,20);
    	launchAutomaton(2,5);
    	launchAutomaton(1,2);
    	return this.caves;
    }
    
	public void launchAutomaton(int mode, int numIterations) {

		ArrayList<ArrayList<Integer>> tmpCaves = new ArrayList<>();

		while (numIterations > 0) {
			numIterations--;
			tmpCaves.clear();
			for (int i = 0; i < width; i++) {
				ArrayList<Integer> tmpCol = new ArrayList<>();
				for (int j = 0; j < height; j++) {
					tmpCol.add(caves.get(i).get(j));
				}
				tmpCaves.add(tmpCol);
			}

			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {

					// Rule to say if the cell stays alive when at the border
					if (i == 0 || i >= width - 1 || j == 0 || j >= height - 1) {
						tmpCaves.get(i).set(j, 1);
					} else {
						int aliveAround = 0;

						// Number of cells alive around the current cell
						aliveAround += caves.get(i - 1).get(j - 1);
						aliveAround += caves.get(i - 1).get(j);
						aliveAround += caves.get(i - 1).get(j + 1);
						aliveAround += caves.get(i).get(j - 1);
						aliveAround += caves.get(i).get(j + 1);
						aliveAround += caves.get(i + 1).get(j - 1);
						aliveAround += caves.get(i + 1).get(j);
						aliveAround += caves.get(i + 1).get(j + 1);

						// apply B678/S345678
						int currentState = caves.get(i).get(j);
						if (mode == 1) {
							if ((currentState == 0) && (aliveAround >= 6 && aliveAround <= 8)) {
								tmpCaves.get(i).set(j, 1);
							} else if ((currentState == 1) && (aliveAround < 3)) {
								tmpCaves.get(i).set(j, 0);
							}
						}

						// B5678/S5678
						else if (mode == 2) {
							if ((currentState == 0) && (aliveAround >= 5 && aliveAround <= 8)) {
								tmpCaves.get(i).set(j, 1);
							} else if ((currentState == 1) && (aliveAround <4)) {
								tmpCaves.get(i).set(j, 0);
							}
						}
					}
				}
			}
			this.caves = (ArrayList<ArrayList<Integer>>) tmpCaves.clone();
		}
	}
}