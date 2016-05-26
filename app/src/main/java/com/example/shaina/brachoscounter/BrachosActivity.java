package com.example.shaina.brachoscounter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Shaina on 5/17/2016.
 */
public class BrachosActivity extends AppCompatActivity {


    protected String[] mBrachosArray;
    private BrachosAdapter mBrachosAdapter; // The adapter we used for this ListView
    private ArrayList<String> mListOfCheckedItems; // ArrayList of items to be
    // passed to the adapter which will add selected items to the list

    /*@Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putStringArrayList("CHECKED_ITEMS", mListOfCheckedItems);
        super.onSaveInstanceState(outState);
    }*/

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
        mBrachosArray = intent.getStringArrayExtra(getString(R.string.brachosList));
    }

    private void initializeArrays(Bundle savedInstanceState) {
        // initialize the list to be put into the ListView
        mBrachosArray = getIntent().getStringArrayExtra(getString(R.string.brachosList));

        // initialize the list to be passed into the Adapter
        // to hold the items whose respective buttons are clicked

        // if we have no saved bundle, then make a new list; else, use the ArrayList from bundle
        mListOfCheckedItems = (savedInstanceState == null ?
                new ArrayList<String>(18) :
                savedInstanceState.getStringArrayList("CHECKED_ITEMS"));
    }

    private void setupListView() {
        ListView list = (ListView) findViewById(R.id.listView);
        //assert mListOfCheckedItems != null;
        mBrachosAdapter = new BrachosAdapter(this, mBrachosArray, R.layout.listview_row,
                R.id.brachaOption, R.id.addSymbol, mListOfCheckedItems);
        list.setAdapter(mBrachosAdapter);
    }

    /*protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
                //TODO: viewTotalBrachos();
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

        }

        return super.onOptionsItemSelected (item);
    }

    private void getSelectedBrachosNumbers(){

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
        AlertDialog alertDialogAbout = new AlertDialog.Builder (BrachosActivity.this).create ();
        alertDialogAbout.setTitle (getString (R.string.aboutDialog_title));;
        alertDialogAbout.setMessage (getString (R.string.aboutDialog_banner));
        alertDialogAbout.setButton (DialogInterface.BUTTON_NEUTRAL,
                getString (R.string.OK), dialogOnClickListener);

        // Show the dialog window
        alertDialogAbout.show ();
    }

    private void getSelectedBrachosDescriptions() {
    }

    @Override
    public void finish() {
        Intent results = new Intent();
        results.putStringArrayListExtra("BRACHOS_DESCRIPTIONS", mListOfCheckedItems);
        results.putIntegerArrayListExtra("BRACHOS_NUMBERS", getArrayListOfBrachosNumbers());
        setResult(RESULT_OK, results);

        super.finish();
    }

    private ArrayList<Integer> getArrayListOfBrachosNumbers(){
        ArrayList<Integer> numbers=new ArrayList<>();
        for (int i = 0; i < mListOfCheckedItems.size(); i++) {
            numbers.add(1);
        }
        return numbers;
    }

}