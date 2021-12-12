package com.example.appantonio.fragment;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.appantonio.R;

public class Fragmento extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment, rootKey);
    }
}
