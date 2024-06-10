package kr.ac.tukorea.spgp2024.minigametycoon.game;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import kr.ac.tukorea.spgp2024.minigametycoon.game.enums.EDataName;
import kr.ac.tukorea.spgp2024.minigametycoon.game.enums.EFurnitureType;

public class RestaurantData {
    private static final String TAG = RestaurantData.class.getSimpleName();
    private static String filePath = "Restaurant_Data.txt";
    static private Context CONTEXT = null;

    static public EFurnitureType[][] FurnitureTypeData;
    static private int MaxChefCount;
    static public int CurChefCount;
    static private int MaxServerCount;
    static public int CurServerCount;
    static public int CurGold;

    static public void SetContext(Context context){
        CONTEXT = context;
        filePath = context.getFilesDir() + filePath;
    }

    // 파일에서 데이터를 읽는 함수
    static public void ReadData(){

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            // x, y 크기 받기
            br.readLine();
            int SizeX, SizeY;
            SizeX = (int)(br.read()) - (int)('0');
            br.read();
            SizeY = br.read() - (int)('0');
            br.readLine();

            if(FurnitureTypeData == null) {
                FurnitureTypeData = new EFurnitureType[SizeX][SizeY];
            }

            br.readLine();
            // Furniture Data 값 받기
            for(int y = 0; y<SizeY; ++y){
                for(int x = 0; x<SizeX; ++x){
                    int Value = (int)(br.read()) - (int)('0');
                    FurnitureTypeData[x][y] = EFurnitureType.values()[Value];
                    br.read();
                }
                br.readLine();
            }

            // Chef / Server 데이터 받기
            {
                br.readLine();
                MaxChefCount =  (int)(br.read()) - (int)('0'); br.readLine();
                CurChefCount = (int)(br.read()) - (int)('0'); br.readLine();
                br.readLine();
                MaxServerCount =  (int)(br.read()) - (int)('0'); br.readLine();
                CurServerCount = (int)(br.read()) - (int)('0'); br.readLine();
            }

            // Gold데이터 받기
            {
                br.readLine();
                String GoldData = br.readLine();
                GoldData.replace('\n','\0');
                CurGold = Integer.parseInt(GoldData);
                Log.d(TAG, "ReadData: " + CurGold);
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // 파일로 데이터를 저장하는 함수
    static public void SaveData(){


    }


    public static void CreateDummyDataFile(){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false));

            // x,y 크기 저장
            bw.write("Restaurant Size XY\n");
            bw.write("6 8\n");

            // 값 저장
            //for(int y = 0; y < 8; ++y){
            //    for(int x = 0; x < 6; ++x){
            //        bw.write(String.format("%d",(int)(Math.random()*EFurnitureType.SIZE.ordinal()-1)) );
            //        bw.write(' ');
            //    }
            //    bw.newLine();
            //}
            bw.write("Restaurant Furniture Data\n");
            bw.write("0 0 0 0 0 0 \n");
            bw.write("0 1 1 1 1 0 \n");
            bw.write("0 0 0 0 0 0 \n");
            bw.write("0 0 0 0 0 0 \n");
            bw.write("0 3 0 0 3 0 \n");
            bw.write("0 0 0 0 0 0 \n");
            bw.write("0 0 0 0 0 0 \n");
            bw.write("0 0 0 0 0 0 \n");

            bw.write("Chef Data\n");
            bw.write("2\n");
            bw.write("1\n");
            bw.write("Server Data\n");
            bw.write("2\n");
            bw.write("1\n");

            bw.write("GOLD\n");
            bw.write("10000\n");



            bw.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }



}

