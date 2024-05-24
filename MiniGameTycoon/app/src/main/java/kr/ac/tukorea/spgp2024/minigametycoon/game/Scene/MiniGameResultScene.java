package kr.ac.tukorea.spgp2024.minigametycoon.game.Scene;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;

import java.util.HashMap;
import java.util.Map;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.res.Sound;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.MyData;
import kr.ac.tukorea.spgp2024.minigametycoon.game.UserDisplay;
import kr.ac.tukorea.spgp2024.minigametycoon.game.enums.EDataName;

public class MiniGameResultScene extends BaseScene {
    private final String TAG = MiniGameResultScene.class.getSimpleName();
    private final Paint textPaint;

    Sprite resultSprite;
    float YOffset = 0.0f;

    Map<Sprite, Integer> resultSpriteValueMap = new HashMap<>();
    int[] spritesResId = new int[]{
            0,
            R.mipmap.temp_farmgame_beet,
            R.mipmap.temp_farmgame_carrot,
            R.mipmap.temp_farmgame_lettuce,
            R.mipmap.temp_farmgame_onion,
            R.mipmap.temp_farmgame_potato,
            R.mipmap.temp_farmgame_cow,
            R.mipmap.temp_farmgame_pig,
            R.mipmap.temp_farmgame_chicken,
            R.mipmap.temp_sliced_ingredient_beet,
            R.mipmap.temp_sliced_ingredient_carrot,
            R.mipmap.temp_sliced_ingredient_lettuce,
            R.mipmap.temp_sliced_ingredient_onion,
            R.mipmap.temp_sliced_ingredient_potato,
            R.mipmap.temp_first_ingredients_beef,
            R.mipmap.temp_first_ingredients_pork,
            R.mipmap.temp_first_ingredients_chicken,
            0
    };

    public enum Layer{
        INFO, COUNT
    }
    public MiniGameResultScene(Map<EDataName, Integer> resultDataMap) {
        // 레이어 초기화
        initLayers(TitleScene.Layer.COUNT);

        resultSprite = new Sprite(R.mipmap.temp_resultscene,
                UserDisplay.getWidth(0.5f),
                UserDisplay.getHeight(0.5f),
                UserDisplay.getDesiredWidth(1.0f),
                UserDisplay.getDesiredHeight(1.0f));
        add(TownScene.Layer.BACKGROUND, resultSprite);


        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setStrokeWidth(10.0f);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(100.0f);

        int i = 0;
        YOffset = UserDisplay.getHeight(0.8f) / resultDataMap.size();
        for(Map.Entry<EDataName,Integer> entry : resultDataMap.entrySet()){
            
            resultSpriteValueMap.put(new Sprite(spritesResId[entry.getKey().ordinal()], 
                            UserDisplay.getWidth(0.2f),
                            UserDisplay.getHeight(0.1f) + YOffset*i,
                            UserDisplay.getWidth(0.2f),
                            UserDisplay.getWidth(0.2f)),
                    entry.getValue());
            i += 1;
        }

        SetResultDataAndSaveData(resultDataMap);
    }

    private void SetResultDataAndSaveData(Map<EDataName, Integer> resultDataMap) {
        for(Map.Entry<EDataName,Integer> entry : resultDataMap.entrySet()){
            MyData.AddResultData_IntType(entry.getKey(), entry.getValue());
        }
        MyData.SaveData();
    }

    @Override
    public void update(long elapsedNanos) {
        super.update(elapsedNanos);
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        for(Map.Entry<Sprite,Integer> entry : resultSpriteValueMap.entrySet()){
            entry.getKey().draw(canvas);
            float[] position = new float[2];
            position = entry.getKey().GetCenterPosition();

            if (entry.getValue() < 0 ){
                canvas.drawText(String.format("%d",entry.getValue()),
                        position[0] + UserDisplay.getWidth(0.5f),
                        position[1], textPaint);
            }else{
                canvas.drawText(String.format("+%d",entry.getValue()),
                        position[0] + UserDisplay.getWidth(0.5f),
                        position[1], textPaint);
            }


           
        }
    }
    @Override
    protected void onStart() {
        // 500ms 뒤 노래 틀기
        new Handler().postDelayed(new Runnable(){
            public void run(){
                Sound.playMusic(R.raw.temp_fieldgameinfo_sound);

            }
        }, 500);

    }

    @Override
    protected void onEnd() {
        Sound.stopMusic();
    }

    @Override
    protected void onPause() {
        Sound.pauseMusic();
    }

    @Override
    protected void onResume() {
        Sound.resumeMusic();
    }

    public boolean handleBackKey(){

        return true;
    }

    @Override
    protected int getTouchLayerIndex() {
        return Layer.INFO.ordinal();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            new TownScene().changeScene();

        }
        return super.onTouchEvent(event);
    }
}
