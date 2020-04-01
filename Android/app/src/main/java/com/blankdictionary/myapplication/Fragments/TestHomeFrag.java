package com.blankdictionary.myapplication.Fragments;
import com.blankdictionary.myapplication.Adapters.myTranslationSpinnerAdapter;
import com.blankdictionary.myapplication.HelperInterfaces.IFragmentCommunicator;
import com.blankdictionary.myapplication.HelperInterfaces.ITranslationDialogListener;
import com.blankdictionary.myapplication.R;
import com.blankdictionary.myapplication.Translation;
import com.google.android.material.snackbar.Snackbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import junit.framework.Test;

import java.io.File;

import static com.blankdictionary.myapplication.Constants.DictionaryData.QUERY;
import static com.blankdictionary.myapplication.Constants.DictionaryData.TRANSLATION_TYPE_NUM_ID;
import static com.blankdictionary.myapplication.Constants.DictionaryTitles.returnTitle;
import static com.blankdictionary.myapplication.Constants.Fragment.NEW_FRAGMENT;
import static com.blankdictionary.myapplication.Constants.Fragment.SEARCH_FRAGMENT;
import static com.blankdictionary.myapplication.Constants.System.APP_NAME;
import static com.blankdictionary.myapplication.Constants.System.APP_PREFERENCES;
import static com.blankdictionary.myapplication.Constants.System.CURRENTLY_SELECTED_DICTIONARY;
import static com.blankdictionary.myapplication.Constants.System.DISABLED_COLOR;
import static com.blankdictionary.myapplication.Constants.System.DROPDOWN_ROW_HEIGHT;
import static com.blankdictionary.myapplication.Constants.Toast.NO_DICT_INSTALLED_TOAST;
import static com.blankdictionary.myapplication.Constants.Toast.NO_DICT_SELECTED_TOAST;


public class TestHomeFrag extends Fragment implements ITranslationDialogListener {
    private SearchView mainSearchBar;
    private IFragmentCommunicator fragmentCommunicator;
    private ITranslationDialogListener iTranslationDialog;
    private Bundle args;
    private myTranslationSpinnerAdapter<String> translationSpinnerAdapter;
    private ImageButton translationButton;
    private Context context;
    private SharedPreferences pref;
    private boolean dictionaryInstalled = true;

    public static TestHomeFrag newInstance() {

        return new TestHomeFrag();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        fragmentCommunicator = (IFragmentCommunicator) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = new Bundle();
        pref = getContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if (!(new File(Environment.getExternalStorageDirectory(), APP_NAME).isDirectory()))
            dictionaryInstalled = false;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test_home, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainSearchBar = view.findViewById(R.id.test_home_search_view);
        translationButton =  view.findViewById(R.id.test_trans_button);

        if (dictionaryInstalled && pref.getString(CURRENTLY_SELECTED_DICTIONARY, null) != null) {
            TextView textView = view.findViewById(R.id.test_title);
            textView.setText(returnTitle(pref.getString(CURRENTLY_SELECTED_DICTIONARY, "")));
            //include this to prevent the search bar from opening keyboard
            mainSearchBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainSearchBar.setIconified(false);
                }
            });
            //actual listener for submitting text
            mainSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String s) {
                    if (args.getInt(TRANSLATION_TYPE_NUM_ID, 0) == 0)
                        args.putInt(TRANSLATION_TYPE_NUM_ID, 0);
                    args.putString(QUERY, s);
                    args.putString(NEW_FRAGMENT, SEARCH_FRAGMENT);
                    fragmentCommunicator.bundPass(args, false);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });


            //TODO IMPLEMENT HERE
            translationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TranslationDialogFragment translationDialogFragment = new TranslationDialogFragment();
                    translationDialogFragment.setTargetFragment(TestHomeFrag.this, 0);
                    translationDialogFragment.show(getActivity().getSupportFragmentManager(), "Warning");
//                    iTranslationDialog.showTranslations();
                }
            });


        } else {
            mainSearchBar.setVisibility(View.GONE);
            translationButton.setVisibility(View.GONE);
            LinearLayout outerLinearLayout = view.findViewById(R.id.test_outer_search_linear_layout);
            outerLinearLayout.setBackgroundColor(Color.parseColor(DISABLED_COLOR));

            if (!dictionaryInstalled) {
                Toast.makeText(getActivity(), NO_DICT_INSTALLED_TOAST, Toast.LENGTH_LONG).show();
                outerLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), NO_DICT_INSTALLED_TOAST, Toast.LENGTH_LONG).show();
                    }
                });
            }
            if (dictionaryInstalled && pref.getString(CURRENTLY_SELECTED_DICTIONARY, null) == null) {
                Toast.makeText(getActivity(), NO_DICT_SELECTED_TOAST, Toast.LENGTH_LONG).show();
                outerLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), NO_DICT_SELECTED_TOAST, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mainSearchBar.setQuery("", false);
        mainSearchBar.setIconified(true);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {


    }

    @Override
    public void sendSelectedTranslation(int pos) {
        System.out.println("Tran " + pos);
//        args.putInt(TRANSLATION_TYPE_NUM_ID, pos);
    }
}
