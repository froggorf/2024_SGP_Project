package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.State.Employee.Server;

import android.graphics.Canvas;
import android.util.Log;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.AnimSprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.DishData;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.Person;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.RestaurantScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.UserDisplay;

public class ServerServeState extends ServerBaseState {
    float[] CurrentLocation;
    float[] DishTableLocation;
    float[] CustomerLocation;
    DishData CustomerDishData;
    boolean bIsCatchDish;

    float LerpAlpha = 0.0f;
    float MoveTime = 3.0f;


    public ServerServeState(Person Owner, float[] StartLocation, DishData Data) {
        super(Owner);

        CurrentLocation = StartLocation;

        PersonSprite = new AnimSprite(R.mipmap.temp_server_walk_right,0,0,54*1.6f,64*1.6f,4,8);
        PersonSprite.Resize(UserDisplay.getWidth(0.1f),UserDisplay.getHeight(0.1f));

        CustomerDishData = Data;

        DishTableLocation = ((RestaurantScene)(BaseScene.getTopScene())).RestaurantObject.GetTileCenterPos(3,3);
        DishTableLocation[0] += ((RestaurantScene)(BaseScene.getTopScene())).RestaurantObject.GetTileCenterPos(4,3)[0];
        DishTableLocation[0] /= 2.0f;

        int[] CustomerTileNum = CustomerDishData.CustomerOrderData.OrderTable;
        CustomerTileNum[1] += 2;
        CustomerLocation = ((RestaurantScene)(BaseScene.getTopScene())).RestaurantObject.GetTileCenterPos(CustomerTileNum[0], CustomerTileNum[1]);

        if(CurrentLocation[0] > DishTableLocation[0]) PersonSprite.setBitmapResource(R.mipmap.temp_server_walk_left);

        PersonSprite.moveTo(StartLocation[0],StartLocation[1]);
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
        LerpAlpha += BaseScene.frameTime/MoveTime;

        if(!bIsCatchDish){
            if(LerpAlpha >= 1.0f){
                LerpAlpha = 1.0f;
                bIsCatchDish = true;
            }

            PersonSprite.moveTo(
                    Lerp(CurrentLocation[0], DishTableLocation[0], LerpAlpha),
                    Lerp(CurrentLocation[1],DishTableLocation[1], LerpAlpha)
            );
            if(bIsCatchDish){
                LerpAlpha = 0.0f;
                CurrentLocation = DishTableLocation;
                if(CurrentLocation[0] > CustomerLocation[0]) PersonSprite.setBitmapResource(R.mipmap.temp_server_walk_left);
                else PersonSprite.setBitmapResource(R.mipmap.temp_server_walk_right);
            }

        }
        else
        {
            if(LerpAlpha >= 1.0f){
                CurrentLocation = CustomerLocation;
                Exit();
                StateOwner.State = new ServerRestState(StateOwner,CurrentLocation);
                StateOwner.State.Enter();
            }

            PersonSprite.moveTo(
                    Lerp(CurrentLocation[0], CustomerLocation[0], LerpAlpha),
                    Lerp(CurrentLocation[1], CustomerLocation[1], LerpAlpha)
            );
        }
    }

    @Override
    public void Draw(Canvas canvas) {
        super.Draw(canvas);
    }

    float Lerp (float a, float b, float f) {
        return a + f * (b - a);
    }
}
