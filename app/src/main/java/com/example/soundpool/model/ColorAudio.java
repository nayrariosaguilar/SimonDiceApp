package com.example.soundpool.model;

public class ColorAudio {
    SimonColor colorSimon;
    int idAudio;

    public ColorAudio(int idAudio, SimonColor colorSimon) {
        this.idAudio = idAudio;
        this.colorSimon = colorSimon;
    }

    public int getIdAudio() {
        return idAudio;
    }

    public SimonColor getColorSimon() {
        return colorSimon;
    }
}
