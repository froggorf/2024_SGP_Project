package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.FieldGame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import androidx.constraintlayout.widget.ConstraintSet;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.interfaces.IGameObject;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.FieldGameScene;



public class FieldBoard implements IGameObject {
    private static final String TAG = FieldBoard.class.getSimpleName();
    Point boardCount = new Point();

    FoodBlock[][]  foodBlocks;
    RectF boardRect;
    Paint fillPaint;
    Paint borderPaint;
    Paint strokePaint;



    public FieldBoard(Point getBoardCount, RectF getBoardRect) {
        // 보드판 크기 설정
        boardCount.set(getBoardCount.x,getBoardCount.y);

        // 보드판 영역 설정
        boardRect = getBoardRect;

        // 보드판 만들기
        foodBlocks = new FoodBlock[boardCount.x][boardCount.y];

        for(int i = 0; i<boardCount.x; ++i){
            for(int j = 0; j<boardCount.y;++j){
                foodBlocks[i][j] = new FoodBlock(GetBoardPositions(new Point(i,j)));
            }
        }


        { // Paint 생성
            fillPaint = new Paint();
            fillPaint.setStyle(Paint.Style.FILL);
            fillPaint.setColor(Color.rgb(155,155,155));

            strokePaint = new Paint();
            strokePaint.setStyle(Paint.Style.STROKE);
            strokePaint.setColor(Color.BLACK);
            strokePaint.setStrokeWidth(5.0f);
        }


    }

    RectF GetBoardPositions(Point currentBoardCount) {
        // data - left / top / right / bottom
        RectF rect = new RectF();
        if(boardRect == null) Log.d("aaa", "GetBoardPositions: ???");
        rect.left= boardRect.width() / boardCount.x * currentBoardCount.x + boardRect.left;
        rect.top= boardRect.height() / boardCount.y * currentBoardCount.y + boardRect.top;
        rect.right = boardRect.width() / boardCount.x * (currentBoardCount.x+1) + boardRect.left;
        rect.bottom = boardRect.height() / boardCount.y * (currentBoardCount.y+1) + boardRect.top;
        return rect;
    }




    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(boardRect.left,boardRect.top,boardRect.right,boardRect.bottom,fillPaint);

        for(int i = 0; i<boardCount.x; ++i){
            for(int j=0; j<boardCount.y; ++j){
                // 보드판 틀 그리기
                RectF rect = GetBoardPositions(new Point(i,j));
                canvas.drawRect(rect.left,rect.top,rect.right,rect.bottom,strokePaint);

                // Food Block 그리기
                foodBlocks[i][j].Draw(canvas);
                }
            }
    }

    public void onClickEvent(MotionEvent event){
        Log.d(TAG, "onClickEvent: ");
    }
}
