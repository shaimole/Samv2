package core;

import Io.Timer;

public class GameLoop {
	
	private GameEngine engine;
	private double frameCap = Utils.getDelta();
	private double time = Timer.getTime();
	private double unprocessed = 0;
	private boolean newToRender = false;
	
	public GameLoop(GameEngine engine) {
		this.engine = engine;
	}
	
	public void run() {
		newToRender = false;

		double innerTime = Timer.getTime();
		double timePassed = innerTime - time;

		unprocessed += timePassed;

		time = innerTime;
		
		while (unprocessed >= frameCap) {
			update();

		}
		
		if (newToRender) {
			engine.render();
		}
	}
	
	private void update() {
		newToRender = true;
		unprocessed -= frameCap;
		engine.updateGameState(frameCap);
	}
}

