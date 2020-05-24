package Io;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

	private long windowPointer;
	private boolean[] keys;

	public Input(long windowPointer) {
		keys = new boolean[GLFW_KEY_LAST];
		this.windowPointer = windowPointer;
	}

	public void update() {
		for(int i = 32; i < GLFW_KEY_LAST;i++) {
			keys[i] = isKeyDown(i);
		}
		glfwPollEvents();
	}

	public boolean isKeyDown(int key) {
		return glfwGetKey(windowPointer, key) == 1;
	}

	public boolean isMouseButtonDown(int key) {
		return glfwGetMouseButton(windowPointer, key) == 1;
	}

	public boolean isKeyPressed(int key) {
		return (isKeyDown(key) &&!keys[key]);
	}
	
	public boolean isKeyReleased(int key) {
		return (!isKeyDown(key) &&keys[key]);
	}
	
}
