package com.blankdictionary.myapplication.Dictionaries.Bhutia;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.room.Room;

import com.blankdictionary.myapplication.Dictionaries.AppDatabase;
import com.blankdictionary.myapplication.DictionaryLayoutHelper;
import com.blankdictionary.myapplication.HelperInterfaces.ILayoutSetter;
import com.blankdictionary.myapplication.R;

import java.util.ArrayList;

import static com.blankdictionary.myapplication.Constants.DictionaryData.QUERY_ID;
import static com.blankdictionary.myapplication.Constants.DictionaryData.TRANSLATION_TYPE_NUM_ID;

//sets the layout for the results fragment
public class BhutiaLayout implements ILayoutSetter {
    private DictionaryLayoutHelper dictionaryLayout;
    private int selectedTranslationId;


    //constructor call from ResultFrag
    public BhutiaLayout(Context context, LayoutInflater inflater, Bundle args) {
        //create your layout here.
        //get translation direction
        this.selectedTranslationId = args.getInt(TRANSLATION_TYPE_NUM_ID);
        //begin filling in text with args here

        //once created, set dictionary layout to resulting view
        View view = inflater.inflate(R.layout.z_final_bhutia, null);
        LinearLayout linearLayout = view.findViewById(R.id.bhutia_result_linear_layout);

        String[] tranType = context.getResources().getStringArray(R.array.bhutia_array);
        ArrayList<TextView> textViewArrayList = new ArrayList<>();

        TextView title = view.findViewById(R.id.bhutia_title_text);
        TextView category = view.findViewById(R.id.bhutia_category);
        TextView subTitle1 = view.findViewById(R.id.bhutia_sub_title_1);
        TextView subTitle2 = view.findViewById(R.id.bhutia_sub_title_2);
        TextView subTitle3 = view.findViewById(R.id.bhutia_sub_title_3);
        TextView subTitle4 = view.findViewById(R.id.bhutia_sub_title_4);
//        TextView source = view.findViewById(R.id.bhutia_source);

        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "Database").allowMainThreadQueries().enableMultiInstanceInvalidation().build();
        BhutiaWord bhutiaWord = db.getBhutiaDao().engTranSearch(args.getString(QUERY_ID)).get(0);


        Log.d("Translation num", String.valueOf(selectedTranslationId));
        String english = "<b>" + context.getString(R.string.bhutia_eng_trans) + "</b> " + bhutiaWord.eng_trans;
        String bhutiaFormal = "<b>" + context.getString(R.string.bhutia_bhutia_form_trans)+ "</b> "  + bhutiaWord.bhut_rom_formal;
        String bhutiaInformal =   "<b>" + context.getString(R.string.bhutia_bhutia_inf_trans)+ "</b> "  + bhutiaWord.bhut_rom_informal;
        String bhutiaScriptFormal =  "<b>" + context.getString(R.string.bhutia_bhutiascript_form_trans)+ "</b> " + bhutiaWord.bhut_script_formal;
        String bhutiaScriptInformal =  "<b>" + context.getString(R.string.bhutia_bhutiascript_inf_trans)+ "</b> " + bhutiaWord.bhut_script_informal;
//        String sourceContent = "<b>" + context.getString(R.string.desc_source) + "</b>" + " " + bhutiaWord.source;

//        ipa.setText(Html.fromHtml(ipaContent));
//        category.setText(Html.fromHtml(categoryContent));
//        source.setText(Html.fromHtml(sourceContent));
//        example.setText(Html.fromHtml(exampleContent));

        switch (tranType[selectedTranslationId]) {
            case ("English to Bhutia (Formal)"):
                title.setText(bhutiaWord.bhut_rom_formal);
                subTitle1.setText(Html.fromHtml(english));
                subTitle2.setText(Html.fromHtml(bhutiaInformal));
                subTitle3.setText(Html.fromHtml(bhutiaScriptFormal));
                subTitle4.setText(Html.fromHtml(bhutiaScriptInformal));
//                source.setText(Html.fromHtml(sourceContent));
                break;
            case ("English to Bhutia (Informal)"):
                title.setText(bhutiaWord.bhut_rom_informal);
                subTitle1.setText(Html.fromHtml(english));
                subTitle2.setText(Html.fromHtml(bhutiaFormal));
                subTitle3.setText(Html.fromHtml(bhutiaScriptFormal));
                subTitle4.setText(Html.fromHtml(bhutiaScriptInformal));
//                source.setText(Html.fromHtml(sourceContent));
                break;

            //Everything to English
            default:
                System.out.println("BHUT " + bhutiaWord.bhut_rom_formal);
                title.setText(bhutiaWord.eng_trans);
                subTitle1.setText(Html.fromHtml(bhutiaFormal));
                subTitle2.setText(Html.fromHtml(bhutiaInformal));
                subTitle3.setText(Html.fromHtml(bhutiaScriptFormal));
                subTitle4.setText(Html.fromHtml(bhutiaScriptInformal));
//                source.setText(Html.fromHtml(sourceContent));

        }


        this.dictionaryLayout = new DictionaryLayoutHelper(view);
    }

    @Override
    public DictionaryLayoutHelper getDictionaryLayout() {
        //return the view back to the parent class
        return dictionaryLayout;
    }
}
