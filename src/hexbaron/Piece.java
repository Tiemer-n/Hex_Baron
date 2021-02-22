/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hexbaron;

/**
 *
 * @author ir191258
 */
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
        
        //If the value of the parameter distanceBetweenTiles is 1 then if the move begins 
        //or ends in a peat bog it returns fuelCostOfMove ×2 and if the move doesn’t begin or 
        //end in a peat bog then it returns fuelCostOfMove otherwise it returns -1.
        
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
        
        //If the attribute belongsToPlayer1 is true then it returns the value of the
        //attribute pieceType otherwise it returns the lower case value.
        
        if (belongsToplayer1) {
            return pieceType;
        } else {
            return pieceType.toLowerCase();
        }
    }

    public void destroyPiece() {
        
        //Sets the value of the protected attribute destroyed to true.
        
        destroyed = true;
    }
}
