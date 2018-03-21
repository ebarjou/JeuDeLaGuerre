package ui.UIElements;

import game.EPlayer;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;

class IconTitledPane extends TitledPane {

    public IconTitledPane(String titleText, EUnitData unit) {
        super();
        this.setText(titleText);
        Label content = new Label("Atk : "+unit.getAtkValue()+"\n"
                +"Def : "+unit.getDefValue()+"\n"
                +"Mov : "+unit.getMovementValue()+"\n"
                +"Range : "+unit.getFightRange()+"\n"
                + (unit.isGetBonusDef()?"+ Can use Def. building\n":"")
                + (unit.isCanCharge()?"+ Can charge\n":""));
        this.setContent(content);
        setAnimated(true);
        setCollapsible(true);
        Canvas canvas = new Canvas(32, 32);
        BoardDrawer.drawUnit(canvas.getGraphicsContext2D(), unit, EPlayer.PLAYER_NORTH, 0, 0, 32);
        setGraphic(canvas);
        setContentDisplay(ContentDisplay.LEFT);
    }

    public IconTitledPane(String titleText, EBuildingData building) {
        super();
        this.setText(titleText);
        Label content = new Label("Accessible : "+building.isAccessible()+"\n" +
                "Def bonus : "+building.getBonusDef()+"\n");
        this.setContent(content);
        setAnimated(true);
        setCollapsible(true);
        Canvas canvas = new Canvas(32, 32);
        BoardDrawer.drawBuilding(canvas.getGraphicsContext2D(), building, EPlayer.PLAYER_NORTH, (building==EBuildingData.FORTRESS?6:0), (building==EBuildingData.FORTRESS?6:0), (building==EBuildingData.FORTRESS?20:32), 0);
        setGraphic(canvas);
        setContentDisplay(ContentDisplay.LEFT);
    }
}