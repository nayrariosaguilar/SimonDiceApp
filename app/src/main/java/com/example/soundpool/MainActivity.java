package com.example.soundpool;

import android.annotation.TargetApi;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.nfc.cardemulation.HostNfcFService;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.soundpool.model.ColorAudio;
import com.example.soundpool.model.Game;
import com.example.soundpool.model.SimonColor;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    SoundPool soundPool;
    int sonAzul;
    int sonVerde;
    int sonRojo;
    int sonAmarillo;
    int sonError;

    int numCOLORS = 4;
    View.OnClickListener listener;
    Handler handler;
    long TIME_DELAY = 1000; //DURA UN SEGUNDO
    Game game;
    long TIME_ILUMINATE=400;
    ImageView azulm, rojo, verde, amarillo, play;
    //IlluminateColor (SIMONCOLOR C) - resetIlluminate(SIMONCOLOR C)
    //if else set Background Color
    //lo desilumina
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //arrayList va creciendo
        //random de 4 elementos
        //comparo lo que
        // initSoundPool();
        game = new Game();
        initGame();
        createSoundPool();
        initAudios();
        initListener();
        setListeners();
    //YA NO LO VAMOS USAR    generoLista();
    //ESTO DESPUES    playLista();

    }
//runnable para encender y apagarColores
    
    private void playLista() {
        //PlayLista tengo una lista de colores y sonidos vamos a hacer que suene
        //que suene de principio hasta el final
        handler = new Handler();
        // a demas del sonido
        for (int i = 0; i < game.getTiradasMachine().size(); i++) {
            //reproduce el color de mi tirada
            long timedelay = TIME_DELAY*i;
            //multiplicado por el tiempo
            handler.postDelayed(new RunnableColor(game.getTiradasMachine().get(i)), timedelay);
            soundPool.play(game.getTiradasMachine().get(i).getIdAudio(), 1, 1, 1, 0, 1);
            //apagate
            handler.postDelayed(new RunnableResetIlluminate(game.getTiradasMachine().get(i)), timedelay+TIME_ILUMINATE);
        }
    }
    public void playColor(ColorAudio c){
        Handler handler2 = new Handler();
        //llamo a uno de los runnables y luego a otro
        handler.post(new RunnableColor(c));
        handler.postDelayed( new RunnableResetIlluminate(c),TIME_ILUMINATE);
    }


    class RunnableColor implements Runnable {
        //directamente suene el audio de un color, constructor reciba el color que quiero que se repro
        //que me haga sonar un color, le tengo que pasar un color
        ColorAudio coloaudio;

        public RunnableColor(ColorAudio c) {
            coloaudio = c;
        }

        @Override
        public void run() {
            soundPool.play(coloaudio.getIdAudio(), 1, 1, 1, 0, 1);
            //iluminate color
            iluminateColor(coloaudio.getColorSimon());

        }
    }

    private void generoLista() {
        //0 a 10 y por cada uno de ellos le doy un add, reproducir la lista
        game.getTiradasMachine().clear();
        for (int i = 0; i < 10; i++) {
            game.getTiradasMachine().add(getRandomColor());
            //PlayLista tengo una lista de colores y sonidos vamos a hacer que suene

        }
    }
    private void initGame(){
        game.initGame();
        //tvInfo.setText("INICIAR");
        //DETECTO SI LE DA AL PLAY, SI ESTA EN MODO START EMPEZAMOS A JUGAR//PAUSE... NO HACE FALTA
    }

    //methods
    private ColorAudio getRandomColor() {
        ColorAudio colorAudio;
        //generamos un numero al azar
        int rnd = new Random().nextInt(numCOLORS);
        if (rnd == 0) {
            //le pasaremos el color y suene el azul
            colorAudio = new ColorAudio(sonAzul, SimonColor.BLUE);
        } else if (rnd == 1) {
            colorAudio = new ColorAudio(sonRojo, SimonColor.RED);
        } else if (rnd == 2) {
            colorAudio = new ColorAudio(sonAmarillo, SimonColor.YELLOW);
        } else {
            colorAudio = new ColorAudio(sonVerde, SimonColor.GREEN);
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

    private void setListeners() {
        azulm.setOnClickListener(listener);
        rojo.setOnClickListener(listener);
        amarillo.setOnClickListener(listener);
        verde.setOnClickListener(listener);
        play.setOnClickListener(listener);
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

    private void initListener() {
        Handler handler = new Handler();
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(game.getState()==Game.START){
                    if(v.getId() ==R.id.ibPlay){
                        //turno de la maquina
                      game.setState(Game.MACHINE);
                      //me lo cargo
                      game.getTiradasMachine().clear();
                      //tvInfo.setText("MACHINE");
                        game.getTiradasMachine().add(getRandomColor());
                        //reproduzco todo los sonidos machine
                        playLista();
                        handler.postDelayed(new RunnableState(Game.PLAYER),TIME_DELAY*game.getTiradasMachine().size());
                        //cuando acabe ahora cambia tu estado a player
                        //runnable de tstate para que cambie de esta auntomaticamente

                    }
                }else if(game.getState()==Game.PLAYER){

                 if(v.getId()==R.id.ibAzul){
                      //  soundPool.play(sonAzul,1,1,0,0,1);
                        //azulm.setImageResource(R.drawable.blueimglight);
                        //  handler.postDelayed(new RunnableResetIlluminate(),500);

                     ColorAudio c = new ColorAudio(sonAzul,SimonColor.BLUE);
                         playColor(c);
                         game.getTiradasMachine().add(c);
                    }else if(v.getId()==R.id.ibAmarillo){
                        //soundPool.play(sonAmarillo,1,1,0,0,1);
                     ColorAudio c = new ColorAudio(sonAmarillo,SimonColor.YELLOW);
                     playColor(c);
                     game.getTiradasMachine().add(c);
                    }else if(v.getId()==R.id.ibRojo){
                       // soundPool.play(sonRojo,1,1,0,0,1);
                     ColorAudio c = new ColorAudio(sonRojo,SimonColor.RED);
                     playColor(c);
                     game.getTiradasMachine().add(c);
                    }else if(v.getId()==R.id.ibVerde){
                     ColorAudio c = new ColorAudio(sonVerde,SimonColor.GREEN);
                     playColor(c);
                     game.getTiradasMachine().add(c);
                 }
                 if(game.CompareColors()){ //LISTAS IGUALES
                     //turno de la maquia cambio el estado
                     game.setState(Game.MACHINE);
                     //limpio las jugadas del jugador porque he acertado
                     //game.getTiradasMachine().clear();
                     //cambio estado, le pido a la
                     game.getTiradasMachine().add(getRandomColor());
                     playLista();
                     handler.postDelayed(new RunnableState(Game.PLAYER),TIME_DELAY*game.getTiradasMachine().size()+TIME_DELAY);
                 }else{
                     game.setState(Game.START);
                     soundPool.play(sonError,1,1,1,0,1);

                 }
                 //COMPROVAR SI OK(LISTA IGUALES)

                    //comprobar si estas en modo player despues comprobar si los chicls son igual que el que nos dio la maquina
                    //cambia estado maquina genera un numero aleatorio+1
                }
    //en el caso de que sean diferente digo error
            }
        };
    }
    private void iluminateColor(SimonColor colorSimon) {
        if(colorSimon == SimonColor.BLUE) {
            azulm.setImageResource(R.drawable.blueimglight);
        }else if(colorSimon == SimonColor.RED) {
            rojo.setImageResource(R.drawable.redimglight);
        }else if(colorSimon == SimonColor.GREEN) {
            verde.setImageResource(R.drawable.greenimglight);
        }else if(colorSimon == SimonColor.YELLOW) {
            amarillo.setImageResource(R.drawable.yellowimglight);
        }
    }

    private void resetIuminateColor(SimonColor colorSimon) {
        if(colorSimon == SimonColor.BLUE) {
            azulm.setImageResource(R.drawable.blueimg);
        }else if(colorSimon == SimonColor.RED) {
            rojo.setImageResource(R.drawable.redimg);
        }else if(colorSimon == SimonColor.GREEN) {
            verde.setImageResource(R.drawable.greenimg);
        }else if(colorSimon == SimonColor.YELLOW) {
            amarillo.setImageResource(R.drawable.yellowimg);
        }
    }

    //envia un color
    class RunnableResetIlluminate implements Runnable{
        ColorAudio coloaudio;

        public RunnableResetIlluminate(ColorAudio c) {
            coloaudio = c;
        }
        @Override
        public void run() {
            resetIuminateColor(coloaudio.getColorSimon());
            //audio y la imagen

        }
    }

    //se cambie automaticamente ele stado de la amaquina
    class RunnableState implements Runnable{
        int stateValue = 0;
        public RunnableState(int st){
            stateValue = st;
        }
        @Override
        public void run() {
         game.setState(Game.PLAYER);
        }
    }
    //secuencia es asincrona suena por otro hilo,

}
