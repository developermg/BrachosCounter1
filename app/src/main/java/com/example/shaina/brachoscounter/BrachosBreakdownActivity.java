package com.example.shaina.brachoscounter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class BrachosBreakdownActivity extends BrachosCounterActivity {

    private ArrayList<String> mBrachosDescription;
    private ArrayList<Integer> mBrachosAmount;
    private BrachosBreakdownAdapter mBrachosAdapter; // The adapter we used for this ListView
    private ArrayList<String> mListOfCheckedItems; // ArrayList of items to be
    // passed to the adapter which will add selected items to the list

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putStringArrayList("CHECKED_ITEMS", mListOfCheckedItems);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brachos);
        processIncomingData();
        //processSavedState(savedInstanceState);
        initializeArrays(savedInstanceState);
        setupListView();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }


    private void processIncomingData() {
        Intent intent = getIntent();
        mBrachosDescription = intent.getStringArrayListExtra("description");
        mBrachosAmount = intent.getIntegerArrayListExtra("amount");
    }

    private void initializeArrays(Bundle savedInstanceState) {
        // initialize the list to be put into the ListView
        mBrachosDescription = getIntent().getStringArrayListExtra("description");
        mBrachosAmount = getIntent().getIntegerArrayListExtra("amount");
    }

    private void setupListView() {
        ListView list = (ListView) findViewById(R.id.listView);
        ArrayList<String> descriptions = mBrachosDescription;
        ArrayList<Integer> numbers = mBrachosAmount;
        String[] descriptionsAndNumbers = new String[descriptions.size()];
        for (int i = 0; i < descriptions.size(); i++) {
            descriptionsAndNumbers[i] = descriptions.get(i) + "  " + numbers.get(i);
        }
        mBrachosAdapter = new BrachosBreakdownAdapter(this, descriptionsAndNumbers,
                R.layout.brachos_breakdown_row, R.id.brachaBreakdown);
        list.setAdapter(mBrachosAdapter);
    }


    @Override public boolean onOptionsItemSelected (MenuItem item)
    {
        int id = item.getItemId ();
        switch (id)
        {
            case R.id.action_settings: {
                launchSettings(mBrachosDescription,mBrachosAmount);
                return true;
            }
            case R.id.action_view_total: {
               showSnackbar(getString(R.string.total_brachos) + " " + getTotalBrachos(mBrachosAmount));
                return true;
            }
            case R.id.about:{
                showAbout();
                return true;
            }
            case android.R.id.home: {
               onBackPressed();
                return true;
            }
            case R.id.action_clear:{
                showSnackbar("Cannot clear in breakdown view.");
                return true;
            }

        }

        return super.onOptionsItemSelected (item);
    }

    private void showSnackbar(String snackbarText){
        final View cl = findViewById (R.id.activity_brachos_breakdown);
        Snackbar sb = Snackbar.make (cl, snackbarText,
                Snackbar.LENGTH_LONG);
        sb.show();
    }
    @Override
    protected void onStop() {
        super.onStop();
        customOnStop(mBrachosDescription,mBrachosAmount);
    }


}
