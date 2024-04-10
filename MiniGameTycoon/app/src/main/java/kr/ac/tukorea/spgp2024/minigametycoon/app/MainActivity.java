package kr.ac.tukorea.spgp2024.minigametycoon.app;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;

import androidx.appcompat.app.AppCompatActivity;


import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.databinding.ActivityMainBinding;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.view.GameView;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.view.Metrics;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.LogoScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.UserDisplay;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
        gameView = new GameView(this);
        gameView.setFullScreen();
        setContentView(gameView);


        UserDisplay.createUserDisplay(getWindowManager().getDefaultDisplay());

        //Metrics.setGameSize(UserDisplay.getWidth(0.0f), UserDisplay.getHeight(0.0f));
        Metrics.setGameSize(UserDisplay.getWidth(1.0f), UserDisplay.getHeight(1.0f));

        new LogoScene(this, null).pushScene();
    }
}