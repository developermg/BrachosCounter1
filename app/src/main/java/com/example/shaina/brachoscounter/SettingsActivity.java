package com.example.shaina.brachoscounter;

import android.content.DialogInterface;
import android.preference.Preference;
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
        import java.util.List;
        import android.os.Bundle;
        import android.preference.CheckBoxPreference;
        import android.preference.Preference;
        import android.preference.PreferenceActivity;
        import android.preference.PreferenceGroup;
        import android.preference.PreferenceManager;
        import android.preference.PreferenceScreen;

        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.content.pm.ResolveInfo;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Setting activity of Pinyin IME.
 */
public class SettingsActivity extends PreferenceActivity implements
        Preference.OnPreferenceChangeListener {
    private static String TAG = "SettingsActivity";
    private SwitchPreference mMalePref;
    private SwitchPreference mYotzerHameorosPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setupActionBar();
        addPreferencesFromResource(R.xml.pref_general);
        PreferenceScreen prefSet = getPreferenceScreen();
        mMalePref = (SwitchPreference) prefSet
                .findPreference(getString(R.string.male_option));
        mYotzerHameorosPref = (SwitchPreference) prefSet
                .findPreference(getString(R.string.yotzer_hameoros_option));

        prefSet.setOnPreferenceChangeListener(this);

    }

    /*private void setupActionBar() {
        try {
            getDelegate().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException nullPointerException) {
            //nullPointerException.printStackTrace();
        }
    }*/

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return true;
    }

    @Override public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater ().inflate (R.menu.menu_main, menu);
        return true; //super.onCreateOptionsMenu (menu);
    }

    @Override public boolean onOptionsItemSelected (MenuItem item)
    {
        int id = item.getItemId ();

        //noinspection SimplifiableIfStatement
        switch (id)
        {
            case R.id.action_settings: {
                //showSettingsActivity ();
                return true;
            }
            case R.id.action_view_total: {
               // viewTotalBrachos();
                return true;
            }
            case R.id.about:{
                showAbout();
                return true;
            }

        }

        return super.onOptionsItemSelected (item);
    }


    // This method is called from the onClick property of the menu item "About"
    @SuppressWarnings ( {"UnusedParameters", "unused"})
    public void showAbout (MenuItem item)
    {
        showAbout ();
    }
    private void showAbout ()
    {

        // Create listener for use with dialog window (could also be created anonymously in setButton...
        DialogInterface.OnClickListener dialogOnClickListener =
                new DialogInterface.OnClickListener ()
                {
                    @Override
                    public void onClick (DialogInterface dialog, int which)
                    {
                        // Nothing needed to do here
                    }
                };

        // Create dialog window
        AlertDialog alertDialogAbout = new AlertDialog.Builder (SettingsActivity.this).create ();
        alertDialogAbout.setTitle (getString (R.string.aboutDialog_title));;
        alertDialogAbout.setMessage (getString (R.string.aboutDialog_banner));
        alertDialogAbout.setButton (DialogInterface.BUTTON_NEUTRAL,
                getString (R.string.OK), dialogOnClickListener);

        // Show the dialog window
        alertDialogAbout.show ();
    }


    public void updatePreference(PreferenceGroup parentPref, String prefKey) {
        Preference preference = parentPref.findPreference(prefKey);
        if (preference == null) {
            return;
        }
        Intent intent = preference.getIntent();
        if (intent != null) {
            PackageManager pm = getPackageManager();
            List<ResolveInfo> list = pm.queryIntentActivities(intent, 0);
            int listSize = list.size();
            if (listSize == 0)
                parentPref.removePreference(preference);
        }
    }
}
