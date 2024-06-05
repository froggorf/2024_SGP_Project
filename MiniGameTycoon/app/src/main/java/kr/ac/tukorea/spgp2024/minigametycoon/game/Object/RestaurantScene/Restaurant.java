package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.health.connect.datatypes.HeightRecord;
import android.renderscript.Float2;
import android.util.Log;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.interfaces.IGameObject;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;

public class Restaurant implements IGameObject {
    static final String TAG = Restaurant.class.getSimpleName();
    static final int X = 0, Y = 1;

    static final int[] RestaurantTileNum = new int[]{6,8};
    RectF RestaurantSize = new RectF();

    Sprite TileSprite;


    public Restaurant(RectF Size) {
        RestaurantSize.set(Size);

        InitialLoad();
    }

    private void InitialLoad() {
        if(TileSprite == null) TileSprite = new Sprite(R.mipmap.temp_restaurant_tile, -100, -100, RestaurantSize.width()/RestaurantTileNum[X], RestaurantSize.height()/RestaurantTileNum[Y]);

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        for(int i = 0; i<RestaurantTileNum[X]; ++i){
            for(int j = 0; j< RestaurantTileNum[Y]; ++j){
                float[] CenterPos = GetTileCenterPos(i,j);
                TileSprite.moveTo(CenterPos[X], CenterPos[Y]);

                TileSprite.draw(canvas);
            }
        }
    }

    private float[] GetTileCenterPos(int x, int y){
        float[] CenterPos = new float[2];
        // 좌측 + 각 칸당 길이 * (x + 0.5)
        CenterPos[X] = RestaurantSize.left + TileSprite.getWidth() * (x + 0.5f);

        // 상단 + 각 칸당 길이 * (y + 0.5)
        CenterPos[Y] = RestaurantSize.top + TileSprite.getHeight() * (y+0.5f);

        return CenterPos;
    }
}
