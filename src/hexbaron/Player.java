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

    protected int piecesInSupply, fuel, vPs, lumber, chances , startingChances;
    protected String name;

    public Player() {
    }

    public void setUpPlayer(String n, int v, int f, int l, int t, int c) {//task 11 adding chances variable
        name = n;
        vPs = v;
        fuel = f;
        lumber = l;
        piecesInSupply = t;
        chances = c;
        startingChances = c;
    }

    public String getStateString() {
        return "VPs: " + vPs + "   Pieces in supply: " + piecesInSupply + "   Lumber: " + lumber + "   Fuel: " + fuel + " Chances: " + chances;//task 11 adding chances variable
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
    
    
    //task 11 chances 
    public void removeChance(){
        chances --;
    }
    
    public int getChances(){
        return chances;
    }
    
    public void resetChances(){
        chances = startingChances;
    }
    //11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 
    
    
}
