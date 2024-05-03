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
import kr.ac.tukorea.spgp2024.minigametycoon.game.UserDisplay;


public class FieldBoard implements IGameObject {
    // DEFINE
    private final int X = 0;
    private final int Y = 1;
    private static final String TAG = FieldBoard.class.getSimpleName();
    Point boardCount = new Point();     // 가로 세로 배열 크기

    FoodBlock[][]  foodBlocks;          // 보드판 내 배열
    RectF boardRect;                    // 보드판 크기
    Paint fillPaint;
    Paint borderPaint;
    Paint strokePaint;

    Point CurrentPickBlock = new Point();   // 현재 픽된 블럭의 인덱스에 대한 변수

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

        GameInitialize();
    }

    private void GameInitialize() {
        CurrentPickBlock.set(-1,-1);
    }

    // 배열 인덱스로부터 좌표 얻는 함수
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

    // 좌표로부터 배열 인덱스를 얻는 함수
    Point GetIndexByPosition(float mousePoint[]){
        Point point = new Point();
        int x = (int) ((mousePoint[X]- boardRect.left) / (boardRect.width()/ boardCount.x));
        int y = (int) ((mousePoint[Y]- boardRect.top) / (boardRect.height()/ boardCount.y));
        point.set(x,y);

        Log.d(TAG, "GetIndexByPosition: " + String.format("%d %d",x,y));
        return point;
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
        float[] mousePoint = new float[2];
        mousePoint[X] = event.getX();
        mousePoint[Y] = event.getY();
        //Log.d(TAG, "onClickEvent: " + String.format("%f %f",mousePoint[X],mousePoint[Y]));
        Point index = GetIndexByPosition(mousePoint);

        // 현재 선택된 것이 없으면 픽된 블럭으로 바꾸고 종료
        if(CurrentPickBlock.x == -1){
            SetPickBlock(index);
            return;
        }


    }

    // 블록 선택하기
    private void SetPickBlock(Point index) {
        CurrentPickBlock.set(index.x,index.y);
        foodBlocks[index.x][index.y].SetPickBitmap(true);
    }
}
