package com.example.myapplication.Adapters;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

import static com.example.myapplication.Constants.System.APP_PREFERENCES;
import static com.example.myapplication.Constants.System.CURRENTLY_SELECTED_DICTIONARY;

public class SettingsListsAdapter extends RecyclerView.Adapter<SettingsListsAdapter.MyViewHolder> {
    ArrayList<String> installed;
    boolean checkboxVisiblity;
    View view;
    Context mContext;
    SharedPreferences pref;


    public SettingsListsAdapter(Context context, ArrayList<String> installed){
        this.installed = installed;
        this.mContext = context;
        pref = context.getSharedPreferences(APP_PREFERENCES, 0); // 0 - for private mode;
        checkboxVisiblity = false;
    }
    @NonNull
    @Override
    public SettingsListsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dict_select, parent, false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            (holder.linearLayout).getLayoutTransition()
                    .enableTransitionType(LayoutTransition.CHANGING);
        }

        final String language = installed.get(position);
        holder.checkBox.setTag(language);
        holder.textView.setText(language);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pref.edit().putString(CURRENTLY_SELECTED_DICTIONARY, language).apply();
                Toast.makeText(mContext, "Selected " + language, Toast.LENGTH_SHORT).show();
            }
        });
        if (checkboxVisiblity == false) holder.checkBox.setVisibility(View.GONE);
        else{
            holder.checkBox.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public int getItemCount() {
        return installed.size();
    }


    public void makeCheckboxVisible(boolean visible){
        if (visible) {
            checkboxVisiblity = visible;
            notifyDataSetChanged();

        }
        else {
            checkboxVisiblity = false;
            notifyDataSetChanged();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public CheckBox checkBox;
        public LinearLayout linearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.language_text);
            checkBox = itemView.findViewById(R.id.dict_checkbox);
            linearLayout = itemView.findViewById(R.id.dict_pack_row_linear_layout);
//            itemView.setTag(this);
//            itemView.setOnClickListener(listener);
        }
    }
}
