package kr.ac.tukorea.spgp2024.minigametycoon.app;

import android.os.Bundle;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;


import kr.ac.tukorea.spgp2024.databinding.ActivityMainBinding;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.view.GameView;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.view.Metrics;

import kr.ac.tukorea.spgp2024.minigametycoon.game.MyData;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.FieldGameScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.FoodPrepGameScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.LogoScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.RestaurantScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.TitleScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.TownScene;
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

        MyData.SetContext(this);
        MyData.CreateDummyDataFile();
        MyData.ReadData();

        UserDisplay.createUserDisplay(getWindowManager().getDefaultDisplay());

        //Metrics.setGameSize(UserDisplay.getWidth(0.0f), UserDisplay.getHeight(0.0f));
        Metrics.setGameSize(UserDisplay.getWidth(1.0f), UserDisplay.getHeight(1.0f));

        //new LogoScene(this,null).pushScene();
        new RestaurantScene().pushScene();
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
        return gameView.onTouchEvent(event);
        //return super.onTouchEvent(event);

        //return false;
    }
}