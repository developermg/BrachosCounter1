package com.example.shaina.brachoscounter;

/**
 * Created by Meira on 5/25/2016.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BrachosCounterActivity extends AppCompatActivity {
    ArrayList<String> brachosDescriptions;
    ArrayList<Integer> brachosNumbers;

    private final static String sPREFS_FIELDS = "PREFS_FIELDS";
    private final static String sBRACHOS_DESCRIPTION = "BRACHOS_DESCRIPTIONS";
    private final static String sBRACHOS_NUMBERS = "BRACHOS_NUMBERS";

    Boolean mPrefMale;
    Boolean mPrefYotzerHameoros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restoreNonSettingsActivityPreferences();
        restorePreferencesSavedFromSettingsActivity();
        setupActionBar();
        brachosDescriptions=new ArrayList<>();
        brachosNumbers=new ArrayList<>();
    }
    private void restorePreferencesSavedFromSettingsActivity ()
    {
        String currentKey;
        String currentDefaultValue;

        // Get handle to custom preferences (not from settings menu)
        // Used for persisting state to storage

        // First, get handle to user settings/preferences
        SharedPreferences defaultSharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //TODO: Set to our preferences!!!
        // Show Background Picture Preference
        currentKey = getString(R.string.male_option);
        mPrefMale = defaultSharedPreferences.getBoolean(currentKey, false);

        currentKey = getString(R.string.male_option);
        mPrefMale = defaultSharedPreferences.getBoolean(currentKey, false);
    }

    @Override
    protected void onStop ()
    {
        super.onStop ();
        saveNonSettingsActivityPreferences ();
    }





    private void setupActionBar() {
        try {
            getDelegate().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException nullPointerException) {
            //nullPointerException.printStackTrace();
        }
    }

    protected void addBrachos(String description, int number){
        brachosDescriptions.add(description);
        brachosNumbers.add(number);
    }

    protected void addBrachos(List<String> descriptions, List<Integer> numbers){
        brachosDescriptions.addAll(descriptions);
        brachosNumbers.addAll(numbers);
    }

    protected int getTotalBrachos ()
    {
        int counter = 0;
        for (Integer brachosNumber : brachosNumbers) {
            counter += brachosNumber;
        }
        String snackbarText=getString(R.string.total_brachos) + " " +counter;
        return counter;

    }
    public void clearBrachos (View view)
    {
        //call addBrachosFromRestoredActivity to flush the 'ToAdd' ArrayLists if click before they are flushed
        //addBrachosFromRestoredActivity ();
        brachosDescriptions.clear ();
        brachosNumbers.clear ();

    }

    private void restoreNonSettingsActivityPreferences () {
        Toast.makeText(this, "toasted", Toast.LENGTH_LONG).show();
        SharedPreferences settings = getSharedPreferences(sPREFS_FIELDS, MODE_PRIVATE);
        String descriptionString = settings.getString(sBRACHOS_DESCRIPTION, "");
        if (!descriptionString.isEmpty()) {
            brachosDescriptions = restoreStringListFromJSON(descriptionString);
            String brachosNumbersString = settings.getString(sBRACHOS_NUMBERS, "[]");


            ArrayList brachosArrayList = restoreIntegerListFromJSON(brachosNumbersString);

            brachosNumbers = (ArrayList<Integer>) brachosArrayList;


        }
    }
    private void saveNonSettingsActivityPreferences ()
    {

        SharedPreferences settings = getSharedPreferences (sPREFS_FIELDS, MODE_PRIVATE); //MP==0
        SharedPreferences.Editor settingsEditor = settings.edit ();

        settingsEditor.clear ();

        String jsonBrachosDescriptions = getJSON (brachosDescriptions);
        Toast.makeText(this, "json brachos descriptions:"+jsonBrachosDescriptions, Toast.LENGTH_LONG).show();
        String jsonBrachosNumbers = getJSON (brachosNumbers);
        settingsEditor.putString (sBRACHOS_DESCRIPTION, jsonBrachosDescriptions);
        settingsEditor.putString (sBRACHOS_NUMBERS, jsonBrachosNumbers);
        settingsEditor.apply ();
        SharedPreferences settings2 = getSharedPreferences (sPREFS_FIELDS, MODE_PRIVATE);
        Toast.makeText(this, settings2.getString(sBRACHOS_DESCRIPTION, "uhoh"), Toast.LENGTH_LONG).show();
        // Tax and tip are derived from values stored automatically via Settings Activity
        // So we need to store only the other two EditTexts
        //TODO: add our prefs
     /*
        */
        // settingsEditor.putString (mSUBTOTAL_PREF_KEY, mSubTotalField.getText ().toString ());
        //settingsEditor.putString (mPAYERS_PREF_KEY, mPayersField.getText ().toString ());


    }

    private String getJSON (ArrayList obj)
    {
        Gson gson = new Gson ();
        String json = gson.toJson (obj);


        return json;
    }

    private ArrayList<Integer> restoreIntegerListFromJSON (String json)
    {

        Gson gson = new Gson ();
        // This is how you tell gson about the generic type you want to get back:
        Type type = new TypeToken<ArrayList<Integer>>(){}.getType();
        ArrayList<Integer> obj = gson.fromJson (json,type);
        return obj;
    }
    private ArrayList<String> restoreStringListFromJSON (String json)
    {
        Gson gson = new Gson ();
        ArrayList<String> obj = gson.fromJson (json, ArrayList.class);
        return obj;
    }

    // This method is for launching the about
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
        AlertDialog alertDialogAbout = new AlertDialog.Builder (this).create ();
        alertDialogAbout.setTitle (getString (R.string.aboutDialog_title));
        alertDialogAbout.setMessage (getString (R.string.aboutDialog_banner));
        alertDialogAbout.setButton (DialogInterface.BUTTON_NEUTRAL,
                getString (R.string.OK), dialogOnClickListener);

        // Show the dialog window
        alertDialogAbout.show ();
    }
}
