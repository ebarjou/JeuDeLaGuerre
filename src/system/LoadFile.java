package system;

import game.EPlayer;
import game.board.Board;
import game.gameMaster.GameMaster;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoadFile {
    private Board boardManager;
    private GameMaster gameMaster;

    //TODO: Need exception on convertBuilding and convertUnit
    public LoadFile() {
    }

    private EBuildingData convertBuilding(String s) {
        for (EBuildingData e : EBuildingData.values())
            if (e.getID().equalsIgnoreCase(s))
                return e;

        System.out.println("Error");
        return EBuildingData.ARSENAL;
    }

    private EUnitData convertUnit(String s) {
        for (EUnitData e : EUnitData.values())
            if (e.getID().equalsIgnoreCase(s))
                return e;

        System.out.println("Error");
        return EUnitData.INFANTRY;
    }

    //TODO: Add try catch and controls on type we should have
    public void loadFile(String name) throws IOException {
        if (name == null || name.isEmpty())
            throw new IOException("Path is empty, can't load.");
        boardManager = new Board(25, 20);
        gameMaster = new GameMaster();
        gameMaster.removeAll();


        BufferedReader br = new BufferedReader(new FileReader(name));
        String line;
        line = br.readLine();
        String[] tokens = line.split(";");
        // init board
        boardManager = new Board(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));

        // set player
        line = br.readLine();
        tokens = line.split(";");
        int player = Integer.parseInt(tokens[0]);
        if (player == 1) {
            gameMaster.setPlayer(EPlayer.PLAYER_NORTH);
        } else {
            gameMaster.setPlayer(EPlayer.PLAYER_SOUTH);
        }
        gameMaster.setActionLeft(Integer.parseInt(tokens[1]));

        //Add buildings
        while ((line = br.readLine()) != null) {
            tokens = line.split(";");
            if (tokens.length == 4)
                break;
            EBuildingData e = convertBuilding(tokens[0]);
            int x = Integer.parseInt(tokens[1]) - 1;
            int y = Integer.parseInt(tokens[2]) - 1;
            boolean destroy = Boolean.parseBoolean(tokens[3]);
            EPlayer p = Integer.parseInt(tokens[4]) == 1 ? EPlayer.PLAYER_NORTH : EPlayer.PLAYER_SOUTH;
            gameMaster.addBuilding(p, e);
            boardManager.setBuilding(e, p, x, y);
        }
        //Add units
        while ((line = br.readLine()) != null) {
            EUnitData u = convertUnit(tokens[0]);
            int x = Integer.parseInt(tokens[1]) - 1;
            int y = Integer.parseInt(tokens[2]) - 1;
            /*
            boolean hasToMove = Boolean.parseBoolean(tokens[3]);
            boolean hasMoved = Boolean.parseBoolean(tokens[4]);
            boolean hasAttacked = Boolean.parseBoolean(tokens[5]);
            */
            EPlayer p = Integer.parseInt(tokens[3]) == 1 ? EPlayer.PLAYER_NORTH : EPlayer.PLAYER_SOUTH;
            gameMaster.addUnit(p, u);
            boardManager.setUnit(u, p, x, y);
            tokens = line.split(";");
        }
        br.close();

    }

}
