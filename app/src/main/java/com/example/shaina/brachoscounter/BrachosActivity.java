package com.example.shaina.brachoscounter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class BrachosActivity extends BrachosCounterActivity {

    protected String[] mBrachosArray;
    private ArrayList<Integer> mTotalBrachosNumbers;
    private ArrayList<String> mTotalBrachosDescriptions;
    private ArrayList<String> mListOfCheckedItems; // ArrayList of items to be passed to the adapter which will add selected items to the list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brachos);
        processIncomingData();
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
        mBrachosArray = intent.getStringArrayExtra(getString(R.string.brachosList));
        mTotalBrachosDescriptions = intent.getStringArrayListExtra(sBRACHOS_DESCRIPTION);
        mTotalBrachosNumbers = intent.getIntegerArrayListExtra(sBRACHOS_NUMBERS);
    }

    private void initializeArrays(Bundle savedInstanceState) {
        // initialize the list to be put into the ListView
        mBrachosArray = getIntent().getStringArrayExtra(getString(R.string.brachosList));

        // initialize the list to be passed into the Adapter
        // to hold the items whose respective buttons are clicked

        // if we have no saved bundle, then make a new list; else, use the ArrayList from bundle
        mListOfCheckedItems = (savedInstanceState == null ?
                new ArrayList<String>() :
                savedInstanceState.getStringArrayList("CHECKED_ITEMS"));
    }

    private void setupListView() {
        ListView list = (ListView) findViewById(R.id.listView);
        assert mListOfCheckedItems != null;
        BrachosAdapter mBrachosAdapter = new BrachosAdapter(this, mBrachosArray, R.layout.listview_row,
                R.id.brachaOption, R.id.addSymbol, mListOfCheckedItems);
        list.setAdapter(mBrachosAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings: {
                addBrachosToTotal();
                launchSettings(mTotalBrachosDescriptions, mTotalBrachosNumbers);
                return true;
            }
            case R.id.action_view_total: {
                addBrachosToTotal();
                showSnackbar(getString(R.string.total_brachos) + " " + getTotalBrachos(mTotalBrachosNumbers));
                return true;
            }
            case R.id.action_view_total_breakdown: {
                addBrachosToTotal();
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
                clearBrachos(mTotalBrachosDescriptions, mTotalBrachosNumbers);
                showSnackbar("Brachos cleared.");
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        customOnStop(mTotalBrachosDescriptions, mTotalBrachosNumbers);
    }

    private void showSnackbar(String snackbarText) {
        final View cl = findViewById(R.id.activity_brachos);
        assert cl != null;
        Snackbar sb = Snackbar.make(cl, snackbarText,
                Snackbar.LENGTH_LONG);
        sb.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        addBrachosToTotal();
    }

    private void addBrachosToTotal() {
        addBrachos(mTotalBrachosDescriptions, mTotalBrachosNumbers, mListOfCheckedItems, getArrayListOfBrachosNumbers());
        mListOfCheckedItems.clear();
    }

    @Override
    public void finish() {
        addBrachosToTotal();
        Intent results = new Intent();
        results.putIntegerArrayListExtra(sBRACHOS_NUMBERS, mTotalBrachosNumbers);
        results.putStringArrayListExtra(sBRACHOS_DESCRIPTION, mTotalBrachosDescriptions);
        setResult(RESULT_OK, results);

        super.finish();
    }

    private ArrayList<Integer> getArrayListOfBrachosNumbers() {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < mListOfCheckedItems.size(); i++) {
            numbers.add(1);
        }
        return numbers;
    }

}