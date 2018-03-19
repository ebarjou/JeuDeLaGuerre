package system;

import game.EPlayer;
import game.Game;
import game.board.Building;
import game.board.Unit;
import game.gameState.GameState;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;

import java.io.*;
import java.util.List;

public class LoadFile {

    //TODO: Need exception on convertBuilding and convertUnit
    public LoadFile() {
    }

    public void save(String filename, GameState gs) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
        bw.write(gs.getWidth() + ";" + gs.getHeight() + "\n");
        bw.write((gs.getActualPlayer().ordinal() + 1) + ";" + gs.getActionLeft() + "\n");

        List<Building> buildings = gs.getAllBuildings();
        for(Building b : buildings){
            bw.write(b.getBuildingData().getID() + ";"
                    + (b.getX() + 1) + ";" + (b.getY() + 1) + ";"
                    + (b.getPlayer().ordinal() + 1) + "\n");
        }

        List<Unit> allUnits = gs.getAllUnits();
        for(Unit u : allUnits){
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

    private boolean hasToMove(Unit u, GameState gs){
        List<Unit> priority = gs.getPriorityUnits();
        for(Unit unit : priority)
            if(u.getX() == unit.getX() && u.getY() == unit.getY())
                return true;
        return false;
    }

    private boolean isLastMove(Unit u, GameState gs){

        Unit tmp;
        try{
            tmp = gs.getLastUnitMoved();
        } catch (NullPointerException e){
            return false;
        }
        return tmp.getX() == u.getX() && tmp.getY() == u.getY();
    }

    private boolean canAttack(Unit u, GameState gs){
        List<Unit> cantAttackUnits = gs.getCantAttackUnits();
        for(Unit unit : cantAttackUnits)
            if(u.getX() == unit.getX() && u.getY() == unit.getY())
                return false;
        return true;
    }

    private EBuildingData convertBuilding(String s) {
        for (EBuildingData e : EBuildingData.values())
            if (e.getID().equalsIgnoreCase(s))
                return e;

        System.out.println("Error");
        return null;
    }

    private EUnitData convertUnit(String s) {
        for (EUnitData e : EUnitData.values())
            if (e.getID().equalsIgnoreCase(s))
                return e;

        System.out.println("Error");
        return null;
    }

    //TODO: Add try catch and controls on type we should have
    public void loadFile(String name) throws IOException, BadFileFormatException {
        if (name == null || name.isEmpty())
            throw new IOException("Path is empty, can't load.");
        //board = new Board(25, 20);

        BufferedReader br = new BufferedReader(new FileReader(name));
        String line;
        line = br.readLine();
        String[] tokens = line.split(";");
        // init board
        int w = Integer.parseInt(tokens[0]);
        int h = Integer.parseInt(tokens[1]);

        GameState gameState = new GameState(w, h);
        gameState.removeAll();

        // set player
        line = br.readLine();
        tokens = line.split(";");
        int player = Integer.parseInt(tokens[0]);
        gameState.setActualPlayer(EPlayer.values()[player - 1]);
        gameState.setActionLeft(Integer.parseInt(tokens[1]));

        //Add buildings
        while ((line = br.readLine()) != null) {
            tokens = line.split(";");
            if(tokens.length > 4)
                break;
            EBuildingData buildingData;
            int x, y;
            EPlayer owner;
            try {
                buildingData = convertBuilding(tokens[0]);
                x = Integer.parseInt(tokens[1]) - 1;
                y = Integer.parseInt(tokens[2]) - 1;
                owner = EPlayer.values()[Integer.parseInt(tokens[3]) - 1];
            } catch(Exception e) {
                br.close();
                throw new BadFileFormatException();
            }
            Building building = new Building(buildingData, owner);
            building.setPosition(x, y);
            gameState.addBuilding(building);
        }

        //Add units
        while (line != null) {
            tokens = line.split(";");
            EUnitData unitData;
            int x, y;
            boolean hasToMove, canMove, isLastMove, canAttack;
            EPlayer owner;
            try {
                unitData = convertUnit(tokens[0]);
                x = Integer.parseInt(tokens[1]) - 1;
                y = Integer.parseInt(tokens[2]) - 1;
                hasToMove = Boolean.parseBoolean(tokens[3]);
                canMove = Boolean.parseBoolean(tokens[4]);
                isLastMove = Boolean.parseBoolean(tokens[5]);
                canAttack = Boolean.parseBoolean(tokens[6]);
                owner = EPlayer.values()[Integer.parseInt(tokens[7]) - 1];
            } catch(Exception e){
                br.close();
                throw new BadFileFormatException();
            }

            Unit unit = new Unit(unitData, owner);
            unit.setPosition(x, y);
            unit.setCanMove(canMove);

            if(hasToMove)
                gameState.addPriorityUnit(unit);
            else if(!canAttack)
                gameState.getCantAttackUnits().add(unit);

            if(isLastMove)
                gameState.setLastUnitMoved(unit);

            gameState.addUnit(unit);
            line = br.readLine();
        }
        br.close();
        Game.getInstance().reinit(gameState);
    }
}
