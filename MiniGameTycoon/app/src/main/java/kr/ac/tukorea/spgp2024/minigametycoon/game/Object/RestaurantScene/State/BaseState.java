package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.State;

import android.graphics.Canvas;
import android.util.Log;

import kr.ac.tukorea.spgp2024.minigametycoon.framework.PersonAnimSprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.AnimSprite;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.Person;

public class BaseState {
    protected long StartTime;
    protected Person StateOwner;
    protected AnimSprite PersonSprite;
    public BaseState(Person Owner){
        StartTime = System.currentTimeMillis();;
        StateOwner = Owner;
        Log.d("BaseState", "BaseState: 실행" );
    }

    public void Enter(){

    }

    public void Exit(){

    }

    public void Update(){

    }

    public void Draw(Canvas canvas){

    }
}
