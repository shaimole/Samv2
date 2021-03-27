package entity.Gui;

import org.joml.Matrix4f;
import org.joml.Vector2f;

import Io.Window;
import entity.Entity;
import entity.Transform;
import entity.Player.Player;
import render.Camera;
import render.Shader;
import world.World;

public class Controls extends Entity{

	public boolean show = false;
	public Controls(int maxAnimations, Transform transform) {
		super(0, transform);
		states = GuiStates.getControlStates();
	}

	@Override
	public void update(float delta, Window window, Camera cam, World world, Player player) {
		if (!show) {
			return;
		}
		this.transform.scale.y = 3f;
		this.transform.scale.x = 3f;
		transform.pos.x = -cam.getPosition().x/world.getScale() -5;
		transform.pos.y = -cam.getPosition().y/world.getScale() +3;
		System.out.println(transform.pos.x + " "+ transform.pos.y);
	}
	
	@Override
	public void render(Shader shader, Camera cam, World world) {
		if (!show) {
			return;
		}
		super.render(shader, cam, world);
	}

	@Override
	public Vector2f getMovement(Window window, float delta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNewState(Vector2f movement, Window window, float delta) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
