package com.example.myapplication;

import android.util.Log;

import com.example.myapplication.Dictionaries.Bhutia.BhutiaDao;
import com.example.myapplication.Dictionaries.Bhutia.BhutiaWord;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataInsert {
    private static BhutiaWord readBhut(JsonReader reader) throws IOException {
        String romanization, ipa, category, eng_trans, tib_script, example;
        romanization = ipa = category = eng_trans = tib_script = example = null;
//        BhutiaWord res = new BhutiaWord();
        reader.beginObject();
        while (reader.hasNext()) {

            String name = reader.nextName();
            if (name.equals("romanization")) {
                romanization = reader.nextString();
            } else if (name.equals("ipa")) {
                ipa = reader.nextString();
            } else if (name.equals("category")) {
                category = reader.nextString();
            } else if (name.equals("eng_trans")) {
                eng_trans = reader.nextString();
            } else if (name.equals("tib_script")) {
                tib_script = reader.nextString();
            } else if (name.equals("example")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    continue;
                }
                example = reader.nextString();
            } else {
                reader.skipValue();
            }

        }
        reader.endObject();
        return new BhutiaWord(romanization, ipa, category, eng_trans, tib_script, example);

    }
    public static void BhuInsert(BhutiaDao bhutiaDao, File file) throws FileNotFoundException {
        Gson gson = new Gson();
        List<BhutiaWord> words = new ArrayList<BhutiaWord>();
        try{
            JsonReader reader = new JsonReader(new FileReader(file));
            reader.beginArray();
            while (reader.hasNext()) {
               while(reader.hasNext()){
                   words.add(readBhut(reader));
               }
            }
            reader.endArray();
        }
        catch (IOException io){
            io.printStackTrace();
        }
        for (BhutiaWord b:words){
            System.out.println(b.romanization);
        }
//        bhutiaDao.insertAll(words);





//        try {
//
//            JsonReader reader = new JsonReader(new FileReader(file));
//            reader.beginArray();
////            reader.beginObject();
//            while (reader.hasNext()) {
//                JsonToken nextToken = reader.peek();
//                System.out.println(nextToken);
//
//                if(JsonToken.BEGIN_OBJECT.equals(nextToken)){
//
//                    reader.beginObject();
//
//                } else if(JsonToken.NAME.equals(nextToken)){
//
//                    String name  =  reader.nextName();
//                    System.out.println(name);
//
//                } else if(JsonToken.STRING.equals(nextToken)){
//
//                    String value =  reader.nextString();
//                    System.out.println(value);
//
//                } else if(JsonToken.NUMBER.equals(nextToken)){
//
//                    long value =  reader.nextLong();
//                    System.out.println(value);
//
//                }
//            }
//        }
//        catch (IOException io) {
//            io.printStackTrace();
//        }

    }

}
