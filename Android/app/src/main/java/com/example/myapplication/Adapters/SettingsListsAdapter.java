package com.example.myapplication.Adapters;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import static com.example.myapplication.Constants.System.BUTTON_FOCUSED_COLOR;
import static com.example.myapplication.Constants.System.CURRENTLY_SELECTED_DICTIONARY;
import static com.example.myapplication.Constants.System.TRANSPARENT_COLOR;

public class SettingsListsAdapter extends RecyclerView.Adapter<SettingsListsAdapter.MyViewHolder> {
    ArrayList<String> installed;
    boolean checkboxVisiblity;
    View view;
    Context mContext;
    SharedPreferences pref;
    String selected;
    private boolean noDictionary = false;
    private int checkedPosition;


    public SettingsListsAdapter(Context context, ArrayList<String> installed) {
        if (installed.size() > 0) this.installed = installed;
        else {
            installed.add("Please Download a Dictionary.");
            this.installed = installed;
            noDictionary = true;
        }
        checkedPosition = -1;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        selected = pref.getString(CURRENTLY_SELECTED_DICTIONARY, "");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            (holder.dictRowLinearLayout).getLayoutTransition()
                    .enableTransitionType(LayoutTransition.CHANGING);
        }
        Toast.makeText(mContext, selected, Toast.LENGTH_SHORT).show();


        String language = installed.get(position);
        if (selected.equals(language)) {
            Toast.makeText(mContext, installed.get(position), Toast.LENGTH_SHORT).show();
            checkedPosition = position;
            holder.dictRowLinearLayout.setBackgroundColor(Color.parseColor(BUTTON_FOCUSED_COLOR));
        } else if (!selected.equals(language)) {
            holder.dictRowLinearLayout.setBackgroundColor(Color.parseColor(TRANSPARENT_COLOR));
        }

        holder.checkBox.setTag(language);
        holder.textView.setText(language);
        if (!noDictionary)
            holder.langNameLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Pos", String.valueOf(position));
                    if (checkedPosition != position) {
                        Toast.makeText(mContext, installed.get(position), Toast.LENGTH_SHORT).show();
                        pref.edit().putString(CURRENTLY_SELECTED_DICTIONARY, installed.get(position)).apply();
                        checkedPosition = position;
                        notifyDataSetChanged();
                    }
                }
            });

        if (checkboxVisiblity == false) holder.checkBox.setVisibility(View.GONE);
        else {
            holder.checkBox.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return installed.size();
    }


    public void makeCheckboxVisible(boolean visible) {
        if (visible) {
            checkboxVisiblity = visible;
            notifyDataSetChanged();

        } else {
            checkboxVisiblity = false;
            notifyDataSetChanged();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public CheckBox checkBox;
        public LinearLayout dictRowLinearLayout;
        public LinearLayout langNameLinearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.language_text);
            checkBox = itemView.findViewById(R.id.dict_checkbox);
            dictRowLinearLayout = itemView.findViewById(R.id.dict_pack_row_linear_layout);
            langNameLinearLayout = itemView.findViewById(R.id.dict_pack_language_title_linear_layout);

        }
    }
}
