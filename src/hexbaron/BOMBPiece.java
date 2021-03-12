
package hexbaron;


//task 12 making a new bomb piece 
public class BOMBPiece extends Piece{
    
    int movesLeft = 5;
    boolean isPrimed = false;
    
    public BOMBPiece(boolean player1) {
        super(player1);
        vPValue = 5;
        fuelCostOfMove = 2;
        pieceType = "N";
    }
    
    
    
}

//12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 12 



//task 12 things to do:

//make the new bomb class

//change the checkspawncommand method in the hexbaron class

//change the executespawncommans in the hexgrid to account for the bomb occasion

//change the destroypieceandcountVPs method in hexgrid to kill all adjacent pieces and give everyone their VP's