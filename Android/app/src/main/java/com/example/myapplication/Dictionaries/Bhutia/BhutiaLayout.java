package com.example.myapplication.Dictionaries.Bhutia;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.room.Room;

import com.example.myapplication.Dictionaries.AppDatabase;
import com.example.myapplication.DictionaryLayout;
import com.example.myapplication.LayoutSetter;
import com.example.myapplication.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

//sets the layout for the results fragment
public class BhutiaLayout implements LayoutSetter {
    private DictionaryLayout dictionaryLayout;
    private int TRANSLATION_DIRECTION;


    //constructor call from ResultFrag
    public BhutiaLayout(Context context,LayoutInflater inflater, Bundle args) {
        //create your layout here.
        //get translation direction
        this.TRANSLATION_DIRECTION = args.getInt("TRANSLATION_ID");
        //begin filling in text with args here

        //once created, set dictionary layout to resulting view
        View view = inflater.inflate(R.layout.z_final_bhutia, null);
        LinearLayout linearLayout = view.findViewById(R.id.bhutia_result_linear_layout);

        String[] tranType = context.getResources().getStringArray(R.array.bhutia_array);
        TextView title = new TextView(context);
        TextView ipa = new TextView(context);
        TextView category = new TextView(context);
        TextView subTitle1 = new TextView(context);
        TextView subTitle2 = new TextView(context);
        TextView subTitle3 = new TextView(context);
        TextView subTitle4 = new TextView(context);
        TextView example = new TextView(context);

        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "Database").enableMultiInstanceInvalidation().build();
        BhutiaWord bhutiaWord = db.getBhutiaDao().engTranSearch(args.getString("QUERY_ID")).get(0);
        ipa.setText("Pronounciation: " + bhutiaWord.ipa);
        category.setText("Category: " + bhutiaWord.category);
        example.setText("Example: " + bhutiaWord.example);

        switch (tranType[TRANSLATION_DIRECTION]) {
            case ("English to Bhutia (Formal)"):
                title.setText(bhutiaWord.bhut_rom_formal);
                subTitle1.setText("English Translation: " + bhutiaWord.eng_trans);
                subTitle2.setText("Bhutia (Informal): " + bhutiaWord.bhut_script_informal);
                subTitle3.setText("Bhutia Script (Formal): " + bhutiaWord.bhut_script_formal);
                subTitle4.setText("Bhutia Script (Informal): " + bhutiaWord.bhut_rom_informal);
                break;
            case ("English to Bhutia (Informal)"):
                title.setText(bhutiaWord.bhut_rom_informal);
                subTitle1.setText("English Translation: " + bhutiaWord.eng_trans);
                subTitle2.setText("Bhutia (Formal): " + bhutiaWord.bhut_rom_formal);
                subTitle3.setText("Bhutia Script (Formal): " + bhutiaWord.bhut_script_formal);
                subTitle4.setText("Bhutia Script (Informal): " + bhutiaWord.bhut_rom_informal);
                break;
            default:
                title.setText(bhutiaWord.eng_trans);
                subTitle1.setText("Bhutia (Formal): " + bhutiaWord.bhut_rom_formal);
                subTitle2.setText("Bhutia (Informal): " + bhutiaWord.bhut_rom_informal);
                subTitle3.setText("Bhutia Script (Formal): " + bhutiaWord.bhut_script_formal);
                subTitle4.setText("Bhutia Script (Informal): " + bhutiaWord.bhut_rom_informal);
//            case ("Bhutia to English (Formal)"):
//                title.setText(bhutiaWord.eng_trans);
//                subTitle1.setText("Bhutia (Formal): " + bhutiaWord.bhut_rom_formal);
//                subTitle2.setText("Bhutia (Informal): " + bhutiaWord.bhut_rom_informal);
//                subTitle3.setText("Bhutia Script (Formal): " + bhutiaWord.bhut_script_formal);
//                subTitle4.setText("Bhutia Script (Informal): " + bhutiaWord.bhut_rom_informal);
//                break;
//            case ("Bhutia to English (Informal)"):
//                title.setText(bhutiaWord.eng_trans);
//                subTitle1.setText("Bhutia (Formal): " + bhutiaWord.bhut_rom_formal);
//                subTitle2.setText("Bhutia (Informal): " + bhutiaWord.bhut_rom_informal);
//                subTitle3.setText("Bhutia Script (Formal): " + bhutiaWord.bhut_script_formal);
//                subTitle4.setText("Bhutia Script (Informal): " + bhutiaWord.bhut_rom_informal);
//                break;
//            case ("Bhutia Script to English (Formal)"):
//                title.setText(bhutiaWord.eng_trans);
//                subTitle1.setText("Bhutia (Formal): " + bhutiaWord.bhut_rom_formal);
//                subTitle2.setText("Bhutia (Informal): " + bhutiaWord.bhut_rom_informal);
//                subTitle3.setText("Bhutia Script (Formal): " + bhutiaWord.bhut_script_formal);
//                subTitle4.setText("Bhutia Script (Informal): " + bhutiaWord.bhut_rom_informal);
//                break;
//            case ("Bhutia Script to English (Informal)"):
//                title.setText(bhutiaWord.eng_trans);
//                subTitle1.setText("Bhutia (Formal): " + bhutiaWord.bhut_rom_formal);
//                subTitle2.setText("Bhutia (Informal): " + bhutiaWord.bhut_rom_informal);
//                subTitle3.setText("Bhutia Script (Formal): " + bhutiaWord.bhut_script_formal);
//                subTitle4.setText("Bhutia Script (Informal): " + bhutiaWord.bhut_rom_informal);
//                break;
        }
        linearLayout.addView(title);
        linearLayout.addView(ipa);
        linearLayout.addView(category);
        linearLayout.addView(subTitle1);
        linearLayout.addView(subTitle2);
        linearLayout.addView(subTitle3);
        linearLayout.addView(subTitle4);
        linearLayout.addView(example);

        this.dictionaryLayout = new DictionaryLayout(view);
    }

    @Override
    public DictionaryLayout getDictionaryLayout() {
        //return the view back to the parent class
        return dictionaryLayout;
    }
}
