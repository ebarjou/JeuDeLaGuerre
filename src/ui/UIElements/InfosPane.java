package ui.UIElements;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;

class InfosPane extends VBox {
    public InfosPane(ComboBox metricsDisplay, CheckBox[] metricsChannel){
        this.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, null, null)));
        this.setMinWidth(MainLayout.INFOS_WIDTH);

        metricsDisplay.getSelectionModel().selectFirst();
        this.getChildren().add(metricsDisplay);
        HBox checks = new HBox();
        checks.setAlignment(Pos.CENTER);
        this.getChildren().add(checks);
        for(CheckBox c : metricsChannel)
            checks.getChildren().add(c);

        this.getChildren().add(new Label("\nLegend : "));

        for(EUnitData unit : EUnitData.values()){
            IconTitledPane tp = new IconTitledPane(unit.getDisplayName(), unit);
            tp.setExpanded(false);
            this.getChildren().add(tp);
        }
        for(EBuildingData building : EBuildingData.values()){
            IconTitledPane tp = new IconTitledPane(building.getDisplayName(), building);
            tp.setExpanded(false);
            this.getChildren().add(tp);
        }

    }
}
