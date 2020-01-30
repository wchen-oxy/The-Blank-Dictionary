package com.example.myapplication.Dictionaries.English;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EnglishDataInsert {
    private static EnglishWord readEng (JsonReader reader) throws IOException {
        String ipa, definition, example;
        ipa = definition = example = "";
        reader.beginObject();

        while (reader.hasNext()) {

            switch (reader.nextName()){
                case ("ipa"):
                    if (reader.peek() == JsonToken.NULL) {
                        reader.skipValue();
                        continue;
                    }
                    ipa = reader.nextString();
                    break;
                case("definition"):
                    if (reader.peek() == JsonToken.NULL) {
                        reader.skipValue();
                        continue;
                    }
                    definition = reader.nextString();
                    break;

                case("example"):
                    if (reader.peek() == JsonToken.NULL) {
                        reader.skipValue();
                        continue;
                    }
                    example = reader.nextString();
                    break;


                default:
                    reader.skipValue();

            }

        }

        reader.endObject();
        return new EnglishWord(ipa, definition, example);

    }
    public static void engInsert(EnglishDao englishDao, File file){
        List<EnglishWord> words = new ArrayList<EnglishWord>();
        try{
            JsonReader reader = new JsonReader(new FileReader(file));
            reader.beginArray();
            while (reader.hasNext()) {
                while(reader.hasNext()){
                    words.add(readEng(reader));
                }
            }
            reader.endArray();
        }
        catch (IOException io){
            io.printStackTrace();
        }
        englishDao.deleteAll();
        englishDao.insertAll(words);


    }
}
