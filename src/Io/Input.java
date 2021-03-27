package Io;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

	private long windowPointer;
	private int [] keys;

	public Input(long windowPointer) {
		keys = new int[GLFW_KEY_LAST];
		this.windowPointer = windowPointer;
	}

	public void update() {
		for(int i = 32; i < GLFW_KEY_LAST;i++) {
			if (isKeyDown(i)) {
				keys[i]++;
			}else {
				keys[i] = 0;
			}

		}
		glfwPollEvents();
	}

	public boolean isKeyDown(int key) {
		return glfwGetKey(windowPointer, key) == 1;
	}
	
	public boolean isKeyHeld(int key) {
		return isKeyDown(key) && keys[key] > 20;
	}

	public boolean isMouseButtonDown(int key) {
		return glfwGetMouseButton(windowPointer, key) == 1;
	}

	public boolean isKeyPressed(int key) {
		return (isKeyDown(key) &&keys[key] == 0);
	}
	
	public boolean isKeyReleased(int key) {
		return (!isKeyDown(key) &&keys[key] != 0);
	}
	
}
