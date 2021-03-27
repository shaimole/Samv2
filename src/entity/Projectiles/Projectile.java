package entity.Projectiles;

import org.joml.Vector2f;

import Io.Window;
import entity.Entity;
import entity.Transform;
import entity.Player.Player;
import render.Camera;
import world.World;

public abstract class Projectile extends Entity {

	
	protected Vector2f movement;
	
	public Projectile(int maxAnimations, Transform transform) {
		super(maxAnimations, transform);
	}
	

	
	public Projectile addMovement(Vector2f movement) {
		this.movement = movement;
		return this;
	}

	public void update(float delta, Window window, Camera cam, World world, Player player) {
		states[useState].update(delta,world,transform);
		if(states[useState].getAnimation().isFinished()) {
			if (useState < states.length-1) {
				useState(useState+1);
			}else {
				timeToLive = 0;
				toRemove = true;
			}
		}

	}

	public Vector2f getMovement(Window window, float delta) {
		return movement;
	}
	
	protected boolean hasCollision() {
		return true;
	}

}
