package com.blankdictionary.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankdictionary.myapplication.Dictionaries.Bhutia.BhutiaTextSwitchboard;
import com.blankdictionary.myapplication.Dictionaries.Bhutia.BhutiaWord;
import com.blankdictionary.myapplication.Dictionaries.English.EnglishWord;
import com.blankdictionary.myapplication.Dictionaries.ResultWrapper;
import com.blankdictionary.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import static com.blankdictionary.myapplication.Constants.SupportedDictionaries.BHUTIA;
import static com.blankdictionary.myapplication.Constants.SupportedDictionaries.ENGLISH;

public class MyQueryResultAdapter extends RecyclerView.Adapter<MyQueryResultAdapter.MyViewHolder> {
    private ResultWrapper mDataset;
    private View.OnClickListener listener;
    private String curDict;
    private String translation;

    public MyQueryResultAdapter(ResultWrapper myDataset, String translation, View.OnClickListener listener, String curDict) {
        this.translation = translation;
        this.mDataset = myDataset;
        this.listener = listener;
        this.curDict = curDict;
    }

    public void notifyTranslation(String newTranslation){
        this.translation = newTranslation;
        this.notifyDataSetChanged();
    }

    //method needs to construct a RecyclerView.ViewHolder and set the view it uses to display its contents.
    @Override
    public MyQueryResultAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    //method needs to fetch the appropriate data, and use it to fill in the view holder's (text) layout on creation
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        System.out.println("QueryResult " + translation);
        //grab the specific list from mDataSet
        switch (curDict) {
            case (BHUTIA):
                List<BhutiaWord> bhutiaList = mDataset.getList().getResult();
                BhutiaTextSwitchboard.select(holder, translation, bhutiaList.get(position));
                break;
            case (ENGLISH):
                List<EnglishWord> englishList = mDataset.getList().getResult();
                holder.textView.setText(englishList.get(position).word);
                break;
        }

    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (mDataset == null) return size;

        switch (curDict) {
            case (BHUTIA):
                List<BhutiaWord> bhutiaList = mDataset.getList().getResult();
                size = bhutiaList.size();
                break;
            case (ENGLISH):
                List<EnglishWord> englishList = mDataset.getList().getResult();
                size = englishList.size();
                break;
        }
        return size;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.row_text);
            itemView.setTag(this);
            itemView.setOnClickListener(listener);
        }
    }
}
