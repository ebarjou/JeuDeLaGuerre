package system;

import game.EPlayer;
import game.Game;
import game.board.Building;
import game.board.Unit;
import game.gameState.GameState;
import ruleEngine.entity.EBuildingProperty;
import ruleEngine.entity.EUnitProperty;

import java.io.*;
import java.util.List;

public class LoadFile {

    public LoadFile() {
    }

    public void save(String filename, GameState gs) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
        bw.write(gs.getWidth() + ";" + gs.getHeight() + "\n");
        bw.write((gs.getActualPlayer().ordinal() + 1) + ";" + gs.getActionLeft() + "\n");

        //Saving buildings
        List<Building> buildings = gs.getAllBuildings();
        for (Building b : buildings) {
            bw.write(b.getBuildingData().getID() + ";"
                    + (b.getX() + 1) + ";" + (b.getY() + 1) + ";"
                    + (b.getPlayer().ordinal() + 1) + "\n");
        }

        //Saving units
        List<Unit> allUnits = gs.getAllUnits();
        for (Unit u : allUnits) {
            bw.write(u.getUnitData().getID() + ";"                  // ID
                    + (u.getX() + 1) + ";" + (u.getY() + 1) + ";"   // x; y
                    + (hasToMove(u, gs)) + ";"                      // priority ?
                    + u.getCanMove() + ";"                         // can Move (has not moved) ?
                    + isLastMove(u, gs) + ";"                       // is lastMove ?
                    + canAttack(u, gs) + ";"                        // can initiate attack this turn?
                    + (u.getPlayer().ordinal() + 1) + "\n");        // player
        }
        bw.close();
    }

    private boolean hasToMove(Unit u, GameState gs) {
        List<Unit> priority = gs.getPriorityUnits();
        for (Unit unit : priority)
            if (u.getX() == unit.getX() && u.getY() == unit.getY())
                return true;
        return false;
    }

    private boolean isLastMove(Unit u, GameState gs) {

        Unit tmp;
        try {
            tmp = gs.getLastUnitMoved();
        } catch (NullPointerException e) {
            return false;
        }
        return tmp.getX() == u.getX() && tmp.getY() == u.getY();
    }

    private boolean canAttack(Unit u, GameState gs) {
        List<Unit> cantAttackUnits = gs.getCantAttackUnits();
        for (Unit unit : cantAttackUnits)
            if (u.getX() == unit.getX() && u.getY() == unit.getY())
                return false;
        return true;
    }

    private EBuildingProperty convertBuilding(String s) {
        for (EBuildingProperty e : EBuildingProperty.values())
            if (e.getID().equalsIgnoreCase(s))
                return e;

        System.out.println("Error");
        return null;
    }

    private EUnitProperty convertUnit(String s) {
        for (EUnitProperty e : EUnitProperty.values())
            if (e.getID().equalsIgnoreCase(s))
                return e;

        System.out.println("Error");
        return null;
    }

    public void loadFile(String name) throws IOException, BadFileFormatException {
        if (name == null || name.isEmpty())
            throw new IOException("Path is empty, can't load.");

        BufferedReader bufferedReader = new BufferedReader(new FileReader(name));
        String line = bufferedReader.readLine();
        String[] tokens = line.split(";");

        // init board
        int width = Integer.parseInt(tokens[0]);
        int height = Integer.parseInt(tokens[1]);

        GameState gameState = new GameState(width, height);
        gameState.removeAll();

        // set player
        line = bufferedReader.readLine();
        tokens = line.split(";");
        int player = Integer.parseInt(tokens[0]);
        gameState.setActualPlayer(EPlayer.values()[player - 1]);
        gameState.setActionLeft(Integer.parseInt(tokens[1]));

        //Add buildings
        while ((line = bufferedReader.readLine()) != null) {
            tokens = line.split(";");
            if (tokens.length > 4)
                break;
            EBuildingProperty buildingProperty;
            int x, y;
            EPlayer owner;
            try {
                buildingProperty = convertBuilding(tokens[0]);
                x = Integer.parseInt(tokens[1]) - 1;
                y = Integer.parseInt(tokens[2]) - 1;
                owner = EPlayer.values()[Integer.parseInt(tokens[3]) - 1];
                if (!gameState.isValidCoordinate(x, y))
                    throw new BadFileFormatException();
            } catch (Exception e) {
                bufferedReader.close();
                throw new BadFileFormatException();
            }
            Building building = new Building(buildingProperty, owner);
            building.setPosition(x, y);
            gameState.addBuilding(building);
        }

        //Add units
        while (line != null) {
            tokens = line.split(";");
            EUnitProperty unitProperty;
            int x, y;
            boolean hasToMove, canMove, isLastMove, canAttack;
            EPlayer owner;
            try {
                unitProperty = convertUnit(tokens[0]);
                x = Integer.parseInt(tokens[1]) - 1;
                y = Integer.parseInt(tokens[2]) - 1;
                hasToMove = Boolean.parseBoolean(tokens[3]);
                canMove = Boolean.parseBoolean(tokens[4]);
                isLastMove = Boolean.parseBoolean(tokens[5]);
                canAttack = Boolean.parseBoolean(tokens[6]);
                owner = EPlayer.values()[Integer.parseInt(tokens[7]) - 1];
                if (!gameState.isValidCoordinate(x, y))
                    throw new BadFileFormatException();
            } catch (Exception e) {
                bufferedReader.close();
                throw new BadFileFormatException();
            }

            Unit unit = new Unit(unitProperty, owner);
            unit.setPosition(x, y);
            unit.setCanMove(canMove);

            if (hasToMove)
                gameState.addPriorityUnit(unit);
            else if (!canAttack)
                gameState.getCantAttackUnits().add(unit);

            if (isLastMove)
                gameState.setLastUnitMoved(unit);

            gameState.addUnit(unit);
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        Game.getInstance().reinit(gameState);
    }
}
