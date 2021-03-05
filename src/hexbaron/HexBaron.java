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
        
        //Calls displayMainMenu and processes the result to either quit, load a game by
        //calling loadGame or play the default game by calling setUpDefaultGame and then calling 
        //playGame to play the game.
        
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
        
        //Asks for the name of a .csv text file in the format documented by AQA which will load a 
        //saved game into memory and then allow it to be played by setting the Player objects and returning 
        //the HexGrid object, which can be passed into playGame.
        
        
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
        
        //Initialises the game with the default values and board size as determined by AQA.
        
        List<String> t = Arrays.asList(new String[]{" ", " ", "#", " ", "~", "~", " ", " ", " ", "~", " ", "#", "#", " ", " ", " ",
            " ", " ", "#", "#", "#", "#", "~", "~", "~", "~", "~", " ", "#", " ", "#", " "});
        int gridSize = 8;
        HexGrid grid = new HexGrid(gridSize);
        
        //task 2 making it so that you input your own names for player 1 and 2
        
        System.out.println("please enter player 1's name: ");
        String player1name = Console.readLine();
        System.out.println("please enter player 2's name: ");
        String player2name = Console.readLine();
        
        
        player1.setUpPlayer(player1name, 0, 10, 10, 5);
        player2.setUpPlayer(player2name, 0, 10, 10, 5);
        
        // ---------------------------------------------------
        
        System.out.println("seofijsefoijsdroioasidjf");
        
        grid.setUpGridTerrain(t);
        
        //normal grid set up:
        
//        grid.addPiece(true, "Baron", 0);
//        grid.addPiece(true, "Serf", 8);
//        grid.addPiece(false, "Baron", 31);
//        grid.addPiece(false, "Serf", 23);
        

        grid.addPiece(true, "Baron", 0);
        grid.addPiece(true, "Serf", 15);
        grid.addPiece(true, "Serf", 27);
        grid.addPiece(true, "Serf", 25);
        grid.addPiece(false, "Baron", 19);
        grid.addPiece(false, "Serf", 8);
        grid.addPiece(false, "Serf", 2);
       
        return grid;
    }

    boolean checkMoveCommandFormat(List<String> items) {
        //Returns true if the following conditions are met:
        //1) There are three elements in the items list
        //2) The second and third items are strings containing integers
        //Otherwise it returns false.
        
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
//        Returns true if the following conditions are met:
//
//        1) There are two elements in the items list
//
//        2) The second item is a string containing an integer
//
//        Otherwise it returns false.
        
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
        
//        Returns true if the following conditions are met:
//
//        1) There are three elements in the items list
//
//        2) The third item is a string containing an integer
//
//        3) The second item is either less or pbds (any case)
//
//        Otherwise it returns false.
        
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
    
    //task 8 
    
    boolean checkDowngradeCommandFormat (List<String> items){
        
        if(items.size() == 2){
            
            try {
                int result = Integer.parseInt(items.get(1));
                return true;
            } catch (Exception e) {
                return false;
            }    
        }
        
        return false;
    }
    
    //888888888888888888888888888888888888888888888888888888888888888
    boolean checkCommandIsValid(List<String> items) {
        //depending on the first string in the list items, this called one of the other check
        //commandformat subrountines
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
                    
                //task 8 making downgrade command
                case "downgrade":
                    return checkDowngradeCommandFormat(items);
                //88888888888888888888888888888888888888888888888888
            }
        }
        return false;
    }

    void playGame(Player player1, Player player2, HexGrid grid) {
        
//        This alternates between Player 1 and Player 2 and always ensures that Player 2 has an equal 
//        number of turns even if Player 1 has just killed their Baron.
//
//        For each player’s turn, the board is printed out and then three commands are read in and validated 
//        (by calling checkCommandIsValid) and then executed (by calling executeCommand on the HexGrid object for the game – stored in the variable grid).
//
//        After each command, the updateLumber, updateFuel and removeTileFromSupply (if necessary) methods are 
//        called on the Player object for that player’s turn.
//
//        Once the commands have been executed, pieces are destroyed, VPs are assigned and a check is made to see whether 
//        the game is over. Before the game moves on to the next player’s turn, the status of both players (resources and VPs) is displayed.
//
//        If the game is over and Player 2 has played, then displayEndMessages is called to display the final scores and the winner.


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
                
                //task 5 implementing the hexes commands giving the player an index grid

                String stringCommand = Console.readLine();
                strip(stringCommand);
                if(stringCommand.equals("hexes")){   
                    System.out.println(grid.getGridAsIndicies());
                    count--;
                }else{
                    commands.add(strip(stringCommand.toLowerCase()));
                }
                
                //--------------------------------------------------------------
            }
            
            //task 6 checking if all the commands are move commands and adding +1 fuel to the user if they are 
            
            boolean addFuel = true;
            for (int i = 0; i < 3; i++) {
                if(!commands.get(i).contains("move")){
                    addFuel = false;
                }
            }
            
            if(player1Turn && addFuel){
                player1.updateFuel(1);
            }else if(!player1Turn && addFuel){
                player2.updateFuel(1);
            }
            
            //6666666666666666666666666666666666666666666666666666666666666666666666666

            
            int countConsecutive = 0;
            
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
            
            //task 7 checking that 3 saw or dig commands were inputted 
            
            int valid = 0;
            for (String c : commands) {
                List<String> items = Arrays.asList(c.split(" "));
                validCommand = checkCommandIsValid(items);
                
                if(validCommand){
                    valid ++;
                }
                
                if(valid == 3){
                   boolean Consecutive = false;

                    if (commands.get(0).equals(commands.get(1)) && commands.get(0).equals(commands.get(2) )) {
                        Consecutive = true;
                    }

                    if(Consecutive){

                        
                        if (player1Turn && commands.get(0).contains("dig")) {
                            player1.updateFuel(2);
                            grid.makeField(items);
                        } else if (!player1Turn && commands.get(0).contains("dig")) {
                            player2.updateFuel(2);
                                  grid.makeField(items);
                        } else if (player1Turn && commands.get(0).contains("saw")) {
                            player1.updateLumber(2);
                                  grid.makeField(items);
                        } else if (!player1Turn && commands.get(0).contains("saw")) {
                            player2.updateLumber(2);
                                  grid.makeField(items);
                        }
                        
                        
                        
                        
                    }  
                }
            }
            
            
            
            
           
            //77777777777777777777777777777777777777777777777777777777777777777777777
            
            
            
            
            commands.clear();
            player1Turn = !player1Turn;
            int player1VPsGained = 0;
            int player2VPsGained = 0;
            Object[] returnObjects;
            if (gameOver) {
                returnObjects = grid.destroyPiecesAndCountVPs(player1VPsGained, player2VPsGained, player1 , player2);
            } else {
                returnObjects = grid.destroyPiecesAndCountVPs(player1VPsGained, player2VPsGained, player1 , player2);
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

    public String strip (String command){
        
        //task 1 i think i chessed this one oops
        //getting rid of all of the spaces before and after the command
        
        String newS = command.trim();
        
        
        return newS;
    }    
    
    void displayEndMessages(Player player1, Player player2) {
        
//        Displays the following for both players:
//
//        1) Their remaining resources
//
//        2) Their VPs
//
//        Then, displays the winner.
        
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
        
        //prints out the main menu
        
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









