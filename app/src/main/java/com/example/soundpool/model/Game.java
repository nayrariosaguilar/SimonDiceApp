package com.example.soundpool.model;

import java.util.ArrayList;
import java.util.Random;

public class Game {
   public static final int START =1, MACHINE=2, PLAYER=3;
    ArrayList<ColorAudio> tiradasMachine;
    ArrayList<ColorAudio> tiradasPlayer;
    int numColors;
    int state;

    public void setTiradasMachine(ArrayList<ColorAudio> tiradasMachine) {
        this.tiradasMachine = tiradasMachine;
    }

    public void setTiradasPlayer(ArrayList<ColorAudio> tiradasPlayer) {
        this.tiradasPlayer = tiradasPlayer;
    }

    public void setNumColors(int numColors) {
        this.numColors = numColors;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Game() {
        tiradasMachine = new ArrayList<>();
        tiradasPlayer = new ArrayList<>();
    }

    public int getNumColors() {
        return numColors;
    }

    public ArrayList<ColorAudio> getTiradasPlayer() {
        return tiradasPlayer;
    }

    public ArrayList<ColorAudio> getTiradasMachine() {
        return tiradasMachine;
    }

    public void initGame(){
        numColors = 4;
        state= START;
        tiradasMachine.clear();
        tiradasPlayer.clear();
        //el mismo objeto solamente resetearlo

    }
    //COMPARE COLORS
    public boolean CompareColors(){
        //si todas son iguales retorna un true
        boolean soniguales = true;
        //en principio sera igual al tamaño de la maquina
        for(int i =0;i<tiradasPlayer.size();i++){
            if(tiradasPlayer.get(i).getColorSimon() != tiradasMachine.get(i).getColorSimon()){
                soniguales = false;
            }
        }
        return soniguales;
    }

}
