package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.State.Customer;

import android.graphics.Canvas;
import android.graphics.Interpolator;
import android.util.Log;

import java.util.Random;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.AnimSprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.Person;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.RestaurantScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.UserDisplay;

public class EnterRestaurantState extends CustomerBaseState{
    static int X = 0; static int Y = 1;
    float[] StartLocation = new float[2];
    float[] EndLocation = new float[2];
    float LerpAlpha = 0.0f;
    boolean bFaceRight = true;
    int[] TableTileNum;
    public EnterRestaurantState(Person Owner, float[] CustomerStartLocation, float[] CustomerEndLocation, int[] TableTileNum){
        super(Owner);

        PersonSprite = new AnimSprite(R.mipmap.temp_customer_walk_right,0,0,52*1.6f,62*1.6f,4,8);
        PersonSprite.Resize(UserDisplay.getWidth(0.1f),UserDisplay.getHeight(0.1f));

        StartLocation = CustomerStartLocation;
        EndLocation = CustomerEndLocation;

        this.TableTileNum = TableTileNum;

        if(CustomerStartLocation[X] > CustomerEndLocation[X]){
            bFaceRight =false;
            PersonSprite.setBitmapResource(R.mipmap.temp_customer_walk_left);
        }

        PersonSprite.moveTo(StartLocation[X], StartLocation[Y]);
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

        LerpAlpha += BaseScene.frameTime / 0.25;

        if(LerpAlpha >= 1.0f){
            LerpAlpha = 1.0f;
            Exit();
            StateOwner.State = new WaitFoodState(StateOwner,EndLocation,bFaceRight,TableTileNum);
            StateOwner.State.Enter();
            return;
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
