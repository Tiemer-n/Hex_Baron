/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hexbaron;

import java.util.Random;

/**
 *
 * @author ir191258
 */
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
        
        //If the value of the parameter distanceBetweenTiles is 1 and startTerrain is not a peat bog 
        //then it returns the value of the protected attribute fuelCostOfMove otherwise it returns -1.
        
        if (distanceBetweenTiles != 1 || startTerrain.equals("~")) {
            return -1;
        }
        return fuelCostOfMove;
    }

    public int dig(String terrain) {
        
        //If terrain is a peat bog then it has a 90% chance of returning 1 and a 10% chance of returning 5, otherwise it returns 0.
        
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
