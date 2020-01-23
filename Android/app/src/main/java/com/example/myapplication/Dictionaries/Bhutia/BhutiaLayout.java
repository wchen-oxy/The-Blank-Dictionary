package com.example.myapplication.Dictionaries.Bhutia;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.room.Room;

import com.example.myapplication.Dictionaries.AppDatabase;
import com.example.myapplication.DictionaryLayoutHelper;
import com.example.myapplication.HelperInterfaces.ILayoutSetter;
import com.example.myapplication.R;

import java.util.ArrayList;

//sets the layout for the results fragment
public class BhutiaLayout implements ILayoutSetter {
    private DictionaryLayoutHelper dictionaryLayout;
    private int TRANSLATION_DIRECTION;


    //constructor call from ResultFrag
    public BhutiaLayout(Context context,LayoutInflater inflater, Bundle args) {
        //create your layout here.
        //get translation direction
        this.TRANSLATION_DIRECTION = args.getInt("TRANSLATION_DIRECTION");
        //begin filling in text with args here

        //once created, set dictionary layout to resulting view
        View view = inflater.inflate(R.layout.z_final_bhutia, null);
        LinearLayout linearLayout = view.findViewById(R.id.bhutia_result_linear_layout);

        String[] tranType = context.getResources().getStringArray(R.array.bhutia_array);
        ArrayList<TextView> textViewArrayList = new ArrayList<>();

        TextView title = view.findViewById(R.id.bhutia_title_text);
        TextView ipa = view.findViewById(R.id.bhutia_ipa);
        TextView category = view.findViewById(R.id.bhutia_category);
        TextView subTitle1 = view.findViewById(R.id.bhutia_sub_title_1);
        TextView subTitle2 = view.findViewById(R.id.bhutia_sub_title_2);
        TextView subTitle3 = view.findViewById(R.id.bhutia_sub_title_3);
        TextView subTitle4 = view.findViewById(R.id.bhutia_sub_title_4);
        TextView example = view.findViewById(R.id.example);
//
//
//        textViewArrayList.add(title);
//        textViewArrayList.add(ipa);
//        textViewArrayList.add(category);
//        textViewArrayList.add(subTitle1);
//        textViewArrayList.add(subTitle2);
//        textViewArrayList.add(subTitle3);
//        textViewArrayList.add(subTitle4);
//        textViewArrayList.add(example);

        //FIXME put on separate thread!
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "Database").allowMainThreadQueries().enableMultiInstanceInvalidation().build();
        BhutiaWord bhutiaWord = db.getBhutiaDao().engTranSearch(args.getString("QUERY_ID")).get(0);


        Log.d("Translation num", String.valueOf(TRANSLATION_DIRECTION));
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
                Log.d("HIT HERE", "Defualt");
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

//        for (TextView textView:textViewArrayList){
////            textView.setTypeface(Typeface.create("alegreya_sans", Typeface.NORMAL));
////            textView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
////            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            linearLayout.addView(textView);
//        }

//        linearLayout.addView(title);
//        linearLayout.addView(ipa);
//        linearLayout.addView(category);
//        linearLayout.addView(subTitle1);
//        linearLayout.addView(subTitle2);
//        linearLayout.addView(subTitle3);
//        linearLayout.addView(subTitle4);
//        linearLayout.addView(example);

        this.dictionaryLayout = new DictionaryLayoutHelper(view);
    }

    @Override
    public DictionaryLayoutHelper getDictionaryLayout() {
        //return the view back to the parent class
        return dictionaryLayout;
    }
}
