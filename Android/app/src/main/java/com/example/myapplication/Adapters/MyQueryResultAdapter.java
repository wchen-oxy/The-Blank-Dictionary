package com.example.myapplication.Adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Dictionaries.Bhutia.BhutiaTextSwitchboard;
import com.example.myapplication.Dictionaries.Bhutia.BhutiaWord;
import com.example.myapplication.Dictionaries.English.EnglishWord;
import com.example.myapplication.Dictionaries.ResultWrapper;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.Constants.SupportedDictionaries.BHUTIA;
import static com.example.myapplication.Constants.SupportedDictionaries.ENGLISH;

public class MyQueryResultAdapter extends RecyclerView.Adapter<MyQueryResultAdapter.MyViewHolder> {
    private ResultWrapper mDataset;
    private View.OnClickListener listener;
    private String curDict;
    private String translation;

    public MyQueryResultAdapter(ResultWrapper myDataset, ArrayList<String> Translation, View.OnClickListener listener, String curDict) {
        this.translation = Translation.get(0);
        this.mDataset = myDataset;
        this.listener = listener;
        this.curDict = curDict;
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
