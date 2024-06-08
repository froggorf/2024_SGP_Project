package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.interfaces.IGameObject;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;
import kr.ac.tukorea.spgp2024.minigametycoon.game.enums.EFurnitureType;

public class FurnitureObject implements IGameObject {
    static final String TAG = FurnitureObject.class.getSimpleName();
    static int[] FurnitureResources = new int[]{
            R.mipmap.fieldgame_board_blank,R.mipmap.temp_furniture_table,R.mipmap.temp_furniture_chair,R.mipmap.temp_furniture_stove,R.mipmap.temp_furniture_countertop
    };
    RectF FurnitureRect = new RectF();
    EFurnitureType Type = EFurnitureType.EMPTY;
    Sprite FurnitureSprite;

    public FurnitureObject(){

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        if(Type == EFurnitureType.EMPTY) return;

        FurnitureSprite.draw(canvas);
    }

    public FurnitureObject SetFurnitureRect(RectF GetSize){
        FurnitureRect.set(GetSize);

        return this;
    }

    public FurnitureObject SetType(EFurnitureType GetType){
        Type = GetType;
        SetImage();
        return this;
    }

    private void SetImage(){
        if(FurnitureRect == null) return;

        if(FurnitureSprite == null){
            FurnitureSprite = new Sprite(FurnitureResources[Type.ordinal()], FurnitureRect.left,FurnitureRect.top,FurnitureRect.right,FurnitureRect.bottom);
        }
        else
        {
            FurnitureSprite.setBitmapResource(FurnitureResources[Type.ordinal()]);
        }
    }
}
