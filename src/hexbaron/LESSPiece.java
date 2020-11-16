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
