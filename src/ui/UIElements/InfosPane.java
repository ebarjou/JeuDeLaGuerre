package ui.UIElements;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;

class InfosPane extends VBox {
    public InfosPane(ChoiceBox metricsDisplay, CheckBox metricsChannel_1, CheckBox metricsChannel_2){
        this.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, null, null)));
        this.setMinWidth(MainLayout.INFOS_WIDTH);
		this.setAlignment(Pos.TOP_CENTER);
        metricsDisplay.getSelectionModel().selectFirst();
        this.getChildren().add(metricsDisplay);
        HBox checks = new HBox();
        checks.setAlignment(Pos.CENTER);
        checks.setSpacing(5);
		checks.setPadding(new Insets(5, 5, 5, 5));
        this.getChildren().add(checks);
        checks.getChildren().add(metricsChannel_1);
        checks.getChildren().add(metricsChannel_2);

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
