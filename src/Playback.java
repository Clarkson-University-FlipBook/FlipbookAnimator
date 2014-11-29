

import java.util.Iterator;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;

/**
 * @author Will 
 *
 */
public class Playback extends AnimationTimer{
	private final long DEFAULT_TIME_BETWEEN_FRAMES = 1000;
	private double speed;
	private int currentFrameIndex;
	private long lastTime;
	private long timeLeft;
	private PlaybackFrontEnd frontend;
	
	public Playback(PlaybackFrontEnd frontend){
		this.frontend = frontend;
	}
	
	public void setPlayback(int index){
		currentFrameIndex = index;
		lastTime = System.currentTimeMillis();
		this.start();
	}

	public void runPlayback(){
		// TODO use AnimationTimer to check if time to switch  
		// TODO if (time to switch) increment currentFrameIndex and set currentVector 
	}


	public void changeSpeed(){
//		double newSpeed = slider.getSpeed();
//		double speedConversion = speed/newSpeed;
//		timeLeft *= speedConversion;
	}
	
	@Override
	public void handle(long now) {
		// TODO Auto-generated method stub
		long currentTime = System.currentTimeMillis();
		timeLeft = (currentTime - lastTime);
		lastTime = currentTime;
		if(timeLeft < 0){
			if(currentFrameIndex < Integer.MAX_VALUE){ //FIX LATER
				currentFrameIndex++;
				frontend.changeFrame();
			}
			else
				this.stop();
		}
	}

	public int getCurrentFrameIndex() {
		// TODO Auto-generated method stub
		return currentFrameIndex;
	}

}
