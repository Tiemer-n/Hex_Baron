/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hexbaron;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ir191258
 */
class Tile {

    protected String terrain;
    protected int x, y, z;
    protected Piece pieceInTile;
    protected List<Tile> neighbours = new ArrayList<>();

    public Tile(int xCoord, int yCoord, int zCoord) {
        x = xCoord;
        y = yCoord;
        z = zCoord;
        terrain = " ";
        pieceInTile = null;
    }

    public int getDistanceToTileT(Tile t) {
        
        //Returns the maximum of the absolute difference in x, y and z between the current tile and t.
        
        return Math.max(Math.max(Math.abs(this.getx() - t.getx()),
                Math.abs(this.gety() - t.gety())),
                Math.abs(this.getz() - t.getz()));
    }

    public void addToNeighbours(Tile N) {
        
        //Adds the tile N to the end of the list stored in the protected attribute neighbours.
        
        neighbours.add(N);
    }

    public List<Tile> getNeighbours() {
        
        //Returns the value of the protected attribute neighbours.
        
        return neighbours;
    }

    public void setPiece(Piece thePiece) {
        pieceInTile = thePiece;
    }

    public void setTerrain(String t) {
        terrain = t;
    }

    public int getx() {
        return x;
    }

    public int gety() {
        return y;
    }

    public int getz() {
        return z;
    }

    public String getTerrain() {
        
        //Returns the value of the protected attribute terrain.
        
        return terrain;
    }

    public Piece getPieceInTile() {
        
        //Returns the value of the protected attribute pieceInTile.
        
        return pieceInTile;
    }
}

