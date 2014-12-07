import java.util.ArrayList;
import java.util.Vector;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FlipBook extends Application {
	
	public boolean FD = false;
	public boolean L = false;
	public boolean C = false;
	public boolean E = false;
	
	double ix;
	double iy;
	double fx;
	double fy;


	

	Image newlab = new Image (getClass().getResourceAsStream("newIcon.png"));
	Image savelab = new Image (getClass().getResourceAsStream("saveIcon.png"));
	Image fdlab = new Image (getClass().getResourceAsStream("fdIcon.png"));
	Image linelab = new Image (getClass().getResourceAsStream("lineIcon.png"));
	Image circlelab = new Image (getClass().getResourceAsStream("circleIcon.png"));
	Image eraselab = new Image (getClass().getResourceAsStream("eraseIcon.png"));
	Image playlab = new Image (getClass().getResourceAsStream("newIcon.png"));
	
	
	Playback playback = new Playback(this, new ArrayList<Image>());
	ImageView player = new ImageView(eraselab);
	
	Vector xvals = new Vector();
	Vector yvals = new Vector();
	
	public void vreset()
	{
		xvals.removeAllElements();
		yvals.removeAllElements();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void displayImage(Image img){
		player.setImage(img);
	}

	@Override
	public void start(Stage primarystage) {
		primarystage.setTitle("Flipbook Animator");
		
//		Rectangle filler = new Rectangle(0,0,484,40);
		
		Button newbtn = new Button();
		newbtn.setGraphic(new ImageView(newlab));
		newbtn.setTooltip(new Tooltip("New slide"));
		
		Button savebtn = new Button();
		savebtn.setGraphic(new ImageView(savelab));
		savebtn.setTooltip(new Tooltip("Save current animation"));
		
		Button fdbtn = new Button();
		fdbtn.setGraphic(new ImageView(fdlab));
		fdbtn.setTooltip(new Tooltip("Free draw tool"));
		
		Button linebtn = new Button();
		linebtn.setGraphic(new ImageView(linelab));
		linebtn.setTooltip(new Tooltip("Line tool"));
		
		Button circlebtn = new Button();
		circlebtn.setGraphic(new ImageView(circlelab));
		circlebtn.setTooltip(new Tooltip("Circle tool"));
		
		Button erasebtn = new Button();
		erasebtn.setGraphic(new ImageView(eraselab));
		erasebtn.setTooltip(new Tooltip("Eraser tool"));
		
		Button playbtn = new Button();
		playbtn.setGraphic(new ImageView(playlab));
		playbtn.setTooltip(new Tooltip("Switch to playback view"));
		
		HBox btnbox = new HBox();
		btnbox.getChildren().addAll(newbtn,savebtn,fdbtn,linebtn,circlebtn,erasebtn,playbtn);
		
		
		Canvas canvas = new Canvas(484,412);
//		ImageView player = new ImageView(eraselab);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		GridPane grid = new GridPane();
		grid.setConstraints(btnbox,1,1);
		grid.setConstraints(canvas,1,2);
		grid.setConstraints(player,1,2);
		grid.getChildren().addAll(btnbox,canvas,player);
		
//		Group g1 = new Group();
//		g1.getChildren().add(grid);
		Scene scene = new Scene(grid);
		scene.getStylesheets().add("flipbookStyle.css");
		primarystage.setScene(scene);
		primarystage.setWidth(500);
		primarystage.setHeight(500);
		primarystage.show();
		
		
		
		newbtn.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        System.out.println("New button placeholder");
				FD = false;
				L = false;
				C = false;
				E = false;
		    }
		});
		
		savebtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				System.out.println("Save button placeholder");
				FD = false;
				L = false;
				C = false;
				E = false;
			}
		});
		
		fdbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				System.out.println("Free Draw button placeholder");
				FD = true;
				L = false;
				C = false;
				E = false;
				vreset();
			}
		});
		
		linebtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				System.out.println("Line button placeholder");
				FD = false;
				L = true;
				C = false;
				E = false;
				vreset();
			}
		});
		
		circlebtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				System.out.println("Circle button placeholder");
				FD = false;
				L = false;
				C = true;
				E = false;
				vreset();
			}
		});
		
		erasebtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				System.out.println("Erase button placeholder");
				FD = false;
				L = false;
				C = false;
				E = true;
				vreset();
			}
		});
		
		playbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				System.out.println("Playback button placeholder");
				FD = false;
				L = false;
				C = false;
				E = false;
				vreset();
				if(canvas.isVisible()){
					playback.setPlayback(0);
					canvas.setVisible(false);
				}
				else{
					canvas.setVisible(true);
				}
			}
		});
		
		canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
		new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				double x = e.getX();
				double y = e.getY();
				xvals.addElement(x);
				yvals.addElement(y);
				
				ix = (double) xvals.firstElement();
				iy = (double) yvals.firstElement();
				
				if(FD == true) {
					gc.setLineWidth(5);
					gc.setStroke(Color.BLACK);
					gc.strokeLine(x,y,x,y);
				}
				if(E == true) {
					gc.setStroke(Color.WHITE);
					gc.setLineWidth(5);
					gc.strokeLine(x,y,x,y);
				}
				
				if(L == true){
					gc.setStroke(Color.BLACK);
					gc.setLineWidth(5);
					gc.strokeLine(ix, iy, ix, iy);
				}
			}
		});
		
		canvas.addEventHandler(MouseEvent.MOUSE_RELEASED,
		new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				ix = (double) xvals.firstElement();
				fx = (double) xvals.lastElement();
				iy = (double) yvals.firstElement();
				fy = (double) yvals.lastElement();
				
				if(L == true) {
					gc.setLineWidth(5);
					gc.setStroke(Color.BLACK);
					gc.strokeLine(ix,iy,fx,fy);
					vreset();
				}
				if(C == true) {
					gc.setLineWidth(5);
					gc.setStroke(Color.BLACK);
					double w = Math.abs(fx - ix);
					if(fx > ix) {
						gc.strokeOval(ix, iy, w, w);
						vreset();
					} else
					{
						gc.strokeOval(w, w, fx, fy);
						vreset();
					}
				}
			}
		});
		
		
		
		
		
	}
}
