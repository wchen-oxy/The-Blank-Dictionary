package com.blankdictionary.myapplication;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import android.content.Context;

import static com.blankdictionary.myapplication.Constants.System.APP_DICTIONARY_FILE;
import static com.blankdictionary.myapplication.Constants.System.APP_DICTIONARY_FOLDER;


public class DataSerialization {
    public static void serializer(ArrayList arrayList, Context context) {
        try {
            File folder = new File(context.getExternalFilesDir(null), APP_DICTIONARY_FOLDER);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            File file = new File(context.getExternalFilesDir(null) +  APP_DICTIONARY_FILE);
            System.out.println(file.getAbsolutePath());
            System.out.println(folder.exists());
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(arrayList);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    public static ArrayList<String> deserializer(File file) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            arrayList = (ArrayList) ois.readObject();


            ois.close();
            fis.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();

        } catch (ClassNotFoundException c) {
            c.printStackTrace();

        }
        return arrayList;

    }
}
