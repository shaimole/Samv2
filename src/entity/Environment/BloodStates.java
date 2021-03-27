package entity.Environment;

import entity.EntityState;
import render.Animation;

public class BloodStates {

	public static final int R1 = 0;
	public static final int R2 = 1;
	public static final int R3 = 2;
	public static final int R4 = 3;
	public static final int R5 = 4;

	public static final int L1 = 5;
	public static final int L2 = 6;
	public static final int L3 = 7;
	public static final int L4 = 8;
	public static final int L5 = 9;

	public static final int SIZE_BLOOD = 10;

	public static EntityState[] getBloodStates() {
		EntityState[] states = new EntityState[SIZE_BLOOD];
		states[R1] = new EntityState(R1, new Animation(3, 8, "blood/splashR").dontLoop());
		states[R2] = new EntityState(R2, new Animation(3, 8, "blood2/splashR").dontLoop());
		states[R3] = new EntityState(R3, new Animation(3, 8, "blood3/splashR").dontLoop());
		states[R4] = new EntityState(R4, new Animation(3, 8, "blood4/splashR").dontLoop());
		states[R5] = new EntityState(R5, new Animation(3, 8, "blood5/splashR").dontLoop());

		states[L1] = new EntityState(L1, new Animation(3, 8, "blood/splashL").dontLoop());
		states[L2] = new EntityState(L2, new Animation(3, 8, "blood2/splashL").dontLoop());
		states[L3] = new EntityState(L3, new Animation(3, 8, "blood3/splashL").dontLoop());
		states[L4] = new EntityState(L4, new Animation(3, 8, "blood4/splashL").dontLoop());
		states[L5] = new EntityState(L5, new Animation(3, 8, "blood5/splashL").dontLoop());

		return states;
	}
}
