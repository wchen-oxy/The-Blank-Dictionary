package com.blankdictionary.myapplication.Dictionaries.English;

import android.content.Context;
import android.os.Bundle;
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

public class EnglishLayout implements ILayoutSetter {
    private DictionaryLayoutHelper dictionaryLayout;
    private int selectedTranslationId;


    //constructor call from ResultFrag
    public EnglishLayout(Context context, LayoutInflater inflater, Bundle args) {
        //create your layout here.
        //get translation direction
        this.selectedTranslationId = args.getInt(TRANSLATION_TYPE_NUM_ID);
        //begin filling in text with args here

        //once created, set dictionary layout to resulting view
        View view = inflater.inflate(R.layout.z_final_english, null);
        LinearLayout linearLayout = view.findViewById(R.id.english_result_linear_layout);

        String[] tranType = context.getResources().getStringArray(R.array.english_array);
        ArrayList<TextView> textViewArrayList = new ArrayList<>();

        TextView title = view.findViewById(R.id.english_title_text);
        TextView ipa = view.findViewById(R.id.english_ipa);
        TextView definition = view.findViewById(R.id.english_definition);
        TextView example = view.findViewById(R.id.english_example);

        //FIXME put on separate thread!
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "Database").allowMainThreadQueries().enableMultiInstanceInvalidation().build();
        EnglishWord englishWord = db.getEnglishDao().englishSearch(args.getString(QUERY_ID)).get(0);


        Log.d("Translation num", String.valueOf(selectedTranslationId));

        ipa.setText("Pronounciation: " + englishWord.ipa);
        definition.setText("Definition: " + englishWord.definition);
        example.setText("Example: " + englishWord.example);

        switch (tranType[selectedTranslationId]) {
            case ("English to English"):
                title.setText(englishWord.word);
                break;

            default:
                Log.d("Something went Wrong.", "Check EnglishLayout");

        }


        this.dictionaryLayout = new DictionaryLayoutHelper(view);
    }

    @Override
    public DictionaryLayoutHelper getDictionaryLayout() {
        //return the view back to the parent class
        return dictionaryLayout;
    }
}
