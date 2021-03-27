package entity.Projectiles;

import org.joml.Vector2f;

import Io.Window;
import entity.Entity;
import entity.EntityState;
import entity.Transform;
import entity.Player.Player;
import render.Camera;
import world.World;

public class PurpleProj extends Projectile {

	private static int ids = 0;
	private int id;;
	
	private boolean isRight = false;
	
	public PurpleProj(int maxAnimations, Transform transform, boolean right) {
		super(maxAnimations, transform);
		this.isRight = right;
		EntityState []  projStates;
		if (right) {
			projStates = PurpleProjStates.getPurpProjStatesR();
		}else {
			projStates = PurpleProjStates.getPurpProjStatesL();
		}
	
		for (int i = 0; i < projStates.length; i++) {
			addState(projStates[i]);
		}
		useState(0);
		id =ids++;
	}
	
	
	public Projectile addMovement(Vector2f movement) {
		this.movement = movement;
		return this;
	}
	@Override
	protected Entity getNew() {
		Transform t = new Transform();
		t.pos.x = transform.pos.x;
		t.pos.y = transform.pos.y;
		PurpleProj proj = new PurpleProj(PurpleProjStates.SIZE_PURP_PROJ,t,isRight);
		return proj.addMovement(movement);
	}	

	@Override
	public int getNewState(Vector2f movement, Window window, float delta) {
		return super.getNextState();
	}
	
	@Override
	public void update(float delta, Window window, Camera cam, World world, Player player) {
		super.update(delta, window, cam, world, player);
		if (useState == PurpleProjStates.PURP_PROJ_TRAVEL) {
			move(getMovement(window,delta));
		}

	}


	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public String getName() {
		return "purpleProj"+ id;
	}
	
	protected boolean hasCollision() {
		return useState == PurpleProjStates.PURP_PROJ_TRAVEL ;
	}

}
