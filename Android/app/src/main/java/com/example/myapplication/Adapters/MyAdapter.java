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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ResultWrapper mDataset;
    private View.OnClickListener listener;
    private String curDict;
    private ArrayList<String> Translation;

    public MyAdapter(ResultWrapper myDataset, ArrayList<String> Translation, View.OnClickListener listener, String curDict) {
        this.Translation = Translation;
        this.mDataset = myDataset;
        this.listener = listener;
        this.curDict = curDict;
    }

    //method needs to construct a RecyclerView.ViewHolder and set the view it uses to display its contents.
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LinearLayout l = LayoutInflater.from(parent.getContext()).inflate(R.layout.query_result, parent, false);
//        MyViewHolder lh = new MyViewHolder(l);
//        return lh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    //method needs to fetch the appropriate data, and use it to fill in the view holder's (text) layout on creation
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        //grab the specific list from mDataSet

        switch (curDict) {
            case ("BHUTIA"):
                List<BhutiaWord> bhutiaList = mDataset.getList().getResult();
                BhutiaTextSwitchboard.select(holder, Translation.get(0), bhutiaList.get(position));


                //FIXME ADd a bunch of switch cases for displaying the differently selected translations

//                holder.bhutiaBind(position, mDataset, listener);
                break;
            case ("ENGLISH"):
                List<EnglishWord> englishList = mDataset.getList().getResult();
                holder.textView.setText(englishList.get(position).word);
//                holder.englishBind(position, englishList.get(position), listener);
                break;
        }


        //determine which dictionary it is
        //bind each one accordingly


//        holder.textView.setText(mDataset.get(position).romanization);
//        holder.bind(mDataset.get(position), listener);

    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (mDataset == null) return size;

        switch (curDict) {
            case ("BHUTIA"):
                List<BhutiaWord> bhutiaList = mDataset.getList().getResult();
                size = bhutiaList.size();
                break;
            case ("ENGLISH"):
                List<EnglishWord> englishList = mDataset.getList().getResult();
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
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;


        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.row_text);
            itemView.setTag(this);
            itemView.setOnClickListener(listener);

//            row = (LinearLayout) v.findViewById(query_row);

        }

        //TODO: MARKED FOR REMOVAL
//        //helper function for onBindViewHolder
//        public void bhutiaBind(final int position, final ResultWrapper item, final View.OnClickListener listener) {
//            Log.d("Item", item.getBhutiaWordList().get(position).romanization);
//
////        textView.setOnClickListener(new View.OnClickListener() {
////            @Override public void onClick(View v) {
////                listener.onClick(v);
////            }
////        });
//        }
//
//        public void englishBind(final ResultWrapper item, final ResultClickListeners.OnItemClickListener listener) {
//            textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    listener.onItemClick(item);
//                }
//            });
//        }
    }

}
