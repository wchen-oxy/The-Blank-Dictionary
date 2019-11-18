package com.example.myapplication;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.Dictionaries.Bhutia.BhutiaWord;

import org.w3c.dom.Text;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<BhutiaWord> mDataset;
    private final OnItemClickListener listener;

    public MyAdapter(List<BhutiaWord> myDataset, OnItemClickListener listener) {
        mDataset = myDataset;
        this.listener = listener;
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

        public void bind(final BhutiaWord item, final OnItemClickListener listener) {
            Log.d("Item", item.romanization);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
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

    //method needs to fetch the appropriate data, and use it to fill in the view holder's layout
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        holder.textView.setText(mDataset.get(position).romanization);
        holder.bind(mDataset.get(position), listener);

    }

    @Override
    public int getItemCount(){
        if (mDataset == null) return 0;
        return mDataset.size();
    }
}
