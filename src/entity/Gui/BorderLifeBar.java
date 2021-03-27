package entity.Gui;

import org.joml.Vector2f;
import org.joml.Vector3f;

import Io.Window;
import entity.Entity;
import entity.Transform;
import entity.Player.Player;
import render.Camera;
import world.World;

public class BorderLifeBar extends Entity{
	private int bossid;

	public BorderLifeBar(int maxAnimations, Transform transform,int bossid) {
		super(maxAnimations, transform);
		this.bossid = bossid;
		this.states = GuiStates.getBorderStates();
		this.transform.scale.y = 0.2f;
		this.transform.scale.x = 7f;
	}

	@Override
	public void update(float delta, Window window, Camera cam, World world, Player player) {
		transform.pos.x = -cam.getPosition().x/world.getScale();
		transform.pos.y = -cam.getPosition().y/world.getScale() + 10.0f-bossid;	
	}

	@Override
	public Vector2f getMovement(Window window, float delta) {
		return new Vector2f (0,0);
	}

	@Override
	public int getNewState(Vector2f movement, Window window, float delta) {
		return 0;
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public String getName() {
		return "lifebarBoder"+ bossid;
	}

}
