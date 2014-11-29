import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * 
 */

/**
 * @author Will
 *
 */
public class PlaybackFrontEnd extends Application{
	private Image currentFrame;
	Playback backend;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void changeFrame(){
		//currentFrame = FRAMECONTAINER[backend.getCurrentFrameIndex];
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		backend = new Playback(this);
		primaryStage.setTitle("Playback");
		Button playPauseBtn = new Button("Play");
		Button drawBtn = new Button("Draw");
		HBox btnbox = new HBox();
		btnbox.getChildren().addAll(playPauseBtn, drawBtn);
		
		changeFrame(); // sets the initial frame
		ImageView frameView = new ImageView(currentFrame);
		
		Group g1 = new Group();
		g1.getChildren().addAll(btnbox, frameView);
		primaryStage.setScene(new Scene(g1));
		primaryStage.setWidth(500);
		primaryStage.setHeight(500);
		primaryStage.show();
	}
	
}
