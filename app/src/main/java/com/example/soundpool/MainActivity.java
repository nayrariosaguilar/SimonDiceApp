package com.example.soundpool;

import android.annotation.TargetApi;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.Image;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    SoundPool soundPool;
    int sonAzul;
    int sonVerde;
    int sonRojo;
    int sonAmarillo;
    int sonError;

    int numCOLORS=4;
    View.OnClickListener listener;
    Handler handler;
    ArrayList<ColorAudio> colorAudioAlAzar;

    ImageView azulm, rojo, verde, amarillo, play;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //arrayList va creciendo
        //random de 4 elementos
        //comparo lo que
       // initSoundPool();
        createSoundPool();
        initAudios();
        listenerInitialize();
        setListeners();
        //0 a 10 y por cada uno de ellos le doy un add, reproducir la lista
        colorAudioAlAzar = new ArrayList<>();
        for (int i = 0; i<10; i++){
            colorAudioAlAzar.add(getRandomColor());
            //
        }
    }
    //methods
    private ColorAudio getRandomColor() {
        ColorAudio colorAudio;
        //generamos un numero al azar
        int rnd = new Random().nextInt(numCOLORS);
        if(rnd == 0) {
            //le pasaremos el color y suene el azul
            colorAudio = new ColorAudio(sonAzul, SimonColor.BLUE);
        }else if(rnd == 1) {
            colorAudio = new ColorAudio(sonRojo,SimonColor.RED);
        }else if(rnd == 2) {
            colorAudio = new ColorAudio(sonAmarillo,SimonColor.YELLOW);
        }else{
            colorAudio = new ColorAudio(sonVerde,SimonColor.GREEN);
        }
        return colorAudio;
    }
    //viewonClickListener
    private void initAudios() {
        sonAzul = soundPool.load(this, R.raw.sounds_01, 1);
        sonVerde = soundPool.load(this, R.raw.sounds_02, 1);
        sonRojo = soundPool.load(this, R.raw.sounds_03, 1);
        sonAmarillo = soundPool.load(this, R.raw.sounds_04, 1);
        sonError = soundPool.load(this, R.raw.error, 1);
        azulm = findViewById(R.id.ibAzul);
        rojo = findViewById(R.id.ibRojo);
        amarillo = findViewById(R.id.ibAmarillo);
        verde = findViewById(R.id.ibVerde);
        play = findViewById(R.id.ibPlay);
    }
    private void setListeners(){
        azulm.setOnClickListener(listener);
        rojo.setOnClickListener(listener);
        amarillo.setOnClickListener(listener);
        verde.setOnClickListener(listener);
        play.setOnClickListener(listener);
    }
    private void listenerInitialize() {
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.ibAzul){
                    azul();

                }else if(view.getId() == R.id.ibRojo){
                    soundPool.play(sonRojo,1,1,1,0,0);
                }else if(view.getId() == R.id.ibAmarillo){
                    soundPool.play(sonAmarillo,1,1,1,0,0);
                }else if(view.getId() == R.id.ibVerde){
                    soundPool.play(sonVerde,1,1,1,0,0);
                }else if(view.getId() == R.id.ibPlay){
                    soundPool.play(R.raw.intro,1,1,1,0,0);
                }
            }
        };
    }

    /**
     * How to use SoundPool on all API levels
     */
    protected void createSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool();
        } else {
            createOldSoundPool();
        }
    }
    /**
     * Create SoundPool for versions >= LOLLIPOP
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }
    /**
     * Create SoundPool for deprecated versions < LOLLIPOP
     */
    @SuppressWarnings("deprecation")
    protected void createOldSoundPool() {
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
    }

  // private void initSoundPool() {
    //    SoundPool soundPool = new SoundPool.Builder()
      //          .build();
        //cero para que no se repita
        //soundPool.load(this, R.raw.sounds_01,0);
        //soundPool.play(R.raw.sounds_01,1,1,1,0,0);
        //el que tenga prioridad mas alta sonara por encima de los demás, con el play indicamos que canción queremos que suene
        //podemos hacer que varias canciones suene a la vez

    //}

    public void azul() {
        azulm.setImageResource(R.drawable.blueimglight);
        
            //sp.play(soundID, leftVolume, rightVolume, priority, loop, rate);
            soundPool.play(sonAzul, 1, 1, 0, 0, 1);
        try {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    azulm.setImageResource(R.drawable.blueimg);
                }
            },500);
        } catch (Exception e) {
            Log.i("Error azul()",e.toString());
        }
    }

}