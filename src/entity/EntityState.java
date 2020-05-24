package entity;

import java.util.HashMap;
import java.util.Map.Entry;

import org.joml.Vector2f;

import Io.Window;
import render.Animation;
import world.World;

public class EntityState {

	private Animation animation;
	private float duration;
	private float locked = 0;
	private int id;
	private float moveDuration = 0;
	private float moveLock = 0;
	private float timeRunning = 0;
	private float[] timesToSpawn;
	private Entity[] entitysToSpawn, entitysToSpawnReload;

	
	public EntityState(int id, Animation animation) {
		this.id = id;
		this.animation = animation;
	}

	public int getID() {
		return id;
	}

	public EntityState setEntitesSpawnOn(float[] times, Entity[] entities) {

		if (times != null && entities != null) {
			timesToSpawn = times;
			entitysToSpawn = entities;
			entitysToSpawnReload = entities;
		}

		return this;
	}

	public EntityState setLocked(float duration) {
		locked = duration;
		this.duration = duration;
		return this;
	}

	public EntityState setMoveLock(float duration) {
		moveLock = duration;
		this.moveDuration = duration;
		return this;
	}

	public void update(float delta, World world, Transform pos) {
		System.out.println(timeRunning);
		timeRunning += delta;
		if (locked > 0.0) {
			locked = locked - delta;
		} else {
			locked = duration;
		}
		if (moveLock > 0.0) {
			moveLock = moveLock - delta;
		} else {
			moveLock = moveDuration;
		}
		if (timesToSpawn != null) {
			for (int i = 0; i < timesToSpawn.length; i++) {
				if (timesToSpawn[i] - timeRunning == 0 && entitysToSpawn[i] != null) {
				
					entitysToSpawn[i].transform.pos.x += pos.pos.x;
					entitysToSpawn[i].transform.pos.y += pos.pos.y;
					world.addEntity(entitysToSpawn[i]);
					entitysToSpawn[i] = entitysToSpawn[i].getNew();
				}
			}
		}
		
	}

	public void reset() {
		locked = duration;
		moveLock = moveDuration;
		timeRunning = 0;
		entitysToSpawn = entitysToSpawnReload;
		animation.reset();
	}

	public float getDuration() {
		return locked;
	}

	public boolean isLocked() {
		return locked > 0.0;
	}

	public boolean isMovementLocked() {
		return moveLock > 0;
	}

	public Animation getAnimation() {
		return animation;
	}

	protected Vector2f getMovement(Window window, float delta, Vector2f lastMove) {
		return new Vector2f(0, 0);
	}

}
