
/**
 * Flipbook.java - Create an animation from a series of drawn images.
 *
 * @author Michael Tillotson
 * @author Dalton Patterson
 * @author William Havelin
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public class FlipBook extends Application {

    private final ArrayList<Image> frameList = new ArrayList<>();
    private int frameIndex = 0;
    private static final String RENDER_FILE = "render.gif";
    private static final String ZIP_FILE = "fb_project.zip";

    private final int PROGRAM_WIDTH = 700;
    private final int CANVAS_WIDTH = PROGRAM_WIDTH - 16;
    private final int PROGRAM_HEIGHT = 700;
    private final int CANVAS_HEIGHT = PROGRAM_HEIGHT - 88;
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
    Image loadlab = new Image(getClass().getResourceAsStream("Icons/loadIcon.png"));
    Image newlab = new Image(getClass().getResourceAsStream("Icons/newIcon.png"));
    Image savelab = new Image(getClass().getResourceAsStream("Icons/saveIcon.png"));
    Image fdlab = new Image(getClass().getResourceAsStream("Icons/fdIcon.png"));
    Image linelab = new Image(getClass().getResourceAsStream("Icons/lineIcon.png"));
    Image circlelab = new Image(getClass().getResourceAsStream("Icons/circleIcon.png"));
    Image eraselab = new Image(getClass().getResourceAsStream("Icons/eraseIcon.png"));
    Image playlab = new Image(getClass().getResourceAsStream("Icons/playIcon.png"));
    Image fwdlab = new Image(getClass().getResourceAsStream("Icons/nextIcon.png"));
    Image backlab = new Image(getClass().getResourceAsStream("Icons/prevIcon.png"));
    Image exportlab = new Image(getClass().getResourceAsStream("Icons/exportIcon.png"));
    Image brushlab = new Image(getClass().getResourceAsStream("Icons/brushIcon.png"));

    Playback playback = new Playback(this, frameList);
    ImageView player = new ImageView();

    /**
     * The vector that hold the x values and y values of mouse respectively
     */
    List<Double> xvals = new ArrayList<>();
    List<Double> yvals = new ArrayList<>();

    /**
     * Resets the vector that contains all the mouse points
     */
    public void vreset() {
        xvals.clear();
        yvals.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void displayImage(Image img) {
        player.setImage(img);
    }

    private static void drawImage(GraphicsContext gc, Image img) {
        int w = (int) img.getWidth();
        int h = (int) img.getHeight();
        PixelReader reader = img.getPixelReader();
        gc.getPixelWriter().setPixels(0, 0, w, h, reader, 0, 0);
    }

    private static void showErrorDialog(Stage stage) {
        Dialog dialog = new Dialog("An Input/Output Error Occurred.");
        dialog.setTitle("IO Error");
        dialog.initOwner(stage);
        dialog.show();
    }

    /**
     * Run the program
     *
     * @param primarystage The stage that we are working on
     */
    @Override
    public void start(final Stage primarystage) {
        primarystage.setTitle("Flipbook Animator");

        Button loadbtn = new Button();
        loadbtn.setGraphic(new ImageView(loadlab));
        loadbtn.setTooltip(new Tooltip("Load program"));

        Button newbtn = new Button();
        newbtn.setGraphic(new ImageView(newlab));
        newbtn.setTooltip(new Tooltip("New slide"));

        Button savebtn = new Button();
        savebtn.setGraphic(new ImageView(savelab));
        savebtn.setTooltip(new Tooltip("Save current animation"));

        Button exportbtn = new Button();
        exportbtn.setGraphic(new ImageView(exportlab));
        exportbtn.setTooltip(new Tooltip("Exports files to a gif"));

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

        final Button playbtn = new Button();
        playbtn.setGraphic(new ImageView(playlab));
        playbtn.setTooltip(new Tooltip("Switch to playback view"));

        Button backbtn = new Button();
        backbtn.setGraphic(new ImageView(backlab));
        backbtn.setTooltip(new Tooltip("Go back a frame"));

        Button fwdbtn = new Button();
        fwdbtn.setGraphic(new ImageView(fwdlab));
        fwdbtn.setTooltip(new Tooltip("Go foward a frame"));
        

        HBox btnbox = new HBox();
        btnbox.getChildren().addAll(loadbtn, newbtn, savebtn, exportbtn, fdbtn, linebtn, circlebtn, erasebtn, playbtn, backbtn, fwdbtn);

        final Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
//		ImageView player = new ImageView(eraselab);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        GridPane grid = new GridPane();
        GridPane.setConstraints(btnbox, 1, 1);
        GridPane.setConstraints(canvas, 1, 2);
        GridPane.setConstraints(player, 1, 2);
        grid.getChildren().addAll(btnbox, canvas, player);

//		Group g1 = new Group();
//		g1.getChildren().add(grid);
        Scene scene = new Scene(grid);
        scene.getStylesheets().add("flipbookStyle.css");
        primarystage.setScene(scene);
        primarystage.setWidth(PROGRAM_WIDTH);
        primarystage.setHeight(PROGRAM_HEIGHT);
        primarystage.setMinHeight(PROGRAM_HEIGHT);
        primarystage.setMinWidth(PROGRAM_WIDTH);
        primarystage.setMaxHeight(PROGRAM_HEIGHT);
        primarystage.setMaxWidth(PROGRAM_WIDTH);
        primarystage.show();

        /**
         * When the New button is pressed, Does nothing.
         */
        loadbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Load button placeholder");
                FD = false;
                L = true;
                C = false;
                E = false;
                vreset();
                frameList.clear();
                try {
                    frameList.addAll(SaveAndRender.load(ZIP_FILE));
                } catch (IOException ex) {
                    showErrorDialog(primarystage);
                }
            }
        });

        newbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                gc.setFill(Color.WHITE);
                gc.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
                FD = false;
                L = false;
                C = false;
                E = false;
                gc.setFill(Color.WHITE);
                gc.fillRect(0, 0, 400, 400);
            }
        });

        /**
         * When the Save button is pressed, Does nothing.
         */
        savebtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                WritableImage tempFrame = new WritableImage(CANVAS_WIDTH, CANVAS_HEIGHT);
                System.out.println("The index is " + frameIndex);
                canvas.snapshot(null, tempFrame);
                if (frameIndex < frameList.size() - 1)
                    frameList.set(frameIndex, tempFrame);
                else
                    frameList.add(frameIndex, tempFrame);
                frameIndex++;
                FD = false;
                L = false;
                C = false;
                E = false;
                File dest = new File(ZIP_FILE);
                Optional<Image> background = Optional.empty();
                try {
                    SaveAndRender.saveProgress(frameList, background, dest);
                } catch (IOException ex) {
                    showErrorDialog(primarystage);
                }
            }
        });

        exportbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("export button placeholder");
                FD = false;
                L = true;
                C = false;
                E = false;
                vreset();
                File dest = new File(RENDER_FILE);
                Optional<Image> background = Optional.empty();
                try {
                    SaveAndRender.renderGif(frameList, background, dest, 200, true);
                } catch (IOException ex) {
                    showErrorDialog(primarystage);
                }
            }
        });

        /**
         * When the Free Draw button is pressed, set the Free Draw boolean to
         * true
         */
        fdbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
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
            @Override
            public void handle(ActionEvent e) {
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
            @Override
            public void handle(ActionEvent e) {
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
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Erase button placeholder");
                FD = false;
                L = false;
                C = false;
                E = true;
                vreset();
            }
        });

        /**
         * When the Play button is press, Play
         */
        playbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("Playback button placeholder");
                FD = false;
                L = false;
                C = false;
                E = false;
                vreset();
                if (canvas.isVisible()) {
                    playback.setPlayback(0);
                    canvas.setVisible(false);
                    player.setVisible(true);
                } else {
                    canvas.setVisible(true);
                    player.setVisible(false);
                    playbtn.setGraphic(new ImageView(brushlab));
                    playbtn.setTooltip(new Tooltip("Switch back to drawing"));
                }
            }
        });

        backbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                FD = false;
                L = false;
                C = false;
                E = true;
                vreset();
                if (frameIndex > 0) {
                    frameIndex--;
                    Image currentImage = frameList.get(frameIndex);
                    drawImage(canvas.getGraphicsContext2D(), currentImage);
                }
                System.out.println("The index is " + frameIndex);
            }
        });

        fwdbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                FD = false;
                L = false;
                C = false;
                E = true;
                vreset();
                if (frameIndex < frameList.size() - 1) {
                    frameIndex++;
                    Image currentImage = frameList.get(frameIndex);
                    drawImage(canvas.getGraphicsContext2D(), currentImage);
                }
                System.out.println("The index is " + frameIndex);
            }
        });


        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        double x = e.getX();
                        double y = e.getY();
                        xvals.add(x);
                        yvals.add(y);
                    }
                });
        /**
         * When the mouse is clicked, draw on the canvas depending on the set
         * boolean value
         */
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        double x = e.getX();
                        double y = e.getY();
                        xvals.add(x);
                        yvals.add(y);

                        ix = (double) xvals.get(0);
                        iy = (double) yvals.get(0);

                        if (FD == true) {
                            gc.setLineWidth(5);
                            gc.setStroke(Color.BLACK);
                            gc.strokeLine(x, y, x, y);
                        }
                        if (E == true) {
                            gc.setStroke(Color.WHITE);
                            gc.setLineWidth(5);
                            gc.strokeLine(x, y, x, y);
                        }

                        if (L == true) {
                            gc.setStroke(Color.BLACK);
                            gc.setLineWidth(5);
                            gc.strokeLine(ix, iy, ix, iy);
                        }
                    }
                });

        /**
         * When the mouse button is released, draw on the canvas depending on
         * the set boolean value
         */
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        ix = (double) xvals.get(0);
                        fx = (double) xvals.get(xvals.size() - 1);
                        iy = (double) yvals.get(0);
                        fy = (double) yvals.get(yvals.size() - 1);

                        if (L == true) {
                            gc.setLineWidth(5);
                            gc.setStroke(Color.BLACK);
                            gc.strokeLine(ix, iy, fx, fy);
                            vreset();
                        }
                        if (C == true) {
                            gc.setLineWidth(5);
                            gc.setStroke(Color.BLACK);
                            double w = Math.abs(fx - ix);
                            if (fx > ix) {
                                gc.strokeOval(ix, iy, w, w);
                                vreset();
                            } else {
                                gc.strokeOval(w, w, fx, fy);
                                vreset();
                            }
                        }
                    }
                });
    }
}
