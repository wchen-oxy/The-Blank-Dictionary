package com.example.myapplication.Dictionaries.Bhutia;

import com.example.myapplication.Adapters.MyAdapter;

public class BhutiaTextSwitchboard {
    public static void select(MyAdapter.MyViewHolder myViewHolder, String string, BhutiaWord bhutiaWord){
        switch (string){
            case("English to Bhutia (Formal)"):
                myViewHolder.textView.setText(bhutiaWord.bhut_rom_formal);
                break;
            case("English to Bhutia (Informal)"):
                myViewHolder.textView.setText(bhutiaWord.bhut_rom_informal);
                break;
            default:
                myViewHolder.textView.setText(bhutiaWord.eng_trans);
                break;

        }

    }
}
