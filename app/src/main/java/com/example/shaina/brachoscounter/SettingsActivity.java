package com.example.shaina.brachoscounter;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;

/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Setting activity of Pinyin IME.
 */
public class SettingsActivity extends AppCompatPreferenceActivity {
    private ArrayList<Integer> mTotalBrachosNumbers;
    private ArrayList<String> mTotalBrachosDescriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Display the fragment as the main content
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
        processIncomingData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        customOnStop(mTotalBrachosDescriptions, mTotalBrachosNumbers);
    }

    protected void customOnStop(ArrayList<String> brachosDescriptions, ArrayList<Integer> brachosNumbers) {
        saveNonSettingsActivityPreferences(brachosDescriptions, brachosNumbers);
        super.onStop();
    }

    protected void saveNonSettingsActivityPreferences(ArrayList<String> brachosDescriptions, ArrayList<Integer> brachosNumbers) {
        SharedPreferences settings = getSharedPreferences(BrachosCounterActivity.sPREFS_FIELDS, MODE_PRIVATE); //MP==0
        SharedPreferences.Editor settingsEditor = settings.edit();
        settingsEditor.clear();

        String jsonBrachosDescriptions = getJSON(brachosDescriptions);
        String jsonBrachosNumbers = getJSON(brachosNumbers);
        settingsEditor.putString(BrachosCounterActivity.sBRACHOS_DESCRIPTION, jsonBrachosDescriptions);
        settingsEditor.putString(BrachosCounterActivity.sBRACHOS_NUMBERS, jsonBrachosNumbers);
        settingsEditor.apply();
    }

    private String getJSON(ArrayList obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    private void processIncomingData() {
        Intent intent = getIntent();
        mTotalBrachosDescriptions = intent.getStringArrayListExtra(BrachosCounterActivity.sBRACHOS_DESCRIPTION);
        mTotalBrachosNumbers = intent.getIntegerArrayListExtra(BrachosCounterActivity.sBRACHOS_NUMBERS);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_view_total: {
                showSnackbar(getString(R.string.total_brachos) + " " + getTotalBrachos());
                return true;
            }
            case R.id.action_view_total_breakdown: {
                launchTotalBreakdown(mTotalBrachosDescriptions, mTotalBrachosNumbers);
                return true;
            }
            case R.id.about: {
                showAbout();
                return true;
            }
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
            case R.id.action_clear: {
                showSnackbar("Cannot clear in settings menu.");
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSnackbar(String snackbarText) {
        View cl = getListView();
        Snackbar sb = Snackbar.make(cl, snackbarText,
                Snackbar.LENGTH_LONG);
        sb.show();
    }

    public void launchTotalBreakdown(ArrayList<String> brachosDescriptions, ArrayList<Integer> brachosNumbers) {
        Intent intent = new Intent(this, BrachosBreakdownActivity.class);
        intent.putStringArrayListExtra("description", brachosDescriptions);
        intent.putIntegerArrayListExtra("amount", brachosNumbers);
        startActivity(intent);
    }

    protected int getTotalBrachos() {
        int counter = 0;
        for (Integer brachosNumber : mTotalBrachosNumbers) {
            counter += brachosNumber;
        }
        return counter;

    }

    // This method is called from the onClick property of the menu item "About"
    @SuppressWarnings({"UnusedParameters", "unused"})
    public void showAbout(MenuItem item) {
        showAbout();
    }

    private void showAbout() {
        // Create listener for use with dialog window (could also be created anonymously in setButton...
        DialogInterface.OnClickListener dialogOnClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Nothing needed to do here
                    }
                };

        // Create dialog window
        AlertDialog alertDialogAbout = new AlertDialog.Builder(SettingsActivity.this).create();
        alertDialogAbout.setTitle(getString(R.string.aboutDialog_title));
        alertDialogAbout.setMessage(getString(R.string.aboutDialog_banner));
        alertDialogAbout.setButton(DialogInterface.BUTTON_NEUTRAL,
                getString(R.string.OK), dialogOnClickListener);

        // Show the dialog window
        alertDialogAbout.show();
    }

    public static class SettingsFragment extends PreferenceFragment
            implements SharedPreferences.OnSharedPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals("male_option")) {
                Boolean value = sharedPreferences.getBoolean(key, false);
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }
    }

}
