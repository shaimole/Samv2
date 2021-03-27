package entity;

import org.joml.Vector2f;

import Io.Window;
import entity.Player.Player;
import render.Animation;
import world.World;

public class EntityState {

	private Animation animation;
	private float duration;
	private float locked = 0;
	private int id;
	private float moveDuration = 0;
	private float moveLock = 0;
	private int[] timesToSpawn;
	private Entity[] entitysToSpawn;
	private boolean [] spawned;
	
	private int [] moveOn;
	
	private Vector2f movement = new Vector2f(0, 0);

	
	public EntityState(int id, Animation animation) {
		this.id = id;
		this.animation = animation;
	}

	public int getID() {
		return id;
	}

	public EntityState setEntitesSpawnOn(int[] times, Entity[] entities) {

		if (times != null && entities != null) {
			timesToSpawn = times;
			entitysToSpawn = entities;
			spawned = new boolean[entities.length];
		}

		return this;
	}
	
	public EntityState setMoveOn(int[] times) {

		if (times != null) {
			moveOn = times;
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
				if (this.getAnimation().getFrame() == timesToSpawn[i] && !spawned[i]) {

					Entity n = entitysToSpawn[i].getNew();
					spawned[i] = true;
					entitysToSpawn[i].transform.pos.x += pos.pos.x;
					entitysToSpawn[i].transform.pos.y += pos.pos.y;
					entitysToSpawn[i].boundingBox.getCenter().set(entitysToSpawn[i].transform.pos.x,entitysToSpawn[i].transform.pos.y);
					entitysToSpawn[i].boundingBox.getHalfExtend().set(new Vector2f(0.1f,0.1f));
					world.addNewEntity(entitysToSpawn[i]);
					entitysToSpawn[i] = n;
				}
			}
		}
		
	}

	public void reset() {
		locked = duration;
		moveLock = moveDuration;
		spawned = new boolean[20];
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

	public Vector2f getMovement(Window window, float delta, Vector2f lastMove, Player player) {
		if (moveOn != null ) {
			System.out.println(getAnimation().getFrame());
			for(int i = 0; i < this.moveOn.length;i++) {
				if(getAnimation().getFrame() == moveOn[i]) {
					return movement;
				}
			}
		}
		
		return new Vector2f(0,0);
	}
	
	public EntityState setMovement(Vector2f movement ) {
		this.movement = movement;
		return this;
	}

}
