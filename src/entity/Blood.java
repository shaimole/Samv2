package entity;

import org.joml.Vector2f;

import Io.Window;
import render.Camera;
import world.World;

public class Blood extends Entity {

	public Blood(int maxAnimations, Transform transform) {
		super(EntityStates.SIZE_BLOOD, transform);
		this.transform.scale.x = 0.5f;
		this.transform.scale.y = 0.3f;
		EntityState[] states = EntityStates.getBloodStates();
		for (int i = 0; i < states.length; i++) {
			System.out.println("x");
			addState(states[i]);
		}
	}
	
	protected Entity getNew() {
		Transform t = new Transform();
		t.pos.x = 1;
		t.pos.y = -0.3f;
		return new Blood(0,t);
	}	
	@Override
	protected boolean hasCollision() {
		return false;
	}

	@Override
	public void update(float delta, Window window, Camera cam, World world) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vector2f getMovement(Window window, float delta) {
		// TODO Auto-generated method stub
		return new Vector2f(0,0);
	}

	@Override
	public int getNewState(Vector2f movement, Window window, float delta) {
		// TODO Auto-generated method stub
		return 0;
	}

}
