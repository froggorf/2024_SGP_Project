package kr.ac.tukorea.spgp2024.minigametycoon.app;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.LogWriter;


import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.databinding.ActivityMainBinding;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.view.GameView;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.view.Metrics;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.FieldGameScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.FoodPrepHouseGameScene;
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

        //new LogoScene(this, null).pushScene();
        new FieldGameScene().pushScene();
    }

    @Override
    protected void onPause(){
        gameView.pauseGame();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resumeGame();
    }

    @Override
    protected void onDestroy() {
        BaseScene.popAll();
        GameView.clear();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (gameView.handleBackKey()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gameView.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}