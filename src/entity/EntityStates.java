package entity;

import org.joml.Vector2f;

import core.Utils;
import render.Animation;

public class EntityStates {

	public static final int IDLER = 0;
	public static final int IDLEL = 1;
	
	public static final int RUNNINGR = 2;
	public static final int RUNNINGL = 3;
	
	public static final int ATTACR1 = 4;
	public static final int ATTACR2 = 5;
	public static final int ATTACR3 = 6;
	
	public static final int ATTACL1 = 7;
	public static final int ATTACL2 = 8;
	public static final int ATTACL3 = 9;
	
	public static final int SIZE_KNIGHT = 10;
	
	public static final int BLOOD1 = 0;
	
	public static final int SIZE_BLOOD = 1;
	


	public static EntityState[] getPlayerStates() {
		EntityState[] states = new EntityState[SIZE_KNIGHT];
		states[0] = new EntityState(IDLER, new Animation(12, 8, "knight/idlelR"));
		states[1] = new EntityState(IDLEL, new Animation(12, 8, "knight/idlelL"));
		
		states[2] = new EntityState(RUNNINGR, new Animation(8, 8, "knight/runningR"));
		states[3] = new EntityState(RUNNINGL, new Animation(8, 8, "knight/runningL"));
		
		float times [] = {Utils.getDelta() * 15};
		Transform t = new Transform();
		t.pos.x = 1;
		t.pos.y = -0.3f;
		Entity entites [] = {new Blood(0,t)};
		states[4] = new EntityState(ATTACR1, new Animation(0,8, 14, "knight/attackR")).setLocked(0.6f).setMoveLock(0.6f).setEntitesSpawnOn(times, entites);
		states[5] = new EntityState(ATTACR2, new Animation(8,12, 14, "knight/attackR")).setLocked(0.3f).setMoveLock(0.3f);
		states[6] = new EntityState(ATTACR3, new Animation(12,19, 10, "knight/attackR")).setLocked(0.75f).setMoveLock(0.75f);
		

		states[7] = new EntityState(ATTACL1, new Animation(0,8, 14, "knight/attackL")).setLocked(0.6f).setMoveLock(0.6f);
		states[8] = new EntityState(ATTACL2, new Animation(8,12, 14, "knight/attackL")).setLocked(0.3f).setMoveLock(0.3f);
		states[9] = new EntityState(ATTACL3, new Animation(12,19, 14, "knight/attackL")).setLocked(0.75f).setMoveLock(0.75f);
		
		return states;
	}
	
	public static EntityState [] getBloodStates() {
		EntityState[] states = new EntityState[SIZE_BLOOD];
		states[0] = new EntityState(BLOOD1, new Animation(1,1, "blood/splash"));
		return states;
	}
}
