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
public class Player {

    protected int piecesInSupply, fuel, vPs, lumber;
    protected String name;

    public Player() {
    }

    public void setUpPlayer(String n, int v, int f, int l, int t) {
        name = n;
        vPs = v;
        fuel = f;
        lumber = l;
        piecesInSupply = t;
    }

    public String getStateString() {
        return "VPs: " + vPs + "   Pieces in supply: " + piecesInSupply + "   Lumber: " + lumber + "   Fuel: " + fuel;
    }

    public int getVPs() {
        return vPs;
    }

    public int getFuel() {
        return fuel;
    }

    public int getLumber() {
        return lumber;
    }

    public String getName() {
        return name;
    }

    public void addToVPs(int n) {
        vPs += n;
    }

    public void updateFuel(int n) {
        fuel += n;
    }

    public void updateLumber(int n) {
        lumber += n;
    }

    public int getPiecesInSupply() {
        return piecesInSupply;
    }

    public void removeTileFromSupply() {
        piecesInSupply -= 1;
    }
    
    public void addTileToSupply(int num){ //task 3 adding a piece to the supply
        piecesInSupply += num;
    }
}
