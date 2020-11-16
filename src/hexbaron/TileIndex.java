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
class TileIndex {

    protected String terrain;
    protected int x, y, z;
    protected List<Tile> neighbours = new ArrayList<>();

    public TileIndex(int xCoord, int yCoord, int zCoord) {
        x = xCoord;
        y = yCoord;
        z = zCoord;
        terrain = " ";
        
    }

    public int getDistanceToTileT(Tile t) {
        return Math.max(Math.max(Math.abs(this.getx() - t.getx()),
                Math.abs(this.gety() - t.gety())),
                Math.abs(this.getz() - t.getz()));
    }

    public void addToNeighbours(Tile N) {
        neighbours.add(N);
    }

    public List<Tile> getNeighbours() {
        return neighbours;
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
        return terrain;
    }

}


