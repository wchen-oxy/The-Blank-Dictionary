package com.blankdictionary.myapplication.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankdictionary.myapplication.Adapters.testAdapter;
import com.blankdictionary.myapplication.HelperInterfaces.ITranslationDialogListener;
import com.blankdictionary.myapplication.R;
import com.blankdictionary.myapplication.Translation;

public class TranslationDialogFragment extends DialogFragment {
    private ITranslationDialogListener iTranslationDialogListener;
    private RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            view.setSelected(true);
            int translationPosition = ((RecyclerView.ViewHolder) view.getTag()).getAdapterPosition();
            iTranslationDialogListener.sendSelectedTranslation(translationPosition);

        }
    };



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iTranslationDialogListener = (ITranslationDialogListener) getTargetFragment();
        this.adapter = new testAdapter(context, listener);
        layoutManager = new LinearLayoutManager(context);

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_translations, null);
        RecyclerView translationRecyclerView = view.findViewById(R.id.recyclerview_translations);


        translationRecyclerView.setHasFixedSize(true);
        translationRecyclerView.setLayoutManager(layoutManager);
        translationRecyclerView.setAdapter(adapter);


//
//
//        builder.setView(view);

        builder.setTitle("Pick your Translation");
        builder.setView(view);

                builder.setNeutralButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        // FIRE ZE MISSILES!
                    }
                });


        // Create the AlertDialog object and return it
        return builder.create();
    }

}