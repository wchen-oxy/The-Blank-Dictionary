package com.blankdictionary.myapplication.Adapters.AdapterHelpers;


import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

//FIXME: IMPLEMENT RECYCLERVIEW SELECTOR
final public class MyDetailsLookup extends ItemDetailsLookup {
    private final RecyclerView mRecyclerView;

    public MyDetailsLookup(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }


    @Nullable
    @Override
    public ItemDetails getItemDetails(@NonNull MotionEvent e) {
        View view = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
        if (view != null) {
//                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(view);
//                if (holder instanceof MyHolder) {
//                    return ((MyHolder) holder).getItemDetails();
//                }
        }
        return null;
    }
}