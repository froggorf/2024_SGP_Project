package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.State;

import android.graphics.Canvas;

import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.AnimSprite;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.Person;

public class BaseState {
    protected long StartTime;
    protected Person StateOwner;
    protected AnimSprite PersonSprite;
    public BaseState(Person Owner){
        StartTime = System.currentTimeMillis();;
        StateOwner = Owner;
    }

    public void Enter(){

    }

    public void Exit(){

    }

    public void Update(){

    }

    public void Draw(Canvas canvas){
        if(PersonSprite != null) PersonSprite.draw(canvas);
    }
}
