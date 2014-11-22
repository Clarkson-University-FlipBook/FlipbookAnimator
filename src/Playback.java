/**
 * 
 */
package playback;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javafx.animation.AnimationTimer;

/**
 * @author Will 
 *
 */
public class Playback extends AnimationTimer{
	private final long DEFAULT_TIME_BETWEEN_FRAMES = 1000;
	private double speed;
	private Iterator currentFrameItr;
	private long lastTime;
	private long timeLeft;
	
	
	
	public void setPlayback(Iterator itr){
		currentFrameItr = itr;
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
			if(currentFrameItr.hasNext())
				currentFrameItr.next();
			else
				this.stop();
		}
	}

}
