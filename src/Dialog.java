
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * A Dialog convenient for displaying simple messages to the user.
 *
 * @author Andres Rivas
 * @date October 2, 2014
 */
public class Dialog extends Stage {

    /**
     * Constructs a Dialog for displaying the given message.
     *
     * @param message The message to display to the user
     */
    public Dialog(final String message) {
        setScene(new Scene(initInterface(message)));
        sizeToScene();
        initModality(Modality.WINDOW_MODAL);
    }

    /**
     * Initializes the interface of this Dialog, which includes the text of the
     * message and an OK button.
     *
     * @param message The message to display to the user
     * @return
     */
    private Parent initInterface(final String message) {
        final Text text = new Text();
        text.setWrappingWidth(500);
        text.setText(message);

        final Button okButton = new Button("OK");
        okButton.setDefaultButton(true);
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                close();
            }
        });

        final GridPane rootPane = new GridPane();
        rootPane.setPadding(new Insets(10));
        rootPane.setVgap(10);
        rootPane.add(text, 0, 0);
        rootPane.add(okButton, 0, 1);
        GridPane.setHalignment(text, HPos.LEFT);
        GridPane.setHalignment(okButton, HPos.RIGHT);

        return rootPane;
    }
}
