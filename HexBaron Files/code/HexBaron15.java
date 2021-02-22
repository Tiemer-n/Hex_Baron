/*
 * Skeleton Program code for the AQA A Level Paper 1 Summer 2021 examination.
 * this code should be used in conjunction with the Preliminary Material
 * written by the AQA Programmer Team
 * developed in the NetBeans IDE 8.1 environment
 */

package aqa.hex.baron;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HexBaron {

    public HexBaron() {
        boolean fileLoaded;
        Player player1 = new Player();
        Player player2 = new Player();
        HexGrid grid;
        String choice = "";
        String fileName;
        while (!choice.equals("Q")) {
            displayMainMenu();
            choice = Console.readLine();
            if (choice.equals("1")) {
                grid = setUpDefaultGame(player1, player2);
                playGame(player1, player2, grid);
            } else if (choice.equals("2")) {
                Console.write("Enter the name of the file to load: ");
                fileName = "aqa/hex/baron/"+Console.readLine();
                Object[] returnObjects  = loadGame(fileName, player1, player2);
                fileLoaded = (boolean)returnObjects[1];
                if (fileLoaded) {
                    grid = (HexGrid)returnObjects[0];
                    playGame(player1, player2, grid);
                }
            } else if (choice.equals("3")) {
                Object[] returnObject  = createGame();
                boolean success = (boolean)returnObject[0];
                fileName = (String)returnObject[1];
                if (success) {
                    Console.write("Would you like to play the game that you've created and saved? ");
                    String answer = Console.readLine().toLowerCase();
                    if (answer.equals("yes") || answer.equals("y")) {
                        returnObject  = loadGame("aqa/hex/baron/"+fileName, player1, player2);
                        fileLoaded = (boolean)returnObject[1];
                        if (fileLoaded) {
                            grid = (HexGrid)returnObject[0];
                            playGame(player1, player2, grid);
                        }
                    }
                } else {
                    Console.writeLine("Sorry but the game could not be created.");
                }
            }
        }
    }

    Object[] createGame() {
        List<String> items;
        String lineFromFile;
        String fileName;
        HexGrid grid;
        Player player1 = new Player();
        Player player2 = new Player();
        int gridSize;
        List<String> forests;
        List<String> peatBogs;
        List<String> terrainList = new ArrayList<String>();
        List<String> newTerrain = new ArrayList<String>();
        List<Integer> p1stats = new ArrayList<Integer>();
        List<Integer> p2stats = new ArrayList<Integer>();
        List<String> p1pieces = new ArrayList<String>();
        List<String> p2pieces = new ArrayList<String>();
        List<Integer> p1locs = new ArrayList<Integer>();
        List<Integer> p2locs = new ArrayList<Integer>();
        String nextPiece = "";
        
        Console.writeLine("Welcome to the Hex Baron Game Creator");
        Console.writeLine("=====================================");
        Console.writeLine();
        Console.write("Please enter the grid size (6, 8 or 10): ");
        gridSize = Integer.parseInt(Console.readLine());
        while (gridSize != 6 && gridSize != 8 && gridSize != 10) {
            Console.write("Invalid grid size, please re-enter (6, 8 or 10): ");
            gridSize = Integer.parseInt(Console.readLine());
        }
        grid = new HexGrid(gridSize);
        for (int terrainIndex=0; terrainIndex<(gridSize*gridSize/2); terrainIndex++) {
            terrainList.add(" ");
        }
        grid.setUpGridTerrain(terrainList);
        Console.writeLine(grid.getGridAsIndices());
        
        Console.write("Please enter all the locations of peat bogs separated by commas with no spaces: ");
        peatBogs = Arrays.asList(Console.readLine().split(","));
        Console.write("Please enter all the locations of forests separated by commas with no spaces: ");
        forests = Arrays.asList(Console.readLine().split(","));
        for (int terrainIndex=0; terrainIndex<terrainList.size(); terrainIndex++) {
            String hex = ""+terrainIndex;
            if (peatBogs.contains(hex)) {
                newTerrain.add("~");
            } else if (forests.contains(hex)) {
                newTerrain.add("#");
            } else {
                newTerrain.add(" ");
            }
        }
        grid.setUpGridTerrain(newTerrain);
        Console.write("Please enter the location of the Baron for Player 1: ");
        p1locs.add(Integer.parseInt(Console.readLine()));
        p1pieces.add("Baron");
        grid.addPiece(true,"Baron",p1locs.get(0));
        Console.write("Please enter the location of the Baron for Player 2: ");
        p2locs.add(Integer.parseInt(Console.readLine()));
        p2pieces.add("Baron");
        grid.addPiece(false,"Baron",p2locs.get(0));
        Console.writeLine(grid.getGridAsString(true));
        
        Console.write("Please enter the starting amount of VPs for Player 1: ");
        p1stats.add(Integer.parseInt(Console.readLine()));
        Console.write("Please enter the starting amount of VPs for Player 2: ");
        p2stats.add(Integer.parseInt(Console.readLine()));
        Console.write("Please enter the starting amount of fuel for Player 1: ");
        p1stats.add(Integer.parseInt(Console.readLine()));
        Console.write("Please enter the starting amount of fuel for Player 2: ");
        p2stats.add(Integer.parseInt(Console.readLine()));
        Console.write("Please enter the starting amount of lumber for Player 1: ");
        p1stats.add(Integer.parseInt(Console.readLine()));
        Console.write("Please enter the starting amount of lumber for Player 2: ");
        p2stats.add(Integer.parseInt(Console.readLine()));
        Console.write("Please enter the starting amount of pieces in the supply chain for Player 1: ");
        p1stats.add(Integer.parseInt(Console.readLine()));
        Console.write("Please enter the starting amount of pieces in the supply chain for Player 2: ");
        p2stats.add(Integer.parseInt(Console.readLine()));
        
        while (!nextPiece.equals("D")) {
            Console.write("Enter piece to add for Player One (S-Serf,L-Less,P-PBDS) or D for Done: ");
            nextPiece = Console.readLine().toUpperCase();
            if (!nextPiece.equals("D")) {
                Console.write("Enter the index of the hex where that piece should be placed: ");
                p1locs.add(Integer.parseInt(Console.readLine()));
                switch (nextPiece) {
                    case "S":
                        p1pieces.add("Serf");
                        break;
                    case "P":
                        p1pieces.add("PBDS");
                        break;
                    case "L":
                        p1pieces.add("LESS");
                        break;
                }
                grid.addPiece(true,p1pieces.get(p1pieces.size()-1),p1locs.get(p1locs.size()-1));
            }
        }
        
        nextPiece = "";
        while (!nextPiece.equals("D")) {
            Console.write("Enter piece to add for Player Two (S-Serf,L-Less,P-PBDS) or D for Done: ");
            nextPiece = Console.readLine().toUpperCase();
            if (!nextPiece.equals("D")) {
                Console.write("Enter the index of the hex where that piece should be placed: ");
                p2locs.add(Integer.parseInt(Console.readLine()));
                switch (nextPiece) {
                    case "S":
                        p2pieces.add("Serf");
                        break;
                    case "P":
                        p2pieces.add("PBDS");
                        break;
                    case "L":
                        p2pieces.add("LESS");
                        break;
                }
                grid.addPiece(false,p2pieces.get(p2pieces.size()-1),p2locs.get(p2locs.size()-1));
            }
        }
        Console.writeLine(grid.getGridAsString(true));
                
        try {
            String p1string = "Player One";
            String p2string = "Player Two";
            String gridString = newTerrain.get(0);
            Console.write("What name would you like to save the file as (please include the extension): ");
            fileName = Console.readLine();
            FileWriter out = new FileWriter("aqa/hex/baron/"+fileName);
            for (int stat : p1stats) {
                p1string += ","+stat;
            }
            out.write(p1string+"\n");
            for (int stat : p2stats) {
                p2string += ","+stat;
            }
            out.write(p2string+"\n");
            out.write(""+gridSize+"\n");
            for (int terrainIndex=1; terrainIndex<newTerrain.size(); terrainIndex++) {
                gridString += ","+newTerrain.get(terrainIndex);
            }
            out.write(gridString+"\n");
            for (int p1Index=0; p1Index<p1pieces.size(); p1Index++) {
                out.write("1,"+p1pieces.get(p1Index)+","+p1locs.get(p1Index)+"\n");
            }
            for (int p2Index=0; p2Index<p2pieces.size(); p2Index++) {
                out.write("2,"+p2pieces.get(p2Index)+","+p2locs.get(p2Index)+"\n");
            }
            out.close();
        } catch (IOException e) {
            Console.writeLine("File could not be saved.");
            return new Object[]{false, null};
        }
        return new Object[]{true, fileName};
    }
    
    Object[] loadGame(String fileName, Player player1, Player player2) {
        List<String> items;
        String lineFromFile;
        HexGrid grid;
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            lineFromFile = in.readLine();
            items = Arrays.asList(lineFromFile.split(","));
            player1.setUpPlayer(items.get(0), Integer.parseInt(items.get(1)), Integer.parseInt(items.get(2)), Integer.parseInt(items.get(3)), Integer.parseInt(items.get(4)));
            lineFromFile = in.readLine();
            items = Arrays.asList(lineFromFile.split(","));
            player2.setUpPlayer(items.get(0), Integer.parseInt(items.get(1)), Integer.parseInt(items.get(2)), Integer.parseInt(items.get(3)), Integer.parseInt(items.get(4)));
            int gridSize = Integer.parseInt(in.readLine());
            grid = new HexGrid(gridSize);
            List<String> t  = Arrays.asList(in.readLine().split(","));
            grid.setUpGridTerrain(t);
            lineFromFile = in.readLine();
            while(lineFromFile != null) {
                items = Arrays.asList(lineFromFile.split(","));
                if (items.get(0).equals("1")) {
                    grid.addPiece(true, items.get(1), Integer.parseInt(items.get(2)));
                } else {
                    grid.addPiece(false, items.get(1), Integer.parseInt(items.get(2)));
                }
                lineFromFile = in.readLine();
            }
        } catch (Exception e) {
            Console.writeLine("File not loaded");
            return new Object[]{null, false};
        }
        return new Object[]{grid, true};
    }

    HexGrid setUpDefaultGame(Player player1, Player player2) {
        List<String> t = Arrays.asList(new String[]{" ", "#", "#", " ", "~", "~", " ", " ", " ", "~", " ", "#", "#", " ", " ", " ",
                                                    " ", " ", "#", "#", "#", "#", "~", "~", "~", "~", "~", " ", "#", " ", "#", " "});
        int gridSize = 8;
        HexGrid grid = new HexGrid(gridSize);
        player1.setUpPlayer("Player One", 0, 10, 10, 5);
        player2.setUpPlayer("Player Two", 1, 10, 10, 5);
        grid.setUpGridTerrain(t);
        grid.addPiece(true, "Baron", 0);
        grid.addPiece(true, "Serf", 8);
        grid.addPiece(false, "Baron", 31);
        grid.addPiece(false, "Serf", 23);
        return grid;
    }

    boolean checkMoveCommandFormat(List<String> items) {
        int result;
        if (items.size() == 3) {
            for (int count = 1; count <= 2; count++) {
                try {
                    result = Integer.parseInt(items.get(count));
                } catch (Exception e) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    boolean checkStandardCommandFormat(List<String> items) {
        int result;
        if (items.size() == 2) {
            try {
                result = Integer.parseInt(items.get(1));
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return false;
    }

    boolean checkUpgradeCommandFormat(List<String> items) {
        int result;
        if (items.size() == 3) {
            if (!items.get(1).toUpperCase().equals("LESS") && !items.get(1).toUpperCase().equals("PBDS")) {
                return false;
            }
            try {
                result = Integer.parseInt(items.get(2));
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return false;
    }

    boolean checkCommandIsValid(List<String> items) {
        if (items.size() > 0) {
        switch (items.get(0)) {
                case "move":
                    return checkMoveCommandFormat(items);
                case "dig":
                case "saw":
                case "spawn":
                    return checkStandardCommandFormat(items);
                case "upgrade":
                    return checkUpgradeCommandFormat(items);
            }
        }
        return false;
    }

    void playGame(Player player1, Player player2, HexGrid grid) {
        boolean gameOver = false;
        boolean player1Turn = true;
        boolean validCommand;
        List<String> commands = new ArrayList<>();
        Console.writeLine("Player One current state - " + player1.getStateString());
        Console.writeLine("Player Two current state - " + player2.getStateString());
        do {
            Console.writeLine(grid.getGridAsString(player1Turn));
            if (player1Turn) {
                Console.writeLine(player1.getName() + " state your three commands, pressing enter after each one.");
            } else {
                Console.writeLine(player2.getName() + " state your three commands, pressing enter after each one.");
            }
            for (int count = 1; count <= 3; count++) {
                String command;
                Console.write("Enter command: ");
                command = Console.readLine().toLowerCase();
                while (command.equals("hexes")) {
                    Console.writeLine(grid.getGridAsIndices());
                    Console.write("Enter command: ");
                    command = Console.readLine().toLowerCase();
                }
                commands.add(command);
            }
            for (String c : commands) {
                List<String> items  = Arrays.asList(c.split(" "));
                validCommand = checkCommandIsValid(items);
                if (!validCommand) {
                    Console.writeLine("Invalid command");
                } else {
                    int fuelChange = 0;
                    int lumberChange = 0;
                    int supplyChange = 0;
                    String summaryOfResult;
                    Object[] returnObjects;
                    if (player1Turn) {
                        returnObjects = grid.executeCommand(items, fuelChange, lumberChange, supplyChange,
                                                              player1.getFuel(), player1.getLumber(),
                                                              player1.getPiecesInSupply());
                        summaryOfResult = returnObjects[0].toString();
                        fuelChange = (int)returnObjects[1];
                        lumberChange = (int)returnObjects[2];
                        supplyChange = (int)returnObjects[3];
                        player1.updateLumber(lumberChange);
                        player1.updateFuel(fuelChange);
                        if (supplyChange == 1) {
                            player1.removeTileFromSupply();
                        }
                    } else {
                        returnObjects = grid.executeCommand(items, fuelChange, lumberChange, supplyChange,
                                                              player2.getFuel(), player2.getLumber(),
                                                              player2.getPiecesInSupply());
                        summaryOfResult = returnObjects[0].toString();
                        fuelChange = (int)returnObjects[1];
                        lumberChange = (int)returnObjects[2];
                        supplyChange = (int)returnObjects[3];
                        player2.updateLumber(lumberChange);
                        player2.updateFuel(fuelChange);
                        if (supplyChange == 1) {
                            player2.removeTileFromSupply();
                        }
                    }
                    Console.writeLine(summaryOfResult);
                }
            }
            commands.clear();
            player1Turn = !player1Turn;
            int player1VPsGained = 0;
            int player2VPsGained= 0;
            Object[] returnObjects;
            if (gameOver) {
                returnObjects = grid.destroyPiecesAndCountVPs(player1VPsGained, player2VPsGained);
            } else {
                returnObjects = grid.destroyPiecesAndCountVPs(player1VPsGained, player2VPsGained);
                gameOver = (boolean)returnObjects[0];
            }
            player1VPsGained = (int)returnObjects[1];
            player2VPsGained = (int)returnObjects[2];
            player1.addToVPs(player1VPsGained);
            player2.addToVPs(player2VPsGained);
            Console.writeLine("Player One current state - " + player1.getStateString());
            Console.writeLine("Player Two current state - " + player2.getStateString());
            Console.write("Press Enter to continue...");
            Console.readLine();
        } while (!gameOver || !player1Turn);
        Console.writeLine(grid.getGridAsString(player1Turn));
        displayEndMessages(player1, player2);
    }

    void displayEndMessages(Player player1, Player player2) {
        Console.writeLine();
        Console.writeLine(player1.getName() + " final state: " + player1.getStateString());
        Console.writeLine();
        Console.writeLine(player2.getName() + " final state: " + player2.getStateString());
        Console.writeLine();
        if (player1.getVPs() > player2.getVPs()) {
            Console.writeLine(player1.getName() + " is the winner!");
        } else {
            Console.writeLine(player2.getName() + " is the winner!");
        }
    }

    void displayMainMenu() {
        Console.writeLine("1. Default game");
        Console.writeLine("2. Load game");
        Console.writeLine("3. Create game");
        Console.writeLine("Q. Quit");
        Console.writeLine();
        Console.write("Enter your choice: ");
    }

    public static void main(String[] args) {
        new HexBaron();
    }
}

class Piece {
    protected boolean destroyed, belongsToplayer1;
    protected int fuelCostOfMove, vPValue, connectionsToDestroy;
    protected String pieceType;

    public Piece(boolean player1) {
        fuelCostOfMove = 1;
        belongsToplayer1 = player1;
        destroyed = false;
        pieceType = "S";
        vPValue = 1;
        connectionsToDestroy = 2;
    }

    public int getVPs() {
        return vPValue;
    }

    public boolean getBelongsToplayer1() {
        return belongsToplayer1;
    }

    public int checkMoveIsValid(int distanceBetweenTiles, String startTerrain, String endTerrain) {
        if (distanceBetweenTiles == 1) {
            if (startTerrain.equals("~") || endTerrain.equals("~")) {
                return fuelCostOfMove * 2;
            } else {
                return fuelCostOfMove;
            }
        }
        return -1;
    }

    public boolean hasMethod(String methodName) {
        try {
            this.getClass().getMethod(methodName, String.class);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    public int getConnectionsNeededToDestroy() {
        return connectionsToDestroy;
    }

    public String getPieceType() {
        if (belongsToplayer1) {
            return pieceType;
        } else {
            return pieceType.toLowerCase();
        }
    }

    public void destroyPiece() {
        destroyed = true;
    }
}

class BaronPiece extends Piece {
    public BaronPiece(boolean player1) {
        super(player1);
        pieceType = "B";
        vPValue = 10;
    }

    @Override
    public int checkMoveIsValid(int distanceBetweenTiles, String startTerrain, String endTerrain) {
        if (distanceBetweenTiles == 1) {
            return fuelCostOfMove;
        }
        return -1;
    }
}

class LESSPiece extends Piece {
    public LESSPiece (boolean player1) {
        super(player1);
        pieceType = "L";
        vPValue = 3;
    }

    @Override
    public int checkMoveIsValid(int distanceBetweenTiles, String startTerrain, String endTerrain) {
        if (distanceBetweenTiles == 1 && !startTerrain.equals("#")) {
            if (startTerrain.equals("~") || endTerrain.equals("~")) {
                return fuelCostOfMove * 2;
            } else {
                return fuelCostOfMove;
            }
        }
        return -1;
    }

    public int saw(String terrain) {
        if (!terrain.equals("#")) {
            return 0;
        }
        return 1;
    }
}

class PBDSPiece extends Piece {
    public static Random rNoGen = new Random();
    public PBDSPiece(boolean player1) {
        super(player1);
        pieceType = "P";
        vPValue = 2;
        fuelCostOfMove = 2;
    }

    @Override
    public int checkMoveIsValid(int distanceBetweenTiles, String startTerrain, String endTerrain) {
        if (distanceBetweenTiles != 1 || startTerrain.equals("~")) {
            return -1;
        }
        return fuelCostOfMove;
    }

    public int dig(String terrain) {
        if (!terrain.equals("~")) {
            return 0;
        }
        if (rNoGen.nextFloat() < 0.9) {
            return 1;
        } else {
            return 5;
        }
    }
}

class Tile {
    protected String terrain;
    protected int x, y, z;
    protected Piece pieceInTile;
    protected List<Tile> neighbours = new ArrayList<>();

    public Tile(int xCoord, int yCoord, int zCoord) {
        x = xCoord;
        y = yCoord;
        z = zCoord;
        terrain = " ";
        pieceInTile = null;
    }

    public int getDistanceToTileT(Tile t) {
        return Math.max(Math.max(Math.abs(this.getx() - t.getx()),
                                 Math.abs(this.gety() - t.gety())),
                                 Math.abs(this.getz() - t.getz()));
    }

    public void addToNeighbours(Tile N) {
        neighbours.add(N);
    }

    public List<Tile> getNeighbours() {
        return neighbours;
    }

    public void setPiece(Piece thePiece) {
        pieceInTile = thePiece;
    }

    public void setTerrain(String t) {
        terrain = t;
    }

    public int getx() {
        return x;
    }

    public int gety() {
        return y;
    }

    public int getz() {
        return z;
    }

    public String getTerrain() {
        return terrain;
    }

    public Piece getPieceInTile() {
        return pieceInTile;
    }
}

class HexGrid {
    protected List<Tile> tiles = new ArrayList<>();
    protected List<Piece> pieces = new ArrayList<>();
    protected int size;
    protected boolean player1Turn;

    public HexGrid(int n) {
        size = n;
        setUpTiles();
        setUpNeighbours();
        player1Turn = true;
    }

    public void setUpGridTerrain(List<String> listOfTerrain) {
        for (int count = 0; count < listOfTerrain.size(); count++) {
            tiles.get(count).setTerrain(listOfTerrain.get(count));
        }
    }

    public void addPiece(boolean belongsToplayer1, String typeOfPiece, int location) {
        Piece newPiece;
        switch (typeOfPiece) {
            case "Baron":
                newPiece = new BaronPiece(belongsToplayer1);
                break;
            case "LESS":
                newPiece = new LESSPiece(belongsToplayer1);
                break;
            case "PBDS":
                newPiece = new PBDSPiece(belongsToplayer1);
                break;
            default:
                newPiece = new Piece(belongsToplayer1);
                break;
        }
        pieces.add(newPiece);
        tiles.get(location).setPiece(newPiece);
    }

    public Object[] executeCommand(List<String> items, int fuelChange, int lumberChange,
                                   int supplyChange, int fuelAvailable , int lumberAvailable,
                                   int piecesInSupply ) {
        int lumberCost;
        switch (items.get(0))
        {
            case "move":
                int fuelCost = executeMoveCommand(items, fuelAvailable);
                if (fuelCost < 0) {
                    return new Object[]{"That move can't be done", fuelChange, lumberChange, supplyChange};
                }
                fuelChange = -fuelCost;
                break;
            case "saw":
            case "dig":
                    Object[] returnObjects = executeCommandInTile(items, fuelChange, lumberChange);
                    boolean execute = (boolean)returnObjects[0];
                    fuelChange = (int)returnObjects[1];
                    lumberChange = (int)returnObjects[2];
                if (!execute) {
                    return new Object[] {"Couldn't do that", fuelChange, lumberChange, supplyChange};
                }
                break;
            case "spawn":
                lumberCost = executeSpawnCommand(items, lumberAvailable, piecesInSupply);
                if (lumberCost < 0) {
                    return new Object[] {"Spawning did not occur", fuelChange, lumberChange, supplyChange};
                }
                lumberChange = -lumberCost;
                supplyChange = 1;
                break;
            case "upgrade":
                lumberCost = executeUpgradeCommand(items, lumberAvailable);
                if (lumberCost < 0) {
                    return new Object[] {"Upgrade not possible", fuelChange, lumberChange, supplyChange};
                }
                lumberChange = -lumberCost;
                break;
        }
        return new Object[] {"Command executed", fuelChange, lumberChange, supplyChange};
    }

    private boolean checkTileIndexIsValid(int tileToCheck) {
        return tileToCheck >= 0 && tileToCheck < tiles.size();
    }

    private boolean checkPieceAndTileAreValid(int tileToUse) {
        if (checkTileIndexIsValid(tileToUse)) {
            Piece thePiece = tiles.get(tileToUse).getPieceInTile();
            if (thePiece != null) {
                if (thePiece.getBelongsToplayer1() == player1Turn) {
                    return true;
                }
            }
        }
        return false;
    }

    private Object[] executeCommandInTile(List<String> items, int fuel, int lumber) {
        int tileToUse = Integer.parseInt(items.get(1));
        if (checkPieceAndTileAreValid(tileToUse) == false) {
            return new Object[] {false, fuel, lumber};
        }
        Piece thePiece = tiles.get(tileToUse).getPieceInTile();
        if (thePiece.hasMethod(items.get(0))) {
            String methodToCall = items.get(0);
            Class t = thePiece.getClass();
            try {
                Method method = t.getMethod(methodToCall, String.class);
                Object parameters = tiles.get(tileToUse).getTerrain();
                if (items.get(0).equals("saw")) {
                    lumber += (int)method.invoke(thePiece, parameters);
                } else if (items.get(0).equals("dig")) {
                    fuel += (int)method.invoke(thePiece, parameters);
                    if (Math.abs(fuel) > 2) {
                    tiles.get(tileToUse).setTerrain(" ");
                    }
                }
                return new Object[] {true, fuel, lumber};
            } catch (Exception ex) {
                Console.writeLine(ex.getMessage());
            }
        }
        return new Object[] {false, fuel, lumber};
    }

    private int executeMoveCommand(List<String> items, int fuelAvailable) {
        int startID = Integer.parseInt(items.get(1));
        int endID = Integer.parseInt(items.get(2));
        if (!checkPieceAndTileAreValid(startID) || !checkTileIndexIsValid(endID)) {
            return -1;
        }
        Piece thePiece = tiles.get(startID).getPieceInTile();
        if (tiles.get(endID).getPieceInTile() != null) {
            return -1;
        }
        int distance = tiles.get(startID).getDistanceToTileT(tiles.get(endID));
        int fuelCost = thePiece.checkMoveIsValid(distance, tiles.get(startID).getTerrain(), tiles.get(endID).getTerrain());
        if (fuelCost == -1 || fuelAvailable < fuelCost) {
            return -1;
        }
        movePiece(endID, startID);
        return fuelCost;
    }

    private int executeSpawnCommand(List<String> items, int lumberAvailable, int piecesInSupply) {
        int tileToUse = Integer.parseInt(items.get(1));
        if (piecesInSupply < 1 || lumberAvailable < 3 || !checkTileIndexIsValid(tileToUse)) {
            return -1;
        }
        Piece thePiece = tiles.get(tileToUse).getPieceInTile();
        if (thePiece != null) {
            return -1;
        }
        boolean ownBaronIsNeighbour = false;
        List<Tile> listOfNeighbours = new ArrayList<>(tiles.get(tileToUse).getNeighbours());
        for (Tile n : listOfNeighbours) {
            thePiece = n.getPieceInTile();
            if (thePiece != null) {
                if (player1Turn && thePiece.getPieceType().equals("B") || !player1Turn && thePiece.getPieceType().equals("b")) {
                    ownBaronIsNeighbour = true;
                    break;
                }
            }
        }
        if (!ownBaronIsNeighbour) {
            return -1;
        }
        Piece newPiece = new Piece(player1Turn);
        pieces.add(newPiece);
        tiles.get(tileToUse).setPiece(newPiece);
        return 3;
    }

    private int executeUpgradeCommand(List<String> items, int lumberAvailable) {
        int tileToUse = Integer.parseInt(items.get(2));
        if (!checkPieceAndTileAreValid(tileToUse) || lumberAvailable < 5 || !(items.get(1).equals("pbds") || items.get(1).equals("less"))) {
            return -1;
        } else {
            Piece thePiece = tiles.get(tileToUse).getPieceInTile();
            if (!thePiece.getPieceType().toUpperCase().equals("S")) {
                return -1;
            }
            thePiece.destroyPiece();
            if (items.get(1).equals("pbds")) {
                thePiece = new PBDSPiece(player1Turn);
            } else {
                thePiece = new LESSPiece(player1Turn);
            }
            pieces.add(thePiece);
            tiles.get(tileToUse).setPiece(thePiece);
            return 5;
        }
    }

    private void setUpTiles() {
        int evenStartY = 0;
        int evenStartZ = 0;
        int oddStartZ = 0;
        int oddStartY = -1;
        int x, y, z;
        for (int count = 1; count <= size/2; count++) {
            y = evenStartY;
            z = evenStartZ;
            for (x = 0; x < size - 1; x+=2) {
                Tile tempTile = new Tile(x, y, z);
                tiles.add(tempTile);
                y -= 1;
                z -= 1;
            }
            evenStartZ += 1;
            evenStartY -= 1;
            y = oddStartY;
            z = oddStartZ;
            for (x = 1; x < size; x+=2) {
                Tile tempTile = new Tile(x, y, z);
                tiles.add(tempTile);
                y -= 1;
                z -= 1;
            }
            oddStartZ += 1;
            oddStartY -= 1;
        }
    }

    private void setUpNeighbours(){
        for (Tile fromTile : tiles) {
            for (Tile toTile : tiles) {
                if (fromTile.getDistanceToTileT(toTile) == 1) {
                    fromTile.addToNeighbours(toTile);
                }
            }
        }
    }

    public Object[] destroyPiecesAndCountVPs(int player1VPs, int player2VPs) {
        boolean baronDestroyed = false;
        List<Tile> listOfTilesContainingDestroyedPieces = new ArrayList<>();
        for (Tile t : tiles) {
            if (t.getPieceInTile() != null) {
                List<Tile> listOfNeighbours = new ArrayList<>(t.getNeighbours());
                int noOfConnections = 0;
                for (Tile n : listOfNeighbours) {
                    if (n.getPieceInTile() != null) {
                        noOfConnections += 1;
                    }
                }
                Piece thePiece = t.getPieceInTile();
                if (noOfConnections >= thePiece.getConnectionsNeededToDestroy()) {
                    thePiece.destroyPiece();
                    if (thePiece.getPieceType().toUpperCase().equals("B")) {
                        baronDestroyed = true;
                    }
                    listOfTilesContainingDestroyedPieces.add(t);
                    if (thePiece.getBelongsToplayer1()) {
                        player2VPs += thePiece.getVPs();
                    } else {
                        player1VPs += thePiece.getVPs();
                    }
                }
            }
        }
        for (Tile t : listOfTilesContainingDestroyedPieces) {
            t.setPiece(null);
        }
        return new Object[]{baronDestroyed, player1VPs, player2VPs};
    }

    public String getGridAsString(boolean P1Turn) {
        int listPositionOfTile = 0;
        player1Turn = P1Turn;
        Object[] returnObjects = createEvenLine(true, listPositionOfTile);
        String gridAsString = createTopLine() + returnObjects[0].toString();
        listPositionOfTile = (int)returnObjects[1];
        listPositionOfTile += 1;
        returnObjects = createOddLine(listPositionOfTile);
        gridAsString += returnObjects[0].toString();
        listPositionOfTile = (int)returnObjects[1];
        for (int count = 1; count < size - 1; count+=2) {
            listPositionOfTile += 1;
            returnObjects = createEvenLine(false, listPositionOfTile);
            gridAsString += returnObjects[0].toString();
            listPositionOfTile = (int)returnObjects[1];
            listPositionOfTile += 1;
            returnObjects = createOddLine(listPositionOfTile);
            gridAsString += returnObjects[0].toString();
            listPositionOfTile = (int)returnObjects[1];
        }
        return gridAsString + createBottomLine();
    }

    public String getGridAsIndices() {
        int listPositionOfTile = 0;
        int index = 0;
        Object[] returnObjects = createEvenIndicesLine(true, listPositionOfTile, index);
        String gridAsString = createTopLine() + returnObjects[0].toString();
        listPositionOfTile = (int)returnObjects[1];
        listPositionOfTile += 1;
        index = (int)returnObjects[2];
        returnObjects = createOddIndicesLine(listPositionOfTile, index);
        gridAsString += returnObjects[0].toString();
        listPositionOfTile = (int)returnObjects[1];
        index = (int)returnObjects[2];
        for (int count = 1; count < size - 1; count+=2) {
            listPositionOfTile += 1;
            returnObjects = createEvenIndicesLine(false, listPositionOfTile, index);
            gridAsString += returnObjects[0].toString();
            listPositionOfTile = (int)returnObjects[1];
            listPositionOfTile += 1;
            index = (int)returnObjects[2];
            returnObjects = createOddIndicesLine(listPositionOfTile, index);
            gridAsString += returnObjects[0].toString();
            listPositionOfTile = (int)returnObjects[1];
            index = (int)returnObjects[2];
        }
        return gridAsString + createBottomLine();
    }
        
    private void movePiece(int newIndex, int oldIndex) {
        tiles.get(newIndex).setPiece(tiles.get(oldIndex).getPieceInTile());
        tiles.get(oldIndex).setPiece(null);
    }

    public String getPieceTypeInTile(int id) {
        Piece thePiece = tiles.get(id).getPieceInTile();
        if (thePiece == null) {
            return " ";
        } else {
            return thePiece.getPieceType();
        }
    }

    private String createBottomLine() {
        String line = "   ";
        for (int count = 1; count <= size/2; count++) {
            line += " \\__/ ";
        }
        return line + "\n";
    }

    private String createTopLine() {
        String line = "\n  ";
        for (int count = 1; count <= size / 2; count++) {
            line += "__    ";
        }
        return line + "\n";
    }
    
    private String padIndex(int n) {
        if (n < 10) {
            return " " + Integer.toString(n);
        } else {
            return Integer.toString(n);
        }
    }
    
    private Object[] createOddIndicesLine(int listPositionOfTile, int index) {
        String line = "";
        for (int count = 1; count <= size / 2; count++) {
            if (count > 1 && count < size / 2) {
                line += "\\__/";
                listPositionOfTile += 1;
                line += padIndex(index);
                index += 1;
            } else if (count == 1) {
                line += " \\__/" + padIndex(index);
                index += 1;
            }
        }
        line += "\\__/";
        listPositionOfTile += 1;
        if (listPositionOfTile < tiles.size()) {
            line += padIndex(index) + "\\\n";
            index += 1;
        } else {
            line += "\\\n";
        }
        return new Object[]{line, listPositionOfTile, index};
    }

    private Object[] createEvenIndicesLine(boolean firstEvenLine, int listPositionOfTile, int index) {
        String line = " /" + padIndex(index);
        index += 1;
        for (int count = 1; count < size / 2; count++) {
            listPositionOfTile += 1;
            line += "\\__/" + padIndex(index);
            index += 1;
        }
        if (firstEvenLine) {
            line += "\\__\n";
        } else {
            line += "\\__/\n";
        }
        return new Object[]{line, listPositionOfTile, index};
    }
        
    private Object[] createOddLine(int listPositionOfTile) {
        String line = "";
        for (int count = 1; count <= size / 2; count++) {
            if (count > 1 && count < size / 2) {
                line += getPieceTypeInTile(listPositionOfTile) + "\\__/";
                listPositionOfTile += 1;
                line += tiles.get(listPositionOfTile).getTerrain();
            } else if (count == 1) {
                line += " \\__/" + tiles.get(listPositionOfTile).getTerrain();
            }
        }
        line += getPieceTypeInTile(listPositionOfTile) + "\\__/";
        listPositionOfTile += 1;
        if (listPositionOfTile < tiles.size()) {
            line += tiles.get(listPositionOfTile).getTerrain() + getPieceTypeInTile(listPositionOfTile) + "\\\n";
        } else {
            line += "\\\n";
        }
        return new Object[]{line, listPositionOfTile};
    }

    private Object[] createEvenLine(boolean firstEvenLine, int listPositionOfTile) {
        String line = " /" + tiles.get(listPositionOfTile).getTerrain();
        for (int count = 1; count < size / 2; count++) {
            line += getPieceTypeInTile(listPositionOfTile);
            listPositionOfTile += 1;
            line += "\\__/" + tiles.get(listPositionOfTile).getTerrain();
        }
        if (firstEvenLine) {
            line += getPieceTypeInTile(listPositionOfTile) + "\\__\n";
        } else {
            line += getPieceTypeInTile(listPositionOfTile) + "\\__/\n";
        }
        return new Object[]{line, listPositionOfTile};
    }
}

class Player {
    protected int piecesInSupply, fuel, vPs, lumber;
    protected String name;

    public Player() {}

    public void setUpPlayer(String n, int v, int f, int l, int t) {
        name = n;
        vPs = v;
        fuel = f;
        lumber = l;
        piecesInSupply = t;
    }

    public String getStateString() {
        return "VPs: " + vPs + "   Pieces in supply: " + piecesInSupply + "   Lumber: " + lumber + "   Fuel: " + fuel;
    }

    public int getVPs() {
        return vPs;
    }

    public int getFuel() {
        return fuel;
    }

    public int getLumber() {
        return lumber;
    }

    public String getName() {
        return name;
    }

    public void addToVPs(int n) {
        vPs += n;
    }

    public void updateFuel(int n) {
        fuel += n;
    }

    public void updateLumber(int n) {
        lumber += n;
    }

    public int getPiecesInSupply() {
        return piecesInSupply;
    }

    public void removeTileFromSupply() {
        piecesInSupply -= 1;
    }
}
