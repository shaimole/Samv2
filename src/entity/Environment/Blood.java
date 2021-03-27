package entity.Environment;

import java.util.Random;

import org.joml.Vector2f;

import Io.Window;
import collision.Collision;
import entity.Entity;
import entity.EntityState;
import entity.Transform;
import entity.Enemies.Boss;
import entity.Player.Player;
import render.Camera;
import world.World;

public class Blood extends Entity {

	protected boolean toRemove = true;
	private static int ids = 0;
	private int id;
	private Random rnd = new Random();

	private int direction = 1;

	public Blood(int maxAnimations, Transform transform) {
		super(BloodStates.SIZE_BLOOD, transform);
		this.transform.scale.x = 0.5f;
		this.transform.scale.y = 0.3f;
		this.states = BloodStates.getBloodStates();
		id = ids++;
	}

	public Blood setDirection(int direction) {
		int random = rnd.nextInt(5);
		if (direction > 0) {
			useState(random);
		} else {
			useState(4 + random);
		}
		this.direction = direction;
		return this;
	}

	@Override
	public void update(float delta, Window window, Camera cam, World world, Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public Vector2f getMovement(Window window, float delta) {
		// TODO Auto-generated method stub
		return new Vector2f(0, 0);
	}

	@Override
	public int getNewState(Vector2f movement, Window window, float delta) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void collideWithEntity(Entity entity) {
		if (!isRendered()) {
			collide(entity);
		}
	}

	private boolean isRendered() {
		return !this.toRemove;
	}

	private void collide(Entity entity) {
		Collision collision = collideWith(entity);
		if (collision.isIntersecting()) {
			hitEntity(entity);
		}
	}

	private void hitEntity(Entity entity) {
		Class<? extends Entity> c = entity.getClass();
		if (playerHitsBoss(c)) {
			hitBoss(entity);
		} else if (bossHitsPlayer(c)) {
			hitPlayer(entity);
		}
	}
	
	private boolean playerHitsBoss(Class c) {
		return c == Boss.class && getParent() == "player";
	}
	
	private boolean bossHitsPlayer(Class c) {
		return c == Player.class && getParent() == "boss";
	}
	@Override
	public String getName() {
		return "blood" + id;
	}

	@Override
	public int getLayer() {
		return 0;
	}

	@Override
	public boolean toRemove() {
		return this.toRemove;
	}

	protected Entity getNew() {
		Transform t = new Transform();
		t.pos.x = direction;
		t.pos.y = -0.3f;
		return new Blood(BloodStates.SIZE_BLOOD, t).setDirection(this.direction).setParent(this.getParent());
	}

	@Override
	protected boolean hasCollision() {
		return false;
	}

	@Override
	public EntityState getCurrentState() {
		return states[useState];
	}

	private void hitBoss(Entity entity) {
		if (!entity.isImmune()) {
			this.transform.pos.x += direction;
			this.toRemove = false;
			this.transform.scale.x = 1.15f;
			entity.timeToLive -= ((Boss) entity).hpScale;
		}
	}

	private void hitPlayer(Entity entity) {
		if (!entity.isImmune()) {
			this.transform.pos.x += direction;
			this.toRemove = false;
			this.transform.scale.x = 1.15f;
			entity.timeToLive = 0;
		}
	}

}
