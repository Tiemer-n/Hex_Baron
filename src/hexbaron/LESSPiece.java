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
class LESSPiece extends Piece {

    public LESSPiece(boolean player1) {
        super(player1);
        pieceType = "L";
        vPValue = 3;
    }

    @Override
    public int checkMoveIsValid(int distanceBetweenTiles, String startTerrain, String endTerrain) {
        
        //If the value of the parameter distanceBetweenTiles is 1 and startTerrain is not a forest 
        //then if the move begins or ends in a peat bog it returns fuelCostOfMove x2 and if the move 
        //doesn’t begin or end in a peat bog then it returns fuelCostOfMove otherwise it returns -1.
        
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
