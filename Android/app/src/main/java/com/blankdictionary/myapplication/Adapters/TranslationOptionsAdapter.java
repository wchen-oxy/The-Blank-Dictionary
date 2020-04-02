package com.blankdictionary.myapplication.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankdictionary.myapplication.R;
import com.blankdictionary.myapplication.Translation;

public class TranslationOptionsAdapter extends RecyclerView.Adapter<TranslationOptionsAdapter.MyViewHolder>{
    private String[] translations;
    private View.OnClickListener listener;
    private int currentTranslation;

    public TranslationOptionsAdapter(Context context, View.OnClickListener listener, int initialTranslation){
        System.out.println("TranslationOptionsAdapter");
        this.listener = listener;
        translations = Translation.getSet(context);
        currentTranslation = initialTranslation;
    }
    public void notifyNewTranslation(int selectedItem) {
        this.currentTranslation = selectedItem;
        this.notifyDataSetChanged();

    }


    @NonNull
    @Override
    public TranslationOptionsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_translation_option, parent, false);
        TranslationOptionsAdapter.MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(translations[position]);
        if (currentTranslation == position) holder.itemView.setBackgroundColor(Color.rgb(235, 235, 235));
        else{
            holder.itemView.setBackgroundColor(Color.WHITE);
        }

    }

    @Override
    public int getItemCount() {
        return translations.length;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_translation_type);
            itemView.setTag(this);
            itemView.setOnClickListener(listener);
        }
    }
}
