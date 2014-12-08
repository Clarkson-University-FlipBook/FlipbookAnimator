package Flipbook;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;

/**
 * @author Will
 *
 */
public class Playback extends AnimationTimer {

    private final long DEFAULT_TIME_BETWEEN_FRAMES = 200;
    private double speed;
    private int currentFrameIndex;
    private long lastTime;
    private long timeLeft;
    private final ArrayList<Image> frameList;
    private boolean isPaused = false;
    private final FlipBook gui;

    /**
     * The default constructor initializes the framelist
     *
     * @param gui the main interface
     * @param frameList the list of frames
     */
    public Playback(FlipBook gui, ArrayList<Image> frameList) {
        this.frameList = frameList;
        this.gui = gui;
    }

    /**
     * starts playback
     *
     * @param index the index to start at
     */
    public void setPlayback(int index) {
        if (frameList.isEmpty())
            frameList.add(new Image(getClass().getResourceAsStream("fdIcon.png")));
        currentFrameIndex = index;
        lastTime = System.currentTimeMillis();
        speed = 1.0; // DEFAULT TO BE CHANGED LATER
        resetTimeLeft();
        isPaused = false;
        this.start();
        setFrame(0);
    }

    /**
     * pauses or unpauses playback
     */
    public void togglePause() {
        if (isPaused) {
            isPaused = false;
            lastTime = System.currentTimeMillis();
        } else
            isPaused = true;
    }

    /**
     * Advances the frame by one
     */
    public void nextFrame() {
        setFrame(currentFrameIndex + 1);
    }

    /**
     * sets the frame
     *
     * @param index the index of the frame to be set
     */
    public void setFrame(int index) {
        if (index < 0)
            return;
        if (index >= frameList.size()) {
            setFrame(0);
            return;
        }
        currentFrameIndex = index;
        gui.displayImage(frameList.get(currentFrameIndex));
        resetTimeLeft();
    }

    /**
     * decreases the current frame by one
     */
    public void prevFrame() {
        setFrame(currentFrameIndex - 1);
    }

    /**
     * sets the time left to the value based on the speed
     */
    private void resetTimeLeft() {
        timeLeft = (DEFAULT_TIME_BETWEEN_FRAMES / (long) speed);
    }

    @Override
    public void handle(long now) {
        if (!isPaused) {
            long currentTime = System.currentTimeMillis();
            timeLeft -= (currentTime - lastTime);
            lastTime = currentTime;
            if (timeLeft < 0)
                nextFrame();
        }
    }

    /**
     * the index of the currently displayed frame
     *
     * @return the currentFrameIndex
     */
    public int getCurrentFrameIndex() {
        return currentFrameIndex;
    }
}
