package entity.Player;

import entity.Entity;
import entity.EntityState;
import entity.Environment.BloodStates;
import entity.Transform;
import entity.Environment.Blood;
import render.Animation;

public class PlayerStates {

	public static final int IDLE_PLAYER_R = 0;
	public static final int IDLE_PLAYER_L = 1;

	public static final int RUNNING_PLAYER_R = 2;
	public static final int RUNNING_PLAYER_L = 3;

	public static final int ATTAC_PLAYER_R1 = 4;
	public static final int ATTAC_PLAYER_R2 = 5;
	public static final int ATTAC_PLAYER_R3 = 6;

	public static final int ATTAC_PLAYER_L1 = 7;
	public static final int ATTAC_PLAYER_L2 = 8;
	public static final int ATTAC_PLAYER_L3 = 9;

	public static final int ROLL_PLAYER_L = 10;
	public static final int ROLL_PLAYER_R = 11;

	public static final int BLOCK_PLAYER_L = 12;
	public static final int BLOCK_PLAYER_R = 13;

	public static final int DEATH_PLAYER_L = 14;
	public static final int DEATH_PLAYER_R = 15;

	public static final int SIZE_PLAYER = 16;

	public static EntityState[] getPlayerStates() {
		EntityState[] states = new EntityState[SIZE_PLAYER];
		states[0] = new EntityState(IDLE_PLAYER_R, new Animation(12, 8, "knight/idlelR"));
		states[1] = new EntityState(IDLE_PLAYER_L, new Animation(12, 8, "knight/idlelL"));

		states[2] = new EntityState(RUNNING_PLAYER_R, new Animation(8, 8, "knight/runningR"));
		states[3] = new EntityState(RUNNING_PLAYER_L, new Animation(8, 8, "knight/runningL"));

		states[4] = new EntityState(ATTAC_PLAYER_R1, new Animation(0, 8, 14, "knight/attackR")).setLocked(0.6f)
				.setMoveLock(0.6f).setEntitesSpawnOn(new int[] { 3 }, new Entity[] {
						new Blood(BloodStates.SIZE_BLOOD, new Transform(1, -0.3f)).setParent("player") });
		states[5] = new EntityState(ATTAC_PLAYER_R2, new Animation(8, 12, 14, "knight/attackR")).setLocked(0.3f)
				.setMoveLock(0.3f).setEntitesSpawnOn(new int[] { 3 }, new Entity[] {
						new Blood(BloodStates.SIZE_BLOOD, new Transform(1, -0.3f)).setParent("player") });
		states[6] = new EntityState(ATTAC_PLAYER_R3, new Animation(12, 19, 10, "knight/attackR")).setLocked(0.75f)
				.setMoveLock(0.75f).setEntitesSpawnOn(new int[] { 3 }, new Entity[] {
						new Blood(BloodStates.SIZE_BLOOD, new Transform(1, -0.3f)).setParent("player") });

		states[7] = new EntityState(ATTAC_PLAYER_L1, new Animation(0, 8, 14, "knight/attackL")).setLocked(0.6f)
				.setMoveLock(0.6f).setEntitesSpawnOn(new int[] { 3 },
						new Entity[] { new Blood(BloodStates.SIZE_BLOOD, new Transform(-1, -0.3f)).setDirection(-1)
								.setParent("player") });
		states[8] = new EntityState(ATTAC_PLAYER_L2, new Animation(8, 12, 14, "knight/attackL")).setLocked(0.3f)
				.setMoveLock(0.3f).setEntitesSpawnOn(new int[] { 3 },
						new Entity[] { new Blood(BloodStates.SIZE_BLOOD, new Transform(-1, -0.3f)).setDirection(-1)
								.setParent("player") });

		states[9] = new EntityState(ATTAC_PLAYER_L3, new Animation(12, 19, 14, "knight/attackL")).setLocked(0.75f)
				.setMoveLock(0.75f).setEntitesSpawnOn(new int[] { 3 }, new Entity[] {
						new Blood(BloodStates.SIZE_BLOOD, new Transform(-1, -0.3f)).setDirection(-1).setParent("player") });

		states[10] = new EntityState(ROLL_PLAYER_L, new Animation(12, 16, "knight/rollL").dontLoop());
		states[11] = new EntityState(ROLL_PLAYER_R, new Animation(12, 16, "knight/rollR").dontLoop());

		states[12] = new EntityState(BLOCK_PLAYER_L, new Animation(7, 10, "knight/blockL").dontLoop());
		states[13] = new EntityState(BLOCK_PLAYER_R, new Animation(7, 10, "knight/blockR").dontLoop());

		states[14] = new EntityState(DEATH_PLAYER_L, new Animation(16, 8, "knight/deathL").dontLoop());
		states[15] = new EntityState(DEATH_PLAYER_R, new Animation(16, 8, "knight/deathR").dontLoop());

		return states;
	}
}
