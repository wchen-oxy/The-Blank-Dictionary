package com.blankdictionary.myapplication.Dictionaries.Bhutia;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BhutiaDataInsert {
    private static BhutiaWord readBhut(JsonReader reader) throws IOException {
        int entry_id;
        String
                eng_trans,
                bhut_rom_formal,
                bhut_rom_informal,
                bhut_script_formal,
                bhut_script_informal;

        eng_trans = bhut_rom_formal = bhut_rom_informal = bhut_script_formal =
                bhut_script_informal = "";
        entry_id = 0;

        reader.beginObject();

        while (reader.hasNext()) {
            switch (reader.nextName()) {
                case ("entry_id"):
                    if (reader.peek() == JsonToken.NULL) {
                        reader.skipValue();
                        continue;
                    }
                    entry_id = reader.nextInt();
                    break;
                case ("eng_trans"):
                    if (reader.peek() == JsonToken.NULL) {
                        reader.skipValue();
                        continue;
                    }
                    eng_trans = reader.nextString();
                    break;
                case ("bhut_rom_formal"):
                    if (reader.peek() == JsonToken.NULL) {
                        reader.skipValue();
                        continue;
                    }
                    bhut_rom_formal = reader.nextString();
                    break;
                case ("bhut_rom_informal"):
                    if (reader.peek() == JsonToken.NULL) {
                        reader.skipValue();
                        continue;
                    }
                    bhut_rom_informal = reader.nextString();
                    break;
                case ("bhut_script_formal"):
                    if (reader.peek() == JsonToken.NULL) {
                        reader.skipValue();
                        continue;
                    }
                    bhut_script_formal = reader.nextString();
                    break;
                case ("bhut_script_informal"):
                    if (reader.peek() == JsonToken.NULL) {
                        reader.skipValue();
                        continue;
                    }
                    bhut_script_informal = reader.nextString();
                    break;

//                case ("bhutia_source"):
//                    if (reader.peek() == JsonToken.NULL) {
//                        reader.skipValue();
//                        continue;
//                    }
//                    bhutia_source = reader.nextString();
//                    break;
                default:
                    reader.skipValue();

            }

        }
        System.out.println(entry_id);

        reader.endObject();
        return new BhutiaWord(entry_id, eng_trans, bhut_rom_formal, bhut_rom_informal,
                bhut_script_formal, bhut_script_informal);

    }

    public static void BhuInsert(BhutiaDao bhutiaDao, File file) {
        List<BhutiaWord> words = new ArrayList<BhutiaWord>();
        try {
            JsonReader reader = new JsonReader(new FileReader(file));
            reader.beginArray();
            while (reader.hasNext()) {
                while (reader.hasNext()) {
                    words.add(readBhut(reader));
                }
            }
            reader.endArray();
        } catch (IOException io) {
            io.printStackTrace();
        }
        bhutiaDao.deleteAll();
        bhutiaDao.insertAll(words);


    }
}
