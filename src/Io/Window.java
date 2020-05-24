package Io;

import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import core.Utils;

public class Window {

	private long windowPointer;
	private int width, height;
	private boolean resized, fullscreen = false;
	private Input input;

	public Window() {
		this.width = 640;
		this.height = 480;
	}

	public Window createWindow(String title) {
		Utils.initGlfw();
		
		windowPointer = glfwCreateWindow(this.width, this.height, title, fullscreen ? glfwGetPrimaryMonitor() : 0, NULL);
		
		if (windowPointer == NULL)
			throw new RuntimeException("Failed to create the GLFW window");
		this.input = new Input(windowPointer);
		glfwSetInputMode(windowPointer, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
		return this;
	}

	public void center() {
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			glfwGetWindowSize(windowPointer, pWidth, pHeight);

			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			glfwSetWindowPos(windowPointer, (vidmode.width() - pWidth.get(0)) / 2,
					(vidmode.height() - pHeight.get(0)) / 2);
			System.out.println(vidmode.width());
		} 

	}

	public void show() {
		glfwShowWindow(windowPointer);

		glfwMakeContextCurrent(windowPointer);
	}

	public void addCallbacks() {
		glfwSetKeyCallback(windowPointer, (window, key, scancode, action, mods) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});
	}

	public Input getInput() {
		return input;
	}

	public boolean shouldClose() {
		return glfwWindowShouldClose(windowPointer);
	}

	public void setResized(boolean wasResized) {
		this.resized = wasResized;
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public boolean isResized() {
		return this.resized;
	}

	public boolean isFullscreen() {
		return fullscreen;
	}

	public void setFullScreen(boolean fullscreen) {
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		this.width = vidmode.width();
		this.height = vidmode.height();
		this.fullscreen = fullscreen;
	}

	public long getHandle() {
		return this.windowPointer;
	}

	public void setClearColor(float color, float color2, float color3, float color4) {
		glClearColor(color, color2, color3, color3);
	}

	public void update() {
		glfwSwapBuffers(windowPointer);
	}

}
