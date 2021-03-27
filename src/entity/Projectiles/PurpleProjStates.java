package entity.Projectiles;

import entity.EntityState;
import render.Animation;

public class PurpleProjStates {

	public static final int PURP_PROJ_SPAWNING = 0;	
	public static final int PURP_PROJ_TRAVEL = 1;
	public static final int PURP_PROJ_DEATH = 2;

	public static final int SIZE_PURP_PROJ = 3;

	
	
	public static EntityState[] getPurpProjStatesL() {
		return getStatesfor("L");
	}
	
	public static EntityState [] getPurpProjStatesR() {
		return getStatesfor("R");
	}
	
	private static EntityState []getStatesfor(String leftOrRight) {
		EntityState[] states = new EntityState[SIZE_PURP_PROJ];
		
		states[PURP_PROJ_SPAWNING] = new EntityState(PURP_PROJ_SPAWNING, new Animation(4, 20, "proj/purple/spawn"+ leftOrRight).dontLoop());
		states[PURP_PROJ_TRAVEL] = new EntityState(PURP_PROJ_TRAVEL, new Animation(8, 4, "proj/purple/travel"+leftOrRight).dontLoop());
		states[PURP_PROJ_DEATH] = new EntityState(PURP_PROJ_DEATH, new Animation(7, 20, "proj/purple/death"+ leftOrRight).dontLoop());
		
		return states;
	}
}
