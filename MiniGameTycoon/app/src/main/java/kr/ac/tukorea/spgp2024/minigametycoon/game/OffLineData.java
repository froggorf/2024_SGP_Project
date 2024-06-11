package kr.ac.tukorea.spgp2024.minigametycoon.game;

import android.annotation.SuppressLint;
import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import kr.ac.tukorea.spgp2024.minigametycoon.game.enums.EFurnitureType;

public class OffLineData {
    private static final String TAG = OffLineData.class.getSimpleName();
    private static String filePath = "OffLine_Data.txt";
    static private Context CONTEXT = null;

    static public long LastOfflineTime;

    static public void SetContext(Context context){
        CONTEXT = context;
        filePath = context.getFilesDir() + filePath;
    }

    // 파일에서 데이터를 읽는 함수
    static public void ReadData(){

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            LastOfflineTime = Long.parseLong(br.readLine());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // 파일로 데이터를 저장하는 함수
    @SuppressLint("DefaultLocale")
    static public void SaveData(){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false));

            String Time =String.format("%d",System.currentTimeMillis());
            bw.write(Time);

            bw.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void CreateDummyDataFile(){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false));

            //더미 시간 데이터 저장
            {
                bw.write("1718102733899");
            }

            bw.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

