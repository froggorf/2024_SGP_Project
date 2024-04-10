package kr.ac.tukorea.spgp2024.minigametycoon.game;

import android.graphics.Point;
import android.util.Log;
import android.view.Display;

//2019180031 이윤석
// 유저의 스마트폰 크기를 가져와서 모든 화면에 그릴 수 있도록 설정
// 기존 프레임워크 에서는 가로 9.0f / 세로 16.0f 의 비율로 고정하여
// 스마트폰 비율이 9:16이 아닌 것에 대해서는 위/아래 부분이 다 그려지지 않았지만,
// UserDisplay 싱글톤 객체를 통해 크기를 관리하고
// 개발은 가로 0.0f ~ 1.0f / 세로 0.0f ~ 1.0f 의 값으로 계산하지만,
// 스마트폰 크기에 대응되는 값을 받아 올 수 있도록 설정
public class UserDisplay {
    private static final String TAG = UserDisplay.class.getSimpleName();
    private static UserDisplay instance;
    private float width;
    private float DesiredWidth = 9.0f;
    private float height;
    private float DesiredHeight = 16.0f;

    // 화면의 비율이 가로에 고정되는지에 대한 변수
    // 리소스 등이 고려된 비율이 9:16인데
    // 화면이 9:18 일 경우 bWidthFixed == true 로
    // 가로의 화면은 잘리지 않게 그리고 세로는 비율 2만큼 잘리게 그린다.
    private boolean bWidthFixed;
    // bWidthFixed 에 따른 비율을 계산한 float
    private float fDisplayRatioScale;

    public UserDisplay(Display display) {
    }

    public static void createUserDisplay(Display display){
        instance = new UserDisplay(display);
        Point size = new Point();
        display.getRealSize(size);
        instance.width = size.x;
        instance.height = size.y;

        // 화면 비율에 따른 고정 방향 구하기
        // 세로가 더 긴 경우
        if(size.x / instance.DesiredWidth < size.y / instance.DesiredHeight)
        {
            instance.bWidthFixed = false;
            instance.fDisplayRatioScale = size.y / instance.DesiredHeight;
        }
        else
        {
            instance.bWidthFixed = true;
            instance.fDisplayRatioScale = size.x / instance.DesiredWidth;
        }


    }

    public static UserDisplay getInstance(){

        return instance;
    }

    // 실제 디바이스 크기에 대응되는 크기 반환 -> UI등에서 사용
    public static float getWidth(float getValue){
        // 매개변수의 모든 값은 0.0f ~ 1.0f의 값
        // 반환 값은 0 ~ 디바이스 크기 .width
        return getValue * instance.width;
    }
    public static float getHeight(float getValue){
        // 매개변수의 모든 값은 0.0f ~ 1.0f의 값
        // 반환 값은 0 ~ 디바이스 크기 .width
        return getValue * instance.height;
    }

    // 9:16 (DesiredWidth : DesiredHeight) 비율에 대응되는 크기 반환 -> 이미지의 크기 등에서 사용
    public static float getDesiredWidth(float getValue){
        // 매개변수의 모든 값은 0.0f ~ 1.0f의 값
        // 반환 값은
        // 가로 고정일 경우
        // 0 ~ 디바이스 크기 .width
        // 세로 고정일 경우
        // 0 - 초과한 비율 크기 ~ 디바이스 크기.width + 초과한 비율 크기 이다.
        float value;
        if(!instance.bWidthFixed)
        {
            value = getValue * instance.fDisplayRatioScale * instance.DesiredWidth - ((instance.fDisplayRatioScale * instance.DesiredWidth - instance.width)/2);

        }
        else
        {
            value =getValue * instance.width;
        }
        Log.w(TAG,String.format("가로: %f",value));
        return value;
    }
    public static float getDesiredHeight(float getValue){
        float value;
        if(instance.bWidthFixed)
        {
            value = getValue * instance.fDisplayRatioScale * instance.DesiredHeight - ((instance.fDisplayRatioScale * instance.DesiredHeight - instance.height)/2);
        }
        else
        {
            value =getValue * instance.height;
        }
        Log.w(TAG,String.format("세로: %f",value));
        return value;
    }
}


