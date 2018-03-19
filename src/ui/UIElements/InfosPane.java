package ui.UIElements;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;

public class InfosPane extends VBox {
    public InfosPane(){
        this.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, null, null)));
        this.setMinWidth(MainLayout.INFOS_WIDTH);

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Communications",
                        "Offensive map",
                        "Defense map"
                );
        ComboBox comboBox = new ComboBox(options);
        comboBox.getSelectionModel().selectFirst();
        this.getChildren().add(comboBox);

        this.getChildren().add(new Label("\nLegend : "));

        for(EUnitData unit : EUnitData.values()){

            IconTitledPane tp = new IconTitledPane(unit.name(), unit);
            tp.setExpanded(false);
            this.getChildren().add(tp);
        }
        for(EBuildingData building : EBuildingData.values()){
            IconTitledPane tp = new IconTitledPane(building.name(), building);
            tp.setExpanded(false);
            this.getChildren().add(tp);
        }

    }
}
