package com.blankdictionary.myapplication.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.blankdictionary.myapplication.Adapters.myTranslationSpinnerAdapter;
import com.blankdictionary.myapplication.HelperInterfaces.IFragmentCommunicator;
import com.blankdictionary.myapplication.MainActivity;
import com.blankdictionary.myapplication.R;
import com.blankdictionary.myapplication.Translation;

import java.io.File;
import java.lang.reflect.Field;

import static com.blankdictionary.myapplication.Constants.DictionaryData.QUERY;
import static com.blankdictionary.myapplication.Constants.DictionaryData.TRANSLATION_STRING;
import static com.blankdictionary.myapplication.Constants.DictionaryData.TRANSLATION_TYPE;
import static com.blankdictionary.myapplication.Constants.DictionaryTitles.returnTitle;
import static com.blankdictionary.myapplication.Constants.Fragment.NEW_FRAGMENT;
import static com.blankdictionary.myapplication.Constants.Fragment.SEARCH_FRAGMENT;
import static com.blankdictionary.myapplication.Constants.System.APP_NAME;
import static com.blankdictionary.myapplication.Constants.System.APP_PREFERENCES;
import static com.blankdictionary.myapplication.Constants.System.CURRENTLY_SELECTED_DICTIONARY;
import static com.blankdictionary.myapplication.Constants.System.DISABLED_COLOR;
import static com.blankdictionary.myapplication.Constants.Toast.NO_DICT_INSTALLED_TOAST;
import static com.blankdictionary.myapplication.Constants.Toast.NO_DICT_SELECTED_TOAST;

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    FragmentManager fragmentManager;
    FragmentTransaction searchTransaction;
    SearchView mainSearchBar;
    IFragmentCommunicator fragmentCommunicator;
    Bundle args;
    myTranslationSpinnerAdapter<String> translationSpinnerAdapter;
    Spinner spinner;
    Context mContext;
    TextView textView;
    private SharedPreferences pref;
    private boolean mDictionaryInstalled = true;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentCommunicator = (IFragmentCommunicator) context;
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getParentFragmentManager();
        args = new Bundle();
        pref = getContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if (!(new File(Environment.getExternalStorageDirectory(), APP_NAME).isDirectory()))
            mDictionaryInstalled = false;
        textView = new TextView(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainSearchBar = view.findViewById(R.id.searchView);
        spinner = view.findViewById(R.id.home_trans_spinner);
        try {

            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinner);

            android.util.TypedValue value = new android.util.TypedValue();
            boolean b = mContext.getTheme().resolveAttribute(android.R.attr.listPreferredItemHeight, value, true);
            String s = TypedValue.coerceToString(value.type, value.data);
            android.util.DisplayMetrics metrics = new android.util.DisplayMetrics();
            ((MainActivity) mContext).getWindowManager().getDefaultDisplay().getMetrics(metrics);
            float ret = value.getDimension(metrics);

            // Set popupWindow height to 500px
            popupWindow.setHeight((int) ret*3);
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            Log.d("ERROR", "getting default style attribute");
        }



        if (mDictionaryInstalled && pref.getString(CURRENTLY_SELECTED_DICTIONARY, null) != null) {
            TextView textView = view.findViewById(R.id.mainTitle);
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
                    if (args.getString(TRANSLATION_STRING) == null)
                        args.putString(TRANSLATION_STRING, translationSpinnerAdapter.getItem(0));
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

            final LinearLayout myLayout = (LinearLayout) view.findViewById(R.id.outer_search_linear_layout);


            myLayout.post(new Runnable() {
                @Override
                public void run() {

                    Log.i("TEST", "Screen width : " +  view.getWidth());
                    Log.i("TEST", "Layout width : " + myLayout.getWidth());
                    refreshDropdown(myLayout.getWidth());


                }
            });

        } else {
            mainSearchBar.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            LinearLayout outerLinearLayout = view.findViewById(R.id.outer_search_linear_layout);
            outerLinearLayout.setBackgroundColor(Color.parseColor(DISABLED_COLOR));

            if (!mDictionaryInstalled) {
                Toast.makeText(getActivity(), NO_DICT_INSTALLED_TOAST, Toast.LENGTH_LONG).show();
                outerLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), NO_DICT_INSTALLED_TOAST, Toast.LENGTH_LONG).show();
                    }
                });
            }
            if (mDictionaryInstalled && pref.getString(CURRENTLY_SELECTED_DICTIONARY, null) == null) {
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
        mainSearchBar.setQuery("", false);
        mainSearchBar.setFocusable(true);
        mainSearchBar.clearFocus();
        System.out.println("GET VIEW " + getView().findViewById(R.id.outer_search_linear_layout).getLayoutParams().width);
        super.onResume();
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        args.putInt(TRANSLATION_TYPE, pos);
        args.putString(TRANSLATION_STRING, parent.getItemAtPosition(pos).toString());
        translationSpinnerAdapter.itemSelect(pos);

    }

    private void refreshDropdown(int myLayoutWidth){
        String[] translationTypesArray = Translation.getSet(mContext);
        translationSpinnerAdapter = new myTranslationSpinnerAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, translationTypesArray, myLayoutWidth);
        translationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(translationSpinnerAdapter);
        spinner.setOnItemSelectedListener(this);
        int leftPadding = -1 * mainSearchBar.getWidth();
        spinner.setPadding(leftPadding, 0, spinner.getPaddingRight(), 0);


//        translationSpinnerAdapter.notifyDataSetChanged();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
