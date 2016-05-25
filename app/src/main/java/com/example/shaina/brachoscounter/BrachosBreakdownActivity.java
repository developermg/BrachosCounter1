package com.example.shaina.brachoscounter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class BrachosBreakdownActivity extends AppCompatActivity {

    protected ArrayList<String> mBrachosDescription;
    protected ArrayList<Integer> mBrachosAmount;
    private BrachosBreakdownAdapter mBrachosAdapter; // The adapter we used for this ListView

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //outState.putStringArrayList("CHECKED_ITEMS", mListOfCheckedItems);
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

    private void setupActionBar() {
        try {
            getDelegate().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException nullPointerException) {
        }
    }

    private void processIncomingData() {
        mBrachosDescription = getIntent().getStringArrayListExtra("description");
        mBrachosAmount = getIntent().getIntegerArrayListExtra("amount");
    }

    private void initializeArrays(Bundle savedInstanceState) {
        // initialize the list to be put into the ListView
        mBrachosDescription = getIntent().getStringArrayListExtra("description");
        mBrachosAmount = getIntent().getIntegerArrayListExtra("amount");
    }

    private void setupListView() {
        ListView list = (ListView) findViewById(R.id.listView);
        mBrachosAdapter = new BrachosBreakdownAdapter(this, mBrachosDescription, mBrachosAmount,
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
