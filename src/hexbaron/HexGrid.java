/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hexbaron;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ir191258
 */
public class HexGrid {

    protected List<Tile> tiles = new ArrayList<>();
    protected List<Piece> pieces = new ArrayList<>();
    protected int size;
    protected boolean player1Turn;

    public HexGrid(int n) {
        
//        Initialises the following protected attributes:
//
//        size from parameter n
//        player1Turn to true
//        It calls the private methods setUpTiles and setUpNeighbours.
        
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
        
//      Calls the appropriate constructor to create a new piece of the correct type according to typeOfPiece 
//      and passes belongsToPlayer1 as an argument to the constructor.
//
//      Once created, it appends the piece to the protected attribute pieces and then adds the piece to the tile 
//      using location as an index into the tiles list and calling the setPiece method on the tile, passing the newly created piece as an argument.

        Piece newPiece = null;
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
            int supplyChange, int fuelAvailable, int lumberAvailable,
            int piecesInSupply) {
        
        //Depending on the first element of items, it either calls executeMoveCommand, executeSpawnCommand,
        //executeUpgradeCommand or executeCommandInTile.

        //Each of these private methods’ return values is used to create a suitable status message and determine 
        //the figures for the return values of fuelChange, lumberChange and supplyChange (which were initialised to 0).
        
        
        int lumberCost;
        switch (items.get(0)) {
            case "move":
                int fuelCost = executeMoveCommand(items, fuelAvailable);
                if (fuelCost < 0) {
                    return new Object[]{"That move can't be done", fuelChange, lumberChange, supplyChange};
                }
                fuelChange = -fuelCost;
                break;
            case "saw":
                
                //WTF HOW theres nothing here 
                
            case "dig":
                Object[] returnObjects = executeCommandInTile(items, fuelChange, lumberChange);
                boolean execute = (boolean) returnObjects[0];
                fuelChange = (int) returnObjects[1];
                lumberChange = (int) returnObjects[2];
                if (!execute) {
                    return new Object[]{"Couldn't do that", fuelChange, lumberChange, supplyChange};
                }
                break;
            case "spawn":
                lumberCost = executeSpawnCommand(items, lumberAvailable, piecesInSupply);
                if (lumberCost < 0) {
                    return new Object[]{"Spawning did not occur", fuelChange, lumberChange, supplyChange};
                }
                lumberChange = -lumberCost;
                supplyChange = 1;
                break;
            case "upgrade":
                lumberCost = executeUpgradeCommand(items, lumberAvailable);
                if (lumberCost < 0) {
                    return new Object[]{"Upgrade not possible", fuelChange, lumberChange, supplyChange};
                }
                lumberChange = -lumberCost;
                break;
                
            //task 8 
            case "downgrade":    
                lumberCost = executeDowngradeCommand(items, lumberAvailable);
                if(lumberCost < 0){
                    return new Object[]{"Downgrade not possible", fuelChange, lumberChange, supplyChange};
                }
                lumberChange = lumberCost;
                break;
            //8888888888888888888888888888888888888888888888888888888
                
            //task 10 making a new salvage command 
            case "salvage":
                lumberCost =  executeSalvageCommand(items);
                
                if(lumberCost < 0 ){
                    
                    return new Object[]{"Salvage not possible", fuelChange, lumberChange, supplyChange};
                    
                }
                supplyChange = -1;
                lumberChange = lumberCost;
                break;
            //1010101010101010101010101010101010101010101010101010101010101010101010
        }
        return new Object[]{"Command executed", fuelChange, lumberChange, supplyChange};
    }

    private boolean checkTileIndexIsValid(int tileToCheck) {
        
       //Returns true if the parameter tileToCheck is a valid index of the protected attribute tiles.
        
        return tileToCheck >= 0 && tileToCheck < tiles.size();
    }

    private boolean checkPieceAndTileAreValid(int tileToUse) {
        
        //Returns true if there is a piece belonging to the current player in tileToUse, otherwise it returns false.
        
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
    
    //task 10 making new salvage command
    private int executeSalvageCommand(List<String> items){
        
        int tileToUse = 0;
        
        try{
            tileToUse = Integer.parseInt(items.get(1));
        }catch (Exception e){
            return -1;
        }
        
        if (!checkPieceAndTileAreValid(tileToUse) || 
                (tiles.get(tileToUse).getPieceInTile().pieceType.equals("B"))) {
            return -1;
        }else{
            Piece thePiece = tiles.get(tileToUse).getPieceInTile();
            
            if (thePiece.getPieceType().toUpperCase().equals("B") ||
                    thePiece.belongsToplayer1 != player1Turn){
                return -1;
            }
            
            thePiece.destroyPiece();
            pieces.remove(thePiece);
            tiles.get(tileToUse).setPiece(null);
            return 5;
        }
        
    }
    //1010101010101010101010101010101010101010101010101010101010101010101010
    
    
    
    //task 8 making a new command 'downgrade'
    
    private int executeDowngradeCommand(List<String> items, int lumberAvailable){
        
        int tileToUse = 0;
        
        try{
            tileToUse = Integer.parseInt(items.get(1));
        }catch (Exception e){
            return -1;
        }
        
        if (!checkPieceAndTileAreValid(tileToUse) || lumberAvailable < 1 
                || (tiles.get(tileToUse).getPieceInTile().pieceType.equals("S"))) {
            return -1;
        }else{
            Piece thePiece = tiles.get(tileToUse).getPieceInTile();
            
            if (!thePiece.getPieceType().toUpperCase().equals("P") && 
                    !thePiece.getPieceType().toUpperCase().equals("L")) {
                return -1;
            }
            
            thePiece.destroyPiece();
            thePiece = new Piece(player1Turn);
            pieces.add(thePiece);
            tiles.get(tileToUse).setPiece(thePiece);
            return 1;
            
        }
    }
    
    //888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888
    

    private Object[] executeCommandInTile(List<String> items, int fuel, int lumber) {
        
        //Checks whether there is a piece belonging to the player in the tile specified as the second string in items,
        //if not then false, 0 and 0 are returned.

        //It then uses hasMethod to determine whether the piece in the tile has a saw or dig command as specified by the 
        //first string in the items list and calls that method. If it was a dig and more than 5 fuel was returned then it 
        //sets the terrain of the tile to a field using the setTerrain method.

        //The method then returns true and the fuel or lumber gained from digging or sawing or it returns false and 0,0 
        //if the command could not be executed.
        
        
        int tileToUse = 0;
        try{
            tileToUse = Integer.parseInt(items.get(1));
        }catch (Exception e){
            return new Object[]{false, fuel, lumber};
        }
        
        
        
        if (checkPieceAndTileAreValid(tileToUse) == false) {
            return new Object[]{false, fuel, lumber};
        }
        Piece thePiece = tiles.get(tileToUse).getPieceInTile();
        if (thePiece.hasMethod(items.get(0))) {
            String methodToCall = items.get(0);
            Class t = thePiece.getClass();
            try {
                Method method = t.getMethod(methodToCall, String.class);
                Object parameters = tiles.get(tileToUse).getTerrain();
                if (items.get(0).equals("saw")) {
                    lumber += (int) method.invoke(thePiece, parameters);
                    
                } else if (items.get(0).equals("dig")) {
                    fuel += (int) method.invoke(thePiece, parameters);
                    
                }
                return new Object[]{true, fuel, lumber};
            } catch (Exception ex) {
                Console.writeLine(ex.getMessage());
            }
        }
        return new Object[]{false, fuel, lumber};
    }

    private int executeMoveCommand(List<String> items, int fuelAvailable) {
        
        //Checks whether there is a piece belonging to the player in the tile specified 
        //as the second string in items and that the tile specified in the third string of items is empty,
        //if not then -1 is returned straight away.

        //Otherwise, it then checks that the move is allowed by calling the checkMoveIsValid method on the piece 
        //in the first tile and if there is enough fuel available and the move was valid then it calls 
        //movePiece to execute the move and returns fuelCost (normally 1 or 2), otherwise it returns -1.
        
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
//Checks to see whether the player has at least 1 pieceInSupply and 3 lumber and that the tile specified 
        //as the second string in the items list is empty, otherwise it returns -1.

        //The method then checks to see that the player’s own Baron is a neighbour and then spawns a new Serf in the tile,
        //appends it to the attribute pieces and returns 3, otherwise it returns -1.
    
    
    private int executeSpawnCommand(List<String> items, int lumberAvailable, int piecesInSupply) {
        
        int tileToUse;
        
        if(items.size() == 3){
            tileToUse = Integer.parseInt(items.get(2));
            
            if (piecesInSupply < 1 || lumberAvailable < 10 || !checkTileIndexIsValid(tileToUse)) {
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

            //Task 4 making sure the player can only have 6 pieces on the board at a time
            int countp1 = 0;
            int countp2 = 0;


            for (int i = 0; i < pieces.size(); i++) {

                Piece PieceTest = pieces.get(i);
                if (PieceTest != null) {
                    if (PieceTest.belongsToplayer1) {
                        countp1++;
                    } else if (!PieceTest.belongsToplayer1) {
                        countp2++;
                    }
                }
            }

            if ((player1Turn && countp1 == 6) || (!player1Turn && countp2 == 6)) {
                System.out.println("Spawn attempted to acceed max pieces ");

                return -1;
            }

            if (!ownBaronIsNeighbour) {
                return -1;
            }
            
            Piece newPiece = new BOMBPiece(player1Turn);
            pieces.add(newPiece);

            tiles.get(tileToUse).setPiece(newPiece);
            return 10;
            
        }else{
            tileToUse = Integer.parseInt(items.get(1));
            
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

            //Task 4 making sure the player can only have 6 pieces on the board at a time
            int countp1 = 0;
            int countp2 = 0;


            for (int i = 0; i < pieces.size(); i++) {

                Piece PieceTest = pieces.get(i);
                if (PieceTest != null) {
                    if (PieceTest.belongsToplayer1) {
                        countp1++;
                    } else if (!PieceTest.belongsToplayer1) {
                        countp2++;
                    }
                }
            }

            if ((player1Turn && countp1 == 6) || (!player1Turn && countp2 == 6)) {
                System.out.println("Spawn attempted to acceed max pieces ");

                return -1;
            }

            if (!ownBaronIsNeighbour) {
                return -1;
            }
            Piece newPiece = new Piece(player1Turn);
            pieces.add(newPiece);

            tiles.get(tileToUse).setPiece(newPiece);
            return 3;
        }
        
    }

    private int executeUpgradeCommand(List<String> items, int lumberAvailable) {
        
        //If the tile specified as the third item of the items list is available and contains a 
        //Serf belonging to the player whose turn it is and that player has at least 5 lumber available then 
        //it is upgraded to a LESSPiece or PBDSPiece according to the second string in Items and 5 is returned as the cost,
        //otherwise -1 is returned.
        int tileToUse = 0;
        try{
            tileToUse = Integer.parseInt(items.get(2));
        }catch (Exception e){
            return -1;
        }
        
        
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
        
        //Loops through and creates all of the tiles needed for the grid according to the 
        //protected attribute size and adds them to the protected attribute tiles.
        
        int evenStartY = 0;
        int evenStartZ = 0;
        int oddStartZ = 0;
        int oddStartY = -1;
        int x, y, z;
        for (int count = 1; count <= size / 2; count++) {
            y = evenStartY;
            z = evenStartZ;
            for (x = 0; x < size - 1; x += 2) {
                Tile tempTile = new Tile(x, y, z);
                tiles.add(tempTile);
                y -= 1;
                z -= 1;
            }
            evenStartZ += 1;
            evenStartY -= 1;
            y = oddStartY;
            z = oddStartZ;
            for (x = 1; x < size; x += 2) {
                Tile tempTile = new Tile(x, y, z);
                tiles.add(tempTile);
                y -= 1;
                z -= 1;
            }
            oddStartZ += 1;
            oddStartY -= 1;
        }
    }

    private void setUpNeighbours() {
        
        //For each tile on the grid, it loops through all of the tiles on the grid and adds any tile that is a distance of 1 away 
        //(as determined by the getDistanceToTileT method in the Tile class) to the list of neighbours 
        //by calling the addToNeighbours method on the tile and passing an argument of the tile that is 1 away.
        
        for (Tile fromTile : tiles) {
            for (Tile toTile : tiles) {
                if (fromTile.getDistanceToTileT(toTile) == 1) {
                    fromTile.addToNeighbours(toTile);
                }
            }
        }
    }

    public Object[] destroyPiecesAndCountVPs(int player1VPs, int player2VPs, Player player1, Player player2) {
        
//      Loops through every tile in the grid and checks for any pieces that need to be destroyed 
//      by counting the number of connections for each piece and comparing it to the number of connections needed to destroy the piece in question.
//
//      For each piece that is destroyed, track the VPs for the relevant player and also whether their Baron was destroyed or not.
//
//      Finally, it then removes any pieces from the grid that were destroyed.
        
        
        boolean baronDestroyed = false;
        List<Tile> listOfTilesContainingDestroyedPieces = new ArrayList<>();
        for (Tile t : tiles) {
            if (t.getPieceInTile() != null) {
                List<Tile> listOfNeighbours = new ArrayList<>(t.getNeighbours());
                int noOfConnections = 0;
                for (Tile n : listOfNeighbours) {
                    
                    //i changed this so that your own pieces cannot destroy themselves because thats stupid and i dont like that ew
                    
                    if (n.getPieceInTile() != null && n.pieceInTile.belongsToplayer1 && !t.pieceInTile.belongsToplayer1) {
                        noOfConnections ++;
                    }else if (n.getPieceInTile() != null &&  !n.pieceInTile.belongsToplayer1 && t.pieceInTile.belongsToplayer1){
                        noOfConnections ++;
                    }
                    
                    //---------------------------------------------------------------------------
                }
                Piece thePiece = t.getPieceInTile();
                if (noOfConnections >= thePiece.getConnectionsNeededToDestroy()) {
                    thePiece.destroyPiece();
                    
                    if (thePiece.getPieceType().toUpperCase().equals("B")) {
                        baronDestroyed = true;
                    }
                    listOfTilesContainingDestroyedPieces.add(t);
                    if (thePiece.getBelongsToplayer1()) {
                        player1.addTileToSupply(1);
                        player2VPs += thePiece.getVPs();
                    } else {
                        player2.addTileToSupply(1);
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
    
    private void movePiece(int newIndex, int oldIndex) {
            tiles.get(newIndex).setPiece(tiles.get(oldIndex).getPieceInTile());
            tiles.get(oldIndex).setPiece(null);
        }

    public String getPieceTypeInTile(int id) {

        //If there is no piece in the tile specified by id then this returns “ ” 
        //(string with a single space), otherwise it returns the result of the getPieceType method which is called on the piece in the tile.

        Piece thePiece = tiles.get(id).getPieceInTile();
        if (thePiece == null) {
            return " ";
        } else {
            return thePiece.getPieceType();
        }
    }
    public String getGridAsString(boolean P1Turn) {
        
        //Uses the private attribute listPositionOfTile to loop through the tiles in the grid 
        //calling the private methods createTopLine, createOddLine, createEvenLine and createBottomLine
        //as needed to form a string containing the entire grid.

        int listPositionOfTile = 0;
        player1Turn = P1Turn;
        Object[] returnObjects = createEvenLine(true, listPositionOfTile);
        String gridAsString = createTopLine() + returnObjects[0].toString();
        listPositionOfTile = (int) returnObjects[1];
        listPositionOfTile += 1;
        returnObjects = createOddLine(listPositionOfTile);
        gridAsString += returnObjects[0].toString();
        listPositionOfTile = (int) returnObjects[1];
        for (int count = 1; count < size -1; count +=2) {
            listPositionOfTile += 1;
            returnObjects = createEvenLine(false, listPositionOfTile);
            gridAsString += returnObjects[0].toString();
            listPositionOfTile = (int) returnObjects[1];
            listPositionOfTile ++;
            returnObjects = createOddLine(listPositionOfTile);
            gridAsString += returnObjects[0].toString();
            listPositionOfTile = (int) returnObjects[1];
        }
        return gridAsString + createBottomLine();
    }

    private String createBottomLine() {
        String line = "   ";
        for (int count = 1; count <= size / 2; count++) {
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
    
    
    //task 5 printing the grid with the indexes showing where you can move to -------------------------
    
    int gridIndex = 0;
    
    public String getGridAsIndicies(){
        
        
        int listPositionOfTile = 0;
        Object[] returnObjects = createEvenLineHex(true, listPositionOfTile);
        String gridAsString = createTopLine() + returnObjects[0].toString();
        listPositionOfTile = (int) returnObjects[1];
        listPositionOfTile += 1;
        returnObjects = createOddLineHex(listPositionOfTile);
        gridAsString += returnObjects[0].toString();
        listPositionOfTile = (int) returnObjects[1];
        for (int count = 1; count < size -1; count +=2) {
            listPositionOfTile += 1;
            returnObjects = createEvenLineHex(false, listPositionOfTile);
            gridAsString += returnObjects[0].toString();
            listPositionOfTile = (int) returnObjects[1];
            listPositionOfTile ++;
            returnObjects = createOddLineHex(listPositionOfTile);
            gridAsString += returnObjects[0].toString();
            listPositionOfTile = (int) returnObjects[1];
        }
        gridIndex = 0;
        return gridAsString + createBottomLine();
        
        
        
        
    }
    
    private Object[] createEvenLineHex(boolean firstEvenLine, int listPositionOfTile) {
        String line = " /" + gridIndex;
        gridIndex++;
        for (int count = 1; count < size / 2; count++) {
            
            String add = "";
            if(!gridIndexTooBig()){
                add = " ";
            }
            
            line += add;
            listPositionOfTile += 1;
            line += "\\__/" + gridIndex;gridIndex++;
        }
        if (firstEvenLine) {
            
            String add = "";
            if(!gridIndexTooBig()){
                add = " ";
            }
            
            line += add + "\\__\n";
        } else {
            
            
            String add = "";
            if(!gridIndexTooBig()){
                add = " ";
            }
            
            line += add + "\\__/\n";
        }
        return new Object[]{line, listPositionOfTile};
    }
    
    private Object[] createOddLineHex(int listPositionOfTile) {
        String line = "";
        
        for (int count = 1; count <= size / 2; count++) {
            if (count > 1 && count < size / 2) {
                
                String add = "";
                if(!gridIndexTooBig()){
                    add = " ";
                }
                
                line += add + "\\__/";
                
                
                
                
                listPositionOfTile += 1;
                line += gridIndex;gridIndex++;
            } else if (count == 1) {
                line += " \\__/" + gridIndex;gridIndex++;
            }
        }
        
        String add = "";
        if(!gridIndexTooBig()){
            add = " ";
        }
        
        line += add + "\\__/";
        
        listPositionOfTile += 1;
        if (listPositionOfTile < tiles.size()) {
            
            add = "";
            if(!gridIndexTooBig()){
                add = " ";
            }
            
            line += gridIndex + add + "\\\n";
            gridIndex++;
        } else {
            line += "\\\n";
        }
        return new Object[]{line, listPositionOfTile};
    }
    
    
    private boolean gridIndexTooBig (){
        
        if(gridIndex > 10){
            return true;
        }
        return false;
        
    }
    
    //-------------------------------------------------------------------------------------------------------------
    
    //task 7 makeField mothod which has the index of the tile needing to be made into a field tile
    
    public void makeField(List<String> items){
        int tileToUse = Integer.parseInt(items.get(1));
        tiles.get(tileToUse).setTerrain(" ");
    }
    
    //77777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777
    
    
}
