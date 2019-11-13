package com.example.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public interface FragmentCommunicator {
    void textPass(String string);
    void bundPass(String fragment, Bundle args);

}
