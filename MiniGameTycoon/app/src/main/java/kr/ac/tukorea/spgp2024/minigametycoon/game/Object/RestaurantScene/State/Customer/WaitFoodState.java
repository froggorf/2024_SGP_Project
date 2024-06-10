package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.State.Customer;

import android.graphics.Canvas;
import android.util.Log;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.AnimSprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.OrderData;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene.Person;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.RestaurantScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.UserDisplay;

public class WaitFoodState extends  CustomerBaseState{
    static int X = 0; static int Y = 1;
    int[] TableTileNum;
    public WaitFoodState(Person Owner, float[] CustomerStartLocation, boolean bFaceRight, int[] TableTileNum){
        super(Owner);

        this.TableTileNum = TableTileNum;

        PersonSprite = new AnimSprite(R.mipmap.temp_customer_idle_right,0,0,54*1.6f,64*1.6f,4,8);
        PersonSprite.Resize(UserDisplay.getWidth(0.1f),UserDisplay.getHeight(0.1f));

        if(!bFaceRight){
            PersonSprite.setBitmapResource(R.mipmap.temp_customer_idle_left);
        }

        PersonSprite.moveTo(CustomerStartLocation[X], CustomerStartLocation[Y]);
    }
    @Override
    public void Enter() {
        super.Enter();
        ((RestaurantScene)(BaseScene.getTopScene())).RestaurantObject.OrderIDNumber += 1;
        ((RestaurantScene)(BaseScene.getTopScene())).RestaurantObject.OrderIDNumber %= Integer.MAX_VALUE;

        OrderData NewOrderData= new OrderData(((RestaurantScene)(BaseScene.getTopScene())).RestaurantObject.OrderIDNumber, TableTileNum, StateOwner);
        ((RestaurantScene)(BaseScene.getTopScene())).RestaurantObject.Orders.add(NewOrderData);
    }

    @Override
    public void Exit() {
        super.Exit();
    }

    @Override
    public void Update() {
        super.Update();



    }

    @Override
    public void Draw(Canvas canvas){
        super.Draw(canvas);
    }
}
