package entity;


import org.joml.Vector2f;

import Io.Window;
import render.Camera;
import world.World;

public abstract class Knight extends Entity {

	EntityState [] knightStates;
	protected int speed = 5;

	protected Vector2f lastMove = new Vector2f(1, 0);

	public Knight(Transform transform) {
		super(EntityStates.SIZE_KNIGHT, transform);
		knightStates = EntityStates.getPlayerStates();
		for (int i = 0; i < knightStates.length; i++) {
			addState(knightStates[i]);
		}

	}
	
	@Override
	protected boolean hasCollision() {
		return true;
	}
	@Override
	public void update(float delta, Window window, Camera cam, World world) {
		getCurrentState().update(delta,world,this.transform);
		window.getInput().update();
		Vector2f movement;
		if (!getCurrentState().isMovementLocked()) {
			movement = getMovement(window, delta);
		}else {
			movement = getCurrentState().getMovement(window, delta, lastMove);
		}
		int requestedState = getNewState(movement,window, delta);
		if (!getCurrentState().isLocked()) {
			useState(requestedState);
		}else {
			setNextState(requestedState);
		}
		
		move(movement);

		
		if (movement.x != 0) {
			lastMove = movement;
		}
		float times [] = {0.3f};
	}


	protected boolean isAttacking() {
		return false;
	}

}
