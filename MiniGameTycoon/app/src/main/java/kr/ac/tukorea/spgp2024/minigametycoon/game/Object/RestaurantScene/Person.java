package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.spgp2024.minigametycoon.framework.interfaces.IGameObject;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.State.BaseState;

public class Person implements IGameObject {
    public BaseState State;
    RectF Location;    // 현재 위치

    @Override
    public void update() {
        if(State != null) State.Update();
    }

    @Override
    public void draw(Canvas canvas) {
        if(State!=null) State.Draw(canvas);
    }
}
