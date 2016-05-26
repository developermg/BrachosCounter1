package com.example.shaina.brachoscounter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddYourOwnActivity extends BrachosCounterActivity {
    private NumberPicker numberPicker;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_your_own);
        setupActionBar();
        numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(100);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setValue(0);

        editText = (EditText) findViewById(R.id.editText);
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
        AlertDialog alertDialogAbout = new AlertDialog.Builder (AddYourOwnActivity.this).create ();
        alertDialogAbout.setTitle (getString (R.string.aboutDialog_title));;
        alertDialogAbout.setMessage (getString (R.string.aboutDialog_banner));
        alertDialogAbout.setButton (DialogInterface.BUTTON_NEUTRAL,
                getString (R.string.OK), dialogOnClickListener);

        // Show the dialog window
        alertDialogAbout.show ();
    }
    private void setupActionBar() {
        try {
            getDelegate().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException nullPointerException) {
            //nullPointerException.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void addBracha(View view) {
        if (numberIsZeroAndContainsText()){
            displayMessage("Zero is not a valid number");

        }
        else if(numberIsGreaterThanZeroAndNoDescription()){
            displayMessage("Description required.");
        }
        else{
            finish();
        }

    }

    private boolean numberIsZeroAndContainsText(){
        return (numberPicker.getValue()==0 && !editText.getText().toString().trim().isEmpty());
    }

    private boolean numberIsGreaterThanZeroAndNoDescription(){
        return (numberPicker.getValue()!=0 && editText.getText().toString().trim().isEmpty());
    }

    @Override
    public void finish() {
        Intent results = new Intent();
        results.putExtra("BRACHOS_NUMBER", numberPicker.getValue());
        results.putExtra("BRACHOS_DESCRIPTION", editText.getText().toString());
        setResult(RESULT_OK, results);

        super.finish();
    }


    private void displayMessage(String message){
        Toast.makeText(
                getApplicationContext(),
                message, Toast.LENGTH_SHORT)
                .show();
    }
}
