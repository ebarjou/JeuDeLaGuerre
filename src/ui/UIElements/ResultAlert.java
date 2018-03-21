package ui.UIElements;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ResultAlert extends Alert {
    private static ResultAlert instance = null;

    private TextArea textArea;

    public static ResultAlert getInstance(){
        if(instance == null) instance = new ResultAlert();
        return instance;
    }

    public ResultAlert(){
        super(AlertType.INFORMATION);
        setTitle("Invalid Action");
        setHeaderText("This command in not valid.");


        // Create expandable Exception.


        Label label = new Label("The exception stacktrace was:");

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
        if(line.length == 0){
            setContentText("An error occured.");
        } else if(line.length == 1){
            setContentText(line[0]);
            textArea.setText(message);
        } else {
            setContentText(line[0]);
            textArea.setText(message);
        }
    }
}
