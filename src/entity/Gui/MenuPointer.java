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

public class MenuPointer extends Entity{

	public MenuPointer(int maxAnimations, Transform transform) {
		super(1, transform);
		states = GuiStates.getMenuPointerOptions();
		useState = 0;
	}

	@Override
	public void update(float delta, Window window, Camera cam, World world, Player player) {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public Vector2f getMovement(Window window, float delta) {
		return new Vector2f(0,0);
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
