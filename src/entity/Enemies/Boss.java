package entity.Enemies;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


import org.joml.Vector2f;

import Io.Window;
import collision.Collision;
import entity.Entity;
import entity.Transform;
import entity.Player.Player;
import render.Camera;
import world.World;
import world.WorldStates;

public class Boss extends Entity {

	public static AtomicInteger id = new AtomicInteger(0); 
	public int idSelf;
	
	private Vector2f lastMove = new Vector2f(-1, 0);
	private Random random = new Random();
	protected float minSpeed = 0.03f;
	protected float speed = 0.03f;
	private boolean isImmune = false;
	private float timeDead;

	private int blockChance = 4000;
	private int purpleChance = 0;
	private int lungeChance = 60;
	private int attackChance = 5000;
	private int blinkChance = 100;
	

	public float timeToLive = 1;
	public float hpScale = 1f;

	public Boss(Transform transform) {
		super(BossStates.SIZE_BOSS, transform);
		states = BossStates.getBossStates();
		idSelf = id.incrementAndGet(); 
		
	}
	public int getID() {
		return idSelf;
	}

	
	@Override
	public String getName() {
		return "boss" + idSelf;
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public Vector2f getMovement(Window window, float delta) {
		return new Vector2f(0, 0);
	}

	@Override
	public int getNewState(Vector2f movement, Window window, float delta) {
		return 0;
	}

	public boolean isImmune() {
		return (useState == BossStates.BLOCK_R || useState == BossStates.BLOCK_L)
				&& states[useState].getAnimation().isFinished() || useState == BossStates.REVIVE_R;
	}

	protected boolean hasCollision() {
		return !isImmune && useState != 14 && useState != 15 && useState != 18 && useState != 19;
	}
	

	@Override
	public void update(float delta, Window window, Camera cam, World world, Player player) {
		if (shouldUpdate(delta, window, cam, world, player)) {
			
			getCurrentState().update(delta, world, this.transform);
			
			Vector2f movement = getMovementBoss(window, delta, player);

			int requestedState = getNewState(movement, window, delta, player);

			if (!getCurrentState().isLocked()) {
				useState(requestedState);
			} else {
				setNextState(requestedState);
			}
			
			move(movement);
			if (movement.x != 0) {
				lastMove = movement;
			}
			
		}	
	}
	
	private boolean shouldUpdate(float delta, Window window, Camera cam, World world, Player player) {
		if (world.getCurrentState() == WorldStates.WORLD_STATE_START) {
			getCurrentState().update(delta, world, this.transform);
			return false;
		}else if (player.timeToLive == 0) {
			useState(BossStates.IDLE_R);
			return false;
		}
		
		if (useState == BossStates.REVIVE_R && !states[useState].getAnimation().isFinished()) {
			return false;
		} else if (states[useState].getAnimation().isFinished() && useState == BossStates.REVIVE_R) {
			UpgradeReset();
		}
		if (world.getEntityByName("boss" + idSelf).timeToLive <= 0) {
			timeDead += delta;
			useState(15);
			if (timeDead >= 4.0) {
				world.getEntityByName("boss" + idSelf).timeToLive = 1;
				timeDead = 0;
				useState(BossStates.REVIVE_R);
			} else {
				return false;
			}
		}
		return true;
	}
	
	private void UpgradeReset() {
		states[useState].reset();
		this.hpScale /= 2;
		this.purpleChance += 100;
		this.lungeChance += 50;
		this.minSpeed += 0.02f;
		this.attackChance += 60;
		this.blinkChance += 10;
		for (int i = 0; i < states.length; i++) {
			states[i].reset();
		}
		useState(0);
	}
	
	public Vector2f getMovementBoss(Window window, float delta, Player player) {
		Vector2f movement = player.getBoundingBox().center.sub(boundingBox.center, new Vector2f());
		Vector2f stateMovement = getCurrentState().getMovement(window, delta, movement, player);
		
		if (isAjecent(player) && stateMovement.x == 0 && stateMovement.y == 0) {
			return  new Vector2f(0, 0);

		}
		if (!getCurrentState().isMovementLocked()) {
			movement = getMove(movement);
		} else {
			movement = getCurrentState().getMovement(window, delta, lastMove, player);
		}
		
		return movement;
	}
	
	private boolean isAjecent(Player player) {
		double distanceX = Math.abs(this.transform.pos.x - player.transform.pos.x);
		double distanceY = Math.abs(this.transform.pos.y - player.transform.pos.y);
		return distanceX != 0 && distanceX < 1.5d && distanceY < 2.5;
	}
	
	private Vector2f getMove(Vector2f movement) {
		if (Math.abs(movement.y) > 0.08) {
			if (movement.y > 0) {
				movement.y = speed < movement.y ? speed : movement.y;
			} else if (movement.y < 0) {
				movement.y = -speed > movement.y ? -speed : movement.y;
			}
			if (Math.abs(movement.y) < minSpeed) {
				if (movement.y > 0) {
					movement.y = minSpeed;
				} else {
					movement.y = -minSpeed;
				}
			}
		}
		if (Math.abs(movement.x) > 0.08) {
			if (movement.x > 0) {
				movement.x = speed < movement.x ? speed : movement.x;
			} else if (movement.x < 0) {
				movement.x = -speed > movement.x ? -speed : movement.x;
			}
			if (Math.abs(movement.x) < minSpeed) {
				if (movement.x > 0) {
					movement.x = minSpeed;
				} else {
					movement.x = -minSpeed;
				}
			}
		}
		return movement;
	}
	
	
	public int getNewState(Vector2f movement, Window window, float delta, Player player) {


		int chance = random.nextInt(10000);
		Collision col = boundingBox.getCollision(player.getBoundingBox());

		if (shouldBlink(chance,col)) {
			return getBlinkState(player);
		}
		chance = random.nextInt(10000);
		if (shouldBlock(chance,player)) {
			return getBlockState(movement);
		}
		chance = random.nextInt(10000);
		if (random.nextInt(4) < 3) {
			if (shouldLunge(chance,col)) {
				return getLungekState(player);
			}
		} else {
			if (shouldUseProj(chance, col)) {
				return getProjState(player);
			}
		}
		chance = random.nextInt(10000);
		if (shouldMeele(chance, col)) {
			return getMeleeState(player);
		}

		if (shouldIdle(movement)) {
			return getIdleState();
		}

		return getRunState(movement);

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private boolean shouldBlink(int chance, Collision col) {
		return ((col.distance.x < 1.5f) && (chance <= blinkChance || useState == BossStates.BLINK_R
				|| useState == BossStates.BLINK_L));
	}
	
	private int getBlinkState(Player player) {
		if (player.getBoundingBox().center.x > boundingBox.center.x) {
			return BossStates.BLINK_R;
		} else {
			return BossStates.BLINK_L;
		}
	}
	
	private boolean shouldBlock(int chance, Player player) {
		return player.isAttacking() && (chance <= blockChance || useState == BossStates.BLOCK_R
				|| useState == BossStates.BLOCK_L && useState != BossStates.ATTAC_R2  && useState != BossStates.ATTAC_L2);
	}
	
	private int getBlockState(Vector2f movement) {
		if (movement.x > 0 || lastMove.x > 0) {
			return BossStates.BLOCK_R;
		} else {
			return BossStates.BLOCK_L;
		}
	}
	
	private boolean shouldLunge(int chance,Collision col) {
		return (col.distance.x > 1.0f) && (chance <= lungeChance || useState == BossStates.ATTAC_R1);
	}
	
	private int getLungekState(Player player) {
		Vector2f move = player.getBoundingBox().center.sub(boundingBox.center, new Vector2f());
		Vector2f direction = getMove(move);
		direction.x *= 5;
		direction.y *= 5;
		if (player.transform.pos.x > transform.pos.x) {
			states[BossStates.ATTAC_R1].setMovement(direction);
			return BossStates.ATTAC_R1;
		} else {
			states[BossStates.ATTAC_L1].setMovement(direction);
			return BossStates.ATTAC_L1;
		}
	}
	
	private boolean shouldUseProj(int chance,Collision col) {
		return ((col.distance.y < 0.2f) && chance <= purpleChance) || ((useState == BossStates.ATTAC_R3 || useState == BossStates.ATTAC_L3)
				&& !states[useState].getAnimation().isFinished());
	}
	
	private int getProjState(Player player) {
		if (player.getBoundingBox().center.x > boundingBox.center.x) {
			return BossStates.ATTAC_R3;
		} else {
			return BossStates.ATTAC_L3;
		}
	}
	
	private boolean shouldMeele(int chance,Collision col) {
		return ((col.distance.x < 1.5f) && (chance <= attackChance || useState == BossStates.ATTAC_R2
				|| useState == BossStates.ATTAC_L2));
	}
	
	private int getMeleeState(Player player) {
		Vector2f move = player.getBoundingBox().center.sub(boundingBox.center, new Vector2f());
		Vector2f direction = getMove(move);
		direction.x *= 5;
		direction.y *= 5;
		if (player.transform.pos.x > transform.pos.x) {
			states[BossStates.ATTAC_R2].setMovement(direction);
			return BossStates.ATTAC_R2;
		} else {
			states[BossStates.ATTAC_L2].setMovement(direction);
			return BossStates.ATTAC_L2;
		}
	}

	
	private boolean shouldIdle(Vector2f movement) {
		return movement.x == 0 && movement.y == 0;
		
	}
	private int getIdleState() {
		if (lastMove.x > 0) {
			return BossStates.IDLE_R;
		} else {
			return BossStates.IDLE_L;
		}
	}
	
	private boolean shouldRun(Vector2f movement) {
		return movement.x == 0 && movement.y == 0;
		
	}
	
	private int getRunState(Vector2f movement) {
		if (movement.x < 0) {
			return BossStates.RUNNING_L;
		} else if (movement.x > 0) {
			return BossStates.RUNNING_R;
		} else {
			if (lastMove.x > 0) {
				return BossStates.RUNNING_L;
			} else {
				return BossStates.RUNNING_R;
			}
		}
	}

	

}
