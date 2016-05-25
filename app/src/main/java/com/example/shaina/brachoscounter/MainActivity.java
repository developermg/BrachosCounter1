package com.example.shaina.brachoscounter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

//import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity
{

    private final static String sPREFS_FIELDS = "PREFS_FIELDS";
    private final static String sBRACHOS_DESCRIPTION = "BRACHOS_DESCRIPTIONS";
    private final static String sBRACHOS_NUMBERS = "BRACHOS_NUMBERS";
    final int SINGLE_BRACHOS_REQUEST_CODE = 1;
    final int MULTI_BRACHOS_REQUEST_CODE = 2;
    final int MULTI_BRACHOS_MULTIPLE_REQUEST_CODE = 3;
    ArrayList<String> brachosDescriptions;
    ArrayList<Integer> brachosNumbers;
    ArrayList<String> brachosDescriptionsToAdd;
    ArrayList<Integer> brachosNumbersToAdd;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        brachosDescriptions = new ArrayList<> ();
        brachosNumbers = new ArrayList<> ();
        brachosDescriptionsToAdd = new ArrayList<> ();
        brachosNumbersToAdd = new ArrayList<> ();
    }


    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult (requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case MULTI_BRACHOS_MULTIPLE_REQUEST_CODE:
                    ArrayList<Integer> numbers = data.getIntegerArrayListExtra ("BRACHOS_NUMBERS");
                    if (!numbers.isEmpty ()) {
                        brachosDescriptionsToAdd.addAll (
                                data.getStringArrayListExtra ("BRACHOS_DESCRIPTIONS"));
                        brachosNumbersToAdd.addAll (numbers);
                    }
                    break;
                case MULTI_BRACHOS_REQUEST_CODE:
                    int number = data.getIntExtra ("BRACHOS_NUMBER", 1);
                    if (number > 0) {
                        brachosDescriptionsToAdd.add (data.getStringExtra ("BRACHOS_DESCRIPTION"));
                        brachosNumbersToAdd.add (number);
                    }
                    break;

            }
        }
    }

    public void launchDaveningPage (View view)
    {
        Intent intent = new Intent (this, DaveningActivity.class);
        startActivityForResult (intent, MULTI_BRACHOS_MULTIPLE_REQUEST_CODE);
    }

    public void launchAddYourOwnPage (View view)
    {
        Intent intent = new Intent (this, AddYourOwnActivity.class);
        startActivityForResult (intent, MULTI_BRACHOS_REQUEST_CODE);
    }

    public void launchFoodDrink (View view)
    {
        Intent intent = new Intent (this, BrachosActivity.class);
        String[] foodBrachos = {"Hamotzi", "Mezonos", "Hagafen", "Haetz", "Ha'adama", "Shehakol",
                                "Birkas Hamazon", "Al Hamichya", "Borei Nefashos"};
        intent.putExtra (getString (R.string.brachosList), foodBrachos);
        startActivityForResult (intent, MULTI_BRACHOS_MULTIPLE_REQUEST_CODE);
    }

    public void launchHolidays(View view) {
        Intent intent = new Intent(this, BrachosActivity.class);
        String[] holidayBrachos = {"L'hadlik Ner Chanuka", "She'asa Nisim", "Shehechiyanu", "Lulav and Esrog",
                "Leisheiv BaSukkah", "Mikra Megillah"};
        intent.putExtra(getString(R.string.brachosList), holidayBrachos);
        startActivityForResult(intent, MULTI_BRACHOS_MULTIPLE_REQUEST_CODE);
    }

    public void launchBirchosHanehenin(View view) {
        Intent intent = new Intent(this, BrachosActivity.class);
        String[] haneheninBrachos = {"Minei Besamim", "Atzei Besamim", "Isvei Besamim", "Reiach tov l'peiros"};
        intent.putExtra(getString(R.string.brachosList), haneheninBrachos);
        startActivityForResult(intent, MULTI_BRACHOS_MULTIPLE_REQUEST_CODE);
    }

    public void launchMiscellaneous(View view) {
        Intent intent = new Intent(this, BrachosActivity.class);
        String[] miscBrachos = {"Sheva Brachos Bracha", "Mezuzah", "Ma'akah", "Tevilas Keilim",
                "Hafrashas Challah", "Maaser", "Oseh Maaseh Beraishis", "Shekocho Ugvuraso",
                "Seeing a rainbow", "Seeing an ocean", "Meshaneh Habriyos", "Seeing a blooming tree",
                "Seeing a Torah scholar", "Seeing a secular scholar", "Chacham Harazim", "Dayan Haemes",
                "HaTov V'hameitiv", "Sha'asa li nes", "Shehechiyanu", "Asher Yatzar", "Tefillas Haderech"};
        intent.putExtra(getString(R.string.brachosList), miscBrachos);
        startActivityForResult(intent, MULTI_BRACHOS_MULTIPLE_REQUEST_CODE);
    }
    
  /*  public void launchTotalBreakdown(View view) {
        Intent intent = new Intent(this, BrachosBreakdown.class);
        intent.putExtra("description", brachosDescriptions);
        intent.putExtra("amount", brachosNumbers);
    }*/

    @Override
    protected void onStart ()
    {
        super.onStart ();
        // restorePreferencesSavedFromSettingsActivity();
        restoreNonSettingsActivityPreferences ();
        addBrachosFromRestoredActivity ();

    }

    //TODO: Do we need an onResume to restore things?
    @Override
    protected void onResume ()
    {
        super.onResume ();
       /* applyNightModePreference();
        showHideBackground ();*/
    }

    @Override
    protected void onStop ()
    {
        super.onStop ();
        saveNonSettingsActivityPreferences ();
    }

    private void restorePreferencesSavedFromSettingsActivity ()
    {
        String currentKey;
        String currentDefaultValue;

        // Get handle to custom preferences (not from settings menu)
        // Used for persisting state to storage

        // First, get handle to user settings/preferences
        //   SharedPreferences defaultSharedPreferences =
        //     PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //TODO: Set to our preferences!!!
        // Show Background Picture Preference
       /* currentKey = getString(R.string.showBackgroundKey);
        mUsePicBackground = defaultSharedPreferences.getBoolean(currentKey, true);*/
    }


    private void restoreNonSettingsActivityPreferences ()
    {
        SharedPreferences settings = getSharedPreferences (sPREFS_FIELDS, MODE_PRIVATE);
        String descriptionString = settings.getString (sBRACHOS_DESCRIPTION, "");
        if (!descriptionString.isEmpty ()) {
            brachosDescriptions = restoreStringListFromJSON (descriptionString);
            String brachosNumbersString=settings.getString (sBRACHOS_NUMBERS, "[]");


            ArrayList brachosArrayList=restoreIntegerListFromJSON (brachosNumbersString);

            brachosNumbers = (ArrayList<Integer>) brachosArrayList;


        }



       /*
        String numbersString=settings.getString(sBRACHOS_NUMBERS,"");
        brachosNumbers=(ArrayList<Integer>)restoreListFromJSON(numbersString);
        brachosDescriptions=(ArrayList<String>)restoreListFromJSON(descriptionString);*/
       /*  String currentString;
      //MP==0

       currentString =
                settings.getString (mSUBTOTAL_PREF_KEY, mSubTotalField.getText ().toString ());
        mSubTotalField.setText (currentString);

        currentString = settings.getString (mPAYERS_PREF_KEY, mPayersField.getText ().toString ());
        mPayersField.setText (currentString);
*/
    }

    private void saveNonSettingsActivityPreferences ()
    {
        SharedPreferences settings = getSharedPreferences (sPREFS_FIELDS, MODE_PRIVATE); //MP==0
        SharedPreferences.Editor settingsEditor = settings.edit ();

        settingsEditor.clear ();

        String jsonBrachosDescriptions = getJSON (brachosDescriptions);
        String jsonBrachosNumbers = getJSON (brachosNumbers);
        settingsEditor.putString (sBRACHOS_DESCRIPTION, jsonBrachosDescriptions);
        settingsEditor.putString (sBRACHOS_NUMBERS, jsonBrachosNumbers);
        // Tax and tip are derived from values stored automatically via Settings Activity
        // So we need to store only the other two EditTexts
        //TODO: add our prefs
     /*
        */
        // settingsEditor.putString (mSUBTOTAL_PREF_KEY, mSubTotalField.getText ().toString ());
        //settingsEditor.putString (mPAYERS_PREF_KEY, mPayersField.getText ().toString ());

        settingsEditor.apply ();
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


    private void addBrachosFromRestoredActivity ()
    {
        //TODO: Increment instead of add all
        brachosDescriptions.addAll (brachosDescriptionsToAdd);
        brachosDescriptionsToAdd.clear ();

        brachosNumbers.addAll (brachosNumbersToAdd);
        brachosNumbersToAdd.clear ();
    }

    public void viewTotalBrachos (View view)
    {
        viewTotalBrachos ();
    }

    private void viewTotalBrachos ()
    {
        int counter = 0;
     for (Integer brachosNumber : brachosNumbers) {
            counter += brachosNumber;
        }
        String snackbarText=getString(R.string.total_brachos) + " " +counter;
      showSnackbar(snackbarText);
    }

    public void clearBrachos (View view)
    {
        //call addBrachosFromRestoredActivity to flush the 'ToAdd' ArrayLists if click before they are flushed
        addBrachosFromRestoredActivity ();
        brachosDescriptions.clear ();
        brachosNumbers.clear ();
        showSnackbar("Brachos cleared.");
    }


   /* public <T> void setList(String key, List<T> list)
    {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        set(key, json);
    }*/

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
                viewTotalBrachos();
                return true;
            }

        }

        return super.onOptionsItemSelected (item);
    }

    // LG work-around
    @Override
    public boolean onKeyDown (int keyCode, KeyEvent event)
    {
        boolean isOldLG = ((keyCode == KeyEvent.KEYCODE_MENU) &&
                                   (Build.VERSION.SDK_INT <= 16) &&
                                   (Build.MANUFACTURER.compareTo ("LGE") == 0));

        //noinspection SimplifiableConditionalExpression
        return isOldLG ? true : super.onKeyDown (keyCode, event);
    }

    @Override
    public boolean onKeyUp (int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_MENU) &&
                (Build.VERSION.SDK_INT <= 16) &&
                (Build.MANUFACTURER.compareTo ("LGE") == 0)) {
            openOptionsMenu ();
            return true;
        }
        return super.onKeyUp (keyCode, event);
    }

    private void showSnackbar(String snackbarText){
        final View cl = findViewById (R.id.activity_main);
        Snackbar sb = Snackbar.make (cl, snackbarText,
                Snackbar.LENGTH_LONG);
        sb.show();
        sb.show();
    }
}
