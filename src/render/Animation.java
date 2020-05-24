package render;

import Io.Timer;

public class Animation {
	private Texture[] frames;
	private int pointer;
	private int start;
	private double elapsedTime,currentTime,lastTime,fps;
	
	private boolean noLoop = false;
	
	
	public Animation(int amount, double fps,String filename) {
		pointer = 0;
		elapsedTime = 0;
		currentTime = 0;
		lastTime = Timer.getTime();
		this.fps = 1.0/fps;
		frames = new Texture[amount];
		for(int i = 0; i< amount; i++) {
			frames[i] = new Texture("ani/"+filename+"/"+i+".png");
		}
	}
	
	public Animation(int start,int end, double fps,String filename) {
		pointer = 0;
		elapsedTime = 0;
		currentTime = 0;
		lastTime = Timer.getTime();
		this.fps = 1.0/fps;
		int limit = end-start;
		frames = new Texture[limit];
		for(int i = 0; i< limit; i++) {
			int number = i + start;
			frames[i] = new Texture("ani/"+filename+"/"+number+".png");
		}
	}
	
	public Animation dontLoop() {
		this.noLoop = true;
		return this;
	}
	
	public void bind() {
		bind(0);
	}
	
	public void reset() {
		pointer = start;
	}
	
	public void setFps(double fps) {
		this.fps = 1.0/fps;
	}
	public void bind (int sampler) {
		currentTime = Timer.getTime();
		elapsedTime += currentTime - lastTime;
		
		if(elapsedTime >= fps) {
			
			elapsedTime = 0;
			pointer++;
		}
		
		if (pointer >= frames.length) {
			if(noLoop) {
				pointer = frames.length;
			}else {
				pointer = 0;		
			}
				
		}

		
		lastTime = currentTime;
		
		frames[pointer].bind(sampler);
	}
}
