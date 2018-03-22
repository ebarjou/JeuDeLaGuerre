package ui.UIElements;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ResultAlert extends Alert {
    private static ResultAlert instance = null;
    private Label label;
    private static final String invalidActionTitle = "Invalid Action";
    private static final String invalidActionHeader = "An error occurred while processing this command";
    private static final String invalidActionLabel = "Detailed error report : ";

    private TextArea textArea;

    public static ResultAlert getInstance(){
        if(instance == null) instance = new ResultAlert();
        return instance;
    }

    private ResultAlert(){
        super(AlertType.INFORMATION);
        setTitle(invalidActionTitle);
        setHeaderText(invalidActionHeader);

        // Create expandable Exception.
        label = new Label(invalidActionLabel);

        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);


        getDialogPane().setExpandableContent(expContent);
    }

    public void setMessage(String message){
        String[] line = message.split("\n");
        switch (line.length) {
            case 0:
                setContentText("An error occured.");
                break;
            default:
                setContentText(line[0]);
                textArea.setText(message);
                break;
        }
    }

    public void setMessage(String title, String header, String label, String message) {
        getDialogPane().setHeaderText(header);
        setTitle(title);
        this.label.setText(label);
        setMessage(message);
    }

    public void resetDisplayText(){
        getDialogPane().setHeaderText(invalidActionHeader);
        setTitle(invalidActionTitle);
        this.label.setText(invalidActionLabel);
    }
}
