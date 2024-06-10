package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.State.Customer;

import android.graphics.Canvas;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.AnimSprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.Person;
import kr.ac.tukorea.spgp2024.minigametycoon.game.UserDisplay;

public class WaitFoodState extends  CustomerBaseState{
    static int X = 0; static int Y = 1;
    float LerpAlpha = 0.0f;
    public WaitFoodState(Person Owner, float[] CustomerStartLocation, boolean bFaceRight){
        super(Owner);

        PersonSprite = new AnimSprite(R.mipmap.temp_customer_walk_right,0,0,52*1.6f,62*1.6f,4,8);
        PersonSprite.Resize(UserDisplay.getWidth(0.1f),UserDisplay.getHeight(0.1f));

        if(!bFaceRight){
            //PersonSprite.setBitmapResource(R.mipmap.temp_customer_walk_left);
        }

        PersonSprite.moveTo(CustomerStartLocation[X], CustomerStartLocation[Y]);
    }
    @Override
    public void Enter() {
        super.Enter();
    }

    @Override
    public void Exit() {
        super.Exit();
    }

    @Override
    public void Update() {
        super.Update();

        LerpAlpha += BaseScene.frameTime / 4;

        if(LerpAlpha >= 1.0f){
            LerpAlpha = 1.0f;

        }

        PersonSprite.moveTo(
                Lerp(StartLocation[X],EndLocation[X],LerpAlpha),
                Lerp(StartLocation[Y],EndLocation[Y],LerpAlpha)
        );

    }

    @Override
    public void Draw(Canvas canvas){
        super.Draw(canvas);
    }

    float Lerp (float a, float b, float f) {
        return a + f * (b - a);
    }
}
