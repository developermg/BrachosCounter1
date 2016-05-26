package com.example.shaina.brachoscounter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends BrachosCounterActivity {

    final int REQUEST_CODE = 1;
    ArrayList<String> brachosDescriptions;
    ArrayList<Integer> brachosNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupActionBar();
        brachosDescriptions = new ArrayList<>();
        brachosNumbers = new ArrayList<>();
        restoreNonSettingsActivityPreferences();
        PreferenceManager.setDefaultValues(this, R.xml.pref_general, true);
    }

    private void setupActionBar() {
        try {
            getDelegate().getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } catch (NullPointerException nullPointerException) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            brachosNumbers = data.getIntegerArrayListExtra(sBRACHOS_NUMBERS);
            brachosDescriptions = data.getStringArrayListExtra(sBRACHOS_DESCRIPTION);
        }
    }

    public void launchDaveningPage(View view) {
        Intent intent = new Intent(this, DaveningActivity.class);
        intent.putIntegerArrayListExtra(sBRACHOS_NUMBERS, brachosNumbers);
        intent.putStringArrayListExtra(sBRACHOS_DESCRIPTION, brachosDescriptions);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void launchAddYourOwnPage(View view) {
        Intent intent = new Intent(this, AddYourOwnActivity.class);
        intent.putIntegerArrayListExtra(sBRACHOS_NUMBERS, brachosNumbers);
        intent.putStringArrayListExtra(sBRACHOS_DESCRIPTION, brachosDescriptions);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void launchFoodDrink(View view) {
        Intent intent = new Intent(this, BrachosActivity.class);
        String[] foodBrachos = {"Hamotzi", "Mezonos", "Hagafen", "Haetz", "Ha'adama", "Shehakol",
                "Birkas Hamazon", "Al Hamichya", "Borei Nefashos"};
        intent.putExtra(getString(R.string.brachosList), foodBrachos);
        intent.putIntegerArrayListExtra(sBRACHOS_NUMBERS, brachosNumbers);
        intent.putStringArrayListExtra(sBRACHOS_DESCRIPTION, brachosDescriptions);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void launchHolidays(View view) {
        Intent intent = new Intent(this, BrachosActivity.class);
        String[] holidayBrachos = {"L'hadlik Ner Chanuka", "She'asa Nisim", "Shehechiyanu", "Lulav and Esrog",
                "Leisheiv BaSukkah", "Mikra Megillah"};
        intent.putExtra(getString(R.string.brachosList), holidayBrachos);
        intent.putIntegerArrayListExtra(sBRACHOS_NUMBERS, brachosNumbers);
        intent.putStringArrayListExtra(sBRACHOS_DESCRIPTION, brachosDescriptions);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void launchBirchosHanehenin(View view) {
        Intent intent = new Intent(this, BrachosActivity.class);
        String[] haneheninBrachos = {"Minei Besamim", "Atzei Besamim", "Isvei Besamim", "Reiach tov l'peiros"};
        intent.putExtra(getString(R.string.brachosList), haneheninBrachos);
        intent.putIntegerArrayListExtra(sBRACHOS_NUMBERS, brachosNumbers);
        intent.putStringArrayListExtra(sBRACHOS_DESCRIPTION, brachosDescriptions);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void launchMiscellaneous(View view) {
        Intent intent = new Intent(this, BrachosActivity.class);
        String[] miscBrachos = {"Sheva Brachos Bracha", "Sefiras Haomer", "Mezuzah", "Tevilas Keilim",
                "Hafrashas Challah", "Maaser", "Oseh Maaseh Beraishis", "Shekocho Ugvuraso",
                "Seeing a rainbow", "Seeing an ocean", "Meshaneh Habriyos", "Seeing a blooming tree",
                "Seeing a Torah scholar", "Seeing a secular scholar", "Chacham Harazim", "Dayan Haemes",
                "HaTov V'hameitiv", "Sha'asa li nes", "Shehechiyanu", "Asher Yatzar", "Tefillas Haderech"};
        intent.putExtra(getString(R.string.brachosList), miscBrachos);
        intent.putIntegerArrayListExtra(sBRACHOS_NUMBERS, brachosNumbers);
        intent.putStringArrayListExtra(sBRACHOS_DESCRIPTION, brachosDescriptions);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void launchTotalBreakdown(View view) {
        launchTotalBreakdown(brachosDescriptions, brachosNumbers);
    }

    @Override
    protected void onStop() {
        super.onStop();
        customOnStop(brachosDescriptions, brachosNumbers);
    }

    private void restoreNonSettingsActivityPreferences() {
        SharedPreferences settings = getSharedPreferences(sPREFS_FIELDS, MODE_PRIVATE);
        String descriptionString = settings.getString(sBRACHOS_DESCRIPTION, "");
        if (!descriptionString.isEmpty()) {
            brachosDescriptions = restoreStringListFromJSON(descriptionString);
            String brachosNumbersString = settings.getString(sBRACHOS_NUMBERS, "[]");
            brachosNumbers = restoreIntegerListFromJSON(brachosNumbersString);
        }
    }

    public void viewTotalBrachos(View view) {
        viewTotalBrachos();
    }

    public void viewTotalBrachos() {
        showSnackbar(getString(R.string.total_brachos) + " " + getTotalBrachos(brachosNumbers));
    }

    public void clearBrachos(View view) {
        super.clearBrachos(brachosDescriptions, brachosNumbers);
        showSnackbar("Brachos cleared.");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings: {
                launchSettings(brachosDescriptions, brachosNumbers);
                return true;
            }
            case R.id.action_view_total: {
                showSnackbar(getString(R.string.total_brachos) + " " + getTotalBrachos(brachosNumbers));
                return true;
            }
            case R.id.action_view_total_breakdown: {
                launchTotalBreakdown(brachosDescriptions, brachosNumbers);
                return true;
            }
            case R.id.about: {
                showAbout();
                return true;
            }
            case android.R.id.home: {
                super.finish();
                return true;
            }
            case R.id.action_clear: {
                clearBrachos(brachosDescriptions, brachosNumbers);
                showSnackbar("Brachos cleared.");
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    // LG work-around
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isOldLG = ((keyCode == KeyEvent.KEYCODE_MENU) &&
                (Build.VERSION.SDK_INT <= 16) &&
                (Build.MANUFACTURER.compareTo("LGE") == 0));
        //noinspection SimplifiableConditionalExpression
        return isOldLG ? true : super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_MENU) &&
                (Build.VERSION.SDK_INT <= 16) &&
                (Build.MANUFACTURER.compareTo("LGE") == 0)) {
            openOptionsMenu();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }


    private void showSnackbar(String snackbarText) {
        final View cl = findViewById(R.id.activity_main);
        assert cl != null;
        Snackbar sb = Snackbar.make(cl, snackbarText,
                Snackbar.LENGTH_LONG);
        sb.show();
    }

}

