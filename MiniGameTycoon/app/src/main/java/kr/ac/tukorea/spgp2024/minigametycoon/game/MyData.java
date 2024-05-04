package kr.ac.tukorea.spgp2024.minigametycoon.game;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.JsonReader;
import android.util.Log;

import kr.ac.tukorea.spgp2024.minigametycoon.game.enums.EDataName;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MyData {
    private static final String TAG = MyData.class.getSimpleName();
    private static String filePath = "minigame_tycoon_datafile.txt";
    static private final Map<EDataName, String> DataMap = new HashMap<>();
    static private Context CONTEXT = null;

    static public void SetContext(Context context){
        CONTEXT = context;
        filePath = context.getFilesDir() + filePath;
    }
    // 파일에서 데이터를 읽는 함수
    static public void ReadData(){
        if(DataMap.isEmpty()){
            CreateDummyDataMap();
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            for(int i =0; i<EDataName.EDN_SIZE.ordinal(); ++i){
                // 데이터 타입 읽기(EDataName)
                String DataType = br.readLine();

                String Value = br.readLine();
                DataMap.replace(EDataName.values()[i],Value);
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(int i =0; i<EDataName.EDN_SIZE.ordinal(); ++i){
            Log.d(TAG, "ReadData: " + EDataName.values()[i].name() + " "+ DataMap.get(EDataName.values()[i]));
        }
    }

    // 파일로 데이터를 저장하는 함수
    static public void SaveData(){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false));

            for(int i =0; i< EDataName.EDN_SIZE.ordinal(); ++i){
                bw.write(EDataName.values()[i].name());
                bw.newLine();
                bw.write(DataMap.get(EDataName.values()[i]));
                bw.newLine();
            }

            bw.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static void CreateDummyDataMap() {
        for(int i =0; i<EDataName.EDN_SIZE.ordinal(); ++i){
            DataMap.put(EDataName.values()[i],"5");
        }
    }

    public static void CreateDummyDataFile(){
        CreateDummyDataMap();
        SaveData();
    }
    // 데이터를 얻는 Get함수
    static public String GetData(EDataName DataType){
        return DataMap.get(DataType);
    }

    // 데이터를 작성하는 Set함수
    static public void SetData(EDataName DataType, String NewValue) {
        DataMap.replace(DataType,NewValue);
    }
}



//        AssetManager assets = context.getAssets();
//        try{
//            InputStream is = assets.open(filePath);
//            InputStreamReader isr = new InputStreamReader(is);
//            JsonReader jr = new JsonReader(isr);
//            jr.beginArray();
//            while (jr.hasNext()) {
//                jr.beginObject();
//                while (jr.hasNext()) {
//                    String name = jr.nextName();
//                    DataMap.replace(EDataName.valueOf(name), jr.nextString());
//                }
//                jr.endObject();
//            }
//            jr.endArray();
//            jr.close();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }