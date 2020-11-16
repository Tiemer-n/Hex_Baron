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
