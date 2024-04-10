package kr.ac.tukorea.spgp2024.minigametycoon.game;

import android.graphics.Point;
import android.view.Display;

//2019180031 이윤석
// 유저의 스마트폰 크기를 가져와서 모든 화면에 그릴 수 있도록 설정
// 기존 프레임워크 에서는 가로 9.0f / 세로 16.0f 의 비율로 고정하여
// 스마트폰 비율이 9:16이 아닌 것에 대해서는 위/아래 부분이 다 그려지지 않았지만,
// UserDisplay 싱글톤 객체를 통해 크기를 관리하고
// 개발은 가로 0.0f ~ 1.0f / 세로 0.0f ~ 1.0f 의 값으로 계산하지만,
// 스마트폰 크기에 대응되는 값을 받아 올 수 있도록 설정
public class UserDisplay {
    private final String TAG = UserDisplay.class.getSimpleName();
    private static UserDisplay instance;
    private float width;
    private float height;

    public UserDisplay(Display display) {
    }

    public static void createUserDisplay(Display display){
        instance = new UserDisplay(display);
        Point size = new Point();
        display.getRealSize(size);
        instance.width = size.x;
        instance.height = size.y;
    }

    public static UserDisplay getInstance(){

        return instance;
    }

    public static float getUserWidth(float getValue){
        // 매개변수의 모든 값은 0.0f ~ 1.0f의 값
        // 반환 값은 0 ~ 디바이스 크기 .width
        return getValue * instance.width;
    }

    public static float getUserHeight(float getValue){
        // 매개변수의 모든 값은 0.0f ~ 1.0f의 값
        // 반환 값은 0 ~ 디바이스 크기 .width
        return getValue * instance.height;
    }
}


