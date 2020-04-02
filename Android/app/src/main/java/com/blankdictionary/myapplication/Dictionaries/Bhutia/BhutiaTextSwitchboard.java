package com.blankdictionary.myapplication.Dictionaries.Bhutia;

import com.blankdictionary.myapplication.Adapters.MyQueryResultAdapter;

public class BhutiaTextSwitchboard {
    public static void select(MyQueryResultAdapter.MyViewHolder myViewHolder, String string, BhutiaWord bhutiaWord) {
        switch (string) {
            case ("English to Bhutia (Formal)"):
                System.out.println("English to Bhutia (Formal)");
                System.out.println(bhutiaWord.bhut_rom_formal);
                myViewHolder.textView.setText(bhutiaWord.bhut_rom_formal);

                break;
            case ("English to Bhutia (Informal)"):
                System.out.println("English to Bhutia (inFormal)");
                System.out.println(bhutiaWord.bhut_rom_informal);
                myViewHolder.textView.setText(bhutiaWord.bhut_rom_informal);

                break;
            default:
                System.out.println("default");
                System.out.println(bhutiaWord.eng_trans);

                myViewHolder.textView.setText(bhutiaWord.eng_trans);



                break;

        }

    }
}
