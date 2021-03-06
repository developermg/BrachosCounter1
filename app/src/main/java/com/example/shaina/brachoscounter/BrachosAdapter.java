package com.example.shaina.brachoscounter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BrachosAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private int mRowLayoutResourceId, mRowTextViewResourceId, mRowButtonResourceId;
    private ArrayList<String> mClickedItemsList;

    public BrachosAdapter(Context context, String[] objects, int rowResourceId,
                          int rowTextViewResourceId, int rowButtonResourceId,
                          ArrayList<String> clickedItemsList) {

        super(context, rowResourceId, objects); // superclass will handle the String array portion of this

        // store a reference to the Context
        mContext = context;

        // We will need this later to inflate each new row
        mLayoutInflater = LayoutInflater.from(context);

        // We will need this later to pass to the LayoutInflater to inflate this res. as the new row
        mRowLayoutResourceId = rowResourceId;

        // We will need these later to know what are the IDs of the resources inside the Row XML
        mRowTextViewResourceId = rowTextViewResourceId;
        mRowButtonResourceId = rowButtonResourceId;

        // Store a reference to the list passed in from the Activity/Fragment to add to the list
        mClickedItemsList = clickedItemsList;

        // The list must have been already initialized or else we can't add any items to it later
        if (mClickedItemsList == null) {
            throw new NullPointerException
                    ("The items list must be initialized before being passed in to the Adapter");
        }

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // set the current row to either the converted View or, if null, get a new View
        View currentRow = convertView != null ? convertView : getNewView(parent);

        final ViewHolder currentViewHolder = (ViewHolder) currentRow.getTag();

        // set the text of the current TextView to whatever the text is in the ArrayAdapter
        currentViewHolder.mCurrentTextView.setText(getItem(position));

        // Store a reference to the current text
        final String currentText = currentViewHolder.mCurrentTextView.getText().toString();

        // set the left drawable of the button to reflect if the ArrayList does not/does contain this item
        currentViewHolder.mCurrentButton.setCompoundDrawablesWithIntrinsicBounds(
                ContextCompat.getDrawable(mContext,
                        R.drawable.ic_add_black_24dp), null, null, null);

        // set the listener
        currentViewHolder.mCurrentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String actionPerformed = "Added ";
                mClickedItemsList.add(currentText); // Add the current row's text to the list
                // Tell the user what was just added
                Toast.makeText(mContext, actionPerformed + currentText, Toast.LENGTH_SHORT).show();
            }
        });
        return currentRow; // set the current row as the View to return when getView is called
    }

    @NonNull
    private View getNewView(ViewGroup parent) {
        // inflate a new instance of the XML layout passed in to the constructor
        View newRow = mLayoutInflater.inflate(mRowLayoutResourceId, parent, false);

        // create a new instance of the custom ViewHolder class above
        ViewHolder newViewHolder = new ViewHolder();

        // set the TextView and Button member fields in this ViewHolder object to refer to
        // the newly-inflated current row's TextView and Button (respectively)
        newViewHolder.mCurrentTextView = (TextView) newRow.findViewById(mRowTextViewResourceId);
        newViewHolder.mCurrentButton = (Button) newRow.findViewById(mRowButtonResourceId);

        // store a reference to this ViewHolder object in the newly-inflated row's Tag attribute
        // Now, we won't need to do a findViewById when processing a converted object in getView
        // because we just stored a reference to each object in the ViewHolder
        newRow.setTag(newViewHolder);

        // Return the newly-inflated row that also contains a ViewHolder reference in its tag
        return newRow;
    }

    static class ViewHolder {
        TextView mCurrentTextView;
        Button mCurrentButton;
    }

}