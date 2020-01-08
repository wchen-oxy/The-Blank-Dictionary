package com.example.myapplication.Dictionaries.Bhutia;

import android.util.Log;

import com.example.myapplication.Dictionaries.Bhutia.BhutiaDao;
import com.example.myapplication.Dictionaries.Bhutia.BhutiaWord;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BhutiaDataInsert {
    private static BhutiaWord readBhut(JsonReader reader) throws IOException {
        String ipa, category, eng_trans, bhut_rom_formal,
                bhut_rom_informal,  bhut_script_formal, bhut_script_informal,
                example,  spoken_b, spoken_e;
        ipa = category = eng_trans = bhut_rom_formal =
                bhut_rom_informal = bhut_script_formal = bhut_script_informal =
                example =  spoken_b = spoken_e = null;
//        BhutiaWord res = new BhutiaWord();
        reader.beginObject();
        while (reader.hasNext()) {
            Log.d("INSERTING", "NOW");

            switch (reader.nextName()){
                case ("ipa"):
                    if (reader.peek() == JsonToken.NULL) {
                        reader.skipValue();
                        continue;
                    }
                    ipa = reader.nextString();
                    break;
                case("category"):
                    if (reader.peek() == JsonToken.NULL) {
                        reader.skipValue();
                        continue;
                    }
                    category = reader.nextString();
                    break;
                case("eng_trans"):
                    if (reader.peek() == JsonToken.NULL) {
                        reader.skipValue();
                        continue;
                    }
                    eng_trans = reader.nextString();
                    break;
                case("bhut_rom_formal"):
                    if (reader.peek() == JsonToken.NULL) {
                        reader.skipValue();
                        continue;
                    }
                    bhut_rom_formal = reader.nextString();
                    break;
                case("bhut_rom_informal"):
                    if (reader.peek() == JsonToken.NULL) {
                        reader.skipValue();
                        continue;
                    }
                    bhut_rom_informal = reader.nextString();
                    break;
                case("bhut_script_formal"):
                    if (reader.peek() == JsonToken.NULL) {
                        reader.skipValue();
                        continue;
                    }
                    bhut_script_formal = reader.nextString();
                    break;
                case("bhut_script_informal"):
                    if (reader.peek() == JsonToken.NULL) {
                        reader.skipValue();
                        continue;
                    }
                    bhut_script_informal = reader.nextString();
                    break;
                case("example"):
                    if (reader.peek() == JsonToken.NULL) {
                        reader.skipValue();
                        continue;
                    }
                    example = reader.nextString();
                    break;
                case("spoken_b"):
                    if (reader.peek() == JsonToken.NULL) {
                        reader.skipValue();
                        continue;
                    }
                    spoken_b = reader.nextString();
                    break;
                case("spoken_e"):
                    if (reader.peek() == JsonToken.NULL) {
                        reader.skipValue();
                        continue;
                    }
                    spoken_e = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    Log.d("bhutiaDataInsert", "SKIPPED");

            }

//            String name = reader.nextName();
//            if (name.equals("romanization")) {
//                romanization = reader.nextString();
//            } else if (name.equals("ipa")) {
//                ipa = reader.nextString();
//            } else if (name.equals("category")) {
//                category = reader.nextString();
//            } else if (name.equals("eng_trans")) {
//                eng_trans = reader.nextString();
//            } else if (name.equals("tib_script")) {
//                tib_script = reader.nextString();
//            } else if (name.equals("example")) {
//                if (reader.peek() == JsonToken.NULL) {
//                    reader.skipValue();
//                    continue;
//                }
//                example = reader.nextString();
//            } else {
//                reader.skipValue();
//            }

        }
        reader.endObject();
        return new BhutiaWord(ipa, category, eng_trans, bhut_rom_formal,
                bhut_rom_informal,  bhut_script_formal, bhut_script_informal,
                example,  spoken_b, spoken_e);

    }
    public static void BhuInsert(BhutiaDao bhutiaDao, File file){
//        Gson gson = new Gson();
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
            System.out.println(b.eng_trans);
        }
        bhutiaDao.deleteAll();
        bhutiaDao.insertAll(words);

        //test code
        for (BhutiaWord b: bhutiaDao.getAll())
        {Log.d("PR3", b.eng_trans);}
    }
}
