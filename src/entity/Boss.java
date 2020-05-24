package entity;

import org.joml.Vector2f;

import Io.Window;

public class Boss extends Knight {

	public Boss(Transform transform) {
		super(transform);
	}

	@Override
	public Vector2f getMovement(Window window, float delta) {
		return new Vector2f(0,0);
	}

	@Override
	public int getNewState(Vector2f movement, Window window, float delta) {
		return 0;
	}

}
