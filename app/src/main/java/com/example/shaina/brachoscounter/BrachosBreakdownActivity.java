package com.example.shaina.brachoscounter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
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
        setupActionBar();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private void setupActionBar() {
        try {
            getDelegate().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException nullPointerException) {
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings: {
                //showSettingsActivity ();
                return true;
            }
            case R.id.action_view_total: {
                //TODO: viewTotalBrachos();
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
        }
        return super.onOptionsItemSelected(item);
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
        AlertDialog alertDialogAbout = new AlertDialog.Builder(BrachosBreakdownActivity.this)
                .create();
        alertDialogAbout.setTitle(getString(R.string.aboutDialog_title));
        ;
        alertDialogAbout.setMessage(getString(R.string.aboutDialog_banner));
        alertDialogAbout.setButton(DialogInterface.BUTTON_NEUTRAL,
                getString(R.string.OK), dialogOnClickListener);

        // Show the dialog window
        alertDialogAbout.show();
    }

}
