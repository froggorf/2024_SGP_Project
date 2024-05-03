package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.FieldGame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.transition.Scene;
import android.util.Log;
import android.view.MotionEvent;

import androidx.constraintlayout.widget.ConstraintSet;

import com.google.android.material.shape.OffsetEdgeTreatment;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import kr.ac.tukorea.spgp2024.R;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.interfaces.IGameObject;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.objects.Sprite;
import kr.ac.tukorea.spgp2024.minigametycoon.framework.scene.BaseScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.FieldGameScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.Scene.TitleScene;
import kr.ac.tukorea.spgp2024.minigametycoon.game.UserDisplay;


public class FieldBoard implements IGameObject {
    // DEFINE
    private final int X = 0;
    private final int Y = 1;
    private static final String TAG = FieldBoard.class.getSimpleName(); //TAG
    private static final float BLOCK_MOVE_TIME = 0.3f;  // 보드 내 블럭 이동 시 시간
    private static final int MATCH_COUNT = 3;   //블럭 몇개 이상 맞춰야 꺠지는지 (가로/세로 방향)
    Point boardCount = new Point();     // 가로 세로 배열 크기

    FoodBlock[][]  foodBlocks;          // 보드판 내 배열
    RectF boardRect;                    // 보드판 크기
    Paint fillPaint;
    Paint borderPaint;
    Paint strokePaint;

    Point CurrentPickBlock = new Point();   // 현재 픽된 블럭의 인덱스에 대한 변수

    Vector<int[]> UpdateBlocks = new Vector<int[]>(0);

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
    public RectF GetBoardPositions(Point currentBoardCount) {
        // data - left / top / right / bottom
        RectF rect = new RectF();
        rect.left= boardRect.width() / boardCount.x * currentBoardCount.x + boardRect.left;
        rect.top= boardRect.height() / boardCount.y * currentBoardCount.y + boardRect.top;
        rect.right = boardRect.width() / boardCount.x * (currentBoardCount.x+1) + boardRect.left;
        rect.bottom = boardRect.height() / boardCount.y * (currentBoardCount.y+1) + boardRect.top;
        return rect;
    }

    // 좌표로부터 배열 인덱스를 얻는 함수
    public Point GetIndexByPosition(float mousePoint[]){
        Point point = new Point();
        int x = (int) ((mousePoint[X]- boardRect.left) / (boardRect.width()/ boardCount.x));
        int y = (int) ((mousePoint[Y]- boardRect.top) / (boardRect.height()/ boardCount.y));
        point.set(x,y);

        return point;
    }


    @Override
    public void update() {
        // 먼저 불필요한 데이터 지우고
        for(int i = 0; i<UpdateBlocks.size(); ++i){
            int[] data = UpdateBlocks.get(i);
            if(data[X] == -1 && data[Y] == -1){
                UpdateBlocks.remove(i);
                --i;
            }

        }

        // 움직임이 있어야 할 블럭들에 대해 움직임 진행
        for(int[] block : UpdateBlocks){
            foodBlocks[block[X]][block[Y]].Update(FieldGameScene.frameTime);
        }


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

        Point index = GetIndexByPosition(mousePoint);

        // 선택된 블럭이 공백칸이라면 예외처리한다.
        if(foodBlocks[index.x][index.y].FoodType == FoodTypeEnum.BLANK) return;
        // 선택된 블럭이 움직이고 있는 중인 보드면 예외처리한다.
        if (foodBlocks[index.x][index.y].bIsMovingBlock) return;

        // 픽된 블럭을 선택했다면 픽 제거하기
        if(CurrentPickBlock.equals(index.x,index.y)){
            foodBlocks[index.x][index.y].SetPickBitmap(false);
            CurrentPickBlock.set(-1,-1);
            return;
        }

        // 현재 선택된 것이 없으면 픽된 블럭으로 바꾸고 종료
        if(CurrentPickBlock.x == -1){
            SetPickBlock(index);
            return;
        }
        
        
        // 근접 보드를 클릭하면 두 블럭 서로 교체
        if(IsNearBoardWithPick(index)) {
            Point firstBlockIndex = new Point(CurrentPickBlock);
            Point secondBlockIndex = new Point(index);
            CurrentPickBlock.set(-1, -1);

            foodBlocks[firstBlockIndex.x][firstBlockIndex.y].SetPickBitmap(false);

            SwapFoodBlock(firstBlockIndex,secondBlockIndex);
        }
        
        
    }

    // 두 지정된 블럭의 위치를 서로 옮기는 함수
    private void SwapFoodBlock(Point firstBlockIndex, Point secondBlockIndex) {
        // 첫번째 인덱스 블럭 두번쨰 인덱스 위치로 옮기기
        MoveFoodBlockToIndex(firstBlockIndex,secondBlockIndex);
        // 두번째 인덱스 블럭 첫번쨰 인덱스 위치로 옮기기
        MoveFoodBlockToIndex(secondBlockIndex,firstBlockIndex);

        // 보드 블럭 변경하고서
        FoodBlock tempBlock = foodBlocks[firstBlockIndex.x][firstBlockIndex.y];
        foodBlocks[firstBlockIndex.x][firstBlockIndex.y] = foodBlocks[secondBlockIndex.x][secondBlockIndex.y];
        foodBlocks[secondBlockIndex.x][secondBlockIndex.y] =tempBlock;

        // 부술 수 있는지 체크하고,
        Set<Point> BreakBlocksVector = new HashSet<>();
        FoodTypeEnum firstFoodType = foodBlocks[firstBlockIndex.x][firstBlockIndex.y].FoodType;
        FindBreakBlocks(firstBlockIndex,firstFoodType, BreakBlocksVector);
        FoodTypeEnum secondFoodType = foodBlocks[secondBlockIndex.x][secondBlockIndex.y].FoodType;
        FindBreakBlocks(secondBlockIndex,secondFoodType, BreakBlocksVector);

        if(!BreakBlocksVector.isEmpty())    // 부술 수 있다면
        {
            new Handler().postDelayed(new Runnable(){
                public void run(){
                    for(Point breakIndex : BreakBlocksVector){
                        foodBlocks[breakIndex.x][breakIndex.y].ChangeFoodType(FoodTypeEnum.BLANK);
                    }
                }
            }, (int)(BLOCK_MOVE_TIME*1500));
        }
        else        //못 부순 다면 다시 돌리기
        {
            new Handler().postDelayed(new Runnable(){
                public void run(){
                    Log.d(TAG, "SwapFoodBlock: 실행됨!!");
                    // 첫번째 인덱스 블럭 두번쨰 인덱스 위치로 옮기기
                    MoveFoodBlockToIndex(firstBlockIndex,secondBlockIndex);
                    // 두번째 인덱스 블럭 첫번쨰 인덱스 위치로 옮기기
                    MoveFoodBlockToIndex(secondBlockIndex,firstBlockIndex);

                    FoodBlock tempBlock = foodBlocks[firstBlockIndex.x][firstBlockIndex.y];
                    foodBlocks[firstBlockIndex.x][firstBlockIndex.y] = foodBlocks[secondBlockIndex.x][secondBlockIndex.y];
                    foodBlocks[secondBlockIndex.x][secondBlockIndex.y] =tempBlock;
                }
            }, (int)(BLOCK_MOVE_TIME*1500));


        }



    }

    private void FindBreakBlocks(Point blockIndex, FoodTypeEnum foodType, Set<Point> BreakBlocksVector) {
        // 좌우 방향으로 체크
        Set<Point> HorizontalVector = new HashSet<>();
        FindSameFoodTypeBlocks(blockIndex, foodType, new Point(1,0),HorizontalVector);      //우측
        FindSameFoodTypeBlocks(blockIndex, foodType, new Point(-1,0),HorizontalVector);      //좌측

        // 위아래 방향으로 체크
        Set<Point> VerticalVector = new HashSet<>();
        FindSameFoodTypeBlocks(blockIndex, foodType, new Point(0,1),VerticalVector);      //우측
        FindSameFoodTypeBlocks(blockIndex, foodType, new Point(0,-1),VerticalVector);      //좌측

        // BreakBlocksVector에 저장하기
        if(HorizontalVector.size() >= MATCH_COUNT){
            Log.d(TAG, "FindBreakBlocks: 가로가 맞았음"+ String.format("%d %d",blockIndex.x,blockIndex.y) + " "+ HorizontalVector.size());
            BreakBlocksVector.addAll(HorizontalVector);
        }
        if(VerticalVector.size() >= MATCH_COUNT){
            Log.d(TAG, "FindBreakBlocks: 세로가 맞았음" + String.format("%d %d",blockIndex.x,blockIndex.y) + " " + VerticalVector.size());
            BreakBlocksVector.addAll(VerticalVector);
        }
    }

    private void FindSameFoodTypeBlocks(Point blockIndex, FoodTypeEnum foodType, Point offset, Set<Point> SameFoodBlockVector) {
        // blockIndex - 현재 블럭에 대한 인덱스 // foodType - 부수는 종류의 foodType // offset - 인덱스 증가되는 방향 // SameFoodBlockVector - 해당 방향에 대한 동일한 FoodType 가진 인덱스에 대한 벡터
        // 인덱스 범위 초과일 경우 재귀함수 중지
        if (offset.x != 0 && (blockIndex.x < 0 || blockIndex.x >= boardCount.x)) return;
        if (offset.y != 0 && (blockIndex.y < 0 || blockIndex.y >= boardCount.y)) return;

        // 현재 블럭이 동일한 블럭이 아니라면 반환
        if(foodBlocks[blockIndex.x][blockIndex.y].FoodType != foodType) return;
        Log.d(TAG, "FindSameFoodTypeBlocks: "+ blockIndex.x + " "+blockIndex.y);
        // 해당 블럭 추가
        SameFoodBlockVector.add(blockIndex);

        Log.d(TAG, "FindSameFoodTypeBlocks: " + SameFoodBlockVector.size());
        // 해당 방향으로 계속 탐색
        FindSameFoodTypeBlocks(new Point(blockIndex.x + offset.x, blockIndex.y + offset.y),foodType,offset,SameFoodBlockVector);
    }

    private void MoveFoodBlockToIndex(Point TargetBlockIndex, Point ToBlockIndex) {
        RectF rect = GetBoardPositions(ToBlockIndex);
        foodBlocks[TargetBlockIndex.x][TargetBlockIndex.y].SetSpriteDrawPositionWithTime(
                rect.centerX(),rect.centerY(), BLOCK_MOVE_TIME
        );
        int[] firstData =  new int[2];
        firstData[X] = TargetBlockIndex.x;
        firstData[Y] = TargetBlockIndex.y;
        UpdateBlocks.add(firstData);
    }

    private boolean IsNearBoardWithPick(Point index) {
        int xAxis = Math.abs(CurrentPickBlock.x- index.x);
        int yAxis = Math.abs(CurrentPickBlock.y- index.y);

        if(xAxis == 1 && yAxis == 0) return true;
        if(xAxis == 0 && yAxis == 1) return true;

        return false;
    }

    // 블록 선택하기
    private void SetPickBlock(Point index) {
        CurrentPickBlock.set(index.x,index.y);
        foodBlocks[index.x][index.y].SetPickBitmap(true);
    }
}
