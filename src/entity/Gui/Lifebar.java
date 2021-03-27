package entity.Gui;

import org.joml.Vector2f;
import org.joml.Vector3f;

import Io.Window;
import entity.Entity;
import entity.Transform;
import entity.Player.Player;
import render.Camera;
import world.World;

public class Lifebar extends Entity{

	private boolean dead = false;
	private float displayHp;
	private int bossid;
	public Lifebar(int maxAnimations, Transform transform, int bossid) {
		super(maxAnimations, transform);
		this.bossid = bossid;
		this.states = GuiStates.getBarStates();
		this.transform.scale.y = 0.2f;
		this.transform.scale.x = 7;
	}

	@Override
	public void update(float delta, Window window, Camera cam, World world, Player player) {
		transform.pos.x = -cam.getPosition().x/world.getScale();
		transform.pos.y = -cam.getPosition().y/world.getScale() + 10.0f-bossid;	
		
		float hp = world.getBossHP(bossid);
		
		if (hp < 0) {
			hp = 0.0f;
		}
		
		if (isDisplayWrong(hp)) {
			loadDisplayHp(delta);
		}else {
			displayHp = hp;
		}
		
		if (hp == 0) {
			dead = true;
		}
		
		this.transform.scale.x = 7* displayHp;
		
	}

	private boolean isDisplayWrong(float hp) {
		return hp == 1 && dead == true && displayHp < 1;
	}
	private void loadDisplayHp(float delta) {
		displayHp += delta/6;
		if (displayHp > 1) {
			displayHp = 1;
		}
	}
	@Override
	public Vector2f getMovement(Window window, float delta) {
		return new Vector2f (0,0);
	}

	@Override
	public int getNewState(Vector2f movement, Window window, float delta) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public String getName() {
		return "lifebar"+ bossid;
	}

}
