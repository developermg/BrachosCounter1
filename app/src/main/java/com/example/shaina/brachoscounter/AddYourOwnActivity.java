package com.example.shaina.brachoscounter;

import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.ArrayList;

public class AddYourOwnActivity extends BrachosCounterActivity {
    private NumberPicker numberPicker;
    private EditText editText;
    private ArrayList<Integer> mTotalBrachosNumbers;
    private ArrayList<String> mTotalBrachosDescriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_your_own);
        processIncomingData();
        setupNumberPicker();

        editText = (EditText) findViewById(R.id.editText);
    }
    private void processIncomingData(){
        Intent intent = getIntent();
        mTotalBrachosDescriptions = intent.getStringArrayListExtra(sBRACHOS_DESCRIPTION);
        mTotalBrachosNumbers = intent.getIntegerArrayListExtra(sBRACHOS_NUMBERS);
    }

    private void setupNumberPicker(){
        numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(100);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setValue(0);
    }
    @Override public boolean onOptionsItemSelected (MenuItem item)
    {
        int id = item.getItemId ();
        switch (id)
        {
            case R.id.action_settings: {
                launchSettings(mTotalBrachosDescriptions,mTotalBrachosNumbers);
                return true;
            }
            case R.id.action_view_total: {
                showSnackbar(getString(R.string.total_brachos) + " " + getTotalBrachos(mTotalBrachosNumbers));
                return true;
            }
            case R.id.action_view_total_breakdown:{
                launchTotalBreakdown(mTotalBrachosDescriptions,mTotalBrachosNumbers);
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
                clearBrachos(mTotalBrachosDescriptions,mTotalBrachosNumbers);
                showSnackbar("Brachos cleared.");
                return true;
            }

        }
        return super.onOptionsItemSelected (item);
    }
    private void showSnackbar(String snackbarText){
        final View cl = findViewById (R.id.activity_add_your_own);
        Snackbar sb = Snackbar.make (cl, snackbarText,
                Snackbar.LENGTH_LONG);
        sb.show();
    }

    public void addBracha(View view) {
        if (numberIsZeroAndContainsText()){
            showSnackbar("Zero is not a valid number");

        }
        else if(numberIsGreaterThanZeroAndNoDescription()){
            showSnackbar("Description required.");
        } else if (numberIsZeroAndNoText()) {
            showSnackbar("Please enter a bracha.");
        }
        else{
            addBrachos();
            finish();
        }

    }

    private boolean numberIsZeroAndNoText() {
        return (numberPicker.getValue() == 0 && editText.getText().toString().trim().isEmpty());

    }
    private void addBrachos(){
        addBrachos(mTotalBrachosDescriptions,mTotalBrachosNumbers,editText.getText().toString(),numberPicker.getValue());
    }
    private boolean numberIsZeroAndContainsText(){
        return (numberPicker.getValue()==0 && !editText.getText().toString().trim().isEmpty());
    }

    private boolean numberIsGreaterThanZeroAndNoDescription(){
        return (numberPicker.getValue()!=0 && editText.getText().toString().trim().isEmpty());
    }

    @Override
    protected void onStop() {
        super.onStop();
        customOnStop(mTotalBrachosDescriptions,mTotalBrachosNumbers);
    }
    @Override
    public void finish() {
        Intent results = new Intent();
        results.putIntegerArrayListExtra(sBRACHOS_NUMBERS, mTotalBrachosNumbers);
        results.putStringArrayListExtra(sBRACHOS_DESCRIPTION, mTotalBrachosDescriptions);
        setResult(RESULT_OK, results);

        super.finish();
    }


}
