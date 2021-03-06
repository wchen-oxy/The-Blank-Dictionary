package com.blankdictionary.myapplication.Dictionaries.English;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EnglishDataInsert {
    private static EnglishWord readEng(JsonReader reader) throws IOException {
        int entry_id;

        String word, ipa, definition, example;
        word = ipa = definition = example = "";
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
                case ("word"):
                    if (reader.peek() == JsonToken.NULL) {
                        reader.skipValue();
                        continue;
                    }
                    word = reader.nextString();
                    break;

                case ("ipa"):
                    if (reader.peek() == JsonToken.NULL) {
                        reader.skipValue();
                        continue;
                    }
                    ipa = reader.nextString();
                    break;
                case ("definition"):
                    if (reader.peek() == JsonToken.NULL) {
                        reader.skipValue();
                        continue;
                    }
                    definition = reader.nextString();
                    break;

                case ("example"):
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
        return new EnglishWord(entry_id, word, definition);

    }

    public static void engInsert(EnglishDao englishDao, File file) {
        List<EnglishWord> words = new ArrayList<EnglishWord>();
        try {
            JsonReader reader = new JsonReader(new FileReader(file));
            reader.beginArray();
            while (reader.hasNext()) {
                while (reader.hasNext()) {
                    words.add(readEng(reader));
                }
            }
            reader.endArray();
        } catch (IOException io) {
            io.printStackTrace();
        }
        englishDao.deleteAll();
        englishDao.insertAll(words);


    }
}
