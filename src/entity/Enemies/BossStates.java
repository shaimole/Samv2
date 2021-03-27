package entity.Enemies;

import org.joml.Vector2f;

import entity.Entity;
import entity.EntityState;
import entity.Environment.BloodStates;
import entity.Transform;
import entity.Environment.Blood;
import entity.Projectiles.PurpleProj;
import entity.Projectiles.PurpleProjStates;
import render.Animation;

public class BossStates {

	public static final int IDLE_L = 0;
	public static final int IDLE_R = 1;

	
	public static final int RUNNING_R = 2;
	public static final int RUNNING_L = 3;
	
	public static final int ATTAC_R1 = 4;
	public static final int ATTAC_R2 = 5;
	public static final int ATTAC_R3 = 6;
	
	public static final int ATTAC_L1 = 7;
	public static final int ATTAC_L2 = 8;
	public static final int ATTAC_L3 = 9;
	
	public static final int ROLL_L = 10;
	public static final int ROLL_R = 11;
	
	public static final int BLOCK_L = 12;
	public static final int BLOCK_R = 13;
	
	public static final int DEATH_L = 14;
	public static final int DEATH_R = 15;
	
	public static final int REVIVE_R = 16;
	
	public static final int BLINK_L = 17;
	public static final int BLINK_R = 18;
	
	public static final int SIZE_BOSS = 19;
	
	
	public static EntityState[] getBossStates() {
		EntityState[] states = new EntityState[SIZE_BOSS];
		states[IDLE_R] = new EntityState(IDLE_R, new Animation(12, 8, "boss/idlelR"));
		states[IDLE_L] = new EntityState(IDLE_L, new Animation(12, 8, "boss/idlelL"));
		
		states[RUNNING_R] = new EntityState(RUNNING_R, new Animation(8, 8, "boss/runningR"));
		states[RUNNING_L] = new EntityState(RUNNING_L, new Animation(8, 8, "boss/runningL"));
		
		states[ATTAC_R1] = new EntityState(ATTAC_R1, new Animation(29, 28, "boss/attackLungeR"))
				.setLocked(0.6f).setMoveLock(0.6f)
				.setEntitesSpawnOn(new int[] {15,16,17,18}, new Entity [] {
						new Blood(BloodStates.SIZE_BLOOD,
								new Transform(-1,-0.3f))
						.setParent("boss"),new Blood(BloodStates.SIZE_BLOOD,
								new Transform(-1,-0.3f))
						.setParent("boss"),new Blood(BloodStates.SIZE_BLOOD,
								new Transform(-1,-0.3f))
						.setParent("boss"),new Blood(BloodStates.SIZE_BLOOD,
								new Transform(-1,-0.3f))
						.setParent("boss")
						}).setMoveOn(new int[] {15,16,17,18});
		
		
		states[ATTAC_R2] = new EntityState(ATTAC_R2, new Animation(8,12, 3, "boss/attackR"))
				.setLocked(0.3f).setMoveLock(0.3f)
				.setEntitesSpawnOn(new int[] {2}, new Entity [] {
						new Blood(
								BloodStates.SIZE_BLOOD,
								new Transform(1,-0.3f)
								)
						.setParent("boss")
						});
		
		
		states[ATTAC_R3] = new EntityState(ATTAC_R3, new Animation(12,19, 10, "boss/attackR").dontLoop())
				.setLocked(0.75f).setMoveLock(0.75f)
				.setEntitesSpawnOn(
						new int[] {5},
						new Entity [] {
								new PurpleProj(
										PurpleProjStates.SIZE_PURP_PROJ,new Transform(1,-0.0f),true)
								.addMovement(new Vector2f(0.2f,0))
								});
		
		states[ATTAC_L1] = new EntityState(ATTAC_L1, new Animation(29, 28, "boss/attackLungeL").dontLoop())
				.setLocked(3f).setMoveLock(3f)
				.setEntitesSpawnOn(new int[] {15,16,17,18}, new Entity [] {new Blood(BloodStates.SIZE_BLOOD,
						new Transform(-1,-0.3f)).setDirection(-1)
						.setParent("boss"),new Blood(BloodStates.SIZE_BLOOD,
								new Transform(-1,-0.3f)).setDirection(-1)
						.setParent("boss"),new Blood(BloodStates.SIZE_BLOOD,
								new Transform(-1,-0.3f)).setDirection(-1)
						.setParent("boss"),new Blood(BloodStates.SIZE_BLOOD,
								new Transform(-1,-0.3f)).setDirection(-1)
						.setParent("boss")
						}).setMoveOn(new int[] {15,16,17,18});
		
		
		states[ATTAC_L2] = new EntityState(ATTAC_L2, new Animation(8,12, 3, "boss/attackL"))
				.setLocked(0.3f).setMoveLock(0.3f)
				.setEntitesSpawnOn(new int[] {2}, new Entity [] {
						new Blood(
								BloodStates.SIZE_BLOOD,
								new Transform(-0.5f,-0.3f))
						.setDirection(-1)
						.setParent("boss")});
		
		
		
		states[ATTAC_L3] = new EntityState(ATTAC_L3, new Animation(12,19, 10, "boss/attackL").dontLoop())
				.setLocked(0.75f).setMoveLock(0.75f)
				.setEntitesSpawnOn(
						new int[] {5},
						new Entity [] {
								new PurpleProj(
										PurpleProjStates.SIZE_PURP_PROJ,new Transform(-0.2f,-0.0f),false)
										
								.addMovement(new Vector2f(-0.2f,0))
								});
				
		
		

		states[ROLL_L] = new EntityState(ROLL_L, new Animation(12, 16, "boss/rollL").dontLoop());
		states[ROLL_R] = new EntityState(ROLL_R, new Animation(12, 16, "boss/rollR").dontLoop());
		
		
		
		states[BLOCK_L] = new EntityState(BLOCK_L, new Animation(7, 50, "boss/blockL").dontLoop());
		states[BLOCK_R] = new EntityState(BLOCK_R, new Animation(7, 50, "boss/blockR").dontLoop());
		
		states[DEATH_L] = new EntityState(DEATH_L, new Animation(16, 8, "boss/deathL").dontLoop());
		states[DEATH_R] = new EntityState(DEATH_R, new Animation(16, 8, "boss/deathR").dontLoop());
		states[REVIVE_R] = new EntityState(REVIVE_R, new Animation(16, 2, "boss/deathR").dontLoop().reverse()).setLocked(0.75f);
		
		states[BLINK_L] = new EntityState(BLINK_L, new Animation(2, 16, "boss/blinkR")).setLocked(1.0f).setMoveLock(1.0f).setMoveOn(new int []{0,1}).setMovement(new Vector2f(0.1f,0));
		states[BLINK_R] = new EntityState(BLINK_R, new Animation(2, 16, "boss/blinkL")).setLocked(1.0f).setMoveLock(1.0f).setMoveOn(new int []{0,1}).setMovement(new Vector2f(-0.1f,0));
		
		return states;
	}
}
