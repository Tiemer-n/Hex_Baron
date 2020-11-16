/*
 * Skeleton Program code for the AQA A Level Paper 1 Summer 2021 examination.
 * this code should be used in conjunction with the Preliminary Material
 * written by the AQA Programmer Team
 * developed in the NetBeans IDE 8.1 environment
 */
package hexbaron;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HexBaron {

    public static HexGrid grid;
    public static HexGrid indexgrid;
  
    public HexBaron() {
        boolean fileLoaded;
        Player player1 = new Player();
        Player player2 = new Player();
        String choice = "";
        while (!choice.equals("Q")) {
            displayMainMenu();
            choice = Console.readLine();
            if (choice.equals("1")) {
                grid = setUpDefaultGame(player1, player2);
                indexgrid = setupIndexGrid();
                playGame(player1, player2, grid);
            } else if (choice.equals("2")) {
                Object[] returnObjects = loadGame(player1, player2);
                fileLoaded = (boolean) returnObjects[1];
                if (fileLoaded) {
                    grid = (HexGrid) returnObjects[0];
                    indexgrid = setupIndexGrid();
                    playGame(player1, player2, grid);
                }
            } else if (choice.equals("3")){
                printInstructions();
                
            }
        }
    }

    Object[] loadGame(Player player1, Player player2) {
        Console.write("Enter the name of the file to load: ");
        String fileName = Console.readLine();
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
            List<String> t = Arrays.asList(in.readLine().split(","));
            grid.setUpGridTerrain(t);
            lineFromFile = in.readLine();
            while (lineFromFile != null) {
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

    HexGrid setupIndexGrid(){
        List<String> t = new ArrayList<>();
        for (int i = 0; i <= 31; i++) {
            t.add(String.valueOf(i));
        }
        int gridsize = 8;
        HexGrid indexgrid = new HexGrid(gridsize);
        indexgrid.setUpGridTerrain(t);
        return indexgrid;
        
    }
    
    HexGrid setUpDefaultGame(Player player1, Player player2) {
        List<String> t = Arrays.asList(new String[]{" ", " ", "#", " ", "~", "~", " ", " ", " ", "~", " ", "#", "#", " ", " ", " ",
            " ", " ", "#", "#", "#", "#", "~", "~", "~", "~", "~", " ", "#", " ", "~", " "});
        int gridSize = 8;
        HexGrid grid = new HexGrid(gridSize);
        player1.setUpPlayer("Player One", 0, 10, 10, 5);
        player2.setUpPlayer("Player Two", 0, 10, 10, 5);
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
    
    
    boolean checkDigCommandFormat(List<String> items){
        
        
        
        
        
        return true;
    }
    
    
    boolean checkSawCommandFormat (List<String> items){
        
        
        
        
        
        
        
        return true;
    }
    boolean checkCommandIsValid(List<String> items) {
        if (items.size() > 0) {
            switch (items.get(0)) {
                case "move":
                    
                    return checkMoveCommandFormat(items);
                case "dig":
                    
                    return checkDigCommandFormat(items);
                case "saw":
                    
                    return checkSawCommandFormat(items);
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
        Console.writeLine("Player Two current state - " + player2.getStateString()+"\n");
        do {
            printIndex();
            Console.writeLine(grid.getGridAsString(player1Turn));
            
            if (player1Turn) {
                Console.writeLine(player1.getName() + " state your three commands, pressing enter after each one.");
            } else {
                Console.writeLine(player2.getName() + " state your three commands, pressing enter after each one.");
            }
            for (int count = 1; count <= 3; count++) {
                Console.write("Enter command number "+count+": ");
                commands.add(Console.readLine().toLowerCase());
            }
            for (String c : commands) {
                List<String> items = Arrays.asList(c.split(" "));
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
                        fuelChange = (int) returnObjects[1];
                        lumberChange = (int) returnObjects[2];
                        supplyChange = (int) returnObjects[3];
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
                        fuelChange = (int) returnObjects[1];
                        lumberChange = (int) returnObjects[2];
                        supplyChange = (int) returnObjects[3];
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
            int player2VPsGained = 0;
            Object[] returnObjects;
            if (gameOver) {
                returnObjects = grid.destroyPiecesAndCountVPs(player1VPsGained, player2VPsGained);
            } else {
                returnObjects = grid.destroyPiecesAndCountVPs(player1VPsGained, player2VPsGained);
                gameOver = (boolean) returnObjects[0];
            }
            player1VPsGained = (int) returnObjects[1];
            player2VPsGained = (int) returnObjects[2];
            player1.addToVPs(player1VPsGained);
            player2.addToVPs(player2VPsGained);
            Console.writeLine("Player One current state - " + player1.getStateString());
            Console.writeLine("Player Two current state - " + player2.getStateString());
            Console.write("Press Enter to continue...");
            Console.readLine();
        } while (!gameOver || !player1Turn);
        Console.writeLine(grid.getGridAsString(player1Turn));
        printIndex();
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
        Console.writeLine("3. Instructions");
        Console.writeLine("Q. Quit");
        Console.writeLine();
        Console.write("Enter your choice: ");
    }
    
    
    void printInstructions(){
        for (int i = 0; i < 50; i++) {
            System.out.println("");
        }
        System.out.println("INSTRUCTIONS:");
        System.out.println("There are two players able to play this game");
        System.out.println("Player One: all Capital pieces");
        System.out.println("Player Two: all lowercase pieces" + "\n");
        System.out.println("Commands:");
        System.out.println("move [starting place] [destination] e.g. [move 8 12] will move piece at index 8 to index 12 as long as its an adjecent tile"+"\n");
        System.out.println("upgrade [upgrade type] [tile with piece on it]    e.g. [upgrade pbds 30 ] will upgrade piece at tile 30 to a pbds piece "+"\n");
        System.out.println("saw [tile with LESS piece in it] e.g. [saw 9] will make piece at tile 9 give one piece of wood as long as its a less piece and its in a forst (#)"+"\n");
        System.out.println("dig [tile with PBDS piece in it] e.g. [dig 11] will make piece at tile 11 give one fule as long as its a pbds and its in a peat bog (~)"+"\n");
        System.out.println("spawn [tile where it will spawn] e.g. [spawn 8] will make the baron spawn a serf in tile 8 ");
        System.out.println("(only barons can spawn pieces onto the board)"+"\n");
        System.out.println("");
        System.out.println("Destroying pieces:");
        System.out.println("surround an opposing piece with two of your own pieces to destroy it and gain points");
        System.out.println("");
        System.out.println("Win Condition:");
        System.out.println("try and make two of your pieces surround the opposing players baron to kill it and win the game");
        for (int i = 0; i < 5; i++) {
            System.out.println("");
        }
    }
    
    void printIndex(){
        
        System.out.println("(index grid)");
        System.out.println("  __    __    __    __    \n" +
                        " /0 \\__/1 \\__/2 \\__/3 \\__\n" +
                        " \\__/4 \\__/5 \\__/6 \\__/7 \\\n" +
                        " /8 \\__/9 \\__/10\\__/11\\__/\n" +
                        " \\__/12\\__/13\\__/14\\__/15\\\n" +
                        " /16\\__/17\\__/18\\__/19\\__/\n" +
                        " \\__/20\\__/21\\__/22\\__/23\\\n" +
                        " /24\\__/25\\__/26\\__/27\\__/\n" +
                        " \\__/28\\__/29\\__/30\\__/31\\\n" +
                        "    \\__/  \\__/  \\__/  \\__/ ");
        
        System.out.println("Commands:");
        System.out.println("move [starting place] [destination] e.g. [move 8 12] will move piece at index 8 to index 12 as long as its an adjecent tile"+"\n");
        System.out.println("upgrade [upgrade type] [tile with piece on it]   e.g. [upgrade pbds 30 ] will upgrade piece at tile 30 to a pbds piece "+"\n");
        System.out.println("saw [tile with LESS piece in it] e.g. [saw 9] will make piece at tile 9 give one piece of wood as long as its a less piece and its in a forst (#)"+"\n");
        System.out.println("dig [tile with PBDS piece in it] e.g. [dig 11] will make piece at tile 11 give one fule as long as its a pbds and its in a peat bog (~)"+"\n");
        System.out.println("spawn [tile where it will spawn] e.g. [spawn 1 8] will make the baron spawn a serf in tile 8 ");
        System.out.println("(only barons can spawn pieces onto the board)"+"\n");
    }
    
    

    public static void main(String[] args) {
        new HexBaron();
    }
}









