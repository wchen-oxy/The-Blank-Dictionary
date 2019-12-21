package com.example.myapplication;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.Dictionaries.Bhutia.BhutiaWord;
import com.example.myapplication.Dictionaries.English.EnglishWord;
import com.example.myapplication.Dictionaries.ResultWrapper;

import org.w3c.dom.Text;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ResultWrapper mDataset;
    private final OnItemClickListener listener;
    private String curDict;

    public MyAdapter(ResultWrapper myDataset, OnItemClickListener listener, String curDict) {
        this.mDataset = myDataset;
        this.listener = listener;
        this.curDict = curDict;
    }

    public interface OnItemClickListener {
        void onItemClick(BhutiaWord item);

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public MyViewHolder (View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.row_text);
//            row = (LinearLayout) v.findViewById(query_row);

        }
        //helper function for onBindViewHolder
        public void bhutiaBind(final BhutiaWord item, final OnItemClickListener listener) {
            Log.d("Item", item.romanization);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }

        public void englishBind(final EnglishWord item, final OnItemClickListener listener) {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
//                    listener.onItemClick(item);
                }
            });
        }
    }

    //method needs to construct a RecyclerView.ViewHolder and set the view it uses to display its contents.
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
//        LinearLayout l = LayoutInflater.from(parent.getContext()).inflate(R.layout.query_result, parent, false);
//        MyViewHolder lh = new MyViewHolder(l);
//        return lh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.query_result, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
    return vh;
    }

    //method needs to fetch the appropriate data, and use it to fill in the view holder's (text) layout
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){

        //grab the specific list from mDataSet

        switch (curDict) {
            case ("BHUTIA"):
                List<BhutiaWord> bhutiaList = mDataset.getBhutiaWordList();
                holder.textView.setText(bhutiaList.get(position).romanization);
                holder.bhutiaBind(bhutiaList.get(position), listener);
                break;
            case("ENGLISH"):
                List<EnglishWord> englishList = mDataset.getEnglishWordList();
                holder.textView.setText(englishList.get(position).word);
                holder.englishBind(englishList.get(position), listener);
                break;
        }



        //determine which dictionary it is
            //bind each one accordingly




//        holder.textView.setText(mDataset.get(position).romanization);
//        holder.bind(mDataset.get(position), listener);

    }

    @Override
    public int getItemCount(){
        int size = 0;
        if (mDataset == null) return size;

        switch (curDict) {
            case ("BHUTIA"):
                List<BhutiaWord> bhutiaList = mDataset.getBhutiaWordList();
                size = bhutiaList.size();
                break;
            case("ENGLISH"):
                List<EnglishWord> englishList = mDataset.getEnglishWordList();
                size = englishList.size();
                break;
        }
        return size;

    }


//    private List getResultWrapper(ResultWrapper resultWrapper){
//        List resultList = null;
//        switch (curDict) {
//            case ("BHUTIA"):
//                resultList = resultWrapper.getBhutiaWordList();
//                break;
//            case("ENGLISH"):
//                resultList = resultWrapper.getEnglishWordList();
//                break;
//        }
//        return resultList;
//    }
}
