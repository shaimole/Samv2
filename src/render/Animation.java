package render;

import Io.Timer;

public class Animation {
	private Texture[] frames;
	private int pointer;
	private int start;
	private double elapsedTime,currentTime,lastTime,fps;
	
	private boolean noLoop = false;
	private boolean reverse;
	
	
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
	
	public boolean isFinished() {
		return noLoop && (reverse ? pointer == 0 :pointer == frames.length-1);
	}
	
	public int getFrame() {
		return pointer;
	}
	
	public Animation(int start,int end, double fps,String filename) {
		pointer = 0;
		this.start = start;
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
	
	public Animation reverse() {
		this.reverse = true;
		pointer = this.frames.length-1;
		return this;
	}
	
	public void bind() {
		bind(0);
	}
	
	public void reset() {
		if(this.reverse) {
			pointer = frames.length -1;
		}else {
			pointer = 0;
		}
		
	}
	
	public void setFps(double fps) {
		this.fps = 1.0/fps;
	}
	public void bind (int sampler) {
		currentTime = Timer.getTime();
		elapsedTime += currentTime - lastTime;
		
		if(elapsedTime >= fps) {
			
			elapsedTime = 0;
			if (reverse) {
				pointer--;
			}else {
				pointer++;
			}
			
		}
		if (reverse) {
			if (pointer <= 0) {
				if(noLoop) {
					pointer =0 ;		
				}else {
					pointer = frames.length-1;
				}
			}
			
		}else {
			if (pointer >= frames.length) {
				if(noLoop) {
					pointer = frames.length-1;
				}else {
					pointer = 0;		
				}
					
			}
		}
		

		
		lastTime = currentTime;
		
		frames[pointer].bind(sampler);
	}
}
