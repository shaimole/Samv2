package entity;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector2f;
import org.joml.Vector3f;

import Io.Window;
import render.Animation;
import render.Camera;
import world.World;

public class Player extends Knight {

	protected int speed = 5;

	public Player(Transform transform) {
		super(transform);
	}

	@Override
	public void update(float delta, Window window, Camera cam, World world) {
		super.update(delta, window, cam, world);

		cam.getPosition().lerp(transform.pos.mul(-world.getScale(), new Vector3f()), 0.25f);

	}

	protected boolean isAttacking() {
		return false;
	}

	public Vector2f getMovement(Window window, float delta) {
		Vector2f movement = new Vector2f();
		if (window.getInput().isKeyDown(GLFW_KEY_W))
			movement.add(0, speed * delta);
		if (window.getInput().isKeyDown(GLFW_KEY_S))
			movement.add(0, -speed * delta);
		if (window.getInput().isKeyDown(GLFW_KEY_D))
			movement.add(speed * delta, 0);
		if (window.getInput().isKeyDown(GLFW_KEY_A))
			movement.add(-speed * delta, 0);

		return movement;

	}

	@Override
	public int getNewState(Vector2f movement, Window window, float delta) {
		if (movement.x < 0) {
			return EntityStates.RUNNINGL;
		} else if (movement.x > 0) {
			return EntityStates.RUNNINGR;
		} else if (movement.y != 0) {
			if (lastMove.x < 0) {
				return EntityStates.RUNNINGL;
			} else {
				return EntityStates.RUNNINGR;
			}
		}

		if ((window.getInput().isKeyDown(GLFW_KEY_R))) {
			if (lastMove.x < 0) {
				if (getCurrentState().getID() == EntityStates.ATTACL1) {
					return EntityStates.ATTACL2;
				}
				if (getCurrentState().getID() == EntityStates.ATTACL2) {
					return EntityStates.ATTACL3;
				}
				return EntityStates.ATTACL1;
			} else {

				if (getCurrentState().getID() == EntityStates.ATTACR1) {
					return EntityStates.ATTACR2;
				}
				if (getCurrentState().getID() == EntityStates.ATTACR2) {
					return EntityStates.ATTACR3;
				}
				return EntityStates.ATTACR1;
			}

		} else if (movement.y == 0 && movement.x == 0) {
			if ((getCurrentState().getID() != EntityStates.IDLEL || getCurrentState().getID() != EntityStates.IDLER)
					&& getNextState() == -1) {
				if (lastMove.x < 0) {

					return EntityStates.IDLEL;
				} else {
					return EntityStates.IDLER;
				}
			}

		}
		return getNextState();

	}
}
