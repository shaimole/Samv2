package entity.Player;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector2f;
import org.joml.Vector3f;

import Io.Window;
import entity.Entity;
import entity.EntityState;
import entity.Transform;
import render.Animation;
import render.Camera;
import world.World;
import world.WorldStates;

public class Player extends Entity {

	protected int speed = 5;
	EntityState [] knightStates;
	Vector2f lastMove = new Vector2f(1,0);
	boolean isRolling;
	boolean isBlocking;


	public Player(Transform transform) {
		super(PlayerStates.SIZE_PLAYER,transform);
		boundingBox.halfExtend = new Vector2f(boundingBox.halfExtend.x/2,boundingBox.halfExtend.y/2);
		states  = PlayerStates.getPlayerStates();
	}

	@Override
	public void update(float delta, Window window, Camera cam, World world, Player player) {
		if (isRolling() && getCurrentState().getAnimation().isFinished()) {
			isRolling = false;
		}
		if (timeToLive == 0) {
			useState(15);
			getCurrentState().update(delta,world,this.transform);
			world.showDeathMenu();
		}else {
			getCurrentState().update(delta,world,this.transform);
			window.getInput().update();
			Vector2f movement;
			if (!getCurrentState().isMovementLocked()) {
				movement = getMovement(window, delta);
			}else {
				movement = getCurrentState().getMovement(window, delta, lastMove, this);
			}
			int requestedState = getNewState(movement,window, delta);
			if (!getCurrentState().isLocked()) {
				useState(requestedState);
			}
			
			move(movement);

			
			if (movement.x != 0) {
				lastMove = movement;
			}
		}
		cam.getPosition().lerp(transform.pos.mul(-world.getScale(), new Vector3f()), 0.01f);

	}

	public boolean isAttacking() {
		return useState < 10 && useState > 3;
	}

	public Vector2f getMovement(Window window, float delta) {
		Vector2f movement = new Vector2f();
		if (window.getInput().isKeyDown(GLFW_KEY_UP))
			movement.add(0, speed * delta);
		if (window.getInput().isKeyDown(GLFW_KEY_DOWN))
			movement.add(0, -speed * delta);
		if (window.getInput().isKeyDown(GLFW_KEY_RIGHT))
			movement.add(speed * delta, 0);
		if (window.getInput().isKeyDown(GLFW_KEY_LEFT))
			movement.add(-speed * delta, 0);
		
		if (isRolling()) {
			movement = lastMove;
		}else if((movement.x !=  0|| movement.y != 0) && window.getInput().isKeyDown(GLFW_KEY_R)) {
			isRolling = true;
		}
		if (window.getInput().isKeyDown(GLFW_KEY_W)) {
			isBlocking = true;
			movement.x = 0;
			movement.y  = 0;
		}else {
			isBlocking = false;
		}
		return movement;

	}

	@Override
	public int getNewState(Vector2f movement, Window window, float delta) {
		
		if (isBlocking()) {
			if (lastMove.x < 0) {
				return PlayerStates.BLOCK_PLAYER_L;
			}else  {
				return PlayerStates.BLOCK_PLAYER_R;
			}
		}
		if (movement.x < 0) {
			return getMoveState(window, false);
		} else if (movement.x > 0) {
			return getMoveState(window, true);
		} else if (movement.y != 0) {
			if (lastMove.x < 0) {
				return getMoveState(window, false);
			} else {
				return getMoveState(window, true);
			}
		}

		if ((window.getInput().isKeyDown(GLFW_KEY_Q))) {
			if (lastMove.x < 0) {
				return PlayerStates.ATTAC_PLAYER_L2;
			} else {

				if (getCurrentState().getID() == PlayerStates.ATTAC_PLAYER_R1) {
					return PlayerStates.ATTAC_PLAYER_R2;
				}
				if (getCurrentState().getID() == PlayerStates.ATTAC_PLAYER_R2) {
					return PlayerStates.ATTAC_PLAYER_R3;
				}
				return PlayerStates.ATTAC_PLAYER_R1;
			}

		} else if (movement.y == 0 && movement.x == 0) {
			if ((getCurrentState().getID() != PlayerStates.IDLE_PLAYER_L || getCurrentState().getID() != PlayerStates.IDLE_PLAYER_R)
					&& getNextState() == -1) {
				if (lastMove.x < 0) {

					return PlayerStates.IDLE_PLAYER_L;
				} else {
					return PlayerStates.IDLE_PLAYER_R;
				}
			}

		}
		return getNextState();

	}
	
	public int getMoveState(Window window, boolean  right) {
		if (isRolling()) {
			return right ? PlayerStates.ROLL_PLAYER_R : PlayerStates.ROLL_PLAYER_L;
		}else {
			return right ? PlayerStates.RUNNING_PLAYER_R : PlayerStates.RUNNING_PLAYER_L;
		}
	}

	public boolean isRolling() {
		return isRolling;
	}
	
	protected boolean hasCollision() {
		return !isImmune();
	}
	
	public boolean isImmune() {
		int iframe = getCurrentState().getAnimation().getFrame();
		return isRolling() || isBlocking();
	}
	public boolean isBlocking() {
		return isBlocking;
	}
	@Override
	public String getName() {
		return "player";
	}

	@Override
	public int getLayer() {
		return 1;
	}
	

}
