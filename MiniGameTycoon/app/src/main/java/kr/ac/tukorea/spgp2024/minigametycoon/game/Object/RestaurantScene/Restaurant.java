package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.health.connect.datatypes.HeightRecord;
import android.renderscript.Float2;
import android.util.Log;
import android.util.Size;

import org.jetbrains.annotations.NotNull;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.interfaces.IGameObject;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;
import kr.ac.tukorea.spgp2024.minigametycoon.game.RestaurantData;
import kr.ac.tukorea.spgp2024.minigametycoon.game.enums.EFurnitureType;

public class Restaurant implements IGameObject {
    static final String TAG = Restaurant.class.getSimpleName();
    static final int X = 0, Y = 1;

    static final int[] RestaurantTileNum = new int[]{6,8};
    FurnitureObject[][] FurnitureObjects;
    RectF RestaurantSize = new RectF();

    Sprite TileSprite;


    public Restaurant(RectF Size) {
        RestaurantSize.set(Size);

        InitialLoad();
    }

    private void InitialLoad() {
        if(TileSprite == null) TileSprite = new Sprite(R.mipmap.temp_restaurant_tile, -100, -100, RestaurantSize.width()/RestaurantTileNum[X], RestaurantSize.height()/RestaurantTileNum[Y]);

        FurnitureObjects = new FurnitureObject[RestaurantTileNum[X]][RestaurantTileNum[Y]];
        for(int x = 0; x < RestaurantTileNum[X]; ++x){
            for(int y = 0; y<RestaurantTileNum[Y]; ++y){
                FurnitureObjects[x][y] = new FurnitureObject();
                RectF FurnitureRect = new RectF();
                float[] Center = GetTileCenterPos(x,y);
                FurnitureRect.set(Center[X],Center[Y],RestaurantSize.width()/RestaurantTileNum[X], RestaurantSize.height()/RestaurantTileNum[Y]);

                FurnitureObjects[x][y].SetFurnitureRect(FurnitureRect);
            }
        }

        // 데이터 로드하여 type 변경하기
        EFurnitureType[][] Data = RestaurantData.FurnitureTypeData;
        for(int x = 0; x < RestaurantTileNum[X]; ++x) {
            for (int y = 0; y < RestaurantTileNum[Y]; ++y) {
                FurnitureObjects[x][y].SetType(Data[x][y]);
            }
        }


    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        // 바닥 타일 그리기
        for(int i = 0; i<RestaurantTileNum[X]; ++i){
            for(int j = 0; j< RestaurantTileNum[Y]; ++j){
                float[] CenterPos = GetTileCenterPos(i,j);
                TileSprite.moveTo(CenterPos[X], CenterPos[Y]);
                TileSprite.draw(canvas);
            }
        }

        // Furniture 가구 그리기
        {
            for(int x = 0; x<RestaurantTileNum[X]; ++x){
                // 아래서부터 그려질 수 있도록 y는 큰값에서 작은값으로 draw
                for(int y = RestaurantTileNum[Y]-1; y>= 0; --y){
                    FurnitureObjects[x][y].draw(canvas);
                }
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
