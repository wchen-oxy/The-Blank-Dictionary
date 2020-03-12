package com.example.myapplication.Adapters.AdapterHelpers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.widget.RecyclerView;

 class LangKeyProvider extends ItemKeyProvider<Long> {

    LangKeyProvider(RecyclerView.Adapter adapter) {
        super(ItemKeyProvider.SCOPE_MAPPED);
    }

    @Nullable
    @Override
    public Long getKey(int position) {
        return (long) position;
    }

    @Override
    public int getPosition(@NonNull Long key) {
        long value = key;
        return (int) value;
    }
}