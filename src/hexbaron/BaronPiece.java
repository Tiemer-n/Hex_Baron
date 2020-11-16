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
