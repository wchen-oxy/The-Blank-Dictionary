package com.example.myapplication;

import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Serialization {
    public static void serializer(ArrayList arrayList){
        try
        {
            FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory()+"/BlankDictionary/list.json");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(arrayList);
            oos.close();
            fos.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public static ArrayList<String> deserializer(File file){
        ArrayList<String> arrayList = new ArrayList<>();
        try
        {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            arrayList = (ArrayList) ois.readObject();


            ois.close();
            fis.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();

        }
        catch (ClassNotFoundException c)
        {
            System.out.println("Class not found");
            c.printStackTrace();

        }
        Log.d("Object Read", arrayList.toString());
        return arrayList;

    }
}
