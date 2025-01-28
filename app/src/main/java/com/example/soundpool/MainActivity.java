package com.example.soundpool;

import android.annotation.TargetApi;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.Image;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    SoundPool soundPool;
    int sonAzul;
    int sonVerde;
    int sonRojo;
    int sonAmarillo;
    int sonError;
    View.OnClickListener listener;

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
    private void listenerInitialize() {
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.ibAzul){

                }else if(view.getId() == R.id.ibRojo){

                }else if(view.getId() == R.id.ibAmarillo){

                }else if(view.getId() == R.id.ibVerde){

                }else if(view.getId() == R.id.ibPlay){

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
}