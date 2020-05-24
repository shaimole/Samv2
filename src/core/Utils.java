package core;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWErrorCallback;

public class Utils {

	public static void initGlfw() {
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");
	}
	
	public static void setErrorCallBacks() {
		glfwSetErrorCallback(new GLFWErrorCallback() {

			@Override
			public void invoke(int error, long description) {
				throw new IllegalStateException(GLFWErrorCallback.getDescription(description));
				
			}
			
		});
	}
	
	public static boolean isNegative(Vector2f vector) {
		return vector.x < 0 && vector.y < 0;
	}

	public static Vector2f makeAbsolute(Vector2f vector) {
		vector.x = (float) Math.abs(vector.x);
		vector.y = (float) Math.abs(vector.y);
		return vector;
	}
	
	public static float getDelta() {
		return 1.0f / 60.0f;
	}
}

