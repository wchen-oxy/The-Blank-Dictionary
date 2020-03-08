package com.example.myapplication;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Constants {
    //Network Constants
    public static class Network {
        public static final String BASE_URL = "http://blank-dictionary.herokuapp.com/";
        public static final String DOWNLOAD_URL_PART = "api/";
        public static final String STATUS_URL_PART = "status/";
        public static final String DICT_LIST_URL_PART = "all/";
        public static final String SECRET_CODE = "Az39dB0n!23";
        public static final String NO_INTERNET_ERROR = "No Connection to the Internet";
        public static final String REQUEST_DESCRIPTION = "Your Dictionary is now downloading.";
        public static final String REQUEST_TITLE = "Dictionary Download Started.";
        public static final String REQUEST_AUTH_HEADER = "Authorization";
        public static final String LANG_DOWNLOAD_HANDLER_THREAD_NAME = "LanguageDownload";
        private static final String DEBUG_URL = "http://10.0.2.2:8000/";

        public static String getAbsoluteUrl(String relativeUrl) {
            return BASE_URL + relativeUrl;
        }


        public static String authDigest() {

            byte[] data1 = SECRET_CODE.getBytes();
            String output = "";

            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                data1 = md.digest(data1);

                output = String.format("%02x", new BigInteger(1, data1));

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return output;
        }
    }

    //Key Value Pairs
    public static class Fragment {
        public static final String NEW_FRAGMENT = "NewFragment";
        public static final String HOME_FRAGMENT = "HomeFragment";
        public static final String SEARCH_FRAGMENT = "SearchFragment";
        public static final String RESULT_FRAGMENT = "ResultFragment";
        public static final String LANGUAGE_DOWNLOAD_FRAGMENT = "LangDownloadFragment";
        public static final String DICTIONARY_SELECTION_FRAGMENT = "DictionaryFragment";
        public static final String SETTINGS_FRAGMENT = "SettingsFragment";
    }

    public static class IntentFilters {
        public static final String SERVER_REACHED = "ServerReached";
        public static final String DICTIONARY_LIST_DOWNLOADED = "DictionaryListDownloaded";

    }

    public static class System {
        public static final String APP_NAME = "BlankDictionary";
        public static final String APP_PREFERENCES = "BlankDictPref";
        public static final String CURRENTLY_SELECTED_DICTIONARY = "CurrentDictionary";
        public static final String FONT_STYLE = "alegreya_sans";
        public static final String APP_DICTIONARY_FILE = "/BlankDictionary/list.json";
        public static final String DATABASE = "Database";
        public static final String DATABASE_CLEARED = "Database Cleared";
        public static final String BUTTON_FOCUSED_COLOR = "#c2c2c2";
        public static final String TRANSPARENT_COLOR = "#00000000";
        public static final String DISABLED_COLOR = "#ebe6e6";
    }

    public static class SupportedDictionaries {
        public static final String BHUTIA = "BHUTIA";
        public static final String ENGLISH = "ENGLISH";


    }

    public static class DictionaryData {
        public static final String QUERY = "Query";
        public static final String QUERY_ID = "QueryId";
        //Note, the value for the Translation Type is an int value!
        public static final String TRANSLATION_TYPE = "TranslationType";
        public static final String TRANSLATION_STRING = "TranslationString";
        public static final String DATABASE = "Database";

    }

    public static class Toast {
        public static final String NO_DICT_INSTALLED_TOAST = "Please Download a Dictionary from Settings.";
        public static final String NO_DICT_SELECTED_TOAST = "Choose your current dictionary in Settings.";
        public static final String DICTIONARY_SELECTED_TOAST = "Dictionary Selected";
        public static final String DICTIONARY_IS_DOWNLOADING_TOAST = "Dictionary is downloading.";
        public static final String BUTTON_SELECTED_TOAST = "Selected";
        public static final String DICT_STILL_DOWNLOADING_TOAST = "A dictionary is already downloading.";
        public static final String BAD_SERVER_CONNECTION_TOAST = "Can't Connect to server to download available dictionaries.";


    }


    //

}
