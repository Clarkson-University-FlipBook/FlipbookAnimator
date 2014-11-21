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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class FlipBook extends Application {
	
	public boolean FD = false;
	public boolean L = false;
	public boolean C = false;
	public boolean E = false;
	
	double ix;
	double iy;
	double fx;
	double fy;
	
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

	@Override
	public void start(Stage primarystage) {
		primarystage.setTitle("Testing Title");
		Button newbtn = new Button("New");
		Button savebtn = new Button("Save");
		Button fdbtn = new Button("Free Draw");
		Button linebtn = new Button("Line");
		Button circlebtn = new Button("Circle");
		Button erasebtn = new Button("Erase");
		
		HBox btnbox = new HBox();
		btnbox.getChildren().addAll(newbtn,savebtn,fdbtn,linebtn,circlebtn,erasebtn);
		
		Canvas canvas = new Canvas(400,400);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		GridPane grid = new GridPane();
		grid.setConstraints(btnbox,1,1);
		grid.setConstraints(canvas,1,2);
		grid.getChildren().addAll(btnbox,canvas);
		
		Group g1 = new Group();
		g1.getChildren().add(grid);
		primarystage.setScene(new Scene(g1));
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
		
		canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
		new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				double x = e.getX();
				double y = e.getY();
				xvals.addElement(x);
				yvals.addElement(y);
//				System.out.println("This is pixel X: " + e.getX());
//				System.out.println("This is pixel Y: " + e.getY());
				
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
						gc.strokeOval(fx, fy, w, w);
						vreset();
					}
				}
			}
		});
		
		
		
		
		
	}
}
