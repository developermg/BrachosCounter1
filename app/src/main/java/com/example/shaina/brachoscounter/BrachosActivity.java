package com.example.shaina.brachoscounter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Shaina on 5/17/2016.
 */
public class BrachosActivity extends AppCompatActivity {


    private BrachosAdapter mBrachosAdapter; // The adapter we used for this ListView
    protected String[] mBrachosArray;
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
            //nullPointerException.printStackTrace();
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

        assert mListOfCheckedItems != null;
        mBrachosAdapter = new BrachosAdapter(this, mBrachosArray, R.layout.listview_row,
                                             R.id.brachaOption, R.id.addSymbol,
                                             mListOfCheckedItems);
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id==android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getSelectedBrachosNumbers(){

    }

    private void getSelectedBrachosDescriptions(){

    }
    @Override
    public void finish() {
        Toast.makeText(this, " in toast", Toast.LENGTH_LONG).show();
        Intent results = new Intent();
        results.putStringArrayListExtra("BRACHOS_DESCRIPTIONS", mListOfCheckedItems);
        results.putIntegerArrayListExtra("BRACHOS_NUMBERS", getArrayListOfBrachosNumbers());
        setResult(RESULT_OK, results);

        super.finish();
    }

    private ArrayList<Integer> getArrayListOfBrachosNumbers(){
        ArrayList<Integer> numbers=new ArrayList<>();
        for (int i=0; i<mListOfCheckedItems.size(); i++){
            numbers.add(1);
        }

        return numbers;
    }

}
