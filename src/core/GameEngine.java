package core;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import Io.Timer;
import Io.Window;
import entity.Entity;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class GameEngine implements Runnable {

	private IGameLogic gameLogic;
	private final Thread gameLoopThread;
	private Window window;

	private String windowTitle;

	public GameEngine(String windowTitle, IGameLogic gameLogic) throws Exception {
		this.windowTitle = windowTitle;
		this.gameLogic = gameLogic;
		gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
	}

	public void start() {
		gameLoopThread.start();
	}

	@Override
	public void run() {
		initGlfw();
		launchGame();

	}

	public void init() {
		GLFWErrorCallback.createPrint(System.err).set();

		initWindow();
		window.show();

		GL.createCapabilities();

		setGlOptions();
	}

	private void setGlOptions() {
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

	}

	private void initGlfw() {
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");
	}

	private void launchGame() {
		try {
			init();
			gameLogic.init(window);
			gameLoop();
		} catch (Exception excp) {
			excp.printStackTrace();
		}
	}

	private void initWindow() {
		window = new Window();
		window.setFullScreen(true);
		window.createWindow(windowTitle);
		window.addCallbacks();
		if (!window.isFullscreen()) {
			window.center();
		}

		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
	}

	private void gameLoop() {

		GameLoop loop = new GameLoop(this);

		while (!window.shouldClose() && !window.restart()) {
			loop.run();
		}
		if (window.restart()) {
			Entity.deleteAsset();
			glfwTerminate();
			
			start();
		}
		
		Entity.deleteAsset();
		glfwTerminate();

	}


	protected void updateGameState(double interval) {
		gameLogic.update(interval, window);
	}

	protected void render() {
		gameLogic.render(window);
		window.update();
	}
}