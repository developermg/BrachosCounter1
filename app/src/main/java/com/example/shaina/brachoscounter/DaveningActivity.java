package com.example.shaina.brachoscounter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//DEAL WITH OPTIONS
public class DaveningActivity extends BrachosCounterActivity {
    DaveningCategoriesAdapter listAdapter;
    ExpandableListView expListView;
    ArrayList<String> daveningCategoryNames;

    HashMap<String, List<String>> brachosNames;
    HashMap<String, List<Integer>> brachosNumbers;

    ArrayList<Integer> totalBrachosNumbers;
    ArrayList<String> totalBrachosDescriptions;
    Boolean mPrefMale;
    Boolean mPrefYotzerHameoros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_davening);
        processIncomingData();
        restorePreferencesSavedFromSettingsActivity();
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new DaveningCategoriesAdapter(this, daveningCategoryNames,
                brachosNames,
                brachosNumbers);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        setupAddButton();
        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {

                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

                listAdapter.checkAllInGroup(groupPosition);

            }
        });



        // ListView on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                return false;
            }
        });

    }

    private void processIncomingData(){
        Intent intent = getIntent();
        totalBrachosDescriptions = intent.getStringArrayListExtra(sBRACHOS_DESCRIPTION);
        totalBrachosNumbers = intent.getIntegerArrayListExtra(sBRACHOS_NUMBERS);
    }
    private void restorePreferencesSavedFromSettingsActivity()
    {
        String currentKey;

        // Get handle to custom preferences (not from settings menu)
        // Used for persisting state to storage

        // First, get handle to user settings/preferences
        SharedPreferences defaultSharedPreferences =
                PreferenceManager.getDefaultSharedPreferences (getApplicationContext ());


        currentKey = getString (R.string.male_option);
        mPrefMale = defaultSharedPreferences.getBoolean (currentKey, true);
        currentKey = getString (R.string.yotzer_hameoros_option);
        mPrefYotzerHameoros=defaultSharedPreferences.getBoolean (currentKey, true);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    //get selected Brachos gets an arraylist of the numbers for the brachos selected
    private ArrayList<Integer> getSelectedBrachosNumbers() {
        ArrayList<Integer> brachosNumbers = new ArrayList<>();
        int sum = 0;
        for (int mGroupPosition = 0;
             mGroupPosition < listAdapter.getGroupCount();
             mGroupPosition++) {

            for (int mChildPosition = 0; mChildPosition < listAdapter.getChildrenCount(mGroupPosition); mChildPosition++) {
                if (listAdapter.childIsChecked(mGroupPosition, mChildPosition)) {
                    // brachosNumbers.add(mChildPosition);
                    brachosNumbers.add(listAdapter.getChildNumericData(mGroupPosition, mChildPosition));
                }
            }
        }
        return brachosNumbers;
    }


    private ArrayList<String> getSelectedBrachosDescriptions()

    {
        ArrayList<String> brachosDescriptions = new ArrayList<>();
        for (int mGroupPosition = 0;
             mGroupPosition < listAdapter.getGroupCount();
             mGroupPosition++) {
            for (int mChildPosition = 0; mChildPosition < listAdapter.getChildrenCount(mGroupPosition); mChildPosition++) {
                if (listAdapter.childIsChecked(mGroupPosition, mChildPosition)) {
                    brachosDescriptions.add(listAdapter.getChild(mGroupPosition, mChildPosition));
                }
            }
        }
        return brachosDescriptions;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override public boolean onOptionsItemSelected (MenuItem item)
    {
        int id = item.getItemId ();
        switch (id)
        {
            case R.id.action_settings: {
                launchSettings(totalBrachosDescriptions,totalBrachosNumbers);
                return true;
            }
            case R.id.action_view_total: {
                showSnackbar(getString(R.string.total_brachos) + " " + getTotalBrachos(totalBrachosNumbers));
                return true;
            }
            case R.id.action_view_total_breakdown:{
                launchTotalBreakdown(totalBrachosDescriptions,totalBrachosNumbers);
                return true;
            }
            case R.id.about:{
                showAbout();
                return true;
            }
            case android.R.id.home: {
                super.finish();
                return true;
            }
            case R.id.action_clear:{
                clearBrachos(totalBrachosDescriptions,totalBrachosNumbers);
                showSnackbar("Brachos cleared.");
                return true;
            }

        }

        return super.onOptionsItemSelected (item);
    }

    private void showSnackbar(String snackbarText){
        final View cl = findViewById (R.id.activity_davening);
        Snackbar sb = Snackbar.make (cl, snackbarText,
                Snackbar.LENGTH_LONG);
        sb.show();
    }

    private void prepareListData() {
        daveningCategoryNames = new ArrayList<String>();
        // Add child data
        brachosNames = new HashMap<String, List<String>>();
        // Header, Child numeric value data
        brachosNumbers = new HashMap<String, List<Integer>>();
        prepareShachrisData();
        prepareMinchaData();
        prepareMaarivData();
        prepareHallelData();
        prepareMussafData();
    }

    private void prepareShachrisData() {

        daveningCategoryNames.add(getString(R.string.Shachris));

        ArrayList<String> brachos = new ArrayList<>();
        brachos.add(getString(R.string.Birchos_HaShachar));
        brachos.add(getString(R.string.Birchos_HaTorah));
        brachos.add(getString(R.string.Psukei_DZimra));
        brachos.add(getString(R.string.Birchos_Krias_Shema));
        brachos.add(getString(R.string.Shemoneh_Esrei));

        ArrayList<Integer> numbers = new ArrayList<>();
        if (mPrefMale){
            numbers.add(getApplicationContext().getResources().getInteger(R.integer.Birchos_HaShachar_men));
        }
        else{
            numbers.add(getApplicationContext().getResources().getInteger(R.integer.Birchos_HaShachar_women));
        }

        numbers.add(getApplicationContext().getResources().getInteger(R.integer.Birchos_HaTorah));
        numbers.add(getApplicationContext().getResources().getInteger(R.integer.Psukei_DZimra));
        numbers.add(getApplicationContext().getResources().getInteger(R.integer.Birchos_Krias_Shema));
        numbers.add(getApplicationContext().getResources().getInteger(R.integer.Shemoneh_Esrei));

        brachosNames.put(getString(R.string.Shachris), brachos);
        brachosNumbers.put(getString(R.string.Shachris), numbers);


    }

    private void prepareMinchaData() {
        daveningCategoryNames.add(getString(R.string.Mincha));

        ArrayList<String> brachos = new ArrayList<>();
        brachos.add(getString(R.string.Shemoneh_Esrei));

        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(getApplicationContext().getResources().getInteger(R.integer.Shemoneh_Esrei));

        brachosNames.put(getString(R.string.Mincha), brachos);
        brachosNumbers.put(getString(R.string.Mincha), numbers);

    }

    private void prepareMaarivData() {

        daveningCategoryNames.add(getString(R.string.Maariv));

        ArrayList<String> brachos = new ArrayList<>();
        brachos.add(getString(R.string.Birchos_Krias_Shema));
        brachos.add(getString(R.string.Shemoneh_Esrei));

        ArrayList<Integer> numbers = new ArrayList<>();
        if (mPrefYotzerHameoros){
            numbers.add(getApplicationContext().getResources().getInteger(R.integer.Birchos_Krias_Shema_Maariv_full));
        }
        else{
            numbers.add(getApplicationContext().getResources().getInteger(R.integer.Birchos_Krias_Shema_Maariv));
        }

        numbers.add(getApplicationContext().getResources().getInteger(R.integer.Shemoneh_Esrei));

        brachosNames.put(getString(R.string.Maariv), brachos);
        brachosNumbers.put(getString(R.string.Maariv), numbers);
    }

    private void prepareHallelData() {

        daveningCategoryNames.add(getString(R.string.Hallel));

        ArrayList<String> brachos = new ArrayList<>();
        brachos.add(getString(R.string.Hallel));

        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(getApplicationContext().getResources().getInteger(R.integer.Hallel));

        brachosNames.put(getString(R.string.Hallel), brachos);
        brachosNumbers.put(getString(R.string.Hallel), numbers);


    }

    private void prepareMussafData() {

        daveningCategoryNames.add(getString(R.string.Mussaf));

        ArrayList<String> brachos = new ArrayList<>();
        brachos.add(getString(R.string.Shemoneh_Esrei));

        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(getApplicationContext().getResources().getInteger(R.integer.Shemoneh_Esrei));

        brachosNames.put(getString(R.string.Mussaf), brachos);
        brachosNumbers.put(getString(R.string.Mussaf), numbers);


    }

    private void setupAddButton() {

        // Create and set a Listener for the FAB to respond to clicks on its link
        Button addButton = (Button) findViewById(R.id.addButton);

        assert addButton != null;

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBrachosToTotalAndFinish();
            }
        });

    }

    private void addBrachosToTotalAndFinish(){

        addBrachos(totalBrachosDescriptions,totalBrachosNumbers,getSelectedBrachosDescriptions(),getSelectedBrachosNumbers());

       finish();
    }
    @Override
    protected void onStop() {
        super.onStop();
        customOnStop(totalBrachosDescriptions,totalBrachosNumbers);
    }

    @Override
    public void finish() {
        //TODO make sure can pass back none!
        Intent results = new Intent();

        results.putIntegerArrayListExtra(sBRACHOS_NUMBERS,totalBrachosNumbers);
        results.putStringArrayListExtra(sBRACHOS_DESCRIPTION, totalBrachosDescriptions);
       setResult(RESULT_OK, results);
     //   saveNonSettingsActivityPreferences(totalBrachosDescriptions,totalBrachosNumbers);

        super.finish();
    }

}

