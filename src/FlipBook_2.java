/**
 * Flipbook.java - Create an animation from a series of drawn images.
 * @author Michael Tillotson
 * @author Dalton Patterson
 */
 
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
	
	/**
	 * Boolean values to determine what button is pressed.
	 */
	public boolean FD = false;
	public boolean L = false;
	public boolean C = false;
	public boolean E = false;
	
	/**
	 * The initial (x,y) point (ix and iy) and the final (x,y) point (fx and fy)
	 */
	double ix;
	double iy;
	double fx;
	double fy;
	
	/**
	 * Images that will be the icons of the different buttons
	 */
	Image newlab = new Image (getClass().getResourceAsStream("newIcon.png"));
	Image savelab = new Image (getClass().getResourceAsStream("saveIcon.png"));
	Image fdlab = new Image (getClass().getResourceAsStream("fdIcon.png"));
	Image linelab = new Image (getClass().getResourceAsStream("lineIcon.png"));
	Image circlelab = new Image (getClass().getResourceAsStream("circleIcon.png"));
	Image eraselab = new Image (getClass().getResourceAsStream("eraseIcon.png"));
	
	/**
	 * The vector that hold the x values and y values of mouse respectively
	 */
	Vector xvals = new Vector();
	Vector yvals = new Vector();
	
	/**
	 * Resets the vector that contains all the mouse points
	 */
	public void vreset()
	{
		xvals.removeAllElements();
		yvals.removeAllElements();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Run the program
	 * @param The stage that we are working on
	 */
	@Override
	public void start(Stage primarystage) {
		primarystage.setTitle("Flipbook Animator");
		
		Button newbtn = new Button("New");
		newbtn.setGraphic(new ImageView(newlab));
		newbtn.setTooltip(new Tooltip("New slide"));
		
		Button savebtn = new Button("Save");
		savebtn.setGraphic(new ImageView(savelab));
		savebtn.setTooltip(new Tooltip("Save current animation"));
		
		Button fdbtn = new Button("Free Draw");
		fdbtn.setGraphic(new ImageView(fdlab));
		fdbtn.setTooltip(new Tooltip("Free draw tool"));

		Button linebtn = new Button("Line");
		linebtn.setGraphic(new ImageView(linelab));
		linebtn.setTooltip(new Tooltip("Line tool"));

		Button circlebtn = new Button("Circle");
		circlebtn.setGraphic(new ImageView(circlelab));
		circlebtn.setTooltip(new Tooltip("Circle tool"));

		Button erasebtn = new Button("Erase");
		erasebtn.setGraphic(new ImageView(eraselab));
		erasebtn.setTooltip(new Tooltip("Eraser tool"));

		HBox btnbox = new HBox();
		btnbox.getChildren().addAll(newbtn,savebtn,fdbtn,linebtn,circlebtn,erasebtn);
		
		Canvas canvas = new Canvas(484,412);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		GridPane grid = new GridPane();
		grid.setConstraints(btnbox,1,1);
		grid.setConstraints(canvas,1,2);
		grid.getChildren().addAll(btnbox,canvas);
		
		Scene scene = new Scene(grid);
		scene.getStylesheets().add("flipbookStyle.css");
		primarystage.setScene(scene);
		primarystage.setWidth(500);
		primarystage.setHeight(500);
		primarystage.show();
		
		
		/**
		 * When the New button is pressed, Does nothing.
		 */
		newbtn.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        System.out.println("New button placeholder");
				FD = false;
				L = false;
				C = false;
				E = false;
		    }
		});
		
		/**
		 * When the Save button is pressed, Does nothing.
		 */
		savebtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				System.out.println("Save button placeholder");
				FD = false;
				L = false;
				C = false;
				E = false;
			}
		});
		
		/**
		 * When the Free Draw button is pressed, set the Free Draw boolean to true
		 */
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
		
		/**
		 * When the Line button is pressed, set the Line boolean to true
		 */
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
		
		/**
		 * When the Circle button is pressed, set the Circle boolean to true
		 */
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
		
		/**
		 * When the Erase button is pressed, set the Erase boolean to true
		 */
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
		
		/**
		 * When the mouse is clicked, draw on the canvas depending on the set boolean value
		 */
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
		
		/**
		 * When the mouse button is released, draw on the canvas depending on the set boolean value
		 */
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
						gc.strokeOval(fx, fy, w, w);
						vreset();
					}
				}
			}
		});
	}
}