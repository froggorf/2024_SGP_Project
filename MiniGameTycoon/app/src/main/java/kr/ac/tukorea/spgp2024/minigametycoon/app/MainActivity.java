package kr.ac.tukorea.spgp2024.minigametycoon.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.databinding.ActivityMainBinding;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.view.GameView;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.view.Metrics;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.LogoScene;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

        Log.d(TAG,"START");

        Log.d("START","시작함!!");
        gameView = new GameView(this);
        gameView.setFullScreen();
        setContentView(gameView);

        new LogoScene(this, null).pushScene();
    }
}