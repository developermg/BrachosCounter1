package com.example.shaina.brachoscounter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddYourOwnActivity extends AppCompatActivity {
    private NumberPicker numberPicker;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_your_own);
        numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(100);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setValue(0);

        editText = (EditText) findViewById(R.id.editText);
    }

    //TODO: add back button

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
